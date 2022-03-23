package InputNeurons;

public class InputSouth extends InputNeuron{
	
	public int distanceS = 0;

	public void update(int posX, int posY, int root) {
		this.distanceS = root - 1 - posY;
	}
	
	public double getSignal() {
		return super.getSignal(this.distanceS);
	}

}
