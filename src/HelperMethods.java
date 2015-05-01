import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.*;

import ec.MathSolution;
import ec.app.bitcoinTrader.FinancialFunctions;
import ec.app.bitcoinTrader.MultiValuedRegression;

public class HelperMethods {
	
	public static String TimestampToDate(String unixtime)
	{
		String[] timeStamp = new String[2];
		if(unixtime.contains(".")){
			timeStamp = unixtime.split("\\.");
		} else {
			timeStamp[0] = unixtime;
		}
		Long timestamp = Long.valueOf(timeStamp[0]).longValue();
		Date date = new Date(timestamp*1000L); // *1000 is to convert seconds to milliseconds
		SimpleDateFormat sdf = new SimpleDateFormat("MM:dd:yy:HH:mm:ss"); // the format of your date
		
		sdf.setTimeZone(TimeZone.getTimeZone("GMT-5")); // give a timezone reference for formating (see comment at the bottom
		
		String formattedDate = sdf.format(date);
		return formattedDate;
	}
	
	public static String readUrl(String urlString) throws Exception {
	    BufferedReader reader = null;
	    try {
	        URL url = new URL(urlString);
	        //added this connection test
	        URLConnection stream = url.openConnection();
	        reader = new BufferedReader(new InputStreamReader(stream.getInputStream()));
	        //reader = new BufferedReader(new InputStreamReader(url.openStream()));
	        StringBuffer buffer = new StringBuffer();
	        int read;
	        char[] chars = new char[1024];
	        while ((read = reader.read(chars)) != -1)
	            buffer.append(chars, 0, read); 

	        return buffer.toString();
	    } catch (MalformedURLException e) { 
	        // new URL() failed
	    	System.err.println("Malformed URL: "+e);
	    	Thread.sleep(1000); //retry connection after 1 second
	    	readUrl(urlString);
			return null; 
	    } 
	    catch (IOException e) {   
	        // openConnection() failed
	    	System.err.println("Connection Error: "+e);
	    	Thread.sleep(1000); //retry connection after 1 second
	    	readUrl(urlString);
			return null; 
	    } 
	    finally {
	        if (reader != null)
	            reader.close();
	    }
	}
	
	
	public static double calcFromGPProgram(double currentPrice){
		
		MathSolution mathFunction = new MathSolution();
		String[] sourceFile = readFromFile("bitstampHistoricData.txt");
		if(sourceFile.length > 240){
			double AverageOver30s = FinancialFunctions.averageOverX(30, sourceFile.length-1, sourceFile);
			double AverageOver60s = FinancialFunctions.averageOverX(60, sourceFile.length-1, sourceFile);
			double AverageOver90s = FinancialFunctions.averageOverX(90, sourceFile.length-1, sourceFile);
			double AverageOver120s = FinancialFunctions.averageOverX(120, sourceFile.length-1, sourceFile);
			
			double RateOfChangeOver30s = FinancialFunctions.rateOfChangeOverX(30, sourceFile.length-1, sourceFile);
			double RateOfChangeOver60s = FinancialFunctions.rateOfChangeOverX(60, sourceFile.length-1, sourceFile);
			double RateOfChangeOver90s = FinancialFunctions.rateOfChangeOverX(90, sourceFile.length-1, sourceFile);
			double RateOfChangeOver120s = FinancialFunctions.rateOfChangeOverX(120, sourceFile.length-1, sourceFile);
			
			double RelativeStrengthIndexOver30s = FinancialFunctions.relativeStrengthIndexOverN(30, sourceFile.length-1, sourceFile);
			double RelativeStrengthIndexOver60s = FinancialFunctions.relativeStrengthIndexOverN(60, sourceFile.length-1, sourceFile);
			double RelativeStrengthIndexOver90s = FinancialFunctions.relativeStrengthIndexOverN(90, sourceFile.length-1, sourceFile);
			double RelativeStrengthIndexOver120s = FinancialFunctions.relativeStrengthIndexOverN(120, sourceFile.length-1, sourceFile);
			
			double MacdValue = FinancialFunctions.MACD(sourceFile.length-1, sourceFile);
			
			double MaxValueOver30s = FinancialFunctions.maxValueOverX(30, sourceFile.length-1, sourceFile);
			double MaxValueOver60s = FinancialFunctions.maxValueOverX(60, sourceFile.length-1, sourceFile);
			double MaxValueOver90s = FinancialFunctions.maxValueOverX(90, sourceFile.length-1, sourceFile);
			double MaxValueOver120s = FinancialFunctions.maxValueOverX(120, sourceFile.length-1, sourceFile);
			
			double MinValueOver30s = FinancialFunctions.minValueOverX(30, sourceFile.length-1, sourceFile);
			double MinValueOver60s = FinancialFunctions.minValueOverX(60, sourceFile.length-1, sourceFile);
			double MinValueOver90s = FinancialFunctions.minValueOverX(90, sourceFile.length-1, sourceFile);
			double MinValueOver120s = FinancialFunctions.minValueOverX(120, sourceFile.length-1, sourceFile);
			
			String[] bitFinexRecords = readFromFile("bitFinexHistoricalData.txt");
			double averageDiffBSBF = FinancialFunctions.averageMarketVariance(sourceFile, bitFinexRecords, sourceFile.length);
			
			double VarBwBSandBF60s = FinancialFunctions.currentVarianceValue(sourceFile, bitFinexRecords, averageDiffBSBF, sourceFile.length-1, 6);
			double VarBwBSandBF120s = FinancialFunctions.currentVarianceValue(sourceFile, bitFinexRecords, averageDiffBSBF, sourceFile.length-1, 12);
			double VarBwBSandBF240s = FinancialFunctions.currentVarianceValue(sourceFile, bitFinexRecords, averageDiffBSBF, sourceFile.length-1, 24);
			
			String[] btceRecords = readFromFile("btcEHistoricalData.txt");
			double averageDiffBSBT = FinancialFunctions.averageMarketVariance(sourceFile, btceRecords, sourceFile.length);
			
			double VarBwBSandBT60s = FinancialFunctions.currentVarianceValue(sourceFile, btceRecords, averageDiffBSBT, sourceFile.length-1, 6);
			double VarBwBSandBT120s = FinancialFunctions.currentVarianceValue(sourceFile, btceRecords, averageDiffBSBT, sourceFile.length-1, 12);
			double VarBwBSandBT240s = FinancialFunctions.currentVarianceValue(sourceFile, btceRecords, averageDiffBSBT, sourceFile.length-1, 24);
			
			String[] okcoinRecords = readFromFile("okcoinHistoricData.txt");
			double averageDiffBSOK = FinancialFunctions.averageMarketVariance(sourceFile, okcoinRecords, sourceFile.length);
			
			double VarBwBSandOK60s = FinancialFunctions.currentVarianceValue(sourceFile, okcoinRecords, averageDiffBSOK, sourceFile.length-1, 6);
			double VarBwBSandOK120s = FinancialFunctions.currentVarianceValue(sourceFile, okcoinRecords, averageDiffBSOK, sourceFile.length-1, 12);
			double VarBwBSandOK240s = FinancialFunctions.currentVarianceValue(sourceFile, okcoinRecords, averageDiffBSOK, sourceFile.length-1, 24);
			
			double VolatilityOver30s = FinancialFunctions.volatility(30, sourceFile.length-1, sourceFile);
			double VolatilityOver60s = FinancialFunctions.volatility(30, sourceFile.length-1, sourceFile);
			double VolatilityOver120s = FinancialFunctions.volatility(30, sourceFile.length-1, sourceFile);
			
			double KeijzerERC = 3.14;
			
			return	mathFunction.calculate(currentPrice, AverageOver30s, AverageOver60s, AverageOver90s, AverageOver120s, 
						RateOfChangeOver30s, RateOfChangeOver60s, RateOfChangeOver90s, RateOfChangeOver120s, 
						RelativeStrengthIndexOver30s, RelativeStrengthIndexOver60s, RelativeStrengthIndexOver90s, 
						RelativeStrengthIndexOver120s, MacdValue, 
						MaxValueOver30s, MaxValueOver60s, MaxValueOver90s, MaxValueOver120s, 
						MinValueOver30s, MinValueOver60s, MinValueOver90s, MinValueOver120s, 
						VarBwBSandBF60s, VarBwBSandBF120s, VarBwBSandBF240s, 
						VarBwBSandBT60s, VarBwBSandBT120s, VarBwBSandBT240s, 
						VarBwBSandOK60s, VarBwBSandOK120s, VarBwBSandOK240s, 
						VolatilityOver30s, VolatilityOver60s, VolatilityOver120s, KeijzerERC);	
		} else {
			return 0.0;	
		}
	}
	
