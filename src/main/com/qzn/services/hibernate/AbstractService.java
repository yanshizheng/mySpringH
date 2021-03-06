package com.qzn.services.hibernate;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.service.spi.ServiceException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.qzn.daos.Dao;
import com.qzn.services.Service;

public abstract class AbstractService<T, ID extends Serializable> implements Service<T, ID> {
	/**
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public abstract Dao<T, ID> getDao() throws ServiceException;

	protected HttpServletRequest getRequest() {
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		return ((ServletRequestAttributes) ra).getRequest();
	}

	protected HttpSession getSession() {
		HttpServletRequest request = getRequest();
		return request.getSession();
	}

	public Class<T> getModelClass() throws ServiceException {
		return getDao().getModelClass();
	}

	@Transactional(readOnly = true, rollbackFor = ServiceException.class)
	public T findById(ID id) throws ServiceException {
		return getDao().get(id);
	}

	@Transactional(rollbackFor = ServiceException.class)
	public void deleteById(ID id) throws ServiceException {
		T t = getDao().get(id);
		getDao().delete(t);
	}

	@Transactional(rollbackFor = ServiceException.class)
	public ID save(T t) throws ServiceException {
		return getDao().save(t);
	}

	@Transactional(rollbackFor = ServiceException.class)
	public void update(T t) throws ServiceException {
		getDao().update(t);

	}

	@Transactional(readOnly = true, rollbackFor = ServiceException.class)
	public List<T> loadByProperty(String property, Object value) throws ServiceException {
		return getDao().getListByProperty(property, value);
	}

	@Transactional(readOnly = true, rollbackFor = ServiceException.class)
	public List<T> loadByProperty(Map<String, Object> map) throws ServiceException {
		return getDao().getListByProperty(null, map);
	}

	@Transactional(rollbackFor = ServiceException.class)
	public void deleteAll() throws ServiceException {
		getDao().deleteAll();
	}

	@Transactional(rollbackFor = ServiceException.class)
	public void deleteByIds(ID[] ids) throws ServiceException {
		// List<T> entities = getDao().loadByIds(ids);
		// getDao().deleteAll(entities);
		getDao().deleteByIds(ids);
	}

	@Transactional(readOnly = true, rollbackFor = ServiceException.class)
	public List<T> loadAll() throws ServiceException {
		return getDao().findAll();
	}

	@Transactional(readOnly = true, rollbackFor = ServiceException.class)
	public List<T> loadAllAscOrderBy(String order) throws ServiceException {
		return getDao().findAllAscOrderBy(order);
	}

	@Transactional(readOnly = true, rollbackFor = ServiceException.class)
	public List<T> loadAllDescOrderBy(String order) throws ServiceException {
		return getDao().findAllDescOrderBy(order);
	}

	@Transactional(readOnly = true, rollbackFor = ServiceException.class)
	public long getLastInsertId() throws ServiceException {
		return getDao().getLastInsertId();
	}

	@Transactional(readOnly = true, rollbackFor = ServiceException.class)
	public int getCount() throws ServiceException {
		return getDao().getUniqueCount();
	}

	@Transactional(readOnly = true, rollbackFor = ServiceException.class)
	public List<T> getOffsetLimitOrderListByProperty(String property, Object value, String orderProperty, String order,
			int limit, int offset) throws ServiceException {
		return getDao().getOffsetLimitOrderListByProperty(property, value, orderProperty, order, limit, offset);
	}

	@Transactional(readOnly = true, rollbackFor = ServiceException.class)
	public List<T> findTopByCriteria(final DetachedCriteria detachedCriteria, final int top, final Order[] orders)
			throws ServiceException {
		return getDao().findTopByCriteria(detachedCriteria, top, orders);
	}

	@Transactional(readOnly = true, rollbackFor = ServiceException.class)
	public List<T> findAllByCriteria(final DetachedCriteria detachedCriteria) throws ServiceException {
		return getDao().findAllByCriteria(detachedCriteria);
	}

	@Transactional(readOnly = true, rollbackFor = ServiceException.class)
	public int getCountByCriteria(final DetachedCriteria detachedCriteria) throws ServiceException {
		return getDao().getCountByCriteria(detachedCriteria);
	}

	@Transactional(readOnly = true, rollbackFor = ServiceException.class)
	public T findByProperty(String property, Object value) throws ServiceException {
		return getDao().findByProperty(property, value);
	}

	@Transactional(readOnly = true, rollbackFor = ServiceException.class)
	public List<T> getListByProperty(String property, Object value) throws ServiceException {
		return getDao().getListByProperty(property, value);
	}

	@Transactional(readOnly = true, rollbackFor = ServiceException.class)
	public List<T> getListByPropertys(String property1, Object value1, String property2, Object value2)
			throws ServiceException {
		return getDao().getListByPropertys(property1, value1, property2, value2);
	}

	@Transactional(readOnly = true, rollbackFor = ServiceException.class)
	public Object max(String property) throws ServiceException {
		return getDao().max(property);
	}

	@Transactional(readOnly = true, rollbackFor = ServiceException.class)
	public List<T> getOffsetLimitOrderList(String orderProperty, String order, int limit, int offset)
			throws ServiceException {
		return getDao().getOffsetLimitOrderList(orderProperty, order, limit, offset);
	}

}
