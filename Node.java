

//tree node  
public class Node implements Comparable<Node> {
	private String code = "";
	private String symbol;
	private int weight;
	//left child
	private Node left;
	//right child
	private Node right;

	public Node(String symbol, int weight) {
		this.symbol = symbol;
		this.weight = weight;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public Node getLeft() {
		return left;
	}

	public void setLeft(Node left) {
		this.left = left;
	}

	public Node getRight() {
		return right;
	}

	public void setRight(Node right) {
		this.right = right;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {

		return this.symbol + "  " + this.code;
	}

	@Override
	public int compareTo(Node o) {
		if (o.getWeight() > this.getWeight()) {
			return 1;
		}
		if (o.getWeight() < this.getWeight()) {
			return -1;
		}

		return 0;
	}
}