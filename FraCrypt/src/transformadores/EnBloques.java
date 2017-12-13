package transformadores;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import fractal.Fractal;
import fractal.Punto;

/**
 * Interfaz que prepara el procedimiento de transformaci�n de los ficheros con
 * buffers para los objetos transformadores.
 * 
 * @author PABLO ARIENZA CARRERA
 * @version 14.12.2017
 *
 */
public interface EnBloques {

	/**
	 * Transforma el fichero mediante buffers
	 * 
	 * @param archivoOrigen
	 *            el archivo a transformar
	 * @param destino
	 *            la carpeta en la que se almacenará la transformación
	 * @param nombre
	 *            el nombre del archivo transformado
	 * @param tamBloques
	 *            el tama�o de los buffers
	 * @param parametros
	 *            los par�metros para generar el fractal
	 * @param fractal
	 *            el conjunto de puntos de partida para transformar los datos
	 * @param encriptando
	 *            <ul>
	 *            <li>true encriptando</li>
	 *            <li>false desencriptando</li>
	 *            </ul>
	 * @throws IOException
	 */
	public default void transformarEnBloques(File archivoOrigen, File destino, String nombre, int tamBloques,
			int[][] parametros, Fractal fractal, boolean encriptando) throws IOException {
		FileInputStream fis = new FileInputStream(archivoOrigen);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		if (tamBloques == 0)
			tamBloques = setTamBloquesAleatorio(parametros);
		// Los datos es extraen en bloques a un buffer
		byte[] buf = new byte[tamBloques];
		// Se altera el punto de inicio del algoritmo
		fractal.setPuntoDeInicio(parametros[2][0], parametros[2][1]);
		Punto punto = fractal.leePunto();
		for (int lectura; (lectura = fis.read(buf)) != -1;) {
			// Las transformaciones se almacenan en un buffer secundario
			byte[] buf2 = new byte[buf.length];
			for (int i = 0; i < buf.length; i++) {
				int operadorXOR = 0;
				for (int p = 0; p < punto.getCoordenadas().length; p++) {
					operadorXOR += punto.getCoordenadas()[p];
				}
				buf2[i] = (byte) (buf[i] ^ (operadorXOR) % 256);
				int avance = 0;
				if (encriptando) {
					avance = Math.abs(buf[i]);
				} else {
					avance = Math.abs(buf2[i]);
				}
				fractal.mutarElPunto(avance);
				fractal.pasaAlSiguienteConjunto(avance);
				punto = fractal.leePunto();
			}
			bos.write(buf2, 0, lectura);
		}
		fis.close();
		byte[] bytes = bos.toByteArray();
		File c = new File(destino.getPath());
		c.mkdirs();
		File archivoDestino = new File(destino.getPath() + "\\" + nombre);
		FileOutputStream fos = new FileOutputStream(archivoDestino);
		fos.write(bytes);
		fos.flush();
		fos.close();
	}// fin transformarEnBloques

	/**
	 * Calcula el tamaño de los bloques cuando el usuario no especifica un tamaño
	 * concreto.
	 * 
	 * @return el tamaño de los bloques
	 */
	default int setTamBloquesAleatorio(int[][] parametros) {
		int tamBloquesAleatorio = 0;
		int i = 0;
		while (i < parametros[0].length) {
			tamBloquesAleatorio += parametros[0][i];
		}
		tamBloquesAleatorio /= i;
		return tamBloquesAleatorio;
	}// fin setTamBloquesAleatorio
}// fin EnBloques
