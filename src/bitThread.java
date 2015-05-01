import java.io.*;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.swing.JTextArea;

public class bitThread implements Runnable{
	//Do not make public/static because then they'll merge the threads
	private String URL;
	private String outputFileName;
	private int updateRate; //seconds eg. 5 = 5000ms.
	private int[] h;
	private JTextArea marketHeader;
	private boolean currentMarket = false; //Current market denotes the home market. 
	private String marketName;
	public double currentPrice;
	
	//Iterative Stuff
	double averageDifferenceBSOK = 5;
	int averageBookMark = 0;
	String lastTradeAction2 = "HOLD";
	
	double[] fakeTradeRules = {1,0,-1,0,1,0,-1,0,1,0,-1,0,1,0,-1,0,1,0,-1,0,1,0,-1,0,-1,0,1,0,-1,0,1,0,-1,0,1,0,-1,0,1,0,-1};
	int[] fakeIterativeTradeRules = {1,0,-1,0,1,0,-1,0,1,0,-1,0,1,0,-1,0,1,0,-1,0,1,0,-1,0,-1,0,1,0,-1,0,1,0,-1,0,1,0,-1,0,1,0,-1};
	
	
	public bitThread(String URL, JTextArea marketHeader, String outputFileName, int[] header, boolean currentMarket, String marketName) 
	{
		this.URL = URL;
		this.outputFileName = outputFileName;
		this.updateRate = 10;
		this.h = header.clone();
		this.marketHeader = marketHeader;
		this.currentMarket = currentMarket;
		this.marketName = marketName;
	}
	
	public bitThread(String URL, JTextArea marketHeader, String outputFileName, int[] header, int updateRate, boolean currentMarket, String marketName) 
	{
		this.URL = URL;
		this.outputFileName = outputFileName;
		this.updateRate = updateRate;
		this.h = header.clone();
		this.marketHeader = marketHeader;
		this.currentMarket = currentMarket;
		this.marketName = marketName;
	}
		
