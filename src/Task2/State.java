package Task2;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import Utils.Distributions;

public class State {
	//parameters
	public double lambda = 150.0;
	public double x_a = 0.002;
	public double x_b = 0.004;
	public double d = 1.0;
	
	//System stuff
	public LinkedList<Integer> buffer;
	public int nbrJobsInSystem = 0;
	Random slump = new Random();
	SimpleFileWriter fw;
	int priority;
	boolean doExpDelay;
	boolean busy = false;
	
	//measurements
	public int nbrMeasurements = 0;
	public int acumNbrInBuffer = 0;
	public int acumNbrJobsInSystem = 0;
	public String name;
	
	public State(int priority, boolean doExpDelay, String name){
		this.doExpDelay = doExpDelay;
		buffer = new LinkedList<Integer>();
		this.priority = priority;
		this.name = name;
		fw = new SimpleFileWriter(name+".m", false);
	}
	
	public void TreatEvent(Event x) {
		switch (x.eventType) {
		case S.ARRIVAL_A:
			if(buffer.size() == 0){
				EventList.InsertEvent(S.READY, S.time + x_a);
				busy = true;
			}
			
			if(priority == S.ARRIVAL_A){
				buffer.addFirst(S.ARRIVAL_A);
			}
			else{
				buffer.addLast((S.ARRIVAL_A));
			}
			
			EventList.InsertEvent(S.ARRIVAL_A, S.time + Distributions.expDistr(1.0/lambda));
			break;
		case S.ARRIVAL_B:
			if(buffer.size() == 0){
				EventList.InsertEvent(S.READY, S.time + x_b);
				busy = true;
			}
			if(priority == S.ARRIVAL_B){
				buffer.addFirst(S.ARRIVAL_B);
			}
			else{
				buffer.addLast((S.ARRIVAL_B));
			}
			nbrJobsInSystem++; //We enter the system
			break;
		case S.READY:
			busy = false;
			if(buffer.size() > 0){
				int job = buffer.poll();
				if(job == S.ARRIVAL_A){
					busy = true;
					EventList.InsertEvent(S.READY, S.time + x_a);
					if(doExpDelay){
						busy = true;
						EventList.InsertEvent(S.ARRIVAL_B, S.time + Distributions.expDistr(d));
					}else{
						busy = true;
						EventList.InsertEvent(S.ARRIVAL_B, S.time + d);
					}
				}
				else if(job == S.ARRIVAL_B){
					busy = true;
					EventList.InsertEvent(S.READY, S.time + x_b);
				}
			}
			break;
		case S.MEASUREMENT:
			fw.println(String.valueOf(buffer.size()));
			nbrMeasurements++;
			acumNbrInBuffer += buffer.size();
			EventList.InsertEvent(S.MEASUREMENT, S.time + 0.1);
			break;
		}
	}
}
