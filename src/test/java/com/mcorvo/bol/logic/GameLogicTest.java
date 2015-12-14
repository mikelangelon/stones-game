package com.mcorvo.bol.logic;

import static org.junit.Assert.assertArrayEquals;
import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.mcorvo.bol.domain.GameState;
import com.mcorvo.bol.domain.Player;
import com.mcorvo.bol.exceptions.InvalidMoveException;
import com.mcorvo.bol.logic.GameLogic;

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
		setBoards(new Integer[]{2,6,0,7,10,6,10},new Integer[]{1,2,3,4,5,6,7});
		gl.move(0);
		//goes to an empty place, steals 4 stones from P2!
		assertArrayEquals(new Integer[]{0,7,0,7,10,6,15}, gl.getNumericalboardP1().toArray(new Integer[] {}));
		assertArrayEquals(new Integer[]{1,2,3,0,5,6,7}, gl.getNumericalboardP2().toArray(new Integer[] {}));
	}

	@Test
	public void testGameFinishedDRAW() {
		for(int i=0;i<5;i++){
			gl.getBoard().getPit(i).setAmountStones(0);
		}
		gl.getBoard().getPit(5).setAmountStones(2);
		gl.getBoard().getPit(6).setAmountStones(6*6);
		gl.move(5);
		
		assertEquals(GameState.DRAW,gl.getGameState());
	}
	
	@Test
	public void testGameFinishedP1WINS() {
		for(int i=0;i<5;i++){
			gl.getBoard().getPit(i).setAmountStones(0);
		}
		gl.getBoard().getPit(5).setAmountStones(2);
		gl.getBoard().getPit(6).setAmountStones(32);
		gl.move(5);
		assertTrue(gl.getGameState().equals(GameState.P2_WINS));
		assertTrue(gl.getWinnerPlayer().equals(Player.P2));
	}
	
	@Test
	public void testGameFinishedP2WINS() {
		for(int i=0;i<5;i++){
			gl.getBoard().getPit(i).setAmountStones(0);
		}
		gl.getBoard().getPit(5).setAmountStones(2);
		gl.getBoard().getPit(6).setAmountStones(32);
		gl.move(5);
		assertTrue(gl.getGameState().equals(GameState.P2_WINS));
	}
	
	private void setBoards(Integer[] stonesP1, Integer[] stonesP2){
		for(int i=0;i<7;i++){
			gl.getBoard().getPit(i).setAmountStones(stonesP1[i]);
		}
		for(int i=0;i<7;i++){
			gl.getBoard().getPit(7+i).setAmountStones(stonesP2[i]);
		}
	}
	
	
	
	
	

}
