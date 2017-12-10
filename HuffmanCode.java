

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class HuffmanCode {
	public static void main(String[] args) {
		HuffmanCode code = new HuffmanCode();
		HuffmanTree tree = new HuffmanTree();
		List<Node> l1 = new ArrayList<Node>();
		
		Map<String, Integer> map = code.read("infile.dat");
		code.clearFile("outfile.dat");
		code.sort(map);
		
		//put all the symbol into a list in order to create a tree.
		Iterator<Entry<String, Integer>> iter = map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			String key = (String) entry.getKey();
			int val = (int) entry.getValue();
			l1.add(new Node(key, val));
		}

		if (l1.size() > 0) {

			Node root = tree.createTree(l1);
			
			if (root.getLeft() == null && root.getRight() == null) {
				//one node situation
				root.setCode("1");
			} else {
				//generate huffman coding
				tree.generateHuffmanCode(root);
			}

			int length = 0;
			String data;
			String output = "Huffman code"+'\n';
			System.out.print(output);
			List<Node> l2 = tree.bfs(root);
			for (Node node : l2) {
				if ((data = node.getSymbol()) != null) {
					System.out.println(node);
					output = output.concat(node.toString() + '\n');
					//calculate the length of the message
					length += map.get(data) * node.getCode().length();
				}
			}
			
			code.write("outfile.dat", output);
			code.write("outfile.dat", "the length of the coded message in terms of number of bits:   " + length);
			System.out.println("");
			System.out.println("the length of the coded message in terms of number of bits:   " + length);
		}

	}

	// read from infile.dat
	public Map<String, Integer> read(String path) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		try {
            File file = new File(path);
			Reader reader = new FileReader(file);

			int temp;

			while ((temp = reader.read()) != -1) {
				String symbol = String.valueOf((char) temp);
				if (isValid(temp) && map.get(symbol) == null) {
					map.put(symbol, 1);
				} else if (isValid(temp) && map.get(symbol) != null) {
					int value = map.get(symbol) + 1;
					map.put(symbol, value);
				}

			}
			reader.close();

			int sum = 0;
			Iterator<Map.Entry<String, Integer>> it = map.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, Integer> entry = it.next();
				int value = entry.getValue();
				sum = sum + value;
			}

			map.put("sum", sum);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;

	}
	
	//check validity
	public boolean isValid(int symbol) {
		if (48 <= symbol && symbol <= 57) {
			return true;
		} else if (65 <= symbol && symbol <= 90) {
			return true;
		} else if (97 <= symbol && symbol <= 122) {
			return true;
		} else {
			return false;
		}
	}
	
	//sort frequency from hashmap
	public void sort(Map<String, Integer> map) {
		String frequencyTable = "symbol " + "frequency" + '\n';
		System.out.print(frequencyTable);
		//sum of value
		int sum = map.get("sum");
		map.remove("sum");

		List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());

		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			// descending
			public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
				if (o1.getValue() < o2.getValue()) {
					return 1;
				}
				if (o1.getValue() > o2.getValue()) {
					return -1;
				}
				return 0;
			}
		});
		
		for (int i = 0; i < list.size(); i++) {
			String key = list.get(i).getKey();
			int value = list.get(i).getValue();
			String frequency = String.format("%.1f", (double) value / sum * 100);
			String line = key +", " + frequency + "%" + '\n';
			frequencyTable = frequencyTable.concat(line);
			System.out.print(line);
		}
		System.out.println("");
		//write to oufile.dat
		write("outfile.dat", frequencyTable);
	}
	
	//write to outfile.dat
	public void write(String path, String content) {

		try {
			File file = new File(path);
			if (!file.exists()) {
				file.createNewFile();
			}
			Writer writer = new FileWriter(file, true);
			writer.write(content);
			writer.write('\n');
			writer.flush();
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//clean up the outfile.dat when program start
	public void clearFile(String path) {
		File file = new File(path);
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter writer;
			writer = new FileWriter(file);
			writer.write("");
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
