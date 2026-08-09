package fractalvisualizer.model;

import javafx.scene.paint.Color;

public abstract class ColorPalette {

    /**
     * Restituisce il colore di un punto.
     *
     * @param iterations iterazioni eseguite
     * @param maxIterations limite massimo iterazioni
     * @return colore punto
     */
    public abstract Color colorFor(int iterations, int maxIterations);

    public abstract ColorPalette copy();

    public abstract String getName();
}
