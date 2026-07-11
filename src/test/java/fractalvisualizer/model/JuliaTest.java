package fractalvisualizer.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JuliaTest {

    private static final double EPSILON = 1e-10;

    @Test
    void distantInitialPointEscapesImmediately() {
        Julia fractal = new Julia(100, new Complex(-0.7, 0.27015));

        int iterations = fractal.computeIterations(new Complex(3.0, 0.0));

        assertEquals(0, iterations);
    }

    @Test
    void zeroWithZeroParameterDoesNotEscape() {
        Julia fractal = new Julia(50, new Complex(0.0, 0.0));

        int iterations = fractal.computeIterations(new Complex(0.0, 0.0));

        assertEquals(50, iterations);
    }

    @Test
    void parameterCanBeChanged() {
        Julia fractal = new Julia(100, new Complex(-0.7, 0.27015));

        fractal.setC(new Complex(0.285, 0.01));

        assertEquals(0.285, fractal.getC().getRe(), EPSILON);
        assertEquals(0.01, fractal.getC().getIm(), EPSILON);
    }

    @Test
    void nameIsJulia() {
        assertEquals("Julia", new Julia(100, new Complex(0.0, 0.0)).getName());
    }
}
