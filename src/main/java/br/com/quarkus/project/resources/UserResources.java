package br.com.quarkus.project.resources;

import java.util.Set;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.quarkus.project.dto.CreateUserRequest;
import br.com.quarkus.project.model.User;
import br.com.quarkus.project.repository.UserRepository;
import br.com.quarkus.project.resources.exceptions.ResponseError;
import io.quarkus.hibernate.orm.panache.PanacheQuery;

@Path("/users")
// A anotation Consumes diz qual tipo objeto consumirei
// neste caso será do tipo json, já a anotation Produces
// diz qual tipo de objeto retornará 
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResources {

	private UserRepository repository;
	private Validator validator;

	// Para injetar uma dependência devemos colocá-la no Construtor ou no Atributo
	@Inject
	public UserResources(UserRepository repository, Validator validator) {
		this.repository = repository;
		this.validator = validator;
	}

	@POST
	@Transactional
	public Response createUser(CreateUserRequest userRequest) {

		// Este campo retorna uma coleção com as violações que ocorrerem na validação da
		// classe userRequest
		Set<ConstraintViolation<CreateUserRequest>> violations = validator.validate(userRequest);
		
		if (!violations.isEmpty()) {

			return ResponseError.createFromValidation(violations).withStatusCode(ResponseError.UNPROCESSABLE_ENTITY_STATUS);
		}

		User user = new User();
		user.setAge(userRequest.getAge());
		user.setName(userRequest.getName());
		repository.persist(user);

		return Response.status(Response.Status.CREATED.getStatusCode())
				.entity(user)
				.build();
	}

	@GET
	public Response listAllUsers() {
		PanacheQuery<User> query = repository.findAll();
		return Response.ok(query.list()).build();
	}

	@DELETE
	@Path("{id}")
	@Transactional
	public Response deleteUser(@PathParam("id") Long id) {
		User user = repository.findById(id);
		if (user != null) {
			repository.delete(user);
			return Response.noContent().build();
		}

		return Response.status(Response.Status.NOT_FOUND).build();

	}

	@PUT
	@Path("{id}")
	@Transactional
	public Response updateUser(@PathParam("id") Long id, CreateUserRequest userData) {
		User user = repository.findById(id);
		if (user != null) {
			user.setName(userData.getName());
			user.setAge(userData.getAge());
			return Response.noContent().build();
		}

		return Response.status(Response.Status.NOT_FOUND).build();
	}

}
