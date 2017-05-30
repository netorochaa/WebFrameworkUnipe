package br.unipe.projeto.ProjetoWebFrameworks.controller;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.unipe.projeto.ProjetoWebFrameworks.model.Contato;
import br.unipe.projeto.ProjetoWebFrameworks.model.Usuario;
import br.unipe.projeto.ProjetoWebFrameworks.repository.ContatoRepository;
import br.unipe.projeto.ProjetoWebFrameworks.repository.UsuarioRepository;

@Controller
@RequestMapping("/")
public class ContatoController {

	private ContatoRepository contatoRepository;	
		
	@Autowired
    public ContatoController( ContatoRepository contatoRepository) {
          this.contatoRepository = contatoRepository;
    }
	
	private UsuarioRepository usuarioRepository;
 
	@RequestMapping(value = "/contato")
	public String contato(){
		return "contato";
	}
  
	@RequestMapping(value = "/cadastraContato/{login}", method = RequestMethod.POST)
	public String autentica(String login, Contato contato, HttpSession session) {
		Usuario usuarioObj = usuarioRepository.findByLogin(login);
		contato.setUsuario(usuarioObj);
		contatoRepository.save(contato);
		
		return "contato";
  }

}
