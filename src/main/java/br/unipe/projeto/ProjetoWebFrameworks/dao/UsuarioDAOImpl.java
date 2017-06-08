package br.unipe.projeto.ProjetoWebFrameworks.dao;

import javax.persistence.EntityManager;

import br.unipe.projeto.ProjetoWebFrameworks.factory.JPAUtil;
import br.unipe.projeto.ProjetoWebFrameworks.model.Usuario;

public class UsuarioDAOImpl {
	
	public void incluir(Usuario usuario) {
		EntityManager manager = JPAUtil.getEntityManager();
		manager.getTransaction().begin();
		manager.persist(usuario);
		manager.getTransaction().commit();
		manager.close();
	}
}
