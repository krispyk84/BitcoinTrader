import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;


public class DB {

	//Makes the API Call for market info to a URL market address
	public static String readUrl(String urlString) throws Exception {
	    BufferedReader reader = null;
	    try {
	        URL url = new URL(urlString);
	        //added this connection test
	        URLConnection stream = url.openConnection();
	        reader = new BufferedReader(new InputStreamReader(stream.getInputStream()));
	        StringBuffer buffer = new StringBuffer();
	        int read;
	        char[] chars = new char[1024];
	        while ((read = reader.read(chars)) != -1)
	            buffer.append(chars, 0, read); 

	        return buffer.toString();
	    } catch (MalformedURLException e) { 
	        // new URL() failed
	    	System.err.println("Malformed URL: "+e);
	    	Thread.sleep(1000); //retry connection after 1 second
	    	readUrl(urlString);
			return null; 
	    } 
	    catch (IOException e) {   
	        // openConnection() failed
	    	System.err.println("Connection Error: "+e);
	    	Thread.sleep(1000); //retry connection after 1 second
	    	readUrl(urlString);
			return null; 
	    } 
	    finally {
	        if (reader != null)
	            reader.close();
	    }
	}
	
	
	//Reads in the trade history from market text files
	public static String[] readFromFile(String filename) throws IOException{
		String[] toReturn = new String[countLines(filename)];
		try {
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(new FileReader(filename));
			int i = 0;
			String line = "";
			while ((line = br.readLine()) != null && i < toReturn.length) {
				toReturn[i] = line;
				i++;
			}
		} catch (IOException e) {
		}
		return toReturn;
	}
	
	public static int countLines(String filename) throws IOException {
	    InputStream is = new BufferedInputStream(new FileInputStream(filename));
	    try {
	        byte[] c = new byte[1024];
	        int count = 0;
	        int readChars = 0;
	        boolean empty = true;
	        while ((readChars = is.read(c)) != -1) {
	            empty = false;
	            for (int i = 0; i < readChars; ++i) {
	                if (c[i] == '\n') {
	                    ++count;
	                }
	            }
	        }
	        return (count == 0 && !empty) ? 1 : count;
	    } finally {
	        is.close();
	    }
	}
	
	//Reads the stored current balances into the program to set some variables
	public static void readBalancesFromFile() throws IOException{
		String[] toReturn = new String[8];
		try {
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(new FileReader("balances.txt"));
			int i = 0;
			String line = "";
			while ((line = br.readLine()) != null && i < toReturn.length) {
				toReturn[i] = line;
				i++;
			}
		} catch (IOException e) {
		}
		
		BasicSwing.currentUSDBalance = Double.parseDouble(toReturn[0]);
		BasicSwing.oldUSDBalance = Double.parseDouble(toReturn[1]);
		BasicSwing.currentBTCBalance = Double.parseDouble(toReturn[2]);
		BasicSwing.oldBTCBalance = Double.parseDouble(toReturn[3]);
		BasicSwing.currentUSDBalance2 = Double.parseDouble(toReturn[4]);
		BasicSwing.oldUSDBalance2 = Double.parseDouble(toReturn[5]);
		BasicSwing.currentBTCBalance2 = Double.parseDouble(toReturn[6]);
		BasicSwing.oldBTCBalance2 = Double.parseDouble(toReturn[7]);
		
	}
	
	//Updates the balance values in the store
	public static void writeBalancesToFile(double currentUSDBalance, double oldUSDBalance, double currentBTCBalance, double oldBTCBalance, 
			double currentUSDBalance2, double oldUSDBalance2, double currentBTCBalance2, double oldBTCBalance2) throws IOException{
		
		String toWrite = currentUSDBalance + "\n" + oldUSDBalance + "\n" + currentBTCBalance + "\n" + oldBTCBalance + "\n" + currentUSDBalance2 + "\n" + oldUSDBalance2 + "\n" + currentBTCBalance2 + "\n" + oldBTCBalance2;
		
		File out = new File("balances.txt");
		if(!out.exists()){
			out.createNewFile();
		}
		
		FileWriter fw = new FileWriter(out.getAbsolutePath());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(toWrite);
		bw.close();
	}
	
	//Converts a String Array into a Linked List of doubles
	public static LinkedList<Double> buildLinkedList(String[] market){
		LinkedList<Double> toReturn = new LinkedList<Double>();
		int j = 0;
		for(int i = market.length-240; i < market.length; i++){
			toReturn.add(Double.parseDouble(market[i]));
			j++;
		}
		System.out.println(j);
		return toReturn;
	}
	
	public static void BuildGPDatabase() throws IOException{
		File bitstamp = new File("bitstampEvery10Seconds.txt");
		FileWriter fwbs = new FileWriter(bitstamp.getAbsolutePath());
		BufferedWriter bwbs = new BufferedWriter(fwbs);
		for(int i = 240; i< BasicSwing.bitstampArray.length; i++){
			bwbs.write(BasicSwing.bitstampArray[i] + "\n");
		}
		for(int i = 0; i<BasicSwing.bitstampList.size(); i++){
			bwbs.write(BasicSwing.bitstampList.get(i) + "\n");
		}
		bwbs.close();
		
		File bitfinex = new File("bitfinexEvery10Seconds.txt");
		FileWriter fwbf = new FileWriter(bitfinex.getAbsolutePath());
		BufferedWriter bwbf = new BufferedWriter(fwbf);
		for(int i = 240; i< BasicSwing.bitfinexArray.length; i++){
			bwbf.write(BasicSwing.bitfinexArray[i] + "\n");
		}
		for(int i = 0; i<BasicSwing.bitfinexList.size(); i++){
			bwbf.write(BasicSwing.bitfinexList.get(i) + "\n");
		}
		bwbf.close();
		
		File btce = new File("btceEvery10Seconds.txt");
		FileWriter fwbt = new FileWriter(btce.getAbsolutePath());
		BufferedWriter bwbt = new BufferedWriter(fwbt);
		for(int i = 240; i< BasicSwing.btceArray.length; i++){
			bwbt.write(BasicSwing.btceArray[i] + "\n");
		}
		for(int i = 0; i<BasicSwing.btceList.size(); i++){
			bwbt.write(BasicSwing.btceList.get(i) + "\n");
		}
		bwbt.close();
		
		File okcoin = new File("okcoinEvery10Seconds.txt");
		FileWriter fwok = new FileWriter(okcoin.getAbsolutePath());
		BufferedWriter bwok = new BufferedWriter(fwok);
		for(int i = 240; i< BasicSwing.okcoinArray.length; i++){
			bwok.write(BasicSwing.okcoinArray[i] + "\n");
		}
		for(int i = 0; i<BasicSwing.okcoinList.size(); i++){
			bwok.write(BasicSwing.okcoinList.get(i) + "\n");
		}
		bwok.close();
	}
	
}
