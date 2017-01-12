package com.livraria.bean;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.google.gson.Gson;
import com.livraria.model.Autor;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

@ManagedBean
public class AutorBean {
	private Autor autor = new Autor();

	public void cadastrar() throws JsonGenerationException, JsonMappingException, IOException {

		Autor usuarioExistente = getAutorExistente(getAutor());
		// verifica se o email já existe
		if (usuarioExistente.getEmail() == null) {

			if (getAutor().getEmail() != null) {
				System.out.println("Salvando " + getAutor().getEmail());
				Client cliente = Client.create();
				WebResource resource = cliente.resource("http://localhost:8080/livraria-rest/autor/send");
				ObjectMapper mapper = new ObjectMapper();
				String jsonCustomer = mapper.writeValueAsString(getAutor());
				resource.accept("application/json").type("application/json").post(Autor.class, jsonCustomer);

				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
						getAutor().getEmail() + " cadastrado com Sucesso", ""));

				setAutor(new Autor());

			}
		} else {
			exibirMensagem("Já existe um usuário com esse email!");
		}
	}

	public static Autor getAutorExistente(Autor autor) {
		Client cliente = Client.create();
		WebResource resource = cliente.resource("http://localhost:8080/livraria-rest/autor/" + autor.getEmail());
		String json = resource.get(String.class);

		return new Gson().fromJson(json, Autor.class);
	}

	private void exibirMensagem(String mensagem) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, ""));
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}
}
