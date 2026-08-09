package fractalvisualizer.model;

import javafx.scene.paint.Color;

public class GrayscalePalette extends ColorPalette {

    @Override
    public Color colorFor(int iterations, int maxIterations) {
        if (iterations >= maxIterations) {
            return Color.BLACK;
        }
        double brightness = (double) iterations / maxIterations;
        return Color.gray(brightness);
    }

    @Override
    public ColorPalette copy() {
        return new GrayscalePalette();
    }

    @Override
    public String getName() {
        return "Grayscale";
    }
}
