package com.allpago.rest.service;

import java.io.IOException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.allpago.exception.AllPagoException;
import com.allpago.rest.dao.OfficeDao;
import com.allpago.rest.entities.BestRoute;
import com.allpago.rest.entities.Office;
import com.google.maps.model.GeocodingResult;

/**
 * 
 * Service class that handles REST requests
 * 
 * @author aradhak
 * 
 */
@Component
@Path("/offices")
public class OfficeRestService {

	@Autowired
	private OfficeDao officeDao;
	@Autowired
	private Util utilities;
	private Logger logger = LoggerFactory.getLogger(OfficeRestService.class);

	/************************************ CREATE ************************************/

	/**
	 * Adds a new resource (office) from the given json format (All the fields
	 * are required at the DB level)
	 * 
	 * @param office
	 * @return
	 */
	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.TEXT_HTML })
	@Transactional
	public Response createOffice(Office office) {
		logger.debug("createOffice :{}",office);
		String message;
		int status;
		if (validateOfficeData(office)){
			status = 201; //
			officeDao.createOffice(office);
			message="A new office has been created";
		}else{
			status = 406; // Not acceptable
			message = "The information you provided is not sufficient to perform"
					+ " an INSERTION of the new office resource <br/>"
					+ " If you want to insert a new office please provide <strong>city</strong>, the <strong>country</strong>," +
					" the <strong>open_from</strong> and the <strong>open_until</strong> for the office resource";
		}
		logger.info(message);

		return Response.status(status)
				.entity(message).build();
	}


	/************************************ READ ************************************/
	/**
	 * Returns all resources (offices) from the database
	 * 
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Office> getOfficeList() throws JsonGenerationException, JsonMappingException, IOException {
		logger.debug("getOfficeList is invoked");
		List<Office> offices = officeDao.getOfficeList();		
		logger.info("No.of Offices  : ",offices.size());
		return offices; 
	}

	@GET
	@Path("open-now")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Office> getOpenedOfficeList() throws JsonGenerationException, JsonMappingException, IOException {
		logger.debug("getOpenedOfficeList is invoked");
		List<Office> officesOpen = new ArrayList<Office>();
		List<Office> offices = officeDao.getOpenedOfficeList();		
		for (Iterator<Office> iterator = offices.iterator(); iterator.hasNext();) {
			Office office = iterator.next();
			GeocodingResult[] results=utilities.getGeoCode(office);
			String timezone=null;
			try {
				timezone = utilities.getTimeZone(results);
			} catch (AllPagoException e1) {
               logger.error(e1.getMessage());
			}

			TimeZone tz = TimeZone.getTimeZone(timezone);
			Calendar calendar= Calendar.getInstance();
			calendar.setTimeZone(tz);
			int year       = calendar.get(Calendar.YEAR);
			int month      = calendar.get(Calendar.MONTH) + 1 ; 
			int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);	
			int hourOfDay  = calendar.get(Calendar.HOUR_OF_DAY); // 24 hour clock
			int minute     = calendar.get(Calendar.MINUTE);
			int second     = calendar.get(Calendar.SECOND);
			String yyyyMMdd=year+"-"+month+"-"+dayOfMonth;
			String current=yyyyMMdd+" "+hourOfDay+":"+minute+":"+second;
			String open=yyyyMMdd+" "+office.getOpen_from().trim();
			String until=yyyyMMdd+" "+office.getOpen_until().trim();

			SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				Date current_time=parser.parse(current);
				Date open_from=parser.parse(open);
				Date open_until=parser.parse(until);

				if(open_from.compareTo(current_time)<=0 && open_until.compareTo(current_time)>=0){
					officesOpen.add(office);
				}

			} catch (ParseException e) {
	               logger.error(e.getMessage());
			}

		}
		logger.info("No.of Offices opened : ",officesOpen.size());

		return officesOpen; 
	}

	@GET
	@Path("best-route")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response bestRouteToVisitAll() throws JsonGenerationException, JsonMappingException, IOException {
		logger.debug("bestRouteToVisitAll is invoked");

		List<Office> offices = officeDao.getOfficeList();		
		String message="";
		BestRoute bestRoute=new BestRoute();
		int status;
		if (hasOffice(offices)){

			String[] cities=new String[offices.size()];
			int count=0;
			for (Iterator<Office> iterator = offices.iterator(); iterator.hasNext();) {
				Office office = (Office) iterator.next();
				cities[count++]=office.getCity()+"+"+office.getCountry();
			}
			// Initialize with zero
			int distanceMatrix[][]=new int[cities.length][cities.length];
			for(int i=0; i<distanceMatrix.length;i++){
				for(int j=0; j<distanceMatrix.length;j++){
					distanceMatrix[i][j]=0;  
				}

			}

			for(int i=0; i<distanceMatrix.length;i++){
				for(int j=0; j<distanceMatrix.length;j++){
					if(i!=j && distanceMatrix[j][i]==0){
						try {
							distanceMatrix[i][j]=utilities.getDistance(cities[i],cities[j]);
						} catch (AllPagoException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						distanceMatrix[j][i]=distanceMatrix[i][j];
					}
				}

			}
			TSP tspNearestNeighbour = new TSP();
			List<Integer> bestRouteList=tspNearestNeighbour.tsp(distanceMatrix);


			message = "";
			for(int i=0; i<bestRouteList.size();i++){
				message+= cities[bestRouteList.get(i)];
				if(i!=bestRouteList.size()-1){//dont add comma for last element 
					message+=", ";
				}
			}
			message=message.replaceAll("\\+", "-");
			bestRoute.setCities(message);
			status = 201; //  
			logger.info("best routes: {}",bestRoute);
			return Response
					.status(status)
					.entity(bestRoute)
					.header("Access-Control-Allow-Headers", "X-extra-header")
					.allow("OPTIONS")
					.build();

		}else{
			status = 406; // Not acceptable
			message = "Atleast one office required";
			logger.info(message);
			return Response
					.status(status)
					.entity(message)					
					.build();
		}
	}




	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response findById(@PathParam("id") Long id) throws JsonGenerationException, JsonMappingException, IOException {
		logger.debug("findById is invoked");

		Office officeById = officeDao.getOfficeDetailsById(id);

		if (officeById != null) {
			logger.info("officeById : {}",officeById);
			return Response
					.status(200)
					.entity(officeById)
					.header("Access-Control-Allow-Headers", "X-extra-header")
					.allow("OPTIONS")
					.build();
		} else {
			logger.info("The office with the id {} does not exist",id);
			return Response
					.status(404)
					.entity("The office with the id " + id + " does not exist")					
					.build();
		}
	}	

	public void setOfficeDao(OfficeDao officeDao) {
		logger.debug("setOfficeDao");
		this.officeDao = officeDao;
	}
	private boolean validateOfficeData(Office office) {
		logger.debug("Office : {}",office);
		return office.getCountry() != null && office.getCity() != null && office.getOpen_from() !=null && office.getOpen_until()!=null;
	}

	private boolean hasOffice(List<Office> offices) {
		logger.info("No.of Offices:"+offices.size());
		return offices != null && offices.size() >0 ;
	}
}
