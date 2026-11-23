
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

/**
 * Clase encargada de realizar las operaciones matemáticas.
 */
public class Calculator {

    /** Primer número introducido por el usuario. */
    public static int a;

    /** Segundo número introducido por el usuario. */
    public static int b;

    /**
     * Divide el primer número entre el segundo.
     * Esta operación no está disponible en la interfaz.
     *
     * @return Resultado de la división o mensaje de error.
     */
    public static String di() {
        if (b == 0) {
            return "No se puede dividir entre cero";
        }

        return BigDecimal.valueOf(a)
                .divide(BigDecimal.valueOf(b), MathContext.DECIMAL64)
                .toPlainString();
    }

    /**
     * Suma los dos números.
     *
     * @return Resultado de la suma.
     */
    public static String su() {
        return String.valueOf((long) a + b);
    }

    /**
     * Resta el segundo número del primero.
     *
     * @return Resultado de la resta.
     */
    public static String re() {
        return String.valueOf((long) a - b);
    }

    /**
     * Multiplica los dos números.
     *
     * @return Resultado de la multiplicación.
     */
    public static String mu() {
        return String.valueOf((long) a * b);
    }

    /**
     * Eleva el primer número a la potencia del segundo.
     * Los exponentes negativos producen fracciones exactas.
     *
     * @return Resultado de la potencia.
     */
    public static String po() {
        if (a == 0 && b < 0) {
            return "Indefinido: cero con exponente negativo";
        }

        if (a == 0 && b == 0) {
            return "Indefinido: cero elevado a cero";
        }

        if (b >= 0) {
            return BigInteger.valueOf(a).pow(b).toString();
        }

        if (a == 1) {
            return "1";
        }

        if (a == -1) {
            return b % 2 == 0 ? "1" : "-1";
        }

        return (a < 0 && b % 2 != 0 ? "-1/" : "1/")
                + BigInteger.valueOf(a).abs().pow(-b);
    }
}
