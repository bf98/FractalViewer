package fractalvisualizer.view;

import fractalvisualizer.controller.FractalController;
import fractalvisualizer.model.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {

    private static final int CANVAS_WIDTH = 640;
    private static final int CANVAS_HEIGHT = 480;
    private static final int DEFAULT_MAX_ITERATIONS = 800;

    private static final int WINDOW_WIDTH = 1100;
    private static final int WINDOW_HEIGHT = 720;

    private FractalController controller;

    @Override
    public void start(Stage stage) {
        // model
        Fractal fractal = new Mandelbrot(DEFAULT_MAX_ITERATIONS);
        ColorPalette palette = new RainbowPalette();
        Viewport viewport = new Viewport(-0.5, 0.0, 1.0, CANVAS_WIDTH, CANVAS_HEIGHT);
        FractalRenderer renderer = new FractalRenderer(fractal, palette);
        ExportManager exportManager = new ExportManager();

        // controller
        controller = new FractalController(renderer, viewport, exportManager);

        // view
        MainView mainView = new MainView(controller, CANVAS_WIDTH, CANVAS_HEIGHT);
        ControlPanel controlPanel = new ControlPanel(controller, mainView, DEFAULT_MAX_ITERATIONS);

        BorderPane root = new BorderPane();
        root.setCenter(mainView.getView());
        root.setRight(controlPanel.getPane());

        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        stage.setTitle("FractalViewer");
        stage.setScene(scene);

        // ridimensionamento non cambia la risoluzione canvas
        stage.setMinWidth(700);
        stage.setMinHeight(500);
        stage.setResizable(true);
        stage.show();

        // mostra inizialmente centro del canvas
        Platform.runLater(mainView::centerScrollPosition);

        // avvio primo rendering
        mainView.redraw();
    }

    @Override
    public void stop() {
        if (controller != null) {
            controller.shutdown();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
