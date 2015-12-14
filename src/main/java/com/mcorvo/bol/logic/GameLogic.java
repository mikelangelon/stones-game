package com.mcorvo.bol.logic;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.mcorvo.bol.domain.Board;
import com.mcorvo.bol.domain.GameState;
import com.mcorvo.bol.domain.Pit;
import com.mcorvo.bol.domain.Player;
import com.mcorvo.bol.exceptions.InvalidMoveException;

/**
 * Main class of the game. Its more important method is 'move', where the player could move the stones of a pit to the next ones.
 *   
 * After every move, the game checks if it's already finished. If not,   
 * @author Miguel.Diaz
 *
 */
@Component
public class GameLogic {

	final static Logger logger = Logger.getLogger(GameLogic.class);

	GameState gameState = GameState.NOT_STARTED;
	Board board = new Board();
	Player currentPlayer;
	Player winnerPlayer;

	/**
	 * Prepares a new Game, initializing variables and the Board
	 */
	public void newGame() {
		logger.info("Starting a new game");
		
		board.initBoard();
		currentPlayer = Player.P1;
		winnerPlayer = null;
		gameState = GameState.ON_GAME;
	}

	/**
	 * The currentPlayer moves a Pit determined from its position as parameter.
	 * First, validates if the move is correct(The chosen Pit is from the user)
	 * @param position
	 */
	public void move(int position) {

		//First step: Validate if it's a valid move
		board.validateMove(currentPlayer, position);
		
		// Takes the stones from the specific position
		int stones = board.takeStones(position);
		logger.debug("taking " + stones + " from " + position);

		//If there are no stones in the chosen pit... the move is not valid
		if (stones == 0) {
			//TODO: Consider moving this condition inside validateMove or create another class for validations.
			logger.error("Invalid move: " +currentPlayer.getPlayerDescription() + " tried to take stones from the pit " + position + " but doesn't have stones");
			throw new InvalidMoveException("No more stones in this pit");
		}
		
		// put taken stones in the next pits
		Pit currentPit = null;
		for (int i = stones; i > 0;) {
			currentPit = board.getPit(++position);
			if (currentPit.couldIuseThisPit(currentPlayer)) {
				//If it's the last stone and the next pit is empty and from the player, it's stealing time!
				//If not, adds a stone
				if(i==1 && currentPit.getAmountStones() == 0 && currentPit.getOwner().equals(currentPlayer)){
					stealStones(currentPlayer, position);
				}else{
					currentPit.setAmountStones(currentPit.getAmountStones() + 1);
				}
				i--;
			}

		}
		logger.debug(board.toString());
		//If the game is still alive, check if the pits of currentPlayer have stones, and change turn
		if (gameState.equals(GameState.ON_GAME) && !isGameFinished(currentPlayer)) {
			// If it's the Large Pit of the player, stills plays. If not, plays the other player.
			if (!currentPit.isMyLargePit(currentPlayer)) {
				currentPlayer = currentPlayer.getRival();
			}
		}
	}

	/**
	 * 
	 * @param player
	 * @param position
	 */
	private void stealStones(Player player, int position) {
		int stealedStones = board.takeStones(6 + (6 - position%14));
		board.getLargePit(player).addStones(1 + stealedStones);
		logger.debug(player.getPlayerDescription() + " steals " + stealedStones + " stones from " + player.getRival().getPlayerDescription());
		
		//Checks if the game is already finished due to this stealing
		isGameFinished(currentPlayer.getRival());

	}

	


	/**
	 * Check if the player has already stones. 
	 * If doesn't have, the game is finish and calculates the total score of each player do determine the winner.
	 * 
	 * @param player
	 * @return true if the game is finished.
	 */
	private boolean isGameFinished(Player player) {
		if (getBoard().isPlayerWithoutStones(player)) {
			// calculate real winner
			int stonesFromPlayer = getBoard().getLargePit(player).getAmountStones();
			int stonesFromRival = getBoard().sumPitsAndLargePit(player.getRival());
			
			if(stonesFromPlayer>stonesFromRival){
				winnerPlayer=player;
			}else if(stonesFromPlayer<stonesFromRival){
				winnerPlayer=player.getRival();
			}else{
				//If they have the same score, it's a DRAW. Updates gameState and finishes the game.
				gameState = GameState.DRAW;
				logger.debug("Game finishes as DRAW!");
				return true;
			}
			gameState = (winnerPlayer == Player.P1) ? GameState.P1_WINS : GameState.P2_WINS;
			logger.debug("Game finishes! The winner is " + winnerPlayer.getPlayerDescription());
			return true;
		}
		return false;
	}



	
	/**
	 * Gets the current player
	 * @return currentPlayer
	 */
	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	
	/**
	 * Gets the board
	 * @return Board
	 */
	public Board getBoard() {
		return board;
	}

	/**
	 * Gets the GameState
	 * @return
	 */
	public GameState getGameState() {
		return gameState;
	}
	
	
	/**
	 * Gets the winner player
	 * @return winnerPlayer
	 */
	public Player getWinnerPlayer() {
		return winnerPlayer;
	}
	
	
	/**
	 * Gets the specific pits from the Player P1. Useful for the view.
	 * 
	 * TODO: Maybe move this method to the view
	 * @return List of Stones from P1
	 */
	public List<Integer> getNumericalboardP1() {
		return getBoard().getListNumericalPits().subList(0, 7);
	}

	
	/**
	 * Gets the specific pits from the Player P2. Useful for the view.
	 * 
	 * TODO: Maybe move this method to the view
	 * @return List of Stones from P2
	 */
	public List<Integer> getNumericalboardP2() {
		return getBoard().getListNumericalPits().subList(7, 14);
	}
}
