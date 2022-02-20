package InputNeurons;

public class InputEast extends InputNeuron {
	
	public int distanceE = 0;
	final double weight = 1d - Math.random()*2d;

	public void update(int posX, int root) {
		this.distanceE = root - 1 - posX;
	}
	
	public double getSignal() {
		return super.getSignal(this.distanceE, this.weight);
	}

}
