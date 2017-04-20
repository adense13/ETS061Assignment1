package Task2;

import java.io.IOException;
import java.util.ArrayList;

public class MainTask2 {
	
		
		public static void simulation(){	
			Event actEvent;
			State actState = new State();
			new EventList();
			EventList.InsertEvent(S.ARRIVAL_A, 0);
			EventList.InsertEvent(S.MEASUREMENT, 0.2);
			while(actState.nbrMeasurements < 1000){
				actEvent = EventList.FetchEvent();
				S.time = actEvent.eventTime;
				actState.TreatEvent(actEvent);
			}
			System.out.println("-----------------------");
			System.out.println("Nbr measurements: " + 1.0 * actState.nbrMeasurements);
			System.out.println("Acum in buffer: " + 1.0 * actState.acumNbrInBuffer);
			System.out.println("Mean in buffer: " + 1.0 * actState.acumNbrInBuffer / actState.nbrMeasurements);
		}

		public static void main(String[] args) throws IOException {
			simulation();
		}

}
