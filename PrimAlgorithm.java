/**
 0 1 1
 1 2 3
 0 2 2
 2 3 4
 2 5 6
 2 4 5
 3 4 7
 4 5 8

 */
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.*;

public class PrimAlgorithm {

    static class mst {

        public mst() {
        // উদাহরণ ইনপুট
int[][] graph = {
    {0, 1, 1, 0, 0, 0},
    {1, 0, 3, 0, 0, 0},
    {1, 3, 0, 4, 5, 6},
    {0, 0, 4, 0, 7, 0},
    {0, 0, 5, 7, 0, 8},
    {0, 0, 6, 0, 8, 0}
};

int sourceNode = 0;

PrimAlgorithm prim = new PrimAlgorithm();
prim.primMST(graph, sourceNode);

        
            

        }
    }
 
    // Class to represent a graph edge
    class Edge {
        int start, end, weight;

        Edge(int start, int end, int weight) {
            this.start = start;
            this.end = end;
            this.weight = weight;
        }
    }

    // Function to find minimum spanning tree using Prim's algorithm
    void primMST(int[][] graph, int sourceNode) {
        int numVertices = graph.length;

        // Array to store constructed MST
        int[] parent = new int[numVertices];

        // Key values used to pick minimum weight edge in cut
        int[] key = new int[numVertices];

        // Boolean array to represent whether a vertex is included in MST or not
        boolean[] mstSet = new boolean[numVertices];

        // Initialize all keys as INFINITE
        Arrays.fill(key, Integer.MAX_VALUE);

        // Start from the source node
        key[sourceNode] = 0; // Make key 0 so that this vertex is picked as the first vertex
        parent[sourceNode] = -1; // Source node is always the root of MST

        // The MST will have V vertices
        for (int i = 0; i < numVertices - 1; i++) {
            // Pick the minimum key vertex from the set of vertices not yet included in MST
            int minKeyIndex = getMinKeyIndex(key, mstSet);
            mstSet[minKeyIndex] = true;

            // Update key and parent arrays
            for (int j = 0; j < numVertices; j++) {
                if (graph[minKeyIndex][j] != 0 && !mstSet[j] && graph[minKeyIndex][j] < key[j]) {
                    parent[j] = minKeyIndex;
                    key[j] = graph[minKeyIndex][j];
                }
            }
        }

        // Print the constructed MST
        System.out.println("Edge \tWeight");
        for (int i = 1; i < numVertices; i++) {
            System.out.println(parent[i] + " - " + i + "\t" + graph[i][parent[i]]);
        }

        // Visualize the minimum spanning tree
        visualizeMST(graph, parent);
    }

    // Utility function to find the vertex with the minimum key value, from the set of vertices not yet included in MST
    int getMinKeyIndex(int[] key, boolean[] mstSet) {
        int minKey = Integer.MAX_VALUE;
        int minKeyIndex = -1;
        int numVertices = key.length;

        for (int v = 0; v < numVertices; v++) {
            if (!mstSet[v] && key[v] < minKey) {
                minKey = key[v];
                minKeyIndex = v;
            }
        }

        return minKeyIndex;
    }
 
    // Function to visualize the minimum spanning tree
void visualizeMST(int[][] graph, int[] parent) {
    Graph graphVisualizer = new SingleGraph("Graph Visualization");

    // Define character labels for the nodes
    char[] labels = new char[graph.length];
    for (int i = 0; i < graph.length; i++) {
        labels[i] = (char) ('A' + i);
    }

    // Add nodes to the graph visualization with character labels
    for (int i = 0; i < graph.length; i++) {
        Node node = graphVisualizer.addNode(Integer.toString(i));
        node.setAttribute("ui.label", Character.toString(labels[i]));
        node.setAttribute("ui.style", "shape: circle; size: 20px, 20px; text-alignment: center; text-size: 12px;");
    }

    // Add edges to the graph visualization
    for (int i = 0; i < graph.length; i++) {
        for (int j = i + 1; j < graph.length; j++) {
            if (graph[i][j] != 0) {
                String edgeId = Integer.toString(i) + "-" + Integer.toString(j);
                org.graphstream.graph.Edge edge = graphVisualizer.addEdge(edgeId, Integer.toString(i), Integer.toString(j));
                edge.setAttribute("ui.label", Integer.toString(graph[i][j]));

                // Set the output lines to bold and green
                if (parent[j] == i || parent[i] == j) {
                    edge.setAttribute("ui.style", "fill-color: green; text-style: bold;");
                }
                
            }
        }
    }
    

    // Set visual attributes for the graph visualization
    graphVisualizer.addAttribute("ui.stylesheet", "edge {text-size: 16px;}");
    graphVisualizer.addAttribute("ui.quality");
    graphVisualizer.addAttribute("ui.antialias");

    // Display the graph visualization
    graphVisualizer.display();
    
}


    
}
