package com.allpago.rest.entities;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**

 * @author aradhak
 *
 */
@SuppressWarnings("restriction")
@XmlRootElement
public class BestRoute implements Serializable {

	private static final long serialVersionUID = -8039686696076337053L;

	/** city of the office */
	private String cities;


	public String getCities() {
		return cities;
	}

	public void setCities(String cities) {
		this.cities = cities;
	}

	public BestRoute(){}
	
	public BestRoute(String cities) {
		
		this.cities = cities;
	}

	public String toString(){
		return cities;
	}
}
