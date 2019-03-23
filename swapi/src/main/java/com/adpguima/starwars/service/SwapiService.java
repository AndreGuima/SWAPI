package com.adpguima.starwars.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.adpguima.starwars.model.SwapiPlanet;
import com.adpguima.starwars.model.SwapiSearch;

@Component
public class SwapiService {

	@Autowired
	private RestTemplate restTemplate;

	@Cacheable("getSwapiPlanetByName")
	public SwapiPlanet getSwapiPlanetByName(String name) throws RestClientException {
		String swapiUrl = "https://swapi.co/api/planets?search=" + name;

		SwapiSearch swapiSearch = restTemplate.getForObject(swapiUrl, SwapiSearch.class);

		if (swapiSearch.getCount() == 0) {
			return null;
		}

		for (SwapiPlanet swapiPlanet : swapiSearch.getResults()) {
			if (swapiPlanet.getName().equals(name)) {
				return swapiPlanet;
			}
		}

		return null;

	}

	@Cacheable("getSwapiPlanetById")
	public SwapiPlanet getSwapiPlanetById(String id) throws RestClientException {
		String endPointswapi = "https://swapi.co/api/planets/" + id;

		SwapiPlanet apiPlanet = restTemplate.getForObject(endPointswapi, SwapiPlanet.class);

		return apiPlanet;
	}

}