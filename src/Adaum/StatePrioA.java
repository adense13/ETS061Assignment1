package Adaum;

import java.util.*;
import java.io.*;

class StatePrioA extends GlobalSimulation {

	// Here follows the state variables and other variables that might be needed
	// e.g. for measurements
	public int accumulated = 0, noMeasurements = 0, noArrivals;
	public LinkedList<String> buffer = new LinkedList<String>();
	double interarrival = (1.0/150.0);
	Random slump = new Random(); // This is just a random number generator

	// The following method is called by the main program each time a new event
	// has been fetched
	// from the event list in the main loop.
	public void treatEvent(Event x) {

		switch (x.eventType) {
		case ARRIVALTYPEA:
			arrivalTypeA();
			break;
		case ARRIVALTYPEB:
			arrivalTypeB();
			break;
		case READY:
			ready();
			break;
		case MEASURE:
			measure();
			break;

		}
	}

	// The following methods defines what should be done when an event takes
	// place. This could
	// have been placed in the case in treatEvent, but often it is simpler to
	// write a method if
	// things are getting more complicated than this.

	private void arrivalTypeA() {
		noArrivals++;
		if (buffer.size() == 0) {
			insertEvent(READY, time + 0.002);
		}
		buffer.addFirst("a");
		insertEvent(ARRIVALTYPEA, time + interarrival);
	}

	private void arrivalTypeB() {
		if (buffer.size() == 0) {
			insertEvent(READY, time + 0.004);
		}
		buffer.addLast("b");
	}

	private void ready() {
		if (buffer.size() > 0) {
			String s = buffer.poll();
			if (s.equals("b")) {
				insertEvent(READY, time + 0.004);
			} else {
				insertEvent(READY, time + 0.002);
				insertEvent(ARRIVALTYPEB, time + 0.002 + 1.0);
			}
		}

	}

	private void measure() {
		accumulated = accumulated + buffer.size();
		noMeasurements++;
		insertEvent(MEASURE, time + 0.1);
	}
}