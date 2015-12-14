package com.mcorvo.bol.domain;

/**
 * Defines Players P1 & P2 and their descriptions
 * 
 * @author Miguel.Diaz
 *
 */
public enum Player {
	P1("Player 1"), P2("Player 2");
	
	private final String playerDescription;

	/**
	 * Creates the player and its description.
	 * @param playerDescription
	 */
	private Player(String playerDescription) {
		this.playerDescription = playerDescription;
    }

	/**
	 * Gets the description.
	 * @return description
	 */
    public String getPlayerDescription() {
        return playerDescription;
    }
    
    /**
     * Gets the opposite player
     * @return rival
     */
	public Player getRival() {
		return this == P1 ? P2 : P1;
	}
	
}
