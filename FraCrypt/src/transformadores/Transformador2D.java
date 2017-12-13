package transformadores;

import java.io.File;
import java.io.IOException;

import fractalFinal.Mandelbrot2D;
import fractalFinal.Mandelbrot2DCoseno;
import fractalFinal.Mandelbrot2DModificado;
import relojes.Cronometro;

/**
 * Implementa la generación de parámetros para 2 dimensiones
 * 
 * @author PABLO ARIENZA CARRERA
 * @version 02.11.2017
 */
public abstract class Transformador2D extends Transformador {

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
	 *            <li>0 Mandelbrot original</li>
	 *            <li>1 Mandelbrot modificado</li>
	 *            <li>2 Mandelbrot coseno</li>
	 *            </ul>
	 * @throws IOException
	 */
	public Transformador2D(String password, File archivoOrigen, File destino, int tamBloques, int tipoDeFractal)
			throws IOException {
		this.byteLeido = 0;
		this.tamBloques = tamBloques;
		sha = creaSha256(password);
		parametros = setParametros(sha);
		String nombre = archivoOrigen.getName();
		encriptando = true;
		String extension = seleccionaFractal(tipoDeFractal);
		if (nombre.endsWith(extension)) {
			nombre = nombre.substring(0, nombre.length() - 4);
			encriptando = false;
		} else {
			nombre += extension;
		}
		Cronometro cr = new Cronometro(); // Benchmarking
		cr.iniciar(); // Benchmarking
		transformar(archivoOrigen, destino, nombre);
		cr.parar(); // Benchmarking
		System.out.println("Tiempo para encriptar el archivo: " + cr.toString()); // Benchmarking
		cr.reset(); // Benchmarking
	}// fin del constructor

	@Override
	protected String seleccionaFractal(int tipoDeFractal) {
		String[] extensiones = { ".f2Do", ".f2Dm", "f2Dc" };
		switch (tipoDeFractal) {
		case 0:
			this.fractal = new Mandelbrot2D(parametros[0], parametros[1], parametros[3][0], parametros[4][0],
					parametros[5][0]);
			break;
		case 1:
			this.fractal = new Mandelbrot2DModificado(parametros[0], parametros[1], parametros[3][0], parametros[4][0],
					parametros[5][0]);
			break;
		case 2:
			this.fractal = new Mandelbrot2DCoseno(parametros[0], parametros[1], parametros[3][0], parametros[4][0],
					parametros[5][0]);
			break;
		}
		return extensiones[tipoDeFractal];
	}

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
