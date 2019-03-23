package com.adpguima.starwars.model;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author André Guimarães <andrepaivaguimaraes@gmail.com>
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SwapiPlanet {

	private String name;
	private String[] films;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[] getFilms() {
		return films;
	}

	public void setFilms(String[] films) {
		this.films = films;
	}

	@Override
	public String toString() {
		return "SwapiPlanet [name=" + name + ", films=" + Arrays.toString(films) + "]";
	}

}
