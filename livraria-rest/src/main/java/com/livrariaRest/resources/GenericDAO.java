package com.livrariaRest.resources;

import java.util.List;

import javax.persistence.EntityManager;

import com.livrariaRest.models.Autor;
import com.livrariaRest.models.Livro;
import com.livrariaRest.models.Usuario;

public class GenericDAO {

	private EntityManager manager;

	public void salvar(Object object) {
		manager = JPAUtil.createManager();
		manager.getTransaction().begin();
		manager.persist(object);
		manager.getTransaction().commit();
		System.out.println("Objeto Persistido com sucesso pela classe DAO");
		manager.close();
	}

	public Usuario consultarUsuarioPorEmail(String email) {
		manager = JPAUtil.createManager();
		Usuario usuario = new Usuario();
		try {
			usuario = manager.createQuery("select u from Usuario u where u.email = '" + email + "'", Usuario.class)
					.getSingleResult();
		} catch (Exception e) {
		}

		if (usuario != null) {
			return usuario;
		} else {
			return new Usuario();
		}
	}

	public Autor consultarAutorPorEmail(String email) {
		manager = JPAUtil.createManager();
		Autor autor = new Autor();
		try {
			autor = manager.createQuery("select a from Autor a where a.email = '" + email + "'", Autor.class)
					.getSingleResult();
		} catch (Exception e) {
		}

		if (autor != null) {
			return autor;
		} else {
			return new Autor();
		}

	}

	public Livro consultarLivroPorTitulo(String titulo) {
		manager = JPAUtil.createManager();
		Livro livro = new Livro();
		try {
			livro = manager.createQuery("select l from Livro l where l.titulo = '" + titulo + "'", Livro.class)
					.getSingleResult();
		} catch (Exception e) {
		}

		if (livro != null) {
			return livro;
		} else {
			return new Livro();
		}

	}

	public List<Autor> getAutores() {
		manager = JPAUtil.createManager();
		List<Autor> autores = manager.createQuery("select a from Autor a", Autor.class).getResultList();

		return autores;
	}
}