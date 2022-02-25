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
	
	public Grid myGrid;
	
	public Dot[] dots;
	public int step = 0;
	
	public int currentGeneration;
	public final int maxGeneration;
	
	public final int gridSize;
	public final int maxSteps;
	public final int scale = 10;
	
	public final int PANEL_WIDTH;
	public final int PANEL_HEIGHT;
	
	
	
	public MyPanel(int gridSize, Dot[] dots, int maxSteps, int generation, int maxGeneration) {
		
		this.dots = dots;
		
		this.currentGeneration = generation;
		this.maxGeneration = maxGeneration;
		
		this.gridSize = gridSize;
		this.maxSteps = maxSteps;
		
		this.PANEL_WIDTH = this.scale*(gridSize);
		this.PANEL_HEIGHT = this.scale*(gridSize);

		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		
	}
	
	public void setMyGrid(Grid grid) {
		this.myGrid = grid;
	}
	
	public void paint (Graphics g) {
		super.paint(g);
		Graphics2D g2D = (Graphics2D) g;
		
		this.myGrid.updateDots();
		
		//paint backroundGrid
		g2D.setColor(Color.GRAY);
		g2D.setStroke(new BasicStroke(1));
		for (int i=0; i<this.gridSize+1; i++) {
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
		//this.myGrid.updateDots();
		this.step++;
		if(this.step > this.maxSteps) {
			if(this.currentGeneration >= this.maxGeneration) {
				this.myGrid.closeFrame();
				this.myGrid.saveResults();
			}else {
				this.myGrid.closeFrame();
				this.myGrid.chooseSurvivors();
				this.myGrid.repopulate();
				this.myGrid.startNextGeneration();
			}
		}else {
			repaint();
		}
		
	}

}
