package util;


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
	
	public void setBoardCoord(int x, int y, boolean state) {
		board[x][y] = state;
	}
	
	public boolean getBoardCoord(int x, int y){
		return board[x][y];
	}

	public abstract void turnToLiving(int x, int y);
	
	public abstract void turnToDead(int x, int y);
	
	public abstract boolean isAlive(int x, int y);
	
	public abstract void initiateRandomCells(double probabilityForEachCell);
	
	public abstract int getNumberOfLivingNeighbours(int x, int y);
	
	public abstract void manageCell(int x, int y, int livingNeighbours);
	
	
	
}
