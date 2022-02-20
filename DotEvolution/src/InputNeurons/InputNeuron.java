package InputNeurons;

public class InputNeuron {
	
	int distance;
	double weight;
	
	public void update(int posX, int root) {}
	
	public double getSignal() {
		return 0;
	}
	
	public double getSignal(int d, double w) {
		/*
		int choice = 0;
		double processedSignal = sigmoid((d/10d)*w);
		
		//System.out.println(processedSignal);
		
		if(processedSignal > 0.6) {
			choice = 1;
		}else if(processedSignal < 0.4) {
			choice = -1;
		}
		*/
		return sigmoid((d/10d)*w);
	}
	
	protected double sigmoid(double x) {
		double fx = 1d/(1d+Math.exp(-x));
		
		return fx;
	}
}
