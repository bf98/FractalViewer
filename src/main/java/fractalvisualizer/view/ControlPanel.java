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
import java.lang.Integer;

 



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
        resetButton.setOnAction(e -> handleResetZoom(controller, mainView, resetButton));

				TextField resWidth = new TextField();
				resWidth.setPromptText("Canvas Width");
				TextField resHeight = new TextField();
				resHeight.setPromptText("Canvas Height");

				Button changeResButton = new Button("Change Canvas Size");
				changeResButton.setOnAction(e -> handleChangeRes(controller, mainView, changeResButton, resWidth, resHeight));
				
        pane = new VBox(12,
                new Label("Frattale"), fractalSelector,
                new Label("Palette"), paletteSelector,
                iterationsLabel, iterationsSlider,
								resWidth, resHeight,
								changeResButton,	
                exportButton,
								resetButton);
        pane.setPrefWidth(200);
        pane.setStyle("-fx-padding: 16;");
    }

		private void handleChangeRes(FractalController controller, MainView mainView, Button changeResButton, TextField resWidth, TextField resHeight) {
				 
				 
				int width = Integer.parseInt(resWidth.getText()); 
				int height = Integer.parseInt(resHeight.getText());

				controller.getViewport().setCanvasWidth(width);
				controller.getViewport().setCanvasHeight(height);
				mainView.getCanvas().setWidth(width);
				mainView.getCanvas().setHeight(height);
				mainView.redraw();
		}

		private void handleResetZoom(FractalController controller, MainView mainView, Button resetButton) {
			 
			controller.getViewport().resetZoom();
			controller.getViewport().setCenterX(-0.5);
			controller.getViewport().setCenterY(0);
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
