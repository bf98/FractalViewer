package fractalvisualizer.view;

import fractalvisualizer.controller.FractalController;
import fractalvisualizer.model.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

 


public class MainApp extends Application {

		 
     
     
    private static final int CANVAS_WIDTH = 1280;
    private static final int CANVAS_HEIGHT = 728;
    private static final int DEFAULT_MAX_ITERATIONS = 800;

    private FractalController controller;

    @Override
    public void start(Stage stage) {
         
        Fractal fractal = new Mandelbrot(DEFAULT_MAX_ITERATIONS);
        ColorPalette palette = new RainbowPalette();
        Viewport viewport = new Viewport(-0.5, 0.0, 1.0, CANVAS_WIDTH, CANVAS_HEIGHT);
        FractalRenderer renderer = new FractalRenderer(fractal, palette);
        ExportManager exportManager = new ExportManager();

         
        controller = new FractalController(renderer, viewport, exportManager);

         
        MainView mainView = new MainView(controller, CANVAS_WIDTH, CANVAS_HEIGHT);
        ControlPanel controlPanel = new ControlPanel(controller, mainView, DEFAULT_MAX_ITERATIONS);

        BorderPane root = new BorderPane();
        root.setCenter(mainView.getCanvas());
        root.setRight(controlPanel.getPane());

        Scene scene = new Scene(root, CANVAS_WIDTH + 220, CANVAS_HEIGHT);
        stage.setTitle("FractalViewer");
        stage.setScene(scene);
        stage.show();

         
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
