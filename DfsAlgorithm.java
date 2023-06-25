import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.Timer;


public class DfsAlgorithm extends JPanel {
    private int vertices;
    private LinkedList<Integer>[] adjacencyList;
    private boolean[] visited;
    private Stack<Integer> stack;
    private int currentNode;
     private ArrayList<Integer> traversalOrder; // New variable for traversal order

    private Timer timer;
    private int delay = 1000; // Delay between each step (in milliseconds)

    DfsAlgorithm(int v) {
        vertices = v;
        adjacencyList = new LinkedList[v];
        for (int i = 0; i < v; ++i)
            adjacencyList[i] = new LinkedList<>();

        visited = new boolean[v];
        stack = new Stack<>();
         traversalOrder = new ArrayList<>();
    }

    void addEdge(int v, int w) {
        adjacencyList[v].add(w);
    }

    void DFS(int startVertex) {
        visited[startVertex] = true;
        stack.push(startVertex);
        

         Timer timer = new Timer();
    timer.scheduleAtFixedRate(new TimerTask() {
            @Override
           
        public void run() {
            if (!stack.isEmpty()) {
                    int node = stack.peek();
                    boolean foundUnvisitedNeighbor = false;
                    for (int neighbor : adjacencyList[node]) {
                        if (!visited[neighbor]) {
                            visited[neighbor] = true;
                            stack.push(neighbor);
                            currentNode = neighbor;
                            traversalOrder.add(currentNode); // Store the traversal order
                            repaint(); // Repaint the panel to visualize the traversal
                            foundUnvisitedNeighbor = true;
                            break;
                        }
                    }
                    if (!foundUnvisitedNeighbor) {
                        stack.pop();
                        if (!stack.isEmpty())
                            currentNode = stack.peek();
                        repaint(); // Repaint the panel to visualize the traversal
                    }
            } else {
                timer.cancel();
            }
        }
    }, 1000, 1000);
}
           

       

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int radius = 30;
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

         // Draw the vertices
    for (int i = 0; i < vertices; i++) {
        int x = (int) (centerX + 150 * Math.cos(2 * Math.PI * i / vertices));
        int y = (int) (centerY + 150 * Math.sin(2 * Math.PI * i / vertices));

        g.setColor(Color.WHITE);
        if (i == currentNode)
            g.setColor(Color.YELLOW);
        g.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);

        g.setColor(Color.BLACK);
        g.drawOval(x - radius, y - radius, 2 * radius, 2 * radius);

        g.setFont(g.getFont().deriveFont(Font.BOLD, 18)); // Set the font to bold with size 18
        g.drawString(Character.toString((char) (i + 65)), x - 7, y + 7); // Display the character value
    }

        // Draw the edges
        for (int i = 0; i < vertices; i++) {
            int x1 = (int) (centerX + 150 * Math.cos(2 * Math.PI * i / vertices));
            int y1 = (int) (centerY + 150 * Math.sin(2 * Math.PI * i / vertices));

            for (int j : adjacencyList[i]) {
                int x2 = (int) (centerX + 150 * Math.cos(2 * Math.PI * j / vertices));
                int y2 = (int) (centerY + 150 * Math.sin(2 * Math.PI * j / vertices));

                g.setColor(Color.BLACK);
                g.drawLine(x1, y1, x2, y2);
            }
        }
        
         // Display the traversal order
    g.setFont(g.getFont().deriveFont(Font.BOLD, 14)); // Set the font to bold with size 14
    g.setColor(Color.BLUE);
    for (int i = 0; i < traversalOrder.size(); i++) {
        int node = traversalOrder.get(i);
        int x = (int) (centerX + 150 * Math.cos(2 * Math.PI * node / vertices));
        int y = (int) (centerY + 150 * Math.sin(2 * Math.PI * node / vertices));

        g.setColor(Color.YELLOW); // Set the color for the circle
        g.fillOval(x - 10, y - 30, 20, 20); // Draw a circle around the traversal order number

        g.setColor(Color.BLUE); // Set the color for the traversal order number
        g.drawString(Integer.toString(i + 1), x - 3, y - 15); // Display the traversal order number
    }
    }

}
