
public class BinarySearchTree {
	
	private TNode root;
	
	public BinarySearchTree() {
		root = null;
	}
	
	public void add(int index, LNode top) {
		if (root == null) {
			root = new TNode(index, top, null, null);
			return;
		}
		this.recAdd(root, index, top);
	}
	
	private void recAdd(TNode node, int index, LNode top) {
		if (index == node.getIndex()) {
			node.setIndex(index);
			node.setTop(top);
		}else if (index < node.getIndex()) {
			if (node.getLeft() == null) {
				node.setLeft(new TNode(index, top, null, null));
			}else {
				this.recAdd(node.getLeft(), index, top);
			}
		}else if (index > node.getIndex()) {
			if (node.getRight() == null) {
				node.setRight(new TNode(index, top, null, null));
			}else {
				this.recAdd(node.getRight(), index, top);
			}
		}
	}
	
	public LNode get(int index) {
		TNode temp = this.recFind(root, index);
		if (temp != null) {
			return temp.getTop();
		}else {
			return null;
		}
	}
	
	private TNode recFind(TNode node, int index) {
		if (node == null) {
			return null;
		}
		int nodeIndex = node.getIndex();
		if (index == nodeIndex) {
			return node;
		}else if (index < nodeIndex) {
			return this.recFind(node.getLeft(), index);
		}else {
			return this.recFind(node.getRight(), index);
		}
	}
}
