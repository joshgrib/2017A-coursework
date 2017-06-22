import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class Client {
	private int id;
	private ArrayList<Exercise> routine;

	public Client(int id) {
		this.id = id;
		this.routine = new ArrayList<Exercise>();
	}

	public void addExercise(Exercise e) {
		routine.add(e);
	}

	public static Client generateRandom(int id, Map<WeightPlateSize, Integer> noOfWeightPlates) {
		int exerciseCount = ThreadLocalRandom.current().nextInt(15, 21);
		Client client = new Client(id);
		for (int i = 0; i < exerciseCount; i++) client.addExercise(Exercise.generateRandom(noOfWeightPlates));
		return client;
	}

	public int getId() {
		return id;
	}

	public ArrayList<Exercise> getRoutine() {
		return routine;
	}
}
