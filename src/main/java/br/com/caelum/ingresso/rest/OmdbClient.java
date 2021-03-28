package br.com.caelum.ingresso.rest;

import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import br.com.caelum.ingresso.model.DetalhesDoFilme;
import br.com.caelum.ingresso.model.Filme;
import br.com.caelum.ingresso.model.ImagemCapa;

@Component
public class OmdbClient {
	public Optional<DetalhesDoFilme> request(Filme filme) {
		RestTemplate client = new RestTemplate();
		String titulo = filme.getNome().replace(" ", "+");
		
		String url = String.format("https://omdb-fj22.herokuapp.com/movie?title=%s", titulo);
		try {
			DetalhesDoFilme detalhesDoFilme = client.getForObject(url, DetalhesDoFilme.class);
			return Optional.ofNullable(detalhesDoFilme);
		} catch (RestClientException e) {
			return Optional.empty();
		}
	}
	
	public Optional<ImagemCapa> buscaPosterNoOmdb(String nomeDoFilme) {

		RestTemplate client = new RestTemplate();

		try {
		String url = "https://omdb-fj22.herokuapp.com/movie?title=" + nomeDoFilme.replace(" ", "+");
		return Optional.ofNullable(client.getForObject(url, ImagemCapa.class));

		} catch (RestClientException ex) {

		return Optional.empty();

		}
	}
}
