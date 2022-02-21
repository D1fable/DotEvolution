package InputNeurons;

public class InputNeuron {
	
	int distance;
	
	public void update(int posX, int root) {}
	
	public double getSignal() {
		return 0;
	}
	
	public double getSignal(int d) {
		return sigmoid(d/10d);
	}
	
	protected double sigmoid(double x) {
		double fx = 1d/(1d+Math.exp(-x));
		return fx;
	}
}
