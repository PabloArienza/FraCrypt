package fractal;

/**
 * Construye un punto de mutacion en función del valor del avance
 * 
 * @Author PABLO ARIENZA CARRERA
 * @version 11.12.2017
 */
public class PuntoDeMutacionPorAvance extends Punto{

	public PuntoDeMutacionPorAvance(int[] coordenadas) {
		super(coordenadas);
	}// fin constructor

	@Override
	public void mutarElPunto(int avance) {
		boolean signo = true;
		for (int i = 0; i < coordenadas.length; i++) {
			if (signo) {
				coordenadas[i] += avance;
			} else {
				coordenadas[i] -= avance;
			}
			signo = !signo;
		}
	}// fin mutarElPunto	
}// fin PuntoDeMutacionPorAvance
