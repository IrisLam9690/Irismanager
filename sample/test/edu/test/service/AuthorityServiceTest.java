package edu.test.service;

import java.io.IOException;

import niuzhixiang.test.service.Before;
import niuzhixiang.test.service.CXFServlet;
import niuzhixiang.test.service.Ignore;
import niuzhixiang.test.service.MockHttpServletRequest;
import niuzhixiang.test.service.MockHttpServletResponse;
import niuzhixiang.test.service.ServletException;
import niuzhixiang.test.service.Test;

public class AuthorityServiceTest {

	private MockHttpServletRequest request;
	private MockHttpServletResponse response;
	private CXFServlet cxfServlet;
	
	@Before
	public void setUp() {
		//request = new MockHttpServletRequest();
		//response = new MockHttpServletResponse();
	}
	
	@Test
	@Ignore
	public void testIndex() {
		request = new MockHttpServletRequest("GET", "http://localhost/sample");
		response = new MockHttpServletResponse();
		cxfServlet = new CXFServlet();
		try {
			cxfServlet.service(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals("sample.bean.IndexBean", response.getClass().toString());
	}
}

