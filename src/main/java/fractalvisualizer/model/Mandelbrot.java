package fractalvisualizer.model;

 



public class Mandelbrot extends Fractal {

    public Mandelbrot(int maxIterations) {
        super(maxIterations, 2.0);
    }

    @Override
    public int computeIterations(Complex c) {
        Complex z = new Complex(0, 0);
        int iterations = 0;

        while (iterations < getMaxIterations() && z.modulusSquared() <= getEscapeRadiusSquared()) {
            z = z.multiply(z).add(c);
            iterations++;
        }

        return iterations;
    }

    @Override
    public String getName() {
        return "Mandelbrot";
    }
}
