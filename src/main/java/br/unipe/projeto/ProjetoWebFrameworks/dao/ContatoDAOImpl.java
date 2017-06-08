package br.unipe.projeto.ProjetoWebFrameworks.dao;

import javax.persistence.EntityManager;

import br.unipe.projeto.ProjetoWebFrameworks.factory.JPAUtil;
import br.unipe.projeto.ProjetoWebFrameworks.model.Contato;

public class ContatoDAOImpl {
	
	public void incluir(Contato contato) {
		EntityManager manager = JPAUtil.getEntityManager();
		manager.getTransaction().begin();
		manager.persist(contato);
		manager.getTransaction().commit();
		manager.close();
	}
}
