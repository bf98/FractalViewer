package fractalvisualizer.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MandelbrotTest {

    @Test
    void originDoesNotEscapeWithinMaximumIterations() {
        Mandelbrot fractal = new Mandelbrot(100);

        int iterations = fractal.computeIterations(new Complex(0.0, 0.0));

        assertEquals(100, iterations);
    }

    @Test
    void minusOneDoesNotEscapeWithinMaximumIterations() {
        Mandelbrot fractal = new Mandelbrot(100);

        int iterations = fractal.computeIterations(new Complex(-1.0, 0.0));

        assertEquals(100, iterations);
    }

    @Test
    void distantPointEscapesAfterFirstIteration() {
        Mandelbrot fractal = new Mandelbrot(100);

        int iterations = fractal.computeIterations(new Complex(3.0, 0.0));

        assertEquals(1, iterations);
    }

    @Test
    void maxIterationsCanBeChanged() {
        Mandelbrot fractal = new Mandelbrot(100);

        fractal.setMaxIterations(250);

        assertEquals(250, fractal.getMaxIterations());
    }

    @Test
    void setMaxIterationsRejectsNonPositiveValues() {
        Mandelbrot fractal = new Mandelbrot(100);

        assertThrows(IllegalArgumentException.class, () -> fractal.setMaxIterations(0));
        assertThrows(IllegalArgumentException.class, () -> fractal.setMaxIterations(-1));
    }

    @Test
    void nameIsMandelbrot() {
        assertEquals("Mandelbrot", new Mandelbrot(100).getName());
    }
}
