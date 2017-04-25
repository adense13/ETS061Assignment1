package Task4;

import java.util.ArrayList;
import java.util.Random;
import Utils.Distributions;

public class State {
	//parameters
	public int N = 1000;
	public double x = 100;
	public double lambda = 8;
	public int T = 1;
	public int M = 1000;
	
	//System stuff
	public int nbrJobsInSystem = 0;
	Random slump = new Random();
	SimpleFileWriter sfw;
	
	//measurements
	public int nbrMeasurements = 0;
	public int acumNbrJobsInSystem = 0;
	
	public State(String fileName, double x, double lambda, int N, int T, int M){
		this.sfw = new SimpleFileWriter(fileName+".m", false);
		this.x = x;
		this.lambda = lambda;
		this.N = N;
		this.T = T;
		this.M = M;
	}
	
	public void TreatEvent(Event event) {
		switch (event.eventType) {
		case S.ARRIVAL:
			//if(nbrJobsInSystem == 0 && nbrJobsInSystem <= N){
			if(nbrJobsInSystem <= N){
				EventList.InsertEvent(S.READY, S.time + x);
				EventList.InsertEvent(S.ARRIVAL, S.time + (1/lambda));
				nbrJobsInSystem++; //We enter the system
			}
			break;
		case S.READY:
			nbrJobsInSystem--; //We leave the system
			break;
		case S.MEASUREMENT:
			nbrMeasurements++;
			acumNbrJobsInSystem += nbrJobsInSystem;
			sfw.println(String.valueOf(nbrJobsInSystem));
			EventList.InsertEvent(S.MEASUREMENT, S.time + T);
			break;
		}
	}
}
