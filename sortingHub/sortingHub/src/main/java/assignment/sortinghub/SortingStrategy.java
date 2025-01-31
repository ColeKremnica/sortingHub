package assignment.sortinghub;

// Abstract class that represents a sorting strategy
abstract class SortingStrategy implements Runnable {

    // Empty run method to be overridden by subclasses
    public void run() {
    }

    // Sort method to be implemented by subclasses (abstract method)
    void sort(int[] numbers) {
    }

    // Shuffle method to be implemented by subclasses (abstract method)
    public void shuffle(int[] numbers) {
    }

    // Reverse method to be implemented by subclasses (abstract method)
    public void reverse(int[] numbers) {
    }
}
