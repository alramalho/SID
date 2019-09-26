import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;

public class GUI {

	JFrame frame;
	
	JPanel panel;
	JTextPane textToDisplay;
	
	JLabel imageLabel;
	Container container;
	
	public GUI() {

		
	    JFrame frame = new JFrame();
	    container = frame.getContentPane();

		panel = new JPanel();
		textToDisplay = new JTextPane();
		textToDisplay.setText("Initializing...");
		textToDisplay.setBackground(frame.getBackground());
		textToDisplay.setEditable(false);
		panel.add(textToDisplay);
	    container.add(panel, BorderLayout.NORTH);

	    Icon imgIcon = new ImageIcon(this.getClass().getResource("ajax-loader.gif"));
	    imageLabel = new JLabel(imgIcon);
	    imageLabel.setBounds(668, 43, 46, 14); // for example, you can use your own values
	    container.add(imageLabel, BorderLayout.CENTER);
	    
	 
		frame.setSize(300, 150);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public void setText(String text) {
		textToDisplay.setText(text);
	}

	public void setImage(String string) {
		 Icon imgIcon = new ImageIcon(this.getClass().getResource(string));		
		 imageLabel.setIcon(imgIcon);
	}
	
	
}
