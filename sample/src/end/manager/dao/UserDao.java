package end.manager.dao;

import java.util.List;

import javax.annotation.Resource;

import java.util.List;

import javax.annotation.Resource;

import edu.manager.bean.basic.UserBean;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;



//使用注解来驱动事务
@Transactional(value = "TransactionManager")
public class UserDao {

	@Resource(name = "HibernateSessionFactory")
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@SuppressWarnings("unchecked")
	public List<UserBean> readAll() {
		Query query = sessionFactory.getCurrentSession().createQuery("from UserBean as userBean");
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<UserBean> readSeveral(int start, int size) {
		Query query = sessionFactory.getCurrentSession().createQuery("from UserBean as userBean");
		query.setFirstResult(start);
		query.setMaxResults(size);
		return query.list();
	}
	
}
