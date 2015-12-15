package com.mcorvo.bol.utilstesting;

import com.mcorvo.bol.domain.Board;

public class Utils {
	public static void setBoards(Board board, Integer[] stonesP1, Integer[] stonesP2){
		for(int i=0;i<7;i++){
			board.getPit(i).setAmountStones(stonesP1[i]);
		}
		for(int i=0;i<7;i++){
			board.getPit(7+i).setAmountStones(stonesP2[i]);
		}
	}
}
