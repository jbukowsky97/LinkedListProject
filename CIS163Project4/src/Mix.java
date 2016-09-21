import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**********************************************************************
 * 
 * This is a class that mixes a string and saves the decryption code
 * to a file, made for CIS 162
 * 
 * @author Jonah Bukowsky
 * @version 1.0
 *********************************************************************/

public class Mix implements IMix {

	/** a linked list to hold the encrypted message */
	private LinkedList linkedList;

	/** a binary tree that holds all of the clipboards */
	private BinarySearchTree clipboards;

	/** a scanner to read input */
	private Scanner scanner;

	/** determines if the mixture loop is running */
	private boolean running;

	/** determines if the program is in testing mode */
	private boolean testing;

	/** the current encrypted message */
	private String curMessage;

	/** the decryption code */
	private String unMixCode;

	/******************************************************************
	 * 
	 * Mix constructor to set the initial values of the instance
	 * variables
	 *****************************************************************/
	public Mix() {
		linkedList = new LinkedList();
		clipboards = new BinarySearchTree();
		scanner = new Scanner(System.in);
		running = true;
		testing = false;
		curMessage = "";
		unMixCode = "";
	}

	/******************************************************************
	 * 
	 * a loop that runs while the user is entering commands, dealing
	 * with them accordingly
	 *****************************************************************/
	private void mixLoop() {
		System.out.println("Enter your secret message: ");
		this.setInitialMessage(scanner.nextLine());
		while (running) {
			System.out.println();
			for (int i = 0; i < linkedList.size(); i++) {
				System.out.print(i + " ");
			}
			System.out.println();
			for (int i = 0; i < linkedList.size(); i++) {
				String blankSpace = " ";
				int tempI = i;
				while (tempI / 10 != 0) {
					blankSpace += " ";
					tempI /= 10;
				}
				System.out.print(curMessage.charAt(i) + blankSpace);
			}
			System.out.println();
			System.out.println();
			System.out.println("Commands:\n"
					+ "Q              quit\n"
					+ "a c #          insert char 'c' after position #\n"
					+ "r c            removes all character 'c's\n"
					+ "c # % &        cut to clipboard &, from # to %" +
					"(inclusive)\n"
					+ "p # &          paste from clipboard &, starting after #\n"
					+ "s filename     saves unmix key to a text file named "
					+ "'filename'\n");
			this.processCommand(scanner.nextLine());
		}
		System.out.println(
				"Your encrypted message is:\n" + curMessage);
	}

	/******************************************************************
	 * 
	 * sets the original message and linkedList accordingly
	 * 
	 * @param message
	 *            the original message
	 *****************************************************************/
	@Override
	public void setInitialMessage(String message) {
		linkedList.setToString(message);
		curMessage = linkedList.toString();
	}

