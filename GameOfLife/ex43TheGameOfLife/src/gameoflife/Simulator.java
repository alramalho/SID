package gameoflife;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import game.PersonalBoard;

@SuppressWarnings("serial")
public class Simulator extends JFrame {

	private PersonalBoard board;
	private CellManager manager;
	private MyGraphics graphics;
	private double prob=0.05;
	
	public Simulator(PersonalBoard board) {
		this.board=board;
		setPreferredSize(new Dimension(600,600));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		createComponents();
	}

	public void createComponents() {
		setLayout(new BorderLayout());
		JPanel buttons = new JPanel();
		JPanel tela = new JPanel();
		JButton start = new JButton("Start");
		JButton stop = new JButton("Stop");
		JButton reload = new JButton("Reload");
		
		graphics = new MyGraphics(manager, board);  //COMO CARALHO ISTO RESULTA?
		manager = new CellManager(graphics, board);

		start.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {	
				manager = new CellManager(graphics, board);
				manager.startCells();
			}
			
		});
		
		stop.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				manager.stopCells();
			}
			
		});
		
		reload.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				board.whipe();
				board.initiateRandomCells(prob);
				graphics.repaint();
			}
			
		});
	
		tela.add(graphics);
		buttons.add(start);
		buttons.add(stop);
		buttons.add(reload);
		add(tela, BorderLayout.CENTER);
		add(buttons, BorderLayout.SOUTH);
	}


	public void simulate(){
		setVisible(true);
		
			
	}
	

}
