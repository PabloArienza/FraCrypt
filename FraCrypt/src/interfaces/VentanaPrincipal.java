package interfaces;

import java.awt.EventQueue;
import java.awt.Rectangle;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class VentanaPrincipal {
	
	private JFileChooser fCOrigen;
	private JFileChooser fCDestino;
	
	private String[] extensiones = {".f2Do", ".f2Dm", ".f2Dc", ".fNDm", ".xml"};

	private JFrame frmFracrypt;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPrincipal window = new VentanaPrincipal();
					window.frmFracrypt.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public VentanaPrincipal() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmFracrypt = new JFrame();
		frmFracrypt.setResizable(false);
		frmFracrypt.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentMoved(ComponentEvent e) {
				frmFracrypt.setLocationRelativeTo(null);
			}
		});
		frmFracrypt.setTitle("FraCrypt 1.0");
		
		frmFracrypt.setBounds(100, 100, 600, 440);
		frmFracrypt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmFracrypt.setLocationRelativeTo(null);
		frmFracrypt.getContentPane().setLayout(null);
		
		JPanel pnlOrigen = new JPanel();
		pnlOrigen.setBounds(0, 0, 600, 415);
		fCOrigen = new JFileChooser();
		fCOrigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals(javax.swing.JFileChooser.APPROVE_SELECTION)) {
//					System.out.println(fCOrigen.getSelectedFile().getName());
					boolean encriptar = discriminarArchivo(fCOrigen.getSelectedFile().getName());
					if(!encriptar) {
						Object[] options = {"Desencriptar", "Reencriptar", "Cancelar"};
						int n = JOptionPane.showOptionDialog(frmFracrypt, "¿Qué desea hacer con el archivo?", "Detectado archivo encriptado", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[2]);
						switch(n) {
							case 0:
								desencriptar(fCOrigen.getSelectedFile());
								break;
							case 1:
								encriptar(fCOrigen.getSelectedFile());
								break;
						}
					}else {
						encriptar(fCOrigen.getSelectedFile());
					}
				}else {
					System.exit(0);
				}
			}
		});
		fCOrigen.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Seleccione un archivo con el que operar", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		pnlOrigen.add(fCOrigen);
		frmFracrypt.getContentPane().add(pnlOrigen);
	}
	
	private boolean discriminarArchivo(String nombre) {
		
		boolean encriptar = true;
		for(String s : extensiones) {
			if(nombre.endsWith(s)) {
				encriptar = false;
			}
		}
		return encriptar;
	}
	
	private void encriptar(File archivoOrigen) {
		JFileChooser fCDestino = new JFileChooser();
		fCDestino.setDialogTitle("Seleccione una carpeta donde guardar.");
		fCDestino.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fCDestino.setCurrentDirectory(archivoOrigen.getParentFile());
		int seleccion = fCDestino.showOpenDialog(null);	
		if(seleccion == JFileChooser.APPROVE_OPTION) {
			seleccionarTipoDeEncriptacion(archivoOrigen, fCDestino.getSelectedFile());
		}
	}
	
	private void seleccionarTipoDeEncriptacion(File archivoOrigen, File Destino) {
		System.out.println("Encriptando");
	//	InterfazEncriptacion IE = new InterfazEncriptacion(archivoOrigen, Destino);
		IntEnc ie = new IntEnc(archivoOrigen, Destino);
	}

	private void desencriptar(File archivoOrigen) {
		System.out.println("Desencriptando");
	}
}
