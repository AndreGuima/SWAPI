package com.adpguima.starwars.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		return planetsRepository.findAll();
	}

	public Optional<Planets> findById(String id) {
		return planetsRepository.findById(id);
	}

	public List<Planets> findByNameIgnoreCase(String name) {
		return planetsRepository.findByNameIgnoreCase(name);
	}

	public Planets save(Planets planet) {
		return planetsRepository.save(planet);
	}

	public void delete(String id) {
		planetsRepository.deleteById(id);
	}

	public Planets create(Planets planetToAdd) {
		SwapiPlanet apiPlanet = swapiService.getSwapiPlanetByName(planetToAdd.getName());
		Planets savedPlanet = new Planets();

		if (apiPlanet != null) {
			savedPlanet = planetsRepository.save(planetToAdd);
			if (apiPlanet.getFilms() == null) {
				savedPlanet.setFilmsCount(0);
			} else {
				savedPlanet.setFilmsCount(apiPlanet.getFilms().length);
			}
		}

		return savedPlanet;
	}

}