package Task7;
import java.util.*;
import java.io.*;


public class MainSimulation extends GlobalSimulation{
 
	
	public static double run(){
		Event actEvent;
    	State actState = new State();
    	time = 0;
        insertEvent(BREAK_1, time + actState.generateLifeLength());
        insertEvent(BREAK_2, time + actState.generateLifeLength());
        insertEvent(BREAK_3, time + actState.generateLifeLength());
        insertEvent(BREAK_4, time + actState.generateLifeLength());
        insertEvent(BREAK_5, time + actState.generateLifeLength());
        
        // The main simulation loop
    	while (actState.nbrBrokenComponents < 5){
    		actEvent = eventList.fetchEvent();
    		time = actEvent.eventTime;
    		actState.treatEvent(actEvent);
    	}
    	
    	System.out.println("Time: " + time);
    	return time;
	}
	
    public static void main(String[] args) throws IOException {
    	double totalTime = 0.0;
    	int i = 0;
    	for(i = 0; i<1000; i++){
    		totalTime += run();
    	}
    	System.out.println("Mean time: "+totalTime/i);
    }
}