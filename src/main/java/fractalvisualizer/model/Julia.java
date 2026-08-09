package fractalvisualizer.model;

public class Julia extends Fractal {

    private Complex c;

    public Julia(int maxIterations, Complex c) {
        super(maxIterations, 2.0);
        this.c = c;
    }

    @Override
    public int computeIterations(Complex point) {
        Complex z = point;
        int iterations = 0;

        while (iterations < getMaxIterations() && z.modulusSquared() <= getEscapeRadiusSquared()) {
            z = z.multiply(z).add(c);
            iterations++;
        }

        return iterations;
    }

    public Complex getC() {
        return c;
    }

    public void setC(Complex c) {
        this.c = c;
    }

    @Override
    public Fractal copy() {
        return new Julia(getMaxIterations(), c);
    }

    @Override
    public String getName() {
        return "Julia";
    }
}
