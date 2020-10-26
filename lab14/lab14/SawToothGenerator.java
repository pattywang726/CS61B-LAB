package lab14;

import lab14lib.Generator;

public class SawToothGenerator implements Generator {
    private int period;
    private int state;

    public SawToothGenerator(int period) {
        state = 0;
        this.period = period;
    }

    @Override
    public double next() {
        state += 1;
        double periodX = state % period;
        return (periodX == 0) ? -1 : normalize(periodX);
    }

    private double normalize (double value) {
        return 2 * ((value - 0) / (period - 1)) - 1;
    }
}
