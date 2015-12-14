package com.mcorvo.bol.logic;

import static org.junit.Assert.assertArrayEquals;
import junit.framework.TestCase;

import org.junit.Test;

import com.mcorvo.bol.domain.GameState;
import com.mcorvo.bol.domain.Player;

public class GameLogicFullScenarioTest extends TestCase {
	
	@Test
	public void testCompleteScenario() {
		GameLogic gl = new GameLogic();
		gl.newGame();
		gl.move(1);
		gl.move(7 + 1);
		checkingArray(gl, new Integer[] { 7, 0, 7, 7, 7, 7, 1 }, new Integer[] { 7, 0, 7, 7, 7, 7, 1 });

		gl.move(3);
		gl.move(7 + 4);
		checkingArray(gl, new Integer[] { 8, 1, 8, 1, 9, 8, 2 }, new Integer[] { 8, 1, 8, 8, 0, 8, 2 });

		gl.move(0);
		gl.move(7 + 1);
		checkingArray(gl, new Integer[] { 0, 2, 9, 2, 10, 9, 3 }, new Integer[] { 9, 0, 9, 9, 0, 8, 2 });

		gl.move(2);
		checkingArray(gl, new Integer[] { 0, 2, 0, 3, 11, 10, 4 }, new Integer[] { 10, 1, 10, 10, 1, 8, 2 });
		gl.move(7 + 0);
		checkingArray(gl, new Integer[] { 1, 3, 1, 4, 11, 10, 4 }, new Integer[] { 0, 2, 11, 11, 2, 9, 3 });

		gl.move(1);
		checkingArray(gl, new Integer[] { 1, 0, 2, 5, 12, 10, 4 }, new Integer[] { 0, 2, 11, 11, 2, 9, 3 });
		gl.move(7 + 3);
		checkingArray(gl, new Integer[] { 2, 1, 3, 6, 13, 11, 4 }, new Integer[] { 1, 3, 11, 0, 3, 10, 4 });

		gl.move(4);
		// Complex move.
		// Moving 13 stones--> every pit gets +1, except LargePit from Rival.
		// But the last stone goes to one of his empty pit(the current one(4))
		// Keeps this last one + steals from the opposite, and all these stones
		// go to his LargePit
		checkingArray(gl, new Integer[] { 3, 2, 4, 7, 0, 12, 10 }, new Integer[] { 2, 0, 12, 1, 4, 11, 4 });
		gl.move(7 + 3);
		checkingArray(gl, new Integer[] { 3, 2, 4, 7, 0, 12, 10 }, new Integer[] { 2, 0, 12, 0, 5, 11, 4 });

		gl.move(2);// Last stone goes to its LargePit --> P1 Moves again!
		checkingArray(gl, new Integer[] { 3, 2, 0, 8, 1, 13, 11 }, new Integer[] { 2, 0, 12, 0, 5, 11, 4 });
		assertTrue(gl.getCurrentPlayer().equals(Player.P1));
		gl.move(3);
		checkingArray(gl, new Integer[] { 3, 2, 0, 0, 2, 14, 12 }, new Integer[] { 3, 1, 13, 1, 6, 11, 4 });
		gl.move(7 + 3);
		checkingArray(gl, new Integer[] { 3, 2, 0, 0, 2, 14, 12 }, new Integer[] { 3, 1, 13, 0, 7, 11, 4 });

		gl.move(0);// Clever move from P1. Move to empty pit, stealing the
					// biggest pit from its opponent!
		checkingArray(gl, new Integer[] { 0, 3, 1, 0, 2, 14, 26 }, new Integer[] { 3, 1, 0, 0, 7, 11, 4 });
		gl.move(7 + 5);// P2 move a last stone to empty pit. Steals 2 stones
						// from P1!
		checkingArray(gl, new Integer[] { 1, 4, 0, 1, 3, 15, 26 }, new Integer[] { 4, 2, 1, 0, 7, 0, 8 });

		gl.move(5);
		checkingArray(gl, new Integer[] { 2, 5, 1, 2, 4, 1, 28 }, new Integer[] { 6, 3, 2, 1, 8, 1, 8 });
		gl.move(7 + 0);// Nice P2 move! Plays again!
		checkingArray(gl, new Integer[] { 2, 5, 1, 2, 4, 1, 28 }, new Integer[] { 0, 4, 3, 2, 9, 2, 9 });
		assertTrue(gl.getCurrentPlayer().equals(Player.P2));
		gl.move(7 + 5);
		checkingArray(gl, new Integer[] { 3, 5, 1, 2, 4, 1, 28 }, new Integer[] { 0, 4, 3, 2, 9, 0, 10 });

		gl.move(1);// P1 moves again!
		checkingArray(gl, new Integer[] { 3, 0, 2, 3, 5, 2, 29 }, new Integer[] { 0, 4, 3, 2, 9, 0, 10 });
		assertTrue(gl.getCurrentPlayer().equals(Player.P1));
		gl.move(3);// And P1 manages to play again! Combo!
		checkingArray(gl, new Integer[] { 3, 0, 2, 0, 6, 3, 30 }, new Integer[] { 0, 4, 3, 2, 9, 0, 10 });
		assertTrue(gl.getCurrentPlayer().equals(Player.P1));
		gl.move(0);// Last stone to empty pit --> steals!
		checkingArray(gl, new Integer[] { 0, 1, 3, 0, 6, 3, 34 }, new Integer[] { 0, 4, 0, 2, 9, 0, 10 });
		gl.move(7 + 3);// P2 steals but... there are no stones in that P1 pit!
		checkingArray(gl, new Integer[] { 0, 1, 3, 0, 6, 3, 34 }, new Integer[] { 0, 4, 0, 0, 10, 0, 11 });

		gl.move(2);
		checkingArray(gl, new Integer[] { 0, 1, 0, 1, 7, 4, 34 }, new Integer[] { 0, 4, 0, 0, 10, 0, 11 });
		gl.move(7 + 4);
		checkingArray(gl, new Integer[] { 1, 2, 1, 2, 8, 5, 34 }, new Integer[] { 1, 5, 0, 0, 0, 1, 12 });

		gl.move(3);
		checkingArray(gl, new Integer[] { 1, 2, 1, 0, 9, 6, 34 }, new Integer[] { 1, 5, 0, 0, 0, 1, 12 });
		gl.move(7 + 1);// P2 moves again!
		checkingArray(gl, new Integer[] { 1, 2, 1, 0, 9, 6, 34 }, new Integer[] { 1, 0, 1, 1, 1, 2, 13 });
		assertTrue(gl.getCurrentPlayer().equals(Player.P2));
		gl.move(7 + 0);// P2 steals 9 stones from P1
		checkingArray(gl, new Integer[] { 1, 2, 1, 0, 0, 6, 34 }, new Integer[] { 0, 0, 1, 1, 1, 2, 23 });

		gl.move(1);// P1 steals 1 stone from P2
		checkingArray(gl, new Integer[] { 1, 0, 2, 0, 0, 6, 36 }, new Integer[] { 0, 0, 0, 1, 1, 2, 23 });
		gl.move(7 + 4);
		checkingArray(gl, new Integer[] { 1, 0, 2, 0, 0, 6, 36 }, new Integer[] { 0, 0, 0, 1, 0, 3, 23 });

		gl.move(5);// Finishing to an empty pit from the rival... no stealing!
		checkingArray(gl, new Integer[] { 1, 0, 2, 0, 0, 0, 37 }, new Integer[] { 1, 1, 1, 2, 1, 3, 23 });
		gl.move(7 + 3);
		checkingArray(gl, new Integer[] { 1, 0, 2, 0, 0, 0, 37 }, new Integer[] { 1, 1, 1, 0, 2, 4, 23 });

		gl.move(2);// P1 steals 1 stone
		checkingArray(gl, new Integer[] { 1, 0, 0, 1, 0, 0, 39 }, new Integer[] { 1, 0, 1, 0, 2, 4, 23 });
		gl.move(7 + 4);// P2 moves again
		checkingArray(gl, new Integer[] { 1, 0, 0, 1, 0, 0, 39 }, new Integer[] { 1, 0, 1, 0, 0, 5, 24 });
		gl.move(7 + 0);// P2 steals 0 stones
		checkingArray(gl, new Integer[] { 1, 0, 0, 1, 0, 0, 39 }, new Integer[] { 0, 0, 1, 0, 0, 5, 25 });

		gl.move(0);// P1 steals 0 stones
		checkingArray(gl, new Integer[] { 0, 0, 0, 1, 0, 0, 40 }, new Integer[] { 0, 0, 1, 0, 0, 5, 25 });

		gl.move(7 + 2);// P2 steals 0 stones
		checkingArray(gl, new Integer[] { 0, 0, 0, 1, 0, 0, 40 }, new Integer[] { 0, 0, 0, 0, 0, 5, 26 });

		gl.move(3);// P1 finish and wins the game!(Also before steals... but he
					// get 0 stones.
		checkingArray(gl, new Integer[] { 0, 0, 0, 0, 0, 0, 41 }, new Integer[] { 0, 0, 0, 0, 0, 5, 26 });
		assertEquals(GameState.P1_WINS, gl.getGameState());

		int scoreP1 = gl.getBoard().sumPitsAndLargePit(Player.P1);
		int scoreP2 = gl.getBoard().sumPitsAndLargePit(Player.P2);
		assertEquals(41, scoreP1);
		assertEquals(26 + 5, scoreP2);

	}

	private void checkingArray(GameLogic gl, Integer[] expectedPitP1, Integer[] expectedPitP2) {
		assertArrayEquals(expectedPitP1, gl.getNumericalboardP1().toArray(new Integer[] {}));
		assertArrayEquals(expectedPitP2, gl.getNumericalboardP2().toArray(new Integer[] {}));
	}
}
