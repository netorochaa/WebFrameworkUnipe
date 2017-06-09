package br.unipe.projeto.ProjetoWebFrameworks.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.unipe.projeto.ProjetoWebFrameworks.model.Perfil;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {
	
	Perfil findByCodigoAndNome(String cdPerfil, String nomePerfil);
	
	Perfil findByCodigo(String codigo);

}