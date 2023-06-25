import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.Timer;

class GraphVisualization extends JPanel {
    private int vertices;
    private LinkedList<Integer>[] adjacencyList;
    private boolean[] visited;
    private Queue<Integer> queue;
    private int currentNode;
    private ArrayList<Integer> traversalOrder; // New variable for traversal order

    GraphVisualization(int v) {
        vertices = v;
        adjacencyList = new LinkedList[v];
        for (int i = 0; i < v; ++i)
            adjacencyList[i] = new LinkedList<>();

        visited = new boolean[v];
        queue = new LinkedList<>();
        traversalOrder = new ArrayList<>();
    }

    void addEdge(int v, int w) {
        adjacencyList[v].add(w);
    }
    
    void BFS(int startVertex) {
    visited[startVertex] = true;
    queue.add(startVertex);

    Timer timer = new Timer();
    timer.scheduleAtFixedRate(new TimerTask() {
        @Override
        public void run() {
            if (!queue.isEmpty()) {
                currentNode = queue.poll();
                traversalOrder.add(currentNode); // Store the traversal order
                repaint(); // Repaint the panel to visualize the current step

                Iterator<Integer> iterator = adjacencyList[currentNode].listIterator();
                while (iterator.hasNext()) {
                    int nextVertex = iterator.next();
                    if (!visited[nextVertex]) {
                        visited[nextVertex] = true;
                        queue.add(nextVertex);
                    }
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

        Iterator<Integer> iterator = adjacencyList[i].listIterator();
        while (iterator.hasNext()) {
            int j = iterator.next();
            int x2 = (int) (centerX + 150 * Math.cos(2 * Math.PI * j / vertices));
            int y2 = (int) (centerY + 150 * Math.sin(2 * Math.PI * j / vertices));

            int xMid = (x1 + x2) / 2;
            int yMid = (y1 + y2) / 2;

            if (visited[j]) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setStroke(new BasicStroke(2)); // Set the stroke to make the line thicker

                if (currentNode == i) {
                    g2.setColor(Color.GREEN);
                    g2.drawLine(x1, y1, xMid, yMid);
                   // g2.drawString(">", xMid, yMid);
                } else {
                    g2.setColor(Color.RED);
                    g2.drawLine(x1, y1, xMid, yMid);
                   // g2.drawString(">", xMid, yMid);
                }

                g.setColor(Color.GRAY);
                g.drawLine(xMid, yMid, x2, y2);
            } else {
                g.setColor(Color.BLACK);
                g.drawLine(x1, y1, x2, y2);
            }

            g.setFont(g.getFont().deriveFont(Font.BOLD, 18)); // Set the font to bold with size 18
             g.setColor(Color.GREEN);
            g.drawString(Character.toString((char) (j + 65)), xMid - 7, yMid + 7); // Display the character value
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


    public static void main(String[] args) {
       
    }
}
