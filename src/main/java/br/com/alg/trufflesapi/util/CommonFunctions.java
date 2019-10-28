package br.com.alg.trufflesapi.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class CommonFunctions {

    public static String padRight(String value, int size) {
        StringBuilder builder = new StringBuilder(value);
        int count = size - value.length();
        while (count > 0) {
            builder.append(" ");
            count--;
        }
        return builder.toString();
    }

    public static String padLeft(String value, int size) {
        StringBuilder textEnd = new StringBuilder(value);
        StringBuilder textFront = new StringBuilder("");

        int count = size - value.length();
        while (count > 0) {
            textFront.append(" ");
            count--;
        }

        textFront.append(textEnd.toString());

        return textFront.toString();
    }

    public static double round(Double value, Integer precision) {
        BigDecimal bd = new BigDecimal(value);
        BigDecimal rounded = bd.setScale(precision, BigDecimal.ROUND_HALF_UP);
        return rounded.doubleValue();
    }

    public static String formatNumber(double value, String pattern) {
        return new DecimalFormat(pattern).format(value);
    }
}
