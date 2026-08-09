package fractalvisualizer.model;

import javafx.scene.paint.Color;

public class RainbowPalette extends ColorPalette {

    @Override
    public Color colorFor(int iterations, int maxIterations) {
        if (iterations >= maxIterations) {
            return Color.BLACK;
        }
        double hue = 360.0 * ((double) iterations / maxIterations);
        return Color.hsb(hue, 0.8, 1.0);
    }

    @Override
    public ColorPalette copy() {
        return new RainbowPalette();
    }

    @Override
    public String getName() {
        return "Rainbow";
    }
}
