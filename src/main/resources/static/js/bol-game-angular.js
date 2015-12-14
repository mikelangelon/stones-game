angular.module('bolGame', []).controller(
		'AngularJSController',
		function($http) {
			var controller = this;
			this.reset = function() {
				controller.turn = "P1";
				controller.formMessageClass = "P1";
				controller.gameState = "ON_GAME";
				this.init();
			}
			this.init = function() {
				$http.get('http://localhost:8080/init').success(
						function(result) {
							controller.pitsP1 = result.numericalboardP1.slice(0, 6).reverse();
							controller.largePitP1 = result.numericalboardP1[6];
							controller.pitsP2 = result.numericalboardP2.slice(0, 6);
							controller.largePitP2 = result.numericalboardP2[6];
							
							controller.message = showPlayerDescription(result.currentPlayer) + " turn";	

						}).error(function() {
							// handle errors....maybe in version 2.0
						});
			}
			this.move = function(position) {
				$http.get('http://localhost:8080/move/' + position).success(
						function(result) {
							if(result.errorMessage){
								controller.message = result.errorMessage;	
								controller.formMessageClass = "error";
								
							}else{
								controller.pitsP1 = result.numericalboardP1.slice(0, 6).reverse();
								controller.largePitP1 = result.numericalboardP1[6];
								controller.pitsP2 = result.numericalboardP2.slice(0, 6);
								controller.largePitP2 = result.numericalboardP2[6];
								controller.turn = result.currentPlayer;
								controller.gameState = result.gameState;
								if(result.gameState=="ON_GAME"){
									controller.message = showPlayerDescription(result.currentPlayer) + " turn";	
									controller.formMessageClass = result.currentPlayer;
								}else{
									controller.message = showGameStateDescription(result.gameState);	
									if(result.winnerPlayer)
										controller.formMessageClass = "winner" + result.winnerPlayer;
									else
										controller.formMessageClass = "draw";
								}
							}
						}).error(function() {
							// handle errors....maybe in version 2.0
						});
			};
			controller.pitsP1 = [];
			controller.pitsP2 = [];
			controller.largePitP1 = 0;
			controller.largePitP2 = 0;
			controller.formMessageClass = "P1";
			controller.message="";
		});