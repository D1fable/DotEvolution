package NeuronConnections;

import InputNeurons.InputNeuron;
import OutputNeuron.OutputNeuron;

public class NeuronConnections {
	
	public InputNeuron inputNeuron;
	public double weight;
	public OutputNeuron outputNeuron;
	
	public NeuronConnections(InputNeuron inputNeuron, double weight, OutputNeuron outputNeuron) {
		this.inputNeuron = inputNeuron;
		this.weight = weight;
		this.outputNeuron = outputNeuron;
	}
	

}
