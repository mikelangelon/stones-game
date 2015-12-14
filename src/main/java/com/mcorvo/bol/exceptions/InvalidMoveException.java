package com.mcorvo.bol.exceptions;

/**
 * Exception to control the game.
 * Useful to indicate when the Pit is not from the user or if the Pit has already 0 stones.
 * 
 * @author Miguel.Diaz
 *
 */
public class InvalidMoveException extends RuntimeException {

	private static final long serialVersionUID = 6325999173123287717L;

	/**
	 * Creates the Exception
	 * @param errorMessage
	 */
	public InvalidMoveException(String errorMessage) {
		super(errorMessage);
	}
}
