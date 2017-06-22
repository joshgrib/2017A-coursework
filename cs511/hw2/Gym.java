import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Gym implements Runnable {
	private static final int GYM_SIZE = 30;
	private static final int GYM_REGISTERED_CLIENTS = 10000;
	private Map<WeightPlateSize, Integer> noOfWeightPlates;
	private ArrayList<Client> clients; // There's no point to a set here
	private ExecutorService executor;

    private static Map<WeightPlateSize, Semaphore> weightSems;
	private static Map<ApparatusType, Semaphore> atSems;
	private static Semaphore weightChangeMutex;

	public Gym() {
        noOfWeightPlates = Collections.synchronizedMap(new HashMap<WeightPlateSize, Integer>());
        //add amounts of each weight
        noOfWeightPlates.put(WeightPlateSize.SMALL_3KG, 40);
        noOfWeightPlates.put(WeightPlateSize.MEDIUM_5KG, 30);
        noOfWeightPlates.put(WeightPlateSize.LARGE_10KG, 20);

		atSems = Collections.synchronizedMap(new HashMap<ApparatusType, Semaphore>());
        //add semaphores for each machine
        atSems.put(ApparatusType.LEGPRESSMACHINE, new Semaphore(1));
		atSems.put(ApparatusType.BARBELL, new Semaphore(1));
        atSems.put(ApparatusType.HACKSQUATMACHINE, new Semaphore(1));
        atSems.put(ApparatusType.LEGEXTENSIONMACHINE, new Semaphore(1));
        atSems.put(ApparatusType.LEGCURLMACHINE, new Semaphore(1));
        atSems.put(ApparatusType.LATPULLDOWNMACHINE, new Semaphore(1));
		atSems.put(ApparatusType.PECDECKMACHINE, new Semaphore(1));
        atSems.put(ApparatusType.CABLECROSSOVERMACHINE, new Semaphore(1));

		weightSems = Collections.synchronizedMap(new HashMap<WeightPlateSize, Semaphore>());
        //add semaphores for each weight
        Semaphore smSem =  new Semaphore(noOfWeightPlates.get(WeightPlateSize.SMALL_3KG));
        Semaphore mdSem = new Semaphore(noOfWeightPlates.get(WeightPlateSize.MEDIUM_5KG));
        Semaphore lgSem = new Semaphore(noOfWeightPlates.get(WeightPlateSize.LARGE_10KG));
		weightSems.put(WeightPlateSize.SMALL_3KG, smSem);
		weightSems.put(WeightPlateSize.MEDIUM_5KG, mdSem);
		weightSems.put(WeightPlateSize.LARGE_10KG, lgSem);

		weightChangeMutex = new Semaphore(1);

		//create all the clients
		clients = new ArrayList<Client>();
		for (int i = 0; i < GYM_REGISTERED_CLIENTS; i++) clients.add(Client.generateRandom(i, noOfWeightPlates));
	}

	public void run() {
		executor = Executors.newFixedThreadPool(GYM_SIZE);
		for (Client c : clients) {
			executor.execute(new Runnable() {
					public void run() {
						ArrayList<Exercise> routine = c.getRoutine();
						for (Exercise e: routine) {
							try {
								// Get access to the machine
								atSems.get(e.getAt()).acquire();
								System.out.println("Client " + c.getId() + " got a machine!");

								int sm = e.getWeight().get(WeightPlateSize.SMALL_3KG);
								int md = e.getWeight().get(WeightPlateSize.MEDIUM_5KG);
								int lg = e.getWeight().get(WeightPlateSize.LARGE_10KG);

								System.out.println("Client " + c.getId() + " wants weights S:" + sm + ", M:" + md + ", and L:" + lg);

                                //get the weight you need
								weightChangeMutex.acquire();
								for (int i = 0; i < sm; i++) weightSems.get(WeightPlateSize.SMALL_3KG).acquire();
								for (int i = 0; i < md; i++) weightSems.get(WeightPlateSize.MEDIUM_5KG).acquire();
								for (int i = 0; i < lg; i++) weightSems.get(WeightPlateSize.LARGE_10KG).acquire();
								System.out.println("Client " + c.getId() + " has all of their weights.");
								weightChangeMutex.release();

								Thread.sleep(e.getDuration());

								for (int i = 0; i < sm; i++) weightSems.get(WeightPlateSize.SMALL_3KG).release();
								for (int i = 0; i < md; i++) weightSems.get(WeightPlateSize.MEDIUM_5KG).release();
								for (int i = 0; i < lg; i++) weightSems.get(WeightPlateSize.LARGE_10KG).release();
								System.out.println("Client " + c.getId() + " has put back all of their weights like a respectable gym-goer.");

                                atSems.get(e.getAt()).release();
								System.out.println("Client " + c.getId() + " has left a machine!");

								System.out.println("Client " + c.getId() + " is all tired out for today!");
							} catch (InterruptedException e1) {
								e1.printStackTrace();
							}
						}
					}
				});
		}
		executor.shutdown();
		try {
			executor.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
