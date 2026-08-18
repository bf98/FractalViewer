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
    private WritableImage currentImage;

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
        double deltaY = event.getDeltaY();

         
         
        if (Math.abs(deltaY) < 1e-9) {
            event.consume();
            return;
        }

        controller.onScroll((int) event.getX(), (int) event.getY(), deltaY);
        event.consume();
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
        controller.renderCurrentFrameAsync(
                this::drawImage,
                Throwable::printStackTrace
        );
    }

    private void drawImage(WritableImage image) {
        currentImage = image;

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.drawImage(image, 0, 0);
    }

     



    public WritableImage getCurrentImage() {
        return currentImage;
    }

    public Canvas getCanvas() {
        return canvas;
    }
}
