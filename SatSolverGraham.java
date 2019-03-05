package a1p1;


import java.io.FileOutputStream;
import org.sat4j.minisat.SolverFactory;
import org.sat4j.reader.DimacsReader;
import org.sat4j.specs.IProblem;
import org.sat4j.specs.ISolver;

/*
 * SatSolver class
 *  Kathleen Graham 
 *  Class: 406.01
 *  Date Assigned: 1/29/2019
 *  Date Due: 2/5/2019
 */

public class SatSolverGraham {
	
	/*
	 * SatSolver method
	 */
	SatSolverGraham(String inputFileName){
		ISolver solver = SolverFactory.newDefault(); //file name given in command line
		DimacsReader reader = new DimacsReader(solver);//reads the cnf file
	
		try {
			IProblem problem = reader.parseInstance(inputFileName);//parses through the cnf file
			int [] vars = problem.findModel();//content of vars is the solution
			
				FileOutputStream output = new FileOutputStream("tempSatFormulaFile.cnf");
				output.write("\n The formula in file: ".getBytes());
				output.write(inputFileName.getBytes());
				output.write(" is Satisfiable...".getBytes());
				output.write("\r".getBytes());
				output.write("The answer is: ".getBytes());
				for(int i = 0; vars.length > i; i++)
				{
					output.write(String.valueOf(vars[i]).getBytes()); 
				}
				output.close();
				
		} catch (Exception e) { //if the file name is not provided then error
			try {
				FileOutputStream output2 = new FileOutputStream("tempSatFormulaFile.cnf");
				output2.write("\n The formula in file: ".getBytes());
				output2.write(inputFileName.getBytes());
				output2.write(" is not Satisfiable.".getBytes());
				output2.close();
			}
			catch(Exception x){
			System.out.println("Error.");
			}
		}
		
		
	}
	
	/*
	 * Main method
	 */
	public static void main(String[] args) {
		
		TimerGraham timer1 = new TimerGraham(); //creates a timer object
		timer1.start(); //starts the timer 
		if(args.length != 0) {
			new SatSolverGraham(args[0]); //solves the equation
			timer1.stop(); //stops the timer 
			System.out.println("The time taken to solve the problem:");
		}
		else {
			System.out.println("Error. Requires an arguement.");
		}
		
		System.out.print(timer1.getDuration()); //prints out the duration
	}
	
}



