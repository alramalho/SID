package gameoflife;

import game.PersonalBoard;

public class CellManager extends Thread{

	private PersonalBoard board;
	private boolean running=false;
	private MyGraphics graphics;

	public CellManager(MyGraphics graphics,PersonalBoard board){
		this.board=board;
		this.graphics=graphics;

	}

	@Override
	public void run(){
		while(running){
			board.updateAll();
			graphics.repaint();

			try {
				sleep(150);
			} catch (InterruptedException e) {
			}
		}

	}

	public void startCells() {
			running=true;
			start();
	}

	public void stopCells() {
		running=false;
	
	}



}
