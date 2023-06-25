import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class MergeSortVisualization extends JPanel {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 400;
    private static final int BAR_WIDTH = 20;
    private static final int DELAY = 1000;

    private int[] arr;
    public int[] sortedArr;
    private int[] divideIndices;
    private int[] mergeIndices;
    private boolean sortingComplete;

    public MergeSortVisualization(int[] arr) {
        this.arr = arr;
        this.sortedArr = Arrays.copyOf(arr, arr.length);
        this.divideIndices = new int[arr.length];
        this.mergeIndices = new int[arr.length];
        this.sortingComplete = false;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.WHITE);
    }

    public void mergeSort() {
        mergeSort(0, arr.length - 1);
        sortingComplete = true;
        repaint();

        // Print sorted array
        System.out.println("Sorted Array: " + Arrays.toString(sortedArr));
    }

    private void mergeSort(int low, int high) {
        if (low < high) {
            int mid = low + (high - low) / 2;
            mergeSort(low, mid);
            mergeSort(mid + 1, high);
            merge(low, mid, high);
        }
    }

    private void merge(int low, int mid, int high) {
        int[] temp = Arrays.copyOfRange(arr, low, high + 1);

        int i = 0;
        int j = mid - low + 1;
        int k = low;

        while (i <= mid - low && j <= high - low) {
            if (temp[i] <= temp[j]) {
                arr[k++] = temp[i++];
            } else {
                arr[k++] = temp[j++];
            }

            // Store indices for divide and merge parts
            divideIndices[k - 1] = 1;
            mergeIndices[k - 1] = 1;

            repaint();
            delay();

            sortedArr[k - 1] = arr[k - 1];
        }

        while (i <= mid - low) {
            arr[k++] = temp[i++];
            divideIndices[k - 1] = 1;
            mergeIndices[k - 1] = 0;
            repaint();
            delay();

            sortedArr[k - 1] = arr[k - 1];
        }
    }

    private void delay() {
        try {
            Thread.sleep(30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < arr.length; i++) {
            int x = i * (BAR_WIDTH + 2);
            int y = HEIGHT - arr[i] * 10;

            if (sortingComplete) {
                g.setColor(Color.GREEN);
            } else if (mergeIndices[i] == 1) {
                g.setColor(Color.BLUE);
            } else if (divideIndices[i] == 1) {
                g.setColor(Color.YELLOW);
            } else {
                g.setColor(Color.RED);
            }

            g.fillRect(x, y, BAR_WIDTH, arr[i] * 10);

            // Draw value label
            g.setColor(Color.BLACK);
            g.drawString(String.valueOf(arr[i]), x, y - 5);
        }
    }
}




// 14,7,3,12,9,11,6,12