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

	//����ע����ע��
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
			//�û������ڣ����û������������
			if (managerBean == null) {
				return Response.ok().status(400).build();
			} 
			//�û����ڣ����е�¼
			else {
				LogonSession logonSession = managerDao.createLogonSession(managerBean);
				NewCookie manageridCookie = new NewCookie("managerid", String.valueOf(logonSession.getManagerid())); 
				NewCookie sessionidCookie = new NewCookie("sessionid", logonSession.getSessionid());
				//���������Դ��PC��ҳ
				if (source.equals("pc")) {
					/*
					 * ���ﲻ��302״̬���룬��Ϊ��������Զ��ػ�302��Ӧ���Զ������ض���AJAX���޷�������Ӧͷ������AJAX����
					 * ֻ�����ͺͽ������ݣ�������תҳ��Ĺ��ܣ���Ҫ����window.location.href�ķ�ʽ�ֶ���д��
					 */
					return Response.ok().status(200).location(new URI("/manager/" + managerBean.getId().toString())).cookie(manageridCookie, sessionidCookie).build();
				}
				//���������Դ���ƶ���
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


