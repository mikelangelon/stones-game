package com.mcorvo.bol.domain;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.mcorvo.bol.exceptions.InvalidMoveException;
import com.mcorvo.bol.logic.GameLogic;

/**
 * The board for the game.
 * 
 * The pits position will be like:
 * 
 * Player1 Pits:
 * Pits -->0, 1,2,3,4,5
 * LargePit -->6
 * 
 * Player 2 Pits:
 * Pits --> 7,8,9,10,11,12
 * LargePit -->13
 * 
 * @author Miguel.Diaz
 *
 */
public class Board {
	final static Logger logger = Logger.getLogger(Board.class);
	
	private static final int SMALL_PITS_PER_PLAYER = 6;
	public static final int INIT_STONES_PER_PIT = 6;
	public static final int DEFAULT_BOARD_SIZE = 2 * (SMALL_PITS_PER_PLAYER+1);
	
	List<Pit> listPits = new ArrayList<Pit>();
	private final int endPositionPlayer1=SMALL_PITS_PER_PLAYER+1;
	private final int endPositionPlayer2=endPositionPlayer1+SMALL_PITS_PER_PLAYER+1;
	
	
	/**
	 * Cleans the listPits and creates new ones with the initial values
	 */
	public void initBoard(){
		listPits.clear();
		createPitsForPlayer(Player.P1);
		createPitsForPlayer(Player.P2);
	}

	
	/**
	 * Takes the stones from a specific Pit and reset it.
	 * 
	 * @param position
	 * @return the taken stones
	 */
	public int takeStones(int position){
		int stones = getPit(position).getAmountStones();
		getPit(position).setAmountStones(0);
		
		return stones;
	}
	
	
	/**
	 * Gets a specific Pit
	 * @param position
	 * @return Pit
	 */
	public Pit getPit(int position){
		return listPits.get(position%14);
	}
	
	
	/**
	 * Get the big Pit from a specific Player
	 * @param player
	 * @return LargePit
	 */
	public LargePit getLargePit(Player player){
		int largePitPosition;
		if(player.equals(Player.P1)){
			largePitPosition=endPositionPlayer1-1;
		}else{
			largePitPosition=endPositionPlayer2-1;
		}
		return (LargePit)getPit(largePitPosition);
	}
	
	
	/**
	 * Just check if the player has stones.
	 * This method could be done much eficiency, if instead of reusing method sumPits was checking if a pit is >0.
	 * 
	 * @param player
	 * @return true if the player doesn't have more stones
	 */
	public boolean isPlayerWithoutStones(Player player) {	
		return (sumPits(player)>0)?false:true;
	}
	
	
	/**
	 * Sums the stones from all the pits(Pits & LargePit) from an specific Player
	 * @param player
	 * @return number of total stones
	 */
	public int sumPitsAndLargePit(Player player) {	
		return sumPits(player)+getLargePit(player).getAmountStones();
	}
	
	
	/**
	 * Sums all the stones from the small pits of a player
	 * @param player
	 * @return stones from a Player
	 */
	private int sumPits(Player player) {	
		int from = player == Player.P1 ? 0 : endPositionPlayer1;
		int to = player == Player.P1 ? endPositionPlayer1-1 : endPositionPlayer2-1;
		int totalStones = 0;
		for (int i = from; i < to; i++) {
			totalStones+=getPit(i).getAmountStones();
		}
		return totalStones;
	}
	
	/**
	 * Returns the Pits as a List of Integers
	 * TODO: Maybe move it in an Utils class.
	 * @return List of all the stones
	 */
	public List<Integer> getListNumericalPits() {
		List<Integer> result = new ArrayList<Integer>();
		for (Pit pit : listPits)
			result.add(pit.getAmountStones());
		return result;
	}
	
	/**
	 * Creates the initial Pits for the player
	 * @param player
	 */
	private void createPitsForPlayer(Player player) {
		for(int i=0;i<SMALL_PITS_PER_PLAYER;i++){
			listPits.add(new Pit(player, listPits.size()+1, INIT_STONES_PER_PIT));
		}
		listPits.add(new LargePit(player, listPits.size()+1, 0));
	}
	
	/**
	 * Validates if the move is correct, checking the position and if the Pit if from the given player.
	 * 
	 * @param player
	 * @param position
	 */
	public void validateMove(Player player, int position) {
		if ((player.equals(Player.P1) && !(position >= 0 && position < endPositionPlayer1-1)) || (player.equals(Player.P2) && !(position >= endPositionPlayer1 && position < endPositionPlayer2-1))) {
			logger.error("Invalid move: " + player.getPlayerDescription() + " tried to move to the position " + position);
			throw new InvalidMoveException("The pit from position " + position + " is not from " + player.getPlayerDescription());
		}
	}


	@Override
	public String toString() {
		String text = "[ ";
		for(Pit pit:listPits){
			text +=pit.getAmountStones()+",";
		}
		return text.substring(0, text.length()-2)+"]";
	}
	
	
}
