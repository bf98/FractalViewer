package fractalvisualizer.controller;

import fractalvisualizer.model.*;
import javafx.concurrent.Task;
import javafx.scene.image.WritableImage;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/** gestisce le azioni dell'utente e il rendering */
public class FractalController {

    private final FractalRenderer renderer;
    private final Viewport viewport;
    private final ExportManager exportManager;

    /** esegue il rendering in un thread separato */
    private final ThreadPoolExecutor renderExecutor;

    private Task<RenderResult> currentTask;
    private long renderGeneration = 0;

    public FractalController(FractalRenderer renderer, Viewport viewport, ExportManager exportManager) {
        this.renderer = renderer;
        this.viewport = viewport;
        this.exportManager = exportManager;

        this.renderExecutor = new ThreadPoolExecutor(
                1,
                1,
                0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(),
                runnable -> {
                    Thread thread = new Thread(runnable, "fractal-render-thread");
                    thread.setDaemon(true);
                    return thread;
                }
        );
    }

    /** crea un'immagine in modo sincrono */
    public WritableImage renderCurrentFrame() {
        return renderer.renderFrame(
                viewport,
                viewport.getCanvasWidth(),
                viewport.getCanvasHeight()
        );
    }

    /** avvia un nuovo rendering in background */
    public void renderCurrentFrameAsync(
            Consumer<WritableImage> onSuccess,
            Consumer<Throwable> onError
    ) {
        cancelPreviousRendering();

        long generation = ++renderGeneration;

        Viewport viewportSnapshot = viewport.copy();
        FractalRenderer rendererSnapshot = new FractalRenderer(
                renderer.getFractal().copy(),
                renderer.getPalette().copy()
        );

        int width = viewportSnapshot.getCanvasWidth();
        int height = viewportSnapshot.getCanvasHeight();

        Task<RenderResult> task = new Task<>() {
            @Override
            protected RenderResult call() {
                return rendererSnapshot.renderPixels(
                        viewportSnapshot,
                        width,
                        height,
                        this::isCancelled
                );
            }
        };

        currentTask = task;

        task.setOnSucceeded(event -> {
            // ignora i risultati superati
            if (generation != renderGeneration) {
                return;
            }

            RenderResult result = task.getValue();
            if (result == null) {
                return;
            }

            WritableImage image = FractalRenderer.toWritableImage(result);
            onSuccess.accept(image);
        });

        task.setOnFailed(event -> {
            if (generation != renderGeneration) {
                return;
            }

            Throwable exception = task.getException();
            if (onError != null && exception != null) {
                onError.accept(exception);
            }
        });

        renderExecutor.execute(task);
    }

		public int getCurrentCanvasWidth() {
			return viewport.getCanvasWidth();
		}

		public int getCurrentCanvasHeight() {
			return viewport.getCanvasHeight();
		}

    /** annulla i rendering superati */
    private void cancelPreviousRendering() {
        if (currentTask != null && !currentTask.isDone()) {
            currentTask.cancel(true);
        }

        renderExecutor.getQueue().clear();
    }

    public void onScroll(int px, int py, double deltaY) {
        if (deltaY == 0) {
            return;
        }

        double factor = deltaY > 0 ? 1.1 : 1.0 / 1.1;
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

    /** aggiorna risoluzione canvas */
    public void onCanvasSizeChanged(int width, int height) {
        viewport.setCanvasSize(width, height);
    }

    /** ripristina vista iniziale */
    public void onResetView() {
        viewport.resetZoom();
        viewport.setCenterX(-0.5);
        viewport.setCenterY(0.0);
    }

    public void onExportRequested(WritableImage currentImage, File targetFile) throws IOException {
        exportManager.exportToPNG(currentImage, targetFile);
    }

    /** ferma il worker */
    public void shutdown() {
        ++renderGeneration;
        cancelPreviousRendering();
        renderExecutor.shutdownNow();
    }

    public Viewport getViewport() {
        return viewport;
    }
}
