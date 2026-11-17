# Vault Burglary

A **maximum-flow** problem in disguise: how many gold bars can a crew carry out of a vault before the guards close every route? Solved with Dinic's algorithm and **vertex splitting**.


## The problem

The vault is a graph of rooms connected by corridors. Each corridor has a capacity, and every room can only be crossed by a limited number of people. Given a source room and an exit, compute the maximum number of groups that can pass through; each group carries a fixed number of bars, up to a maximum number of trips.

## The modelling trick

Edge capacities alone cannot express "this room can only be used *k* times". Each room is therefore split into an **in** node and an **out** node joined by a capacity-1 edge:

```
room i  →  i_in ──(1)──▶ i_out
```

Corridors then connect `out` nodes to `in` nodes. The max flow on this expanded graph is exactly the number of disjoint routes through the vault.

## Algorithm

Dinic's algorithm on the split graph:

1. Build the level graph with BFS.
2. Find blocking flows with DFS along level edges.
3. Repeat until the sink is unreachable.

Residual edges are stored as pairs pointing at each other, so augmenting a path updates the reverse edge in O(1).

## Input

```
3 10 5 6      # max trips, bars per trip, rooms, corridors
1 2
2 3
...
1 5           # entry room, exit room
```

- Line 1: `T` (max trips), `B` (bars per trip), `L` (rooms), `R` (corridors).
- Next `R` lines: corridor endpoints.
- Last line: entry and exit room.

## Output

```
30
```

The number of gold bars carried out: `min(maxGroups, T) × B`.

## Run

```bash
javac src/*.java -d out
java -cp out Main
```

A working example is in `src/tests.txt`.

## Structure

| File | Responsibility |
|---|---|
| `src/Main.java` | Input parsing and output |
| `src/EscapeRoutes.java` | Graph construction, node splitting, Dinic |
| `src/Edge.java` | Flow edge with residual pointer |
| `problem-statement.pdf` | Problem statement |
