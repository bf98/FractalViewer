package fractalvisualizer.model;

/** base comune frattali */
public abstract class Fractal {

    private int maxIterations;
    private final double escapeRadius;

    protected Fractal(int maxIterations, double escapeRadius) {
        this.maxIterations = maxIterations;
        this.escapeRadius = escapeRadius;
    }

    /**
     * Calcola le iterazioni per un punto.
     *
     * @param c punto da controllare
     * @return numero iterazioni
     */
    public abstract int computeIterations(Complex c);

    public int getMaxIterations() {
        return maxIterations;
    }

    public void setMaxIterations(int maxIterations) {
        if (maxIterations <= 0) {
            throw new IllegalArgumentException("maxIterations deve essere positivo");
        }
        this.maxIterations = maxIterations;
    }

    protected double getEscapeRadiusSquared() {
        return escapeRadius * escapeRadius;
    }

    public abstract Fractal copy();

    /** restituisce nome frattale */
    public abstract String getName();
}
