package com.allpago.rest.service;

import static com.allpago.rest.constants.OfficeConstants.DISTANCE_MATRIX_ENDPOINT;
import static com.allpago.rest.constants.OfficeConstants.GOOGLE_KEY;
import static com.allpago.rest.constants.OfficeConstants.LANGUAGE;
import static com.allpago.rest.constants.OfficeConstants.STATUS_OK;
import static com.allpago.rest.constants.OfficeConstants.TIMEZONE_ENDPOINT;
import static com.allpago.rest.constants.OfficeConstants.TRAVEL_MODE;

import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.allpago.exception.AllPagoException;
import com.allpago.rest.entities.Office;
import com.allpago.rest.model.DistanceMatrix;
import com.allpago.rest.model.TimeZone;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
/**
 * 
 * @author aradhak
 * 
 */
public class Util {
	private HttpClient httpClient = HttpClientBuilder.create().build();
    private ObjectMapper objectMapper = new ObjectMapper();
	private Logger logger = LoggerFactory.getLogger(Util.class);
	
	public String getTimeZone(GeocodingResult[] results) throws AllPagoException{
	    logger.debug("lat={},lng={}",results[0].geometry.location.lat,results[0].geometry.location.lng);
		String URL = TIMEZONE_ENDPOINT+"location="+results[0].geometry.location.lat+","+results[0].geometry.location.lng+
				"&timestamp="+(System.currentTimeMillis()/1000)+"&key="+GOOGLE_KEY;
   	HttpGet httpGet = new HttpGet(URL);
		HttpResponse httpResponse=null;
		try {
			httpResponse = httpClient.execute(httpGet);
		} catch (Exception e) {
		    logger.error(e.getMessage());
			throw new AllPagoException(e.getMessage());
		}
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		TimeZone timeZone = null; 
		if(statusCode==200){
			InputStream inputStream=null;
			try {
				inputStream = httpResponse.getEntity().getContent();
				timeZone = objectMapper.readValue(inputStream, TimeZone.class);
			} catch (Exception e) {
			    logger.error(e.getMessage());
				throw new AllPagoException(e.getMessage());
			}

		}else{
		    logger.error("Invalid status code: {}",statusCode);
			throw new AllPagoException("No response found for timezone");
		}
	    logger.info("Timezone : {}",timeZone.getTimeZoneId());
		return timeZone.getTimeZoneId();
	}
	
	public int getDistance(String city1, String city2) throws AllPagoException{
	    logger.debug("city1={},city2={}",city1,city2);
        String URL=DISTANCE_MATRIX_ENDPOINT+"origins="+city1+"&destinations="+city2+"&mode="+TRAVEL_MODE+"&language="+LANGUAGE+"&key="+GOOGLE_KEY;
        HttpGet httpGet = new HttpGet(URL);
		HttpResponse httpResponse=null;
		try {
			httpResponse = httpClient.execute(httpGet);
		} catch (Exception e) {
		    logger.error(e.getMessage());
			throw new AllPagoException(e.getMessage());
		}
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		DistanceMatrix distanceMatrix = null; 
		if(statusCode==200){
			InputStream inputStream=null;
			try {
				inputStream = httpResponse.getEntity().getContent();
				distanceMatrix = objectMapper.readValue(inputStream, DistanceMatrix.class);
			} catch (Exception e) {
			    logger.error(e.getMessage());
				throw new AllPagoException(e.getMessage());
			}

		}else{
		    logger.error("Invalid status code: {}",statusCode);
			throw new AllPagoException("No response found for timezone");
		}
		int distance=0;
		if(distanceMatrix.getStatus().equals(STATUS_OK)){
			if(distanceMatrix.getRows()[0].getElements()[0].getStatus().equals(STATUS_OK)){
				distance=distanceMatrix.getRows()[0].getElements()[0].getDistance().getValue();
			}
			
		}
	    logger.info("distance : {}",distance);
        return distance;
	} 

	
	public GeocodingResult[] getGeoCode(Office office){
	    logger.info("getGeoCode for city {}, country ={}",office.getCity(),office.getCountry());
	    GeoApiContext context = new GeoApiContext().setApiKey(GOOGLE_KEY);
		GeocodingResult[] results=null;
		try {
			results = GeocodingApi.geocode(context,office.getCity()+" "+office.getCountry()).await();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return results;
	}

}
