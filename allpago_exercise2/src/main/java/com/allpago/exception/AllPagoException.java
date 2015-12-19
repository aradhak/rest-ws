package com.allpago.exception;
/**
 * 
 * @author Anandh
 * 
 * Class GoEuroException is a user defined exception class. Its used for Exception handling 
 *
 */
public class AllPagoException extends Exception{

	private static final long serialVersionUID = 1L;

	public AllPagoException(String errormessage){
      super(errormessage);
	}
}
