package InputNeurons;

public class InputWest extends InputNeuron{

	public int distanceW = 0;
	
	public void update(int posX, int posY, int root) {
		this.distanceW = posX;
	}

	public double getSignal() {
		return super.getSignal(this.distanceW);
	}
}
