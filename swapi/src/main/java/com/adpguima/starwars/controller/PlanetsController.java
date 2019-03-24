package com.adpguima.starwars.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.adpguima.starwars.model.Planets;
import com.adpguima.starwars.model.SwapiPlanet;
import com.adpguima.starwars.service.PlanetsService;
import com.adpguima.starwars.service.SwapiService;

@RestController
public class PlanetsController {

	@Autowired
	PlanetsService planetsService;

	@Autowired
	private SwapiService swapiService;

	// Fetches planet by id
	@GetMapping(value = "planet/{id}")
	public Planets getPlanetById(@PathVariable("id") long id) throws RestClientException {
		return this.planetsService.findById(id);
	}

	// Fetches planet by name
	@GetMapping(value = "planets/search")
	public Planets findByName(@RequestParam(value = "name", required = true) String name) throws RestClientException {
		return this.planetsService.findByNameIgnoreCase(name);
	}

	// Fetches all planets
	@GetMapping(value = "planets")
	public List<Planets> getAllPlanets() throws RestClientException {
		return this.planetsService.list();
	}

	// Creates a new planet
	@PostMapping(value = "planet")
	public ResponseEntity<Planets> addPlanet(@RequestBody Planets planetToAdd) throws RestClientException {
		Planets newPlanet = planetsService.add(planetToAdd);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().replacePath("/planets/{id}")
				.buildAndExpand(newPlanet.getPlanetId()).toUri();

		return ResponseEntity.created(location).body(newPlanet);
	}

	// Deletes planet by id
	@DeleteMapping(value = "planet/{id}")
	public void delete(@PathVariable("id") long id) throws RestClientException {
		this.planetsService.delete(id);
	}

	// Fetches all swapi planets
	@GetMapping("planets/swapi/{id}")
	public SwapiPlanet getSWAPIById(@PathVariable("id") String id) throws RestClientException {
		return this.swapiService.getSwapiPlanetById(id);
	}

}
