package fractalvisualizer.model;

import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ExportManagerTest {

    @TempDir
    Path tempDir;

    @Test
    void exportCreatesReadablePngWithCorrectDimensions() throws IOException {
        WritableImage image = new WritableImage(10, 8);
        image.getPixelWriter().setColor(0, 0, Color.RED);

        File output = tempDir.resolve("fractal-test.png").toFile();
        ExportManager manager = new ExportManager();

        manager.exportToPNG(image, output);

        assertTrue(output.exists());
        assertTrue(output.length() > 0);

        BufferedImage reloaded = ImageIO.read(output);
        assertNotNull(reloaded);
        assertEquals(10, reloaded.getWidth());
        assertEquals(8, reloaded.getHeight());
    }
}
