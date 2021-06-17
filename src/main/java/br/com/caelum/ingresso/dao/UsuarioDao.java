package br.com.caelum.ingresso.dao;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.caelum.ingresso.model.Usuario;

public class UsuarioDao {

	@PersistenceContext
	private EntityManager manager;

	public Optional<Usuario> findByEmail(String email) {
		return manager.createQuery("select u from Usuario u where u.email = :email", Usuario.class)
				.setParameter("email", email).getResultList().stream().findFirst();
	}
}
