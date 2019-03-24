package com.adpguima.starwars.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.adpguima.starwars.exception.PlanetNotFoundException;
import com.adpguima.starwars.model.SwapiPlanet;
import com.adpguima.starwars.model.SwapiSearch;

@Component
public class SwapiService {

	@Autowired
	private RestTemplate restTemplate;

	public SwapiPlanet getSwapiPlanetByName(String name) throws RestClientException, PlanetNotFoundException {
		String swapiUrl = "https://swapi.co/api/planets?search=" + name;

		SwapiSearch swapiSearch = restTemplate.getForObject(swapiUrl, SwapiSearch.class);

		if (swapiSearch.getCount() == 0) {
			throw new PlanetNotFoundException("No such planet on the star wars api, check the name you've input");
		}

		for (SwapiPlanet swapiPlanet : swapiSearch.getResults()) {
			if (swapiPlanet.getName().equals(name)) {
				return swapiPlanet;
			}
		}

		throw new PlanetNotFoundException("No such planet on the star wars api, check the name you've input");
	}

	public SwapiPlanet getSwapiPlanetById(String id) throws RestClientException, PlanetNotFoundException {
		String endPointswapi = "https://swapi.co/api/planets/" + id;

		SwapiPlanet apiPlanet = restTemplate.getForObject(endPointswapi, SwapiPlanet.class);

		if (apiPlanet == null) {
			throw new PlanetNotFoundException("No such planet on the star wars api, check the name you've input");
		}

		return apiPlanet;
	}

}