package fractalFinal;

import java.util.ArrayList;

import fractal.ConjuntoDePuntos;
import fractal.Fractal;
import fractal.Punto;

/**
 * Genera un fractal n-dimensional.
 * 
 * @author PABLO ARIENZA CARRERA
 * @version 24.08.2017
 */
public class FractalNDimensionalSimple extends Fractal {

	/**
	 * Constructor de la clase.
	 * 
	 * @param dimensiones
	 *            el tamaño de las dimensiones del espacio
	 * @param coordenadasCentro
	 *            el centro relativo del espacio n-dimensional
	 * @param iteraciones
	 *            el número máximo de veces que se iterará cada punto
	 * @param limite
	 *            el valor máximo de la función de escape
	 * @param escala
	 *            el modificador de la función de escape
	 */
	public FractalNDimensionalSimple(int[] dimensiones, int[] coordenadasCentro, int iteraciones, int limite,
			int escala) {
		this.fractal = new ArrayList<ConjuntoDePuntos>();
		this.ultimoLeido = 0;
		calculaLosConjuntos(dimensiones, coordenadasCentro, iteraciones, limite, escala);
	}// fin del constructor

	@Override
	public void calculaElConjuntoDelPunto(int[] coordenadas, int[] coordenadasCentro, int iteraciones, int limite,
			int escala) {
		float[] coordenadasRelativas = new float[coordenadas.length];
		// Se transforman las coordenadas absolutas en relativas al punto central del
		// espacio n-dimensional
		for (int i = 0; i < coordenadas.length; i++)
			coordenadasRelativas[i] = ((float) coordenadas[i] - (float) coordenadasCentro[i]) / (float) escala;
		float[] z = new float[coordenadas.length];
		for (int i = 0; i < z.length; i++)
			z[i] = 0;
		for (int i = 0; i < iteraciones; i++) {
			iteracionesDePuntos++; // Benchmarking
			float[] n = new float[coordenadas.length];
			for (int j = 0; j < n.length; j++)
				// La función fractal es f(z) = z^n + c con n = dimensiones del espacio
				n[j] = (float) Math.pow(z[i], coordenadas.length) + coordenadasRelativas[j];
			z = n;
			// La función de escape es SUMA((-1)^k * z[k]) de 0 a n < limite
			float resultadoFuncion = 0;
			for (int k = 0; k < z.length; k++) {
				if (k % 2 == 0) {
					resultadoFuncion += z[k];
				} else {
					resultadoFuncion -= z[k];
				}
			}
			if (resultadoFuncion > (float) limite) {
				addPuntoASuConjunto(new Punto(coordenadas), i);
				break;
			}
		}
	}// fin calculaElConjuntoDelPunto
}// fin FractalNDimensional
