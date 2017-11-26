package transformadores;

import java.io.File;
import java.io.IOException;

/**
 * Implementa la generación de parámetros para 2 dimensiones
 * 
 * @author PABLO ARIENZA CARRERA
 * @version 02.11.2017
 */
public abstract class Transformador2D extends Transformador {

	/**
	 * Realiza la transformación del archivo.
	 * 
	 * @param archivoOrigen
	 *            el archivo a transformar
	 * @param destino
	 *            la carpeta en la que se almacenará la transformación
	 * @param nombre
	 *            el nombre del archivo transformado
	 * @throws IOException
	 */
	protected abstract void transformar(File archivoOrigen, File destino, String nombre) throws IOException;

	@Override
	protected int[][] setParametros(String sha) {
		int[][] shaCortado = new int[6][2];
		int multiplicador = Character.getNumericValue(sha.charAt(63)) + 10;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 14; j++) {
				shaCortado[i * 14][j / 7] += Character.getNumericValue(sha.charAt(j));
			}
		}
		for (int i = 42; i < 63; i++) {
			shaCortado[(i - 21 / 7)][0] = Character.getNumericValue(sha.charAt(i));
		}
		for (int i = 1; i < 3; i++) {
			for (int j = 0; j < 2; j++) {
				if (shaCortado[i][j] > shaCortado[0][j])
					shaCortado[i][j] /= 2;
			}
		}
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 2; j++) {
				shaCortado[i][j] *= multiplicador;
			}
		}
		return shaCortado;
	}// fin setParametros
}// fin Transformador2D
