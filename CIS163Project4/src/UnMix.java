import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.plaf.synth.SynthStyle;

/**********************************************************************
 * 
 * This is a class that takes an encrypted message and decrypts it
 * using a file that is specified by the user, made for CIS 162
 * 
 * @author Jonah Bukowsky
 * @version 1.0
 *********************************************************************/

public class UnMix implements IUnMix {

	/** a scanner to get user input */
	private Scanner scanner;

	/** linkedList of the message that is being decrypted */
	private LinkedList linkedList;

	/******************************************************************
	 * 
	 * UnMix constructor that initializes the instance variables
	 *****************************************************************/
	public UnMix() {
		scanner = new Scanner(System.in);
		linkedList = new LinkedList();
	}

	/******************************************************************
	 * 
	 * method to get the message being decrypted and the file that
	 * is decrypting the message
	 *****************************************************************/
	private void dMix() {
		System.out.println("Enter the message being decrypted");
		String message = scanner.nextLine();
		this.setMessage(message);
		System.out.println("Enter the name of the decryption file");
		String file = scanner.nextLine();
		System.out.println(this.UnMixUsingFile(file, message));
	}

	/******************************************************************
	 * 
	 * decrypts the message using the file provided
	 * 
	 * @param filename
	 *            the name of the file
	 * @param mixedMessage
	 *            the encrypted message
	 * 
	 * @return String the decrypted message
	 *****************************************************************/
	@Override
	public String UnMixUsingFile(String filename,
			String mixedMessage) {
		String commands = "";
		try {
			Scanner reader = new Scanner(new File(filename));
			if (reader.hasNextLine()) {
				commands = reader.nextLine();
			}
			reader.close();
		} catch (IOException e) {
			System.out.println("File not found");
		}
		while (commands.length() > 0) {
			int temp;
			int insIndex;
			String strIndex;
			switch (commands.charAt(0)) {
			case 'a':
				char insChar = commands.charAt(1);
				strIndex = "";
				temp = 2;
				while (Character.isDigit(commands.charAt(temp))
						|| commands.charAt(temp) == '-') {
					strIndex += commands.substring(temp, temp + 1);
					temp++;
					if (temp >= commands.length()) {
						break;
					}
				}
				insIndex = Integer.parseInt(strIndex);
				commands = commands.substring(temp);
				linkedList.insert(insChar, insIndex);
				break;
			case 'r':
				strIndex = "";
				temp = 1;
				while (Character.isDigit(commands.charAt(temp))) {
					strIndex += commands.substring(temp, temp + 1);
					temp++;
					if (temp >= commands.length()) {
						break;
					}
				}
				insIndex = Integer.parseInt(strIndex);
				commands = commands.substring(temp);
				linkedList.removeAt(insIndex);
				break;
			}
		}
		return linkedList.toString();
	}

	/******************************************************************
	 * 
	 * sets the initial encrypted message
	 * 
	 * @param s
	 *            the encrypted message
	 *****************************************************************/
	public void setMessage(String s) {
		for (char c : s.toCharArray()) {
			linkedList.append(c);
		}
	}

	/******************************************************************
	 * 
	 * main method to start running the program
	 * 
	 * @param args
	 *            the arguments given at the start of the program
	 *****************************************************************/
	public static void main(String[] args) {
		UnMix m = new UnMix();
		m.dMix();
	}
}
