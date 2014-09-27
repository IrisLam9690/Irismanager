package end.manager.dao;

import javax.annotation.Resource;

import edu.manager.bean.basic.DataBean;

import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactiona

@Transactional(value = "TransactionManager")

public class DataDaoManager {


	@Resource(name = "HibernateSessionFactory")
	private SessionFactory sessionFactory;
	
	public void save(DataBeanManager data) {
		sessionFactory.getCurrentSession().save(data);
	}
}

