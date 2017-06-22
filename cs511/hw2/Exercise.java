import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class Exercise {
	private ApparatusType at;
	private Map<WeightPlateSize, Integer> weight;
	private int duration;
	
	public Exercise(ApparatusType at, Map<WeightPlateSize, Integer> weight, int duration) {
		this.setAt(at);
		this.setWeight(weight);
		this.setDuration(duration);
	}
	
	public static Exercise generateRandom(Map<WeightPlateSize, Integer> weight) {
		// Determine the apparatus
		ApparatusType at = ApparatusType.getRandom();
		
		// Determine the duration
		// (between 0 and 10ms because we have 10,000 clients to get through)
		int duration = ThreadLocalRandom.current().nextInt(11);
		
		// Now pick random numbers from each weight class below the limits of the gym
		int small = ThreadLocalRandom.current().nextInt(weight.get(WeightPlateSize.SMALL_3KG) + 1);
		int medium = ThreadLocalRandom.current().nextInt(weight.get(WeightPlateSize.MEDIUM_5KG) + 1);
		int large = ThreadLocalRandom.current().nextInt(weight.get(WeightPlateSize.LARGE_10KG) + 1);
				
		if (small == 0 && medium == 0 && large == 0)
			return generateRandom(weight);
		else {
			// Load up them weights
			HashMap<WeightPlateSize, Integer> weights = 
				new HashMap<WeightPlateSize, Integer>();
			weights.put(WeightPlateSize.SMALL_3KG, small);
			weights.put(WeightPlateSize.MEDIUM_5KG, medium);
			weights.put(WeightPlateSize.LARGE_10KG, large);
			
			// Create that exercise
			return new Exercise(at, weights, duration);
		}
	}

	public ApparatusType getAt() {
		return at;
	}

	public void setAt(ApparatusType at) {
		this.at = at;
	}

	public Map<WeightPlateSize, Integer> getWeight() {
		return weight;
	}

	public void setWeight(Map<WeightPlateSize, Integer> weight) {
		this.weight = weight;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
}
