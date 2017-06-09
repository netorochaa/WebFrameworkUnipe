package br.unipe.projeto.ProjetoWebFrameworks.util;

import br.unipe.projeto.ProjetoWebFrameworks.model.Usuario;
import br.unipe.projeto.ProjetoWebFrameworks.repository.UsuarioRepository;

public class Autentica {
	
	public Usuario authUsuario(String login, String senha, UsuarioRepository usuarioRepository){
		
		Usuario usuarioValido = null;
		
		if( login != null && !login.isEmpty() 
				&& senha != null && !senha.isEmpty()){
  		  
  		  usuarioValido = usuarioRepository.findByLoginAndSenha(login, senha);
  		  
		}
		return usuarioValido;
	}
}
