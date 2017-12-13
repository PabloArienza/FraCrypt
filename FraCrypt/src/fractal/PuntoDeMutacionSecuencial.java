package fractal;

/**
 * Construye un punto de mutacion secuencial
 * 
 * @Author PABLO ARIENZA CARRERA
 * @version 11.12.2017
 */
public class PuntoDeMutacionSecuencial extends Punto {

	/**
	 * Construcción de la clase
	 * 
	 * @see Punto.java
	 * 
	 * @param coordenadas
	 *            el arreglo de enteros que forman las coordenadas del punto.
	 */
	public PuntoDeMutacionSecuencial(int[] coordenadas) {
		super(coordenadas);
	}// fin constructor

	@Override
	public void mutarElPunto(int avance) {
		boolean signo = true;
		if (Math.abs(avance) < 127)
			signo = false;
		for (int i = 0; i < coordenadas.length; i++) {
			if (signo) {
				coordenadas[i] += 1;
				// Si llega a 2.147.483.647 al sumar pasa directamente a -2.147.483.648
			} else {
				coordenadas[i] -= 1;
				// Si llega a -2.147.483.648 al sumar pasa directamente a 2.147.483.647
			}
			signo = !signo;
		}
	}// fin mutarElPunto
}// fin PuntoDeMutacionSecuencial
