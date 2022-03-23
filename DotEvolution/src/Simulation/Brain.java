package Simulation;

import java.util.ArrayList;

import InputNeurons.InputNeuron;
import InputNeurons.InputNorth;
import InputNeurons.InputEast;
import InputNeurons.InputSouth;
import InputNeurons.InputWest;

import NeuronConnections.NeuronConnections;

import OutputNeuron.OutputNeuron;
import OutputNeuron.OutputNorth;
import OutputNeuron.OutputEast;
import OutputNeuron.OutputSouth;
import OutputNeuron.OutputWest;

public class Brain {
	
	public ArrayList<NeuronConnections> neuronConnections;
	
	public int actualBrainSize = 0;
	public double[] processedSignals;
	
	public Brain(int[] DNA) {
		
		this.neuronConnections = new ArrayList<NeuronConnections>();
		
		InputNeuron[] inputs = new InputNeuron[4];
		OutputNeuron[] outputs = new OutputNeuron[4];
		
		inputs[0] = new InputNorth();
		outputs[0] = new OutputNorth();
		inputs[1] = new InputEast();
		outputs[1] = new OutputEast();		
		inputs[2] = new InputSouth();
		outputs[2] = new OutputSouth();		
		inputs[3] = new InputWest();
		outputs[3] = new OutputWest();
		
		int length = DNA.length;
		for (int i=0; i<length; i = i+2) {
			if(DNA[i] == 4 || DNA[i+1] == 4) {
				//Do Nothing
			}else {
				double weight = 1.0 - Math.random()*(2.0);
				this.neuronConnections.add(new NeuronConnections(inputs[DNA[i]],
						                                        weight,
						                                        outputs[DNA[i+1]]
						                                        ));
				this.actualBrainSize++;
			}
		}
		
		this.processedSignals = new double[this.actualBrainSize];
	}
	
	public int getDirection() {
		
		if(this.actualBrainSize == 0) {
			return -1;
		}
		
		//updates signals
		for (int i=0; i<this.actualBrainSize; i++) {
			NeuronConnections temp = this.neuronConnections.get(i);
			this.processedSignals[i] = temp.inputNeuron.getSignal()*temp.weight;
		}
		
		//looks for greatest signal, saves it in index
		int index = 0;
		double greatestSignal = this.processedSignals[0];
		double tempSignal = 0d;
		for (int i=1; i<this.actualBrainSize; i++) {
			tempSignal = this.processedSignals[i];
			if(Math.abs(tempSignal) > Math.abs(greatestSignal)) {
				greatestSignal = tempSignal;
				index = i;
			}
		}
		
		//System.out.println("greatestSignal " + greatestSignal + "; index " + index);
		if(Math.abs(greatestSignal) < 0.0) {
	      return -1;
		}
		boolean invert = false;
		if(greatestSignal < 0) {
			invert = true;
		}
		
		//0-North
		//1-East
		//2-South
		//3-West
		
		int out = this.neuronConnections.get(index).outputNeuron.getDirection();
		
		if(invert) {
			out = (out + 2) % 4;
		}
		
		return out;
	}
	
	public void updateInputNeurons(int posX, int posY, int root) {
		for (int i=0; i<this.actualBrainSize; i++) {
			this.neuronConnections.get(i).inputNeuron.update(posX, posY, root);
		}
		
	}

}
