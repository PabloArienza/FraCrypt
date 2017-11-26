package interfaces;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.FlowLayout;
import javax.swing.border.TitledBorder;

import transformadores.Transformador;
import transformadores.Transformador2DEnBloques;

import javax.swing.JRadioButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

public class InterfazEncriptacion extends JFrame {

	JPanel pnlInfo = new JPanel();
	JPanel pnlDimensiones = new JPanel();
	JPanel pnlOpciones = new JPanel();
	JPanel pnlProcedimiento = new JPanel();
	JPanel pnlBloques = new JPanel();
	JPanel pnlBotones = new JPanel();
	JPanel pnlOtros = new JPanel();

	JLabel lblOrigen = new JLabel("Origen: ");
	JLabel lblDestino = new JLabel("Destino: ");
	JLabel lblTamBloques = new JLabel("Tamaño de los bloques:");

	JButton btnCancelar = new JButton("Cancelar");
	JButton btnEncriptar = new JButton("Encriptar");

	ButtonGroup grupoDimensiones = new ButtonGroup();
	JRadioButton rd2Dimensiones = new JRadioButton("2");
	JRadioButton rdNDimensiones = new JRadioButton("Aleatorio");

	ButtonGroup grupoProcedimiento = new ButtonGroup();
	JRadioButton rdBloquesFijos = new JRadioButton("");
	JRadioButton rdBloquesAleatorios = new JRadioButton("Aleatorios");
	JRadioButton rdByteAByte = new JRadioButton("Byte a byte");
	JRadioButton rdStreaming = new JRadioButton("Streaming");

	JTextField txtBloquesFijos = new JTextField();

	public InterfazEncriptacion(File archivoOrigen, File destino) {

		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().add(pnlInfo, BorderLayout.NORTH);
		pnlInfo.setLayout(new GridLayout(2, 1));
		String textoOrigen = formateaNombreDeArchivo("Origen: ", archivoOrigen.getPath());
		lblOrigen.setText(textoOrigen);
		pnlInfo.add(lblOrigen);
		String textoDestino = formateaNombreDeArchivo("Destino: ", destino.getPath());
		lblDestino.setText(textoDestino);
		pnlInfo.add(lblDestino);
		int alturaVentana = 321 + lblOrigen.getHeight() + lblDestino.getHeight();
		setBounds(100, 100, 380, alturaVentana);
		setLocationRelativeTo(null);
		getContentPane().add(pnlBotones, BorderLayout.SOUTH);
		pnlBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		pnlBotones.add(btnCancelar);
		btnEncriptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rd2Dimensiones.setActionCommand("0");
				rdNDimensiones.setActionCommand("1");
				int dimensiones = Integer.parseInt(grupoDimensiones.getSelection().getActionCommand());

				rdBloquesFijos.setActionCommand("0");
				rdBloquesAleatorios.setActionCommand("1");
				rdByteAByte.setActionCommand("2");
				rdStreaming.setActionCommand("3");

				int procedimiento = Integer.parseInt(grupoProcedimiento.getSelection().getActionCommand());
				setAlwaysOnTop(false);
				if (procedimiento == 0 && txtBloquesFijos.getText().equals("")) {

					JOptionPane.showMessageDialog(getParent(), "Introduzca el tamaño de los bloques en bytes.",
							"Inane error", JOptionPane.ERROR_MESSAGE);
					setAlwaysOnTop(true);
				} else {
					String password = JOptionPane.showInputDialog("Contraseña para encriptar:", "");
					if (password.equals("")) {
						JOptionPane.showMessageDialog(getParent(), "La contraseña debe tener al menos 1 caracter.",
								"Inane error", JOptionPane.ERROR_MESSAGE);
						setAlwaysOnTop(true);
					} else {
						encriptar(archivoOrigen, destino, password, dimensiones, procedimiento,
								Integer.parseInt(txtBloquesFijos.getText()));
						System.out.println(password);
					}
				}
			}

