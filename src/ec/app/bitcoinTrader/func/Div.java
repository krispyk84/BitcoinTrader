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
 
/** 
 * Performs Protected Division on two numbers.<Br /> 
 * If the denominator is zero, then zero is returned. 
 */ 
public class Div extends ParseableGPNode { 
 
    public Div() { 
        // I have 2 children - this must match the type in the params file. It is ECJ's checking mechanism. 
        super("/", 2); 
    } 
 
    /** 
     * Called as the function is evaluated. 
     */ 
    public void eval(final EvolutionState state, final int thread, final GPData input, final ADFStack stack, final GPIndividual individual, final Problem problem) { 
 
        DoubleData dd = ((DoubleData) (input)); 
 
        // evaluate the left child 
        children[0].eval(state, thread, input, stack, individual, problem); 
 
        // get the first number 
        double number1 = dd.x; 
 
        // then the right child 
        children[1].eval(state, thread, input, stack, individual, problem); 
 
        // get the second number 
        double number2 = dd.x; 
 
        // then add them together (but avoid divide by zero) 
        dd.x = number2 == 0? 0 : number1 / number2; 
 
    } 
 
    public int getObjectType() { 
        return ParseableGPNode.DOUBLE; 
    } 
 
    /** 
     * Convert this code to pure Java 
     */ 
    public String getJavaCode() { 
        return getChild(1).getVariableName() + " == 0 ? 0 : " + getChild(0).getVariableName() + " / " + getChild(1).getVariableName(); 
    } 
 
} 