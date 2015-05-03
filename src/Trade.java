
public class Trade {

	//Executes a GP trade based on the trading action
	public static void makeGPTrade(double tradeAction, String lastTradeAction, double currentPrice){
		if(tradeAction > 0){
			if(!lastTradeAction.equals("BUY")){
				if(BasicSwing.currentUSDBalance > 0){
					BasicSwing.numTradeInt++;
					System.out.println("GP BUY");
					BasicSwing.oldUSDBalance = BasicSwing.currentUSDBalance;
					BasicSwing.currentBTCBalance = (BasicSwing.currentUSDBalance)/(currentPrice);
					BasicSwing.lastTime.setText("BUY");
					BasicSwing.currentUSDBalance = 0.0;
					BasicSwing.numTrades.setText(BasicSwing.numTradeInt+"");
				}
				lastTradeAction = "BUY";
			} else {
				BasicSwing.lastTime.setText("HOLD");
			}
		} else if(tradeAction < 0){
			if(!lastTradeAction.equals("SELL")){
				if(BasicSwing.currentBTCBalance > 0){
					BasicSwing.numTradeInt++;
					System.out.println("GP SELL");
					BasicSwing.oldBTCBalance = BasicSwing.currentBTCBalance;
					BasicSwing.currentUSDBalance = BasicSwing.currentBTCBalance*currentPrice;
					BasicSwing.lastTime.setText("SELL");
					BasicSwing.currentBTCBalance = 0.0;
					BasicSwing.numTrades.setText(BasicSwing.numTradeInt+"");
				}								
				
				lastTradeAction = "SELL";
			} else {
				BasicSwing.lastTime.setText("HOLD");
			}
		} else {
			BasicSwing.lastTime.setText("HOLD");
		}
	}
	
	//Executes an iterative trade based on the trade rule
	public static void makeIterativeTrade(double iterativeTradeRule, String lastTradeAction2, double currentPrice){
		if(iterativeTradeRule == 1){
			if(!lastTradeAction2.equals("BUY")){
				if(BasicSwing.currentUSDBalance2 > 0){
					BasicSwing.tradeAction.setText("BUY");
					System.out.println("IT BUY");
					BasicSwing.oldUSDBalance2 = BasicSwing.currentUSDBalance2;
					BasicSwing.currentBTCBalance2 = BasicSwing.currentUSDBalance2/currentPrice;
					BasicSwing.tradeAction.setText("BUY");
					BasicSwing.currentUSDBalance2 = 0.0;
					BasicSwing.numTrade2Int++;
					BasicSwing.numTrades2.setText(BasicSwing.numTrade2Int+"");
				}
				lastTradeAction2 = "BUY";
			} else {
				BasicSwing.tradeAction.setText("HOLD");
			}
		} else if (iterativeTradeRule == -1){
			if(!lastTradeAction2.equals("SELL")){
				if(BasicSwing.currentBTCBalance2 > 0){
					BasicSwing.tradeAction.setText("SELL");
					System.out.println("IT SELL");
					BasicSwing.oldBTCBalance2 = BasicSwing.currentBTCBalance2;
					BasicSwing.currentUSDBalance2 = BasicSwing.currentBTCBalance2*currentPrice;
					BasicSwing.tradeAction.setText("SELL");
					BasicSwing.currentBTCBalance2 = 0;
					BasicSwing.numTrade2Int++;
					BasicSwing.numTrades2.setText(BasicSwing.numTrade2Int+"");
				}								
				lastTradeAction2 = "SELL";
			} else {
				BasicSwing.lastTime.setText("HOLD");
			}
		} else {
			BasicSwing.tradeAction.setText("HOLD");
		}
	}
	
	//Creates the initial buy and hold strategy trade with the current market price
	public static void setBuyHold(String currentPrice){
		BasicSwing.buyHoldBTCBalance = 1000/Double.parseDouble(currentPrice);
		BasicSwing.btcBalance3.setText("BTC " + String.format("%.8f", BasicSwing.buyHoldBTCBalance));
		BasicSwing.btcUsdEquivalent3.setText("Sells For: $" + String.format("%.2f", BasicSwing.buyHoldBTCBalance*Double.parseDouble(currentPrice)));
		BasicSwing.buyHoldCalculated = true;
	}
	
}
