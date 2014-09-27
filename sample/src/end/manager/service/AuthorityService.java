package edu.manager.service;

import java.net.URI;
import java.net.URISyntaxException;

import javax.annotation.Resource;
import javax.ws.rs.CookieParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import com.sun.istack.logging.Logger;

import edu.manager.bean.basic.IndexBeanManager;
import edu.manager.bean.basic.ListContainerManager;
import edu.manager.bean.basic.RegisterBeanManager;
import edu.manager.bean.basic.EnterpriseBean;
import edu.manager.bean.basic.ManagerBean;
import edu.manager.bean.mongo.LogonSessionManager;
import end.manager.dao.EnterpriseDao;
import end.manager.dao.ManagerDao;

public class AuthorityService {
	Logger logger = Logger.getLogger(AuthorityService.class);

	//利用注解来注入
	@Resource(name = "ManagerDao")
	private UserDao managerDao;
	@Resource(name = "EnterpriseDao")
	private EnterpriseDao enterpriseDao;
	
	public ManagerDao getManagerDao() {
		return managerDao;
	}

	public void setManagerDao(ManagerDao ManagerDao) {
		this.managerDao = managerDao;
	}

	public EnterpriseDao getEnterpriseDao() {
		return enterpriseDao;
	}

	public void setEnterpriseDao(EnterpriseDao Dao) {
		this.enterpriseDao = enterpriseDao;
	}

	
	
	@GET
	@Path("/")
	@Produces({ "application/json", "application/xml", "text/html" })
	public Response index() {
		return Response.ok(new IndexBean()).build();
	}
	
	
	@POST
	@Path("/logon")
	@Produces(MediaType.APPLICATION_JSON)
	public Response logon(@FormParam("managername") String managername, @FormParam("password") String password, @FormParam("source") String source) {
		try {
			logger.info("request source is " + source);
			ManagerBean managerBean = managerDao.checkManager(managername, password);
			//用户不存在，即用户名或密码错误
			if (managerBean == null) {
				return Response.ok().status(400).build();
			} 
			//用户存在，进行登录
			else {
				LogonSession logonSession = managerDao.createLogonSession(managerBean);
				NewCookie manageridCookie = new NewCookie("managerid", String.valueOf(logonSession.getManagerid())); 
				NewCookie sessionidCookie = new NewCookie("sessionid", logonSession.getSessionid());
				//如果请求来源是PC网页
				if (source.equals("pc")) {
					/*
					 * 这里不用302状态代码，因为浏览器会自动截获302响应并自动进行重定向，AJAX将无法解析响应头，而且AJAX自身
					 * 只负责发送和接收数据，并无跳转页面的功能，需要采用window.location.href的方式手动编写。
					 */
					return Response.ok().status(200).location(new URI("/manager/" + managerBean.getId().toString())).cookie(manageridCookie, sessionidCookie).build();
				}
				//如果请求来源是移动端
				else if (source.equals("mobile")) {
					return Response.ok().status(302).location(new URI("/manager/mobile/" + managerBean.getId().toString())).cookie(manageridCookie, sessionidCookie).build();
				} else {
					return Response.ok().status(200).build();
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return Response.ok().status(500).build();
		}
	}
	
	@GET
	@Path("/logout")
	@Produces(MediaType.APPLICATION_JSON)
	public Response logout(@CookieParam("managerid") int managerid) {
		try {
			managerDao.deleteLogonSession(managerid);
			return Response.ok().status(302).location(new URI("/")).build();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.ok().status(500).build();
		}
	}
	
}