			private void encriptar(File archivoOrigen, File destino, String password, int dimensiones,
					int procedimiento, int tambloques) {
				if (dimensiones == 0) {
					Object[] opciones = { "Mandelbrot", "Mandelbrot modificado", "Coseno" };
					String s = (String) JOptionPane.showInputDialog(getParent(),
							"Hay varios fractales para 2 dimensiones\n" + "¿Qué fractal desea generar?",
							"Customized Dialog", JOptionPane.PLAIN_MESSAGE, null, opciones, "Mandelbrot");
					int tipoDeFractal = 0;
					if(s.equals(opciones[0])) {
						tipoDeFractal = 0;
					}else if(s.equals(opciones[1])) {
						tipoDeFractal = 1;
					}else if(s.equals(opciones[2])) {
						tipoDeFractal = 2;
					}
					try {
						Transformador t = new Transformador2DEnBloques(password, archivoOrigen, destino, tambloques, tipoDeFractal);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else if(dimensiones == 1) {
					
				}

			}

		});
		pnlBotones.add(btnEncriptar);
		getContentPane().add(pnlOpciones, BorderLayout.CENTER);
		pnlOpciones.setLayout(new GridLayout(2, 1));
		pnlDimensiones.setBorder(new TitledBorder(null, "<html><body><b>Dimensiones</b></body></html>",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlOpciones.add(pnlDimensiones);
		pnlDimensiones.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		rd2Dimensiones.setSelected(true);
		pnlDimensiones.add(rd2Dimensiones);
		pnlDimensiones.add(rdNDimensiones);
		grupoDimensiones.add(rd2Dimensiones);
		grupoDimensiones.add(rdNDimensiones);

		pnlProcedimiento.setBorder(new TitledBorder(null, "<html><body><b>Procedimiento</b></body></html>",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlOpciones.add(pnlProcedimiento);
		pnlProcedimiento.setLayout(new GridLayout(1, 2));
		pnlBloques.setBorder(new TitledBorder(null, "Por bloques", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlProcedimiento.add(pnlBloques);
		pnlBloques.setLayout(null);
		lblTamBloques.setBounds(11, 16, 150, 16);
		pnlBloques.add(lblTamBloques);
		rdBloquesFijos.setSelected(true);
		rdBloquesFijos.setBounds(11, 36, 28, 23);
		pnlBloques.add(rdBloquesFijos);
		txtBloquesFijos.setBounds(37, 36, 112, 26);
		pnlBloques.add(txtBloquesFijos);
		txtBloquesFijos.setColumns(10);

		rdBloquesAleatorios.setBounds(11, 62, 112, 22);
		pnlBloques.add(rdBloquesAleatorios);
		pnlProcedimiento.add(pnlOtros);
		pnlOtros.setLayout(new GridLayout(0, 1, 0, 0));
		pnlOtros.add(rdByteAByte);
		pnlOtros.add(rdStreaming);
		grupoProcedimiento.add(rdBloquesFijos);
		grupoProcedimiento.add(rdBloquesAleatorios);
		grupoProcedimiento.add(rdByteAByte);
		grupoProcedimiento.add(rdStreaming);
		setVisible(true);
	}

	private String formateaNombreDeArchivo(String preTexto, String nombreDeArchivo) {
		String textoFormateado = "<html><body><b>" + preTexto + "</b>";
		int retornoCarro = 0;
		for (int i = 0; i < nombreDeArchivo.length(); i++) {
			textoFormateado += nombreDeArchivo.charAt(i);
			retornoCarro++;
			if (retornoCarro == 50) {
				textoFormateado += "<br>";
				for (int j = 0; j < preTexto.length(); j++) {
					textoFormateado += " ";
				}
				retornoCarro = 0;
			}
		}
		textoFormateado += "</body></html>";
		return textoFormateado;
	}

}
