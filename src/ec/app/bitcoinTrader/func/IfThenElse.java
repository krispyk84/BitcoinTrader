/*
  Copyright 2013 by Sean Luke
  Licensed under the Academic Free License version 3.0
  See the file "LICENSE" for more information
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
 
public class IfThenElse extends ParseableGPNode
    {
    public IfThenElse() {
		super("IfThenElse", 3);
		// TODO Auto-generated constructor stub
	}

	public String toString() { return "IfThenElse"; }

    public int expectedChildren() { return 3; }

    public void eval(final EvolutionState state,
        final int thread,
        final GPData input,
        final ADFStack stack,
        final GPIndividual individual,
        final Problem problem)
        {
        double result, if_arg, then_arg, else_arg;
        DoubleData rd = ((DoubleData)(input));

        children[0].eval(state,thread,input,stack,individual,problem);
        if_arg = rd.x;

        children[1].eval(state,thread,input,stack,individual,problem);
        then_arg = rd.x;

      children[2].eval(state,thread,input,stack,individual,problem);
        else_arg = rd.x;

      if (if_arg >= 1){
       result = then_arg;
      } else {
       result = else_arg;
      }
      rd.x = result;
        }

	@Override
	public int getObjectType() {
		// TODO Auto-generated method stub
		return ParseableGPNode.DOUBLE;
	}
	
	@Override
	public String getJavaCode() { 
        return "( "+getChild(0).getVariableName()+">=1 ) ? "+getChild(1).getVariableName()+" : "+getChild(2).getVariableName(); 
    }

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return "IfThenElse";
	}
}

