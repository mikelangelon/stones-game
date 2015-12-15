package com.mcorvo.bol.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertArrayEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.mcorvo.bol.logic.GameLogic;
import com.mcorvo.bol.utilstesting.Utils;

public class BoardTest {

	Board board;
	@Before
    public void setUp() {
		board = new Board();
		board.initBoard();

    }
	@Test
	public void testInit() {
		assertTrue(board.listPits.size() == Board.DEFAULT_BOARD_SIZE);
		for (Pit pit : board.listPits) {
			assertEquals("Grava hals initially should be empty", pit instanceof LargePit, pit.getAmountStones() == 0);
			assertEquals("Regular pits should contain initially default number of stones", !(pit instanceof LargePit), pit.getAmountStones() == board.INIT_STONES_PER_PIT);
		}
	}
	
	@Test
	public void testTakeStones(){
		int stones = board.takeStones(1);
		assertTrue(stones == Board.INIT_STONES_PER_PIT);
		assertTrue(board.getPit(1).getAmountStones()==0);
		
	}
	
	@Test
	public void testIsPlayerWithoutStones(){
		for(int i=0;i<board.DEFAULT_BOARD_SIZE/2;i++){
			board.getPit(i).setAmountStones(0);
		}
		boolean hasStonesP1 =  board.isPlayerWithoutStones(Player.P1);
		boolean hasStonesP2 =  board.isPlayerWithoutStones(Player.P2);
		
		assertTrue(hasStonesP1);
		assertTrue(!hasStonesP2);
	}
	
	@Test
	public void testGetListNumericalPits(){
		Integer[] array = {6,6,6,6,6,6,0,6,6,6,6,6,6,0};
		
		List<Integer> listPits = board.getListNumericalPits();
		
		assertArrayEquals(array, listPits.toArray(new Integer[]{}));
		
	}
	
	@Test
	public void testToString(){
		Utils.setBoards(board, new Integer[]{2,6,0,7,10,6,10},new Integer[]{1,2,3,4,5,6,7});
		assertEquals("[ 2,6,0,7,10,6,10,1,2,3,4,5,6,7 ]", board.toString());
	}
}
