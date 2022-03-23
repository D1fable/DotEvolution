package Simulation;

import java.util.Random;

public class Dot {
	
	public int x;
	public int y;
	public int color;
	
	public int[] DNA = new int[8];
	public Brain brain;
	
	public Dot(int x, int y) {
		this.x = x;
		this.y = y;

		this.color = 0;
		
		Random rand = new Random();
		DNA[0] = 0;
		DNA[1] = 0;
		DNA[2] = 1;
		DNA[3] = 1;
		DNA[4] = 2;
		DNA[5] = 2;
		DNA[6] = 3;
		DNA[7] = 3;
		
		//printDNA();
		
		this.brain = new Brain(this.DNA);
	}
	
	//helper method that prints DNA
	@SuppressWarnings("unused")
	private void printDNA() {
		System.out.print("[");
		for (int i=0; i<7;  i++) {
			System.out.print(this.DNA[i] + ",");
		}
		System.out.println(this.DNA[7] + "]");
	}

}
