package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.Border;

import util.MyGraphics;

public class GUI {

	private JFrame frame;
	private MyGraphics graphics;
	
	public GUI(){
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setSize(new Dimension(650,500));
		createComponents();
		frame.setVisible(true);
		//frame.pack();
	}
	
	public void createComponents(){
		frame.setLayout(new BorderLayout());
		
		JPanel board = new JPanel();
		JPanel specs = new JPanel();
		
		graphics = new MyGraphics(5);
		board.add(graphics);
		
		
		JButton start = new JButton("Start");
		JButton clear = new JButton("Clear");
		JButton stop = new JButton("Stop");
		stop.setEnabled(false);
		clear.setEnabled(false);
		
		
		start.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				graphics.start();
				start.setEnabled(false);
				stop.setEnabled(true);
				clear.setEnabled(true);
			}
			
		});
		
		stop.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				stop();
				start.setEnabled(true);
			}
		});
		
		clear.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				graphics.clear();
				stop();
				start.setEnabled(true);
			}
			
		});
		
		JPanel scale = new JPanel();
		JLabel scaleLabel = new JLabel("Scale:");
		String[] options = {"5","10","20"};
		JComboBox<String> scalex = new JComboBox<String>(options);
		
		scalex.setSelectedIndex(0);

		scalex.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String option = (String)scalex.getSelectedItem();
				graphics.setScale(Integer.valueOf(option));
				
			}
		});
		
		scale.add(scaleLabel);
		scale.add(scalex);

		JPanel velocity = new JPanel();
		JLabel velocityLabel = new JLabel("Velocity:");
		String[] options2= {"0.25x","0.5x","1x", "2x", "5x", "10x"};

		JComboBox<String> velocityx = new JComboBox<String>(options2);
		
		velocityx.setSelectedIndex(2);
		velocityx.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String option = (String)velocityx.getSelectedItem();
				graphics.getManager().updateVelocity(option);
			}
		});
		
		velocity.add(velocityLabel);
		velocity.add(velocityx);
		
		specs.setLayout(new GridLayout(6,1, 0, 20));
		specs.add(start);
		specs.add(clear);
		specs.add(stop);
		specs.add(scale);
		specs.add(velocity);

		
		frame.add(board, BorderLayout.CENTER);
		frame.add(specs, BorderLayout.EAST);
	}

	protected void stop() {
		graphics.stop();
	}
	
	
}
