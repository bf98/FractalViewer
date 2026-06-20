package fractalvisualizer.model;

public final class Complex {

    private final double re;
    private final double im;

    public Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }

    public double getRe() {
        return re;
    }

    public double getIm() {
        return im;
    }

    public Complex add(Complex other) {
        return new Complex(this.re + other.re, this.im + other.im);
    }

    public Complex multiply(Complex other) {
        double newRe = this.re * other.re - this.im * other.im;
        double newIm = this.re * other.im + this.im * other.re;
        return new Complex(newRe, newIm);
    }

    public double modulusSquared() {
        return re * re + im * im;
    }

    public Complex abs() {
        return new Complex(Math.abs(re), Math.abs(im));
    }

    @Override
    public String toString() {
        return String.format("(%.6f %+.6fi)", re, im);
    }
}
