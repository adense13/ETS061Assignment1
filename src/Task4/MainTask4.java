package Task4;

import java.io.IOException;
import java.util.ArrayList;

public class MainTask4 {
	
		
		public static void simulation(String name, double x, double lambda, int N, int T, int M){	
			Event actEvent;
			State actState = new State(name, x, lambda, N, T, M);
			new EventList();
			EventList.InsertEvent(S.ARRIVAL, (1/actState.lambda));
			EventList.InsertEvent(S.MEASUREMENT, actState.T);
			while(actState.nbrMeasurements < actState.M){
				actEvent = EventList.FetchEvent();
				S.time = actEvent.eventTime;
				actState.TreatEvent(actEvent);
			}
			actState.sfw.close();
			System.out.println("-----------------------");
			System.out.println(name);
			System.out.println("-----------------------");
			System.out.println("Nbr measurements: " + 1.0 * actState.nbrMeasurements);
			System.out.println("\n");
		}

		public static void main(String[] args) {
			simulation("Task_4_1", 100, 8, 1000, 1, 1000);
			simulation("Task_4_2", 10, 80, 1000, 1, 1000);
			simulation("Task_4_3", 200, 4, 1000, 1, 1000);
			
			simulation("Task_4_4", 10, 4, 100, 4, 1000);
			simulation("Task_4_5", 10, 4, 100, 1, 4000);
			simulation("Task_4_6", 10, 4, 100, 4, 4000);
		}

}
