
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import bitcoinUI.BuildInterface;

public class BasicSwing extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	public static JPanel p = new JPanel();
	
	public final static Object monitor = new Object();
	
	public static BasicSwing parent;
	public static String[] ARGS;
	
	//Thread booleans for synchronization
	public static boolean thread1 = false;
	public static boolean thread2 = false;
	public static boolean thread3 = false;
	public static boolean thread4 = false;
	public static boolean allThreads = false;
	
	//Booleans for stop and pause
	public static boolean STOP = false;
	public static boolean PAUSE = true;
	
	public static JTextArea marketsHeaderBitstamp = new JTextArea("Loading Market Data...");
	public static JTextArea marketsHeaderOKCoin = new JTextArea("Loading Market Data...");
	public static JTextArea marketsHeaderBitfinex = new JTextArea("Loading Market Data...");
	public static JTextArea marketsHeaderBtcE = new JTextArea("Loading Market Data...");
	
	//PARAM TEXT BOX
	//public static JTextArea paramatersText = new JTextArea(5,20);
	
	public static JButton start = new JButton("START");
	public static JButton pause = new JButton("PAUSE");
	public static JButton stop = new JButton("STOP/RESET");
	public static JButton update = new JButton("UPDATE");
	public static JButton reRunGP = new JButton("RUN GP");
	
	public static JTextField lastPrice = new JTextField();
	public static JTextField lastBTC = new JTextField();
	public static JTextField lastTime = new JTextField();
	public static JTextField numTrades = new JTextField();
	public static int numTradeInt = 0;
	
	public static JTextField usdBalance = new JTextField("Loading...");
	public static JLabel usdBtcEquivalent = new JLabel("Loading...");
	public static JTextField btcBalance = new JTextField("Loading...");
	public static JLabel btcUsdEquivalent = new JLabel("Loading...");
	
	public static int currentMarketTrading = 0;
	public static double currentMarketPrice = 0;
	public static double currentUSDBalance = 0;
	public static double currentBTCBalance = 0;
	public static double oldUSDBalance = 0.0;
	public static double oldBTCBalance = 0.0;
	
	//The Balances for the Iterative Algorithm
	public static double currentUSDBalance2 = 0;
	public static double currentBTCBalance2 = 0;
	public static double oldUSDBalance2 = 0.0;
	public static double oldBTCBalance2 = 0.0;
	
	//The UI Objects for the Iterative Algorithm
	public static JTextField usdBalance2 = new JTextField("Loading...");
	public static JLabel usdBtcEquivalent2 = new JLabel("Loading...");
	public static JTextField btcBalance2 = new JTextField("Loading...");
	public static JLabel btcUsdEquivalent2 = new JLabel("Loading...");
	
	public static JTextField lastPrice2 = new JTextField();
	public static JTextField status = new JTextField();
	public static JTextField tradeAction = new JTextField();
	public static JTextField numTrades2 = new JTextField();
	public static int numTrade2Int = 0;
	
	//The UI Objects for the Iterative Algorithm
	public static JTextField btcBalance3 = new JTextField("0.00000000 BTC");
	public static JLabel btcUsdEquivalent3 = new JLabel("Loading...");
		
	//IterativeMethod Variables
	public static ArrayList<Double> bitStampRecords = new ArrayList<Double>();
	public static ArrayList<Double> okCoinRecords = new ArrayList<Double>();
	public static double totalDiff;
	public static double averageDifferenceBSOK = 5;
	public static double averageDifferenceBSBF = 5;
	public static double averageDifferenceBSBT = 5;
	
	public static String currentHomeMarketPrice = "";
	
	//BuyHold Variables
	public static double buyHoldBTCBalance;
	public static boolean buyHoldCalculated = false;
	
	//These Linked Lists hold the historic price data. LIFO when reaches size of 240+
	static LinkedList<Double> bitstampList;
	static LinkedList<Double> bitfinexList;
	static LinkedList<Double> btceList;
	static LinkedList<Double> okcoinList;
	
	public static String[] bitstampArray;
	public static String[] okcoinArray;
	public static String[] bitfinexArray;
	public static String[] btceArray;
	
	//Popup Icons
	ImageIcon icon;
	
	public static void main(String[] args) throws Exception
	{	
		System.out.println("Calculating Historic Average Difference");
		System.out.println("Loading...");
		bitstampArray = DB.readFromFile("bitstampEvery10Seconds.txt");
		okcoinArray = DB.readFromFile("okcoinEvery10Seconds.txt");
		bitfinexArray = DB.readFromFile("bitfinexEvery10Seconds.txt");
		btceArray = DB.readFromFile("btceEvery10Seconds.txt");
		
		averageDifferenceBSOK = RuleCalc.calculateHistoricAverageDifference(bitstampArray, okcoinArray);
		averageDifferenceBSBF = RuleCalc.calculateHistoricAverageDifference(bitstampArray, bitfinexArray);
		averageDifferenceBSBT = RuleCalc.calculateHistoricAverageDifference(bitstampArray, btceArray);
		
		System.out.println("Loading Balances.");
		DB.readBalancesFromFile();
		
		System.out.println("Building LinkedList's of Historic Data");
		System.out.println("Loading...");
		bitstampList = DB.buildLinkedList(bitstampArray);
		okcoinList = DB.buildLinkedList(okcoinArray);
		bitfinexList = DB.buildLinkedList(bitfinexArray);
		btceList = DB.buildLinkedList(btceArray);
		
		System.out.println("Building GP Databases");
		DB.BuildGPDatabase();
		
		System.out.println("Spawning Threads");
		ArrayList<Thread> threads = new ArrayList<Thread>();
		threads.add(new Thread(new bitThread("https://www.bitstamp.net/api/ticker/",marketsHeaderBitstamp,"bitstampHistoricData.txt",new int[]{2,1,3,7,5},true,"bitstamp")));
		threads.add(new Thread(new bitThread("https://api.bitfinex.com/v1/ticker/btcusd",marketsHeaderBitfinex,"bitFinexHistoricalData.txt",new int[]{4,3,1,2,-1},false,"bitfinex")));
		threads.add(new Thread(new bitThread("https://btc-e.com/api/2/btc_usd/ticker",marketsHeaderBtcE,"btcEHistoricalData.txt",new int[]{9,5,6,7,3},false,"btce")));
		threads.add(new Thread(new bitThread("https://www.okcoin.com/api/ticker.do?ok=1",marketsHeaderOKCoin,"okcoinHistoricData.txt",new int[]{0,3,1,5,6},false,"okcoin")));
		
		for(int i = 0; i<threads.size(); i++){
			threads.get(i).start();
		}
		
		parent = new BasicSwing();
		ARGS = args;
		startMainLoop(ARGS);
	}
	
	public static void startMainLoop(String[] args) throws Exception 
	{
		System.out.println("STARTING MAIN LOOP");
		long startTime = System.currentTimeMillis();
		int loopCounter = 0;
		while(!STOP){
			loopCounter++;
			if(currentMarketPrice != 0 && loopCounter%20000 == 0){
				UIFunct.updateBalanceStatusBoard(currentMarketPrice+"");
			}
			System.out.print("");
			if (thread1 && thread2 && thread3 && thread4){
				synchronized(monitor) {
					monitor.notifyAll();
				}
				
				long currentTime = System.currentTimeMillis();
				double threeMonths = 7884000000.0;
				if((currentTime-startTime) >= threeMonths){
					RestartGPPopUp(args, parent, new ImageIcon("popupicon.png"));
				}
			}
		}
		System.out.println("STOPPING MAIN LOOP");
	}
	
	public BasicSwing() throws Exception
	{
		super();
		setSize(1000,800);
		setResizable(false);
		p.setLayout(null);
		
		this.setTitle("BitCoin Trader - Developed by Karim Hamasni & Dayan Balevski");
		
		BuildInterface.buildUserInterface(marketsHeaderBitstamp, marketsHeaderOKCoin, marketsHeaderBitfinex, marketsHeaderBtcE, start, pause, stop, update, lastPrice, lastBTC, lastTime, usdBalance, usdBtcEquivalent, btcBalance, btcUsdEquivalent, currentMarketTrading, p, usdBalance2, usdBtcEquivalent2, btcBalance2, btcUsdEquivalent2, lastPrice2, status, tradeAction, btcBalance3, btcUsdEquivalent3, numTrades, numTrades2);
		start.addActionListener(this);
		start.setBounds(50,230,100,40);
		start.setEnabled(true);
		start.setBackground(Color.GREEN);
		start.setOpaque(true);
		start.addActionListener(new ActionListener() {
		     public void actionPerformed(ActionEvent ae) {
		    	if (STOP == true) {
		    		STOP = false;
		    		try {
						startMainLoop(ARGS);
					} catch (Exception e) {
						e.printStackTrace();
					}
		    	}
		    	start.setEnabled(false);
		        pause.setEnabled(true);
				pause.setBackground(Color.YELLOW);
		        pause.setText("PAUSE");
		        stop.setEnabled(true);
				stop.setBackground(Color.RED);
				PAUSE = false;
		     }
		   }
		 );
		p.add(start);
		
		pause.addActionListener(this);
		pause.setBounds(170,230,100,40);
		pause.setEnabled(false);
		pause.setBackground(Color.YELLOW);
		pause.setOpaque(true);
		pause.addActionListener(new ActionListener() {
		     public void actionPerformed(ActionEvent ae) {
		        PAUSE = true;
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
		stop.setBackground(Color.RED);
		stop.setOpaque(true);
		stop.addActionListener(new ActionListener() {
		     public void actionPerformed(ActionEvent ae) {
		        start.setEnabled(true);
		        start.setOpaque(true);
		        pause.setEnabled(false);
		        pause.setText("PAUSE");
		        stop.setEnabled(false);
		        STOP = true;
		        UIFunct.defaultBalances();
		     }
		   }
		 );
	    p.add(stop);

	    reRunGP.addActionListener(this);
	    reRunGP.setActionCommand("RUN GP");
		reRunGP.setBounds(50,620, 100, 40);
		reRunGP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae){
				JOptionPane.showMessageDialog(parent, "Trading will be paused as the GP Program Runs. This can take a long time!","GP Run Warning", JOptionPane.WARNING_MESSAGE);
				try {
					Restart(ARGS);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		p.add(reRunGP);

		update.addActionListener(this);
	    p.add(update);

	    lastPrice.setText("Load..");
		lastPrice2.setText("Load..");
		
		add(p);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {	}
	
	public static void Restart(String[] args) throws InterruptedException, IOException{
		STOP = true;
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
	
	@SuppressWarnings("static-access")
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
	
	public static void RestartGPPopUp(String[] args, JFrame parent, ImageIcon icon) throws InterruptedException, IOException{
        
		int choice = JOptionPane.showConfirmDialog(parent, "Your current GP Rule Program has been running for a while. Would you like to refresh it or no to continue?", "Run the GP Program?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon); //(this, "Your current GP Rule Program has been running for a while. Would you like to refresh it or continue?", null);
        if(choice == 0){
        	PAUSE = true;
        	Restart(args);
        }
        else{
        	
        }
	}
}
