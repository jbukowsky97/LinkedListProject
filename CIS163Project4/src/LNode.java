import java.io.Serializable;

public class LNode implements Serializable {
	
	private char character;
	private LNode next;
	private LNode previous;
	
	public LNode(char character, LNode previous, LNode next) {
		this.character = character;
		this.previous = previous;
		this.next = next;
	}
	
	public void setCharacter(char c) {
		character = c;
	}
	
	public char getCharacter() {
		return character;
	}
	
	public void setPrevious(LNode p) {
		previous = p;
	}
	
	public LNode getPrevious() {
		return previous;
	}
	
	public void setNext(LNode n) {
		next = n;
	}
	
	public LNode getNext() {
		return next;
	}
}
