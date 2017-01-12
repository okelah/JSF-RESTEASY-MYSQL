package com.livraria.bean;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.google.gson.Gson;
import com.livraria.model.Usuario;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

@ManagedBean
public class UsuarioBean {

	private Usuario usuario = new Usuario();

	public void cadastrar() throws JsonGenerationException, JsonMappingException, IOException {

		Usuario usuarioExistente = getUsuarioExistente(usuario);
		// verifica se o email já existe
		if (usuarioExistente.getEmail() == null) {

			if (usuario.getEmail() != null) {
				System.out.println("Salvando " + getUsuario().getEmail());
				Client cliente = Client.create();
				WebResource resource = cliente.resource("http://localhost:8080/livraria-rest/usuario/send");
				ObjectMapper mapper = new ObjectMapper();
				String jsonCustomer = mapper.writeValueAsString(getUsuario());
				resource.accept("application/json").type("application/json").post(Usuario.class, jsonCustomer);

				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
						usuario.getEmail() + " cadastrado com Sucesso", ""));

				usuario = new Usuario();
				
			}
		} else {
			exibirMensagem("Já existe um usuário com esse email!");
		}
	}
	
	public static Usuario getUsuarioExistente(Usuario usuario) {
		Client cliente = Client.create();
		WebResource resource = cliente.resource("http://localhost:8080/livraria-rest/usuario/" + usuario.getEmail());
		String json = resource.get(String.class);

		return new Gson().fromJson(json, Usuario.class);
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
