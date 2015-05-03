import java.io.IOException;
import java.util.ArrayList;

import ec.MathSolution;
import ec.app.bitcoinTrader.FinancialFunctions;


public class RuleCalc {
	
	//Calculates the trading rule from the GP program
	public static double calcFromGPProgram(double currentPrice){
		
		MathSolution mathFunction = new MathSolution();
		
		if(BasicSwing.bitstampList.size() >= 240){
			double AverageOver30s = FinancialFunctions.averageOverX(30, BasicSwing.bitstampList.size()-1, BasicSwing.bitstampList);
			double AverageOver60s = FinancialFunctions.averageOverX(60, BasicSwing.bitstampList.size()-1, BasicSwing.bitstampList);
			double AverageOver90s = FinancialFunctions.averageOverX(90, BasicSwing.bitstampList.size()-1, BasicSwing.bitstampList);
			double AverageOver120s = FinancialFunctions.averageOverX(120, BasicSwing.bitstampList.size()-1, BasicSwing.bitstampList);
			
			double RateOfChangeOver30s = FinancialFunctions.rateOfChangeOverX(30, BasicSwing.bitstampList.size()-1, BasicSwing.bitstampList);
			double RateOfChangeOver60s = FinancialFunctions.rateOfChangeOverX(60, BasicSwing.bitstampList.size()-1, BasicSwing.bitstampList);
			double RateOfChangeOver90s = FinancialFunctions.rateOfChangeOverX(90, BasicSwing.bitstampList.size()-1, BasicSwing.bitstampList);
			double RateOfChangeOver120s = FinancialFunctions.rateOfChangeOverX(120, BasicSwing.bitstampList.size()-1, BasicSwing.bitstampList);
			
			double RelativeStrengthIndexOver30s = FinancialFunctions.relativeStrengthIndexOverN(30, BasicSwing.bitstampList.size()-1, BasicSwing.bitstampList);
			double RelativeStrengthIndexOver60s = FinancialFunctions.relativeStrengthIndexOverN(60, BasicSwing.bitstampList.size()-1, BasicSwing.bitstampList);
			double RelativeStrengthIndexOver90s = FinancialFunctions.relativeStrengthIndexOverN(90, BasicSwing.bitstampList.size()-1, BasicSwing.bitstampList);
			double RelativeStrengthIndexOver120s = FinancialFunctions.relativeStrengthIndexOverN(120, BasicSwing.bitstampList.size()-1, BasicSwing.bitstampList);
			
			double MacdValue = FinancialFunctions.MACD(BasicSwing.bitstampList.size()-1, BasicSwing.bitstampList);
			
			double MaxValueOver30s = FinancialFunctions.maxValueOverX(30, BasicSwing.bitstampList.size()-1, BasicSwing.bitstampList);
			double MaxValueOver60s = FinancialFunctions.maxValueOverX(60, BasicSwing.bitstampList.size()-1, BasicSwing.bitstampList);
			double MaxValueOver90s = FinancialFunctions.maxValueOverX(90, BasicSwing.bitstampList.size()-1, BasicSwing.bitstampList);
			double MaxValueOver120s = FinancialFunctions.maxValueOverX(120, BasicSwing.bitstampList.size()-1, BasicSwing.bitstampList);
			
			double MinValueOver30s = FinancialFunctions.minValueOverX(30, BasicSwing.bitstampList.size()-1, BasicSwing.bitstampList);
			double MinValueOver60s = FinancialFunctions.minValueOverX(60, BasicSwing.bitstampList.size()-1, BasicSwing.bitstampList);
			double MinValueOver90s = FinancialFunctions.minValueOverX(90, BasicSwing.bitstampList.size()-1, BasicSwing.bitstampList);
			double MinValueOver120s = FinancialFunctions.minValueOverX(120, BasicSwing.bitstampList.size()-1, BasicSwing.bitstampList);
			
			double VarBwBSandBF60s = FinancialFunctions.currentVarianceValue(BasicSwing.bitstampList, BasicSwing.bitfinexList, BasicSwing.averageDifferenceBSBF, BasicSwing.bitstampList.size()-1, 6);
			double VarBwBSandBF120s = FinancialFunctions.currentVarianceValue(BasicSwing.bitstampList, BasicSwing.bitfinexList, BasicSwing.averageDifferenceBSBF, BasicSwing.bitstampList.size()-1, 12);
			double VarBwBSandBF240s = FinancialFunctions.currentVarianceValue(BasicSwing.bitstampList, BasicSwing.bitfinexList, BasicSwing.averageDifferenceBSBF, BasicSwing.bitstampList.size()-1, 24);
			
			double VarBwBSandBT60s = FinancialFunctions.currentVarianceValue(BasicSwing.bitstampList, BasicSwing.btceList, BasicSwing.averageDifferenceBSBT, BasicSwing.bitstampList.size()-1, 6);
			double VarBwBSandBT120s = FinancialFunctions.currentVarianceValue(BasicSwing.bitstampList, BasicSwing.btceList, BasicSwing.averageDifferenceBSBT, BasicSwing.bitstampList.size()-1, 12);
			double VarBwBSandBT240s = FinancialFunctions.currentVarianceValue(BasicSwing.bitstampList, BasicSwing.btceList, BasicSwing.averageDifferenceBSBT, BasicSwing.bitstampList.size()-1, 24);
			
			double VarBwBSandOK60s = FinancialFunctions.currentVarianceValue(BasicSwing.bitstampList, BasicSwing.okcoinList, BasicSwing.averageDifferenceBSOK, BasicSwing.bitstampList.size()-1, 6);
			double VarBwBSandOK120s = FinancialFunctions.currentVarianceValue(BasicSwing.bitstampList, BasicSwing.okcoinList, BasicSwing.averageDifferenceBSOK, BasicSwing.bitstampList.size()-1, 12);
			double VarBwBSandOK240s = FinancialFunctions.currentVarianceValue(BasicSwing.bitstampList, BasicSwing.okcoinList, BasicSwing.averageDifferenceBSOK, BasicSwing.bitstampList.size()-1, 24);
			
			double VolatilityOver30s = FinancialFunctions.volatility(30, BasicSwing.bitstampList.size()-1, BasicSwing.bitstampList);
			double VolatilityOver60s = FinancialFunctions.volatility(60, BasicSwing.bitstampList.size()-1, BasicSwing.bitstampList);
			double VolatilityOver120s = FinancialFunctions.volatility(120,  BasicSwing.bitstampList.size()-1, BasicSwing.bitstampList);
			
			return	mathFunction.calculate(currentPrice, AverageOver30s, AverageOver60s, AverageOver90s, AverageOver120s, 
						RateOfChangeOver30s, RateOfChangeOver60s, RateOfChangeOver90s, RateOfChangeOver120s, 
						RelativeStrengthIndexOver30s, RelativeStrengthIndexOver60s, RelativeStrengthIndexOver90s, 
						RelativeStrengthIndexOver120s, MacdValue, 
						MaxValueOver30s, MaxValueOver60s, MaxValueOver90s, MaxValueOver120s, 
						MinValueOver30s, MinValueOver60s, MinValueOver90s, MinValueOver120s, 
						VarBwBSandBF60s, VarBwBSandBF120s, VarBwBSandBF240s, 
						VarBwBSandBT60s, VarBwBSandBT120s, VarBwBSandBT240s, 
						VarBwBSandOK60s, VarBwBSandOK120s, VarBwBSandOK240s, 
						VolatilityOver30s, VolatilityOver60s, VolatilityOver120s);	
		} else {
			return 0.0;	
		}
	}
	
	//The algorithm that determines trades on the Iterative Trading Rules
	public static int getIterativeTradingRule(ArrayList<Double> homeMarket, ArrayList<Double> awayMarket, double averageDiff){
		int toReturn = 0;
		if(awayMarket.size() > 0 && homeMarket.size() > 0){
			double currentDifference = awayMarket.get(awayMarket.size()-1) - homeMarket.get(homeMarket.size()-1);
			if(Math.abs(currentDifference) > Math.abs(averageDiff)){
				if(currentDifference > 0){
					toReturn = 1;
				} else if (currentDifference < 0){
					toReturn = -1;
				}
			}
		}
		return toReturn;
	}
	
	public static double calculateHistoricAverageDifference(String[] bitstampArray, String[] okcoinArray) throws IOException{
		int shortest = Math.min(bitstampArray.length, okcoinArray.length);
		double priceSum = 0.0;
		int j = 0;
		for(int i = 0; i < shortest; i++){
			priceSum+= Math.abs(Double.parseDouble(okcoinArray[i])-Double.parseDouble(bitstampArray[i]));
			j++;
		}
		System.out.println(j+"");
		return priceSum/shortest;
		
	}
}
