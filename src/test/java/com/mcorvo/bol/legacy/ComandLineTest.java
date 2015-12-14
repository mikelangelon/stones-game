package com.mcorvo.bol.legacy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;

import com.mcorvo.bol.domain.Board;
import com.mcorvo.bol.domain.LargePit;
import com.mcorvo.bol.legacy.ComandLineGame;

public class ComandLineTest {
	
	private static final String TABS = "\t\t";
	
	@Test
    public void testPrintBoard() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        ComandLineGame gl = new ComandLineGame();
        Class[] cArg = new Class[1];
        cArg[0] = Board.class;
        Method method = gl.getClass().getDeclaredMethod("getPrintedBoard", cArg);
        method.setAccessible(true);
        
        Board board = new Board();
        board.initBoard();
        String text = (String)method.invoke(gl, board);

        String expectedText =format("Player 1")+
        					format("| 6 | 6 | 6 | 6 | 6 | 6 |")+
        					"P1 LargePit(0)  |-----------------------| P2 LargePit(0)\n"+
        					format("| 6 | 6 | 6 | 6 | 6 | 6 |")+
        					format("Player 2");
        String[] textToCompareParts = text.split("\n");
        String[] expectedTextParts = expectedText.split("\n");
        
        for(int i=0;i<textToCompareParts.length;i++){
        	 assertEquals(expectedTextParts[i],textToCompareParts[i]);		
        }
        				
    }
	
	private String format(String text){
		return TABS+text+"\n";
	}
	
}
