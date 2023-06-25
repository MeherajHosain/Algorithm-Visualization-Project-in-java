import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

class Vertex {
    int x, y;

    Vertex(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

class Edge implements Comparable<Edge> {
    int src, dest, weight;

    Edge(int src, int dest, int weight) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }

    public int compareTo(Edge edge) {
        return this.weight - edge.weight;
    }
}

class Graph {
    int V, E;
    Edge[] edges;
    Vertex[] vertices;

    Graph(int V, int E) {
        this.V = V;
        this.E = E;
        edges = new Edge[E];
        vertices = new Vertex[V];
        for (int i = 0; i < E; i++)
            edges[i] = new Edge(0, 0, 0);
    }
}

class GraphPanel extends JPanel {
    private Graph graph;
    private Edge[] mstEdges;
    private boolean[] visited;
    private int currentIndex;
    private Timer timer;

    GraphPanel(Graph graph, Edge[] mstEdges) {
        this.graph = graph;
        this.mstEdges = mstEdges;
        this.visited = new boolean[graph.V];
        this.currentIndex = 0;
        setPreferredSize(new Dimension(600, 600));
    }

    void startAnimation() {
        timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentIndex < mstEdges.length) {
                    currentIndex++;
                    visited[mstEdges[currentIndex - 1].src] = true;
                    visited[mstEdges[currentIndex - 1].dest] = true;
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
        for (int i = 0; i < graph.V; i++) {
            Vertex vertex = graph.vertices[i];
            g.setColor(Color.BLACK);
            g.fillOval(vertex.x - 5, vertex.y - 5, 10, 10);

            // Draw the node labels
            g.setColor(Color.BLACK);
            g.drawString(Character.toString((char) ('A' + i)), vertex.x - 10, vertex.y - 10);
        }

        // Draw all the edges in the graph
        for (Edge edge : graph.edges) {
            Vertex src = graph.vertices[edge.src];
            Vertex dest = graph.vertices[edge.dest];
            g.setColor(Color.GRAY);
            g.drawLine(src.x, src.y, dest.x, dest.y);

            // Draw the weight of each edge
            g.setColor(Color.BLACK);
            int x = (src.x + dest.x) / 2;
            int y = (src.y + dest.y) / 2;
            g.drawString(Integer.toString(edge.weight), x, y);
        }

        // Highlight the visited nodes in green color
        g.setColor(Color.GREEN);
        for (int i = 0; i < graph.V; i++) {
            if (visited[i]) {
                Vertex vertex = graph.vertices[i];
                g.fillOval(vertex.x - 5, vertex.y - 5, 10, 10);
            }
        }

        // Highlight the MST edges in green color up to the currentIndex
        g.setColor(Color.GREEN);
        for (int i = 0; i < currentIndex; i++) {
            Edge edge = mstEdges[i];
            Vertex src = graph.vertices[edge.src];
            Vertex dest = graph.vertices[edge.dest];
            g.drawLine(src.x, src.y, dest.x, dest.y);
        }
    }
}

public class KruskalAlgorithm {
    static class MST{
    public  MST(){
        int V = 4;
        int E = 5;

        Graph graph = new Graph(V, E);

        graph.vertices[0] = new Vertex(100, 100);
        graph.vertices[1] = new Vertex(200, 200);
        graph.vertices[2] = new Vertex(300, 100);
        graph.vertices[3] = new Vertex(200, 300);

        graph.edges[0] = new Edge(0, 1, 10);
        graph.edges[1] = new Edge(0, 2, 6);
        graph.edges[2] = new Edge(0, 3, 5);
        graph.edges[3] = new Edge(1, 3, 15);
        graph.edges[4] = new Edge(2, 3, 4);

        // Sort the edges by weight
        Arrays.sort(graph.edges);

        // Kruskal's algorithm to find MST
        Edge[] mstEdges = new Edge[V - 1];
        int[] parent = new int[V];
        int[] rank = new int[V];
        for (int i = 0; i < V; i++) {
            parent[i] = i;
            rank[i] = 0;
        }

        GraphPanel graphPanel = new GraphPanel(graph, mstEdges);

        // Create the JFrame and add the GraphPanel
        JFrame frame = new JFrame("Minimum Spanning Tree Visualization");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(graphPanel);
        frame.pack();
        frame.setVisible(true);

        // Traverse the edges and add them to the MST one by one
        int edgeCount = 0;
        for (Edge edge : graph.edges) {
            int x = find(parent, edge.src);
            int y = find(parent, edge.dest);

            if (x != y) {
                mstEdges[edgeCount++] = edge;
                union(parent, rank, x, y);
                graphPanel.startAnimation();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private static int find(int[] parent, int i) {
        if (parent[i] != i)
            parent[i] = find(parent, parent[i]);
        return parent[i];
    }

    private static void union(int[] parent, int[] rank, int x, int y) {
        int xRoot = find(parent, x);
        int yRoot = find(parent, y);

        if (rank[xRoot] < rank[yRoot])
            parent[xRoot] = yRoot;
        else if (rank[yRoot] < rank[xRoot])
            parent[yRoot] = xRoot;
        else {
            parent[yRoot] = xRoot;
            rank[xRoot]++;
        }
    }
}
}

