package transformadores;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import fractal.*;

/**
 * Transforma un fichero en bloques de bytes
 * 
 * @author PABLO ARIENZA CARRERA
 * @version 02.11.2017
 */
public class Transformador2DEnBloques extends Transformador2D {

	

	/**
	 * Constructor de la clase
	 * 
	 * @see Transformador2D
	 */
	public Transformador2DEnBloques(String password, File archivoOrigen, File destino, int tamBloques,
			int tipoDeFractal) throws IOException {
		super(password, archivoOrigen, destino, tamBloques, tipoDeFractal);
	}

	@Override
	protected void transformar(File archivoOrigen, File destino, String nombre) throws IOException {
		FileInputStream fis = new FileInputStream(archivoOrigen);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		if(tamBloques == 0) tamBloques = setTamBloquesAleatorio();
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
	}// fin transformar
}// fin Transformador2DEnBloques
