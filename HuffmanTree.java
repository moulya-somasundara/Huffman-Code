

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

public class HuffmanTree {
	// create huffman tree
	public Node createTree(List<Node> nodes) {
		while (nodes.size() > 1) {
			Collections.sort(nodes);

			Node left = nodes.get(nodes.size() - 1);
			Node right = nodes.get(nodes.size() - 2);
			Node parent = new Node(null, left.getWeight() + right.getWeight());

			// set child node
			parent.setLeft(left);
			parent.setRight(right);

			nodes.remove(left);
			nodes.remove(right);

			// put it back to forest
			nodes.add(parent);
		}
		// return root
		return nodes.get(0);
	}

	// recursively generate huffman coding
	public void generateHuffmanCode(Node root) {
		if (root == null) {
			return;
		}
		if (root.getLeft() != null) {
			root.getLeft().setCode(root.getCode() + "0");
		}

		if (root.getRight() != null) {
			root.getRight().setCode(root.getCode() + "1");
		}

		generateHuffmanCode(root.getLeft());
		generateHuffmanCode(root.getRight());
	}

	// breadth-first traversal of a huffman tree
	public List<Node> bfs(Node root) {
		List<Node> list = new ArrayList<Node>();
		Queue<Node> queue = new ArrayDeque<Node>();

		if (root != null) {
			queue.offer(root);
		}

		while (!queue.isEmpty()) {
			list.add(queue.peek());
			Node node = queue.poll();

			if (node.getLeft() != null) {
				queue.offer(node.getLeft());
			}

			if (node.getRight() != null) {
				queue.offer(node.getRight());
			}
		}
		return list;
	}
}
