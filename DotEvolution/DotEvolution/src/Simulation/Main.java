package Simulation;

public class Main {

	public static void main(String[] args) {
		
		int gridSize = 100;
		int numberOfDots = 100; //safe only for even numbers (for now)
		int stepSpeed = 5;
		int numberOfGeneretions = 20;
		
		@SuppressWarnings("unused")
		Grid simulation = new Grid( gridSize,
				                    numberOfDots,
				                    stepSpeed,
				                    gridSize*3,
				                    numberOfGeneretions
				                  );
		
	}

}
