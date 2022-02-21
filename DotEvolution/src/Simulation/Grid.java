package Simulation;

import java.util.Random;
import java.util.ArrayList;

import javax.swing.Timer;

import Graphics.MyFrame;

public class Grid {
	
	public static MyFrame grid;
	public static Timer timer;
	public static Dot[] dots;
	
	public static boolean[][] occupied;
	public static boolean[] survivors;
	
	public static int generation = 0;
	public static int speed;
	public static int maxSteps;
	public static int amount;
	
	public static int maxGenerations = 0;
	
	public static int root;
	
	public static ArrayList<Double> results = new ArrayList<Double>();
	
	//constructor
	public Grid(int root, int amount, int speed, int maxSteps, int maxGeneration) {
		Grid.dots = new Dot[amount];
		Grid.occupied = new boolean[root][root];
		Grid.survivors = new boolean[amount];
		Grid.speed = speed;
		Grid.maxSteps = maxSteps;
		Grid.amount = amount;
		Grid.root = root;
		
		Grid.initializeDots();
		
		Grid.grid = new MyFrame(root, dots, maxSteps, generation, maxGeneration);
		Grid.timer = new Timer(speed, Grid.grid.panel);
		Grid.timer.start();
		
	}
	
	//closes the Frame of the current Simulation generation
	public static void closeFrame() {
		Grid.timer.stop();
		Grid.grid.dispose();
	}
	
	//initialized "amount" of unique random dots
	public static void initializeDots() {
		Random rand = new Random();
		
		for(int i=0; i<root; i++) {
			for (int j=0; j<root; j++) {
				Grid.occupied[i][j] = false;
			}
		}
		
		int x, y;
		for (int i=0; i<Grid.amount; i++) {
			x = rand.nextInt(root);
			y = rand.nextInt(root);
			while(occupied[x][y]) {
				x = rand.nextInt(root);
				y = rand.nextInt(root);
			}
			Grid.occupied[x][y] = true;
			Grid.dots[i] = new Dot(x, y);
			Grid.survivors[i] = true;
		}
		
	}

	//updates dots depending on their brain
	public static void updateDots() {
		int length = dots.length;
		int direction = 0;
		for (int i=0; i<length; i++) {

			//get the direction in witch the dot wants to move
			direction = dots[i].brain.getDirection();
			
			//check if the dot has space to move
			if(direction == -1) {
				if(dots[i].x > 0 && !occupied[dots[i].x-1][dots[i].y]) {
					occupied[dots[i].x][dots[i].y] = false;
					dots[i].x --;
					occupied[dots[i].x][dots[i].y] = true;
				}
			}else if(direction == 1) {
				if(dots[i].x < (root-1) && !occupied[dots[i].x+1][dots[i].y]) {
					occupied[dots[i].x][dots[i].y] = false;
					dots[i].x ++;
					occupied[dots[i].x][dots[i].y] = true;
				}
			}
			
			//update inputNeurons of current dot 
			dots[i].brain.updateInputNeurons(dots[i].x, root);
			
		}
					
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
		for(int i=0; i<Grid.root; i++) {
			for(int j=0; j<Grid.root; j++){
				if(occupied[i][j] == true) {
					count++;
				}
			}
		}
		System.out.println(count);
		System.out.println(test);
	}

	//chooses survivors
	public static void chooseSurvivors() {
		
		//checks for survivors via a set criteria
		int length = Grid.dots.length;
		for (int i=0; i<length; i++) {
			if(Grid.dots[i].x < Grid.root/3 || Grid.dots[i].x > 2*Grid.root/3) {
				Grid.survivors[i] = false;
			}
		}
		
	}

	//replaces deaths with random survivors
	public static void repopulate() {
		
		int length = Grid.dots.length;
		int survived = 0;

		//count survivors
		for (int i=0; i<length; i++) {
			if(Grid.survivors[i] == true) {
				survived++;
			}
		}
		
		//Prints survival rate to console
		double percentage = (double)survived/(double)Grid.amount;
		System.out.println("Survivors Generation " + Grid.generation + ": " + survived + "/" + Grid.amount + ", " + percentage);
		
		Grid.results.add(percentage);
		
		//if no dots survive reinitialize all dots
		if (survived == 0) {
			for (int i=0; i<length; i++) {
				Grid.dots[i] = null;
			}
			Grid.initializeDots();
			return;
		}
		
		//create array with only surviving dots
		Dot[] survivingDots = new Dot[survived];
		int position = 0;
		for (int i=0; i<length; i++) {
			if(Grid.survivors[i] == true) {
				survivingDots[position] = Grid.dots[i];
				position++;
			}
		}
		
		//replace dead dots with random surviving dots
		Random rand = new Random();
		int index = 0;
		for (int i=0; i<length; i++) {
			if(Grid.survivors[i] == false) {
				index = rand.nextInt(survived);
				Grid.dots[i] = createCopy(survivingDots[index]);
				Grid.survivors[i] = true;
			}
		}
		
		//shuffle all dot positions
		Grid.shuffleDots();
		
	}

	//helper method of repopulate that creates a copy of a dot
	private static Dot createCopy(Dot dot) {	
		Dot newDot = new Dot(0, 0);
		newDot.brain = dot.brain;
		newDot.color = dot.color;
		return newDot;
	}

	//shuffles the position of all Dots
	private static void shuffleDots() {
		int length = Grid.dots.length;
		for (int i=0; i<length; i++) {
			Grid.dots[i].x = 0;
			Grid.dots[i].y = 0;
		}
		
		for(int i=0; i<root; i++) {
			for (int j=0; j<root; j++) {
				Grid.occupied[i][j] = false;
			}
		}
		
		Random rand = new Random();
		
		int x, y;
		for (int i=0; i<Grid.amount; i++) {
			x = rand.nextInt(root);
			y = rand.nextInt(root);
			while(occupied[x][y]) {
				x = rand.nextInt(root);
				y = rand.nextInt(root);
			}
			Grid.occupied[x][y] = true;
			Grid.dots[i].x = x;
			Grid.dots[i].y = y;
		}
		
	}

	//initializes the next Generation of dots
	public static void startNextGeneration() {
		Grid.generation++;
		Grid.grid = new MyFrame(Grid.root, Grid.dots, Grid.maxSteps, Grid.generation, Grid.maxGenerations);
		timer = new Timer(speed, Grid.grid.panel);
		timer.start();
		
	}

}

