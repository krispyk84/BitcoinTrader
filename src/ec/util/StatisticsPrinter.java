package ec.util;

 
import ec.gp.koza.KozaStatistics; 
import ec.EvolutionState; 
import ec.util.Output; 
import ec.simple.SimpleProblemForm; 
 
/** 
 * 
 * <p> 
 * Causes the descibe method on the MathsProblem class to fire 
 * at the end of every generation, allowing the JavaWriter 
 * to output the best individual as a Java file 
 * </p> 
 * 
 * <p/> 
 * This program is free software; you can redistribute it and/or 
 * modify it under the terms of the GNU General Public License 
 * as published by the Free Software Foundation; either version 2 
 * of the License, or (at your option) any later version, 
 * provided that any use properly credits the author. 
 * This program is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the 
 * GNU General Public License for more details at http://www.gnu.org 
 * </p> 
 * 
 * @author Olly Oechsle, University of Essex, Date: 05-Sep-2006 
 * @version 1.0 
 */ 
@SuppressWarnings("deprecation")
public class StatisticsPrinter extends KozaStatistics { 
 
    public void postEvaluationStatistics(EvolutionState state) { 
 
        super.postEvaluationStatistics(state); 
 
        System.out.println(); 
 
        // we have only one population, so this is kosher 
        // call the describe method which allows us to process the best individual 
        // at the end of every generation. In this case we'd like to convert that individual 
        // into Java and save it for later use. 
        ((SimpleProblemForm) (state.evaluator.p_problem.clone())).describe( state,best_of_run[0], 0, statisticslog, Output.V_NO_GENERAL); 
 
    } 
 
} 
