package edu.test.dao;

import javax.annotation.Resource;

import niuzhixiang.bean.mongo.LogonSession;
import niuzhixiang.test.dao.AbstractTransactionalJUnit4SpringContextTests;
import niuzhixiang.test.dao.ContextConfiguration;
import niuzhixiang.test.dao.Ignore;
import niuzhixiang.test.dao.MongoTemplate;
import niuzhixiang.test.dao.Query;
import niuzhixiang.test.dao.RunWith;
import niuzhixiang.test.dao.SpringJUnit4ClassRunner;
import niuzhixiang.test.dao.Test;
import niuzhixiang.test.dao.TransactionConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:ApplicationContext.xml"})
@TransactionConfiguration(defaultRollback = false)

public class MongoDaoTest  extends AbstractTransactionalJUnit4SpringContextTests {


	@Resource(name = "MongoTemplate")
	private MongoTemplate mongoTemplate;
	
	private LogonSession logonSession;
	
	//@Before
	public void setUp() {
		logonSession = new LogonSession();
		logonSession.setSessionid("aaa");
		mongoTemplate.save(logonSession, "logonsession");
	}
	
	@Test
	@Ignore
	public void testUpdate() {
		LogonSession result = mongoTemplate.findOne(new Query(Criteria.where("sessionid").is("aaa")), LogonSession.class, "logonsession");
		result.setUserid(6);
		mongoTemplate.save(result, "logonsession");
	}
}

