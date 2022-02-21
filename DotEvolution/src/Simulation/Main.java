package Simulation;

import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

	public static void main(String[] args) {
		
		
		@SuppressWarnings("unused")
		//call for (grid size, amount of dots, step speed, number of steps, maxGenerations);
		Grid simulation = new Grid(68, 700, 1, 140, 5);
		
		ArrayList<Double> results = Grid.results;
		int length = results.size();
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("SimulationData.txt"));
			writer.write(length);
			for (int i=0; i<length; i++) {
				writer.write("\n" + results.get(i));
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		

		
	}

}
