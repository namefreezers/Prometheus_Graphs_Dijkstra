import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

class Heap {
	class Value{
		int a; 
		int b;
	}
	Value[] heap;
	int size;

	Heap(int n) {
		heap = new Value[n + 2];
		size = 0;
	}

	int par(int i) {
		return i / 2;
	}

	int left(int i) {
		return i * 2;
	}

	int right(int i) {
		return i * 2 + 1;
	}
}


class MinHeap extends Heap {
	MinHeap(int n) {
		super(n);
	}

	void minHeapify(int i) {
		int p = left(i), q = right(i);
		int min;
		if (p <= size && heap[p] < heap[i])
			min = p;
		else
			min = i;
		if (q <= size && heap[q] < heap[min])
			min = q;
		if (min != i) {
			int temp = heap[i];
			heap[i] = heap[min];
			heap[min] = temp;
			minHeapify(min);
		}
	}

	void insert(int key) {
		size++;
		heap[size] = Integer.MAX_VALUE;
		decreaseKey(size, key);
	}

	void decreaseKey(int i, int key) {
		heap[i] = key;
		while (par(i) != 0 & heap[par(i)] > heap[i]) {
			int temp = heap[par(i)];
			heap[par(i)] = heap[i];
			heap[i] = temp;
			i = par(i);
		}
	}

	int min() {
		return heap[1];
	}

	int extractMin() {
		int min = heap[1];
		heap[1] = heap[size];
		size--;
		minHeapify(1);
		return min;
	}
}

class Graph {
	HashMap<Integer, HashMap<Integer, Integer>> map;
	int n, m;

	Graph() {

	}

	Graph(String file) {
		map = new HashMap<Integer, HashMap<Integer, Integer>>();
		try (BufferedReader in = new BufferedReader(new FileReader(file))) {
			String[] str = in.readLine().split(" ");
			n = Integer.parseInt(str[0]);
			m = Integer.parseInt(str[1]);
			for (int i = 1; i <= n; i++)
				map.put(i, new HashMap<Integer, Integer>());
			while (in.ready()) {
				str = in.readLine().split(" ");
				int first = Integer.parseInt(str[0]), second = Integer.parseInt(str[1]),
						third = Integer.parseInt(str[2]);

				HashMap<Integer, Integer> innerMap = map.get(first), innerMap2 = map.get(second);

				if (!innerMap.containsKey(second)) {
					innerMap.put(second, third);
					innerMap2.put(first, third);
				} else {
					if (innerMap.get(second) > third) {
						innerMap.replace(second, third);
						innerMap2.replace(first, third);
					}
				}
			}
		} catch (IOException e) {
			System.out.println("IOExc " + e);
		}
	}
	
	void shortestPath(int start){
		
	}
}

public class Prometheus_Graphs_Dijkstra_Main {

	public static void main(String[] args) {
		String[] files = { "test_09/input_1_100.txt", "test_09/input_2_100.txt", "test_09/input_3_100.txt",
				"test_09/input_4_1000.txt", "test_09/input_5_10.txt", "test_09/input_6_10.txt",
				"test_09/input_7_10.txt", "test_09/input_8_10.txt", "test_09/USA-FLA.txt" };
		for (String s : files) {
			System.out.println(new java.sql.Timestamp(new java.util.Date().getTime()));
			try (BufferedReader in = new BufferedReader(new FileReader(s))) {
				Graph g = new Graph(s);
			} catch (IOException e) {
				System.out.println("IOExc " + e);
			}
			
		}
		System.out.println(new java.sql.Timestamp(new java.util.Date().getTime()));
	}

}
