package assignment.sortinghub;

import javafx.application.Platform;

public class QuickSort extends SortingStrategy {
    private int[] list;
    private SortingHubController controller;

    // Constructor to initialize list and controller
    public QuickSort(int[] list, SortingHubController controller) {
        this.list = list;
        this.controller = controller;
    }

    // Entry point to determine action (sort, reverse, shuffle)
    public void run() {
        if (controller == null) {
            System.out.println("Controller is null");
            return;
        }

        String choice = controller.getWhichBtn();
        if (choice.equals("sort")) {
            sort(list);
        } else if (choice.equals("reverse")) {
            reverse(list);
        } else {
            shuffle(list);
        }
    }

    // Main sorting function, calls quickSort
    public void sort(int[] numbers) {
        quickSort(numbers, 0, numbers.length - 1, controller);
    }

    // Shuffling the array
    public void shuffle(int[] numbers) {
        for (int x = 0; x < numbers.length; x++) {
            numbers[x] = x;
        }
        // Fisher-Yates shuffle
        for (int y = 0; y < numbers.length; y++) {
            int randomNumber = (int) (Math.random() * numbers.length);
            int temp = numbers[y];
            numbers[y] = numbers[randomNumber];
            numbers[randomNumber] = temp;
        }
        updateUI(numbers);
    }

    // Reversing the array
    public void reverse(int[] numbers) {
        for (int x = 0; x < numbers.length / 2; x++) {
            int temp = numbers[x];
            numbers[x] = numbers[numbers.length - 1 - x];
            numbers[numbers.length - 1 - x] = temp;
        }
        updateUI(numbers);
    }

    // Partition function for QuickSort
    static int partitionGrowing(int[] arr, int low, int high) {
        int pivot = arr[high];  // Choose the pivot element
        int i = low - 1;  // Index of smaller element

        // Move elements smaller than pivot to the left
        for (int j = low; j <= high - 1; j++) {
            if (arr[j] < pivot) {
                i++;
                swap(arr, i, j);
            }
        }

        // Place pivot in the correct position
        swap(arr, i + 1, high);
        return i + 1;
    }

    // Swap elements at indices i and j
    static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // QuickSort function implementation
    static void quickSort(int[] arr, int low, int high, SortingHubController controller) {
        if (low < high) {
            // Partition the array
            int pi = partitionGrowing(arr, low, high);

            // Recursively sort the two partitions
            quickSort(arr, low, pi - 1, controller);
            quickSort(arr, pi + 1, high, controller);

            // Update UI after partitioning
            Platform.runLater(() -> {
                try {
                    controller.populateBars(arr.length, arr);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });

            // Sleep to visualize sorting with delay
            try {
                Thread.sleep(controller.getDelay());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Helper function to update the UI with new array state
    private void updateUI(int[] numbers) {
        Platform.runLater(() -> {
            try {
                controller.populateBars(numbers.length, numbers);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
