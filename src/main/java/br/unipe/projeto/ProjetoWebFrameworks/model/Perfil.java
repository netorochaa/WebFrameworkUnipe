package br.unipe.projeto.ProjetoWebFrameworks.model;

public enum Perfil {
	
ADM(1, "ADMINISTRADOR"), USER(2, "USUÁRIO");
	
	private int cdPerfil;
	private String noPerfil;
	
	Perfil(int cdPerfil, String noPerfil){
		this.cdPerfil = cdPerfil;
		this.noPerfil = noPerfil;
	}

	public int getCdPerfil() {
		return cdPerfil;
	}

	public void setCdPerfil(int cdPerfil) {
		this.cdPerfil = cdPerfil;
	}

	public String getNoPerfil() {
		return noPerfil;
	}

	public void setNoPerfil(String noPerfil) {
		this.noPerfil = noPerfil;
	}

}
