package fractalvisualizer.controller;

import fractalvisualizer.model.*;
import javafx.scene.image.WritableImage;

import java.io.File;
import java.io.IOException;

 









public class FractalController {

    private final FractalRenderer renderer;
    private final Viewport viewport;
    private final ExportManager exportManager;

    public FractalController(FractalRenderer renderer, Viewport viewport, ExportManager exportManager) {
        this.renderer = renderer;
        this.viewport = viewport;
        this.exportManager = exportManager;
    }

     



    public WritableImage renderCurrentFrame() {
        return renderer.renderFrame(viewport, viewport.getCanvasWidth(), viewport.getCanvasHeight());
    }

    public void onScroll(int px, int py, double deltaY) {
         
				if (deltaY == 0) {
					return;
				}

				double factor;

				if (deltaY > 0) {
					factor = 1.1;
				}
				else {
					factor = 1.0 / 1.1;
				}

        viewport.zoomAt(px, py, factor);
         
    }

    public void onMouseDrag(double dx, double dy) {
        viewport.pan(dx, dy);
         
    }

    public void onFractalSelected(FractalType type) {
        int currentMaxIterations = renderer.getFractal().getMaxIterations();
        Fractal newFractal = switch (type) {
            case MANDELBROT -> new Mandelbrot(currentMaxIterations);
            case JULIA -> new Julia(currentMaxIterations, new Complex(-0.7, 0.27015));
            case BURNING_SHIP -> new BurningShip(currentMaxIterations);
        };
        renderer.setFractal(newFractal);
    }

    public void onPaletteSelected(PaletteType type) {
        ColorPalette newPalette = switch (type) {
            case GRAYSCALE -> new GrayscalePalette();
            case RAINBOW -> new RainbowPalette();
        };
        renderer.setPalette(newPalette);
    }

    public void onMaxIterationsChanged(int newMaxIterations) {
        renderer.getFractal().setMaxIterations(newMaxIterations);
    }

    public void onExportRequested(WritableImage currentImage, File targetFile) throws IOException {
        exportManager.exportToPNG(currentImage, targetFile);
    }

    public Viewport getViewport() {
        return viewport;
    }
}
