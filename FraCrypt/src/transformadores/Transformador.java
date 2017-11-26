package transformadores;

import java.security.MessageDigest;

import fractal.*;
import fractalFinal.*;

/**
 * Compone la estructura de los transformadores en diferentes formatos.
 * 
 * @author PABLO ARIENZA CARRERA
 * @version 28.08.2017
 */
public abstract class Transformador {
	protected int[][] parametros;
	protected String sha;
	protected Fractal fractal;
	protected int byteLeido;
	protected boolean encriptando;
	protected int tamBloques;

	/*
	 * *************************************************************************
	 * Variables de benchmarking
	 */

	public int calculosSha;

	// **************************************************************************

	/**
	 * Procesa el hash de la contraseña dividiéndolo en fragmentos con los que se
	 * forman los parámetros necesarios para crear el fractal.
	 * 
	 * @param sha
	 *            el hash de la contraseña
	 * @return los parámetros para generar el fractal<br>
	 *         <ul>
	 *         <li>parametros[0] Tamaño de las dimensiones</li>
	 *         <li>parametros[1] Coordenadas del centro</li>
	 *         <li>parametros[2] parámetros para establecer el punto de inicio</li>
	 *         <li>parametros[3][0] iteraciones</li>
	 *         <li>parametros[4][0] límite</li>
	 *         <li>parametros[5][0] Escala</li>
	 *         </ul>
	 */
	protected abstract int[][] setParametros(String sha);

	/**
	 * Genera el hash con el algoritmo SHA256
	 * 
	 * @param password
	 *            la contraseña
	 * @return el hash
	 */
	protected String creaSha256(String password) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(password.getBytes("UTF-8"));
			StringBuffer hexString = new StringBuffer();

			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if (hex.length() == 1) {
					hexString.append('0');
					calculosSha++; // benchmarking
				}
				hexString.append(hex);
				calculosSha++; // benchmarking
			}
			return hexString.toString();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}// fin creaSha256

	/**
	 * Devuelve los parámetros con los que se ha creado el fractal.
	 * 
	 * @return los parámetros con los que se ha creado el fractal
	 */
	public int[][] getParametros() {
		return parametros;
	}// fin getParametros

	/**
	 * Devuelve el hash con el que se han creado los parámetros del fractal.
	 * 
	 * @return el hash de la contraseña
	 */
	public String getSha() {
		return sha;
	}// fin getSha

	/**
	 * Devuelve el conjunto de puntos seleccionados del fractal separados en
	 * conjuntos en funciñon de su valor de escape.
	 * 
	 * @return el conjunto de puntos seleccionados del fractal separados en
	 *         conjuntos en funciñon de su valor de escape
	 */
	public Fractal getFractal() {
		return fractal;
	}// fin getFractal

	/**
	 * Calcula el tamaño de los bloques cuando el usuario no especifica un tamaño
	 * concreto.
	 * 
	 * @return el tamaño de los bloques
	 */
	protected int setTamBloquesAleatorio() {
		int tamBloquesAleatorio = 0;
		int i = 0;
		while (i < parametros[0].length) {
			tamBloquesAleatorio += parametros[0][i];
		}
		tamBloquesAleatorio /= i;
		return tamBloquesAleatorio;
	}// fin setTamBloquesAleatorio

}// fin Transformador
