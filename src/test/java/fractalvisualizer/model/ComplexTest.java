package fractalvisualizer.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ComplexTest {

    private static final double EPSILON = 1e-10;

    @Test
    void additionProducesExpectedComplexNumber() {
        Complex a = new Complex(1.0, 2.0);
        Complex b = new Complex(3.0, -4.0);

        Complex result = a.add(b);

        assertEquals(4.0, result.getRe(), EPSILON);
        assertEquals(-2.0, result.getIm(), EPSILON);
    }

    @Test
    void multiplicationProducesExpectedComplexNumber() {
        Complex a = new Complex(1.0, 2.0);
        Complex b = new Complex(3.0, -4.0);

        Complex result = a.multiply(b);

        assertEquals(11.0, result.getRe(), EPSILON);
        assertEquals(2.0, result.getIm(), EPSILON);
    }

    @Test
    void modulusSquaredIsComputedWithoutSquareRoot() {
        Complex z = new Complex(3.0, 4.0);

        assertEquals(25.0, z.modulusSquared(), EPSILON);
    }

    @Test
    void absMakesBothComponentsNonNegative() {
        Complex z = new Complex(-3.0, -4.0);

        Complex result = z.abs();

        assertEquals(3.0, result.getRe(), EPSILON);
        assertEquals(4.0, result.getIm(), EPSILON);
    }

    @Test
    void operationsDoNotModifyOriginalObjects() {
        Complex original = new Complex(1.0, 2.0);
        Complex other = new Complex(5.0, 7.0);

        original.add(other);
        original.multiply(other);
        original.abs();

        assertEquals(1.0, original.getRe(), EPSILON);
        assertEquals(2.0, original.getIm(), EPSILON);
    }
}
