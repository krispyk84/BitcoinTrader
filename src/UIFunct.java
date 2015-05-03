import java.text.SimpleDateFormat;
import java.util.Date;


public class UIFunct {
	
	//Sets the default balance values when the stop button is pressed
	public static void defaultBalances() {
		
		BasicSwing.currentMarketTrading = 0;
		
		BasicSwing.currentMarketPrice = 0;
		BasicSwing.lastPrice.setText("");
		
		BasicSwing.currentUSDBalance = 1000.00;
		BasicSwing.usdBalance.setText("$1000.00 USD");
		
		BasicSwing.currentBTCBalance = 0.00000000;
		BasicSwing.btcBalance.setText("0.00000000 BTC");
		
		BasicSwing.oldUSDBalance = 0.0;
		BasicSwing.oldBTCBalance = 0.0;
		
		//The Balances for the Iterative Algorithm
		BasicSwing.currentUSDBalance2 = 1000.00;
		BasicSwing.usdBalance2.setText("$1000.00 USD");
		
		BasicSwing.currentBTCBalance2 = 0.00000000;
		BasicSwing.btcBalance2.setText("0.00000000 BTC");
		
		BasicSwing.oldUSDBalance2 = 0.0;
		BasicSwing.oldBTCBalance2 = 0.0;
		
		BasicSwing.btcBalance3.setText("0.00000000 BTC");
		BasicSwing.buyHoldBTCBalance = 0;
		BasicSwing.buyHoldCalculated = false;
		
		BasicSwing.lastBTC.setText("On Hold");
		BasicSwing.lastTime.setText("HOLD");
		
		BasicSwing.status.setText("On Hold");
		BasicSwing.tradeAction.setText("HOLD");
		
		BasicSwing.numTradeInt = 0;
		BasicSwing.numTrades.setText("0");
	}
	
	//Sets the values on the balance/status boards
	public static void updateBalanceStatusBoard(String currentPrice){
		BasicSwing.btcUsdEquivalent3.setText("Sells For: $" + String.format("%.2f", BasicSwing.buyHoldBTCBalance*Double.parseDouble(currentPrice)));	
		if(BasicSwing.currentUSDBalance > 0){
				BasicSwing.usdBalance.setText("$"+String.format("%.2f", BasicSwing.currentUSDBalance)+" USD");
				BasicSwing.btcBalance.setText("BTC 0.00000000");
				BasicSwing.usdBtcEquivalent.setText("Buys: BTC"+ (BasicSwing.currentUSDBalance/(Double.parseDouble(currentPrice))));
				BasicSwing.btcUsdEquivalent.setText("Sells For: $0.00 USD");
			} else if (BasicSwing.currentBTCBalance > 0) {
				BasicSwing.btcBalance.setText("BTC"+String.format("%.8f", BasicSwing.currentBTCBalance));
				BasicSwing.usdBalance.setText("$0.00 USD");
				BasicSwing.usdBtcEquivalent.setText("Buys: BTC0.00000000");
				BasicSwing.btcUsdEquivalent.setText("Sells For: $" + String.format("%.2f", BasicSwing.currentBTCBalance*Double.parseDouble(currentPrice)));	
			}
		
			if(BasicSwing.currentUSDBalance2 > 0){
				BasicSwing.usdBalance2.setText("$"+String.format("%.2f", BasicSwing.currentUSDBalance2)+" USD");
				BasicSwing.btcBalance2.setText("BTC 0.00000000");
				BasicSwing.usdBtcEquivalent2.setText("Buys: BTC"+ (BasicSwing.currentUSDBalance/(Double.parseDouble(currentPrice))));
				BasicSwing.btcUsdEquivalent2.setText("Sells For: $0.00 USD");
			} else if (BasicSwing.currentBTCBalance2 > 0) {
				BasicSwing.btcBalance2.setText("BTC"+String.format("%.8f", BasicSwing.currentBTCBalance2));
				BasicSwing.usdBalance2.setText("$0.00 USD");
				BasicSwing.usdBtcEquivalent2.setText("Buys: BTC0.00000000");
				BasicSwing.btcUsdEquivalent2.setText("Sells For: $" + String.format("%.2f", BasicSwing.currentBTCBalance2*Double.parseDouble(currentPrice)));	
			}	
	}
	
	//Takes the current system time and returns a human readable value
	public static String getDateTime(){
		long currentMilliSeconds = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss");
		Date currentDateTime = new Date(currentMilliSeconds);
		return sdf.format(currentDateTime);
	}
}
