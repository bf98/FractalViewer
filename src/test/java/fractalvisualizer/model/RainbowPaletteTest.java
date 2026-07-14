package fractalvisualizer.model;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RainbowPaletteTest {

    private static final double EPSILON = 1e-10;

    @Test
    void pointInsideSetIsBlack() {
        RainbowPalette palette = new RainbowPalette();

        assertEquals(Color.BLACK, palette.colorFor(100, 100));
    }

    @Test
    void escapingPointUsesExpectedHsbColor() {
        RainbowPalette palette = new RainbowPalette();

        Color expected = Color.hsb(180.0, 0.8, 1.0);
        Color actual = palette.colorFor(50, 100);

        assertEquals(expected.getRed(), actual.getRed(), EPSILON);
        assertEquals(expected.getGreen(), actual.getGreen(), EPSILON);
        assertEquals(expected.getBlue(), actual.getBlue(), EPSILON);
        assertEquals(expected.getOpacity(), actual.getOpacity(), EPSILON);
    }

    @Test
    void nameIsRainbow() {
        assertEquals("Rainbow", new RainbowPalette().getName());
    }
}
