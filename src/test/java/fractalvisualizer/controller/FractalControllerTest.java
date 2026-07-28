package fractalvisualizer.controller;

import fractalvisualizer.model.*;
import javafx.scene.image.WritableImage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FractalControllerTest {

    private static final double EPSILON = 1e-10;

    private Fixture createFixture() {
        FractalRenderer renderer = new FractalRenderer(
                new Mandelbrot(100),
                new RainbowPalette()
        );
        Viewport viewport = new Viewport(-0.5, 0.0, 1.0, 8, 6);
        FractalController controller = new FractalController(
                renderer,
                viewport,
                new ExportManager()
        );
        return new Fixture(renderer, viewport, controller);
    }

    @Test
    void selectingJuliaReplacesCurrentFractalAndPreservesIterations() {
        Fixture fixture = createFixture();

        fixture.controller.onFractalSelected(FractalType.JULIA);

        assertInstanceOf(Julia.class, fixture.renderer.getFractal());
        assertEquals(100, fixture.renderer.getFractal().getMaxIterations());
    }

    @Test
    void selectingBurningShipReplacesCurrentFractal() {
        Fixture fixture = createFixture();

        fixture.controller.onFractalSelected(FractalType.BURNING_SHIP);

        assertInstanceOf(BurningShip.class, fixture.renderer.getFractal());
    }

    @Test
    void selectingGrayscaleReplacesCurrentPalette() {
        Fixture fixture = createFixture();

        fixture.controller.onPaletteSelected(PaletteType.GRAYSCALE);

        assertInstanceOf(GrayscalePalette.class, fixture.renderer.getPalette());
    }

    @Test
    void changingMaxIterationsUpdatesCurrentFractal() {
        Fixture fixture = createFixture();

        fixture.controller.onMaxIterationsChanged(250);

        assertEquals(250, fixture.renderer.getFractal().getMaxIterations());
    }

    @Test
    void positiveScrollIncreasesZoom() {
        Fixture fixture = createFixture();
        double before = fixture.viewport.getZoom();

        fixture.controller.onScroll(4, 3, 40.0);

        assertEquals(before * 1.1, fixture.viewport.getZoom(), EPSILON);
    }

    @Test
    void negativeScrollDecreasesZoom() {
        Fixture fixture = createFixture();
        double before = fixture.viewport.getZoom();

        fixture.controller.onScroll(4, 3, -40.0);

        assertEquals(before / 1.1, fixture.viewport.getZoom(), EPSILON);
    }

    @Test
    void zeroVerticalScrollDoesNotChangeZoom() {
        Fixture fixture = createFixture();
        double before = fixture.viewport.getZoom();

        fixture.controller.onScroll(4, 3, 0.0);

        assertEquals(before, fixture.viewport.getZoom(), EPSILON);
    }

    @Test
    void mouseDragPansViewport() {
        Fixture fixture = createFixture();
        double centerXBefore = fixture.viewport.getCenterX();
        double centerYBefore = fixture.viewport.getCenterY();

        fixture.controller.onMouseDrag(1.0, 1.0);

        assertNotEquals(centerXBefore, fixture.viewport.getCenterX());
        assertNotEquals(centerYBefore, fixture.viewport.getCenterY());
    }

    @Test
    void renderCurrentFrameUsesViewportDimensions() {
        Fixture fixture = createFixture();

        WritableImage image = fixture.controller.renderCurrentFrame();

        assertEquals(8, (int) image.getWidth());
        assertEquals(6, (int) image.getHeight());
    }

    private record Fixture(
            FractalRenderer renderer,
            Viewport viewport,
            FractalController controller
    ) {}
}
