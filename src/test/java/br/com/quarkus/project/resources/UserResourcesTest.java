package br.com.quarkus.project.resources;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.URL;
import java.util.List;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import br.com.quarkus.project.dto.CreateUserRequest;
import br.com.quarkus.project.resources.exceptions.ResponseError;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // Para ordenar a sequência de execução dos testes
class UserResourcesTest {

	@TestHTTPResource("/users")
	URL apiURL;
	
	@Test
	@DisplayName("should create an user sucessfully")
	@Order(1)
	public void createUserTest() {

		CreateUserRequest user = new CreateUserRequest();
		user.setName("Naruto");
		user.setAge(17);

		// Criação de cenário
		var response =

				given() // DADO
						.contentType(ContentType.JSON) // que eu tenho esse conteúdo
						.body(user) // com esse corpo
						.when() // QUANDO
						.post(apiURL) // faço uma requisição para esse endereço
						.then() // ENTÃO
						.extract().response(); // recebo essa resposta

		// Comparo se a resposta da requisição
		// acima retornou status 201
		assertEquals(201, response.statusCode());
		// e id não retornou NULL
		assertNotNull(response.jsonPath().getString("id"));
	}

	@Test
	@DisplayName("should return error when json is not valid")
	@Order(2)
	public void createUserValidationErrorTest() {

		var user = new CreateUserRequest();
		user.setAge(null);
		user.setName(null);

		var response = given().contentType(ContentType.JSON).body(user).when().post(apiURL).then().extract()
				.response();

		assertEquals(ResponseError.UNPROCESSABLE_ENTITY_STATUS, response.statusCode());
		// No response do Postman por exemplo pegarei o valor do campo message
		// e verei se é igual a String Validation Error
		assertEquals("Validation Error", response.jsonPath().getString("message"));

		// Capturando a lista de errors
		List<Map<String, String>> errors = response.jsonPath().getList("errors");

		// Vendo se as mensagens de erro não estão nulas
		// considerando que passamos idade e nome nulos
		assertNotNull(errors.get(0).get("message"));
		assertNotNull(errors.get(1).get("message"));

	}
	
	@Test
	@DisplayName("should list all users")
	@Order(3)
	public void listAllUsersTest() {
		
		given() // DADO
			.contentType(ContentType.JSON) // que eu tenho esse conteúdo
		.when() // QUANDO
			.get(apiURL) // faço uma requisição para esse endereço
		.then() // ENTÃO
			.statusCode(200)
			.body("size()",Matchers.is(1)); // recebo essa resposta de tamanho do Array
		
	}

}
