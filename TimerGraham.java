package a1p1;
/*
 * Timer Class 
 * Kathleen Graham 
 * Class: 406.01
 * Date Assigned: 1/29/2019
 * Date Due: 2/5/2019
 */
public class TimerGraham {
	double startTime;
	double stopTime;
	
	
	void start() {
		 startTime = System.currentTimeMillis();	
	}
	void stop() {
		 stopTime = System.currentTimeMillis();
	}
	double getDuration() {
		return stopTime - startTime; 
		
	}
}

