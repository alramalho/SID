package util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;

public class MyGraphics extends JComponent{

	private int currentX, currentY, oldX, oldY;
	private Graphics2D g2;
	private Image image = null;
	private static int SCALE = 5;
	private CellManager manager;
	private Board board;

	public MyGraphics(int scale){
		this.SCALE=scale;
		board = new Board(500/SCALE, 500/SCALE, this);
		setPreferredSize(new Dimension(500, 500));
		setDoubleBuffered(false);
		addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e) {
				oldX = e.getX();
				oldY = e.getY();
				fillCells(oldX,oldY);
				
			}
		});

		addMouseMotionListener(new MouseMotionAdapter() {

			@Override
			public void mouseDragged(MouseEvent e) {
				currentX = e.getX();
				currentY = e.getY();

				if (g2 != null) {
					// draw line if g2 context not null
					//g2.drawLine(oldX, oldY, currentX, currentY);
					fillCells(currentX,currentY);
					// refresh draw area to repaint
					repaint();
					// store current coords x,y as olds x,y
					oldX = currentX;
					oldY = currentY;
				}
			}
		});
	}

	protected void fillCells(int currentX, int currentY) {
		if(verifyCoords(currentX, currentY))
			board.turnToLiving(currentX/SCALE, currentY/SCALE);

		int tempX = currentX/SCALE +1;
		int tempY = currentY/SCALE +1;
		for(int i=0; i<getSize().width; i++){
			for(int j=0; j<getSize().height; j++){
				if((i/SCALE)+1 == tempX && (j/SCALE)+1 == tempY)
					g2.fillRect(i, j, 1, 1);
			}
		}
		repaint();
	}

	private boolean verifyCoords(int x, int y) {
		return x>0 && x<getSize().width && y>0 && y<getSize().height;
	}

	public void paintComponent(Graphics g){
		if(image==null){
			image = createImage(getSize().width, getSize().height);
			g2 = (Graphics2D) image.getGraphics();
			clear();
		}
		g.drawImage(image, 0, 0, null);
	}

	public void clear(){
		board.reset();
		g2.setPaint(Color.white);
		g2.fillRect(0, 0, getSize().width, getSize().height);
		g2.setPaint(Color.BLACK);
		repaint();
	}

	public void start() {
		manager = new CellManager(board);
		manager.start();
	}

	public void update() {
		for(int i=0; i<board.getLength(); i++){
			for(int j=0; j<board.getHeight(); j++){
				if(board.isAlive(i, j)){
					g2.setPaint(Color.black);
					g2.fillRect(i*SCALE, j*SCALE, SCALE, SCALE);
				}
				else{
					g2.setPaint(Color.white);
					g2.fillRect(i*SCALE, j*SCALE, SCALE, SCALE);
				}
			}
		}
		g2.setPaint(Color.black);
		repaint();
	}

	public void stop() {
		manager.interrupt();
	}
	
	public void reset(){
		image=null;
	}

	public void setScale(int option) {
		SCALE=option;
		clear();
	}
	
	public CellManager getManager(){
		return manager;
	}

}
