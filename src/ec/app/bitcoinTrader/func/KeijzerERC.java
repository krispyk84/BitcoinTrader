/*
  Copyright 2012 by Sean Luke
  Licensed under the Academic Free License version 3.0
  See the file "LICENSE" for more information
*/


package ec.app.bitcoinTrader.func;

import ec.*;
import ec.app.bitcoinTrader.*;
import ec.gp.*;
import ec.util.*;
import java.io.*;
import ac.essex.ooechs.ecj.ecj2java.nodes.ParseableERC;
import ac.essex.ooechs.ecj.ecj2java.example.data.DoubleData;
//import ac.essex.ooechs.ecj.ecj2java.example.data.DoubleData; 

/* 
 * KeijzerERC.java
 * 
 * Created: Wed Nov  3 18:26:37 1999
 * By: Sean Luke

 <p>This ERC appears in the Keijzer function set.  It is defined as a random value drawn from a Gaussian distriution with a mean of 0.0 and a standard deviation of 5.0.

 <p>M. Keijzer. Improving Symbolic Regression with Interval Arithmetic and Linear Scaling. In <i>Proc. EuroGP.</i> 2003.
*/

/**
 * @author Sean Luke
 * @version 1.0 
 */

public class KeijzerERC extends ParseableERC
    {
    public static final double MEAN = 0.0;
    public static final double STANDARD_DEVIATION = 5.0;
    
    public String name() { return "KeijzerERC"; }

    public void resetNode(final EvolutionState state, final int thread)
        { 
        value = MEAN + state.random[thread].nextGaussian() * STANDARD_DEVIATION;
        }
    

	@Override
	public int getObjectType() {
		// TODO Auto-generated method stub
		return DOUBLE;
	}
	
	@Override
	public String getJavaCode() { 
        return ""+value; 
    }

	@Override
	public double setNumber(EvolutionState arg0, int arg1) {
		// TODO Auto-generated method stub
		return 0;
	}
}



