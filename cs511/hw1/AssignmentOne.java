/*
Josh Gribbon
*/
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class AssignmentOne {
    /*
    Given list of intervals, create a PrimeFinder thread to handle each interval
    For each interval find all the primes, then combine the lists of primes
    */
	public static List<Integer> lprimes(List<Integer[]> intervals) {
		List<PrimeFinder> primeFinders = new ArrayList<PrimeFinder>();
		List<Thread> threadList = new ArrayList<Thread>();
		for (Integer[] pair : intervals){
            PrimeFinder pf = new PrimeFinder(pair[0], pair[1]);
			primeFinders.add(pf);
            Thread t = new Thread(pf);
            threadList.add(t);
            t.start();
        }
		for (Thread t : threadList) {
			try {
				t.join();
			} catch (InterruptedException e) {
				System.out.println(e);
			}
		}
		List<Integer> primes = new ArrayList<Integer>();
		for (PrimeFinder r : primeFinders){
			for (Integer i : r.getPrimesList()){
				primes.add(i);
            }
        }
		return (primes);
	}

    /*
    Read in list of integers to create intervals
    Get list of primes using intervals
    Print out list of primes
    */
	public static void main(String[] args) {
		List<Integer[]> inputList = new ArrayList<Integer[]>();

		for (int i = 0; i < args.length - 1;) {
			Integer[] interval = {Integer.decode(args[i]), Integer.decode(args[++i])};
			inputList.add(interval);
		}

		List<Integer> outputList = lprimes(inputList);

        System.out.println(Arrays.toString(outputList.toArray()));
	}
}
