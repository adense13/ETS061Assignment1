import java.util.*;
import java.io.*;

public class Main {

	public static void main(String[] args) throws IOException {
		Event actEvent;
		State actState = new State();
		new EventList();
		EventList.InsertEvent(G.ARRIVAL, 1);
		EventList.InsertEvent(G.MEASURE, 5);
		while (G.time < 100000) {
			System.out.println(G.time);
			actEvent = EventList.FetchEvent();
			G.time = actEvent.eventTime;
			actState.TreatEvent(actEvent);
		}
		System.out.println("No. of measurements: " + actState.noMeasurements);
		
		System.out.println("Accum/no measurements: " + 1.0 * actState.accumulated / actState.noMeasurements);
		System.out.println("Accumulated in Q1: " + actState.accumulated);
		
		System.out.println("Accumulated in Q2: " + actState.accumulated2);
		System.out.println("Accum2/no measurements: " + 1.0 * actState.accumulated2 / actState.noMeasurements);
		
		actState.W.close();
	}
}