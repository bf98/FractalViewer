package fractalvisualizer.model;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GrayscalePaletteTest {

    private static final double EPSILON = 1e-10;

    @Test
    void pointInsideSetIsBlack() {
        GrayscalePalette palette = new GrayscalePalette();

        assertEquals(Color.BLACK, palette.colorFor(100, 100));
    }

    @Test
    void halfIterationsProducesHalfBrightnessGray() {
        GrayscalePalette palette = new GrayscalePalette();

        Color color = palette.colorFor(50, 100);

        assertEquals(0.5, color.getRed(), EPSILON);
        assertEquals(0.5, color.getGreen(), EPSILON);
        assertEquals(0.5, color.getBlue(), EPSILON);
        assertEquals(1.0, color.getOpacity(), EPSILON);
    }

    @Test
    void nameIsGrayscale() {
        assertEquals("Grayscale", new GrayscalePalette().getName());
    }
}
