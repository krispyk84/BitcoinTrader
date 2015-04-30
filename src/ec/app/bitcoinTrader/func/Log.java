/*
  Copyright 2006 by Sean Luke
  Licensed under the Academic Free License version 3.0
  See the file "LICENSE" for more information
*/


package ec.app.bitcoinTrader.func;

import ac.essex.ooechs.ecj.ecj2java.example.data.DoubleData;
import ec.gp.GPData; 
import ec.gp.ADFStack; 
import ec.gp.GPIndividual; 
import ec.EvolutionState; 
import ec.Problem; 
import ac.essex.ooechs.ecj.ecj2java.nodes.ParseableGPNode; 
//import ac.essex.ooechs.ecj.ecj2java.example.data.DoubleData; 
 
/* 
 * Log.java
 * 
 * Created: Wed Nov  3 18:26:37 1999
 * By: Sean Luke
 */

/**
 * @author Sean Luke
 * @version 1.0 
 */

public class Log extends ParseableGPNode
    {
    public Log() {
		super("rlog", 1);
		// TODO Auto-generated constructor stub
	}

	private static final long serialVersionUID = 1;

    public String toString() { return "rlog"; }

/*
  public void checkConstraints(final EvolutionState state,
  final int tree,
  final GPIndividual typicalIndividual,
  final Parameter individualBase)
  {
  super.checkConstraints(state,tree,typicalIndividual,individualBase);
  if (children.length!=1)
  state.output.error("Incorrect number of children for node " + 
  toStringForError() + " at " +
  individualBase);
  }
*/
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
        rd.x = (rd.x == 0.0 ? 0.0 : /*Strict*/Math.log(/*Strict*/Math.abs(rd.x)));
        }

	@Override
	public int getObjectType() {
		// TODO Auto-generated method stub
		return ParseableGPNode.DOUBLE;
	}
	
	@Override
	public String getJavaCode() { 
        return "Math.log(Math.abs("+getChild(0).getVariableName()+"))"; 
    }

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return "rlog";
	}
}



