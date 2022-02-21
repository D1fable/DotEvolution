package InputNeurons;

public class InputEast extends InputNeuron {
	
	public int distanceE = 0;

	public void update(int posX, int root) {
		this.distanceE = root - 1 - posX;
	}
	
	public double getSignal() {
		return super.getSignal(this.distanceE);
	}

}
