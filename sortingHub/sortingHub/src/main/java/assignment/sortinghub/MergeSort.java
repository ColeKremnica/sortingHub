package assignment.sortinghub;

import javafx.application.Platform;

public class MergeSort extends SortingStrategy {
    private int[] list;
    private SortingHubController controller;

    // Constructor to initialize the list and controller
    public MergeSort(int[] list, SortingHubController controller) {
        this.list = list;
        this.controller = controller;
    }

    // Run method to decide whether to sort, reverse, or shuffle the list
    @Override
    public void run() {
        String choice = controller.getWhichBtn();
        if (choice.equals("sort")) {
            sort(list);
        } else if (choice.equals("reverse")) {
            reverse(list);
        } else {
            shuffle(list);
        }
    }

    // Sort the list using merge sort
    public void sort(int[] numbers) {
        mergeSort(numbers, 0, numbers.length - 1, controller);
    }

    // Shuffle the array randomly
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

    // Reverse the array
    public void reverse(int[] numbers) {
        for (int x = 0; x < numbers.length / 2; x++) {
            int temp = numbers[x];
            numbers[x] = numbers[numbers.length - 1 - x];
            numbers[numbers.length - 1 - x] = temp;
        }
        updateUI(numbers);
    }

    // Merge sort function (recursive)
    public static void mergeSort(int[] arr, int low, int high, SortingHubController controller) {
        if (high <= low)  // Keep the bounds in check
            return;

        int mid = (low + high) / 2;
        mergeSort(arr, low, mid, controller);  // Sort left half
        mergeSort(arr, mid + 1, high, controller);  // Sort right half
        merge(arr, low, mid, high);  // Merge both halves

        // Update UI after merge step
        Platform.runLater(() -> {
            try {
                controller.populateBars(arr.length, arr);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        // Introduce delay for visualization
        try {
            Thread.sleep(controller.getDelay());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Merge function to combine two sorted subarrays into a single sorted array
    public static void merge(int[] array, int low, int mid, int high) {
        // Create temporary arrays for left and right subarrays
        int[] leftArray = new int[mid - low + 1];
        int[] rightArray = new int[high - mid];

        // Copy data into temporary arrays
        System.arraycopy(array, low, leftArray, 0, leftArray.length);
        System.arraycopy(array, mid + 1, rightArray, 0, rightArray.length);

        int leftIndex = 0, rightIndex = 0, currentIndex = low;

        // Merge the two subarrays back into the original array
        while (leftIndex < leftArray.length && rightIndex < rightArray.length) {
            if (leftArray[leftIndex] <= rightArray[rightIndex]) {
                array[currentIndex] = leftArray[leftIndex];
                leftIndex++;
            } else {
                array[currentIndex] = rightArray[rightIndex];
                rightIndex++;
            }
            currentIndex++;
        }

        // Copy remaining elements from leftArray
        while (leftIndex < leftArray.length) {
            array[currentIndex] = leftArray[leftIndex];
            leftIndex++;
            currentIndex++;
        }

        // Copy remaining elements from rightArray
        while (rightIndex < rightArray.length) {
            array[currentIndex] = rightArray[rightIndex];
            rightIndex++;
            currentIndex++;
        }
    }

    // Helper function to update UI with the current array state
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
