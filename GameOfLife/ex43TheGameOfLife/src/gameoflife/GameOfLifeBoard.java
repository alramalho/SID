package gameoflife;


public abstract class GameOfLifeBoard {
	
	int length; 
	int height;
	boolean[][] board;


	public GameOfLifeBoard(int length, int height){
		this.length=length;
		this.height=height;
		board= new boolean[length][height];
	}

	public void playTurn(){
		
	}

	public int getLength() {
		return length;
	}

	public int getHeight() {
		return height;
	}

	public boolean[][] getBoard() {
		return board;
	}

	public void setBoard(boolean[][] board) {
		this.board = board;
	}

	public abstract void turnToLiving(int x, int y);
	
	public abstract void turnToDead(int x, int y);
	
	public abstract boolean isAlive(int x, int y);
	
	public abstract void initiateRandomCells(double probabilityForEachCell);
	
	public abstract int getNumberOfLivingNeighbours(int x, int y);
	
	public abstract void manageCell(int x, int y, int livingNeighbours);
	
	public abstract void print();
	
	
}
