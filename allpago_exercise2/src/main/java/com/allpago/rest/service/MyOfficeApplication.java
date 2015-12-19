package com.allpago.rest.service;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.allpago.rest.util.CORSResponseFilter;
import com.allpago.rest.util.LoggingResponseFilter;

/**
 * 
 * @author aradhak
 * 
 */
public class MyOfficeApplication extends ResourceConfig {
	private Logger logger = LoggerFactory.getLogger(MyOfficeApplication.class);

    /**
	* Register JAX-RS application components.
	*/	
	public MyOfficeApplication(){
        logger.debug("MyOfficeApplication is invoked");
		register(RequestContextFilter.class);
		register(OfficeRestService.class);
		register(JacksonFeature.class);	
		register(LoggingResponseFilter.class);
		register(CORSResponseFilter.class);
	}
}
