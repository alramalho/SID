package gameoflife;

import game.PersonalBoard;

public class GameOfLifeTester {

	PersonalBoard board;
	
	public GameOfLifeTester(PersonalBoard board) {
		this.board=board;
	}

	public void play() {
		board.print();
	}
	
}
