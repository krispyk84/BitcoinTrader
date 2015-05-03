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
	
	
	String lastTradeAction = "HOLD";
	String lastTradeAction2 = "HOLD";
	
	double[] fakeTradeRules = {1,0,-1,0,1,0,-1,0,1,0,-1,0,1,0,-1,0,1,0,-1,0,1,0,-1,0,-1,0,1,0,-1,0,1,0,-1,0,1,0,-1,0,1,0,-1};
	int[] fakeIterativeTradeRules = {1,0,-1,0,1,0,-1,0,1,0,-1,0,1,0,-1,0,1,0,-1,0,1,0,-1,0,-1,0,1,0,-1,0,1,0,-1,0,1,0,-1,0,1,0,-1};
	
	
	public bitThread(String URL, JTextArea marketHeader, String outputFileName, int[] header, boolean currentMarket, String marketName) 
	{
		this.URL = URL;
		this.outputFileName = outputFileName;
		this.updateRate = 9;
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
			
			long startTime = System.currentTimeMillis();
			while((System.currentTimeMillis()-startTime) < 604800000)
			{
				Thread.sleep(updateRate * 1000);
				String apiDataFull = DB.readUrl(URL);
				
				synchronized(BasicSwing.monitor) {
				    while (!BasicSwing.allThreads) {
				        if (this.marketName == "okcoin"){
				        	BasicSwing.thread4 = true;
				        	//System.out.println(this.marketName  + " READY");
				        }
				        if (this.marketName == "bitstamp"){
				        	BasicSwing.thread1 = true;
				        	//System.out.println(this.marketName  + " READY");
				        }
				        if (this.marketName == "bitfinex"){
				        	BasicSwing.thread2 = true;
				        	//System.out.println(this.marketName  + " READY");
				        }
				        if (this.marketName == "btce"){
				        	BasicSwing.thread3 = true;
				        	//System.out.println(this.marketName + " READY");
				        }
				        BasicSwing.monitor.wait();
				        break;
				    }
				}
				BasicSwing.allThreads = false;
			    if (this.marketName == "okcoin"){
		        	BasicSwing.thread4 = false;
		        }
		        if (this.marketName == "bitstamp"){
		        	BasicSwing.thread1 = false;
		        }
		        if (this.marketName == "bitfinex"){
		        	BasicSwing.thread2 = false;
		        }
		        if (this.marketName == "btce"){
		        	BasicSwing.thread3 = false;
		        }
								
				String[] apiData = apiDataFull.split(",");
				for(int i = 0; i< apiData.length; i++)
				{
					apiData[i] = apiData[i].replaceAll("[^0-9.,]+","");
				}
				currentPrice = Double.parseDouble(apiData[h[1]]);
				if(outputFileName.equals("okcoinHistoricData.txt")){
					BasicSwing.okCoinRecords.add(currentPrice);
				}
				writeToFile(currentPrice+"", bw);
				if(currentMarket){
					BasicSwing.bitstampList.add(currentPrice);
					BasicSwing.bitstampList.removeFirst();
				} else if (outputFileName.equals("bitFinexHistoricalData.txt")){
					BasicSwing.bitfinexList.add(currentPrice);
					BasicSwing.bitfinexList.removeFirst();
				} else if (outputFileName.equals("btcEHistoricalData.txt")){
					BasicSwing.btceList.add(currentPrice);
					BasicSwing.btceList.removeFirst();
				} else {
					BasicSwing.okcoinList.add(currentPrice);
					BasicSwing.okcoinList.removeFirst();
				}
				
				lineCounter++;
				marketHeader.setText("");
				marketHeader.append("Last:" 	+ apiData[h[1]] + "\n");
				marketHeader.append("Bid:" 		+ apiData[h[2]] + "\n");
				marketHeader.append("Ask:" 		+ apiData[h[3]] + "\n");
				marketHeader.append("24h Volume:" 	+ (h[4] >= 0 ? apiData[h[4]] : "N/A") + "\n");
				marketHeader.append(UIFunct.getDateTime()+ "\nUpdated Every " +(1+updateRate) + " sec"); //apiData[h[0]] 
				System.out.println(lineCounter); //for debug, to remove
				
				if(!BasicSwing.buyHoldCalculated && currentMarket && !BasicSwing.PAUSE){
					Trade.setBuyHold(apiData[h[1]]);
				}
				
				if(currentMarket && !BasicSwing.PAUSE){
					BasicSwing.bitStampRecords.add(currentPrice);
					BasicSwing.currentMarketPrice = Double.parseDouble(apiData[h[3]]);
					BasicSwing.lastPrice.setText("$"+String.format("%.2f",currentPrice));
					BasicSwing.lastPrice2.setText("$"+String.format("%.2f",currentPrice));
					if(lineCounter > 1){
						BasicSwing.lastBTC.setText("ACTIVE");
						BasicSwing.status.setText("ACTIVE");
						//double tradeAction = fakeTradeRules[fakeTradeRuleIndex];						
						double tradeAction = RuleCalc.calcFromGPProgram(currentPrice);
						System.out.println("Current Price:" + currentPrice+"");
						System.out.println("TradeAction: "+ tradeAction);
						Trade.makeGPTrade(tradeAction, lastTradeAction, currentPrice);
						//int iterativeTradeRule = fakeIterativeTradeRules[fakeTradeRuleIndex];
						int iterativeTradeRule = RuleCalc.getIterativeTradingRule(BasicSwing.bitStampRecords, BasicSwing.okCoinRecords, BasicSwing.averageDifferenceBSOK);
						System.out.println("IT TradeRule: " + iterativeTradeRule);
						fakeTradeRuleIndex++;
						Trade.makeIterativeTrade(iterativeTradeRule, lastTradeAction2, currentPrice);
						DB.writeBalancesToFile(BasicSwing.currentUSDBalance, BasicSwing.oldUSDBalance, BasicSwing.currentBTCBalance, BasicSwing.oldBTCBalance, BasicSwing.currentUSDBalance2, BasicSwing.oldUSDBalance2, BasicSwing.currentBTCBalance2, BasicSwing.oldBTCBalance2);
					}
					if(lineCounter%240 == 0){
						DB.BuildGPDatabase();
					}
				}
			}	
			
			bw.close();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void writeToFile(String currentPrice, BufferedWriter bw) throws IOException{
		bw.write(currentPrice);
		bw.newLine();
		bw.flush();
	}
}
