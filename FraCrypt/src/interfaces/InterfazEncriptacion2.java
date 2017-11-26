package interfaces;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JRadioButton;

public class InterfazEncriptacion2 extends JFrame{
	
	String[] fractales = {"Mandelbrot2D", "Mandelbrot2DModificado", "Mandelbrot2DCoseno", "FractalNdimensionalSimple"};
	


	public InterfazEncriptacion2() {
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		

		
		for(String s : fractales) {
			JRadioButton nuevo = new JRadioButton(s);
			nuevo.setName(s);
			panel.add(nuevo);
		}
	}

	
}