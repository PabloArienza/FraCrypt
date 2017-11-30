package fractal;

import java.util.ArrayList;

import relojes.Cronometro;

/**
 * Genera las distintas listas de puntos necesarios para formar el fractal según
 * la función de escape seleccionada.
 * 
 * @author PABLO ARIENZA CARRERA
 * @version 24.08.2017
 */
public abstract class Fractal {

	protected ArrayList<ConjuntoDePuntos> fractal;
	protected int ultimoLeido;
	
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
	public Fractal(int[] dimensiones, int[] coordenadasCentro, int iteraciones, int limite, int escala) {
		this.fractal = new ArrayList<ConjuntoDePuntos>();
		this.ultimoLeido = 0;
		calculaLosConjuntos(dimensiones, coordenadasCentro, iteraciones, limite, escala);
	}// fin del constructor

	/*
	 * *************************************************************************
	 * Variables de benchmarking
	 */

	public int puntosTotales = 0;
	public int puntosAConjunto = 0;
	public int busquedasDeConjunto = 0;
	public int iteracionesDePuntos = 0;

	// **************************************************************************

	/**
	 * Calcula a iteración en la que el punto escapa del límite establecido.
	 * 
	 * @param coordenadas
	 *            las coordenadas del punto a iterar
	 * @param coordenadasCentro
	 *            el centro relativo del espacio n-dimensional
	 * @param iteraciones
	 *            el número máximo de veces que se iterará cada punto
	 * @param limite
	 *            el valor máximo de la ecuación de escape
	 * @param escala
	 *            el modificador de la ecuación de escape
	 */
	public abstract void calculaElConjuntoDelPunto(int[] coordenadas, int[] coordenadasCentro, int iteraciones,
			int limite, int escala);

	/**
	 * Añade un punto al conjunto al que pertence en función de su valor de escape.
	 * 
	 * @param punto
	 *            el punto a incluir en un conjunto
	 * @param iteracion
	 *            la iteración en la que el punto escapa a la función de escape
	 */
	public void addPuntoASuConjunto(Punto punto, int iteracion) {
		boolean anotado = false;
		for (ConjuntoDePuntos c : fractal) {
			busquedasDeConjunto++; // Benchmarking
			if (c.getiD() == iteracion) {
				c.addPunto(punto);
				puntosAConjunto++; // Benchmarking
				anotado = true;
				break;
			}
		}
		// Si no existe un conjunto con el valor de escape se crea y se añade el punto
		if (!anotado) {
			ConjuntoDePuntos nuevo = new ConjuntoDePuntos(iteracion);
			nuevo.addPunto(punto);
			fractal.add(nuevo);
			puntosAConjunto++; // Benchmarking
		}
	}// fin addPuntoASuConjunto

	/**
	 * Calcula a qué conjunto pertence cada punto del espacio n-dimensional.
	 * 
	 * @param dimensiones
	 *            el número de dimensiones y su tamaño
	 * @param coordenadasCentro
	 *            el centro relativo del espacio n-dimensional
	 * @param iteraciones
	 *            el número máximo de veces que se iterará cada punto
	 * @param limite
	 *            el valor máximo de la función de escape
	 * @param escala
	 *            el modificador de la función de escape
	 */
	public void calculaLosConjuntos(int[] dimensiones, int[] coordenadasCentro, int iteraciones, int limite,
			int escala) {
		
		Cronometro cr = new Cronometro(); // Benchmarking
		cr.iniciar(); // Benchmarking
		
		// Conjunto de multiplicadores necesarios para generar iterativamente todos los
		// puntos del espacio n-dimensional
		double[] multiplicadores = new double[dimensiones.length];
		for (int i = 0; i < dimensiones.length; i++)
			multiplicadores[i] = 1;
		for (int i = 0; i < dimensiones.length; i++) {
			for (int j = i + 1; j < dimensiones.length; j++)
				multiplicadores[i] *= dimensiones[j];
		}
		// El número de puntos total del espacio
		int numeroPuntos = 1;
		for (int d : dimensiones)
			numeroPuntos *= d;
		for (int i = 0; i < numeroPuntos; i++) {
			int[] coordenadas = new int[dimensiones.length];
			for (int j = 0; j < dimensiones.length; j++) {
				coordenadas[j] = (int) ((i / multiplicadores[j]) % dimensiones[j]);
			}
			puntosTotales++; // Benchmarking
			calculaElConjuntoDelPunto(coordenadas, coordenadasCentro, iteraciones, limite, escala);
		}
		cr.parar(); // Benchmarking
		System.out.println("Tiempo para generar el fractal: " + cr.toString()); // Benchmarking
		cr.reset(); // Benchmarking
	}// fin calculaLosConjuntos

