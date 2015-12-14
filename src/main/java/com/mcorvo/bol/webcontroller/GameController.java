package com.mcorvo.bol.webcontroller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.mcorvo.bol.domain.GameError;
import com.mcorvo.bol.exceptions.InvalidMoveException;
import com.mcorvo.bol.logic.GameLogic;

/**
 * Controller of the application, which has three methods to control the possible game actions and an ExceptionHandler.
 * @author Miguel.Diaz
 *
 */
@RestController
public class GameController {

	
	/**
	 * Initial page.
	 * @return goes to page.html
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home() {
		return new ModelAndView("page");
	}

	/**
	 * Starts a new game.
	 * 
	 * @param session
	 * @return initial GameLogic
	 */
	@RequestMapping(value = { "/init"}, method = RequestMethod.GET)
	public GameLogic init(HttpSession session) {
		GameLogic gameLogic = new GameLogic();	
		gameLogic.newGame();
		
		session.setAttribute("GameSession", gameLogic);
		

		return gameLogic;
	}

	/**
	 * Moves the stones of a position.
	 * 
	 * @param position
	 * @param session
	 * @return new gameLogic situation
	 */
	@RequestMapping(value = { "/move/{position}" }, method = RequestMethod.GET)
	public GameLogic move(@PathVariable("position") Integer position, HttpSession session) {
		GameLogic gameLogic = (GameLogic)session.getAttribute("GameSession");
		gameLogic.move(position);
		
		return gameLogic;
	}
	
	/**
	 * In case of an Error, sends the GameError bean with the message.
	 * TODO: Consider other Runtime errors.
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(InvalidMoveException.class)
	public GameError handleGameException(InvalidMoveException ex) {
		return new GameError(ex.getMessage());
	}
}
