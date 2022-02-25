package Simulation;

import java.util.ArrayList;
import java.util.Random;

import InputNeurons.InputEast;
import InputNeurons.InputNeuron;
import InputNeurons.InputWest;
import NeuronConnections.NeuronConnections;
import OutputNeuron.OutputEast;
import OutputNeuron.OutputNeuron;
import OutputNeuron.OutputWest;

public class Brain {
	public ArrayList<InputNeuron> inputNeurons = new ArrayList<InputNeuron>();
	public NeuronConnections neuronConnections;
	public ArrayList<OutputNeuron> outputNeurons = new ArrayList<OutputNeuron>();
	
	public int numberOfInNeurons = 2;
	public int numberOfOutNeurons = 2;
	
	public double[] weights;
	public double[] processedSignals;
	
	public Brain() {
		
		//instantiate inputNeuron
		inputNeurons.add(new InputEast());
		inputNeurons.add(new InputWest());
		
		//instantiate neuronConnections
		Random rand = new Random();
		this.neuronConnections = new NeuronConnections(rand.nextBoolean());
		
		//instantiate outputNeuron
		outputNeurons.add(new OutputEast());
		outputNeurons.add(new OutputWest());
		
		
		//instantiate wieghts
		this.weights = new double[this.numberOfInNeurons];
		for (int i=0; i<this.numberOfInNeurons; i++) {
			this.weights[i] = Math.random()*(-1.0);
		}

		
		//instantiate processedSignals
		this.processedSignals = new double[this.numberOfInNeurons];
	}
	
	public int getDirection() {
		//updates signals
		for (int i=0; i<this.numberOfInNeurons; i++) {
			this.processedSignals[i] = this.inputNeurons.get(i).getSignal()*this.weights[i];
		}
		
		//looks for greated signal, saves it in index
		int index = 0;
		double greatestSignal = this.processedSignals[0];
		double tempSignal = 0d;
		for (int i=1; i<this.numberOfInNeurons; i++) {
			tempSignal = this.processedSignals[i];
			if(Math.abs(tempSignal) > Math.abs(greatestSignal)) {
				greatestSignal = tempSignal;
				index = i;
			}
		}
		
		if(Math.abs(greatestSignal) < 0.5) {
			//return 0;
		}
		
		//sets invert based on greatest Signal
		int invert = 1;
		if(greatestSignal < 0) {
			invert = -1;
		}
		
		// -1 Left/West
		//  1 Right/East
		return this.outputNeurons.get(index).getDirection()*invert;
	}
	
	public void updateInputNeurons(int posX, int root) {
		for (int i=0; i<this.numberOfInNeurons; i++) {
			this.inputNeurons.get(i).update(posX, root);
		}
		
	}
	
	//debugging method to visualize Signals
	public void printSingals() {
		System.out.println("[" + this.processedSignals[0] + "," + this.processedSignals[1] + "]");
	}
}
