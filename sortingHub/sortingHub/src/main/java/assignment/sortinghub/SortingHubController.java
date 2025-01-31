package assignment.sortinghub;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SortingHubController  {

    @FXML
    private Slider ArrayCountSlider, DelayVertSlider;

    @FXML
    private AnchorPane BarChart;

    @FXML
    private Button ReverseBtn, ShuffleBtn, SortBtn;

    @FXML
    private ComboBox<String> SortMethodComboBox;

    @FXML
    private Label arraySizeLable, delayLable;

    private int[] order; // Stores the array to be sorted
    private int delay;   // Stores delay value for visualization
    private String sorts, whichBtn;
    private SortingStrategy sortChosen; // Current sorting algorithm

    public void initialize() throws InterruptedException {
        // Initialize the combo box with sorting options
        SortMethodComboBox.getItems().addAll("QuickSort","MergeSort","SelectionSort");
        SortMethodComboBox.setValue("QuickSort"); // Default selection
        BarChart.setStyle("-fx-background-color: lightGray; -fx-border-color: black; -fx-border-width: 2;");
        order = RNGArray(32); // Generate initial random array
        delay = 200; // sets delay to 200 mili seconds
        DelayVertSlider.setValue(200.0);
        delayLable.setText(String.format("%.0f", 200.0));
        arraySizeLable.setText(String.format("%.0f", 32.0));
    }

    @FXML
    void ChangeArraySize() {
        // Updates the array size dynamically when slider is moved
        ArrayCountSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            int size = newValue.intValue();
            arraySizeLable.setText(String.format("%.0f", newValue));
            order = RNGArray(size); // Generate new array
            try {
                populateBars(size, order); // Update visualization
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            ChangeSortMethod(); // Ensure sorting method is updated
        });
    }

    @FXML
    void ChangeDelay() {
        // Updates delay value dynamically when slider is moved
        DelayVertSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            delay = newValue.intValue();
            delayLable.setText(String.format("%.0f", newValue));
        });
    }

    @FXML
    void ChangeSortMethod() {
        // Gets selected sorting method from combo box
        sorts = SortMethodComboBox.getSelectionModel().getSelectedItem();

        // Assigns appropriate sorting algorithm
        if (sorts.equals("QuickSort")) {
            sortChosen = new QuickSort(order, this);
        } else if (sorts.equals("MergeSort")) {
            sortChosen = new MergeSort(order, this);
        } else if (sorts.equals("SelectionSort")) {
            sortChosen  = new SelectionSort(order, this);
        }
    }

    @FXML
    void ReversePressed() {
        if (sortChosen != null) {
            setWhichBtn("reverse"); // Indicate reverse operation
            Thread sortingThread = new Thread(() -> sortChosen.run());
            sortingThread.start(); // Run in a separate thread
        } else {
            System.out.println("No sorting method selected.");
        }
    }

    @FXML
    void ShufflePressed() {
        if (sortChosen != null) {
            setWhichBtn("shuffle"); // Indicate shuffle operation
            Thread sortingThread = new Thread(() -> sortChosen.run());
            sortingThread.start(); // Run in a separate thread
        } else {
            System.out.println("No sorting method selected.");
        }
    }

    @FXML
    void SortPressed() {
        if (sortChosen != null) {
            setWhichBtn("sort"); // Indicate sorting operation
            Thread sortingThread = new Thread(() -> sortChosen.run());
            sortingThread.start(); // Run in a separate thread
        } else {
            System.out.println("No sorting method selected.");
        }
    }

    public void populateBars(int size, int[] order) throws InterruptedException {
        BarChart.getChildren().clear(); // Clear previous bars
        double width = 494.0 / size;
        double heightScale = 296.0 / size;

        for (int x = 0; x < size; x++) {
            double height = heightScale * (x + 1);
            Rectangle rect = new Rectangle((order[x] * width) + 6.0, (300 - height), width, height);
            rect.setFill(Color.RED);
            rect.setStroke(Color.LIGHTGRAY);
            BarChart.getChildren().add(rect);
        }
    }

    public int[] RNGArray(int size) {
        // Generates an array with shuffled values
        order = new int[size];
        for (int x = 0; x < order.length; x++) {
            order[x] = x;
        }
        for (int y = 0; y < order.length; y++) {
            int randomNumber = (int) (Math.random() * size);
            int temp = order[y];
            order[y] = order[randomNumber];
            order[randomNumber] = temp;
        }
        return order;
    }

    public long getDelay() {
        return delay;
    }

    public String getWhichBtn() {
        return whichBtn;
    }

    public void setWhichBtn(String whichBtn) {
        this.whichBtn = whichBtn;
    }
}
