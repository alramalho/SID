package gameoflife;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;

import game.PersonalBoard;

public class MyGraphics extends JComponent {
	
	private static final long serialVersionUID = 1L;
	
	private PersonalBoard board;
	
	private CellManager manager;
	
	public MyGraphics(CellManager manager, PersonalBoard board) {
         setPreferredSize(new Dimension(500, 500));
         this.board=board;
         this.manager=manager;
     }
	
	public void paintComponent(Graphics g){
		for(int i=0; i<board.getLength()*5; i+=5){
			for(int j=0; j<board.getHeight()*5; j+=5)
				if(board.isAlive(i/5, j/5)){
					g.setColor(Color.BLACK);
					g.fillRect(i, j, 5, 5);
				}
				else if(!board.isAlive(i/5, j/5)){
					g.setColor(Color.WHITE);
					g.fillRect(i, j, 5, 5);
				}
		}
	}
	
}
