package lab14;

import lab14lib.Generator;

import static java.lang.Math.*;

public class AcceleratingSawToothGenerator implements Generator {
    private int period;
    private double factor;
    private int state;
    private int periodX;

    public AcceleratingSawToothGenerator (int period, double factor) {
        this.period = period;
        this.factor = factor;
        state = 0;
    }

    @Override
    public double next() {
        if (periodX == period) {
            period = (int) round(period * factor);
            periodX = 0;
        }
        state += 1;
        periodX += 1;
        return (periodX == period) ? -1 : normalize(periodX);
    }

    private double normalize (double value) {
        return 2 * ((value - 0) / (period - 1)) - 1;
    }
}
