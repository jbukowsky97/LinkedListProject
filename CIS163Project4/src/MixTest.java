import java.util.ArrayList;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

public class MixTest {

	@Test
	public void testProcessCommand() {
		for (int go = 0; go < 1000; go++) {
			Mix message = new Mix();
			message.setTesting(true);
			message.setInitialMessage(
					"We the people of the United States, in order to"
							+ "form a more perfect union, establish "
							+ "justice, insure domestic tranquility, "
							+ "provide for the common defense, promote the "
							+ "general welfare, and secure the blessings"
							+ " of liberty to ourselves and our posterity, "
							+ "do ordain and establish this Constitution"
							+ " for the United States of America.");
			Random random = new Random();
			ArrayList<Integer> validClipboards = new ArrayList<Integer>();
			for (int i = 0; i < 200; i++) {
				int decider = random.nextInt(6);
				if (decider == 0) {
					char c = (random.nextInt(20) == 7) ? ' '
							: (char) (random.nextInt(26) + 97);
					message.processCommand(
							"a " + c + " "
									+ (random.nextInt(message
											.toString().length())
									- 1));
				} else if (decider == 1) {
					char c = (random.nextInt(100) == 7) ? ' '
							: (char) (random.nextInt(26) + 97);
					message.processCommand("r " + c);
				} else if (decider == 2) {
					char c = (random.nextInt(20) == 7) ? ' '
							: (char) (random.nextInt(26) + 65);
					message.processCommand(
							"a " + c + " "
									+ (random.nextInt(message
											.toString().length())
									- 1));
				} else if (decider == 3) {
					char c = (random.nextInt(100) == 7) ? ' '
							: (char) (random.nextInt(26) + 65);
					message.processCommand("r " + c);
				} else if (decider == 4) {
					int clip = random.nextInt(1000);
					message.processCommand("c "
							+ (random.nextInt(
									message.toString().length())
							- 1)
							+ " "
							+ (random.nextInt(
									message.toString().length())
									- 1)
							+ " " + clip);
					validClipboards.add(clip);
				} else if (decider == 5) {
					if (validClipboards.size() > 0) {
						message.processCommand("p "
								+ (random.nextInt(
										message.toString().length())
								- 1) + " "
								+ random.nextInt(
										validClipboards.size()));
					}
				}
			}
			message.processCommand("s test.txt");
			String codedMessage = message.processCommand("Q");
			System.out.println(codedMessage);

			UnMix decode = new UnMix();
			decode.setMessage(codedMessage);
			String decodedMessage = decode
					.UnMixUsingFile("test.txt", codedMessage);
			Assert.assertEquals(
					"We the people of the United States, in order to"
							+ "form a more perfect union, establish "
							+ "justice, insure domestic tranquility, "
							+ "provide for the common defense, promote the "
							+ "general welfare, and secure the blessings"
							+ " of liberty to ourselves and our posterity, "
							+ "do ordain and establish this Constitution"
							+ " for the United States of America.",
					decodedMessage);
		}
	}

	@Test
	public void testWeirdEntries() {
		for (int go = 0; go < 100000; go++) {
			Mix message = new Mix();
			message.setTesting(true);
			message.setInitialMessage(
					"We the people of the United States, in order to"
							+ "form a more perfect union, establish "
							+ "justice, insure domestic tranquility, "
							+ "provide for the common defense, promote the "
							+ "general welfare, and secure the blessings"
							+ " of liberty to ourselves and our posterity, "
							+ "do ordain and establish this Constitution"
							+ " for the United States of America.");
			Random random = new Random();
			int cmdLength = random.nextInt(10);
			String command = "";
			for (int i = 0; i < cmdLength; i++) {
				int decider = random.nextInt(5);
				if (decider == 0) {
					char c = (char) (random.nextInt(26) + 97);
					if (c == 's') {
						c = 'S';
					}
					command += c;
				} else if (decider == 1) {
					command += (char) (random.nextInt(26) + 65);
				} else if (decider == 2) {
					command += random.nextInt(10);
				} else if (decider == 3) {
					command += " ";
				} else if (decider == 4) {
					command += "-";
				}
			}
			message.processCommand(command);
			System.out.println(command);
		}
	}
}
