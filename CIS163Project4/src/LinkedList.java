
public class LinkedList {
	
	private LNode top;
	private LNode tail;
	
	public LinkedList() {
		top = null;
		tail = null;
	}
	
	public void append(char c) {
		if (top == null) {
			top = new LNode(c, null, null);
			tail = top;
			return;
		}
		tail = new LNode(c, tail, null);
		tail.getPrevious().setNext(tail);
	}
	
	public boolean insert(char c, int index) {
		if (index < -1 || index >= this.size()) {
			return false;
		}
		if (index == -1) {
			top = new LNode(c, null, top);
			if (top.getNext() == null) {
				tail = top;
			}else {
				top.getNext().setPrevious(top);
			}
			return true;
		}
		int counter = 0;
		LNode temp = top;
		while (counter < index) {
			temp = temp.getNext();
			counter++;
		}
		temp.setNext(new LNode(c, temp, temp.getNext()));
		if (temp.getNext().getNext() != null) {
			temp.getNext().getNext().setPrevious(temp.getNext());
		}
		return true;
	}
	
	public void removeAt(int index) {
		int counter = 0;
		LNode temp = top;
		if (counter == index) {
			top = top.getNext();
			if (top != null) {
				top.setPrevious(null);
			}
		}else {
			while (counter < index) {
				temp = temp.getNext();
				counter++;
			}
			temp.getPrevious().setNext(temp.getNext());
			if (temp.getNext() != null) {
				temp.getNext().setPrevious(temp.getPrevious());
			}
		}
	}
	
	public boolean removeSingle(char c) {
		LNode temp = top;
		if (top == null) {
			return false;
		}
		if (temp.getCharacter() == c) {
			top = top.getNext();
			if (top != null) {
				top.setPrevious(null);
			}
			return true;
		}
		while (temp.getNext() != null) {
			temp = temp.getNext();
			if (temp.getCharacter() == c) {
				temp.getPrevious().setNext(temp.getNext());
				if (temp.getNext() != null) {
					temp.getNext().setPrevious(temp.getPrevious());
				}
				return true;
			}
		}
		return false;
	}
	
	public void remove(char c) {
		while (removeSingle(c));
	}
	
	public void setToString(String s) {
		top = null;
		tail = null;
		for (char c : s.toCharArray()) {
			this.append(c);
		}
	}
	
	public int size() {
		LNode temp = top;
		int size = 0;
		if (temp == null) {
			return size;
		}else {
			size = 1;
		}
		while (temp.getNext() != null) {
			temp = temp.getNext();
			size++;
		}
		return size;
	}
	
	public void setTop(LNode top) {
		this.top = top;
	}
	
	public LNode getTop() {
		return top;
	}
	
	public String toString() {
		String returnString = "";
		LNode temp = top;
		while (temp != null) {
			returnString += temp.getCharacter();
			temp = temp.getNext();
		}
		return returnString;
	}
}
