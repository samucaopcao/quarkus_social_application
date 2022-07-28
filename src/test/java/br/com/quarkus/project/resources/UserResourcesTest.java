package br.com.quarkus.project.resources;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.quarkus.project.dto.CreateUserRequest;
import br.com.quarkus.project.resources.exceptions.ResponseError;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
class UserResourcesTest {

	@Test
	@DisplayName("should create an user sucessfully")
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
						.post("/users") // faço uma requisição para esse endereço
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
	public void createUserValidationErrorTest() {

		var user = new CreateUserRequest();
		user.setAge(null);
		user.setName(null);

		var response = given().contentType(ContentType.JSON).body(user).when().post("/users").then().extract()
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

}
