package edu.test.dao;

import niuzhixiang.dao.UserDao;
import niuzhixiang.test.dao.AbstractTransactionalJUnit4SpringContextTests;
import niuzhixiang.test.dao.Autowired;
import niuzhixiang.test.dao.ContextConfiguration;
import niuzhixiang.test.dao.RunWith;
import niuzhixiang.test.dao.SpringJUnit4ClassRunner;
import niuzhixiang.test.dao.Test;
import niuzhixiang.test.dao.TransactionConfiguration;
import niuzhixiang.test.dao.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:ApplicationContext.xml"})
@TransactionConfiguration(defaultRollback = true)

public class ManagerDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private ManagerDao managerDao;
	
	@Test
	@Transactional
	public void testSave() {
		String result = managerDao.save("test_managername", "test_email@test.com", "test_password", 1);
		assertEquals("the return value of method save() must be \"register_success\"", "register_success", result);
	}
	
	@Test
	@Transactional
	public void testGetManagerByProperty() {
		managerDao.save("test_managername", "test_email@test.com", "test_password", 1);
		assertEquals("result must be test_managername", "test_managername", managerDao.getManagerByProperty("managername", "test_managername").getManagername());
	}
}

