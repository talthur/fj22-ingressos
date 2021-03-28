package br.com.caelum.ingresso.validacao;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.ingresso.model.Filme;
import br.com.caelum.ingresso.model.Ingresso;
import br.com.caelum.ingresso.model.Lugar;
import br.com.caelum.ingresso.model.Sala;
import br.com.caelum.ingresso.model.Sessao;
import br.com.caelum.ingresso.model.TipoDeIngresso;

public class DescontoTest {
	
	
	private Sala sala;
	private Filme filme;
	private Sessao sessao;
	private Lugar lugar;
	
	@Before
	public void preparaTestes() {
		this.sala = new Sala("Eldorado - IMAX", new BigDecimal("20.5"));
		this.filme = new Filme("Rogue One", Duration.ofMinutes(120),
		"SCI-FI", new BigDecimal("12"));
		this.sessao = new Sessao(LocalTime.parse("10:00:00"), filme, sala);
		this.lugar = new Lugar("A",1);
	}
	
	@Test
	public void naoDeveConcederDescontoParaIngressoNormal() {
		
		Ingresso ingresso = new Ingresso(sessao, TipoDeIngresso.INTEIRO,this.lugar);
		BigDecimal precoEsperado = new BigDecimal("32.50");
		Assert.assertEquals(precoEsperado, ingresso.getPreco());
	}
	
	@Test
	public void deveConcederDescontoDe30PorcentoParaIngressosDeClientesDeBancos() {
		
		Ingresso ingresso = new Ingresso(sessao, TipoDeIngresso.BANCO,this.lugar);
		BigDecimal precoEsperado = new BigDecimal("22.750");
		Assert.assertEquals(precoEsperado, ingresso.getPreco());
	}
	
	@Test
	public void deveConcederDescontoDe50PorcentoParaIngressoDeEstudante() {
		
		Ingresso ingresso = new Ingresso(sessao, TipoDeIngresso.ESTUDANTE,this.lugar);
		BigDecimal precoEsperado = new BigDecimal("16.25");
		Assert.assertEquals(precoEsperado, ingresso.getPreco());
	}
}