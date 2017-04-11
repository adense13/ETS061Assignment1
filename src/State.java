import java.util.*;
import java.io.*;

class State {
	//Q1
	public int numberInQueue = 0, accumulated = 0, noMeasurements = 0, noMeasurements2 = 0;
	public int maxQ1 = 10;
	public int Q1_Interarrivaltime = 1;
	public double rejected = 0;
	public double arrivals = 0;
	
	//Q2
	public int numberInQueue2 = 0, accumulated2 = 0;

	Random slump = new Random();
	SimpleFileWriter W = new SimpleFileWriter("number.m", false);
	
	public double expDistr(double mean){
		return (mean) * Math.log(slump.nextDouble());
	}

	public void TreatEvent(Event x) {
		switch (x.eventType) {
			case G.ARRIVAL: {
				arrivals++;
				if(numberInQueue < 10){ //to prevent queue from exceeding its limit
					numberInQueue++;
					if (numberInQueue == 1) {
						EventList.InsertEvent(G.READY, G.time - expDistr(2.1));
					}
				}
				else{
					rejected++;
				}
				EventList.InsertEvent(G.ARRIVAL, G.time + Q1_Interarrivaltime);
			}
				break;
			case G.READY: {
				numberInQueue--;
				if (numberInQueue > 0) {
					EventList.InsertEvent(G.READY, G.time - expDistr(2.1));
				}
				//Insert ARRIVAL into Q2
				EventList.InsertEvent(G.ARRIVAL_2, G.time);
			}
				break;
			case G.MEASURE: {
				accumulated = accumulated + numberInQueue;
				noMeasurements++;
				EventList.InsertEvent(G.MEASURE, G.time + slump.nextDouble()*10);
				W.println(String.valueOf(numberInQueue));
			}
				break;
			case G.ARRIVAL_2: {
				if(numberInQueue2 == 0){
					EventList.InsertEvent(G.READY_2, G.time + 2.0);
				}
				numberInQueue2++;
			}
				break;
			case G.READY_2: {
				System.out.println("nbr in Q2 before -: "+numberInQueue2);
				numberInQueue2--;
				if (numberInQueue > 0) {
					EventList.InsertEvent(G.READY_2, G.time + 2.0);
				}
			}
				break;
			case G.MEASURE_2: {
				accumulated2 = accumulated2 + numberInQueue2;
				//System.out.println("ac: " + accumulated2);
				noMeasurements2++;
				EventList.InsertEvent(G.MEASURE_2, G.time - expDistr(5));
			}
				break;
		}
		
	}
}