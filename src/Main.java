import java.util.*;
import java.io.*;

public class Main {

	public static void main(String[] args) throws IOException {
		Event actEvent;
		State actState = new State();
		new EventList();
		EventList.InsertEvent(G.ARRIVAL, 1);
		EventList.InsertEvent(G.MEASURE, 5);
		while (G.time < 10000) {
			//System.out.println(G.time);
			actEvent = EventList.FetchEvent();
			G.time = actEvent.eventTime;
			actState.TreatEvent(actEvent);
		}
		System.out.println("No. of measurements: " + actState.noMeasurements);
		
		System.out.println("Q1 Accum/nbr: " + 1.0 * actState.accumulated / actState.noMeasurements);
		System.out.println("Q1 Accum: " + actState.accumulated);
		
		System.out.println("\n");
		
		System.out.println("Q2 Accum: " + actState.accumulated2);
		System.out.println("Q2 Accum/nbr: " + 1.0 * actState.accumulated2 / actState.noMeasurements);
		
		
		System.out.println("Rejection prob: " + actState.rejected / actState.arrivals);
		actState.W.close();
	}
}