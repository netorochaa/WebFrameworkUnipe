package br.unipe.projeto.ProjetoWebFrameworks.dao;

import br.unipe.projeto.ProjetoWebFrameworks.model.Usuario;

public interface UsuarioDAOIF {

	public Usuario consultar(long id) ;
	public void incluir(Usuario entity) ;
	public void atualizar(Usuario entity) ;
	public void remover(Usuario entity) ;
	public void removerPorId(long entityId) ;
	
}
