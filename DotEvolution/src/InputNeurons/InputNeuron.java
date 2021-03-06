package InputNeurons;

public class InputNeuron {
	
	public int distance;
	
	public void update(int posX, int posY, int root) {}
	
	public double getSignal() {
		return 0;
	}
	
	public double getSignal(int d) {
		return sigmoid(d - 50);
	}
	
	protected double sigmoid(double x) {
		double fx = 1d/(1d+Math.exp(-(x/15d)));
		return fx;
	}
}
