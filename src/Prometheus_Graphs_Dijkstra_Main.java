import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class Heap {
	static class Value{
		int a; 
		int b;
		Value(int a, int b){
			this.a = a;
			this.b = b;
		}
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
		if (p <= size && heap[p].a < heap[i].a)
			min = p;
		else
			min = i;
		if (q <= size && heap[q].a < heap[min].a)
			min = q;
		if (min != i) {
			Value temp = heap[i];
			heap[i] = heap[min];
			heap[min] = temp;
			minHeapify(min);
		}
	}

	void insert(int key, int value) {
		size++;
		heap[size] = new Value(key, Integer.MAX_VALUE);
		decreaseKey(size, key);
	}

	void decreaseKey(int i, int key) {
		heap[i].a = key;
		while (par(i) != 0 & heap[par(i)].a > heap[i].a) {
			Value temp = heap[par(i)];
			heap[par(i)] = heap[i];
			heap[i] = temp;
			i = par(i);
		}
	}

	Value min() {
		return heap[1];
	}

	Value extractMin() {
		Value min = heap[1];
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
		MinHeap heap = new MinHeap(n);
		heap.heap[1] = new Heap.Value(0, start);
		for (int i = 1; i<start; i++)
			heap.heap[i+1] = new Heap.Value(Integer.MAX_VALUE, i);
		for (int i = start + 1; i<=n;i++)
			heap.heap[i] = new Heap.Value(Integer.MAX_VALUE, i);
		int[] A = new int[n+1];
		for (int i = 1; i < A.length; i++) {/////////////////exclude start
			A[i] = Integer.MAX_VALUE;
		}
		int[] B = new int[n+1];
		while (heap.size > 0){
			Heap.Value next = heap.extractMin();
			Map<Integer, Integer> nextMap = map.get(next.b);
			for (Integer s : nextMap.keySet()){
				
			}
			
			
		}
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
