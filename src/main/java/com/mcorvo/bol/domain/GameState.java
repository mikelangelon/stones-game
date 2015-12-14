package com.mcorvo.bol.domain;

/**
 * Enum with all the possible game states.
 * 
 * TODO: Since GameLogic has a variable for the winnerPlayer Player, consider deliting P1_WINS & P2_WINS and having just 
 * only one(like VICTORY). If the GameState is VICTORY, the winner would be in the variable winnerPlayer of GameLogic Class.
 * 
 * @author Miguel.Diaz
 *
 */
public enum GameState {
	NOT_STARTED,
	ON_GAME,
	P1_WINS,
	P2_WINS,
	DRAW
}
