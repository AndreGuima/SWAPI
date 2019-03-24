package com.adpguima.starwars.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import com.adpguima.starwars.exception.PlanetDuplicationInDatabaseException;
import com.adpguima.starwars.exception.PlanetNotFoundException;
import com.adpguima.starwars.model.Planets;
import com.adpguima.starwars.model.SwapiPlanet;
import com.adpguima.starwars.repository.PlanetsRepository;

@Service
public class PlanetsService {

	@Autowired
	PlanetsRepository planetsRepository;

	@Autowired
	private SwapiService swapiService;

	public List<Planets> list() {
		List<Planets> allPlanets = planetsRepository.findAll();
		for (Planets planets : allPlanets) {
			SwapiPlanet swapiPlanetByName = swapiService.getSwapiPlanetByName(planets.getName());
			planets.setFilmsCount(swapiPlanetByName.getFilmsCount());
		}
		return allPlanets;
	}

	public Planets findById(long id) {
		Planets planet = planetsRepository.findById(id)
				.orElseThrow(() -> new PlanetNotFoundException("Planet not found " + id));
		SwapiPlanet swapiPlanetByName = swapiService.getSwapiPlanetByName(planet.getName());
		planet.setFilmsCount(swapiPlanetByName.getFilmsCount());
		return planet;
	}

	public Planets findByNameIgnoreCase(String name) {
		List<Planets> planets = planetsRepository.findByNameIgnoreCase(name);

		if (planets.size() > 0) {
			Planets planet = planets.get(0);

			SwapiPlanet swapiPlanetByName = swapiService.getSwapiPlanetByName(planet.getName());
			planet.setFilmsCount(swapiPlanetByName.getFilmsCount());

			return planet;
		}
		return null;
	}

	public void delete(long id) {
		planetsRepository.deleteById(id);
	}

	public Planets add(Planets planetToAdd) throws RestClientException, PlanetNotFoundException {
		SwapiPlanet apiPlanet = swapiService.getSwapiPlanetByName(planetToAdd.getName());

		// Check duplication by name
		if (planetsRepository.findByNameIgnoreCase(apiPlanet.getName()).size() == 0) {
			Planets savedPlanet = planetsRepository.save(planetToAdd);
			savedPlanet.setFilmsCount(apiPlanet.getFilmsCount());
			return savedPlanet;
		}

		throw new PlanetDuplicationInDatabaseException(
				"Planet name must be unique in the database, check the name you've input");
	}

}