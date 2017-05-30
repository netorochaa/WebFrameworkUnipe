package br.unipe.projeto.ProjetoWebFrameworks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.unipe.projeto.ProjetoWebFrameworks.model.Contato;

public interface ContatoRepository extends JpaRepository<Contato, Long> {
//   
//        List<Contato> findByAutor(String autor);
//        
//        List<Contato> findByTitulo(String titulo);
//        
//        Livro findByIsbn(String isbn);
   
  }