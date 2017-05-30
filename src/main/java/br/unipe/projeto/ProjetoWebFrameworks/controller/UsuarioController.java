package br.unipe.projeto.ProjetoWebFrameworks.controller;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.unipe.projeto.ProjetoWebFrameworks.model.Usuario;
import br.unipe.projeto.ProjetoWebFrameworks.repository.UsuarioRepository;

@Controller
@RequestMapping("/")
public class UsuarioController {

	private UsuarioRepository usuarioRepository;
		
	  @Autowired
      public UsuarioController( UsuarioRepository usuarioRepository) {
            this.usuarioRepository = usuarioRepository;
      }
 
	  @RequestMapping(value = "/")
      public String home(){
    	  return "home";
      }
	  
	  @RequestMapping(value = "/usuario")
      public String usuario(){
    	  return "usuario";
      }
	  
      @RequestMapping(value = "/autentica", method = RequestMethod.POST)
      public String autentica(String login, String senha, HttpSession session) {
    	  if(!login.isEmpty() || !senha.isEmpty()){
    		  Usuario usuarioValido = usuarioRepository.findByLogin(login);
    	      if(!usuarioValido.equals(null) && usuarioValido.getSenha().equals(senha)){
    			  session.setAttribute("usuarioLogado", usuarioValido);
    			  return "contatos";
    		  }
    	  }
    	  return "home";
      }
    	  
	
}
