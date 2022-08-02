package br.com.quarkus.project.resources;



import static io.restassured.RestAssured.given;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.quarkus.project.dto.CreatePostRequest;
import br.com.quarkus.project.model.User;
import br.com.quarkus.project.repository.UserRepository;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
@TestHTTPEndpoint(PostResource.class) //Aponta para o caminho da URL da classe PostResource
class PostResourceTest {
	
	@Inject
	UserRepository userRepository;
	Long userId;
	
	@BeforeEach
	@Transactional
	public void setUP() {
		var user = new User();
		user.setAge(36);
		user.setName("Samuel");
		
		userRepository.persist(user);
		userId = user.getId();
		
	}
	
	@Test
	@DisplayName("should create a post for a user ")
	public void createPostTest() {
		
		var postRequest = new CreatePostRequest();
		postRequest.setText("Some Text");
		
		var userID = 1;
		
		given()
			.contentType(ContentType.JSON)
			.body(postRequest)
			.pathParam("userId", userID)
		.when()
			.post()
		.then()
			.statusCode(201);
	}

}