	public static String[] readFromFile(String filename){
		String[] toReturn = new String[241];
		try {
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(new FileReader(filename));
			int i = 0;
			String line = "";
			while ((line = br.readLine()) != null && i < 241) {
				toReturn[i] = line;
				i++;
			}
		} catch (IOException e) {
		}
		return toReturn;
	}
	
	public static void makeGPTrade(double tradeAction, String lastTradeAction, double currentPrice){
		if(tradeAction > 0){
			if(!lastTradeAction.equals("BUY")){
				if(BasicSwing.currentUSDBalance > 0){
					BasicSwing.oldUSDBalance = BasicSwing.currentUSDBalance;
					BasicSwing.currentBTCBalance = (BasicSwing.currentUSDBalance)/(currentPrice);
					BasicSwing.usdBalance.setText("$0.00 USD");
					BasicSwing.btcBalance.setText("BTC " + BasicSwing.currentBTCBalance);
					BasicSwing.lastTime.setText("BUY");
					BasicSwing.usdBtcEquivalent.setText("Buys: BTC " + 0.00000000);
					BasicSwing.btcUsdEquivalent.setText("Sells For: $" + String.format("%.2f", BasicSwing.currentBTCBalance*(currentPrice)));
					
				}
				lastTradeAction = "BUY";
			} else {
				BasicSwing.lastTime.setText("HOLD");
			}
		} else if(tradeAction < 0){
			if(!lastTradeAction.equals("SELL")){
				if(BasicSwing.currentBTCBalance > 0){
					BasicSwing.oldBTCBalance = BasicSwing.currentBTCBalance;
					BasicSwing.currentUSDBalance = BasicSwing.currentBTCBalance*currentPrice;
					BasicSwing.btcBalance.setText("BTC 0.0000000");
					BasicSwing.usdBalance.setText("$" + String.format("%.2f", BasicSwing.currentUSDBalance) + " USD");
					BasicSwing.lastTime.setText("SELL");
					BasicSwing.usdBtcEquivalent.setText("Buys: BTC "+ (BasicSwing.currentUSDBalance/(currentPrice)));
					BasicSwing.btcUsdEquivalent.setText("Sells For: $" + 0.00+" USD");
				}								
				lastTradeAction = "SELL";
			} else {
				BasicSwing.lastTime.setText("HOLD");
			}
		} else {
			BasicSwing.lastTime.setText("HOLD");
		}
	}
	
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
	
