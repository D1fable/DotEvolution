package InputNeurons;

public class InputNorth extends InputNeuron{
	
	public int distanceN = 0;

	public void update(int posX, int posY, int root) {
		this.distanceN = posY;
	}
	
	public double getSignal() {
		return super.getSignal(this.distanceN);
	}
}
