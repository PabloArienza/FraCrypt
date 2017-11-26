package interfaces;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.border.TitledBorder;

import transformadores.*;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class IntEnc extends JFrame{

	private String[] fractales = {"Mandelbrot2D", "Mandelbrot2DModificado", "Mandelbrot2DCoseno", "FractalNdimensionalSimple"};
	private String[] procedimientos = {"byteAByte", "enBloques"};
	private int fractalSeleccionado = -1;
	private int procedimientoSeleccionado = -1;
	ButtonGroup grupoFractales = new ButtonGroup();
	ButtonGroup grupoProcedimientos = new ButtonGroup();
	JLabel lblOrigen = new JLabel("Origen: ");
	JLabel lblDestino = new JLabel("Destino: ");

	public IntEnc(File archivoOrigen, File destino) {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentMoved(ComponentEvent e) {
				setLocationRelativeTo(null);
			}
		});
		setTitle("Seleccione las características de la encriptación");
		setResizable(false);	
		setBounds(100, 100, 450, 300);
		setAlwaysOnTop(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JPanel pnlSur = new JPanel();
		getContentPane().add(pnlSur, BorderLayout.SOUTH);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		pnlSur.add(btnCancelar);
		
		JButton btnEjecutar = new JButton("Encriptar");
		btnEjecutar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int fractalSeleccionado = Integer.parseInt(grupoFractales.getSelection().getActionCommand());
					int procedimientoSeleccionado = Integer.parseInt(grupoProcedimientos.getSelection().getActionCommand());
					System.out.println(fractalSeleccionado + " - " + procedimientoSeleccionado);
					setAlwaysOnTop(false);
					String password = "";
					while(password.equals("")) {
						password = JOptionPane.showInputDialog("Contraseña para encriptar:", "");
						if (password.equals("")) {
							JOptionPane.showMessageDialog(getParent(), "La contraseña debe tener al menos 1 caracter.",
									"Inane error", JOptionPane.ERROR_MESSAGE);
							
						} else {
							setAlwaysOnTop(true);
							//***************************************************
							System.out.println(password);
							
							encriptar(archivoOrigen, destino, password, fractalSeleccionado, procedimientoSeleccionado);
						}
					}
				}catch(NullPointerException ne) {
					setAlwaysOnTop(false);
					JOptionPane.showMessageDialog(null, "Seleccione un fractal y un procedimiento.");
					setAlwaysOnTop(true);
				}
			}

			private void encriptar(File archivoOrigen, File destino, String password, int fractalSeleccionado,
					int procedimientoSeleccionado) {
				switch(procedimientoSeleccionado) {
				case 0:
					
					break;
				case 1:
					String respuesta = JOptionPane.showInputDialog("Introduzca el tamaño de los bloques (vacío para aleatorio):", "");
					int tamBloques = Integer.parseInt(respuesta);
					if(tamBloques == 0) {
						
					}
					try {
						Transformador2DEnBloques t = new Transformador2DEnBloques(password, archivoOrigen, destino, tamBloques, fractalSeleccionado);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				}
				
			}
		});
		pnlSur.add(btnEjecutar);
		
		JPanel pnlCentro = new JPanel();
		getContentPane().add(pnlCentro, BorderLayout.CENTER);
		pnlCentro.setLayout(new BorderLayout(0, 0));
		
		JPanel pnlAbajo = new JPanel();
		pnlAbajo.setBorder(new TitledBorder(null, "Procedimiento", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlCentro.add(pnlAbajo, BorderLayout.SOUTH);
		
		JPanel pnlArriba = new JPanel();
		pnlArriba.setBorder(new TitledBorder(null, "Fractales disponibles", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlCentro.add(pnlArriba, BorderLayout.CENTER);

		
		addRadioButtons(fractales, pnlArriba, grupoFractales);
		pnlArriba.setLayout(new GridLayout(0, 3, 0, 0));
		addRadioButtons(procedimientos, pnlAbajo, grupoProcedimientos);
		pnlAbajo.setLayout(new GridLayout(0, 3, 0, 0));
		
		JPanel pnlInfo = new JPanel();
		getContentPane().add(pnlInfo, BorderLayout.NORTH);
		pnlInfo.setLayout(new GridLayout(2, 1));
		String textoOrigen = formateaNombreDeArchivo("Origen: ", archivoOrigen.getPath());
		lblOrigen.setText(textoOrigen);
		pnlInfo.add(lblOrigen);
		String textoDestino = formateaNombreDeArchivo("Destino: ", destino.getPath());
		lblDestino.setText(textoDestino);
		pnlInfo.add(lblDestino);
		
		pack();
		setVisible(true);
	}

	private void addRadioButtons(String[] nombres, JPanel panel, ButtonGroup grupo) {
		for(int i = 0; i < nombres.length; i++) {
			JRadioButton nuevo = new JRadioButton(nombres[i]);
			nuevo.setName(nombres[i]);
			nuevo.setActionCommand(Integer.toString(i));
			panel.add(nuevo);
			grupo.add(nuevo);
		}
	}
	
	private String formateaNombreDeArchivo(String preTexto, String nombreDeArchivo) {
		String textoFormateado = "<html><body><b>" + preTexto + "</b>";
		int retornoCarro = 0;
		for (int i = 0; i < nombreDeArchivo.length(); i++) {
			textoFormateado += nombreDeArchivo.charAt(i);
			retornoCarro++;
			if (retornoCarro == 70) {
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
