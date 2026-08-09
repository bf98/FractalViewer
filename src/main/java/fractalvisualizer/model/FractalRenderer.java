package fractalvisualizer.model;

import javafx.scene.image.PixelFormat;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.function.BooleanSupplier;

/** crea l'immagine del frattale */
public class FractalRenderer {

    private Fractal fractal;
    private ColorPalette palette;

    public FractalRenderer(Fractal fractal, ColorPalette palette) {
        this.fractal = fractal;
        this.palette = palette;
    }

    /**
     * Calcola i pixel dell'immagine.
     *
     * @return il risultato o null se annullato
     */
    public RenderResult renderPixels(
            Viewport viewport,
            int width,
            int height,
            BooleanSupplier cancelled
    ) {
        int[] pixels = new int[width * height];
        int maxIterations = fractal.getMaxIterations();

        for (int y = 0; y < height; y++) {
            if (cancelled.getAsBoolean()) {
                return null;
            }

            for (int x = 0; x < width; x++) {
                // controlla periodicamente se il calcolo e' stato annullato
                if ((x & 63) == 0 && cancelled.getAsBoolean()) {
                    return null;
                }

                Complex point = viewport.pixelToComplex(x, y);
                int iterations = fractal.computeIterations(point);
                Color color = palette.colorFor(iterations, maxIterations);
                pixels[y * width + x] = toArgb(color);
            }
        }

        return new RenderResult(width, height, pixels);
    }

    /** crea immagine in modo sincrono */
    public WritableImage renderFrame(Viewport viewport, int width, int height) {
        RenderResult result = renderPixels(viewport, width, height, () -> false);
        return toWritableImage(result);
    }

    /** converte risultato in immagine */
    public static WritableImage toWritableImage(RenderResult result) {
        WritableImage image = new WritableImage(result.width(), result.height());
        image.getPixelWriter().setPixels(
                0,
                0,
                result.width(),
                result.height(),
                PixelFormat.getIntArgbInstance(),
                result.pixels(),
                0,
                result.width()
        );
        return image;
    }

    private static int toArgb(Color color) {
        int a = (int) Math.round(color.getOpacity() * 255.0);
        int r = (int) Math.round(color.getRed() * 255.0);
        int g = (int) Math.round(color.getGreen() * 255.0);
        int b = (int) Math.round(color.getBlue() * 255.0);

        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    public void setFractal(Fractal fractal) {
        this.fractal = fractal;
    }

    public void setPalette(ColorPalette palette) {
        this.palette = palette;
    }

    public Fractal getFractal() {
        return fractal;
    }

    public ColorPalette getPalette() {
        return palette;
    }
}
