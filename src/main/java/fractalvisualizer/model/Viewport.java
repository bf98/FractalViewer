package fractalvisualizer.model;

/** gestisce area visibile, zoom e panning */
public class Viewport {

    private double centerX;
    private double centerY;
    private double zoom; 
    private int canvasWidth;
    private int canvasHeight;

    public Viewport(double centerX, double centerY, double zoom, int canvasWidth, int canvasHeight) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.zoom = zoom;
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
    }

    /** pixel -> punto piano complesso */
    public Complex pixelToComplex(int px, int py) {
        double scale = 1.0 / zoom;
        double re = centerX + (px - canvasWidth / 2.0) * scale / canvasWidth * 4.0;
        double im = centerY + (py - canvasHeight / 2.0) * scale / canvasHeight * 4.0;
        return new Complex(re, im);
    }

    /**
     * Applica lo zoom sul punto indicato.
     *
     * @param factor >1 ingrandisce, <1 riduce
     */
    public void zoomAt(int px, int py, double factor) {
        Complex targetBefore = pixelToComplex(px, py);
        this.zoom *= factor;
        Complex targetAfter = pixelToComplex(px, py);

        // ferma punto sotto cursore mouse
        this.centerX += targetBefore.getRe() - targetAfter.getRe();
        this.centerY += targetBefore.getIm() - targetAfter.getIm();
    }

    public void pan(double dxPixels, double dyPixels) {
        double scale = 1.0 / zoom;
        centerX -= dxPixels * scale / canvasWidth * 4.0;
        centerY -= dyPixels * scale / canvasHeight * 4.0;
    }

    public double getCenterX() {
        return centerX;
    }

		public void setCenterX(double that) {
				this.centerX = that;
		}

    public double getCenterY() {
        return centerY;
    }

		public void setCenterY(double that) {
				this.centerY = that;
		}

    public double getZoom() {
        return zoom;
    }

		public void resetZoom() {
			this.zoom = 1;
		}

    public int getCanvasWidth() {
        return canvasWidth;
    }

    public int getCanvasHeight() {
        return canvasHeight;
    }

    public void setCanvasWidth(int that) {
        setCanvasSize(that, this.canvasHeight);
    }

    public void setCanvasHeight(int that) {
        setCanvasSize(this.canvasWidth, that);
    }

    public void setCanvasSize(int width, int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException(
                    "Larghezza e altezza del Canvas devono essere maggiori di zero."
            );
        }

        this.canvasWidth = width;
        this.canvasHeight = height;
    }

    public Viewport copy() {
        return new Viewport(centerX, centerY, zoom, canvasWidth, canvasHeight);
    }
}
