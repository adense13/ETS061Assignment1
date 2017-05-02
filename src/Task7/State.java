package Task7;
import java.util.*;
import java.io.*;

class State extends GlobalSimulation{
	
	// Here follows the state variables and other variables that might be needed
	// e.g. for measurements
	public int numberInQueue = 0, accumulated = 0, noMeasurements = 0;

	public boolean running1 = true;
	public boolean running2 = true;
	public boolean running3 = true;
	public boolean running4 = true;
	public boolean running5 = true;
	public int nbrBrokenComponents = 0;
	
	Random slump = new Random(); // This is just a random number generator
	
	
	// The following method is called by the main program each time a new event has been fetched
	// from the event list in the main loop. 
	public void treatEvent(Event x){
		switch (x.eventType){
			case ARRIVAL:
				arrival();
				break;
			case READY:
				ready();
				break;
			case BREAK_1:
				if(running1){
					running1 = false;
					running2 = false;
					running5 = false;
					nbrBrokenComponents += 3;
				}
				break;
			case BREAK_2:
				if(running2){
					running2 = false;
					nbrBrokenComponents += 1;
				}
				break;
			case BREAK_3:
				if(running3){
					running3 = false;
					running4 = false;
					nbrBrokenComponents += 2;
				}
				break;
			case BREAK_4:
				if(running4){
					running4 = false;
					nbrBrokenComponents += 1;
				}
				break;
			case BREAK_5:
				if(running5){
					nbrBrokenComponents += 1;
					running5 = false;
				}
				break;
			case MEASURE:
				measure();
				break;
		}
	}	
	
	// The following methods defines what should be done when an event takes place. This could
	// have been placed in the case in treatEvent, but often it is simpler to write a method if 
	// things are getting more complicated than this.
	
	private void arrival(){
		if (numberInQueue == 0)
			insertEvent(READY, time + 2*slump.nextDouble());
		numberInQueue++;
		insertEvent(ARRIVAL, time + 2.5*slump.nextDouble());
	}
	
	private void ready(){
		numberInQueue--;
		if (numberInQueue > 0)
			insertEvent(READY, time + 2*slump.nextDouble());
	}
	
	private void measure(){
		accumulated = accumulated + numberInQueue;
		noMeasurements++;
		insertEvent(MEASURE, time + slump.nextDouble()*10);
	}
	
	public double generateLifeLength(){
		return 1.0 + ( slump.nextDouble()*5.0 );
	}
}