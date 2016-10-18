package uk.ac.cam.de300.fjava.tick1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.Thread;
import java.net.Socket;

public class StringChat {
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

		//s is declared final because the java compiler passes a copy of local variables to the inner
		// class rather than a reference which would lead to desynchronisation between the values of s
		// for the inner and outer classes unless we explicitly prevent changes to the value of s. 
		final Socket s;
		OutputStream outputStream = null;
		final InputStream inputStream;
		try {
			s = new Socket(serverName, portNumber);
			inputStream = s.getInputStream();
			outputStream = s.getOutputStream();
		} catch (IOException ex) {
			System.err.println("Cannot connect to "+ serverName + " on port " + portNumber);
			return;
		}
			
		//output thread to run in the background reading from the server and printing messages
		Thread output = new Thread() {
			@Override
			public void run() {
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
		};
		
		//the JVM can stop running the program when only daemon threads are running
		output.setDaemon(true);
		output.start();

		//read the user's input and send it to the server
		BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
		String message = null;
		while( true ) {
			try {
				message = r.readLine();
			} catch (IOException e) {
				System.err.println("Error reading user's message");
			}
			byte[] messageBytes = message.getBytes();
			
			try {
				outputStream.write(messageBytes);
			} catch (IOException e) {
				System.err.println("Error writing message to the server");
			}
		}
	}
}