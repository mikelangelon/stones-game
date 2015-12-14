package com.mcorvo.bol.domain;

/**
 * Pit from the game. Every Pit will keep its position, the quantity of stones that has currently and the owner.
 * 
 * TODO: It's already possible to know the owner from the position. Consider keeping just one.
 * @author Miguel.Diaz
 *
 */
public class Pit {
	Player owner;
	int position;
	int amountStones;
	
	/**
	 * Constructor of the Pit
	 * 
	 * @param owner
	 * @param position
	 * @param amountStones
	 */
	public Pit(Player owner, int position, int amountStones){
		this.owner=owner;
		this.position = position;
		this.amountStones=amountStones;
	}

	
	/**
	 * gets the amount of stones
	 * @return stones
	 */
	public int getAmountStones() {
		return amountStones;
	}
	
	
	/**
	 * Add stones to the Pit
	 * @param amountStones
	 */
	public void addStones(int amountStones) {
		this.amountStones += amountStones;
	}
	
	
	/**
	 * Setting an specific amount of stones
	 * 
	 * TODO: Finally is just used to reset it as 0 and for Testing...
	 * @param amountStones
	 */
	public void setAmountStones(int amountStones) {
		this.amountStones = amountStones;
	}
	
	
	/**
	 * Gets the owner
	 * @return owner
	 */
	public Player getOwner() {
		return owner;
	}

	/**
	 * Checks if the player could use this Pit
	 * 
	 * @param player
	 * @return true if it's the owner and is a small Pit
	 */
	public boolean couldIuseThisPit(Player player){
		if(this instanceof LargePit && !player.equals(owner)){
			return false;
		}
		return true;
	}
	
	/**
	 * Check if the pit is the Large Pit of the player. 
	 * Useful to take the LargePit in consideration when game moves taken stones to the next pits
	 * 
	 * @param player
	 * @return true if is the LargePit of the player
	 */
	public boolean isMyLargePit(Player player){
		if(this instanceof LargePit && player.equals(owner)){
			return true;
		}
		return false;
	}
	
	
	
}
