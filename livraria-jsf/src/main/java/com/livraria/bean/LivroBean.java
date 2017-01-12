package com.livraria.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.google.gson.Gson;
import com.livraria.model.Autor;
import com.livraria.model.Livro;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

@ManagedBean
public class LivroBean {
	private Livro livro = new Livro();
	private Autor autor = new Autor();
	private List<Autor> autores = new ArrayList<>();
	private int autorID;

	public void cadastrar() throws JsonGenerationException, JsonMappingException, IOException {

		Livro usuarioExistente = getLivroExistente(getLivro());
		// verifica se o email já existe
		if (usuarioExistente.getTitulo() == null) {

			if (getLivro().getTitulo() != null) {
				System.out.println("Salvando " + getLivro().getTitulo());
				Client cliente = Client.create();
				WebResource resource = cliente.resource("http://localhost:8080/livraria-rest/livro/send");
				ObjectMapper mapper = new ObjectMapper();
				String jsonCustomer = mapper.writeValueAsString(getLivro());
				resource.accept("application/json").type("application/json").post(Livro.class, jsonCustomer);

				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
						getLivro().getTitulo() + " cadastrado com Sucesso", ""));

				setLivro(new Livro());

			}
		} else {
			exibirMensagem("Já existe um Livro com esse Título!");
		}
	}

	public static Livro getLivroExistente(Livro livro) {
		Client cliente = Client.create();
		WebResource resource = cliente.resource("http://localhost:8080/livraria-rest/livro/" + livro.getTitulo());
		String json = resource.get(String.class);

		return new Gson().fromJson(json, Livro.class);
	}

	private void exibirMensagem(String mensagem) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, ""));
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public List<Autor> getAutores() {
		Client cliente = Client.create();
		WebResource resource = cliente
				.resource("http://localhost:8080/livraria-rest/livro/autores" + livro.getTitulo());
		String json = resource.get(String.class);
		autor = new Gson().fromJson(json, Autor.class);
		autores.add(autor);
		
		return autores;
	}

	public void setAutores(List<Autor> autores) {
		this.autores = autores;
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	public int getAutorID() {
		return autorID;
	}

	public void setAutorID(int autorID) {
		this.autorID = autorID;
	}
}
