import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Scanner;

public class Test {
	private String serverAddress;
	private int portNum;
	private Socket socket;
	
	Scanner inputStream;
	PrintWriter outputStream;

	
	public Test(String serverAddress, int portNum)
	{
		this.serverAddress=serverAddress;
		this.portNum=portNum;
	}
	
	private void connectServer()
	{
		try
		{
			socket=new Socket(serverAddress,portNum);
			inputStream=new Scanner(socket.getInputStream());
			outputStream=new PrintWriter(socket.getOutputStream(),true);
		}
		catch(IOException ioe)
		{
			System.out.println(ioe.getMessage());
			System.exit(0);
		}
	}
	

	public static void main(String[] args) {
		Scanner fileInput=null;
		String inputServerAddress="";
		int inputPortNum=0;
		
		try
		{
			fileInput=new Scanner(new File("serverinfo.dat"));
			inputServerAddress=fileInput.nextLine();
			inputPortNum=fileInput.nextInt();
		}
		catch(FileNotFoundException fnfe)
		{
			System.out.println("There is no file : serverinfo.dat");
			System.out.println("default IP : localhost & default portNum : 35859");
			inputServerAddress="192.168.0.6";
			inputPortNum=35859;
		}
		
		Test client=new Test(inputServerAddress,inputPortNum);
		client.connectServer();
		
		System.out.println("Conncet Server");
	}

}