package com.allpago.rest.service;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.test.JerseyTest;
import org.junit.Ignore;
import org.junit.Test;
/**
 * 
 * @author aradhak
 * 
 */
public class TestOfficeRestServiceWithJerseyTestFramework extends JerseyTest{
	
    @Override
    protected Application configure() {
        // Enable logging.
        enable(org.glassfish.jersey.test.TestProperties.LOG_TRAFFIC);
        enable(org.glassfish.jersey.test.TestProperties.DUMP_ENTITY);
        
        return new MyOfficeApplication()
        			.property("contextConfigLocation", "classpath:test-applicationContext.xml");
    }
 	

    @Ignore @Test
    public void testJerseyResource() {
        // Make a better test method than simply outputting the result.
        System.out.println(target("spring-hello").request().get(String.class));
    }
    

     
}
