import java.util.Queue;
import java.util.LinkedList;

public class Runway {

	private int minutesForLanding;
	private int minutesForTakeoff;
	private int serviceTimeLeft;

	public Runway(int l, int t) {
		minutesForLanding = l;
		minutesForTakeoff = t;
		serviceTimeLeft = 0;
	}

	public boolean isBusy() {
		return (serviceTimeLeft > 0);
	}

	public void reduceRemainingTime() {
		if (serviceTimeLeft > 0)
			serviceTimeLeft--;
	}

	public void startService(int status) {
		if (serviceTimeLeft > 0) {
			throw new IllegalStateException("Runway is already busy");
		}
		if (status == 0) {
			serviceTimeLeft = minutesForTakeoff;
		}
		if (status == 1) {
			serviceTimeLeft = minutesForLanding;
		}
	}

	/*
	 * Exponential distribution public double getNext() { return
	 * Math.log(1-rand.nextDouble())/(-lambda); }
	 */
	public static void runwaySimulate(int landingTime, int takeoffTime, double landingProb, double takeoffProb,
			int totalTime) {

		Runway runwayOne = new Runway(landingTime, takeoffTime);
		Runway runwayTwo = new Runway(landingTime, takeoffTime);

		int next;
		final int FUEL = 10;
		int crashes = 0;

		BooleanSource arrival = new BooleanSource(landingProb);
		BooleanSource departure = new BooleanSource(takeoffProb);

		Queue<Integer> arrivalTimes = new LinkedList<Integer>();
		Queue<Integer> departureTimes = new LinkedList<Integer>();

		Averager landingWaitTimes = new Averager();
		Averager takeoffWaitTimes = new Averager();
		int currentMinute;

		System.out.println("Minutes to land one plane: " + landingTime);
		System.out.print("Probability of plane arrival during a minute: ");
		System.out.println(landingProb);
		System.out.println("Total simulation minutes: " + totalTime);

		if (landingTime <= 0 || landingProb < 0 || landingProb > 1 || totalTime < 0) {
			throw new IllegalArgumentException("Values out of range");
		}
		if (takeoffTime <= 0 || takeoffProb < 0 || takeoffProb > 1) {
			throw new IllegalArgumentException("Values out of range");
		}
		for (currentMinute = 0; currentMinute < totalTime; currentMinute++) {
			if (arrival.query()) {
				arrivalTimes.add(currentMinute);
			}
			if (departure.query()) {
				departureTimes.add(currentMinute);
			}
			// check both runways
			while (!runwayOne.isBusy() && !arrivalTimes.isEmpty()) {
				next = arrivalTimes.remove();
				landingWaitTimes.addNumber(currentMinute - next);
				if ((currentMinute - next) > FUEL) {
					crashes++;
				} else {
					runwayOne.startService(1);
				}
			}
			while (!runwayTwo.isBusy() && !arrivalTimes.isEmpty()) {
				next = arrivalTimes.remove();
				landingWaitTimes.addNumber(currentMinute - next);
				if ((currentMinute - next) > FUEL) {
					crashes++;
				} else {
					runwayTwo.startService(1);
				}
			}
			if ((!runwayOne.isBusy()) && !departureTimes.isEmpty()){
				next = departureTimes.remove();
				takeoffWaitTimes.addNumber(currentMinute - next);
				runwayOne.startService(0);
			}
			if ((!runwayTwo.isBusy()) && !departureTimes.isEmpty()){
				next = departureTimes.remove();
				takeoffWaitTimes.addNumber(currentMinute - next);
				runwayTwo.startService(0);
			}
			runwayOne.reduceRemainingTime();
			runwayTwo.reduceRemainingTime();
		}

		System.out.println("Planes landed: " + landingWaitTimes.howManyNumbers());
		if (landingWaitTimes.howManyNumbers() > 0) {
			System.out.println("Average landing wait: " + landingWaitTimes.average() + " min");
		}
		System.out.println("Planes took off: " + takeoffWaitTimes.howManyNumbers());
		if (takeoffWaitTimes.howManyNumbers() > 0) {
			System.out.println("Average takeoff wait: " + takeoffWaitTimes.average() + " min");
		}
		System.out.println("Total crashes:  " + crashes);
	}

