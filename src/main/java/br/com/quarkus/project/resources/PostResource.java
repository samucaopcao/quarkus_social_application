package br.com.quarkus.project.resources;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.quarkus.project.dto.CreatePostRequest;
import br.com.quarkus.project.dto.PostResponse;
import br.com.quarkus.project.model.Post;
import br.com.quarkus.project.model.User;
import br.com.quarkus.project.repository.PostRepository;
import br.com.quarkus.project.repository.UserRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;

@Path("/users/{userId}/posts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PostResource {

	private UserRepository userRepository;
	private PostRepository repository;

	@Inject
	public PostResource(
			UserRepository userRepository,
			PostRepository repository) {
		this.userRepository = userRepository;
		this.repository = repository;

	}

	@POST
	@Transactional
	public Response savePost(@PathParam("userId") Long userId, CreatePostRequest request) {
		User user = userRepository.findById(userId);
		if (user == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		
		Post post = new Post();
		post.setText(request.getText());
		post.setUser(user);

		repository.persist(post);
		
		return Response.status(Response.Status.CREATED).build();

	}
	
	
	@GET
	public Response listPost(@PathParam("userId") Long userId) {
		User user = userRepository.findById(userId);
		if (user == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		PanacheQuery<Post> query = repository.find("user",user);
		List<Post> list = query.list();
		
		// Mostrando apenas informações essenciais de um Post de usuário
		// trabalhando com o DTO PostResponse e seu método fromEntity
		List<PostResponse> postResponseList = list.stream()
				.map(PostResponse::fromEntity)
				.collect(Collectors.toList());
		
		return Response.ok(list).build();

	}

}
