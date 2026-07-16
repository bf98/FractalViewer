package fractalvisualizer.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ViewportTest {

    private static final double EPSILON = 1e-10;

    @Test
    void centerPixelMapsToViewportCenter() {
        Viewport viewport = new Viewport(-0.5, 0.0, 1.0, 800, 600);

        Complex point = viewport.pixelToComplex(400, 300);

        assertEquals(-0.5, point.getRe(), EPSILON);
        assertEquals(0.0, point.getIm(), EPSILON);
    }

    @Test
    void topLeftPixelMapsToExpectedComplexCoordinate() {
        Viewport viewport = new Viewport(-0.5, 0.0, 1.0, 800, 600);

        Complex point = viewport.pixelToComplex(0, 0);

        assertEquals(-2.5, point.getRe(), EPSILON);
        assertEquals(-2.0, point.getIm(), EPSILON);
    }

    @Test
    void zoomKeepsPointUnderCursorFixed() {
        Viewport viewport = new Viewport(-0.5, 0.0, 1.0, 800, 600);
        int mouseX = 250;
        int mouseY = 180;

        Complex before = viewport.pixelToComplex(mouseX, mouseY);
        viewport.zoomAt(mouseX, mouseY, 2.0);
        Complex after = viewport.pixelToComplex(mouseX, mouseY);

        assertEquals(before.getRe(), after.getRe(), EPSILON);
        assertEquals(before.getIm(), after.getIm(), EPSILON);
        assertEquals(2.0, viewport.getZoom(), EPSILON);
    }

    @Test
    void panChangesViewportCenterAccordingToPixelDisplacement() {
        Viewport viewport = new Viewport(-0.5, 0.0, 1.0, 800, 600);

        viewport.pan(80.0, 60.0);

        assertEquals(-0.9, viewport.getCenterX(), EPSILON);
        assertEquals(-0.4, viewport.getCenterY(), EPSILON);
    }
}
