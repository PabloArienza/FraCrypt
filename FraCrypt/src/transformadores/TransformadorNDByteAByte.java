package transformadores;

import java.io.File;
import java.io.IOException;

/**
 * Crea los objetos transformadores con fractales de n dimensiones byte a byte
 * 
 * @author PABLO ARIENZA CARRERA
 * @version 14.12.2017
 *
 */
public class TransformadorNDByteAByte extends Transformador2D implements ByteAByte {

	/**
	 * Constructor de la clase
	 * 
	 * @see Transformador.java
	 * @see TransformadorND.java
	 * @see ByteAByte.java
	 * 
	 * @param password
	 *            la contraseña
	 * @param archivoOrigen
	 *            el archivo a transformar
	 * @param destino
	 *            la carpeta destino
	 * @param tipoDeFractal
	 *            <ul>
	 *            <li>0 FractalNDimensionalSimple</li>
	 *            </ul>
	 * @throws IOException
	 */
	public TransformadorNDByteAByte(String password, File archivoOrigen, File destino, int tipoDeFractal)
			throws IOException {
		super(password, archivoOrigen, destino, tipoDeFractal);
	}// fin del constructor

	@Override
	protected void transformar(File archivoOrigen, File destino, String nombre) throws IOException {
		transformarByteAByte(archivoOrigen, destino, nombre, parametros, fractal, encriptando);
	}// fin transformar
}// fin TransformadorNDByteAByte
