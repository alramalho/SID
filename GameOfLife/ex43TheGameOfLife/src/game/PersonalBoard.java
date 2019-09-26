package game;

import gameoflife.GameOfLifeBoard;

public class PersonalBoard extends GameOfLifeBoard {
		
	public PersonalBoard( int length, int height) {
		super(length, height);
	}

	@Override
	public void turnToLiving(int x, int y) {
		if(x<getLength() && x>=0 && y<getHeight() && y>=0)
			getBoard()[x][y]=true;
	}

	@Override
	public void turnToDead(int x, int y) {
		if(x<getLength() && x>=0 && y<getHeight() && y>=0)
			getBoard()[x][y]=false;

	}

	@Override
	public boolean isAlive(int x, int y) {
		if(x<getLength() && x>=0 && y<getHeight() && y>=0)
			return getBoard()[x][y];
		return false;
	}

	@Override
	public void initiateRandomCells(double probabilityForEachCell) {
		for(int j=0; j<getHeight(); j++){
			for(int i=0; i<getLength(); i++){
				if(Math.random()<probabilityForEachCell)
					turnToLiving(i, j);
			}
		}
	}

	@Override
	public int getNumberOfLivingNeighbours(int x, int y) {
		int counter=0;
		for(int i=x-1; i<x+2; i++)
			for(int j=y-1; j<y+2; j++)
				if(!(i==x && j==y))
					if(isAlive(i,j))
						counter++;
			
		
		return counter;
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

	@Override
	public void print() {
		for(int j=0; j<getHeight(); j++, System.out.println()){
			for(int i=0; i<getLength(); i++)
				if(getBoard()[i][j])
					System.out.print("X");
				else
					System.out.print(" ");
			}
	}
	
	public void updateAll(){
		for(int i=0; i<getBoard().length; i++){
			for(int j=0; j<getBoard()[i].length; j++){
				manageCell(i, j, getNumberOfLivingNeighbours(i, j));
			}		
		}
	}

	public void whipe() {
		for(int i=0; i<getBoard().length; i++){
			for(int j=0; j<getBoard()[i].length; j++){
				 turnToDead(i, j);
			}
		}
	}

}
