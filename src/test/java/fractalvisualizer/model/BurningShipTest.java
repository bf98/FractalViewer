package fractalvisualizer.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BurningShipTest {

    @Test
    void originDoesNotEscapeWithinMaximumIterations() {
        BurningShip fractal = new BurningShip(100);

        int iterations = fractal.computeIterations(new Complex(0.0, 0.0));

        assertEquals(100, iterations);
    }

    @Test
    void distantPointEscapesAfterFirstIteration() {
        BurningShip fractal = new BurningShip(100);

        int iterations = fractal.computeIterations(new Complex(3.0, 0.0));

        assertEquals(1, iterations);
    }

    @Test
    void nameIsBurningShip() {
        assertEquals("Burning Ship", new BurningShip(100).getName());
    }
}