	public static void main(String[] args) {
		// Time to land, Time to take off, Arrival Rate,
		// Departure Rate, totalTime
		System.out.println("\nExperiment 4, 2a-1.\n");		
		Runway.runwaySimulate(3, 4, 0.19, 0.3, 90000);
		System.out.println("---------------------");
		System.out.println("\nExperiment 4, 2a-2.\n");		
		Runway.runwaySimulate(3, 4, 0.18, 0.3, 90000);
		System.out.println("---------------------");
		System.out.println("\nExperiment 4, 2a-3.\n");		
		Runway.runwaySimulate(3, 4, 0.17, 0.3, 90000);
		System.out.println("---------------------");
		System.out.println("\nExperiment 4, 2a-4.\n");		
		Runway.runwaySimulate(3, 4, 0.16, 0.3, 90000);
		System.out.println("---------------------");
		System.out.println("\nExperiment 4, 2a-5.\n");		
		Runway.runwaySimulate(3, 4, 0.15, 0.3, 90000);
		System.out.println("---------------------");
		System.out.println("\nExperiment 4, 2a-6.\n");		
		Runway.runwaySimulate(3, 4, 0.14, 0.3, 90000);
		System.out.println("---------------------");
		System.out.println("\nExperiment 4, 2a-7.\n");		
		Runway.runwaySimulate(3, 4, 0.13, 0.3, 90000);
		System.out.println("---------------------");
		System.out.println("\nExperiment 4, 2a-8.\n");		
		Runway.runwaySimulate(3, 4, 0.12, 0.3, 90000);
		System.out.println("---------------------");
		System.out.println("\nExperiment 4, 2a-9.\n");		
		Runway.runwaySimulate(3, 4, 0.11, 0.3, 90000);
		System.out.println("---------------------");
		System.out.println("\nExperiment 4, 2a-10.\n");		
		Runway.runwaySimulate(3, 4, 0.1, 0.3, 90000);
		System.out.println("---------------------");
		System.out.println("\nExperiment 4, 3a-1.\n");
		Runway.runwaySimulate(3, 4, 0.3, 0.01, 90000);
		System.out.println("---------------------");
		System.out.println("\nExperiment 4, 3a-2.\n");
		Runway.runwaySimulate(3, 4, 0.3, 0.02, 90000);
		System.out.println("---------------------");
		System.out.println("\nExperiment 4, 3a-3.\n");
		Runway.runwaySimulate(3, 4, 0.3, 0.03, 90000);
		System.out.println("---------------------");
		System.out.println("\nExperiment 4, 3a-4.\n");
		Runway.runwaySimulate(3, 4, 0.3, 0.04, 90000);
		System.out.println("---------------------");
		System.out.println("\nExperiment 4, 3a-5.\n");
		Runway.runwaySimulate(3, 4, 0.3, 0.05, 90000);
		System.out.println("---------------------");
		System.out.println("\nExperiment 4, 3a-6.\n");
		Runway.runwaySimulate(3, 4, 0.3, 0.06, 90000);
		System.out.println("---------------------");
		System.out.println("\nExperiment 4, 3a-7.\n");
		Runway.runwaySimulate(3, 4, 0.3, 0.07, 90000);
		System.out.println("---------------------");
		System.out.println("\nExperiment 4, 3a-8.\n");
		Runway.runwaySimulate(3, 4, 0.3, 0.08, 90000);
		System.out.println("---------------------");
		System.out.println("\nExperiment 4, 3a-9.\n");
		Runway.runwaySimulate(3, 4, 0.3, 0.09, 90000);
		System.out.println("---------------------");
		System.out.println("\nExperiment 4, 3a-10.\n");
		Runway.runwaySimulate(3, 4, 0.3, 0.10, 90000);
		System.out.println("---------------------");
	}
}