	@Override
	public void run() 
	{
		SSLContext ctx = null;
        TrustManager[] trustAllCerts = new X509TrustManager[]{new X509TrustManager(){
            public X509Certificate[] getAcceptedIssuers(){return null;}
            public void checkClientTrusted(X509Certificate[] certs, String authType){}
            public void checkServerTrusted(X509Certificate[] certs, String authType){}
        }};
        try {
            ctx = SSLContext.getInstance("SSL");
            ctx.init(null, trustAllCerts, null);
        } catch (Exception e) {
        	System.out.println("Didn't work");
        }
        SSLContext.setDefault(ctx);
		
		FileWriter fw;
		BufferedWriter bw = null;
		try {
			System.out.println(outputFileName);
	        File file = new File(outputFileName);
	        if (!file.exists()) {
				file.createNewFile();
			}
	
			fw = new FileWriter(file.getAbsoluteFile());
			bw = new BufferedWriter(fw);
			int lineCounter = 0;
			int fakeTradeRuleIndex = 0;
			String lastTradeAction = "HOLD";
			long startTime = System.currentTimeMillis();
			while((System.currentTimeMillis()-startTime) < 604800000)
			{
				Thread.sleep(updateRate * 1000);
				String apiDataFull = HelperMethods.readUrl(URL);
				
				String[] apiData = apiDataFull.split(",");
				for(int i = 0; i< apiData.length; i++)
				{
					apiData[i] = apiData[i].replaceAll("[^0-9.,]+","");
				}
				apiData[h[0]] = HelperMethods.TimestampToDate(apiData[h[0]]);
				currentPrice = Double.parseDouble(apiData[h[1]]);
				if(outputFileName.equals("okcoinHistoricData.txt")){
					BasicSwing.okCoinRecords.add(currentPrice);
				}
				writeToFile(currentPrice+"", bw);
				updateCurrentPrice(marketName, currentPrice);
				

				lineCounter++;
				marketHeader.setText("");
				marketHeader.append("Last:" 	+ apiData[h[1]] + "\n");
				marketHeader.append("Bid:" 		+ apiData[h[2]] + "\n");
				marketHeader.append("Ask:" 		+ apiData[h[3]] + "\n");
				marketHeader.append("Volume:" 	+ (h[4] >= 0 ? apiData[h[4]] : "N/A") + "\n");
				marketHeader.append(apiData[h[0]] + "\nUpdated Every " + updateRate + " sec");
				System.out.println(lineCounter);
				
				if(lineCounter == 1 && currentMarket){
					BasicSwing.buyHoldBTCBalance = 1000/Double.parseDouble(apiData[h[1]]);
					BasicSwing.btcBalance3.setText("BTC " + BasicSwing.buyHoldBTCBalance);
					BasicSwing.btcUsdEquivalent3.setText("Sells For: $" + String.format("%.2f", BasicSwing.buyHoldBTCBalance*Double.parseDouble(apiData[h[1]])));
				}
				
				if(currentMarket){
					BasicSwing.btcUsdEquivalent3.setText("Sells For: $" + String.format("%.2f", BasicSwing.buyHoldBTCBalance*Double.parseDouble(apiData[h[1]])));
					
					BasicSwing.bitStampRecords.add(currentPrice);
					BasicSwing.currentMarketPrice = Double.parseDouble(apiData[h[3]]);
					if(BasicSwing.currentUSDBalance > 0){
						BasicSwing.usdBtcEquivalent.setText("Buys: BTC "+ (BasicSwing.currentUSDBalance/(Double.parseDouble(apiData[h[1]]))));
						BasicSwing.btcUsdEquivalent.setText("Sells For: $0.00");
					} else {
						BasicSwing.usdBtcEquivalent.setText("Buys: BTC 0.00000000");
						BasicSwing.btcUsdEquivalent.setText("Sells For: $" + String.format("%.2f", BasicSwing.currentBTCBalance2*Double.parseDouble(apiData[h[1]])));	
					}
					if(BasicSwing.currentUSDBalance2 > 0){						
						BasicSwing.usdBtcEquivalent2.setText("Buys: BTC "+ (BasicSwing.currentUSDBalance/(Double.parseDouble(apiData[h[1]]))));
						BasicSwing.btcUsdEquivalent2.setText("Sells For: $0.00");
					} else {
						BasicSwing.usdBtcEquivalent2.setText("Buys: BTC 0.00000000");
						BasicSwing.btcUsdEquivalent2.setText("Sells For: $" + String.format("%.2f", BasicSwing.currentBTCBalance2*Double.parseDouble(apiData[h[1]])));	
					}
					
					BasicSwing.lastPrice.setText("$"+currentPrice);
					if(lineCounter > 3){
						//double tradeAction = fakeTradeRules[fakeTradeRuleIndex];
						
						BasicSwing.lastBTC.setText("Active");
						double tradeAction = HelperMethods.calcFromGPProgram(currentPrice);
						System.out.println(currentPrice+"");
						System.out.println("TradeAction: "+ tradeAction);
						
						HelperMethods.makeGPTrade(tradeAction, lastTradeAction, currentPrice);
						
						if(lineCounter%240 == 0){
							if(BasicSwing.bitStampRecords.size() < 776390){
								averageDifferenceBSOK = HelperMethods.calcIterativeAverage(BasicSwing.bitStampRecords, BasicSwing.okCoinRecords, averageDifferenceBSOK, averageBookMark);
							} else {
								averageDifferenceBSOK = HelperMethods.calcRollingAverageDiff(BasicSwing.bitStampRecords, BasicSwing.okCoinRecords);
							}
						}
						
						int iterativeTradeRule = HelperMethods.getIterativeTradingRule(BasicSwing.bitStampRecords, BasicSwing.okCoinRecords, averageDifferenceBSOK);
						//int iterativeTradeRule = fakeIterativeTradeRules[fakeTradeRuleIndex];
						System.out.println("IT TradeRule: " + iterativeTradeRule);
						fakeTradeRuleIndex++;
						if(iterativeTradeRule == 1){
							if(!lastTradeAction2.equals("BUY")){
								if(BasicSwing.currentUSDBalance2 > 0){
									BasicSwing.oldUSDBalance2 = BasicSwing.currentUSDBalance2;
									BasicSwing.currentBTCBalance2 = (BasicSwing.currentUSDBalance2)/(currentPrice);
									BasicSwing.usdBalance2.setText("$0.00 USD");
									BasicSwing.btcBalance2.setText("BTC " + BasicSwing.currentBTCBalance2);
									BasicSwing.tradeAction.setText("BUY");
									BasicSwing.usdBtcEquivalent2.setText("Buys: BTC " + 0.00000000);
									BasicSwing.btcUsdEquivalent2.setText("Sells For: $" + String.format("%.2f", BasicSwing.currentBTCBalance2*(currentPrice)));
								}
								lastTradeAction2 = "BUY";
							} else {
								BasicSwing.tradeAction.setText("HOLD");
							}
						} else if (iterativeTradeRule == -1){
							if(!lastTradeAction2.equals("SELL")){
								if(BasicSwing.currentBTCBalance2 > 0){
									BasicSwing.oldBTCBalance2 = BasicSwing.currentBTCBalance2;
									BasicSwing.currentUSDBalance2 = BasicSwing.currentBTCBalance2*currentPrice;
									BasicSwing.btcBalance2.setText("BTC 0.0000000");
									BasicSwing.usdBalance2.setText("$" + String.format("%.2f", BasicSwing.currentUSDBalance2) + " USD");
									BasicSwing.tradeAction.setText("SELL");
									BasicSwing.usdBtcEquivalent2.setText("Buys: BTC "+ (BasicSwing.currentUSDBalance2/(currentPrice)));
									BasicSwing.btcUsdEquivalent2.setText("Sells For: $" + 0.00+" USD");
								}								
								lastTradeAction2 = "SELL";
							} else {
								BasicSwing.lastTime.setText("HOLD");
							}
						} else {
							BasicSwing.tradeAction.setText("HOLD");
						}
						
					}
				}
			}	
			
			bw.close();
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateCurrentPrice(String marketName, double currentPrice){
		if(marketName.equals("bitstamp")){
			BasicSwing.currentBistampPrice = currentPrice;
		} else if (marketName.equals("btce")){
			BasicSwing.currentBTCEPrice = currentPrice;
		} else if (marketName.equals("bitfinex")){
			BasicSwing.currentBitfinexPrice = currentPrice;
		} else if (marketName.equals("okcoin")){
			BasicSwing.currentOKCoinPrice = currentPrice;
		} else {
			System.out.println("Failed on updateCurrentPrice()");
		}
	}
	
	public void writeToFile(String currentPrice, BufferedWriter bw) throws IOException{
		bw.write(currentPrice);
		bw.newLine();
		bw.flush();
	}
}
