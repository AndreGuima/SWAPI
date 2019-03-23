package com.adpguima.starwars.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SwapiSearch {	

	private Integer count;
	
	private SwapiPlanet[] results;
	
	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public SwapiPlanet[] getResults() {
		return results;
	}

	public void setResults(SwapiPlanet[] swPlanets) {
		this.results = swPlanets;
	}

}