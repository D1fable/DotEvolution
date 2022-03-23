package Simulation;

public class Main {

	public static void main(String[] args) {
		
		int gridSize = 100;
		int numberOfDots = 400; //safe only for even numbers (for now)
		int stepSpeed = 10;
		int numberOfGeneretions = 20;
		
		@SuppressWarnings("unused")
		Grid simulation = new Grid( gridSize,
				                    numberOfDots,
				                    stepSpeed,
				                    gridSize*2,
				                    numberOfGeneretions
				                  );
		
	}

}
