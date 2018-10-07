import static java.lang.Math.*;
import java.util.Random;

public class Main {

	/**
	 * Выполняет лабораторную работу №1 с se.ifmo.ru под вариантом номер 108
	 * @param args можно не вводить, они все равно ни на что не повиляют
	 */

	public static void main(String[] args) {


		short[] b = new short[10];
		short putNumber = 2; // число, которое мы будем класть в массив уменьшая его на 2
		double val1;
		double val2;
		float[] x = new float[16];
		float randomNumber;
		final byte RANGE = 16;
		final byte BIAS = -6;
		Random random = new Random(System.currentTimeMillis());
		double[][] c = new double[10][16];

		for (int i = 0; i < 10; i++) {
			b[i] = putNumber;
			putNumber += 2;
		}

		
		for (int i = 0; i < 16; i++) {
			x[i] = random.nextFloat() * RANGE + BIAS;
			randomNumber = x[i];
		}

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 16; j++) {
				if (b[i] == 2) {
					c[i][j] = log (pow (tan(3 * pow(x[j], x[j] * (4 + x[j]))) , 2));
				} else if ((b[i] == 6) || (b[i] == 8) || (b[i] == 12) || (b[i] == 18) || (b[i] == 20)) {
					c[i][j] = pow(log(abs(x[j])) / 2, 2);
				} else {
					val1 = pow(pow(4 * (asin( (x[j] + 2) / 16 ) + 1 ), pow (x[j], 2 * x[j]) ), 0.25 / (cbrt (atan( (x[j] + 2) / 16 )) - 1));
					val2 = sin(cbrt(sin(x[j]))) * (asin(cos(pow(cos(x[j] / 4), 2))) - 1);
					c[i][j] = pow(val1, val2);
				}

				System.out.printf("%10.3f ", c[i][j]);
			}
			System.out.println();
		}
	}
}