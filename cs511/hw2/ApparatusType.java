import java.util.concurrent.ThreadLocalRandom;
import java.util.Random;

public enum ApparatusType {
	LEGPRESSMACHINE, BARBELL, HACKSQUATMACHINE, LEGEXTENSIONMACHINE,
	LEGCURLMACHINE, LATPULLDOWNMACHINE, PECDECKMACHINE, CABLECROSSOVERMACHINE;

    private static final ApparatusType[] VALUES = values();
    private static final int SIZE = VALUES.length;
    private static final Random RANDOM = new Random();

	public static ApparatusType getRandom() {
		return VALUES[RANDOM.nextInt(SIZE)];
	}
}
