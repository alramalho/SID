package util;

public class CellManager extends Thread {

	private boolean running=true;
	private Board board;
	private int VELOCITY=100;

	public CellManager(Board board) {
		this.board=board;
	}

	@Override
	public void run(){
		while(running){
			board.playTurn();
			try {
				sleep(50000/VELOCITY);
			} catch (InterruptedException e) {
				running=false;
			}
		}
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean b) {
		this.running=b;
	}

	public void updateVelocity(String s){
		if(s.equals("0.25x"))
			VELOCITY = 25;
		if(s.equals("0.5x"))
			VELOCITY = 50;
		if(s.equals("1x"))
			VELOCITY = 100;
		if(s.equals("2x"))
			VELOCITY = 200;
		if(s.equals("5x"))
			VELOCITY = 500;
		if(s.equals("10x"))
			VELOCITY = 1000;

	}


}
