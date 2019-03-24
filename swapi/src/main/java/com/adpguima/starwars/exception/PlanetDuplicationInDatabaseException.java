package com.adpguima.starwars.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author André Guimarães <andrepaivaguimaraes@gmail.com>
 *
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class PlanetDuplicationInDatabaseException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PlanetDuplicationInDatabaseException(String msg) {
		super(msg);
	}

}
