package com.adpguima.starwars.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author André Guimarães <andrepaivaguimaraes@gmail.com>
 *
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class PlanetNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PlanetNotFoundException(String message) {
		super(message);
	}

}
