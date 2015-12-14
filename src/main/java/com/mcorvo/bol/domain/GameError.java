package com.mcorvo.bol.domain;

/**
 * Object useful for the Controller to send the error message back to the view.
 * 
 * @author Miguel.Diaz
 *
 */
public class GameError {
	
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
