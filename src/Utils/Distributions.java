package Utils;

import java.util.Random;

public class Distributions {
	
	public static double expDistr(double mean){
		Random rand = new Random();
		return -(mean) * Math.log(rand.nextDouble());
	}
	
	public static double poDistr(double lambda){
		double L = Math.exp(-lambda);
		  double p = 1.0;
		  int k = 0;

		  do {
		    k++;
		    p *= Math.random();
		  } while (p > L);

		  return k - 1;
	}
}