	public static double calcIterativeAverage(ArrayList<Double> homeMarket, ArrayList<Double> awayMarket, double prevAverage, int bookmark){
		double sum = 0.0;
		for(int i = 0; i<240; i++){
			sum+= awayMarket.get(bookmark+i) - homeMarket.get(bookmark+i);
		}
		sum = sum/240;
		prevAverage += sum;
		return prevAverage;
	}
	
	public static double calcCurrentDifference(String[] homeMarket, String[] awayMarket){
		double homePrice = Double.parseDouble(homeMarket[homeMarket.length-1]);
		double awayPrice = Double.parseDouble(awayMarket[awayMarket.length-1]);
		return awayPrice-homePrice;
	}
	
	public static double calcRollingAverageDiffSmall(String[] homeMarket, String[] awayMarket, int prevTotal, int prevNumberOfRecords){
		int shortest = Math.min(homeMarket.length, awayMarket.length);
		int numberOfRecordsToAdd = shortest - prevNumberOfRecords;
		for(int i = shortest; i<(shortest+numberOfRecordsToAdd); i++){
			prevTotal += Math.abs(Double.parseDouble(awayMarket[i]) - Double.parseDouble(homeMarket[i]));
		}
		
		return prevTotal/shortest;
	}
	
	public static double calcRollingAverageDiff(ArrayList<Double> homeMarket, ArrayList<Double> awayMarket){
		int shortest = Math.min(homeMarket.size(), awayMarket.size());
		int numberOfRecordsToAddSub = shortest - 776390;
		for(int i = 0; i<numberOfRecordsToAddSub; i++){
			BasicSwing.totalDiff -= Math.abs(awayMarket.get(i) - homeMarket.get(i));
		}
		
		if(homeMarket.size() < awayMarket.size()){	
			for(int i = homeMarket.size()-1; i >= (homeMarket.size()-numberOfRecordsToAddSub); i--){
				BasicSwing.totalDiff += Math.abs(awayMarket.get(i) - homeMarket.get(i));
			}
		} else {
			for(int i = awayMarket.size()-1; i >= (awayMarket.size()-numberOfRecordsToAddSub); i--){
				BasicSwing.totalDiff += Math.abs(awayMarket.get(i) - homeMarket.get(i));
			}
		}
		return BasicSwing.totalDiff/776930;
	}
	
}
