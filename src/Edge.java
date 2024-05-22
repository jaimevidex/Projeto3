public class Edge {
    int from, capacity, to;
    Edge residual;

    Edge(int from, int to, int capacity) {
        this.from = from;
        this.capacity = capacity;
        this.to = to;
    }
}
