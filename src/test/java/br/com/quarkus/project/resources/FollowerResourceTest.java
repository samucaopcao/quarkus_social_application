package br.com.quarkus.project.resources;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.quarkus.project.dto.FollowerRequest;
import br.com.quarkus.project.model.Follower;
import br.com.quarkus.project.model.User;
import br.com.quarkus.project.repository.FollowerRepository;
import br.com.quarkus.project.repository.UserRepository;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
@TestHTTPEndpoint(FollowerResource.class)
class FollowerResourceTest {

	@Inject
	UserRepository userRepository;
	
	@Inject
	FollowerRepository followerRepository;
	
	Long userId;
	Long userFollowerId;
	
	@BeforeEach
	@Transactional
	void setUP() {
		
		//Usuário padrão dos Testes
		var user = new User();
		user.setAge(36);
		user.setName("Samuel");
		userRepository.persist(user);
		userId = user.getId();
		
		//Usuário seguidor
		var follower = new User();
		follower.setAge(40);
		follower.setName("Cipriano");
		userRepository.persist(follower);
		userFollowerId = follower.getId();
		
		// Cria um Follower
		var followerEntity = new Follower();
		followerEntity.setFollower(follower);
		followerEntity.setUser(user);
		followerRepository.persist(followerEntity);
		
	}

	@Test
	@DisplayName("should return 409 when Follower Id is equal to User Id")
	public void sameUserAsFollowerTest() {
		
		var body = new FollowerRequest();
		body.setFollowerId(userId);
		
		given()
			.contentType(ContentType.JSON)
			.body(body)
			.pathParam("userId", userId)
		.when()
			.put()
		.then()
			.statusCode(Response.Status.CONFLICT.getStatusCode())
			.body(Matchers.is("You can't follow yourself"));
	}
	
	@Test
	@DisplayName("should return 404 on follow a user when User Id doesn't exist")
	public void userNotFoundWhenTryingToFollowTest() {
		
		var inexistentUserId = 999;
		
		var body = new FollowerRequest();
		body.setFollowerId(userId);
		
		given()
			.contentType(ContentType.JSON)
			.body(body)
			.pathParam("userId", inexistentUserId)
		.when()
			.put()
		.then()
			.statusCode(Response.Status.NOT_FOUND.getStatusCode());
		
	}
	
	@Test
	@DisplayName("should follow a user")
	public void followUserTest() {
		
		var body = new FollowerRequest();
		body.setFollowerId(userFollowerId);
		
		given()
			.contentType(ContentType.JSON)
			.body(body)
			.pathParam("userId", userId)
		.when()
			.put()
		.then()
			.statusCode(Response.Status.NO_CONTENT.getStatusCode());
		
	}
	
	@Test
	@DisplayName("should return 404 on list user followers and User Id doesn't exist")
	public void userNotFoundWhenListingFollowersTest() {
		
		var inexistentUserId = 999;
		
		var body = new FollowerRequest();
		body.setFollowerId(userId);
		
		given()
			.contentType(ContentType.JSON)
			.pathParam("userId", inexistentUserId)
		.when()
			.get()
		.then()
			.statusCode(Response.Status.NOT_FOUND.getStatusCode());
		
	}
	
	@Test
	@DisplayName("should list a user's followers")
	public void listingFollowersTest() {
		
		var response =		
		given()
			.contentType(ContentType.JSON)
			.pathParam("userId", userId)
		.when()
			.get()
		.then()
			.extract().response();
		
		var followersCount = response.jsonPath().get("followersCount");
		var followersContent = response.jsonPath().getList("content");
		
		assertEquals(Response.Status.OK.getStatusCode(),response.getStatusCode());
		assertEquals(1,followersCount);
		assertEquals(1,followersContent.size());
	}
	
	@Test
	@DisplayName("should return 404 on unfollow user and User id doesn't exist")
	public void userNotFoundWhenUnfollowingAUserTest() {
		
		var inexistentUserId = 999;
		
		given()
			.contentType(ContentType.JSON)
			.pathParam("userId", inexistentUserId)
			.queryParam("followerId", userFollowerId)
		.when()
			.delete()
		.then()
			.statusCode(Response.Status.NOT_FOUND.getStatusCode());
		
	}
	
	@Test
	@DisplayName("should unfollow an user")
	public void unfollowUserTest() {
		
		given()
			.pathParam("userId", userId)
			.queryParam("followerId", userFollowerId)
		.when()
			.delete()
		.then()
			.statusCode(Response.Status.NO_CONTENT.getStatusCode());
		
	}
}
