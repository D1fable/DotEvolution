package Simulation;

import java.util.Random;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.Timer;

import Graphics.MyFrame;

public class Grid {
	
	public Dot[] dots;
	public MyFrame grid;
	public Timer timer;
	
	public boolean[][] occupied;
	public boolean[] survivors;
	
	public int gridSize;
	public int amount;
	public int speed;
	public int maxSteps;
	public int generation = 0;
	public int maxGenerations;

	
	public ArrayList<Double> results = new ArrayList<Double>();
	
	//constructor
	public Grid(int gridSize, int amount, int speed, int maxSteps, int maxGeneration) {
		
		this.occupied = new boolean[gridSize][gridSize];
		this.survivors = new boolean[amount];
		
		this.gridSize = gridSize;
		this.amount = amount;
		this.speed = speed;
		this.maxSteps = maxSteps;
	    this.maxGenerations = maxGeneration;
		
	    this.dots = new Dot[amount];
		this.initializeDots();
		
		this.grid = new MyFrame(gridSize, dots, maxSteps, generation, maxGeneration);
		this.grid.panel.setMyGrid(this);
		
		this.timer = new Timer(speed, this.grid.panel);
		this.timer.start();

	}
	
	//closes the Frame of the current Simulation generation
	public void closeFrame() {
		this.timer.stop();
		this.grid.dispose();
	}
	
	//initialized "amount" of unique random dots
	public void initializeDots() {
		
		for (int i=0; i<this.amount; i++) {
			this.survivors[i] = true;
			dots[i] = new Dot(0, 0);
		}
		
		this.shuffleDots();
		
	}

	//updates dots depending on their brain
	public void updateDots() {
		int length = dots.length;
		int direction = 0;
		for (int i=0; i<length; i++) {

			//get the direction in witch the dot wants to move
			direction = dots[i].brain.getDirection();
			
			//-1 stay still
			//0-North
			//1-East
			//2-South
			//3-West
			
			//check if the dot has space to move
			if(direction == 0) {
				if(dots[i].y > 0 && !occupied[dots[i].x][dots[i].y-1]) {
					occupied[dots[i].x][dots[i].y] = false;
					dots[i].y --;
					occupied[dots[i].x][dots[i].y] = true;
				}
			}else if(direction == 1) {
				if(dots[i].x < (this.gridSize-1) && !occupied[dots[i].x+1][dots[i].y]) {
					occupied[dots[i].x][dots[i].y] = false;
					dots[i].x ++;
					occupied[dots[i].x][dots[i].y] = true;
				}
			}else if(direction == 2) {
				if(dots[i].y < (this.gridSize-1) && !occupied[dots[i].x][dots[i].y+1]) {
					occupied[dots[i].x][dots[i].y] = false;
					dots[i].y ++;
					occupied[dots[i].x][dots[i].y] = true;
				}
			}else if(direction == 3) {
				if(dots[i].x > 0 && !occupied[dots[i].x-1][dots[i].y]) {
					occupied[dots[i].x][dots[i].y] = false;
					dots[i].x --;
					occupied[dots[i].x][dots[i].y] = true;
				}
			}
			
			//System.out.println("Direction " + direction);
			dots[i].brain.updateInputNeurons(dots[i].x, dots[i].y, this.gridSize);
			
		}
					
	}

	//chooses survivors
	public void chooseSurvivors() {
		
		//checks for survivors via a set criteria
		int length = dots.length;
		for (int i=0; i<length; i++) {
			if(dots[i].y < 20 || dots[i].y > 70 || dots[i].x < 20 || dots[i].x > 70) {
				this.survivors[i] = false;
			}
		}
		
	}

