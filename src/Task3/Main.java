package Task3;
import java.util.*;
import java.io.*;

public class Main {
	
	public static void simulation(){
		simulation(0);
	}
	
	public static void simulation(double meanArrivalTime){
		Event actEvent;
		State actState = new State();
		if(meanArrivalTime != 0){
			actState.meanArrivalTime = meanArrivalTime;
		}
		new EventList();
		EventList.InsertEvent(G.ARRIVAL, 0);
		EventList.InsertEvent(G.MEASURE, 5);
		EventList.InsertEvent(G.MEASURE_2, 5);
		//while (G.time < 10000) {
		while(actState.noMeasurements2 < 1000 || actState.noMeasurements1 < 1000){
			actEvent = EventList.FetchEvent();
			G.time = actEvent.eventTime;
			actState.TreatEvent(actEvent);
		}
		double meanNbrInQ1 = actState.accumulated1/actState.noMeasurements1;
		double meanNbrInQ2 = actState.accumulated2/actState.noMeasurements2;
		System.out.println("------------------");
		System.out.println("Mean Arrival Time used: "+1.0*actState.meanArrivalTime);
		//System.out.println("Number of measurements Q1: "+1.0*actState.noMeasurements1);
		//System.out.println("Number of measurements Q2: "+1.0*actState.noMeasurements2);
		System.out.println("Mean nbr customers in NETWORK:"+1.0*(meanNbrInQ1+meanNbrInQ2));
		System.out.println( "N = 2/(x-1) = " + 1.0*(2/(meanArrivalTime-1)) );
		System.out.println("Mean time in system:"+1.0*(actState.totalTimeInSystem/actState.arrivals));
		System.out.println( "T = 2x/(x-1) = " + 1.0*((2*meanArrivalTime)/(meanArrivalTime-1)) );
	}

	public static void main(String[] args) throws IOException {
		simulation(2);
		simulation(1.5);
		simulation(1.1);
	}
}