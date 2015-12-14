package com.mcorvo.bol.logic;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import com.mcorvo.bol.logic.GameLogic;

@RunWith(value = BlockJUnit4ClassRunner.class)
public class GameLogicExceptions extends TestCase{
	@Test(expected = Exception.class)
    public void testNoMoreStones() throws Exception{
        GameLogic gl = new GameLogic();
        gl.newGame();
        gl.move(5);
        gl.move(10);
        gl.move(5);
    }
	
	@Test(expected = Exception.class)
    public void testNotValidMoveForP1() throws Exception{
        GameLogic gl = new GameLogic();
        gl.newGame();
        gl.move(10);
    }
	
	@Test(expected = Exception.class)
    public void testNotValidMoveForP2() throws Exception{
        GameLogic gl = new GameLogic();
        gl.newGame();
        gl.move(1);
        gl.move(1);
    }
}
