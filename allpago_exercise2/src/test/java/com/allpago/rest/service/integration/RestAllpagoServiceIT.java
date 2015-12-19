package com.allpago.rest.service.integration;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.junit.Assert;
import org.junit.Test;

import com.allpago.exception.AllPagoException;
import com.allpago.rest.entities.BestRoute;
import com.allpago.rest.entities.Office;
import com.allpago.rest.service.Util;
import com.google.maps.model.GeocodingResult;

public class RestAllpagoServiceIT {
	private Util utilities= new Util();
	

    @Test
   public void testDistance(){
   	int distance=0;
		try {
			distance = utilities.getDistance("Chennai+India", "Delhi+India");
		} catch (AllPagoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   	Assert.assertEquals(2190, distance);
   }
    
    @Test
   public void testgetGeoCode(){
   	 
   	Office office=new Office("Chennai","India","",""); 
   	GeocodingResult[] results=utilities.getGeoCode(office);
   	Assert.assertNotNull(results);
   }
    
    @Test
   public void testTimeZone() throws IOException{
   	 
   	Office office=new Office("Chennai","India","",""); 
   	GeocodingResult[] results=utilities.getGeoCode(office);
   	String timezone=null;
	try {
		timezone = utilities.getTimeZone(results);
	} catch (AllPagoException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   	Assert.assertEquals(timezone, "Asia/Calcutta");
   }

    
	@Test
	public void testGetOffices() throws JsonGenerationException,
			JsonMappingException, IOException {

		ClientConfig clientConfig = new ClientConfig();
		clientConfig.register(JacksonFeature.class);

		Client client = ClientBuilder.newClient(clientConfig);

		WebTarget webTarget = client
				.target("http://localhost:8080/offices-rest-0.0.1-SNAPSHOT/offices/");

		Builder request = webTarget.request();
		request.header("Content-type", MediaType.APPLICATION_JSON);

		Response response = request.get();
		Assert.assertTrue(response.getStatus() == 200);

		List<Office> offices = response
				.readEntity(new GenericType<List<Office>>() {
				});

		ObjectMapper mapper = new ObjectMapper();
		System.out.print(mapper.writerWithDefaultPrettyPrinter()
				.writeValueAsString(offices));

		Assert.assertTrue("At least one office is present",
				offices.size() > 0);
	}

	@Test
	public void testGetOffice() throws JsonGenerationException,
			JsonMappingException, IOException {

		ClientConfig clientConfig = new ClientConfig();
		clientConfig.register(JacksonFeature.class);

		Client client = ClientBuilder.newClient(clientConfig);

		WebTarget webTarget = client
				.target("http://localhost:8080/offices-rest-0.0.1-SNAPSHOT/offices/1");

		Builder request = webTarget.request(MediaType.APPLICATION_JSON);

		Response response = request.get();
		Assert.assertTrue(response.getStatus() == 200);

		Office office = response.readEntity(Office.class);

		ObjectMapper mapper = new ObjectMapper();
		System.out
				.print("Received office from database *************************** "
						+ mapper.writerWithDefaultPrettyPrinter()
								.writeValueAsString(office));

	}
	
	@Test
	public void testGetOfficesOpenedNow() throws JsonGenerationException,
			JsonMappingException, IOException {

		ClientConfig clientConfig = new ClientConfig();
		clientConfig.register(JacksonFeature.class);

		Client client = ClientBuilder.newClient(clientConfig);

		WebTarget webTarget = client
				.target("http://localhost:8080/offices-rest-0.0.1-SNAPSHOT/offices/open-now");

		Builder request = webTarget.request();
		request.header("Content-type", MediaType.APPLICATION_JSON);

		Response response = request.get();
		Assert.assertTrue(response.getStatus() == 200);

		List<Office> offices = response
				.readEntity(new GenericType<List<Office>>() {
				});

		ObjectMapper mapper = new ObjectMapper();
		System.out.print(mapper.writerWithDefaultPrettyPrinter()
				.writeValueAsString(offices));

		Assert.assertTrue("At least one office is present",
				offices.size() > 0);
	}
	
	@Test
	public void testBestRouteToVisitAll() throws JsonGenerationException,
			JsonMappingException, IOException {

		ClientConfig clientConfig = new ClientConfig();
		clientConfig.register(JacksonFeature.class);

		Client client = ClientBuilder.newClient(clientConfig);

		WebTarget webTarget = client
				.target("http://localhost:8080/offices-rest-0.0.1-SNAPSHOT/offices/best-route");

		Builder request = webTarget.request(MediaType.APPLICATION_JSON);

		Response response = request.get();
		Assert.assertTrue(response.getStatus() == 200);

		BestRoute bestRoute = response.readEntity(BestRoute.class);

		ObjectMapper mapper = new ObjectMapper();
		System.out
				.print("Received office from database *************************** "
						+ mapper.writerWithDefaultPrettyPrinter()
								.writeValueAsString(bestRoute));

	}

}
