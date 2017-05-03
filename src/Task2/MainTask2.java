package Task2;

import java.io.IOException;
import java.util.ArrayList;

public class MainTask2 {
	
		
		public static void simulation(String name, int priority, boolean doExpDelay){	
			Event actEvent;
			State actState = new State(priority, doExpDelay);
			new EventList();
			EventList.InsertEvent(S.ARRIVAL_A, 5);
			EventList.InsertEvent(S.MEASUREMENT, 0.1);
			while(actState.nbrMeasurements < 1000){
				actEvent = EventList.FetchEvent();
				S.time = actEvent.eventTime;
				actState.TreatEvent(actEvent);
			}
			System.out.println("-----------------------");
			System.out.println(name);
			System.out.println("-----------------------");
			System.out.println("Nbr measurements: " + 1.0 * actState.nbrMeasurements);
			System.out.println("Acum in buffer: " + 1.0 * actState.acumNbrInBuffer);
			System.out.println("Mean in buffer: " + 1.0 * actState.acumNbrInBuffer / actState.nbrMeasurements);
		}

		public static void main(String[] args) {
			simulation("Task 2.1", S.ARRIVAL_B, false);
			simulation("Task 2.2", S.ARRIVAL_B, true);
			simulation("Task 2.3", S.ARRIVAL_A, false);
		}

}
