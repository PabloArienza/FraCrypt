package transformadores;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import fractal.*;
import fractalFinal.*;
import relojes.Cronometro;

/**
 * Transforma un fichero en bloques de bytes
 * 
 * @author PABLO ARIENZA CARRERA
 * @version 02.11.2017
 */
public class TransformadorNDEnBloques extends TransformadorND {

	/**
	 * Constructor de la clase
	 * 
	 * @param password
	 *            la contraseña
	 * @param archivoOrigen
	 *            el archivo a transformar
	 * @param destino
	 *            la carpeta destino
	 * @param tamBloques
	 *            el tamaño de los buffers
	 * @param tipoDeFractal
	 *            <ul>
	 *            <li>0 Transformación simple</li>
	 *            </ul>
	 * @throws IOException
	 */
	public TransformadorNDEnBloques(String password, File archivoOrigen, File destino, int tamBloques,
			int tipoDeFractal) throws IOException {
		this.byteLeido = 0;
		this.tamBloques = tamBloques;
		sha = creaSha256(password);
		parametros = setParametros(sha);

		String nombre = archivoOrigen.getName();
		String extension = "";
		encriptando = true;
		switch (tipoDeFractal) {
		case 0:
			this.fractal = new FractalNDimensionalSimple(parametros[0], parametros[1], parametros[3][0],
					parametros[4][0], parametros[5][0]);
			extension = ".fNDs";
			break;
		}
		if (nombre.endsWith(extension)) {
			nombre = nombre.substring(0, nombre.length() - 4);
			encriptando = false;
		} else {
			nombre += extension;
		}
		Cronometro cr = new Cronometro(); // Benchmarking
		cr.start(); // Benchmarking
		transformar(archivoOrigen, destino, nombre);
		cr.stop(); // Benchmarking
		System.out.println("Tiempo para encriptar el archivo: " + cr.toString()); // Benchmarking
		cr.reset(); // Benchmarking
	}// fin del constructor

	@Override
	protected void transformar(File archivoOrigen, File destino, String nombre) throws IOException {
		FileInputStream fis = new FileInputStream(archivoOrigen);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		if (tamBloques == 0)
			tamBloques = setTamBloquesAleatorio();
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
}// fin TransformadorNDEnBloques
