import java.util.ArrayList;
import java.util.List;

public class PrimeFinder implements Runnable {
	private Integer start;
	private Integer end;
	private List<Integer> primes;

	PrimeFinder(Integer startNum, Integer endNum) {
		this.start = startNum;
		this.end = endNum;
	}

	public List<Integer> getPrimesList() {
		return primes;
	}

	public Boolean isPrime(int n) {
		if (n == 1) return false;
		if (n == 2) return true;
		for (int i = (int)Math.floor(Math.sqrt(n)); i>1; i--) {
			if (n%i == 0) return false;
		}
		return true;
	}

	public void run() {
		primes = new ArrayList<Integer>();
		for (Integer i = this.start; i < this.end; i++) {
			if (isPrime(i))
				primes.add(i);
		}
	}
}
