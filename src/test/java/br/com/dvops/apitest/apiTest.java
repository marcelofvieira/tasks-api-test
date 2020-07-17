package br.com.dvops.apitest;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class apiTest {
	
	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "http://host.docker.internal:8001/tasks-backend";
	}
	

	@Test
	public void returnAlltasks() {
		
		RestAssured
		.given()
		.when()
			.get("/todo")
		.then()
			.statusCode(200);
	
	}

	
	@Test
	public void addTask() {
		
		String json = "{\"task\":\"Teste 003\",\"dueDate\":\"2020-10-10\"}";
		
		RestAssured
		.given()
			.body(json)
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
			.statusCode(201);
		
	}
	
	@Test
	public void addInvalidTest() {
		
		String json = "{\"task\":\"Teste 003\",\"dueDate\":\"2010-10-10\"}";
		
		RestAssured
		.given()
			.body(json)
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
			.log().all()
			.statusCode(400)
			.body("message", CoreMatchers.is("Due date must not be in past"));
		
	}
	
}
