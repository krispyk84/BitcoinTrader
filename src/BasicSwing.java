
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

import bitcoinUI.BuildInterface;
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
	public static double oldUSDBalance = 0.0;
	public static double oldBTCBalance = 0.0;
	
	//The Balances for the Iterative Algorithm
	public static double currentUSDBalance2 = 1000.00;
	public static double currentBTCBalance2 = 0.00000000;
	public static double oldUSDBalance2 = 0.0;
	public static double oldBTCBalance2 = 0.0;
	
	public static double currentBistampPrice;
	public static double currentBitfinexPrice;
	public static double currentBTCEPrice;
	public static double currentOKCoinPrice;
	
	//The UI Objects for the Iterative Algorithm
	public static JLabel accountBalancesTitles2 = new JLabel("Account Balances");
	public static JTextField usdBalance2 = new JTextField("$1000.00 USD");
	public static JLabel usdBtcEquivalent2 = new JLabel("Loading...");
	public static JTextField btcBalance2 = new JTextField("0.00000000 BTC");
	public static JLabel btcUsdEquivalent2 = new JLabel("Loading...");
	
	public static JTextField lastPrice2 = new JTextField();
	public static JTextField status = new JTextField();
	public static JTextField tradeAction = new JTextField();
	
	//The UI Objects for the Iterative Algorithm
	public static JLabel accountBalancesTitles3 = new JLabel("Current Bitcoin Balance and Value");
	public static JTextField btcBalance3 = new JTextField("0.00000000 BTC");
	public static JLabel btcUsdEquivalent3 = new JLabel("Loading...");
		
	//IterativeMethod Variables
	public static ArrayList<Double> bitStampRecords = new ArrayList<Double>();
	public static ArrayList<Double> okCoinRecords = new ArrayList<Double>();
	public static double totalDiff;
	
	//BuyHold Variables
	public static double buyHoldBTCBalance;
	
	public static void main(String[] args) throws Exception
	{		
		System.out.println("Spawning Threads");
		ArrayList<Thread> threads = new ArrayList<Thread>();
		
		threads.add(new Thread(new bitThread("https://www.bitstamp.net/api/ticker/",marketsHeaderBitstamp,"bitstampHistoricData.txt",new int[]{2,1,3,7,5},true,"bitstamp")));
		threads.add(new Thread(new bitThread("https://api.bitfinex.com/v1/ticker/btcusd",marketsHeaderBitfinex,"bitFinexHistoricalData.txt",new int[]{4,3,1,2,-1},false,"bitfinex")));
		threads.add(new Thread(new bitThread("https://btc-e.com/api/2/btc_usd/ticker",marketsHeaderBtcE,"btcEHistoricalData.txt",new int[]{9,5,6,7,3},false,"btce")));
		threads.add(new Thread(new bitThread("https://www.okcoin.com/api/ticker.do?ok=1",marketsHeaderOKCoin,"okcoinHistoricData.txt",new int[]{0,3,1,5,6},false,"okcoin")));
		
		for(int i = 0; i<threads.size(); i++){
			threads.get(i).start();
		}
		
		mainParamsFile = "bitcoinTrader.params"; //Dayan
		
		BasicSwing parent = new BasicSwing();
		ReadParamsFile("bitcoinTrader.params");  //Dayan
		
		
		Thread.sleep(20000);
		System.out.println(currentBistampPrice);
		
		
		
		//RestartGPPopUp(args, parent);//Keep this for later....
		
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
		
		BuildInterface.buildUserInterface(marketSectionTitle, parametersTitle, marketsHeaderBitstamp, marketsHeaderOKCoin, marketsHeaderBitfinex, marketsHeaderBtcE, paramatersText, controlsTitle, start, pause, stop, update, lastPrice, lastBTC, lastTime, accountBalancesTitles, usdBalance, usdBtcEquivalent, btcBalance, btcUsdEquivalent, currentMarketTrading, p, mainParamsFile, accountBalancesTitles2, usdBalance2, usdBtcEquivalent2, btcBalance2, btcUsdEquivalent2, lastPrice2, status, tradeAction, accountBalancesTitles3, btcBalance3, btcUsdEquivalent3);
		start.addActionListener(this);
		p.add(start);
		pause.addActionListener(this);
	    p.add(pause);
		stop.addActionListener(this);
	    p.add(stop);
		update.addActionListener(this);
	    p.add(update);
		

		lastPrice.setText("TEST");
		
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
