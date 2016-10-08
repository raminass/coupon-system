package test;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class randomizer {

	final String lexicon = "ABCDEFGHIJKLMNOPQRSTUVWXYZ12345674890";

	final java.util.Random rand = new java.util.Random();

	// consider using a Map<String,Boolean> to say whether the identifier is
	// being used or not
	final Set<String> identifiers = new HashSet<String>();

	long LOWER_RANGE = 0; // assign lower range value
	long UPPER_RANGE = 1000000; // assign upper range value

	public long randID() {
		Random random = new Random();

		return LOWER_RANGE + (long) (random.nextDouble() * (UPPER_RANGE - LOWER_RANGE));

	}

	public String randomIdentifier() {
		StringBuilder builder = new StringBuilder();
		while (builder.toString().length() == 0) {
			int length = rand.nextInt(5) + 5;
			for (int i = 0; i < length; i++)
				builder.append(lexicon.charAt(rand.nextInt(lexicon.length())));
			if (identifiers.contains(builder.toString()))
				builder = new StringBuilder();
		}
		return builder.toString();
	}

}
