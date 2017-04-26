package Task5;

import java.util.ArrayList;
import java.util.Random;

import Task4.Event;
import Task4.EventList;
import Task4.S;
import Task4.State;

public class MainTask5 {
	
	public static int METHOD_RANDOM = 1;
	public static int METHOD_ROUNDROBIN = 2;
	public static int METHOD_LEASTBUSY = 3;
	
	public int method;
	Random rand = new Random();
	public int roundRobin = 0;
	
	public ArrayList<ArrayList<Integer>> queueList;
	
	public MainTask5(int nbrQueues, double serviceTime, int method){
		roundRobin = 0;
		queueList = new ArrayList<ArrayList<Integer>>();
		for(int i = 0; i < nbrQueues; i++){
			queueList.add(i, new ArrayList<Integer>());
		}
	}
	
	public int pickQueue(){
		if(method == METHOD_RANDOM){
			return rand.nextInt(queueList.size());
		}
		else if(method == METHOD_ROUNDROBIN){
			int useThis = roundRobin;
			roundRobin++;
			if(roundRobin > queueList.size()){
				roundRobin = 0;
			}
			return useThis;
		}
		else if(method == METHOD_LEASTBUSY){ //ADD RANDOM FOR THOSE WHO ARE EQUAL
			int leastBusyIndex = 0;
			int leastBusyCount = Integer.MAX_VALUE;
			for(int i = 0; i < queueList.size(); i++){
				if(queueList.get(i).size() < leastBusyCount){
					leastBusyIndex = i;
				}
			}
			return leastBusyIndex;
		}
		return 0;
	}
	
	public static void main(String[] args){
		
	}

}
