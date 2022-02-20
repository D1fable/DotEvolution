package InputNeurons;

public class InputWest extends InputNeuron{

	public int distanceW = 0;
	final double weight = 1d - Math.random()*2d;
	
	public void update(int posX, int root) {
		this.distanceW = posX;
	}

	public double getSignal() {
		return super.getSignal(this.distanceW, this.weight);
	}
}
