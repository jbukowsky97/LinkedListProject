
public class TNode {
	
	private int index;
	private LNode top;
	private TNode left;
	private TNode right;
	
	public TNode(int index, LNode top, TNode left, TNode right) {
		this.index = index;
		this.top = top;
		this.left = left;
		this.right = right;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	public int getIndex() {
		return index;
	}
	
	public void setTop(LNode top) {
		this.top = top;
	}
	
	public LNode getTop() {
		return top;
	}

	public void setLeft(TNode left) {
		this.left = left;
	}
	
	public TNode getLeft() {
		return left;
	}
	
	public void setRight(TNode right) {
		this.right = right;
	}

	public TNode getRight() {
		return right;
	}
}
