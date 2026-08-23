package fractalvisualizer.view;

import fractalvisualizer.controller.FractalController;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

/** mostra il frattale e gestisce comandi del mouse */
public class MainView {

    private final FractalController controller;
    private final Canvas canvas;
    private final Group canvasContainer;
    private final ScrollPane scrollPane;

    private double lastDragX;
    private double lastDragY;
    private WritableImage currentImage;

    public MainView(FractalController controller, int width, int height) {
        this.controller = controller;
        this.canvas = new Canvas(width, height);

        // mantiene dimensioni originali canvas
        this.canvasContainer = new Group(canvas);
        this.scrollPane = new ScrollPane(canvasContainer);
        configureScrollPane();
        attachEventHandlers();
    }

    private void configureScrollPane() {
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        // mantiene risoluzione scelta
        scrollPane.setFitToWidth(false);
        scrollPane.setFitToHeight(false);

        // evita conflitti con trascinamento canvas
        scrollPane.setPannable(false);

        // permette allo scrollpane di adattarsi alla finestra
        scrollPane.setMinWidth(0);
        scrollPane.setMinHeight(0);
    }

    private void attachEventHandlers() {
        canvas.setOnScroll(this::handleScroll);
        canvas.setOnMousePressed(this::handleMousePressed);
        canvas.setOnMouseDragged(this::handleMouseDragged);
    }

    private void handleScroll(ScrollEvent event) {
        double deltaY = event.getDeltaY();

        // ignora scorrimento senza zoom
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

    /** update risoluzione mostrata */
    public void setCanvasSize(int width, int height) {
        canvas.setWidth(width);
        canvas.setHeight(height);
        canvasContainer.requestLayout();
        scrollPane.requestLayout();

        // mostra centro canvas
        Platform.runLater(this::centerScrollPosition);
    }

    public void centerScrollPosition() {
        scrollPane.setHvalue(0.5);
        scrollPane.setVvalue(0.5);
    }

    /** restituisce ultima immagine mostrata */
    public WritableImage getCurrentImage() {
        return currentImage;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    /** restituisce contenuto principale */
    public ScrollPane getView() {
        return scrollPane;
    }
}
