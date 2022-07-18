package br.com.quarkus.project.resources;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
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
import br.com.quarkus.project.repository.FollowerRepository;
import br.com.quarkus.project.repository.PostRepository;
import br.com.quarkus.project.repository.UserRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Sort;

@Path("/users/{userId}/posts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PostResource {

	private UserRepository userRepository;
	private PostRepository repository;
	private FollowerRepository followerRepository;

	@Inject
	public PostResource(
			UserRepository userRepository,
			PostRepository repository,
			FollowerRepository followerRepository) {
		this.userRepository = userRepository;
		this.repository = repository;
		this.followerRepository = followerRepository;

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
	public Response listPost(
			@PathParam("userId") Long userId, 
			@HeaderParam("followerId") Long followerId) {
		User user = userRepository.findById(userId);
		if (user == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		
		// Se esqueceu de passar o parâmetro via header
		if(followerId == null) {
			return Response
					.status(Response.Status.BAD_REQUEST)
					.entity("You forgot the header followerId")
					.build();
		}
		
		User follower = userRepository.findById(followerId);

		if(follower == null) {
			return Response
					.status(Response.Status.BAD_REQUEST)
					.entity("Inexistent followerId")
					.build();
		}
		
		
		// Vai dizer se segue ou não
		boolean follows = followerRepository.follows(follower, user);
		
		// Se não segue 
		if(!follows) {
			return Response.status(Response.Status.FORBIDDEN).entity("You cant't see these posts").build();
		}
		
		// Organizando posts de usuário s
		PanacheQuery<Post> query = repository.find("user",Sort.by("dateTime", Sort.Direction.Descending),user);
		List<Post> list = query.list();
		
		// Mostrando apenas informações essenciais de um Post de usuário
		// trabalhando com o DTO PostResponse e seu método fromEntity
		List<PostResponse> postResponseList = list.stream()
				.map(PostResponse::fromEntity)
				.collect(Collectors.toList());
		
		return Response.ok(list).build();

	}

}
