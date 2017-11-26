package fractalFinal;

import java.util.ArrayList;

import fractal.*;

/**
 * Genera un fractal de Mandelbrot con la función f(z) = cos(z) + 1/c.
 * 
 * @author PABLO ARIENZA CARRERA
 * @version 24.08.2017
 */
public class Mandelbrot2DCoseno extends Fractal {

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
	public Mandelbrot2DCoseno(int[] dimensiones, int[] coordenadasCentro, int iteraciones, int limite, int escala) {
		this.fractal = new ArrayList<ConjuntoDePuntos>();
		this.ultimoLeido = 0;
		calculaLosConjuntos(dimensiones, coordenadasCentro, iteraciones, limite, escala);
	}// fin del constructor

	@Override
	public void calculaElConjuntoDelPunto(int[] coordenadas, int[] coordenadasCentro, int iteraciones, int limite,
			int escala) {
		float xC = ((float) coordenadas[0] - (float) coordenadasCentro[0]) / (float) escala;
		float yC = ((float) coordenadas[1] - (float) coordenadasCentro[1]) / (float) escala;
		float zX = 0;
		float zY = 0;
		// para el punto c = a + b*i
		// f(z) = cos(z) + (1/c)
		// cos(z) = (cos(a) * (e^b+e^-b) / 2) - (sen(a) * (e^b-e^-b)/2)i
		// 1/c = a / (a^2+b^2) - b/(a^2+b^2)i
		// f(z) = ((cos(a) * (e^b+e^-b) / 2) + a / (a^2+b^2)) - ((sen(a) * (e^b-e^-b)/2)
		// + b/(a^2+b^2))i
		for (int i = 0; i < iteraciones; i++) {
			iteracionesDePuntos++; // Benchmarking
			float eALaB = (float) Math.pow(Math.E, zY);
			float a2MasB2 = (float) (Math.pow(zX, 2) + Math.pow(zY, 2));
			float nX = (float) (((Math.cos(zX) * (eALaB + (1 / eALaB))) / 2) + (zX / a2MasB2));
			float nY = (float) (((Math.sin(zX) * (eALaB - (1 / eALaB))) / 2) + (zY / a2MasB2));
			zX = nX;
			zY = nY;
			// La función de escape es x^2 + y^2 < limite
			if (zX * zX + zY * zY > (float) limite) {
				addPuntoASuConjunto(new Punto(coordenadas), i);
				break;
			}
		}
	}// fin calculaElConjuntoDelPunto
}// fin Mandelbrot2DCoseno
