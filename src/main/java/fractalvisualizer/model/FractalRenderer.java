package fractalvisualizer.model;

import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

 










public class FractalRenderer {

    private Fractal fractal;
    private ColorPalette palette;

    public FractalRenderer(Fractal fractal, ColorPalette palette) {
        this.fractal = fractal;
        this.palette = palette;
    }

    public WritableImage renderFrame(Viewport viewport, int width, int height) {
        WritableImage image = new WritableImage(width, height);
        PixelWriter writer = image.getPixelWriter();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Complex point = viewport.pixelToComplex(x, y);
                int iterations = fractal.computeIterations(point);
                writer.setColor(x, y, palette.colorFor(iterations, fractal.getMaxIterations()));
            }
        }

        return image;
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
