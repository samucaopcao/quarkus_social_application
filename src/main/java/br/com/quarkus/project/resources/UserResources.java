package br.com.quarkus.project.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.quarkus.project.dto.CreateUserRequest;

@Path("/users")
// A anotation Consumes diz qual tipo objeto consumirei
// neste caso será do tipo json, já a anotation Produces
// diz qual tipo de objeto retornará 
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResources {

	@POST
	public Response createUser(CreateUserRequest userRequest) {
		return Response.ok(userRequest).build();
	}

	@GET
	public Response listAllUsers() {
		return Response.ok().build();
	}
}
