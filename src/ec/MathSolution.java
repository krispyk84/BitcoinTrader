package ec;

/**
Fitness: 21624
Generated by ECJ2Java on Thu Apr 30 02:32:33 EDT 2015
**/

public class MathSolution {

	public double calculate(double x, double AverageOver30s, double AverageOver60s, double AverageOver90s, double AverageOver120s, double RateOfChangeOver30s, double RateOfChangeOver60s, double RateOfChangeOver90s, double RateOfChangeOver120s, double RelativeStrengthIndexOver30s, double RelativeStrengthIndexOver60s, double RelativeStrengthIndexOver90s, double RelativeStrengthIndexOver120s, double MacdValue, double MaxValueOver30s, double MaxValueOver60s, double MaxValueOver90s,  double MaxValueOver120s,  double MinValueOver30s,  double MinValueOver60s,  double MinValueOver90s,  double MinValueOver120s, double VarBwBSandBF60s, double VarBwBSandBF120s, double VarBwBSandBF240s, double VarBwBSandBT60s, double VarBwBSandBT120s, double VarBwBSandBT240s, double VarBwBSandOK60s, double VarBwBSandOK120s, double VarBwBSandOK240s, double VolatilityOver30s, double VolatilityOver60s, double VolatilityOver120s, double KeijzerERC) {
		double func_2 = Math.sin(Math.toRadians(RelativeStrengthIndexOver60s));
		double func_5 = VarBwBSandBF60s - RelativeStrengthIndexOver120s;
		double func_6 = func_2 - func_5;
		double func_7 = Math.pow(func_6,2);
		double func_10 = MacdValue * MinValueOver90s;
		double func_12 = Math.log(Math.abs(MacdValue));
		double func_13 = func_10 * func_12;
		double func_14 = Math.sin(Math.toRadians(func_13));
		return func_7 * func_14;
	}

}