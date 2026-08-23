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

        Button resetButton = new Button("Reset Zoom");
        resetButton.setOnAction(e -> handleResetView(controller, mainView));

        TextField resWidth = new TextField();
        resWidth.setPromptText("Canvas Width");

        TextField resHeight = new TextField();
        resHeight.setPromptText("Canvas Height");

        Button changeResButton = new Button("Change Canvas Size");
        changeResButton.setOnAction(e ->
                handleChangeResolution(controller, mainView, resWidth, resHeight));

        pane = new VBox(12,
                new Label("Frattale"), fractalSelector,
                new Label("Palette"), paletteSelector,
                iterationsLabel, iterationsSlider,
                new Label("Risoluzione Canvas"),
                resWidth, resHeight,
                changeResButton,
                exportButton,
                resetButton
        );
        pane.setPrefWidth(200);
        pane.setStyle("-fx-padding: 16;");
    }

    private void handleChangeResolution(
            FractalController controller,
            MainView mainView,
            TextField resWidth,
            TextField resHeight
    ) {
        final int width;
        final int height;

        try {
            width = Integer.parseInt(resWidth.getText().trim());
            height = Integer.parseInt(resHeight.getText().trim());
        } catch (NumberFormatException ex) {
            showResolutionError("Inserire larghezza e altezza come numeri interi positivi.");
            return;
        }

        try {
             
            controller.onCanvasSizeChanged(width, height);

             
            mainView.setCanvasSize(width, height);
            mainView.redraw();
        } catch (IllegalArgumentException ex) {
            showResolutionError(ex.getMessage());
        }
    }

    private void showResolutionError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.setHeaderText("Risoluzione non valida");
        alert.showAndWait();
    }

    private void handleResetView(FractalController controller, MainView mainView) {
        controller.onResetView();
        mainView.redraw();
    }

    private void handleExport(FractalController controller, MainView mainView, Button exportButton) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("fractal.png");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG", "*.png"));
        File file = fileChooser.showSaveDialog(exportButton.getScene().getWindow());
        if (file == null) {
            return;
        }

         
         
        WritableImage currentImage = mainView.getCurrentImage();
        if (currentImage == null) {
            Alert alert = new Alert(
                    Alert.AlertType.INFORMATION,
                    "Il primo rendering non e' ancora terminato. Riprova tra poco.",
                    ButtonType.OK
            );
            alert.setHeaderText("Nessun frame disponibile");
            alert.showAndWait();
            return;
        }

        try {
            controller.onExportRequested(currentImage, file);
        } catch (IOException ex) {
            Alert alert = new Alert(
                    Alert.AlertType.ERROR,
                    "Impossibile esportare l'immagine: " + ex.getMessage(),
                    ButtonType.OK
            );
            alert.setHeaderText("Errore di esportazione");
            alert.showAndWait();
        }
    }

    public VBox getPane() {
        return pane;
    }
}
