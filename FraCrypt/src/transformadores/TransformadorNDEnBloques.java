package transformadores;

import java.io.File;
import java.io.IOException;

/**
 * Transforma un fichero en bloques de bytes
 * 
 * @author PABLO ARIENZA CARRERA
 * @version 02.11.2017
 */
public class TransformadorNDEnBloques extends TransformadorND implements EnBloques {

	private int tamBloques;

	/**
	 * Constructor de la clase
	 * 
	 * @see Transformador.java
	 * @see TransformadorND.java
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
	 *            <li>0 FractalNDimenwsionalSimple</li>
	 *            </ul>
	 * @throws IOException
	 */
	public TransformadorNDEnBloques(String password, File archivoOrigen, File destino, int tamBloques,
			int tipoDeFractal) throws IOException {
		super(password, archivoOrigen, destino, tipoDeFractal);
		this.tamBloques = tamBloques;
	}

	@Override
	protected void transformar(File archivoOrigen, File destino, String nombre) throws IOException {
		transformarEnBloques(archivoOrigen, destino, nombre, tamBloques, parametros, fractal, encriptando);
	}// fin transformar
}// fin TransformadorNDEnBloques
