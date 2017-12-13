package fractalFinal;

import fractal.*;

/**
 * Genera un fractal de Mandelbrot.
 * 
 * @author PABLO ARIENZA CARRERA
 * @version 24.08.2017
 */
public class Mandelbrot2D extends Fractal {

	/**
	 * Constructor de la clase.
	 * 
	 * @see Fractal.java
	 */
	public Mandelbrot2D(int[] dimensiones, int[] coordenadasCentro, int iteraciones, int limite, int escala) {
		super(dimensiones, coordenadasCentro, iteraciones, limite, escala);
	}

	@Override
	public void calculaElConjuntoDelPunto(int[] coordenadas, int[] coordenadasCentro, int iteraciones, int limite,
			int escala) {
		contadorDePuntos ++;
		float xC = ((float) coordenadas[0] - (float) coordenadasCentro[0]) / (float) escala;
		float yC = ((float) coordenadas[1] - (float) coordenadasCentro[1]) / (float) escala;
		float zX = 0;
		float zY = 0;
		// para el punto c = a + b*i
		// f(z) = z^2 + c
		for (int i = 0; i < iteraciones; i++) {
			iteracionesDePuntos++; // Benchmarking
			float nX = zX * zX - zY * zY + xC;
			float nY = 2 * zX * zY + yC;
			zX = nX;
			zY = nY;
			// La función de escape es x^2 + y^2 < limite
			if (zX * zX + zY * zY > (float) limite) {
				Punto punto = seleccionaTipoDePunto(contadorDePuntos, coordenadas);
				addPuntoASuConjunto(punto, i);
				break;
			}
		}
	}// fin calculaElConjuntoDelPunto
}// fin Mandelbrot2D
