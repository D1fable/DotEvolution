package Simulation;

import java.util.ArrayList;

import InputNeurons.InputEast;
import InputNeurons.InputNeuron;
import InputNeurons.InputWest;
import NeuronConnections.NeuronConnections;
import OutputNeuron.OutputEast;
import OutputNeuron.OutputNeuron;
import OutputNeuron.OutputWest;

public class Brain {
	ArrayList<InputNeuron> inputNeurons = new ArrayList<InputNeuron>();
	InputNeuron in;
	
	NeuronConnections neuronConnections;
	
	ArrayList<OutputNeuron> outputNeurons = new ArrayList<OutputNeuron>();
	OutputNeuron out;
	
	public int numberOfInNeurons = 2;
	public int numberOfOutNeurons = 1;
	
	public double weight;
	
	public Brain(int seed1, int seed2) {
		//instantiate inputNeuron
		if(seed1 == 0) {
			in = new InputEast();
			inputNeurons.add(new InputEast());
			inputNeurons.add(new InputWest());
		}else {
			in = new InputWest();
			inputNeurons.add(new InputEast());
			inputNeurons.add(new InputWest());
		}
		
		//instantiate outputNeuron
		if(seed2 == 0) {
			out = new OutputEast();
			outputNeurons.add(new OutputEast());
			outputNeurons.add(new OutputWest());
		}else {
			out = new OutputWest();
			outputNeurons.add(new OutputEast());
			outputNeurons.add(new OutputWest());
		}
		
	}
	
	public int getDirection() {
		int direction = this.out.getDirection(); //-1 for left, 1 for right
		//int choice = this.in.getSignal(); //-1 for invert, 0 for don't move, 1 for normal
		
		double strongestSignal = 0d;
		int strongestSignalIndex = -1;
		double temp = 0;
		for (int i=0; i<this.numberOfInNeurons; i++) {
			temp = this.inputNeurons.get(i).getSignal();
			if(Math.abs(temp) > strongestSignal) {
				strongestSignal = temp;
				strongestSignalIndex = i;
			}
		}
		
		if(strongestSignalIndex == 0) {
			//InputE
			
		}else {
			
		}
		
		return (0x000000);
	}

}
