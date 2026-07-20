package fractalvisualizer.model;

import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FractalRendererTest {

    @Test
    void rendererUsesFractalAndPalettePolymorphically() {
        Fractal fakeFractal = new Fractal(10, 2.0) {
            @Override
            public int computeIterations(Complex c) {
                return 5;
            }

            @Override
            public String getName() {
                return "Fake fractal";
            }
        };

        ColorPalette fakePalette = new ColorPalette() {
            @Override
            public Color colorFor(int iterations, int maxIterations) {
                assertEquals(5, iterations);
                assertEquals(10, maxIterations);
                return Color.RED;
            }

            @Override
            public String getName() {
                return "Fake palette";
            }
        };

        FractalRenderer renderer = new FractalRenderer(fakeFractal, fakePalette);
        Viewport viewport = new Viewport(0.0, 0.0, 1.0, 3, 2);

        WritableImage image = renderer.renderFrame(viewport, 3, 2);

        assertEquals(3, (int) image.getWidth());
        assertEquals(2, (int) image.getHeight());

        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 3; x++) {
                assertEquals(Color.RED, image.getPixelReader().getColor(x, y));
            }
        }
    }

    @Test
    void fractalCanBeReplacedAtRuntime() {
        FractalRenderer renderer = new FractalRenderer(
                new Mandelbrot(100),
                new GrayscalePalette()
        );

        Julia julia = new Julia(100, new Complex(-0.7, 0.27015));
        renderer.setFractal(julia);

        assertSame(julia, renderer.getFractal());
    }

    @Test
    void paletteCanBeReplacedAtRuntime() {
        FractalRenderer renderer = new FractalRenderer(
                new Mandelbrot(100),
                new GrayscalePalette()
        );

        RainbowPalette rainbow = new RainbowPalette();
        renderer.setPalette(rainbow);

        assertSame(rainbow, renderer.getPalette());
    }
}
