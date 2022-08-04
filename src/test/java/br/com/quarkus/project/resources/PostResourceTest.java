package br.com.quarkus.project.resources;



import static io.restassured.RestAssured.given;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.quarkus.project.dto.CreatePostRequest;
import br.com.quarkus.project.model.Follower;
import br.com.quarkus.project.model.Post;
import br.com.quarkus.project.model.User;
import br.com.quarkus.project.repository.FollowerRepository;
import br.com.quarkus.project.repository.PostRepository;
import br.com.quarkus.project.repository.UserRepository;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
@TestHTTPEndpoint(PostResource.class) //Aponta para o caminho da URL da classe PostResource
class PostResourceTest {
	
	@Inject
	UserRepository userRepository;
	
	@Inject
	FollowerRepository followerRepository;
	
	@Inject
	PostRepository postRepository;
	
	Long userId;
	Long userNotFollowerId;
	Long userFollowerId;
	
	@BeforeEach
	@Transactional
	public void setUP() {
		
		//Usuário padrão dos Testes
		var user = new User();
		user.setAge(36);
		user.setName("Samuel");
		userRepository.persist(user);
		userId = user.getId();
		
		Post post = new Post();
		post.setText("Hello");
		post.setUser(user);
		postRepository.persist(post);

		//Usuário que não segue ninguém
		var userNotFollower = new User();
		userNotFollower.setAge(37);
		userNotFollower.setName("Fulano");
		userRepository.persist(userNotFollower);
		userNotFollowerId = userNotFollower.getId();
		
		//Usuário seguidor
		var userFollower = new User();
		userFollower.setAge(38);
		userFollower.setName("Beltrano");
		userRepository.persist(userFollower);
		userFollowerId = userFollower.getId();
		
		Follower follower = new Follower();
		follower.setUser(user);
		follower.setFollower(userFollower);
		followerRepository.persist(follower);
	}
	
	@Test
	@DisplayName("should create a post for a user ")
	public void createPostTest() {
		
		var postRequest = new CreatePostRequest();
		postRequest.setText("Some Text");
		
		
		given()
			.contentType(ContentType.JSON)
			.body(postRequest)
			.pathParam("userId", userId)
		.when()
			.post()
		.then()
			.statusCode(201);
	}

	@Test
	@DisplayName("should return status 404 when trying to make a post for an inexistent user ")
	public void postForAnInexistentUserTest() {
		
		var postRequest = new CreatePostRequest();
		postRequest.setText("Some Text");
		
		var inxistentUserId = 999;
		
		given()
			.contentType(ContentType.JSON)
			.body(postRequest)
			.pathParam("userId", inxistentUserId)
		.when()
			.post()
		.then()
			.statusCode(404);
	}
	
	@Test
	@DisplayName("should return status 404 when user doesn't exist")
	public void listPostUserNotFoundTest() {
		var inexistentUserId = 999;
		
		given()
			.pathParam("userId", inexistentUserId)
		.when()
			.get() // Não preciso colocar a URL pois tenho a anotation @TestHTTPEndpoint na classe
		.then()
			.statusCode(404);
	}
	
	@Test
	@DisplayName("should return status 400 when followerId header is not present")
	public void listPostFollowerHeaderNotSendTest() {
		
	given()
		.pathParam("userId", userId)
	.when()
		.get() // Não preciso colocar a URL pois tenho a anotation @TestHTTPEndpoint na classe
	.then()
		.statusCode(400)
		.body(Matchers.is("You forgot the header followerId"));
	}
	
	@Test
	@DisplayName("should return status 400 when follower doesn't exist")
	public void listPostFollowerNotFoundTest() {
		
		var inexistentFollowerId = 999;
		
	given()
		.pathParam("userId", userId)
		.header("followerId", inexistentFollowerId)
	.when()
		.get() // Não preciso colocar a URL pois tenho a anotation @TestHTTPEndpoint na classe
	.then()
		.statusCode(400)
		.body(Matchers.is("Inexistent followerId"));
		
	}
	
	@Test
	@DisplayName("should return status 403 when follower isn't a follower")
	public void listPostNotAFollowerTest() {
		
	given()
		.pathParam("userId", userId)
		.header("followerId", userNotFollowerId)
	.when()
		.get() // Não preciso colocar a URL pois tenho a anotation @TestHTTPEndpoint na classe
	.then()
		.statusCode(403)
		.body(Matchers.is("You cant't see these posts"));
		
	}
	
	@Test
	@DisplayName("should return posts")
	public void listPostsTest() {
		
		given()
		.pathParam("userId", userId)
		.header("followerId", userFollowerId)
	.when()
		.get() // Não preciso colocar a URL pois tenho a anotation @TestHTTPEndpoint na classe
	.then()
		.statusCode(200)
		.body("size()",Matchers.is(1));
		
		
	}
	
}
