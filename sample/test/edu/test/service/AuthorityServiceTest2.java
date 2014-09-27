package edu.test.service;

import java.net.URI;
import java.net.URISyntaxException;

import niuzhixiang.bean.mongo.LogonSession;
import niuzhixiang.service.AuthorityService;
import niuzhixiang.test.service.AbstractTransactionalJUnit4SpringContextTests;
import niuzhixiang.test.service.Autowired;
import niuzhixiang.test.service.ContextConfiguration;
import niuzhixiang.test.service.MongoTemplate;
import niuzhixiang.test.service.Response;
import niuzhixiang.test.service.RunWith;
import niuzhixiang.test.service.SpringJUnit4ClassRunner;
import niuzhixiang.test.service.Test;
import niuzhixiang.test.service.TransactionConfiguration;
import niuzhixiang.test.service.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:ApplicationContext.xml"})
@TransactionConfiguration(defaultRollback = true)
public class AuthorityServiceTest2 extends
AbstractTransactionalJUnit4SpringContextTests {

@Autowired
private AuthorityService authorityService;
@Autowired
private MongoTemplate mongoTemplate;

@Test
@Transactional
public void testDoRegister() {
Response response = authorityService.doRegister("test", "test@test.com", "test", "1");
Assert.assertEquals(200, response.getStatus());
}

@Test
@Transactional
public void testLogonAndLogout() {
Response response = authorityService.logon("nzx", "nzx", "pc");
Assert.assertEquals("response code of logon must be 200", 200, response.getStatus());
Assert.assertTrue(mongoTemplate.find(Query.query(Criteria.where("managerid").is(3)), LogonSession.class, "logonsession").size() > 0);
Assert.assertNotNull("there must be a cookie named \"managerid\"", response.getCookies().get("managerid"));
Assert.assertNotNull("there must be a cookie named \"sessionid\"", response.getCookies().get("sessionid"));
try {
	Assert.assertEquals(new URI("/user/3"), response.getLocation());
} catch (URISyntaxException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
Response response2 = authorityService.logout(3);
Assert.assertEquals("response code of logout must be 302", 302, response2.getStatus());
Assert.assertTrue(mongoTemplate.find(Query.query(Criteria.where("userid").is(3)), LogonSession.class, "logonsession").size() == 0);	
}
}

