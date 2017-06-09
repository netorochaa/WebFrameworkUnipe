package br.unipe.projeto.ProjetoWebFrameworks.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.unipe.projeto.ProjetoWebFrameworks.model.Contato;
import br.unipe.projeto.ProjetoWebFrameworks.model.Perfil;
import br.unipe.projeto.ProjetoWebFrameworks.model.Usuario;
import br.unipe.projeto.ProjetoWebFrameworks.repository.ContatoRepository;
import br.unipe.projeto.ProjetoWebFrameworks.repository.PerfilRepository;
import br.unipe.projeto.ProjetoWebFrameworks.repository.UsuarioRepository;
import br.unipe.projeto.ProjetoWebFrameworks.util.Autentica;

@Controller
@RequestMapping(value = "/")
public class MainController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private ContatoRepository contatoRepository;

	@Autowired
	private PerfilRepository perfilRepository;

	private HttpSession session;

	@GetMapping
	public String home(){
		criarPerfis();
		criarUsuarioAdministrador();

		if(this.session != null) {
			try{
				this.session.invalidate();
			}catch(IllegalStateException ex){
				return "home";
			}
		}

		return "home";
	}
	
	@RequestMapping("/deslogar")
	public String deslogar() {
		return "redirect:";
	}

	@RequestMapping("/login-error")
	public String loginError(Model model) {
		model.addAttribute("loginError", true);
		return "home";
	}

	@RequestMapping(value = "/principal", method = {RequestMethod.POST, RequestMethod.GET})
	public String autentica(String login, String senha, Model model, HttpSession session) {

		this.session = session;
		Usuario usuario = null;

		Autentica auth = new Autentica();
		usuario = auth.authUsuario(login, senha, usuarioRepository);

		if(usuario == null){ 
			return "redirect:login-error"; 
		}

		this.session.setAttribute("usuarioLogado", usuario);

		model.addAttribute("usuarioLogado", 
				this.session.getAttribute("usuarioLogado"));


		if(usuario.getPerfil().getCodigo().equals("adm_01")) {

			List<Usuario> listaUsuario = usuarioRepository.findAll();
			model.addAttribute("usuarios", listaUsuario);
			List<Perfil> listaPerfil = perfilRepository.findAll();
			model.addAttribute("perfis", listaPerfil);			
			return "usuario";
		}

		if(usuario.getPerfil().getCodigo().equals("user_01")) {

			List<Contato> listaContato = contatoRepository.findByUsuario(usuario);
			model.addAttribute("contatos", listaContato);
			return "contato";

		}	 

		return "redirect:login-error"; 
	}

	@RequestMapping(value = "/usuario", method = {RequestMethod.POST, RequestMethod.GET})
	public String usuario(Model model){
		
		model.addAttribute("usuarioLogado", this.session.getAttribute("usuarioLogado"));
		
		List<Usuario> lista = usuarioRepository.findAll();
		model.addAttribute("usuarios", lista);
		List<Perfil> listaPerfil = perfilRepository.findAll();
		model.addAttribute("perfis", listaPerfil);
		return "usuario";
	}

	@RequestMapping(value = "/contato", method = {RequestMethod.POST, RequestMethod.GET})
	public String contato(Model model){

		Usuario usuarioLogado = (Usuario) this.session.getAttribute("usuarioLogado");

		model.addAttribute("usuarioLogado", usuarioLogado);
		List<Contato> lista = contatoRepository.findByUsuario(usuarioLogado);
		model.addAttribute("contatos", lista);
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

		contatoRepository.save(contato);

		return "redirect:contato";
	}
	
	@RequestMapping(value = "/deletaContato/{id}", method = RequestMethod.GET)
	public String deletaContato(@PathVariable("id") Integer id) {

		Contato contato = contatoRepository.findById(id);
		
		if(contato != null){
			contatoRepository.delete(contato);
		}

		return "redirect:../contato";
	}

	@RequestMapping(value = "/cadastraUsuario", method = RequestMethod.POST)
	public String cadastraUsuario(String login, String nome, String senha, String perfil){

		Usuario usuario = new Usuario();

		usuario.setLogin(login);
		usuario.setNome(nome);
		usuario.setSenha(senha);
		
		usuario.setPerfil(perfilRepository.findByCodigo(perfil));

		usuarioRepository.save(usuario);

		return "redirect:usuario";
	}
	
	@RequestMapping(value = "/deletaUsuario/{id}", method = RequestMethod.GET)
	public String deletaUsuario(@PathVariable("id") Integer id, Model model) {

		Usuario usuarioLogado = (Usuario) this.session.getAttribute("usuarioLogado");
		Usuario usuario = usuarioRepository.findById(id);
		
		if(usuario != null && usuarioLogado.getId() != usuario.getId() && 
				!usuario.getLogin().equals("admin")) {
			
			List<Contato> contatos = contatoRepository.findByUsuario(usuario);
			
			contatoRepository.delete(contatos);
			
			usuarioRepository.delete(usuario);
		} 

		return "redirect:../usuario";
	}

	/*
	 * métodos após a criação da aplicação para garantir que existam os perfis 
	 * e o usuário administrador
	 */

	private void criarPerfis() {

		Perfil perfil = perfilRepository.findByCodigoAndNome("adm_01", "Administrador");

		if(perfil == null) {

			perfil = new Perfil();

			perfil.setCodigo("adm_01");
			perfil.setNome("Administrador");

			perfilRepository.save(perfil);

		}

		perfil = perfilRepository.findByCodigoAndNome("user_01", "Usuario");

		if(perfil == null) {

			perfil = new Perfil();

			perfil.setCodigo("user_01");
			perfil.setNome("Usuario");

			perfilRepository.save(perfil);

		}
	}

	private void criarUsuarioAdministrador() {

		Usuario admin = usuarioRepository.findByLogin("admin");

		if(admin == null) {

			admin = new Usuario();

			admin.setLogin("admin");
			admin.setNome("Administrador do Sistema");
			admin.setSenha("admin");

			Perfil perfil = perfilRepository.findByCodigoAndNome("adm_01", "Administrador");

			admin.setPerfil(perfil);

			usuarioRepository.save(admin);
		}

	}

}
