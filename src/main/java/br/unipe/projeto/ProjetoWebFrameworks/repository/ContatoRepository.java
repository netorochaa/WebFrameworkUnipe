package br.unipe.projeto.ProjetoWebFrameworks.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import br.unipe.projeto.ProjetoWebFrameworks.model.Contato;
import br.unipe.projeto.ProjetoWebFrameworks.model.Usuario;

public interface ContatoRepository extends JpaRepository<Contato, Long> {
	
	List<Contato> findByUsuario(Usuario usuario);

}