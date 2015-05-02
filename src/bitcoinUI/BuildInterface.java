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
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;


public class BuildInterface {
	
	

	public static void buildUserInterface(JTextArea marketsHeaderBitstamp, JTextArea marketsHeaderOKCoin, 
			JTextArea marketsHeaderBitfinex, JTextArea marketsHeaderBtcE, 
			JButton start, JButton pause, JButton stop, JButton update,
			JTextField lastPrice, JTextField lastBTC, JTextField lastTime,
			JTextField usdBalance, JLabel usdBtcEquivalent, JTextField btcBalance, JLabel btcUsdEquivalent,
			int currentMarketTrading, JPanel p, JTextField usdBalance2,
			JLabel usdBtcEquivalent2, JTextField btcBalance2, JLabel btcUsdEquivalent2, JTextField lastPrice2, JTextField status, JTextField tradeAction, JTextField btcBalance3, JLabel btcUsdEquivalent3) throws IOException{
		JPanel marketSnapshot = new JPanel(new FlowLayout()); // new FlowLayout not needed
		marketSnapshot.setOpaque(false);
		 
		String[] paramVals = new String[7]; 
		int i = 0;
		for (String line : Files.readAllLines(Paths.get("currentParamValues.txt"))) {
		    paramVals[i] = line;
		    i++;
		}
		
		Font myFontTitle = new Font("Helvetica", Font.BOLD, 18);
		
		JLabel marketSectionTitle = new JLabel("Market Snapshot");
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
		
		JLabel accountBalancesTitles = new JLabel("Account Balances");
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
		
		JLabel controlsTitle = new JLabel("Controls");
		p.add(controlsTitle);
		controlsTitle.setLocation(50,190);
		controlsTitle.setSize(200,20);
		controlsTitle.setFont(myFont);
	

		JLabel parametersTitle = new JLabel("Modify GP parameters");
		p.add(parametersTitle);
		parametersTitle.setSize(200,20);
		parametersTitle.setLocation(50,300);
		parametersTitle.setFont(myFont);

		JLabel lblPopSize = new JLabel("Population Size", JLabel.TRAILING);
		p.add(lblPopSize);
		JTextField txtPopSize = new JTextField(10);
		p.add(txtPopSize);
		txtPopSize.setSize(180,20);
		lblPopSize.setSize(100,20);
		lblPopSize.setLabelFor(txtPopSize);
		lblPopSize.setLocation(80,340);
		txtPopSize.setLocation(190,340);
		txtPopSize.setHorizontalAlignment(JTextField.CENTER);
		txtPopSize.setText(paramVals[0]);
		
		JLabel lblTrainSize = new JLabel("Training Set Size", JLabel.TRAILING);
		p.add(lblTrainSize);
		JTextField txtTrainSize = new JTextField(10);
		p.add(txtTrainSize);
		txtTrainSize.setSize(180,20);
		lblTrainSize.setSize(120,20);
		lblTrainSize.setLabelFor(txtTrainSize);
		lblTrainSize.setLocation(60,380);
		txtTrainSize.setLocation(190,380);
		txtTrainSize.setHorizontalAlignment(JTextField.CENTER);
		txtTrainSize.setText(paramVals[1]);
		
		JLabel lblGenerations = new JLabel("Generations", JLabel.TRAILING);
		p.add(lblGenerations);
		JTextField txtGenerations = new JTextField(10);
		p.add(txtGenerations);
		txtGenerations.setSize(180,20);
		lblGenerations.setSize(100,20);
		lblGenerations.setLabelFor(txtGenerations);
		lblGenerations.setLocation(80,420);
		txtGenerations.setLocation(190,420);
		txtGenerations.setHorizontalAlignment(JTextField.CENTER);
		txtGenerations.setText(paramVals[2]);
		
		JLabel lblTournSize = new JLabel("Tournament Size", JLabel.TRAILING);
		p.add(lblTournSize);
		JTextField txtTournSize = new JTextField(10);
		p.add(txtTournSize);
		txtTournSize.setSize(180,20);
		lblTournSize.setSize(120,20);
		lblTournSize.setLabelFor(txtTournSize);
		lblTournSize.setLocation(60,460);
		txtTournSize.setLocation(190,460);
		txtTournSize.setHorizontalAlignment(JTextField.CENTER);
		txtTournSize.setText(paramVals[3]);
		
		JLabel lblNumElite = new JLabel("Number of Elites", JLabel.TRAILING);
		p.add(lblNumElite);
		JTextField txtNumElite = new JTextField(10);
		p.add(txtNumElite);
		txtNumElite.setSize(180,20);
		lblNumElite.setSize(120,20);
		lblNumElite.setLabelFor(txtNumElite);
		lblNumElite.setLocation(60,500);
		txtNumElite.setLocation(190,500);
		txtNumElite.setHorizontalAlignment(JTextField.CENTER);
		txtNumElite.setText(paramVals[4]);
		
		JLabel lblCrossProb = new JLabel("Crossover Probability", JLabel.TRAILING);
		p.add(lblCrossProb);
		JTextField txtCrossProb = new JTextField(10);
		p.add(txtCrossProb);
		txtCrossProb.setSize(180,20);
		lblCrossProb.setSize(135,20);
		lblCrossProb.setLabelFor(txtCrossProb);
		lblCrossProb.setLocation(45,540);
		txtCrossProb.setLocation(190,540);
		txtCrossProb.setHorizontalAlignment(JTextField.CENTER);
		txtCrossProb.setText(paramVals[5]);
		
		JLabel lblMuteProb = new JLabel("Mutation Probability", JLabel.TRAILING);
		p.add(lblMuteProb);
		JTextField txtMuteProb = new JTextField(10);
		p.add(txtMuteProb);
		txtMuteProb.setSize(180,20);
		lblMuteProb.setSize(130,20);
		lblMuteProb.setLabelFor(txtMuteProb);
		lblMuteProb.setLocation(50,580);
		txtMuteProb.setLocation(190,580);
		txtMuteProb.setHorizontalAlignment(JTextField.CENTER);
		txtMuteProb.setText(paramVals[6]);
		
		/*
		JScrollPane scroll = new JScrollPane(paramatersText);
	    scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    scroll.setBounds(50,340, 360,330);
		p.add(scroll);  
	    */
		//update.addActionListener(this);
		update.setActionCommand("Update");
		update.setBounds(270,620, 100, 40);
		update.addActionListener(new ActionListener() {
		     public void actionPerformed(ActionEvent ae) {
		        try {
					UpdateParamsFile(txtPopSize, txtTrainSize, txtGenerations, txtTournSize, txtNumElite, txtCrossProb, txtMuteProb);          //Dayan
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
		
		JLabel accountBalancesTitles2 = new JLabel("Account Balances");
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
		
		JLabel accountBalancesTitles3 = new JLabel("Current Bitcoin Balance and Value");
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
	
	public static void UpdateParamsFile(JTextField txtPopSize, JTextField txtTrainSize, JTextField txtGenerations, JTextField txtTournSize, JTextField txtNumElite, JTextField txtCrossProb, JTextField txtMuteProb) throws IOException  //Dayan
	{
		try {
			File paramDB = new File("currentParamValues.txt");

			FileWriter fw2 = new FileWriter(paramDB.getAbsoluteFile());
			BufferedWriter bw2 = new BufferedWriter(fw2);
			
			//Set String Values
			String strPopSize = "eval.problem.size = " + txtPopSize.getText() + "\n";
			bw2.write(txtPopSize.getText() + "\n");
			String strTrainSize = "eval.problem.trainingSetSize = " + txtTrainSize.getText() + "\n";
			bw2.write(txtTrainSize.getText() + "\n");
			String strGeneration = "generations = " + txtGenerations.getText() + "\n";
			bw2.write(txtGenerations.getText() + "\n");
			String strTournSize = "select.tournament.size = " + txtTournSize.getText() + "\n";
			bw2.write(txtTournSize.getText() + "\n");
			String strNumElite = "breed.elite.0 = " + txtNumElite.getText() + "\n";
			bw2.write(txtNumElite.getText() + "\n");
			String strCrossProb = "pop.subpop.0.species.crossover-prob = " + txtCrossProb.getText() + "\n";
			bw2.write(txtCrossProb.getText() + "\n");
			String strMuteProb = "pop.subpop.0.species.mutation-prob = " + txtMuteProb.getText() + "\n";
			bw2.write(txtMuteProb.getText() + "\n");
			bw2.close();
			File params = new File("src" + java.io.File.separator + "ec" + java.io.File.separator + "app" + java.io.File.separator + "bitcoinTrader" + java.io.File.separator + "bitcoinTrader.params");
			
			
			if (!params.exists()) {
				params.createNewFile();
			}
			
			FileWriter fw = new FileWriter(params.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);

			
			
			for (String line : Files.readAllLines(Paths.get("basebitcoinTrader.params"))) {
				bw.write(line + "\n");
			}
			bw.write(strPopSize);
			bw.write(strTrainSize);
			bw.write(strGeneration);
			bw.write(strTournSize);
			bw.write(strNumElite);
			bw.write(strCrossProb);
			bw.write(strMuteProb);

			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
}
