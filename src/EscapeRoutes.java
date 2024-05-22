import java.util.*;

public class EscapeRoutes {
    int source;
    int sink;
    List<Edge>[] adj;
    int[] level;

    @SuppressWarnings("unchecked")
    public EscapeRoutes(int V) {
        int newSize = V*2 + 2;
        adj = new ArrayList[newSize];
        for (int i = 0; i < newSize; i++) {
            adj[i] = new ArrayList<>();
        }
        level = new int[newSize];
    }

    void addEdge(int from, int to) {
        int fromIn = from * 2;
        int fromOut = from * 2 + 1;
        int toIn = to * 2;
        int toOut = to * 2 + 1;

        // Ensure node splitting edges are created only once
        if (adj[fromIn].isEmpty()) {
            Edge e1 = new Edge(fromIn, fromOut, 1);
            Edge e2 = new Edge(fromOut, fromIn, 0);
            e1.residual = e2;
            e2.residual = e1;
            adj[fromIn].add(e1);
            adj[fromOut].add(e2);
        }

        if (adj[toIn].isEmpty()) {
            Edge e1 = new Edge(toIn, toOut, 1);
            Edge e2 = new Edge(toOut, toIn, 0);
            e1.residual = e2;
            e2.residual = e1;
            adj[toIn].add(e1);
            adj[toOut].add(e2);
        }

        Edge e3 = new Edge(fromOut, toIn, 1);
        Edge e4 = new Edge(toIn, fromOut, 0);
        e3.residual = e4;
        e4.residual = e3;
        adj[fromOut].add(e3);
        adj[toIn].add(e4);
    }

    boolean bfs() {
        Arrays.fill(level, -1);
        level[source * 2 + 1] = 0;
        Deque<Integer> q = new ArrayDeque<>();
        q.offer(source * 2 + 1);
        
        while (!q.isEmpty()) {
            int u = q.poll();
            for (Edge e : adj[u]) {
                if (level[e.to] < 0 && e.capacity > 0) {
                    level[e.to] = level[u] + 1;
                    q.offer(e.to);
                }
            }
        }
        return level[sink*2] >= 0;
    }

    boolean dfs(int u, int[] start, int flow) {
        if (u == sink * 2) return true;
        
        for (; start[u] < adj[u].size(); start[u]++) {
            Edge e = adj[u].get(start[u]);
            if (level[e.to] == level[u] + 1 && e.capacity > 0) {
                boolean pathExists = dfs(e.to, start, Math.min(flow, e.capacity));
                if (pathExists) {
                    e.capacity --;
                    e.residual.capacity ++;
                    return true;
                }
            }
        }
        return false;
    }

    int solve(int source, int sink) {
        this.source = source;
        this.sink = sink;

        int[] next = new int[adj.length];
        int total = 0;

        while (bfs()) {
            Arrays.fill(next, 0);
            while (dfs(source * 2 + 1, next, Integer.MAX_VALUE)) {
                total ++;
            }
        }
        return total;
    }
}
