package Adaum;

import java.util.*;
import java.io.*;

public class MainSimulation extends GlobalSimulation {

	public static void main(String[] args) throws IOException {
		Event actEvent;
		StatePrioB actState1 = new StatePrioB();
		StatePrioA actState2 = new StatePrioA();
		StatePrioBExpDelay actState3 = new StatePrioBExpDelay();
		// Some events must be put in the event list at the beginning
		insertEvent(ARRIVALTYPEA, 0);
		insertEvent(MEASURE, 0);
		// The main simulation loop type B prio
		while (actState1.noMeasurements < 1000) {
			actEvent = eventList.fetchEvent();
			time = actEvent.eventTime;
			actState1.treatEvent(actEvent);
		}
		insertEvent(ARRIVALTYPEA, 0);
		insertEvent(MEASURE, 0);
		// The main simulation loop type A prio
		while (actState2.noMeasurements < 1000) {
			actEvent = eventList.fetchEvent();
			time = actEvent.eventTime;
			actState2.treatEvent(actEvent);
		}
		insertEvent(ARRIVALTYPEA, 0);
		insertEvent(MEASURE, 0);
		// The main simulation loop type B prio, exp distribution
		while (actState3.noMeasurements < 1000) {
			actEvent = eventList.fetchEvent();
			time = actEvent.eventTime;
			actState3.treatEvent(actEvent);
		}

		// Printing the result of the simulation, in this case a mean value
		System.out.println("Simulations results when type b jobs has priority");
		System.out.println("Number of arrivals to the system: " + 1.0
				* actState1.noArrivals);
		System.out.println("mean in the buffer: " + 1.0 * actState1.accumulated
				/ actState1.noMeasurements);
		System.out.println("--------------------------------");
		System.out.println("Simulations results when type a jobs has priority");
		System.out.println("Number of arrivals to the system: " + 1.0
				* actState2.noArrivals);
		System.out.println("mean in the buffer: " + 1.0 * actState2.accumulated
				/ actState2.noMeasurements);
		System.out.println("--------------------------------");
		System.out
				.println("Simulation results with exponetional distrubtion, type b priority");
		System.out.println("Number of arrivals to the system: " + 1.0
				* actState3.noArrivals);
		System.out.println("mean in the buffer: " + 1.0 * actState3.accumulated
				/ actState3.noMeasurements);
	}
}