package Graphics;

import javax.swing.*;

import Simulation.Dot;

@SuppressWarnings("serial")
public class MyFrame extends JFrame { 
	
	public MyPanel panel;
	
	public MyFrame(int gridSize, Dot[] dots, int steps, int generation, int maxGeneration) {
		
		panel = new MyPanel(gridSize, dots, steps, generation, maxGeneration);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Generation " + generation);
		
		this.add(panel);
		this.pack();
		
		ImageIcon image = new ImageIcon("download.jpg");
		this.setIconImage(image.getImage());
		
		this.setVisible(true);
	
	}

}
