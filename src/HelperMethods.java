import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
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
		
		//String[] sourceFile = readFromFile("bitstampHistoricData.txt");
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
	
	public static String[] readFromFile(String filename) throws IOException{
		String[] toReturn = new String[countLines(filename)];
		try {
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(new FileReader(filename));
			int i = 0;
			String line = "";
			while ((line = br.readLine()) != null && i < toReturn.length) {
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
					System.out.println("GP BUY");
					BasicSwing.oldUSDBalance = BasicSwing.currentUSDBalance;
					BasicSwing.currentBTCBalance = (BasicSwing.currentUSDBalance)/(currentPrice);
					BasicSwing.usdBalance.setText("$0.00 USD");
					BasicSwing.btcBalance.setText("BTC " + BasicSwing.currentBTCBalance);
					BasicSwing.lastTime.setText("BUY");
					BasicSwing.usdBtcEquivalent.setText("Buys: BTC0.00000000");
					BasicSwing.btcUsdEquivalent.setText("Sells For: $" + String.format("%.2f", BasicSwing.currentBTCBalance*(currentPrice)));
					BasicSwing.currentUSDBalance = 0.0;
				}
				lastTradeAction = "BUY";
			} else {
				BasicSwing.lastTime.setText("HOLD");
			}
		} else if(tradeAction < 0){
			if(!lastTradeAction.equals("SELL")){
				if(BasicSwing.currentBTCBalance > 0){
					System.out.println("GP SELL");
					BasicSwing.oldBTCBalance = BasicSwing.currentBTCBalance;
					BasicSwing.currentUSDBalance = BasicSwing.currentBTCBalance*currentPrice;
					BasicSwing.btcBalance.setText("BTC 0.0000000");
					BasicSwing.usdBalance.setText("$" + String.format("%.2f", BasicSwing.currentUSDBalance) + " USD");
					BasicSwing.lastTime.setText("SELL");
					BasicSwing.usdBtcEquivalent.setText("Buys: BTC"+ (BasicSwing.currentUSDBalance/(currentPrice)));
					BasicSwing.btcUsdEquivalent.setText("Sells For: $0.00 USD");
					BasicSwing.currentBTCBalance = 0.0;
				}								
				lastTradeAction = "SELL";
			} else {
				BasicSwing.lastTime.setText("HOLD");
			}
		} else {
			BasicSwing.lastTime.setText("HOLD");
		}
	}
	
	public static void makeIterativeTrade(double iterativeTradeRule, String lastTradeAction2, double currentPrice){
		if(iterativeTradeRule == 1){
			if(!lastTradeAction2.equals("BUY")){
				if(BasicSwing.currentUSDBalance2 > 0){
					System.out.println("IT BUY");
					BasicSwing.oldUSDBalance2 = BasicSwing.currentUSDBalance2;
					BasicSwing.currentBTCBalance2 = BasicSwing.currentUSDBalance2/currentPrice;
					BasicSwing.usdBalance2.setText("$0.00 USD");
					BasicSwing.btcBalance2.setText("BTC " + BasicSwing.currentBTCBalance2);
					BasicSwing.tradeAction.setText("BUY");
					BasicSwing.usdBtcEquivalent2.setText("Buys: BTC0.00000000");
					BasicSwing.btcUsdEquivalent2.setText("Sells For: $" + String.format("%.2f", BasicSwing.currentBTCBalance2*(currentPrice)));
					BasicSwing.currentUSDBalance2 = 0.0;
				}
				lastTradeAction2 = "BUY";
			} else {
				BasicSwing.tradeAction.setText("HOLD");
			}
		} else if (iterativeTradeRule == -1){
			if(!lastTradeAction2.equals("SELL")){
				if(BasicSwing.currentBTCBalance2 > 0){
					System.out.println("IT SELL");
					BasicSwing.oldBTCBalance2 = BasicSwing.currentBTCBalance2;
					BasicSwing.currentUSDBalance2 = BasicSwing.currentBTCBalance2*currentPrice;
					BasicSwing.btcBalance2.setText("BTC 0.0000000");
					BasicSwing.usdBalance2.setText("$" + String.format("%.2f", BasicSwing.currentUSDBalance2) + " USD");
					BasicSwing.tradeAction.setText("SELL");
					BasicSwing.usdBtcEquivalent2.setText("Buys: BTC"+ (BasicSwing.currentUSDBalance2/(currentPrice)));
					BasicSwing.btcUsdEquivalent2.setText("Sells For: $0.00 USD");
					BasicSwing.currentBTCBalance2 = 0;
				}								
				lastTradeAction2 = "SELL";
			} else {
				BasicSwing.lastTime.setText("HOLD");
			}
		} else {
			BasicSwing.tradeAction.setText("HOLD");
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
	
	public static int countLines(String filename) throws IOException {
	    InputStream is = new BufferedInputStream(new FileInputStream(filename));
	    try {
	        byte[] c = new byte[1024];
	        int count = 0;
	        int readChars = 0;
	        boolean empty = true;
	        while ((readChars = is.read(c)) != -1) {
	            empty = false;
	            for (int i = 0; i < readChars; ++i) {
	                if (c[i] == '\n') {
	                    ++count;
	                }
	            }
	        }
	        return (count == 0 && !empty) ? 1 : count;
	    } finally {
	        is.close();
	    }
	}
	
	public static LinkedList<Double> buildLinkedList(String[] market){
		LinkedList<Double> toReturn = new LinkedList<Double>();
		int j = 0;
		for(int i = market.length-240; i < market.length; i++){
			toReturn.add(Double.parseDouble(market[i]));
			j++;
		}
		System.out.println(j);
		return toReturn;
		
	}
	
	public static void setBuyHold(String currentPrice){
		BasicSwing.buyHoldBTCBalance = 1000/Double.parseDouble(currentPrice);
		BasicSwing.btcBalance3.setText("BTC " + BasicSwing.buyHoldBTCBalance);
		BasicSwing.btcUsdEquivalent3.setText("Sells For: $" + String.format("%.2f", BasicSwing.buyHoldBTCBalance*Double.parseDouble(currentPrice)));
		BasicSwing.buyHoldCalculated = true;
	}
	
	public static void updateBalanceStatusBoard(String currentPrice){
		System.out.println(BasicSwing.currentUSDBalance + " | " + BasicSwing.currentBTCBalance);
		if(BasicSwing.currentUSDBalance > 0){
			BasicSwing.usdBtcEquivalent.setText("Buys: BTC"+ (BasicSwing.currentUSDBalance/(Double.parseDouble(currentPrice))));
			BasicSwing.btcUsdEquivalent.setText("Sells For: $0.00");
		} else {
			BasicSwing.usdBtcEquivalent.setText("Buys: BTC0.00000000");
			BasicSwing.btcUsdEquivalent.setText("Sells For: $" + String.format("%.2f", BasicSwing.currentBTCBalance*Double.parseDouble(currentPrice)));	
		}
		
		if(BasicSwing.currentUSDBalance2 > 0){						
			BasicSwing.usdBtcEquivalent2.setText("Buys: BTC"+ (BasicSwing.currentUSDBalance/(Double.parseDouble(currentPrice))));
			BasicSwing.btcUsdEquivalent2.setText("Sells For: $0.00");
		} else {
			BasicSwing.usdBtcEquivalent2.setText("Buys: BTC0.00000000");
			BasicSwing.btcUsdEquivalent2.setText("Sells For: $" + String.format("%.2f", BasicSwing.currentBTCBalance2*Double.parseDouble(currentPrice)));	
		}
	}
	
}
