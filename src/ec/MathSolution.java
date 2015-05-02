package ec;

/**
Fitness: 9059
Generated by ECJ2Java on Sat May 02 01:35:17 EDT 2015
**/

public class MathSolution {

	public double calculate(double x, double AverageOver30s, double AverageOver60s, double AverageOver90s, double AverageOver120s, double RateOfChangeOver30s, double RateOfChangeOver60s, double RateOfChangeOver90s, double RateOfChangeOver120s, double RelativeStrengthIndexOver30s, double RelativeStrengthIndexOver60s, double RelativeStrengthIndexOver90s, double RelativeStrengthIndexOver120s, double MacdValue, double MaxValueOver30s, double MaxValueOver60s, double MaxValueOver90s,  double MaxValueOver120s,  double MinValueOver30s,  double MinValueOver60s,  double MinValueOver90s,  double MinValueOver120s, double VarBwBSandBF60s, double VarBwBSandBF120s, double VarBwBSandBF240s, double VarBwBSandBT60s, double VarBwBSandBT120s, double VarBwBSandBT240s, double VarBwBSandOK60s, double VarBwBSandOK120s, double VarBwBSandOK240s, double VolatilityOver30s, double VolatilityOver60s, double VolatilityOver120s) {
		double func_3 = MinValueOver60s == 0 ? 0 : VolatilityOver30s / MinValueOver60s;
		double func_6 = Math.log(Math.abs(MaxValueOver90s));
		double func_7 = MaxValueOver90s * func_6;
		double func_8 = Math.cos(Math.toRadians(func_7));
		double func_9 = func_3 + func_8;
		double func_11 = Math.cos(Math.toRadians(MaxValueOver120s));
		double func_12 = func_11 == 0 ? 0 : func_9 / func_11;
		double func_15 = VarBwBSandOK240s - VarBwBSandBT60s;
		double func_16 = func_15 == 0 ? 0 : func_12 / func_15;
		double func_18 = Math.sin(Math.toRadians(VarBwBSandBT240s));
		double func_21 = AverageOver60s * RateOfChangeOver30s;
		double func_23 = Math.cos(Math.toRadians(MaxValueOver90s));
		double func_26 = RelativeStrengthIndexOver120s == 0 ? 0 : VarBwBSandOK240s / RelativeStrengthIndexOver120s;
		double func_28 = 0.5391712917702556 * MaxValueOver60s;
		double func_29 = ( func_23>=1 ) ? func_26 : func_28;
		double func_31 = Math.sin(Math.toRadians(RateOfChangeOver60s));
		double func_32 = func_29 - func_31;
		double func_33 = Math.sin(Math.toRadians(func_32));
		double func_34 = ( func_18>=1 ) ? func_21 : func_33;
		double func_36 = Math.cos(Math.toRadians(VarBwBSandBF60s));
		double func_38 = Math.log(Math.abs(VarBwBSandBF60s));
		double func_39 = func_36 * func_38;
		double func_40 = ( func_16>=1 ) ? func_34 : func_39;
		double func_43 = Math.log(Math.abs(MaxValueOver90s));
		double func_44 = MaxValueOver90s * func_43;
		double func_45 = Math.sin(Math.toRadians(func_44));
		return func_40 - func_45;
	}

}