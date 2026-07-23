package fractalvisualizer.model;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class ExportManager {

    public void exportToPNG(WritableImage image, File file) throws IOException {
        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
    }
}
