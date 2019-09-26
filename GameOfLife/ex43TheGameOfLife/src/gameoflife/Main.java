package gameoflife;

import game.PersonalBoard;

public class Main {

	public static void main(String[] args) {
		PersonalBoard board = new PersonalBoard( 100, 100);
        board.initiateRandomCells(.05);

        Simulator simulator = new Simulator(board);
        simulator.simulate();
	}

}
