package com.adpguima.starwars.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adpguima.starwars.model.Planets;

@Repository
public interface PlanetsRepository extends JpaRepository<Planets, String> {

	List<Planets> findByNameIgnoreCase(String name);

	List<Planets> findByName(String name);

}