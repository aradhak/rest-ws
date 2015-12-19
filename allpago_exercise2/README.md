offices-rest
========================================
Prerequisites:
-------------------
- MySQL 5.5 or 5.6 
- Eclipse
- JDK 1.7 (if you want to use Jetty 9 with the jetty-maven-plugin from project)
- Maven

Install
---------
Create db schema by executing AllpagoDB.sql(create and insert default records) against the MySQL database

DB USN/PWD
------------
 This project will work for the db username/password as root/trimble123 by default.
 
 Please change the db username and password in the following files as per your DB usn/pwd
   1.  \allpago_exercise2\src\main\resources\config\jetty9.xml
   2.  \allpago_exercise2\src\main\webapp\META-INF\context.xml
   3.  \allpago_exercise2\src\test\resources\db.properties

Building project
---------------
Go to directory \allpago_exercise2
and run the following command in command prompt

  >> mvn clean install
  
  
Deploying App
---------------
Once build is completed, deploy the offices-rest-0.0.1-SNAPSHOT.war file located at \allpago_exercise2\target\  in Tomcat\webapps

Run The Tomcat
--------------
Run tomcat\bin\startup.sh

Google Key
----------
 Update your own GOOGLE_KEY on file OfficeConstants.java(com.allpago.rest.constants)  to access google apis

Execute the below API on any rest client.

Please find below the API Design((Paths,methods and input/output parameters)
-------------------------------------------------------------------------------

1. Create office:-

    URL:  http://localhost:8080/offices-rest-0.0.1-SNAPSHOT/offices/     METHOD: POST    CONTENT-TYPE: Application/json
    
   Sample response:
     {
	"city":"Bangalore",
	"country":"India",
	"open_from":"12:30:00",
	"open_until":"22:30:00"
     }
     
2.  List offices:-
    
        URL:  http://localhost:8080/offices-rest-0.0.1-SNAPSHOT/offices/     METHOD: GET   
        
        Sample response:
        
        [{"id":1,"city":"Chennai","country":"India","open_from":"10:00:00","open_until":"20:00:00"},{"id":2,"city":"Delhi","country":"India","open_from":"10:00:00","open_until":"23:00:00"},{"id":3,"city":"Mumbai","country":"India","open_from":"10:00:00","open_until":"23:00:00"},{"id":4,"city":"Berlin","country":"Germany","open_from":"10:00:00","open_until":"20:00:00"},{"id":5,"city":"London","country":"UK","open_from":"10:00:00","open_until":"23:00:00"},{"id":6,"city":"Los_Angeles","country":"America","open_from":"10:00:00","open_until":"20:00:00"},{"id":7,"city":"Bangalore","country":"India","open_from":"12:30:00","open_until":"22:30:00"}] 
        
3.  List offices open now:-

        URL:  http://localhost:8080/offices-rest-0.0.1-SNAPSHOT/offices/open-now    METHOD: GET  
        
        Sample Response: [{"id":5,"city":"London","country":"UK","open_from":"10:00:00","open_until":"23:00:00"}]
        
4.  See details of an office:-

        URL:  http://localhost:8080/offices-rest-0.0.1-SNAPSHOT/offices/1     METHOD: GET  
        
       Sample Response:  {
			id: 1
			city: "Chennai"
			country: "India"
			open_from: "10:00:00"
			open_until: "20:00:00"
		}
        
5.  Best route (shorter) to visit all the offices:-

        URL:  http://localhost:8080/offices-rest-0.0.1-SNAPSHOT/offices/best-route    METHOD: GET  
 
        Sample Respose:
           
           {
			cities: "Chennai-India, Bangalore-India, Mumbai-India, Delhi-India, Berlin-Germany, London-UK"
		}