	/******************************************************************
	 * 
	 * processes a command that the user entered
	 * 
	 * @param command
	 *            the command being handled
	 * 
	 * @return String the resulting encrypted message from the
	 *         command
	 *****************************************************************/
	@Override
	public String processCommand(String command) {
		if (this.isValid("c", command)) {
			// structure matches the quit command, check to see if
			// the first
			// char is right
			if (command.charAt(0) == 'Q') {
				running = false;
			} else {
				System.out
						.print((testing) ? "" : "Invalid command!");
			}
		} else if (this.isValid("c| |c| |n", command)) {
			// structure matches the insert command, check to see if
			// the first
			// char is right
			if (command.charAt(0) == 'a') {
				char insertChar = command.charAt(2);
				int index = Integer.parseInt(command.substring(4));
				if (linkedList.insert(insertChar, index)) {
					unMixCode = "r" + (index + 1) + unMixCode;
				} else {
					System.out.println("Invalid index!");
				}
			} else {
				System.out
						.print((testing) ? "" : "Invalid command!");
			}
		} else if (this.isValid("c| |c", command)) {
			// structure matches the remove command, check to see if
			// the first
			// char is right
			if (command.charAt(0) == 'r') {
				char removeChar = command.charAt(2);
				linkedList.remove(removeChar);
				for (int i = curMessage.length() - 1; i >= 0; i--) {
					if (removeChar == curMessage.charAt(i)) {
						unMixCode = "a" + removeChar + (i - 1)
								+ unMixCode;
					}
				}
			} else {
				System.out
						.print((testing) ? "" : "Invalid command!");
			}
		} else if (this.isValid("c| |n| |n| |n", command)) {
			// structure matches the cut command, check to see if
			// the first char
			// is right
			if (command.charAt(0) == 'c') {
				command = command.substring(2);
				int space1 = command.indexOf(" ");
				int space2 = command.indexOf(" ", space1 + 1);
				int startIndex = Integer
						.parseInt(command.substring(0, space1));
				int endIndex = Integer.parseInt(
						command.substring(space1 + 1, space2));
				int clipboardIndex = Integer
						.parseInt(command.substring(space2 + 1));
				boolean ok = true;
				if (startIndex < 0
						|| startIndex >= curMessage.length()) {
					ok = false;
				}
				if (endIndex < startIndex || endIndex < 0
						|| endIndex >= curMessage.length()) {
					ok = false;
				}
				if (clipboardIndex < 0 || clipboardIndex >= 1000) {
					ok = false;
				}
				if (ok) {
					LinkedList temp = new LinkedList();
					temp.setToString(linkedList.toString()
							.substring(startIndex, endIndex + 1));
					clipboards.add(clipboardIndex, temp.getTop());
					for (int i = endIndex; i >= startIndex; i--) {
						unMixCode = "a"
								+ curMessage.toString().charAt(i)
								+ (i - 1) + unMixCode;
						linkedList.removeAt(i);
					}
				} else {
					System.out.print(
							(testing) ? "" : "Invalid command!");
				}
			} else {
				System.out
						.print((testing) ? "" : "Invalid command!");
			}
		} else if (this.isValid("c| |n| |n", command)) {
			// structure matches the paste command, check to see if
			// the first
			// char is right
			if (command.charAt(0) == 'p') {
				command = command.substring(2);
				int spc1 = command.indexOf(' ');
				int startIndex = Integer
						.parseInt(command.substring(0, spc1));
				int clipboardIndex = Integer
						.parseInt(command.substring(spc1 + 1));
				boolean ok = true;
				if (startIndex < 0
						|| startIndex >= curMessage.length()) {
					if (startIndex != -1) {
						ok = false;
					}
				}
				if (clipboardIndex < 0 || clipboardIndex >= 1000) {
					ok = false;
				}
				LinkedList temp = new LinkedList();
				temp.setTop(clipboards.get(clipboardIndex));
				if (temp.getTop() == null) {
					ok = false;
				}
				if (ok) {
					for (char c : temp.toString().toCharArray()) {
						if (linkedList.insert(c, startIndex)) {
							unMixCode = "r" + (startIndex + 1)
									+ unMixCode;
						}
						startIndex++;
					}
				} else {
					System.out.print(
							(testing) ? "" : "Invalid command!");
				}
			} else {
				System.out
						.print((testing) ? "" : "Invalid command!");
			}
		} else if (this.isValid("c| |s", command)) {
			// structure matches the save command, check to see if
			// the first
			// char is right
			if (command.charAt(0) == 's') {
				String fileName = command.substring(2,
						command.length());
				try {
					PrintWriter saver = new PrintWriter(
							new BufferedWriter(
									new FileWriter(fileName)));
					saver.print(unMixCode);
					saver.close();
					if (!testing) {
						System.out.println("Saved successfully");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				System.out
						.print((testing) ? "" : "Invalid command!");
			}
		} else {
			System.out.print((testing) ? "" : "Invalid command!");
		}
		curMessage = linkedList.toString();
		return curMessage;
	}

	/******************************************************************
	 * 
	 * checks a command to see if it is valid against a certain
	 * structure defined by the programmer
	 * 
	 * @param structure
	 *            the structure the command is being checked against
	 * @param command
	 *            the command that is being checked against
	 * 
	 * @return boolean true if valid, false if not
	 *****************************************************************/
	private boolean isValid(String structure, String command) {
		// checks to see if the provided structure matches the
		// command
		// 'c' = char
		// 'n' = number (can take up more than one character, and
		// can be
		// negative
		// ' ' = space
		// 's' = string (that has to go till the end of the command
		// with no
		// spaces
		String[] format = structure.split("|");
		boolean valid = true;
		int curIndex = 0;
		forloop: for (String str : format) {
			switch (str.charAt(0)) {
			case 'c':
				if (command.length() > curIndex) {
					if (!Character
							.isAlphabetic(command.charAt(curIndex))
							&& !Character.isWhitespace(
									command.charAt(curIndex))) {
						valid = false;
						break forloop;
					}
					curIndex++;
				} else {
					valid = false;
					break forloop;
				}
				break;
			case 'n':
				if (command.length() > curIndex) {
					boolean wait = false;
					if (!Character.isDigit(command.charAt(curIndex))
							&& command.charAt(curIndex) != '-') {
						valid = false;
						break forloop;
					}
					if (command.charAt(curIndex) == '-') {
						wait = true;
					}
					curIndex++;
					while (command.length() > curIndex && Character
							.isDigit(command.charAt(curIndex))) {
						wait = false;
						curIndex++;
					}
					if (wait) {
						valid = false;
						break forloop;
					}
				} else {
					valid = false;
					break forloop;
				}
				break;
			case ' ':
				if (command.length() > curIndex) {
					if (!Character.isWhitespace(
							command.charAt(curIndex))) {
						valid = false;
						break forloop;
					}
					curIndex++;
				} else {
					valid = false;
					break forloop;
				}
				break;
			case 's':
				if (command.length() > curIndex) {
					while (command.length() > curIndex) {
						if (!Character.isAlphabetic(
								command.charAt(curIndex))
								&& command
										.charAt(curIndex) != '.') {
							valid = false;
							break forloop;
						}
						curIndex++;
					}
				} else {
					valid = false;
					break forloop;
				}
				break;
			}
		}
		if (curIndex != command.length()) {
			valid = false;
		}
		return valid;
	}

	/******************************************************************
	 * 
	 * sets the testing mode to the value given
	 * 
	 * @param t
	 *            boolean to set testing to true or false
	 *****************************************************************/
	public void setTesting(boolean t) {
		testing = t;
	}

	/******************************************************************
	 * 
	 * main method to start running the program
	 * 
	 * @param args
	 *            the arguments given at the start of the program
	 *****************************************************************/
	public static void main(String[] args) {
		Mix mix = new Mix();
		mix.mixLoop();
	}

}
