package Task1;
import java.util.*;
import java.io.*;

public class Main {
	
	public static void simulation(){
		simulation(0);
	}
	
	public static void simulation(int interarrivalTime){
		Event actEvent;
		State actState = new State();
		if(interarrivalTime != 0){
			actState.Q1_Interarrivaltime = interarrivalTime;
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
		System.out.println("------------------");
		System.out.println("Inter-Arrival Time used: "+1.0*actState.Q1_Interarrivaltime);
		System.out.println("Number of measurements Q1: "+1.0*actState.noMeasurements1);
		System.out.println("Number of measurements Q2: "+1.0*actState.noMeasurements2);
		System.out.println("1.1-->Mean value Q2: "+1.0*actState.accumulated2 / actState.noMeasurements2);
		System.out.println("1.2-->Rejection prob for Q1: "+1.0*actState.rejectionsQ1 / actState.accumulated1);
		actState.W.close();
	}

	public static void main(String[] args) throws IOException {
		simulation(1);
		simulation(2);
		simulation(5);
	}
}