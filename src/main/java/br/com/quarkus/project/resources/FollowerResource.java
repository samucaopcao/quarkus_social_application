package br.com.quarkus.project.resources;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.quarkus.project.dto.FollowerRequest;
import br.com.quarkus.project.model.Follower;
import br.com.quarkus.project.model.User;
import br.com.quarkus.project.repository.FollowerRepository;
import br.com.quarkus.project.repository.UserRepository;

@Path("/users/{userId}/followers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FollowerResource {

	private FollowerRepository repository;
	private UserRepository userRepository;

	@Inject
	public FollowerResource(FollowerRepository repository, UserRepository userRepository) {
		this.repository = repository;
		this.userRepository = userRepository;
	}

	@PUT
	@Transactional
	public Response followUser(@PathParam("userId") Long userId, FollowerRequest request) {

		// Verifica se usuário está tentando seguir a si mesmo
		if (userId.equals(request.getFollowerId())) {
			return Response.status(Response.Status.CONFLICT).entity("You can't follow yourself").build();
		}

		User user = userRepository.findById(userId);
		if (user == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		User follower = userRepository.findById(request.getFollowerId());

		boolean follows = repository.follows(follower, user);

		// Se esse follow não segue esse usuário ainda
		if (!follows) {
			Follower entity = new Follower();
			entity.setUser(user);
			entity.setFollower(follower);

			repository.persist(entity);
		}

		return Response.status(Response.Status.NO_CONTENT).build();
	}

}
