package Simulation;

import java.util.Random;

public class Dot {
	
	public int x;
	public int y;
	public int color;
	
	public Brain brain;
	
	public Dot(int x, int y) {
		this.x = x;
		this.y = y;
		
		Random rand = new Random();
		int seed1 = rand.nextInt(2);
		int seed2 = rand.nextInt(2);
		this.color = (seed1+seed2)*0x5666;
		
		this.brain = new Brain(seed1, seed2);
	}

}
