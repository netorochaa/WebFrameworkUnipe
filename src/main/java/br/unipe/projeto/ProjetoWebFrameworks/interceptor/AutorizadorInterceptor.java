package br.unipe.projeto.ProjetoWebFrameworks.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import br.unipe.projeto.ProjetoWebFrameworks.model.Usuario;

@Component
public class AutorizadorInterceptor extends HandlerInterceptorAdapter {
	
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, 
			Object handler) throws Exception {
		
		String uri = request.getRequestURI();
		
		if(uri.equals("/") || uri.equals("/principal") 
				|| uri.equals("/deslogar") || uri.equals("/login-error")
				|| uri.equals("/error")){
			return true;
		}
		
		HttpSession sessao =  request.getSession();
		
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		
		if(usuario != null){
			
			if(uri.equals("/contato") || uri.equals("/cadastraContato")
					|| uri.contains("/deletaContato")) {
				
				if(usuario.getPerfil().getCodigo().equals("user_01")){
					return true;
				}
				
				mostrarAlerta(response, "Usuário não tem permissão de acesso!", "/usuario");
				return false;
				
			}else if(uri.equals("/usuario") || uri.equals("/cadastraUsuario")
					|| uri.contains("/deletaUsuario")){
				
				if(usuario.getPerfil().getCodigo().equals("adm_01")){
					return true;
				}
				
				mostrarAlerta(response, "Usuário não tem permissão de acesso!", "/contato");;
				return false;
			}		
		}
		
		mostrarAlerta(response, "Acesso negado! É necessário logar no Sistema", "/");
		return false;
	}
	
	private void mostrarAlerta(HttpServletResponse response, String texto, String pagina) throws Exception{
		PrintWriter out = response.getWriter();  
		response.setContentType("text/html");  
		out.println("<script type=\"text/javascript\">");  
		out.println("alert('" + texto + "');"); 
		out.println("location.href='" + pagina + "';");  
		out.println("</script>");
	}

}
