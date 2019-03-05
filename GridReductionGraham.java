package a1p1;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class GridReductionGraham {

	private static int rows;
	private static int columns;
	private static int color;
	private static PrintWriter printWriter  = null;
	String outputFileName = "outputFileName.cnf";
	
	GridReductionGraham(int n, int m, int nColor) throws IOException {
		rows = n;
		columns = m;
		color = nColor;
		printWriter = new PrintWriter(outputFileName);
		
		
		TimerGraham timer1 = new TimerGraham();
		timer1.start(); //start the clock 
		solve(); //calls solve to solve the grid coloring instance
		timer1.stop(); //stops the clocks
		
		System.out.print(timer1.getDuration());
	}
	
	private void reduceToSat(String outputFileName)throws IOException{
		
		writeFileHeader(outputFileName);//call writeFileHeader
		exactlyOneColorPerCell();//call exactlyOneColorPerCell
		subgridCornerConstraint();//call subgridCornerConstraint
		
	}
	
	private void writeFileHeader(String outputFileName)throws IOException{
		
		//calculate the number of clauses and variables...
		int vars = rows * columns * color;
		int clauseA = (((color *(color -1))/2) +1)*(rows * columns);
		int rowChooseTwo = (rows *(rows -1)/2);
		int columnChooseTwo = (columns *(columns -1)/2);
		int sum = rowChooseTwo * columnChooseTwo; //finds the number of sub-grids
		
		int allClauses = clauseA + (sum * color);
		printWriter.print("p cnf " + vars + " " + allClauses + "\n");
		
	}
	private void exactlyOneColorPerCell(){
		for(int i = 1; i < rows; i++) {
			for(int j = 1; j < columns; j++) {
				exactlyOneColorPerCell(i, j);
			}
		}
	}
	
	private void exactlyOneColorPerCell(int p, int q) {
		constraintOneA(p, q); //implement 1a
		constraintOneB(p, q); //implement 1b
		
	}
	
	private void constraintOneA(int p, int q) {
		//calculates constraint 1a
		//finds the number of clauses for the whole grid
		for(int i = 1; i <= color; i++) { //check to the amount of colors 
			printWriter.print(encode(p,q,i) + " "); //use encode
		}
		printWriter.print("0");
		printWriter.println();
		
	}
	private void constraintOneB(int p, int q) {
		for(int j = 1; j < color; j++) { //check to the amount of colors 
			for(int k = j+1; k <= color; k++) { //check to the amount of colors + 1
				printWriter.write("-"+encode(p,q,j)+" "+ "-" +encode(p,q,k)+ " " + "0"); //print in correct format
			}
		}
	}
	
	private void subgridCornerConstraint() {
		for(int i = 1; i < rows; i++) {
			for(int j = 1; j < columns; j++) {
				subgridCornerConstraintWork(i,j);
			}
		}
		printWriter.close();
	}
	
	private void subgridCornerConstraintWork(int p, int q) {
		for(int i = 1; i < rows; i++) { //checks each cell 
			for(int j = 1; j < columns; j++) {
				for(int k = 1; k <= color; k++) {
					if(i+p <= rows && j+q <= columns) {
						printWriter.print( "-" + encode(i, j, k) + " " +
								   "-" + encode(i, j+q, k) + " " +
								   "-" + encode(i+p, j, k) + " " +
								   "-" + encode(i+p, j+q, k) + " " + "0");
						printWriter.println();
					}
					
				}
			}
		}
		
	}
	
	private int encode(int i, int j, int k) {
		//converts the triplet of numbers into a single integer which is the number of variables
		return((color*((i-1)*columns + (j-1))+k)); 		
	}
	
	private void output(String outputFileName) { 
		//solve using the SAT solver, the SAT Formula in satFileName
		new SatSolverGraham(outputFileName); //use the sat solver
		Scanner sc = null; 
		try {
			sc = new Scanner(new File("outputFileName.cnf")); //output file is being scanned
			int count = 0;
	
			while(sc.hasNext()) { //scan through the file 
				String n = sc.next();
				try {
					
					int i = Integer.parseInt(n);
					
					if(i > 0) {
						switch(i%color) {
							case 0: System.out.print("C" + " ");break;
							case 1: System.out.print("M" + " ");break;
							case 2: System.out.print("Y" + " ");break;
							case 3: System.out.print("K" + " ");break;
						}
						count++;
					
						if (count == columns) {
							System.out.println();
							count = 0;
						}
						
					}
					
					
				}catch(NumberFormatException e) {
				//System.out.println("The grid can't be printed.");
				}
		}
		}catch(FileNotFoundException e) {
			System.out.println("Error: File not found.");
		}
	}
	private void solve() throws IOException{
		
		reduceToSat("outputFileName.cnf"); //calls reduceToSat and gets the cnf file 
		output(outputFileName); //does output
	}
}
