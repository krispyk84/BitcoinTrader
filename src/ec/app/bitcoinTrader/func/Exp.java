/*
  Copyright 2006 by Sean Luke
  Licensed under the Academic Free License version 3.0
  See the file "LICENSE" for more information
*/
/*
 * Exp.java
 *
 * Created: Wed Nov  3 18:26:37 1999
 * By: Sean Luke
 */

/**
 * @author Sean Luke
 * @version 1.0
 */

package ec.app.bitcoinTrader.func;

import ec.gp.GPData; 
import ec.gp.ADFStack; 
import ec.gp.GPIndividual; 
import ec.EvolutionState; 
import ec.Problem; 
import ac.essex.ooechs.ecj.ecj2java.nodes.ParseableGPNode; 
import ac.essex.ooechs.ecj.ecj2java.example.data.DoubleData;

//import ac.essex.ooechs.ecj.ecj2java.example.data.DoubleData; 
 
public class Exp extends ParseableGPNode
    {
    public Exp() {
		super("exp", 1);
		// TODO Auto-generated constructor stub
	}

	public String toString() { return "exp"; }
	
    public int expectedChildren() { return 1; }

    public void eval(final EvolutionState state,
        final int thread,
        final GPData input,
        final ADFStack stack,
        final GPIndividual individual,
        final Problem problem)
    {
	    DoubleData rd = ((DoubleData)(input));
	
	    children[0].eval(state,thread,input,stack,individual,problem);
	    rd.x = /*Strict*/Math.exp(rd.x);
    }

	@Override
	public int getObjectType() {
		// TODO Auto-generated method stub
		return ParseableGPNode.DOUBLE;
	}
	
	@Override
	public String getJavaCode() { 
        return "Math.pow("+getChild(0).getVariableName()+",2)"; 
    }

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return "exp";
	}
}
