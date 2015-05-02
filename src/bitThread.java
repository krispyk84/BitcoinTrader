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
				String apiDataFull = HelperMethods.readUrl(URL);
				
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
				apiData[h[0]] = HelperMethods.TimestampToDate(apiData[h[0]]);
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
				marketHeader.append(System.currentTimeMillis()+ "\nUpdated Every " +(1+updateRate) + " sec"); //apiData[h[0]] 
				System.out.println(lineCounter); //for debug, to remove
				
				if(!BasicSwing.buyHoldCalculated && currentMarket && !BasicSwing.PAUSE){
					HelperMethods.setBuyHold(apiData[h[1]]);
				}
				if(currentMarket){
					HelperMethods.updateBalanceStatusBoard(apiData[h[1]]);
				}
				
				if(currentMarket && !BasicSwing.PAUSE){
					BasicSwing.btcUsdEquivalent3.setText("Sells For: $" + String.format("%.2f", BasicSwing.buyHoldBTCBalance*Double.parseDouble(apiData[h[1]])));
					
					BasicSwing.bitStampRecords.add(currentPrice);
					BasicSwing.currentMarketPrice = Double.parseDouble(apiData[h[3]]);

					
					BasicSwing.lastPrice.setText("$"+currentPrice);
					//Put last price for iterative here...
					if(lineCounter > 1){
						
						BasicSwing.lastBTC.setText("Active");
						//put active for iterative method here
						
						double tradeAction = fakeTradeRules[fakeTradeRuleIndex];						
						//double tradeAction = HelperMethods.calcFromGPProgram(currentPrice);
						System.out.println("Current Price:" + currentPrice+"");
						System.out.println("TradeAction: "+ tradeAction);
						
						HelperMethods.makeGPTrade(tradeAction, lastTradeAction, currentPrice);
						
						int iterativeTradeRule = fakeIterativeTradeRules[fakeTradeRuleIndex];
						//int iterativeTradeRule = HelperMethods.getIterativeTradingRule(BasicSwing.bitStampRecords, BasicSwing.okCoinRecords, BasicSwing.averageDifferenceBSOK);
						System.out.println("IT TradeRule: " + iterativeTradeRule);
						fakeTradeRuleIndex++;
						HelperMethods.makeIterativeTrade(iterativeTradeRule, lastTradeAction2, currentPrice);
						
					}
				}
				//System.out.println("END OF THREAD");
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
