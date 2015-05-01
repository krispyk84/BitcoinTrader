package bitcoinUI;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;


public class BuildInterface {

	public static void buildUserInterface(JLabel marketSectionTitle, JLabel parametersTitle, JTextArea marketsHeaderBitstamp, JTextArea marketsHeaderOKCoin, JTextArea marketsHeaderBitfinex, JTextArea marketsHeaderBtcE, JTextArea paramatersText
			, JLabel controlsTitle, JButton start, JButton pause, JButton stop, JButton update,
			JTextField lastPrice, JTextField lastBTC, JTextField lastTime,
			JLabel accountBalancesTitles, JTextField usdBalance, JLabel usdBtcEquivalent, JTextField btcBalance, JLabel btcUsdEquivalent,
			int currentMarketTrading, JPanel p, String mainParamsFile, JLabel accountBalancesTitles2, JTextField usdBalance2,
			JLabel usdBtcEquivalent2, JTextField btcBalance2, JLabel btcUsdEquivalent2, JTextField lastPrice2, JTextField status, JTextField tradeAction, JLabel accountBalancesTitles3, JTextField btcBalance3, JLabel btcUsdEquivalent3){
		JPanel marketSnapshot = new JPanel(new FlowLayout()); // new FlowLayout not needed
		marketSnapshot.setOpaque(false);
		 /* JButton button = new JButton("OK");
		  //button.setSize(80, 200);
		  button.setPreferredSize(new Dimension(80, 200)); // ?? I don't like this.
		  button.setFont(new Font("Arial", 1, 40));
		  marketSnapshot.add(button);*/
		
		Font myFontTitle = new Font("Helvetica", Font.BOLD, 18);
		
		p.add(marketSectionTitle);
		marketSectionTitle.setLocation(400,20);
		marketSectionTitle.setSize(200,20);
		Font myFont = new Font("Helvetica", Font.PLAIN, 18);
		marketSectionTitle.setFont(myFont);
				
		//Specify that no layout is set for the main panel
		p.add(marketsHeaderBitstamp);
		marketsHeaderBitstamp.setLocation(50,50);
		marketsHeaderBitstamp.setSize(200,120);
		marketsHeaderBitstamp.setBorder(BorderFactory.createTitledBorder("Bistamp"));
		
		p.add(marketsHeaderOKCoin);
		marketsHeaderOKCoin.setLocation(260,50);
		marketsHeaderOKCoin.setSize(200,120);
		marketsHeaderOKCoin.setBorder(BorderFactory.createTitledBorder("OKCoin"));
		
		p.add(marketsHeaderBitfinex);
		marketsHeaderBitfinex.setLocation(470,50);
		marketsHeaderBitfinex.setSize(200,120);
		marketsHeaderBitfinex.setBorder(BorderFactory.createTitledBorder("BitFinex"));
		
		p.add(marketsHeaderBtcE);
		marketsHeaderBtcE.setLocation(680,50);
		marketsHeaderBtcE.setSize(200,120);
		marketsHeaderBtcE.setBorder(BorderFactory.createTitledBorder("BTC-E"));
		
		JPanel balances = new JPanel(new GridLayout()); // new FlowLayout not needed
		balances.setOpaque(false);
		
		p.add(accountBalancesTitles);
		accountBalancesTitles.setLocation(500,215);
		accountBalancesTitles.setSize(200,20);
		accountBalancesTitles.setFont(myFont);
		
	    p.add(usdBalance);
		usdBalance.setLocation(500,245);
		usdBalance.setSize(180,30);
		Font myFontBalance = new Font("Helvetica", Font.BOLD, 22);
		usdBalance.setFont(myFontBalance);
		usdBalance.setHorizontalAlignment(JTextField.CENTER);
		
		p.add(usdBtcEquivalent);
		usdBtcEquivalent.setLocation(500,280);
		usdBtcEquivalent.setSize(180,20);
		usdBtcEquivalent.setFont(myFont);
		
		p.add(btcBalance);
		btcBalance.setLocation(700,245);
		btcBalance.setSize(180,30);
		btcBalance.setFont(myFontBalance);
		btcBalance.setHorizontalAlignment(JTextField.CENTER);
		
		p.add(btcUsdEquivalent);
		btcUsdEquivalent.setLocation(700,280);
		btcUsdEquivalent.setSize(180,20);
		btcUsdEquivalent.setFont(myFont);
		
		p.add(controlsTitle);
		controlsTitle.setLocation(50,190);
		controlsTitle.setSize(200,20);
		controlsTitle.setFont(myFont);
	
		//start.addActionListener(this);
		start.setBounds(50,230,100,40);
		start.setEnabled(true);
		start.setBackground(Color.GREEN);
		start.addActionListener(new ActionListener() {
		     public void actionPerformed(ActionEvent ae) {
		        start.setEnabled(false);
		        start.setBackground(null);
		        pause.setEnabled(true);
				pause.setBackground(Color.YELLOW);
		        pause.setText("PAUSE");
		        stop.setEnabled(true);
				stop.setBackground(Color.RED);
		     }
		   }
		 );
		//p.add(start);
		
		//pause.addActionListener(this);
		pause.setBounds(170,230,100,40);
		pause.setEnabled(false);
		pause.addActionListener(new ActionListener() {
		     public void actionPerformed(ActionEvent ae) {
		        start.setEnabled(false);
		        pause.setEnabled(true);
		        if (pause.getText() == "PAUSE"){
		          pause.setText("UNPAUSE");
		        }else {
		          pause.setText("PAUSE");
		        }
		        stop.setEnabled(true);
		     }
		   }
		 );
	    //p.add(pause);
	    
		//stop.addActionListener(this);
		stop.setBounds(290,230,100,40);
		stop.setEnabled(false);
		stop.addActionListener(new ActionListener() {
		     public void actionPerformed(ActionEvent ae) {
		        start.setEnabled(true);
		        start.setBackground(Color.GREEN);
		        pause.setEnabled(false);
		        pause.setText("PAUSE");
		        pause.setBackground(null);
		        stop.setEnabled(false);
		        stop.setBackground(null);
		     }
		   }
		 );
	    //p.add(stop);
		
		p.add(parametersTitle);
		parametersTitle.setSize(200,20);
		parametersTitle.setLocation(50,300);
		parametersTitle.setFont(myFont);
		
		JScrollPane scroll = new JScrollPane(paramatersText);
	    scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    scroll.setBounds(50,340, 360,330);
		p.add(scroll);  
	    
		//update.addActionListener(this);
		update.setActionCommand("Update");
		update.setBounds(310,680, 100, 40);
		update.addActionListener(new ActionListener() {
		     public void actionPerformed(ActionEvent ae) {
		        try {
					UpdateParamsFile(paramatersText, mainParamsFile);          //Dayan
				} catch (IOException e) {
					e.printStackTrace();
				}
		     }
		   }
		 );
	    //p.add(update);
	    
		JLabel lastUpdateTitle = new JLabel("Genetic Programming Trader Status");
		JLabel price = new JLabel("$ Price");
		JLabel BTC = new JLabel("BTC");
		JLabel time = new JLabel("Time");
		
		lastUpdateTitle.setSize(320,20);
		lastUpdateTitle.setLocation(500,190);
		lastUpdateTitle.setFont(myFontTitle);
		p.add(lastUpdateTitle);
				
		lastPrice.setSize(150,30);
		lastPrice.setLocation(500,320);
		lastPrice.setFont(myFont);
		p.add(lastPrice);
		
		lastBTC.setSize(150,30);
		lastBTC.setLocation(660,320);
		lastBTC.setFont(myFont);
		lastBTC.setText("On Hold");
		p.add(lastBTC);
		
		lastTime.setSize(150,30);
		lastTime.setLocation(820,320);
		lastTime.setFont(myFont);
		lastTime.setText("HOLD");
		p.add(lastTime);
		
		JLabel iterativeAlgTitle = new JLabel("Iterative Algorithm Trader Status");
		
		iterativeAlgTitle.setSize(300,20);
		iterativeAlgTitle.setLocation(500,390);
		iterativeAlgTitle.setFont(myFontTitle);
		p.add(iterativeAlgTitle);
		
		accountBalancesTitles2.setLocation(500,415);
		accountBalancesTitles2.setSize(200,20);
		accountBalancesTitles2.setFont(myFont);
		p.add(accountBalancesTitles2);
		
		p.add(usdBalance2);
		usdBalance2.setLocation(500,445);
		usdBalance2.setSize(180,30);
		usdBalance2.setFont(myFontBalance);
		usdBalance2.setHorizontalAlignment(JTextField.CENTER);
		
		p.add(usdBtcEquivalent2);
		usdBtcEquivalent2.setLocation(500,480);
		usdBtcEquivalent2.setSize(180,20);
		usdBtcEquivalent2.setFont(myFont);
		
		p.add(btcBalance2);
		btcBalance2.setLocation(700,445);
		btcBalance2.setSize(180,30);
		btcBalance2.setFont(myFontBalance);
		btcBalance2.setHorizontalAlignment(JTextField.CENTER);
		
		p.add(btcUsdEquivalent2);
		btcUsdEquivalent2.setLocation(700,480);
		btcUsdEquivalent2.setSize(180,20);
		btcUsdEquivalent2.setFont(myFont);
		
		lastPrice2.setSize(150,30);
		lastPrice2.setLocation(500,520);
		lastPrice2.setFont(myFont);
		p.add(lastPrice2);
		
		status.setSize(150,30);
		status.setLocation(660,520);
		status.setFont(myFont);
		status.setText("On Hold");
		p.add(status);
		
		tradeAction.setSize(150,30);
		tradeAction.setLocation(820,520);
		tradeAction.setFont(myFont);
		tradeAction.setText("HOLD");
		p.add(tradeAction);
		
		JLabel buyholdTitle = new JLabel("Buy & Hold Returns");
		
		buyholdTitle.setSize(300,20);
		buyholdTitle.setLocation(500,590);
		buyholdTitle.setFont(myFontTitle);
		p.add(buyholdTitle);
		
		accountBalancesTitles3.setLocation(500,615);
		accountBalancesTitles3.setSize(300,20);
		accountBalancesTitles3.setFont(myFont);
		p.add(accountBalancesTitles3);
		
		p.add(btcBalance3);
		btcBalance3.setLocation(500,645);
		btcBalance3.setSize(180,30);
		btcBalance3.setFont(myFontBalance);
		btcBalance3.setHorizontalAlignment(JTextField.CENTER);
		
		p.add(btcUsdEquivalent3);
		btcUsdEquivalent3.setLocation(500,680);
		btcUsdEquivalent3.setSize(180,20);
		btcUsdEquivalent3.setFont(myFont);
		
		
		//add(p);
		
	}
	
	public static void UpdateParamsFile(JTextArea paramatersText, String file) throws IOException  //Dayan
	{
		try {
			String[] lines = paramatersText.getText().split("\\n"); 
			File params = new File(file);
 
			if (!params.exists()) {
				params.createNewFile();
			}
 
			FileWriter fw = new FileWriter(params.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			
			for(String line : lines) {
				bw.write(line + "\n");
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
}
