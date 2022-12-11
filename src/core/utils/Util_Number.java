package core.utils;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Util_Number {

    public boolean isNotInt(final String s) {
        try {
            Integer.parseInt(s);
        } catch (final NumberFormatException nfe) {
            return true;
        }
        return false;
    }

    public boolean isNotDouble(final String s) {
        try {
            Double.parseDouble(s);
        } catch (final NumberFormatException nfe) {
            return true;
        }
        return false;
    }

    public boolean IsWithin(final int NumberCheaking, final int Num1, final int Num2, final Boolean IncludingNum1Num2) {

        final int Min = Math.min(Num1, Num2);
        final int Max = Math.max(Num1, Num2);

        if (IncludingNum1Num2) {
            return NumberCheaking <= Max && NumberCheaking >= Min;
        }
        else {
            return NumberCheaking < Max && NumberCheaking > Min;
        }

    }

    public boolean IsWithin(final double NumberCheaking, final double Num1, final double Num2, final Boolean IncludingNum1Num2) {

        final double Min = Math.min(Num1, Num2);
        final double Max = Math.max(Num1, Num2);

        if (IncludingNum1Num2) {
            return NumberCheaking <= Max && NumberCheaking >= Min;
        }
        else {
            return NumberCheaking < Max && NumberCheaking > Min;
        }

    }

    public double round(final double Value, int DecimalPlaces) {

        if (DecimalPlaces <= 0) {
            DecimalPlaces = 1;
        }

        String Format = "#." + "#".repeat(DecimalPlaces);

        final DecimalFormat df = new DecimalFormat(Format);
        df.setRoundingMode(RoundingMode.CEILING);

        return Double.parseDouble(df.format(Value));
    }


    /**
     * Will bind the value between the min and max.
     * @param value The value to bind.
     * @param max The max value it can be.
     * @param min The min value it can be
     * @return The value that in binded to the min and max.
     */
    public double bind(double value, double max, double min){
        return Math.min(min, Math.max(value, max));
    }

    public int differance(int i1, int i2) {
        return i1 >= i2 ? i1 - i2 : i2 - i1;
    }

    public float differance(float i1, float i2) {
        return i1 >= i2 ? i1 - i2 : i2 - i1;
    }

    public double differance(double i1, double i2) {
        return i1 >= i2 ? i1 - i2 : i2 - i1;
    }

    public double distance (double x, double y, double z, double x2, double y2, double z2) {
        double fx = (x - x2);
        double fy = (y - y2);
        double fz = (z - z2);
        return (fx * fx + fy * fy + fz * fz);
    }
}
