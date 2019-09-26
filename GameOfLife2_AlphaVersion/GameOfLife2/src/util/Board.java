package util;

import javax.swing.JComponent;

public class Board extends GameOfLifeBoard {

	private MyGraphics graphics;


	public Board(int length, int height, MyGraphics graphics) {
		super(length, height);
		this.graphics=graphics;
	}

	@Override
	public void playTurn(){
		for(int i=0; i<getLength(); i++)
			for(int j=0; j<getHeight(); j++)
				manageCell(i,j, getNumberOfLivingNeighbours(i, j));
		graphics.update();
	}

	@Override
	public void turnToLiving(int x, int y) {
		setBoardCoord(x,y,true);
	}

	@Override
	public void turnToDead(int x, int y) {
		setBoardCoord(x,y,false);

	}

	@Override
	public boolean isAlive(int x, int y) {
		return getBoardCoord(x, y);
	}

	@Override
	public void initiateRandomCells(double probabilityForEachCell) {
		for(int i=0; i<getLength(); i++){
			for(int j=0; j<getHeight(); j++){
				//...
			}
		}
	}

	//DA ERRO QUE NAO FAÇO A MINIMA
		@Override
		public int getNumberOfLivingNeighbours(int x, int y) {
			int count=0;
			for(int i=x-1; i<=x+1; i++){
				for(int j=y-1; j<=y+1; j++){
					if(i>0 && i<length && j>0 && j<height){
						if(i==x && j==y)
							continue;
						else
							if(isAlive(i,j))
								count++;
					}
				}
			}	
			return count;
		}

	@Override
	public void manageCell(int x, int y, int livingNeighbours) {
		if(isAlive(x, y)){
			if(getNumberOfLivingNeighbours(x, y)<2 || getNumberOfLivingNeighbours(x, y)>3){
				turnToDead(x, y);
			}
		}
		else
			if(getNumberOfLivingNeighbours(x, y)==3)
				turnToLiving(x, y);	
	}

	public void reset() {
		setBoard(new boolean[getLength()][getHeight()]);
	}


}
