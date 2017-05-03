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
	SimpleFileWriter Task21 = new SimpleFileWriter("Task21.m", false);
	int priority;
	boolean doExpDelay;
	boolean busy = false;
	
	//measurements
	public int nbrMeasurements = 0;
	public int acumNbrInBuffer = 0;
	public int acumNbrJobsInSystem = 0;
	
	public State(int priority, boolean doExpDelay){
		buffer = new LinkedList<Integer>();
		this.priority = priority;
	}
	
	public void TreatEvent(Event x) {
		switch (x.eventType) {
		case S.ARRIVAL_A:
			if(buffer.size() == 0 || (!busy)){
				EventList.InsertEvent(S.READY_A, S.time + x_a);
				busy = true;
			}
			else{
				if(priority == S.ARRIVAL_A){
					buffer.addFirst(S.ARRIVAL_A);
				}
				else{
					buffer.addLast((S.ARRIVAL_A));
				}
			}
			EventList.InsertEvent(S.ARRIVAL_A, S.time + Distributions.expDistr(1.0/lambda));
			break;
		case S.ARRIVAL_B:
			if(buffer.size() == 0 && (!busy)){
				EventList.InsertEvent(S.READY_B, S.time + x_b);
				busy = true;
			}
			else{
				if(priority == S.ARRIVAL_B){
					buffer.addFirst(S.ARRIVAL_B);
				}
				else{
					buffer.addLast((S.ARRIVAL_B));
				}
			}
			nbrJobsInSystem++; //We enter the system
			break;
		case S.READY_A:
			busy = false;
			nbrJobsInSystem--; //We leave the system
			if(doExpDelay){
				EventList.InsertEvent(S.ARRIVAL_B, S.time + Distributions.expDistr(d));
			}else{
				EventList.InsertEvent(S.ARRIVAL_B, S.time + d);
			}
			newJob();
			break;
		case S.READY_B:
			busy = false;
			nbrJobsInSystem--; //We leave the system
			newJob();
			break;
		case S.MEASUREMENT:
			Task21.println(String.valueOf(buffer.size()));
			nbrMeasurements++; //We leave the system
			acumNbrInBuffer += buffer.size();
			EventList.InsertEvent(S.MEASUREMENT, S.time + 0.1);
			break;
		}
	}
	
	public void newJob(){
		if(buffer.size() > 0){
			int job = buffer.poll();
			if(job == S.ARRIVAL_A){
				busy = true;
				EventList.InsertEvent(S.READY_A, S.time + x_a);
			}
			else if(job == S.ARRIVAL_B){
				busy = true;
				EventList.InsertEvent(S.READY_B, S.time + x_b);
			}
		}
	}
}