	/**
	 * Devuelve el punto con el que transforma el dato.
	 * 
	 * @return el punto con el que transforma el dato
	 */
	public Punto leePunto() {
		return fractal.get(ultimoLeido).getPunto();
	}// fin leePunto

	/**
	 * Avanza el contador de conjuntos y el contador de puntos en el conjunto
	 * seleccionado.
	 * 
	 * @param avance
	 *            la cantidad de conjuntos que se avanza
	 */

	public void pasaAlSiguienteConjunto(int avance) {
		ultimoLeido = (ultimoLeido + avance) % fractal.size();
		fractal.get(ultimoLeido).pasaAlSiguientePunto();
	}// fin pasaAlSiguienteConjunto

	/**
	 * Traslada el factor de mutación al objeto correspondiente.
	 * 
	 * @param avance
	 *            el factor de mutación
	 */
	public void mutarElPunto(int avance) {
		fractal.get(ultimoLeido).mutarElPunto(avance);
	}// fin mutarElPunto

	/**
	 * Altera el orden de los conjuntos y los puntos del fractal en función del
	 * punto desde el que se inicia el algoritmo.
	 * 
	 * @param avanceConjuntos
	 *            el número de conjuntos que se pasan al final de la lista de
	 *            conjuntos
	 * @param avancePuntos
	 *            el número de puntos que se pasan al final del conjunto
	 */
	public void setPuntoDeInicio(int avanceConjuntos, int avancePuntos) {
		for (int i = 0; i < avanceConjuntos; i++) {
			ConjuntoDePuntos primero = fractal.get(0);
			fractal.remove(0);
			fractal.add(primero);
			fractal.get(0).setPuntoDeInicio(avancePuntos);
		}
	}// fin setPuntoDeInicio

	// ******************************************************************************
	// Presentación en pantalla. No forma parte del algoritmo.

	/**
	 * Para presentación en pantalla. Imprime el resumen de la generación del
	 * fractal
	 */
	public void imprimeDatosDelFractal() {
		int numeroDeConjuntos = fractal.size();
		int[][] tamConjuntos = new int[numeroDeConjuntos][2];
		int puntosDelFractal = 0;
		for (int i = 0; i < numeroDeConjuntos; i++) {
			tamConjuntos[i][0] = fractal.get(i).getiD();
			tamConjuntos[i][1] = fractal.get(i).getTamLista();
			puntosDelFractal += tamConjuntos[i][1];
		}
		int[] conjuntoMenor = tamConjuntos[0];
		int[] conjuntoMayor = tamConjuntos[0];
		for (int i = 1; i < numeroDeConjuntos; i++) {
			if (tamConjuntos[i][1] < conjuntoMenor[1])
				conjuntoMenor = tamConjuntos[i];
			if (tamConjuntos[i][1] > conjuntoMayor[1])
				conjuntoMayor = tamConjuntos[i];

		}

		System.out.println("******************************************************");
		System.out.println("Información del fractal\n");
		System.out.println("Número de conjuntos de puntos: " + numeroDeConjuntos);
		System.out.println("Número de puntos totales: " + puntosTotales);
		System.out.println("Número de puntos de interés: " + puntosDelFractal);
		System.out
				.println("Tamaño máximo en el conjunto " + conjuntoMayor[0] + " con " + conjuntoMayor[1] + " puntos.");
		System.out
				.println("Tamaño mínimo en el conjunto " + conjuntoMenor[0] + " con " + conjuntoMenor[1] + " puntos.");
		System.out.println(
				"Tamaño medio de los conjuntos: " + (double) (puntosDelFractal / numeroDeConjuntos) + " puntos.");
		System.out.println("Iteraciones de los puntos total: " + iteracionesDePuntos + ". Media por punto: "
				+ (double) (iteracionesDePuntos / puntosTotales));
		System.out.println("Búsquedas de conjunto para insertar punto: " + busquedasDeConjunto
				+ ". Media por punto insertado: " + (double) (busquedasDeConjunto / puntosDelFractal));
		for (ConjuntoDePuntos c : fractal)
			c.imprimeListaDePuntos();
	}// fin imprimeDatosDelFractal
		// ******************************************************************************
}// fin Fractal
