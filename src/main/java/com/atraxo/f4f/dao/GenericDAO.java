package com.atraxo.f4f.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;

import com.atraxo.f4f.model.commons.AbstractTimestamp;
import com.atraxo.f4f.util.CodeConstants;

public abstract class GenericDAO<T> implements Serializable {
	private static final long serialVersionUID = 2490727278203645433L;
	private static final Logger LOGGER = Logger.getLogger(GenericDAO.class);
	
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("Food4Fuel");
	protected transient EntityManager em;

	private Class<T> entityClass;

	public GenericDAO(Class<T> entityClass) {
		this.entityClass = entityClass;
	}
	
	public EntityManager getEm() {
		return em;
	}
	
	public void setEm(EntityManager em) {
		this.em = em;
	}
	
	public void beginTransaction() {
		em = emf.createEntityManager();
		em.getTransaction().begin();
	}

	public void commit() {
		if ( em.getTransaction().isActive() ){
			em.getTransaction().commit();
		}
	}

	public void rollback() {
		em.getTransaction().rollback();
	}

	private void closeTransaction() {
		em.close();
	}

	public void commitAndCloseTransaction() {
		try {
			commit();
		} catch (Exception e) {
			LOGGER.error("Coul not commit and close transaction ", e);
			throw e;
		}
		finally{
			closeTransaction();
		}
	}

	public void flush() {
		em.flush();
	}

	public void joinTransaction() {
		em = emf.createEntityManager();
		em.joinTransaction();
	}

	public void save(T entity) {
		if ( em == null ) {
			beginTransaction();
		}
		
		em.persist(entity);
	}

	public void delete(T entity) {
		T entityToBeRemoved = em.merge(entity);
		
		em.remove(entityToBeRemoved);
	}

	public T update(T entity) {
		return  em.merge(entity);
	}

	public T find(int entityID) {
		return em.find(entityClass, entityID);
	}

	public T findReferenceOnly(int entityID) {
		return em.getReference(entityClass, entityID); 
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<T> findAll() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery cq = cb.createQuery();
		Root<T> c = cq.from(entityClass);
		cq.select(c);

		try {
			if ( entityClass.asSubclass( AbstractTimestamp.class) != null ) {
				//order DESCENDENT by creation date
				cq.orderBy(cb.desc(c.get("timestampCreate"))); 
			}
		}
		catch (ClassCastException e) {
			LOGGER.info("ClassCastException", e);
		}

		return em.createQuery(cq).getResultList(); 
	}

	@SuppressWarnings("unchecked")
	protected T findOneResult(String namedQuery, Map<String, Object> parameters) {
		T result = null;

		try {
			Query query = em.createNamedQuery(namedQuery);

			if (parameters != null && !parameters.isEmpty()) {
				populateQueryParameters(query, parameters);
			}

			result = (T) query.getSingleResult();

		} catch (NoResultException e) {
			LOGGER.info("No results found", e);
		} catch (Exception e) {
			LOGGER.error("Error while running query: ", e);
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	protected List<T> findMultipleResults(String namedQuery, Map<String, Object> parameters) {
		List<T> result = new ArrayList<>();

		try {
			Query query = em.createNamedQuery(namedQuery);

			if (parameters != null && !parameters.isEmpty()) {
				populateQueryParameters(query, parameters);
			}

			result = query.getResultList();

		} catch (NoResultException e) {
			LOGGER.info("No result found for named query: ", e);
		} catch (Exception e) {
			LOGGER.error("Error while running query: ", e);
		}

		return result;
	}

	protected void populateQueryParameters(Query query, Map<String, Object> parameters) {
		for (Entry<String, Object> entry : parameters.entrySet()) {

			String key = entry.getKey();
			Object value = entry.getValue();

			if ( key.equals(CodeConstants.QUERY_MAX_RESULTS) ) {
				query.setMaxResults((Integer)value);
			}
			else{
				query.setParameter(key, value);
			}
		}
	}
	
}
