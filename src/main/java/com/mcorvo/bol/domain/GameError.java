package com.mcorvo.bol.domain;

import java.io.Serializable;

/**
 * Object useful for the Controller to send the error message back to the view.
 * 
 * @author Miguel.Diaz
 *
 */
public class GameError implements Serializable{
	
	private static final long serialVersionUID = -4591534250852005152L;
	
	String errorMessage;

	/**
	 * Just sets the errorMessage
	 * @param errorMessage
	 */
	public GameError(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

}
