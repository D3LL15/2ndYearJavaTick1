package uk.ac.cam.de300.fjava.tick1;

public class HelloWorld {
	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("Hello, world");
		} else {
			if (args.length == 1) {
				System.out.println("Hello, " + args[0]);
			}
		}
	}
}
