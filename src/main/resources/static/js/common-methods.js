
    function showPlayerDescription(currentplayer) {
    	if(currentplayer == 'P1'){
    		return 'Player 1';
    	}else{
    		return 'Player 2';
    	}
    }
    
    function showGameStateDescription(gameState) {
    	if(gameState == 'P1_WINS'){
    		return 'Player 1 wins the game!';
    	}else if(gameState == 'P2_WINS'){
    		return 'Player 2 wins the game!';
    	}else{
    		return "Boring! It's a draw";
    	}
    }



