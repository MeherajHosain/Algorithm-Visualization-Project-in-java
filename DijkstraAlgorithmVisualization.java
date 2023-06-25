
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.Timer;

class Vertex1 {
    int x, y;

    Vertex1(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

class Edge1 {
    int src, dest, weight;

    Edge1(int src, int dest, int weight) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }
}

class Graph1 {
    int V, E;
    ArrayList<ArrayList<Edge1>> adjList;
    Vertex[] vertices;

    Graph1(int V) {
        this.V = V;
        this.E = 0;
        adjList = new ArrayList<>(V);
        for (int i = 0; i < V; i++)
            adjList.add(new ArrayList<>());
        vertices = new Vertex[V];
    }

    void addEdge1(int src, int dest, int weight) {
        Edge1 edge1 = new Edge1(src, dest, weight);
        adjList.get(src).add(edge1);
        E++;
    }
}

class GraphPanel1 extends JPanel {
    private Graph1 graph1;
    private int[] distances;
    private boolean[] visited;
    private int currentIndex;
    private Timer timer;

    GraphPanel1(Graph1 graph1, int[] distances) {
        this.graph1 = graph1;
        this.distances = distances;
        this.visited = new boolean[graph1.V];
        this.currentIndex = 0;
        setPreferredSize(new Dimension(600, 600));
    }

    void startAnimation() {
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentIndex < graph1.V) {
                    currentIndex++;
                    visited[currentIndex - 1] = true;
                    repaint();
                } else {
                    timer.stop();
                }
            }
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the vertices
        for (int i = 0; i < graph1.V; i++) {
            Vertex vertex = graph1.vertices[i];
            g.setColor(Color.BLACK);
            g.fillOval(vertex.x - 5, vertex.y - 5, 10, 10);

            // Draw the node labels
            g.setColor(Color.BLACK);
            g.drawString(Character.toString((char) ('A' + i)), vertex.x - 10, vertex.y - 10);
        }

        // Draw all the edges in the graph
        for (int src = 0; src < graph1.V; src++) {
            for (Edge1 edge1 : graph1.adjList.get(src)) {
                Vertex srcVertex = graph1.vertices[edge1.src];
                Vertex destVertex = graph1.vertices[edge1.dest];
                g.setColor(Color.GRAY);
                g.drawLine(srcVertex.x, srcVertex.y, destVertex.x, destVertex.y);

                // Draw the weight of each edge
                g.setColor(Color.BLACK);
                int x = (srcVertex.x + destVertex.x) / 2;
                int y = (srcVertex.y + destVertex.y) / 2;
                g.drawString(Integer.toString(edge1.weight), x, y);
            }
        }

        // Highlight the visited nodes in green color
        g.setColor(Color.GREEN);
        for (int i = 0; i < graph1.V; i++) {
            if (visited[i]) {
                Vertex vertex = graph1.vertices[i];
                g.fillOval(vertex.x - 5, vertex.y - 5, 10, 10);
            }
        }

        // Draw the shortest path distances
        g.setColor(Color.BLUE);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        for (int i = 0; i < graph1.V; i++) {
            Vertex vertex = graph1.vertices[i];
            int distance = distances[i];
            if (distance != Integer.MAX_VALUE) {
                g.drawString(Integer.toString(distance), vertex.x + 10, vertex.y - 10);
            }
        }
    }
}

public class DijkstraAlgorithmVisualization {
    static class ShortestPath{
        public ShortestPath(){
            int V = 5;
        Graph1 graph1 = new Graph1(V);

        graph1.vertices[0] = new Vertex(100, 100);
        graph1.vertices[1] = new Vertex(200, 200);
        graph1.vertices[2] = new Vertex(300, 100);
        graph1.vertices[3] = new Vertex(200, 300);
        graph1.vertices[4] = new Vertex(400, 200);

        graph1.addEdge1(0, 1, 5);
        graph1.addEdge1(0, 2, 3);
        graph1.addEdge1(1, 3, 2);
        graph1.addEdge1(2, 1, 1);
        graph1.addEdge1(2, 3, 1);
        graph1.addEdge1(3, 4, 3);

        int startNode = 0;

        int[] distances = dijkstra(graph1, startNode);

        GraphPanel1 graphPanel = new GraphPanel1(graph1, distances);

        // Create the JFrame and add the GraphPanel
        JFrame frame = new JFrame("Dijkstra's Algorithm Visualization");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(graphPanel);
        frame.pack();
        frame.setVisible(true);

        graphPanel.startAnimation();
    }

    private static int[] dijkstra(Graph1 graph1, int startNode) {
        int V = graph1.V;
        int[] distances = new int[V];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[startNode] = 0;

        PriorityQueue<VertexDistancePair> pq = new PriorityQueue<>(V, Comparator.comparingInt(vd -> vd.distance));
        pq.offer(new VertexDistancePair(startNode, 0));

        while (!pq.isEmpty()) {
            VertexDistancePair vd = pq.poll();
            int u = vd.vertex;

            for (Edge1 edge1 : graph1.adjList.get(u)) {
                int v = edge1.dest;
                int weight = edge1.weight;
                int newDistance = distances[u] + weight;

                if (newDistance < distances[v]) {
                    distances[v] = newDistance;
                    pq.offer(new VertexDistancePair(v, newDistance));
                }
            }
        }

        return distances;
    }

    static class VertexDistancePair {
        int vertex;
        int distance;

        VertexDistancePair(int vertex, int distance) {
            this.vertex = vertex;
            this.distance = distance;
        }
    }
        }
    }
   