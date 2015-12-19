package com.allpago.rest.dao.impl;

import java.util.List;



import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.allpago.rest.dao.OfficeDao;
import com.allpago.rest.entities.Office;

/**
 * 
 * @author Anandh
 * 
 * This class used to implements all the apis for an office 
 */
public class OfficeDaoJPA2Impl implements OfficeDao {

	private static final Logger logger = LoggerFactory.getLogger(OfficeDaoJPA2Impl.class);

	@PersistenceContext(unitName="demoRestPersistence")
	private EntityManager entityManager;

	public List<Office> getOfficeList() {
		logger.debug("getOfficeList is invoked");
		String qlString = "SELECT o FROM Office o";
		TypedQuery<Office> query = entityManager.createQuery(qlString, Office.class);		

		return query.getResultList();
	}

	public List<Office> getOpenedOfficeList() {
		logger.debug("getOpenedOfficeList is invoked");
		String qlString = "SELECT o FROM Office o";
		TypedQuery<Office> query = entityManager.createQuery(qlString, Office.class);		
		return query.getResultList();
	}

   public Long createOffice(Office office) {
		logger.debug("createOffice is invoked");
		entityManager.persist(office);
		entityManager.flush();//force insert to receive the id of the office
		logger.info("office id:{}",office.getId());
		return office.getId();
	}

	public Office getOfficeDetailsById(Long id) {
		logger.debug("getOfficeDetailsById is invoked");
		try {
			String qlString = "SELECT o FROM Office o WHERE o.id = ?1";
			TypedQuery<Office> query = entityManager.createQuery(qlString, Office.class);		
			query.setParameter(1, id);

			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
   
	public Long deleteOfficeById(Long id) {
		logger.debug("deleteOfficeById is invoked");
		Office office = entityManager.find(Office.class, id);
		entityManager.remove(office);
		return 1L;
	}


}
