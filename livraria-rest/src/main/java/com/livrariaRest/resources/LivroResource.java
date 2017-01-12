package com.livrariaRest.resources;

import java.net.URISyntaxException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.livrariaRest.models.Autor;
import com.livrariaRest.models.Livro;

@Path("/livro")
public class LivroResource {

	@Path("{titulo}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getAutor(@PathParam("titulo") String titulo) {
		Livro livro = new GenericDAO().consultarLivroPorTitulo(titulo);

		return new Gson().toJson(livro);
	}

	@Path("/send")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String cadastrarUsuario(Livro livro) throws URISyntaxException {
		System.out.println("Passando como POST o produto - " + livro.getTitulo() + " da classe ProductResource");
		new GenericDAO().salvar(livro);

		return new Gson().toJson(livro);
	}

	@Path("autores")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Autor> getAutores() {
		return new GenericDAO().getAutores();
	}
}
