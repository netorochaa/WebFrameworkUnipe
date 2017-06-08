package br.unipe.projeto.ProjetoWebFrameworks.util;

import br.unipe.projeto.ProjetoWebFrameworks.model.Usuario;
import br.unipe.projeto.ProjetoWebFrameworks.repository.UsuarioRepository;

public class Autentica {
	
	public Usuario authUsuário(String login, String senha, UsuarioRepository usuarioRepository){
		if(!login.isEmpty() || !senha.isEmpty()){
  		  Usuario usuarioValido = null;
  		  
  		  if(usuarioRepository.findByLogin(login) != (null)){
			  usuarioValido = usuarioRepository.findByLogin(login);    			  
		  }
	      
		  if(!usuarioValido.equals(null) && usuarioValido.getSenha().equals(senha)){
			  return usuarioValido;
		  }
		}
		return null;
	}
}
