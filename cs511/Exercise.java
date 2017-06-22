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
		ApparatusType at = ApparatusType.getRandom();
		int exDuration = ThreadLocalRandom.current().nextInt(11);
		int sm = ThreadLocalRandom.current().nextInt(weight.get(WeightPlateSize.SMALL_3KG) + 1);
		int md = ThreadLocalRandom.current().nextInt(weight.get(WeightPlateSize.MEDIUM_5KG) + 1);
		int lg = ThreadLocalRandom.current().nextInt(weight.get(WeightPlateSize.LARGE_10KG) + 1);

		if ((sm + md + lg) != 0) {
			HashMap<WeightPlateSize, Integer> weights = new HashMap<WeightPlateSize, Integer>();
            weights.put(WeightPlateSize.LARGE_10KG, lg);
			weights.put(WeightPlateSize.MEDIUM_5KG, md);
            weights.put(WeightPlateSize.SMALL_3KG, sm);
			return new Exercise(at, weights, exDuration);
		}
        return generateRandom(weight);
	}
	public ApparatusType getAt() {
		return at;
	}
    public Map<WeightPlateSize, Integer> getWeight() {
		return weight;
	}
    public int getDuration() {
		return duration;
	}
	public void setAt(ApparatusType at) {
		this.at = at;
	}
	public void setWeight(Map<WeightPlateSize, Integer> weight) {
		this.weight = weight;
	}
	public void setDuration(int exDuration) {
		this.duration = exDuration;
	}
}
