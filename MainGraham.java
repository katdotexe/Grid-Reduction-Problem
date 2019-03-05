package a1p1;

import java.io.IOException;

public class MainGraham {

	public static void main(String[]args)throws NumberFormatException, IOException {
		if(args.length == 3) {
			new GridReductionGraham(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
		}
		else {
			System.out.println("Error, incorrect number of parameters provided.");
		}
		
		System.exit(0);
	}
	
}
