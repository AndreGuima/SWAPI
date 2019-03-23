package com.adpguima.starwars.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.annotation.Transient;

@Entity(name = "Planets")
public class Planets {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long planetId;

	@Column(name = "name", nullable = false)
	private String name;
	private String climate;
	private String terrain;

	@Transient
	private Integer filmsCount;

	public Planets() {
	}

	public long getPlanetId() {
		return planetId;
	}

	public void setPlanetId(long planetId) {
		this.planetId = planetId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClimate() {
		return climate;
	}

	public void setClimate(String climate) {
		this.climate = climate;
	}

	public String getTerrain() {
		return terrain;
	}

	public void setTerrain(String terrain) {
		this.terrain = terrain;
	}

	public Integer getFilmsCount() {
		return filmsCount;
	}

	public void setFilmsCount(Integer filmsCount) {
		this.filmsCount = filmsCount;
	}

	@Override
	public String toString() {
		return "Planets [planetId=" + planetId + ", name=" + name + ", climate=" + climate + ", terrain=" + terrain
				+ ", filmsCount=" + filmsCount + "]";
	}

}
