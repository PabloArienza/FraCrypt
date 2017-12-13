package transformadores;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import fractal.Fractal;
import fractal.Punto;

/**
 * Interfaz que prepara el procedimiento de transformaciÛn de los ficheros byte
 * a byte para los objetos transformadores.
 * 
 * @author PABLO ARIENZA CARRERA
 * @version 14.12.2017
 *
 */
public interface ByteAByte {

	/**
	 * Transforma el fichero byte a byte
	 * 
	 * @param archivoOrigen
	 *            el archivo a transformar
	 * @param destino
	 *            la carpeta en la que se almacenar√° la transformaci√≥n
	 * @param nombre
	 *            el nombre del archivo transformado
	 * 
	 * @param parametros
	 *            los par·metros para generar el fractal
	 * @param fractal
	 *            el conjunto de puntos de partida para transformar los datos
	 * @param encriptando
	 *            <ul>
	 *            <li>true encriptando</li>
	 *            <li>false desencriptando</li>
	 *            </ul>
	 * @throws IOException
	 */
	public default void transformarByteAByte(File archivoOrigen, File destino, String nombre, int[][] parametros,
			Fractal fractal, boolean encriptando) throws IOException {
		FileInputStream fis = null;
		FileOutputStream fos = null;

		try {
			fis = new FileInputStream(archivoOrigen);
			fos = new FileOutputStream(destino.getPath() + "\\" + nombre);
			fractal.setPuntoDeInicio(parametros[2][0], parametros[2][1]);
			Punto punto = fractal.leePunto();
			int lectura;
			while ((lectura = fis.read()) != -1) {
				int operadorXOR = 0;
				for (int p = 0; p < punto.getCoordenadas().length; p++) {
					operadorXOR += punto.getCoordenadas()[p];
				}
				int transformado = lectura ^ (operadorXOR) % 256;
				int avance = 0;
				if (encriptando) {
					avance = Math.abs(lectura);
				} else {
					avance = Math.abs(transformado);
				}
				fractal.mutarElPunto(avance);
				fractal.pasaAlSiguienteConjunto(avance);
				punto = fractal.leePunto();
				fos.write(transformado);
			}
		} finally {
			if (fis != null) {
				fis.close();
			}
			if (fos != null) {
				fos.close();
			}
		}
	}// fin transformarByteAByte
}// fin ByteAByte
