package com.mcorvo.bol.domain;

/**
 * Simple class just to differentiate Pits from the LargePits.
 * @author Miguel.Diaz
 *
 */
public class LargePit extends Pit{

	private static final long serialVersionUID = -2674312262259047246L;

	/**
	 * Constructor of the LargePit
	 * 
	 * @param owner
	 * @param position
	 * @param amountStones
	 */
	public LargePit(Player owner, int position, int amountStones) {
		super(owner, position, amountStones);
	}

}
