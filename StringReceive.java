package uk.ac.cam.de300.fjava.tick1;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class StringReceive {

	public static void main(String[] args) {
		//parse input
		if (args.length != 2) {
			System.err.println("This application requires two arguments: <machine> <port>");
			return;
		}
		String serverName = args[0];
		int portNumber;
		try {
			portNumber = Integer.valueOf(args[1]);
		} catch (NumberFormatException ex) {
			System.err.println("This application requires two arguments: <machine> <port>");
			return;
		}
		
		//initialise socket and inputstream
		Socket socket;
		InputStream inputStream = null;
		try {
			socket = new Socket(serverName, portNumber);
			inputStream = socket.getInputStream();
		} catch (IOException ex) {
			System.err.println("Cannot connect to "+ serverName + " on port " + portNumber);
			return;
		}
		
		byte[] buffer = new byte[1024];
		String input;
		int length = 0;
		
		while (true) {
			//read bytes
			try{
				length = inputStream.read(buffer);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			
			//turn bytes to text
			input = new String(buffer, 0, length);
			
			//print text
			System.out.print(input);
		}
	}
}
