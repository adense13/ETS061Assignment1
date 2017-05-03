package Task3;
import java.util.*;

import Utils.Distributions;

import java.io.*;

class State {
	public double totalTimeInSystem = 0;
	//Q1
	public double numberInQueue1 = 0, accumulated1 = 0, noMeasurements1 = 0, noMeasurements2 = 0;
	public double maxQ1 = 10;
	public double meanArrivalTime = 1;
	public double rejectionsQ1 = 0;
	public double arrivals = 0;
	
	//Q2
	public int numberInQueue2 = 0, accumulated2 = 0;

	Random slump = new Random();
	SimpleFileWriter W = new SimpleFileWriter("number.m", false);

	public void TreatEvent(Event x) {
		switch (x.eventType) {
			case G.ARRIVAL:
				arrivals++;
				if(numberInQueue1 == 0){
					double readyTime = Distributions.expDistr(1);
					totalTimeInSystem += readyTime;
					totalTimeInSystem += numberInQueue1*readyTime;
					EventList.InsertEvent(G.READY, G.time + readyTime);
				}
				numberInQueue1++;
				EventList.InsertEvent(G.ARRIVAL, G.time + Distributions.expDistr(meanArrivalTime));
				break;
			case G.READY:
				numberInQueue1--;
				if (numberInQueue1 > 0) {
					double readyTime = Distributions.expDistr(1);
					totalTimeInSystem += readyTime;
					totalTimeInSystem += numberInQueue1*readyTime;
					EventList.InsertEvent(G.READY, G.time + readyTime);
				}
				//Insert ARRIVAL into Q2
				EventList.InsertEvent(G.ARRIVAL_2, G.time);
				break;
			case G.MEASURE:
				accumulated1 = accumulated1 + numberInQueue1;
				noMeasurements1++;
				EventList.InsertEvent(G.MEASURE, G.time + slump.nextDouble()*10);
				W.println(String.valueOf(numberInQueue1));
				break;
			case G.ARRIVAL_2:
				if(numberInQueue2 == 0){
					double readyTime = Distributions.expDistr(1);
					totalTimeInSystem += readyTime;
					EventList.InsertEvent(G.READY_2, G.time + readyTime);
				}
				numberInQueue2++;
				break;
			case G.READY_2:
				numberInQueue2--;
				if (numberInQueue2 > 0) {
					double readyTime = Distributions.expDistr(1);
					totalTimeInSystem += readyTime;
					totalTimeInSystem += numberInQueue2*readyTime;
					EventList.InsertEvent(G.READY_2, G.time + readyTime);
				}
				break;
			case G.MEASURE_2:
				accumulated2 = accumulated2 + numberInQueue2;
				noMeasurements2++;
				EventList.InsertEvent(G.MEASURE_2, G.time + Distributions.expDistr(5));
				break;
		}
		
	}
}