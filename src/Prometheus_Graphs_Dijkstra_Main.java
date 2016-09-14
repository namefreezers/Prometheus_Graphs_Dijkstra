import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class Heap {
	static class Value {
		int key;
		int top;

		Value(int key, int top) {
			this.key = key;
			this.top = top;
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

	HashMap<Integer, Integer> indexHeap = new HashMap<>();

	MinHeap(int n) {
		super(n);
	}

	void minHeapify(int i) {
		int p = left(i), q = right(i);
		int min;
		if (p <= size && heap[p].key < heap[i].key)
			min = p;
		else
			min = i;
		if (q <= size && heap[q].key < heap[min].key)
			min = q;
		if (min != i) {
			Value temp = heap[i];
			indexHeap.replace(temp.top, min);
			heap[i] = heap[min];
			indexHeap.replace(heap[i].top, i);
			heap[min] = temp;
			minHeapify(min);
		}
	}

	void insert(int key, int value) {
		size++;
		heap[size] = new Value(key, Integer.MAX_VALUE);
		decreaseKey(size, key);
	}

	void decreaseTop(int top, int key) {
		int ind = indexHeap.get(top);
		heap[ind].key = key;
		while (par(ind) != 0 && heap[par(ind)].key > heap[ind].key) {
			Value temp = heap[par(ind)];
			heap[par(ind)] = heap[ind];
			heap[ind] = temp;
			indexHeap.replace(temp.top, ind); ////////////////////////////////////
			ind = par(ind);
		}
		indexHeap.replace(top, ind);
	}

	int decreaseKey(int i, int key) {
		heap[i].key = key;
		while (par(i) != 0 & heap[par(i)].key > heap[i].key) {
			Value temp = heap[par(i)];
			heap[par(i)] = heap[i];
			heap[i] = temp;
			i = par(i);
		}
		return i;
	}

	Value min() {
		return heap[1];
	}

	Value extractMin() {
		Value min = heap[1];
		indexHeap.remove(min.top);
		heap[1] = heap[size];
		indexHeap.replace(heap[1].top, 1);
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

				HashMap<Integer, Integer> innerMap = map.get(first);

				if (!innerMap.containsKey(second)) {
					innerMap.put(second, third);
				} else {
					if (innerMap.get(second) > third) {
						innerMap.replace(second, third);
					}
				}
			}
		} catch (IOException e) {
			System.out.println("IOExc " + e);
		}
	}

	void shortestPath(int start, int[] A, Map<Integer, Set<Integer>> B) {
		MinHeap heap = new MinHeap(n);
		// int[] A = new int[n + 1];
		// int[] B = new int[n + 1];
		heap.heap[1] = new Heap.Value(0, start);
		A[start] = 0;
		heap.indexHeap.put(start, 1);
		for (int i = 1; i < start; i++) {
			heap.heap[i + 1] = new Heap.Value(Integer.MAX_VALUE, i);
			A[i] = Integer.MAX_VALUE;
			heap.indexHeap.put(i, i + 1);
		}
		for (int i = start + 1; i <= n; i++) {
			heap.heap[i] = new Heap.Value(Integer.MAX_VALUE, i);
			A[i] = Integer.MAX_VALUE;
			heap.indexHeap.put(i, i);
		}
		heap.size = n;
		boolean[] researched = new boolean[n + 1];
		while (heap.size > 0) {
			Heap.Value next = heap.extractMin();
			researched[next.top] = true;
			Map<Integer, Integer> nextMap = map.get(next.top);
			start = next.top;
			for (Integer s : nextMap.keySet()) {
				if (!researched[s])
					if (A[s] == A[start] + nextMap.get(s))
						B.get(s).add(start);
					else if (A[s] > A[start] + nextMap.get(s)) {
						A[s] = A[start] + nextMap.get(s);

						/*
						 * Set<Integer> set = B.get(s); set.clear();
						 * set.add(start);
						 */
						HashSet<Integer> set = new HashSet<>();
						set.add(start);
						B.put(s, set);

						heap.decreaseTop(s, A[s]);
					}
			}

		}
	}
}

public class Prometheus_Graphs_Dijkstra_Main {

	static int differentPaths(int start, Map<Integer, Set<Integer>> map) {
		Set<Integer> set = map.get(start);
		if (set == null || set.isEmpty())
			return 1;
		int i = 0;
		for (Integer j : set)
			i += differentPaths(j, map);
		return i;
	}

	public static void main(String[] args) {
		/*
		 * 
		 * String[] files = { "test_09/input_1_100.txt",
		 * "test_09/input_2_100.txt", "test_09/input_3_100.txt",
		 * "test_09/input_4_1000.txt", "test_09/input_5_10.txt",
		 * "test_09/input_6_10.txt", "test_09/input_7_10.txt",
		 * "test_09/input_8_10.txt", "test_09/USA-FLA.txt" }; for (String s :
		 * files) { System.out.println(new java.sql.Timestamp(new
		 * java.util.Date().getTime())); try (BufferedReader in = new
		 * BufferedReader(new FileReader(s))) { Graph g = new Graph(s); } catch
		 * (IOException e) { System.out.println("IOExc " + e); }
		 * 
		 * }
		 */
		System.out.println(new java.sql.Timestamp(new java.util.Date().getTime()));
		Graph g = new Graph("test_09/USA-FLA.txt");
		System.out.println(new java.sql.Timestamp(new java.util.Date().getTime()));
		int[] A = new int[g.n + 1];
		HashMap<Integer, Set<Integer>> B = new HashMap<>();
		g.shortestPath(100562, A, B);
		System.out.println(new java.sql.Timestamp(new java.util.Date().getTime()));
		System.out.println(A[1070345]);
		System.out.println(differentPaths(1070345, B));
		System.out.println(new java.sql.Timestamp(new java.util.Date().getTime()));
		// for (int i : A)
		// System.out.println(i);

		// System.out.println(new java.sql.Timestamp(new
		// java.util.Date().getTime()));
	}

}
