package com.allpago.rest.dao;

import java.util.List;
import com.allpago.rest.entities.Office;
/**
 * 
 * @author Anandh
 *
 * This interface is used to define the list of API for an office 
 */
public interface OfficeDao {


	public Long createOffice(Office office);
	
	public List<Office> getOfficeList();
	
	public List<Office> getOpenedOfficeList();
	
	public Office getOfficeDetailsById(Long id);

	public Long deleteOfficeById(Long id);
}
