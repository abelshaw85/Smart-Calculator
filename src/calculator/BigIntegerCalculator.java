package calculator;

import java.math.BigInteger;

public class BigIntegerCalculator {
    public static BigInteger add(BigInteger num1, BigInteger num2) {
        return num1.add(num2);
    }

    public static BigInteger subtract(BigInteger num1, BigInteger num2) {
        return num1.subtract(num2);
    }

    public static BigInteger multiply(BigInteger num1, BigInteger num2) {
        return num1.multiply(num2);
    }

    public static BigInteger divide(BigInteger dividend, BigInteger divisor) {
        if (!divisor.equals(BigInteger.ZERO)) {
            return dividend.divide(divisor);
        }
        return BigInteger.ZERO;
    }

    public static BigInteger power(BigInteger num, int exponent) { //note, exponent must be an int
        return num.pow(exponent);
    }
}
