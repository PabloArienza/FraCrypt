package transformadores;

import java.io.File;
import java.io.IOException;

/**
 * Transforma un fichero en bloques de bytes
 * 
 * @author PABLO ARIENZA CARRERA
 * @version 02.11.2017
 */
public class Transformador2DEnBloques extends Transformador2D implements EnBloques {

	private int tamBloques;

	/**
	 * Constructor de la clase
	 * 
	 * @see Transformador.java
	 * @see Transformador2D.java
	 * @see EnBloques.java
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
	public Transformador2DEnBloques(String password, File archivoOrigen, File destino, int tamBloques,
			int tipoDeFractal) throws IOException {
		super(password, archivoOrigen, destino, tipoDeFractal);
		this.tamBloques = tamBloques;
	}// fin del constructor

	@Override
	protected void transformar(File archivoOrigen, File destino, String nombre) throws IOException {
		transformarEnBloques(archivoOrigen, destino, nombre, tamBloques, parametros, fractal, encriptando);
	}// fin transformar
}// fin Transformador2DEnBloques
