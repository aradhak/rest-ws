package com.allpago.rest.service;
import static com.jayway.restassured.RestAssured.expect;

import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;
/**
 * 
 * @author aradhak
 * 
 */
public class OfficeRestServiceTestWithRestAssured {

	@Test
	public void testOfficeFetchSuccessful(){
		expect().
			body("id", equalTo("2")).
			body("city", equalTo("Delhi")).
		when().
			get("/offices-rest-0.0.1-SNAPSHOT/offices/2");
	}
}
