package br.unipe.projeto.ProjetoWebFrameworks.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.unipe.projeto.ProjetoWebFrameworks.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	Usuario findByLogin(String login);
	
	Usuario findByLoginAndSenha(String login, String senha);
	
	Usuario findById(Integer id);
	
}
