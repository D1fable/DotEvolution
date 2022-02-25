package Simulation;

public class Dot {
	
	public int x;
	public int y;
	public int color;
	
	public Brain brain;
	
	public Dot(int x, int y) {
		this.x = x;
		this.y = y;

		this.color = 0;
		
		this.brain = new Brain();
	}

}
