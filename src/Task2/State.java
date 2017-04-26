package Task2;

import java.util.ArrayList;
import java.util.Random;
import Utils.Distributions;

public class State {
	//parameters
	public double lambda = 150;
	public double x_a = 0.002;
	public double x_b = 0.004;
	public double d = 1;
	
	//System stuff
	public ArrayList<Integer> buffer;
	public int nbrJobsInSystem = 0;
	Random slump = new Random();
	SimpleFileWriter Task21 = new SimpleFileWriter("Task21.m", false);
	int priority;
	boolean doExpDelay;
	
	//measurements
	public int nbrMeasurements = 0;
	public int acumNbrInBuffer = 0;
	public int acumNbrJobsInSystem = 0;
	
	public State(int priority, boolean doExpDelay){
		buffer = new ArrayList<Integer>();
		this.priority = priority;
	}
	
	public void TreatEvent(Event x) {
		switch (x.eventType) {
		case S.ARRIVAL_A:
			//if(nbrJobsInSystem == 0){
			//	EventList.InsertEvent(S.READY_A, S.time + x_a);
			//}
			if(buffer.size() == 0 && nbrJobsInSystem == 0){
				EventList.InsertEvent(S.READY_A, S.time + x_a);
			}
			else{
				buffer.add(S.ARRIVAL_A);
			}
			EventList.InsertEvent(S.ARRIVAL_A, S.time + Distributions.expDistr(1/lambda));
			nbrJobsInSystem++; //We enter the system
			break;
		case S.ARRIVAL_B:
			//if(nbrJobsInSystem == 0){
			//	EventList.InsertEvent(S.READY_B, S.time + x_b);
			//}
			if(buffer.size() == 0 && nbrJobsInSystem == 0){
				EventList.InsertEvent(S.READY_B, S.time + x_b);
			}
			else{
				buffer.add(S.ARRIVAL_B);
			}
			nbrJobsInSystem++; //We enter the system
			break;
		case S.READY_A:
			nbrJobsInSystem--; //We leave the system
			if(doExpDelay){
				EventList.InsertEvent(S.ARRIVAL_B, S.time + Distributions.expDistr(d));
			}else{
				EventList.InsertEvent(S.ARRIVAL_B, S.time + d);
			}
			newJob();
			break;
		case S.READY_B:
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
	
	public int fetchJobFromBuffer(){
		for(int i = 0; i<buffer.size(); i++){
			if(buffer.get(i) == priority){
				return buffer.remove(i); //we fetch the first JOB_B we can find
			}
		}
		if(buffer.size() > 0){
			return buffer.get(0); //otherwise let's just fetch the first job in the list
		}
		else{
			return 0; //empty buffer
		}
	}
	
	public void newJob(){
		int jobFromBuffer = fetchJobFromBuffer();
		
		if( jobFromBuffer == S.ARRIVAL_B){
			EventList.InsertEvent(S.READY_B, S.time + x_b);
		}
		else if( jobFromBuffer == S.ARRIVAL_A){
			EventList.InsertEvent(S.READY_A, S.time + x_a);
		}
	}
}
