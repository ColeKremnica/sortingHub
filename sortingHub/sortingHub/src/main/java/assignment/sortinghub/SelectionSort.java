package assignment.sortinghub;

import javafx.application.Platform;

public class SelectionSort extends SortingStrategy {
    private int[] list;
    private SortingHubController controller;

    public SelectionSort(int[] list, SortingHubController controller) {
        this.list = list;
        this.controller = controller;
    }

    @Override
    public void run() {
        // Determine which action to perform (sort, reverse, shuffle)
        String choice = controller.getWhichBtn();
        if (choice.equals("sort")) {
            sort(list);
        } else if (choice.equals("reverse")) {
            reverse(list);
        } else {
            shuffle(list);
        }
    }

    public void sort(int[] numbers) {
        int n = numbers.length;
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;

            // Find index of minimum element in remaining array
            for (int j = i + 1; j < n; j++) {
                if (numbers[j] < numbers[minIdx]) {
                    minIdx = j;
                }
            }

            // Swap elements
            int temp = numbers[i];
            numbers[i] = numbers[minIdx];
            numbers[minIdx] = temp;

            // Update UI on JavaFX thread
            Platform.runLater(() -> {
                try {
                    controller.populateBars(numbers.length, numbers);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });

            // Delay for visualization
            try {
                Thread.sleep(controller.getDelay());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void shuffle(int[] numbers) {
        // Reset array to sequential order
        for (int x = 0; x < numbers.length; x++) {
            numbers[x] = x;
        }

        // Shuffle using Fisher-Yates algorithm
        for (int y = 0; y < numbers.length; y++) {
            int randomNumber = (int) (Math.random() * numbers.length);
            int temp = numbers[y];
            numbers[y] = numbers[randomNumber];
            numbers[randomNumber] = temp;
        }

        // Update UI on JavaFX thread
        Platform.runLater(() -> {
            try {
                controller.populateBars(numbers.length, numbers);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void reverse(int[] numbers) {
        // Reverse array in-place
        for (int x = 0; x < numbers.length / 2; x++) {
            int temp = numbers[x];
            numbers[x] = numbers[numbers.length - 1 - x];
            numbers[numbers.length - 1 - x] = temp;
        }

        // Update UI on JavaFX thread
        Platform.runLater(() -> {
            try {
                controller.populateBars(numbers.length, numbers);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
