package end.manager.dao;

import java.util.List;

import javax.annotation.Resource;

import edu.manager.bean.basic.EnterpriseBean;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

@Transactional(value = "TransactionManager")


public class EnterpriseDao {

	@Resource(name = "HibernateSessionFactory")
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@SuppressWarnings("unchecked")
	public List<EnterpriseBean> readAll() {
		Query query = getSessionFactory().getCurrentSession().createQuery("from EnterpriseBean as enterpriseBean");
		return query.list();
	}

}

