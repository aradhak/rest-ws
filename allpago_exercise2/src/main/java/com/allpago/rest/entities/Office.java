package com.allpago.rest.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * office entity 
 * 
 * @author aradhak
 *
 */
@SuppressWarnings("restriction")
@XmlRootElement
@Entity
@Table(name="offices")
public class Office implements Serializable {

	private static final long serialVersionUID = -8039686696076337053L;

	/** id of the office */
	@Id
	@GeneratedValue
	@Column(name="id")
	private Long id;
	
	/** city of the office */
	@Column(name="city")
	private String city;
		
	/** country of the office*/
	@Column(name="country")
	private String country;
	
	/** office open time */
	@Column(name="open_from")
	private String open_from;
	
	/** office open_until  */
	@Column(name="open_until")
	private String open_until; 

	public Office(){}
	
	public Office(String city, String country, String open_from,
			String open_until) {
		
		this.city = city;
		this.country = country;
		this.open_from = open_from;
		this.open_until = open_until;
		
	}
	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getOpen_until() {
		return open_until;
	}

	public void setOpen_until(String open_until) {
		this.open_until = open_until;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOpen_from() {
		return open_from;
	}

	public void setOpen_from(String open_from) {
		this.open_from = open_from;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "city="+city+" country="+country+" open_until="+open_until+" open_from="+open_from;
	}	
}
