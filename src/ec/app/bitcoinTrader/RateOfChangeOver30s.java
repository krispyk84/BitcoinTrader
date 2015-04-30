/*
  Copyright 2006 by Sean Luke
  Licensed under the Academic Free License version 3.0
  See the file "LICENSE" for more information
*/


package ec.app.bitcoinTrader;

import ec.gp.GPData; 
import ec.gp.ADFStack; 
import ec.gp.GPIndividual; 
import ec.EvolutionState; 
import ec.Problem; 
import ac.essex.ooechs.ecj.ecj2java.nodes.ParseableGPNode; 
import ac.essex.ooechs.ecj.ecj2java.example.data.DoubleData;
//import ac.essex.ooechs.ecj.ecj2java.example.data.DoubleData; 
 
public class RateOfChangeOver30s extends ParseableGPNode
    {
    public RateOfChangeOver30s() {
		super("RateOfChangeOver30s", 0);
		// TODO Auto-generated constructor stub
	}

	public String toString() { return "RateOfChangeOver30s"; }

    public int expectedChildren() { return 0; }

    public void eval(final EvolutionState state,
        final int thread,
        final GPData input,
        final ADFStack stack,
        final GPIndividual individual,
        final Problem problem)
        {
        DoubleData rd = ((DoubleData)(input));
        rd.x = ((MultiValuedRegression)problem).rateOfChange30seconds;
        }

	@Override
	public int getObjectType() {
		// TODO Auto-generated method stub
		return DOUBLE;
	}
	
	@Override
	public String getJavaCode() { 
        return "RateOfChangeOver30s"; 
    }

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return "RateOfChangeOver30s";
	}
}