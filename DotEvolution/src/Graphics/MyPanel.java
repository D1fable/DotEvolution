package Graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import Simulation.Dot;
import Simulation.Grid;

@SuppressWarnings("serial")
public class MyPanel extends JPanel implements ActionListener{
	
	public Dot[] dots;
	public int step = 0;
	
	final int ROOT;
	final int maxSteps;
	final int scale = 10;
	
	final int PANEL_WIDTH;
	final int PANEL_HEIGHT;
	
	public MyPanel(int root, Dot[] dots, int maxSteps) {
		this.dots = dots;
		this.ROOT = root;
		this.PANEL_WIDTH = this.scale*(root);
		this.PANEL_HEIGHT = this.scale*(root);
		this.maxSteps = maxSteps;
		
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		
	}
	
	public void paint (Graphics g) {
		super.paint(g);
		
		Graphics2D g2D = (Graphics2D) g;
		
		//paint backroundGrid
		g2D.setColor(Color.GRAY);
		g2D.setStroke(new BasicStroke(1));
		for (int i=0; i<this.ROOT+1; i++) {
			g2D.drawLine(0, this.scale*i, this.PANEL_HEIGHT, this.scale*i);
			g2D.drawLine(this.scale*i, 0, this.scale*i, this.PANEL_WIDTH);
		}
		
		//paint Dots
		int length = dots.length;
		for (int i=0; i<length; i++) {
			g2D.setColor(new Color(dots[i].color));
			g2D.fillOval(dots[i].x*this.scale + 3, dots[i].y*this.scale + 3, this.scale/2, this.scale/2);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Grid.updateDots();
		this.step++;
		if(this.step > this.maxSteps) {
			Grid.closeFrame();
			Grid.chooseSurvivors();
			Grid.repopulate();
			Grid.startNextGeneration();
		}else {
			repaint();
		}
		
	}

}
