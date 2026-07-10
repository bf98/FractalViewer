package fractalvisualizer.model;

 




public class BurningShip extends Fractal {

    public BurningShip(int maxIterations) {
        super(maxIterations, 2.0);
    }

    @Override
    public int computeIterations(Complex c) {
        Complex z = new Complex(0, 0);
        int iterations = 0;

        while (iterations < getMaxIterations() && z.modulusSquared() <= getEscapeRadiusSquared()) {
            Complex absZ = z.abs();
            z = absZ.multiply(absZ).add(c);
            iterations++;
        }

        return iterations;
    }

    @Override
    public String getName() {
        return "Burning Ship";
    }
}
