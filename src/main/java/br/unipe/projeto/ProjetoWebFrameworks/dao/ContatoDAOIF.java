package br.unipe.projeto.ProjetoWebFrameworks.dao;

import br.unipe.projeto.ProjetoWebFrameworks.model.Contato;

public interface ContatoDAOIF {

	public Contato consultar(long id) ;
	public void incluir(Contato entity) ;
	public void atualizar(Contato entity) ;
	public void remover(Contato entity) ;
	public void removerPorId(long entityId) ;
	
}
