
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import ec.app.bitcoinTrader.FinancialFunctions;

public class BasicSwing extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	JPanel p = new JPanel();
	
	public static String mainParamsFile = "bitcoinTrader.params";
	
	public static JTextArea marketsHeaderBitstamp = new JTextArea("Loading Market Data...");
	public static JTextArea marketsHeaderOKCoin = new JTextArea("Loading Market Data...");
	public static JTextArea marketsHeaderBitfinex = new JTextArea("Loading Market Data...");
	public static JTextArea marketsHeaderBtcE = new JTextArea("Loading Market Data...");
	public static JLabel marketSectionTitle = new JLabel("Market Snapshot");
	
	public static JLabel parametersTitle = new JLabel("Modify GP parameters");
	public static JTextArea paramatersText = new JTextArea(5,20);
	
	public static JLabel controlsTitle = new JLabel("Controls");
	public static JButton start = new JButton("START");
	public static JButton pause = new JButton("PAUSE");
	public static JButton stop = new JButton("STOP");
	public static JButton update = new JButton("UPDATE");
	
	public static JTextField lastPrice = new JTextField();
	public static JTextField lastBTC = new JTextField();
	public static JTextField lastTime = new JTextField();
	
	public static JLabel accountBalancesTitles = new JLabel("Account Balances");
	public static JTextField usdBalance = new JTextField("$1000.00 USD");
	public static JLabel usdBtcEquivalent = new JLabel("Loading...");
	public static JTextField btcBalance = new JTextField("0.00000000 BTC");
	public static JLabel btcUsdEquivalent = new JLabel("Loading...");
	
	public static int currentMarketTrading = 0;
	public static double currentMarketPrice = 0;
	public static double currentUSDBalance = 1000.00;
	public static double currentBTCBalance = 0.00000000;
	
	public static void main(String[] args) throws Exception
	{		
		
		/*
		ec.Evolve gpProg1 = new ec.Evolve();
		ec.Evolve gpProg2 = new ec.Evolve();
		ec.Evolve gpProg3 = new ec.Evolve();
		ec.Evolve gpProg4 = new ec.Evolve();
		ec.Evolve gpProg5 = new ec.Evolve();
		ec.Evolve gpProg6 = new ec.Evolve();
		ec.Evolve gpProg7 = new ec.Evolve();
		ec.Evolve gpProg8 = new ec.Evolve();
		ec.Evolve gpProg9 = new ec.Evolve();
		ec.Evolve gpProg10 = new ec.Evolve();
		
		gpProg1.main(args, "bitcoinTrader.params");
		gpProg2.main(args, "bitcoinTrader1.params");
		gpProg3.main(args, "bitcoinTrader2.params");
		gpProg4.main(args, "bitcoinTrader3.params");
		gpProg1.main(args, "bitcoinTrader4.params");
		gpProg2.main(args, "bitcoinTrader5.params");
		gpProg3.main(args, "bitcoinTrader6.params");
		gpProg4.main(args, "bitcoinTrader7.params");
		gpProg1.main(args, "bitcoinTrader8.params");
		gpProg2.main(args, "bitcoinTrader9.params");
		gpProg3.main(args, "bitcoinTrader10.params");
		*/
		
		
		System.out.println("Spawning Threads");
		ArrayList<Thread> threads = new ArrayList<Thread>();
		
		threads.add(new Thread(new bitThread("https://www.bitstamp.net/api/ticker/",marketsHeaderBitstamp,"bitstampHistoricData.txt",new int[]{2,1,3,7,5},true)));
		threads.add(new Thread(new bitThread("https://api.bitfinex.com/v1/ticker/btcusd",marketsHeaderBitfinex,"bitFinexHistoricalData.txt",new int[]{4,3,1,2,-1},false)));
		threads.add(new Thread(new bitThread("https://btc-e.com/api/2/btc_usd/ticker",marketsHeaderBtcE,"btcEHistoricalData.txt",new int[]{9,5,6,7,3},false)));
		threads.add(new Thread(new bitThread("https://www.okcoin.com/api/ticker.do?ok=1",marketsHeaderOKCoin,"okcoinHistoricData.txt",new int[]{0,3,1,5,6},false)));
		
		for(int i = 0; i<threads.size(); i++){
			threads.get(i).start();
		}
		
		mainParamsFile = "bitcoinTrader.params"; //Dayan
		
		BasicSwing parent = new BasicSwing();
		ReadParamsFile("bitcoinTrader.params");  //Dayan
		Thread.sleep(20000);
		RestartGPPopUp(args, parent);
		
		//ec.Evolve gpProg = new ec.Evolve();
		//gpProg.main(args);
		
	}
	
	public static void ReadParamsFile(String file) throws IOException   //Dayan
	{
		FileInputStream fstream = new FileInputStream(file);
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

		String strLine;

		while ((strLine = br.readLine()) != null)   {
		  paramatersText.append(strLine+"\n");
		}
		br.close();
	}
	
	public static void UpdateParamsFile(String file) throws IOException  //Dayan
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
	
	public BasicSwing() throws Exception
	{
		super();
		setSize(1000,800);
		setResizable(false);
		p.setLayout(null);
		
		JPanel marketSnapshot = new JPanel(new FlowLayout()); // new FlowLayout not needed
		marketSnapshot.setOpaque(false);
		 /* JButton button = new JButton("OK");
		  //button.setSize(80, 200);
		  button.setPreferredSize(new Dimension(80, 200)); // ?? I don't like this.
		  button.setFont(new Font("Arial", 1, 40));
		  marketSnapshot.add(button);*/
		
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
		accountBalancesTitles.setLocation(500,190);
		accountBalancesTitles.setSize(200,20);
		accountBalancesTitles.setFont(myFont);
		
	    p.add(usdBalance);
		usdBalance.setLocation(500,230);
		usdBalance.setSize(180,30);
		Font myFontBalance = new Font("Helvetica", Font.BOLD, 22);
		usdBalance.setFont(myFontBalance);
		usdBalance.setHorizontalAlignment(JTextField.CENTER);
		
		p.add(usdBtcEquivalent);
		usdBtcEquivalent.setLocation(500,265);
		usdBtcEquivalent.setSize(180,20);
		usdBtcEquivalent.setFont(myFont);
		
		p.add(btcBalance);
		btcBalance.setLocation(700,230);
		btcBalance.setSize(180,30);
		btcBalance.setFont(myFontBalance);
		btcBalance.setHorizontalAlignment(JTextField.CENTER);
		
		p.add(btcUsdEquivalent);
		btcUsdEquivalent.setLocation(700,265);
		btcUsdEquivalent.setSize(180,20);
		btcUsdEquivalent.setFont(myFont);
		
		p.add(controlsTitle);
		controlsTitle.setLocation(50,190);
		controlsTitle.setSize(200,20);
		controlsTitle.setFont(myFont);
	
		start.addActionListener(this);
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
		p.add(start);
		
		pause.addActionListener(this);
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
	    p.add(pause);
	    
		stop.addActionListener(this);
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
	    p.add(stop);
		
		p.add(parametersTitle);
		parametersTitle.setSize(200,20);
		parametersTitle.setLocation(50,300);
		parametersTitle.setFont(myFont);
		
		JScrollPane scroll = new JScrollPane(paramatersText);
	    scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    scroll.setBounds(50,340, 360,330);
		p.add(scroll);  
	    
		update.addActionListener(this);
		update.setActionCommand("Update");
		update.setBounds(310,680, 100, 40);
		update.addActionListener(new ActionListener() {
		     public void actionPerformed(ActionEvent ae) {
		        try {
					UpdateParamsFile(mainParamsFile);          //Dayan
				} catch (IOException e) {
					e.printStackTrace();
				}
		     }
		   }
		 );
	    p.add(update);
	    
		JLabel lastUpdateTitle = new JLabel("Last update from current market");
		JLabel price = new JLabel("$ Price");
		JLabel BTC = new JLabel("BTC");
		JLabel time = new JLabel("Time");
		
		lastUpdateTitle.setSize(300,20);
		lastUpdateTitle.setLocation(500,300);
		lastUpdateTitle.setFont(myFont);
		p.add(lastUpdateTitle);
				
		lastPrice.setSize(150,30);
		lastPrice.setLocation(500,340);
		lastPrice.setFont(myFont);
		p.add(lastPrice);
		
		lastBTC.setSize(150,30);
		lastBTC.setLocation(660,340);
		lastBTC.setFont(myFont);
		p.add(lastBTC);
		
		lastTime.setSize(150,30);
		lastTime.setLocation(820,340);
		lastTime.setFont(myFont);
		p.add(lastTime);
		
		add(p);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public static void Restart(String[] args) throws InterruptedException, IOException{
		RunGP(args);
		
		Thread.sleep(5000);
		RecompileGPProgram();
		
		StringBuilder cmd = new StringBuilder();
        cmd.append(System.getProperty("java.home") + File.separator + "bin" + File.separator + "java ");
        for (String jvmArg : ManagementFactory.getRuntimeMXBean().getInputArguments()) {
            cmd.append(jvmArg + " ");
        }
        cmd.append("-cp ").append(ManagementFactory.getRuntimeMXBean().getClassPath()).append(" ");
        cmd.append(BasicSwing.class.getName()).append(" ");
        
        Thread.sleep(5000);
        
        Runtime.getRuntime().exec(cmd.toString());
        System.exit(0);
	}
	
	public static void RunGP(String[] args){
		ec.Evolve gpProg1 = new ec.Evolve();
		gpProg1.main(args, "bitcoinTrader.params");
	}
	
	public static void RecompileGPProgram(){
		String fileToCompile = "src" + java.io.File.separator + "ec" + java.io.File.separator + "MathSolution.java";//"+ec.VarHolder.AlgorithmNumber+".java";

		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

		int compilationResult = compiler.run(null, null, null, fileToCompile);

		if(compilationResult == 0){
            System.out.println("Compilation is successful");
        }else{
            System.out.println("Compilation Failed");
        }
	}
	
	public static void RestartGPPopUp(String[] args, JFrame parent) throws InterruptedException, IOException{
        int choice = JOptionPane.showConfirmDialog(parent, "Your current GP Rule Program has been running for a while. Would you like to refresh it or continue?"); //(this, "Your current GP Rule Program has been running for a while. Would you like to refresh it or continue?", null);
        if(choice == 0){
        	Restart(args);
        }
        else{
        	
        }
	}
}
