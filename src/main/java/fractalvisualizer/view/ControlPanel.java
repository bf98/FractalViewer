package fractalvisualizer.view;

import fractalvisualizer.controller.FractalController;
import fractalvisualizer.model.FractalType;
import fractalvisualizer.model.PaletteType;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

 







public class ControlPanel {

    private final VBox pane;

    public ControlPanel(FractalController controller, MainView mainView, int defaultMaxIterations) {
        ComboBox<FractalType> fractalSelector = new ComboBox<>();
        fractalSelector.getItems().addAll(FractalType.values());
        fractalSelector.setValue(FractalType.MANDELBROT);
        fractalSelector.setOnAction(e -> {
            controller.onFractalSelected(fractalSelector.getValue());
            mainView.redraw();
        });

        ComboBox<PaletteType> paletteSelector = new ComboBox<>();
        paletteSelector.getItems().addAll(PaletteType.values());
        paletteSelector.setValue(PaletteType.RAINBOW);
        paletteSelector.setOnAction(e -> {
            controller.onPaletteSelected(paletteSelector.getValue());
            mainView.redraw();
        });

        Slider iterationsSlider = new Slider(50, 1000, defaultMaxIterations);
        iterationsSlider.setShowTickLabels(true);
        Label iterationsLabel = new Label("Iterazioni: " + defaultMaxIterations);
        iterationsSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            int value = newVal.intValue();
            iterationsLabel.setText("Iterazioni: " + value);
            controller.onMaxIterationsChanged(value);
        });
         
        iterationsSlider.setOnMouseReleased(e -> mainView.redraw());

        Button exportButton = new Button("Esporta PNG");
        exportButton.setOnAction(e -> handleExport(controller, mainView, exportButton));

        pane = new VBox(12,
                new Label("Frattale"), fractalSelector,
                new Label("Palette"), paletteSelector,
                iterationsLabel, iterationsSlider,
                exportButton);
        pane.setPrefWidth(200);
        pane.setStyle("-fx-padding: 16;");
    }

    private void handleExport(FractalController controller, MainView mainView, Button exportButton) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("fractal.png");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG", "*.png"));
        File file = fileChooser.showSaveDialog(exportButton.getScene().getWindow());
        if (file == null) {
            return;  
        }
        try {
            WritableImage currentImage = controller.renderCurrentFrame();
            controller.onExportRequested(currentImage, file);
        } catch (IOException ex) {
             
            ex.printStackTrace();
        }
    }

    public VBox getPane() {
        return pane;
    }
}