	//replaces deaths with random survivors
	public void repopulate() {
		
		int length = dots.length;
		int survived = 0;

		//count survivors
		for (int i=0; i<length; i++) {
			if(this.survivors[i] == true) {
				survived++;
			}
		}
		
		//Prints survival rate to SimulationData.txt
		double percentage = (double)survived/(double)this.amount;
		System.out.println("Survivalrate generation "+ this.generation + ": "+ percentage);
		this.results.add(percentage);
		
		//if no dots survive reinitialize all dots
		if (survived == 0) {
			for (int i=0; i<length; i++) {
				dots[i] = null;
			}
			this.initializeDots();
			return;
		}
		
		//create array with only surviving dots
		Dot[] survivingDots = new Dot[survived];
		int position = 0;
		for (int i=0; i<length; i++) {
			if(this.survivors[i] == true) {
				survivingDots[position] = dots[i];
				position++;
			}
		}
		
		//repopulate by granting 1 child to each survivor
		if (survived < this.amount/2) {
			//at least half surivived
			Dot[] childrenDots = new Dot[survived];
			for (int i=0; i<survived; i++) {
				childrenDots[i] = createCopy(survivingDots[i]);
			}
			int empty = this.amount - 2*survived;
			
			for (int i=0; i<survived; i++) {
				dots[i] = survivingDots[i];
				dots[survived + i] = childrenDots[i];
				this.survivors[i] = true;
				this.survivors[survived + i] = true;
			}
			for (int i=2*survived; i<empty; i++) {
				dots[i] = new Dot(0, 0);
				this.survivors[i] = true;
			}
			
			
			
			
			
		}else {
			//more than half survived
			Random rand = new Random();
			int index = 0;
			for (int i=0; i<length; i++) {
				if(this.survivors[i] == false) {
					index = rand.nextInt(survived);
					dots[i] = createCopy(survivingDots[index]);
					this.survivors[i] = true;
				}
			}
		}
		
		//shuffle all dot positions
		this.shuffleDots();
		
	}

	//helper method of repopulate that creates a copy of a dot
	private Dot createCopy(Dot oldDot) {	
		Dot newDot = new Dot(0, 0);
		//newDot.color = oldDot.color;
		newDot.brain = new Brain(oldDot.DNA);
		
		int length = newDot.brain.neuronConnections.size();
		for (int i=0; i<length; i++) {
			newDot.brain.neuronConnections.get(i).weight = oldDot.brain.neuronConnections.get(i).weight;
		}
		
		return newDot;
	}

	//shuffles the position of all Dots
	private void shuffleDots() {
		int length = dots.length;
		for (int i=0; i<length; i++) {
			dots[i].x = -1;
			dots[i].y = -1;
		}
		
		for(int i=0; i<this.gridSize; i++) {
			for (int j=0; j<this.gridSize; j++) {
				this.occupied[i][j] = false;
			}
		}
		
		Random rand = new Random();
		
		int x, y;
		for (int i=0; i<this.amount; i++) {
			x = rand.nextInt(this.gridSize);
			y = rand.nextInt(this.gridSize);
			while(occupied[x][y]) {
				x = rand.nextInt(this.gridSize);
				y = rand.nextInt(this.gridSize);
			}
			this.occupied[x][y] = true;
			dots[i].x = x;
			dots[i].y = y;
			dots[i].brain.updateInputNeurons(x, y, this.gridSize);
		}
		
	}

	//initializes the next Generation of dots
	public void startNextGeneration() {

		this.generation++;
		this.grid = new MyFrame(this.gridSize, this.dots, this.maxSteps, this.generation, this.maxGenerations);
		this.grid.panel.setMyGrid(this);
		
		timer = new Timer(speed, this.grid.panel);
		timer.start();	
	}

	public void saveResults() {
		int length = this.results.size();
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("SimulationData.txt"));
			writer.write("" + length);
			for (int i=0; i<length; i++) {
				writer.write("\n" + this.results.get(i));
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	//Debugging Method
	public void checkBound() {
		boolean test = true;
		int n = dots.length;
		for (int i=0; i<n; i++) {
			if(dots[i].x < 0 || dots[i].x > 31 || dots[i].y < 0 || dots[i].y > 31 ) {
				test = false;
			}
		}
		int count = 0;
		for(int i=0; i<this.gridSize; i++) {
			for(int j=0; j<this.gridSize; j++){
				if(occupied[i][j] == true) {
					count++;
				}
			}
		}
		System.out.println(count);
		System.out.println(test);
	}

	//Debugging Method
	public boolean checkUniquenes() {
		boolean test = true;
		int n = dots.length;
		for (int i=0; i<n; i++) {
			for (int j=0; j<n; j++) {
				if(dots[j].x == dots[i].x && dots[j].y == dots[i].y) {
					test = false;
				}
			}
		}
		return test;
	}
}

