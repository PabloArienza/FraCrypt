package relojes;

import java.util.concurrent.TimeUnit;

/**
 * Calcula el tiempo de trabajo desde que se inicia hasta que se para.
 * 
 * @author PABLO ARIENZA CARRERA
 * @version 27.11.2017
 *
 */
public class Cronometro {

	private long inicio = 0L;
	private long fin = 0L;
	boolean corriendo = false;

	/**
	 * Almacena el momento en el que se inicia el cronómetro.
	 */
	public void start() {
		if (corriendo) {
			throw new IllegalStateException("No se puede empezar el reloj porque ya ha empezado.");
		}
		inicio = System.currentTimeMillis();
		corriendo = true;
	}// fin start

	/**
	 * Almacena el momento en el que se para el cronómetro.
	 */
	public void stop() {
		if (!corriendo) {
			throw new IllegalStateException("No se puede parar el reloj porque no ha empezado.");
		}
		fin = System.currentTimeMillis();
	}// fin stop

	/**
	 * reinicia el cronómetro.
	 */
	public void reset() {
		inicio = fin = 0L;
		corriendo = false;
	}// fin reset

	@Override
	public String toString() {
		String resultado = formateaElTiempo(fin - inicio);
		return "Tiempo de trabajo: " + resultado;

	}// fin toString

	/**
	 * Compone el tiempo medido en milisegundos en lenguaje natural.
	 * @param tiempo medido en milisegundos
	 * @return el tiempo en lenguaje natural
	 */
	private String formateaElTiempo(long tiempo) {
		String salida = "";
		long horas = TimeUnit.MILLISECONDS.toHours(tiempo);
		long minutos = TimeUnit.MILLISECONDS.toMinutes(tiempo) % TimeUnit.HOURS.toMinutes(1);
		long segundos = TimeUnit.MILLISECONDS.toSeconds(tiempo) % TimeUnit.MINUTES.toSeconds(1);
		long milisegundos = tiempo % TimeUnit.SECONDS.toMillis(1);
		if (horas > 0) {
			salida += TimeUnit.MILLISECONDS.toHours(tiempo) + " hora";
			if (horas > 1)
				salida += "s";
		}
		if (minutos > 0) {
			if (!salida.equals(""))
				salida += ", ";
			salida += TimeUnit.MILLISECONDS.toMinutes(tiempo) % TimeUnit.HOURS.toMinutes(1) + " minuto";
			if (minutos > 1)
				salida += "s";
		}
		if (segundos > 0) {
			if (!salida.equals(""))
				salida += ", ";
			salida += TimeUnit.MILLISECONDS.toSeconds(tiempo) % TimeUnit.MINUTES.toSeconds(1) + " segundo";
			if (segundos > 1)
				salida += "s";
		}
		if (milisegundos > 0) {
			if (!salida.equals(""))
				salida += ", ";
			salida += tiempo % TimeUnit.SECONDS.toMillis(1) + " milisegundo";
			if (milisegundos > 1)
				salida += "s";
		}
		int posicionUltimaComa = salida.lastIndexOf(",");
		if (posicionUltimaComa > 0)
			salida = salida.substring(0, posicionUltimaComa) + " y" + salida.substring(posicionUltimaComa + 1);
		return salida;
	}// fin formateaElTiempo
}// fin Cronometro
