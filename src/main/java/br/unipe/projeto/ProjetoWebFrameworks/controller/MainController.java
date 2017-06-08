package br.unipe.projeto.ProjetoWebFrameworks.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.unipe.projeto.ProjetoWebFrameworks.dao.ContatoDAOImpl;
import br.unipe.projeto.ProjetoWebFrameworks.dao.UsuarioDAOImpl;
import br.unipe.projeto.ProjetoWebFrameworks.model.Contato;
import br.unipe.projeto.ProjetoWebFrameworks.model.Usuario;
import br.unipe.projeto.ProjetoWebFrameworks.repository.ContatoRepository;
import br.unipe.projeto.ProjetoWebFrameworks.repository.UsuarioRepository;
import br.unipe.projeto.ProjetoWebFrameworks.util.Autentica;

@Controller
@RequestMapping(value = "/")
public class MainController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private ContatoRepository contatos;	
	
	private HttpSession session;
	
	  @GetMapping
	  public String home(){
		  return "home";
	  }
	  
	  @RequestMapping("/login-error")
	  public String loginError(Model model) {
		  model.addAttribute("loginError", true);
		  return "home";
	  }
	  
	  @RequestMapping(value = "/usuario", method = {RequestMethod.POST, RequestMethod.GET})
	  public String usuario(Model model){
		  model.addAttribute("usuarioLogado", this.session.getAttribute("usuarioLogado"));
		  List<Usuario> lista = usuarioRepository.findAll();
		  model.addAttribute("usuarios", lista);
    	  return "usuario";
      }
	  
      @RequestMapping(value = "/contato", method = {RequestMethod.POST, RequestMethod.GET})
      public String autentica(String login, String senha, Model model, HttpSession session) {
    	  
    	  this.session = session;
    	  Usuario usuarioValido = null;
    	  if(this.session.isNew()){
			  Autentica auth = new Autentica();
	    	  usuarioValido = auth.authUsuário(login, senha, usuarioRepository);
	    	  
	    	  if(usuarioValido == null){ return "redirect:login-error"; }
	    	  this.session.setAttribute("usuarioLogado", usuarioValido);
    	  }else{
    		  usuarioValido = (Usuario) session.getAttribute("usuarioLogado");
    	  }
    	  
    	  List<Contato> lista = contatos.findByUsuario(usuarioValido);
		  model.addAttribute("contatos", lista);
		  model.addAttribute("usuarioLogado", this.session.getAttribute("usuarioLogado"));
		  
		  return "contato";
      }
      
      @RequestMapping(value = "/cadastraContato", method = RequestMethod.POST)
      public String cadastraContato(String nome, String sobreNome, String email, String telefone) {
    	  Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
    	  
    	  Contato contato = new Contato();
    	  
    	  contato.setNome(nome);
    	  contato.setSobreNome(sobreNome);
    	  contato.setEmail(email);
    	  contato.setTelefone(telefone);
    	  contato.setUsuario(usuario);
    	  
    	  ContatoDAOImpl dao = new ContatoDAOImpl();
    	  dao.incluir(contato);
    	  
		  return "redirect:contato";
      }
      
      @RequestMapping(value = "/cadastraUsuario", method = RequestMethod.POST)
      public String cadastraUsuario(String login, String nome, String senha, String perfil){
    	  
    	  Usuario usuario = new Usuario();
    	  
    	  usuario.setLogin(login);
    	  usuario.setNome(nome);
    	  usuario.setSenha(senha);
    	  
    	  UsuarioDAOImpl dao = new UsuarioDAOImpl();
    	  dao.incluir(usuario);
    	  
    	  return "redirect:usuario";
      }
      
}
