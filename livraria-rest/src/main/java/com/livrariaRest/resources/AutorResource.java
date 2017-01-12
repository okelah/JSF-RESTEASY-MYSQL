package com.livrariaRest.resources;

import java.net.URISyntaxException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.livrariaRest.models.Autor;

@Path("/autor")
public class AutorResource {

	@Path("{email}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getAutor(@PathParam("email") String email) {
		Autor autor = new GenericDAO().consultarAutorPorEmail(email);

		return new Gson().toJson(autor);
	}

	@Path("/send")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String cadastrarUsuario(Autor autor) throws URISyntaxException {
		System.out.println("Passando como POST o produto - " + autor.getEmail() + " da classe ProductResource");
		new GenericDAO().salvar(autor);

		return new Gson().toJson(autor);
	}
}
