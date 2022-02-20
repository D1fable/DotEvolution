package Graphics;

import javax.swing.*;

import Simulation.Dot;

@SuppressWarnings("serial")
public class MyFrame extends JFrame { 
	
	public MyPanel panel;
	
	public MyFrame(int root, Dot[] dots, int steps, int generation) {
		panel = new MyPanel(root, dots, steps);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//this.setResizable(false);
		this.setTitle("Generation " + generation);
		
		this.add(panel);
		this.pack();
		
		ImageIcon image = new ImageIcon("download.jpg");
		this.setIconImage(image.getImage());
		
		this.setVisible(true);
	
	}

}
