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
import com.livrariaRest.models.Usuario;

@Path("/usuario")
public class UsuarioResource {

	@Path("{email}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getUusario(@PathParam("email") String email) {
		Usuario usuario = new GenericDAO().consultarUsuarioPorEmail(email);

		return new Gson().toJson(usuario);
	}

	@Path("/send")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String cadastrarUsuario(Usuario usuario) throws URISyntaxException {
		System.out.println("Passando como POST o produto - " + usuario.getEmail() + " da classe ProductResource");
		new GenericDAO().salvar(usuario);

		return new Gson().toJson(usuario);
	}
}
