package transformadores;

import java.io.File;
import java.io.IOException;

/**
 * Implementa la generación de parámetros para N dimensiones
 * 
 * @author PABLO ARIENZA CARRERA
 * @version 12.11.2017
 */
public abstract class TransformadorND extends Transformador {

	protected int caracterLeido;

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
		// Se garantiza que el número de dimensiones no sea 0
		int numeroDeDimensiones = Character.getNumericValue(sha.charAt(0)) + 2;
		int[][] shaCortado = new int[6][numeroDeDimensiones];

		// Se almacena el tamaño de cada dimensión
		for (int i = 0; i < numeroDeDimensiones; i++) {
			shaCortado[0][i] = 10 * creaParametro(0) / numeroDeDimensiones;
		}

		return null;
	}// fin setParametros

	/**
	 * Procesa un número de caracteres en función del parámetro a calcular.
	 * 
	 * @param claseOperacion
	 *            el tipo de parámetro a calcular
	 * @return el resultado del procesamiento de los caracteres
	 */
	private int creaParametro(int claseOperacion) {
		int parametro = 0;
		int numeroDeCaracteres = 0;
		switch (claseOperacion) {
		case 0:
			numeroDeCaracteres = 8;
			break;
		case 1:
			numeroDeCaracteres = 4;
			break;
		}
		for (int i = 0; i < numeroDeCaracteres; i++) {
			parametro += leeSha();
		}
		return parametro;
	}// fin creaParametro

	/**
	 * Lee un caracter del hash y avanza el contador para la siguiente lectura
	 * 
	 * @return el valor entero del caracter
	 */
	private int leeSha() {
		caracterLeido++;
		if (caracterLeido == 63)
			caracterLeido = 0;
		return Character.getNumericValue(sha.charAt(caracterLeido));
	}// fin leeSha
}// fin TransformadorND
