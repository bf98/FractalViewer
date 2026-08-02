package fractalvisualizer.view;

import fractalvisualizer.controller.FractalController;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

 





public class MainView {

    private final FractalController controller;
    private final Canvas canvas;
    private double lastDragX;
    private double lastDragY;

    public MainView(FractalController controller, int width, int height) {
        this.controller = controller;
        this.canvas = new Canvas(width, height);
        attachEventHandlers();
    }

    private void attachEventHandlers() {
        canvas.setOnScroll(this::handleScroll);
        canvas.setOnMousePressed(this::handleMousePressed);
        canvas.setOnMouseDragged(this::handleMouseDragged);
    }

    private void handleScroll(ScrollEvent event) {
        controller.onScroll((int) event.getX(), (int) event.getY(), event.getDeltaY());
        redraw();
    }

    private void handleMousePressed(MouseEvent event) {
        lastDragX = event.getX();
        lastDragY = event.getY();
    }

    private void handleMouseDragged(MouseEvent event) {
        double dx = event.getX() - lastDragX;
        double dy = event.getY() - lastDragY;
        controller.onMouseDrag(dx, dy);
        lastDragX = event.getX();
        lastDragY = event.getY();
        redraw();
    }

     







    public void redraw() {
        WritableImage image = controller.renderCurrentFrame();
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(image, 0, 0);
    }

    public Canvas getCanvas() {
        return canvas;
    }
}
