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
	ArrayList<InputNeuron> inputNeurons = new ArrayList<InputNeuron>();
	NeuronConnections neuronConnections;
	ArrayList<OutputNeuron> outputNeurons = new ArrayList<OutputNeuron>();
	
	public int numberOfInNeurons = 2;
	public int numberOfOutNeurons = 2;
	
	double[] weights;
	double[] processedSignals;
	
	public Brain() {
		//instantiate inputNeuron
		inputNeurons.add(new InputEast());
		inputNeurons.add(new InputWest());
		
		//instantiate outputNeuron
		outputNeurons.add(new OutputEast());
		outputNeurons.add(new OutputWest());
		
		//instantiate neuronConnections
		Random rand = new Random();
		this.neuronConnections = new NeuronConnections(rand.nextBoolean());
		
		//instantiate wieghts
		this.weights = new double[this.numberOfInNeurons];
		for (int i=0; i<this.numberOfInNeurons; i++) {
			this.weights[i] = 1d - Math.random()*2d;
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
		
		//sets invert based on greatest Signal
		int invert = 1;
		if(greatestSignal < 0) {
			invert = -1;
		}
		
		//set output
		int out = 0;
		if(!this.neuronConnections.getCross()) {
			out = this.outputNeurons.get(index).getDirection()*invert;
		}else {
			out = this.outputNeurons.get(1 - index).getDirection()*invert;
		}
		
		// -1 Left/West
		//  1 Right/East
		return (out);
	}
	
	public void updateInputNeurons(int posX, int root) {
		for (int i=0; i<this.numberOfInNeurons; i++) {
			this.inputNeurons.get(i).update(posX, root);
		}
		
	}

}
