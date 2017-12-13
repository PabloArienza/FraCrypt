package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;

import relojes.Cronometro;

public class TestCronometro {

	@Test(expected = IllegalStateException.class)
	public void validarIniciarCronometro() throws IllegalStateException{
		Cronometro cr = new Cronometro();
		cr.iniciar();
		cr.iniciar();
	}
	
	@Test(expected = IllegalStateException.class)
	public void validarPararCronometro() throws IllegalStateException{
		Cronometro cr = new Cronometro();
		//cr.iniciar();
		//cr.parar();
		cr.parar();
	}

}
