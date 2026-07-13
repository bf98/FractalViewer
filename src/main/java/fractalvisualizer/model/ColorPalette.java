package fractalvisualizer.model;

import javafx.scene.paint.Color;

 





public abstract class ColorPalette {

     




    public abstract Color colorFor(int iterations, int maxIterations);

    public abstract String getName();
}
