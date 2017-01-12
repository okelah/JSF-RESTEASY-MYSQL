package com.livraria.bean;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import com.livraria.model.Usuario;

@ManagedBean
public class LoginBean {

	private Usuario usuario = new Usuario();
	private String url;

	public void getUsuarioParaLogin() throws IOException {
		if (usuario != null) {
			Usuario usuarioJson = UsuarioBean.getUsuarioExistente(usuario);

			if (usuarioJson.getEmail() == null) {
				exibirMensagem("Não existe nenhum usuário com esse email");
				usuario = new Usuario();

				return;
			} else {
				if (usuarioJson.getSenha().equals(usuario.getSenha())) {
					redirecionar("home.xhtml");
				} else {
					exibirMensagem("Senha inválida para este email");
					usuario = new Usuario();
				}
			}
		}
	}

	public void cadastrar() throws IOException {
		url = "cadastro_usuario.xhtml";
		redirecionar(url);
	}

	private void redirecionar(String url) throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect(url);
	}

	private void exibirMensagem(String mensagem) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, ""));
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
