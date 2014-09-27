package edu.manager.dao;


import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import edu.manager.bean.basic.EnterpriseBean;
import edu.manager.bean.basic.ManagerBean;
import edu.manager.bean.mongo.LogonSessionManager;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.transaction.annotation.Transactional;

//利用注解来驱动事务
@Transactional(value = "TransactionManager")
public class ManagerDao {

	public ManagerrDao() {
		super();
		// TODO Auto-generated constructor stub	
	}
	
	//利用注解来注入
	@Resource(name = "HibernateSessionFactory")
	private SessionFactory sessionFactory;
	
	@Resource(name = "MongoTemplate")
	private MongoTemplate mongoTemplate;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	
	
	public String save(String managername, String password, int id) {
		ManagerBean managerBean = new ManagerBean();
		managerBean.setManagername(managername);
		managerBean.setPassword(password);
		managerBean.setEnterpriseBean((EnterpriseBean)(sessionFactory.getCurrentSession().load(EnterpriseBean.class, enterpriseid)));		
		//创建管理员
		if (ManagerExists(managerBean)) {
			System.out.println("manager exists!");
			return "manager_exists";
		} else {
			sessionFactory.getCurrentSession().save(ManagerBean);
			System.out.println("manager created successfully!");
			return "register_success";
		}
		
	}
	
	//好像可以不要的样子
	private boolean managerExists(ManagerBean managerBean){
		Query query = sessionFactory.getCurrentSession().createQuery("from ManagerBean as managerbean where managerbean.email = :email or managerbean.managername = :managername");
		
		query.setParameter("Managername", managerBean.getManagername());		
		if (query.list().size() == 0) {			
			return false;
		} else {			
			return true;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public ManagerBean checkManager(String managername, String password) {
		Query query = sessionFactory.getCurrentSession().createQuery("from ManagerBean as managerbean where managerbean.managername = :managername and managerbean.password = :password");
		query.setParameter("managername", managername);
		query.setParameter("password", password);
		List<ManagerBean> list = query.list();
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}
	
	/**
	 * 创建登录会话
	 * @param userBean
	 * @return 登录会话LogonSession（持久化到mongodb中）
	 */
	public LogonSession createLogonSession(ManagerBean managerBean) {
		String sessionid = UUID.randomUUID().toString();
		getMongoTemplate().insert(new LogonSession(sessionid, managerBean.getId()), "logonsession");
		return getMongoTemplate().findOne(new org.springframework.data.mongodb.core.query.Query(Criteria.where("userid").is(managerBean.getId())), LogonSession.class, "logonsession");
	}
	
	/**
	 * 注销登录会话
	 * @param userBean
	 */
	public void deleteLogonSession(int id) {
		getMongoTemplate().remove(new org.springframework.data.mongodb.core.query.Query(Criteria.where("id").is(id)), "logonsession");
	}
	
	
	@SuppressWarnings("unchecked")
	
	public ManagerBean getManagerById(int id) {
		Query query = sessionFactory.getCurrentSession().createQuery("from ManagerBean as managerbean where managerbean.id = :id");
		query.setParameter("id", id);
		List<ManagerBean> list = query.list();
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public ManagerBean getManagerByProperty(String property, Object value) {
		Query query = getSessionFactory().getCurrentSession().createQuery("from ManagerBean as managerBean where managerBean." + property + " = :value");
		query.setParameter("value", value);
		List<ManagerBean> managerList = query.list();
		if (managerList.size() > 0) {
			return managerList.get(0);
		} else {
			return null;
		}
		
	}
}
