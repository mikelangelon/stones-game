package com.mcorvo.bol.logic;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import com.mcorvo.bol.domain.GameState;
import com.mcorvo.bol.domain.Player;
import com.mcorvo.bol.utilstesting.Utils;

public class GameLogicTest extends TestCase {
	
	GameLogic gl;
	
	@Before
    public void setUp() {
		gl = new GameLogic();
		gl.newGame();

    }

	@Test
	public void testMove() {
		gl.move(1);
	}

	@Test
	public void testMoveScenarioPlayerOneMovesAgain() {
		gl.move(0);
		assertTrue(gl.getCurrentPlayer().equals(Player.P1));
		assertTrue(gl.getBoard().getPit(0).getAmountStones() == 0);
		assertTrue(gl.getBoard().getLargePit(Player.P1).getAmountStones() == 1);
	}



	@Test
	public void testStealStones() {
		Utils.setBoards(gl.getBoard(), new Integer[]{2,6,0,7,10,6,10},new Integer[]{1,2,3,4,5,6,7});
		gl.move(0);
		//goes to an empty place, steals 4 stones from P2!
		assertArrayEquals(new Integer[]{0,7,0,7,10,6,15}, gl.getNumericalboardP1().toArray(new Integer[] {}));
		assertArrayEquals(new Integer[]{1,2,3,0,5,6,7}, gl.getNumericalboardP2().toArray(new Integer[] {}));
	}

	@Test
	public void testGameFinishedDRAW() {
		Utils.setBoards(gl.getBoard(), new Integer[]{0,0,0,0,0,2,35},new Integer[]{0,2,0,1,0,2,30});
		gl.move(5);
		
		assertEquals(GameState.DRAW,gl.getGameState());
	}
	
	@Test
	public void testGameFinishedP1WINS() {
		Utils.setBoards(gl.getBoard(), new Integer[]{0,0,0,1,0,0,42},new Integer[]{0,0,0,0,0,2,27});
		gl.move(3);
		
		assertTrue(gl.getGameState().equals(GameState.P1_WINS));
		assertTrue(gl.getWinnerPlayer().equals(Player.P1));
	}
	
	@Test
	public void testGameFinishedP2WINS() {
		Utils.setBoards(gl.getBoard(), new Integer[]{0,2,0,1,0,0,30},new Integer[]{0,0,0,0,0,2,37});
		gl.move(3);
		gl.move(7+5);
		assertTrue(gl.getGameState().equals(GameState.P2_WINS));
		assertTrue(gl.getWinnerPlayer().equals(Player.P2));
	}
	
	@Test
	public void testGameFinishedP1StealsANDWins() {
		Utils.setBoards(gl.getBoard(), new Integer[]{0,1,0,0,0,1,20},new Integer[]{0,0,0,15,0,2,33});
		gl.move(5);//Now P1 moves again
		gl.move(1);//Steals 15 stones!
		
		assertTrue(gl.getGameState().equals(GameState.P1_WINS));
		assertTrue(gl.getWinnerPlayer().equals(Player.P1));
	}
	
	
	@Test
	public void testGameFinishedP1NoStonesFirstButP2WINS() {
		Utils.setBoards(gl.getBoard(), new Integer[]{0,1,0,0,0,0,22},new Integer[]{0,0,15,0,0,2,33});
		gl.move(1);
		assertTrue(gl.getGameState().equals(GameState.P2_WINS));
	}
	
	
	
	
	
	
	

}
