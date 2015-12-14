package com.mcorvo.bol.legacy;

import java.util.Scanner;

import com.mcorvo.bol.domain.Board;
import com.mcorvo.bol.domain.GameState;
import com.mcorvo.bol.logic.GameLogic;

/**
 * Specific Visual CMD
 * @author Miguel.Diaz
 *
 */
public class ComandLineGame {

//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		ComandLineGame gc = new ComandLineGame();
//		gc.initCMDGame();
//		// create a scanner so we can read the command-line input
//
//	}
	
	public void initCMDGame(){
		GameLogic gc = new GameLogic();
		gc.newGame();
	    @SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
	    do{
			System.out.println(getPrintedBoard(gc.getBoard()));
			System.out.println("Play your move " + gc.getCurrentPlayer().getPlayerDescription());
			// get the age as an int
		    int position = scanner.nextInt();
		    gc.move(position);
		    if(position==15)
		    	break;
	    }while(gc.getGameState().equals(GameState.ON_GAME));
	}
	
	private String getPrintedBoard(Board board){
		
		
		String innerText = getInnerBoardText(board);
		String text = "\t\t";
		text+="Player 1\n"+innerText+"\t\tPlayer 2\n";
		return text;
	}

	private String getInnerBoardText(Board board) {
		String text ="";
		String player1line = "\t\t|";
		for(int i=5;i>=0;i--){
			player1line+=" "+board.getPit(i).getAmountStones()+" |";
		}
		text += player1line + "\n";
		text +="P1 LargePit("+board.getPit(6).getAmountStones()+")  |-----------------------| P2 LargePit("+board.getPit(13).getAmountStones()+")\n";
		String player2line = "\t\t|";
		for(int i=7;i<13;i++){
			player2line+=" "+board.getPit(i).getAmountStones()+" |";
		}
		text +=player2line+"\n";
		return text;
	}

}
