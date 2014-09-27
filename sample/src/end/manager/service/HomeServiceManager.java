package end.manager.service;


mport javax.annotation.Resource;
import javax.mail.FetchProfile.Item;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.CookieParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.ext.MessageContext;
import org.springframework.transaction.annotation.Transactional;

import com.sun.istack.logging.Logger;

import edu.manager.bean.basic.DataBean;
import edu.manager.bean.basic.HomeBeanMobileManager;
import edu.manager.bean.basic.HomeBeanPCManager;
import edu.manager.bean.basic.UserBean;
import edu.manager.bean.basic.ListContainerManager;
import edu.manager.bean.basic.Steps;
import edu.manager.bean.basic.ManagerBean;
import end.manager.dao.DataDaoManager;
import end.manager.dao.UserDao;
import end.manager.dao.ManagerDao;

@Path("/user")
public class HomeServiceManager {
	Logger logger = Logger.getLogger(HomeService.class);

	@Resource(name = "ManagerDao")
	private ManagerDao managerDao;
	
	@Resource(name = "UserDao")
	private UserDao userDao;
	
	@Resource(name = "DataDaoManager")
	private DataDaoManager dataDaomanager;

	public ManagerDao getManagerDao() {
		return managerDao;
	}

	public void setManagerDao(ManagerDao managerDao) {
		this.managerDao = managerDao;
	}
	
	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	@GET
	@Path("/{managerid}")
	@Produces({ "application/json", "application/xml", "text/html" })
	public Response homePC(@PathParam("managerid") int managerid, @QueryParam("start") String start, @QueryParam("size") String size) {
		try {			
			//如果没有start和size参数，说明这是第一次加载该页面（非AJAX请求），则返回HomeBeanPC（包含UserBean和ListContainer）
			if (start==null || size==null) {
				ManagerBean managerBean = managerDao.getManagerById(managerid);
				ListContainerManager<UserBean> users = new ListContainerManager<UserBean>(userDao.readSeveral(0, 5));			
				HomeBeanPC homeBeanPC = new HomeBeanPC(userBean, items);
				return Response.ok(homeBeanPC).status(200).build();
			} 
			//如果有start和size参数，说明是AJAX请求，则只返回ListContainer即可
			else {
				ListContainerManager<UserBean> users = new ListContainerManager<UserBean>(userDao.readSeveral(Integer.parseInt(start), Integer.parseInt(size)));
				return Response.ok(items).status(200).build();
			}			
		} catch (Exception e) {
			// TODO: handle exception
			return Response.ok().status(500).build();
		}		
	}
	
	@GET
	@Path("/mobile/{managerid}")
	@Produces({ "application/json", "application/xml", "text/html" })
	public Response homeMobile(@PathParam("managerid") int managerid) {
		try {			
			logger.info("/manager/mobile is visited");
			ManagerBean managerBean = managerDao.getManagerById(managerid);		
			HomeBeanMobile homeBeanMobile = new HomeBeanMobile(managerBean);
			return Response.ok(homeBeanMobile).status(200).build();			
		} catch (Exception e) {
			// TODO: handle exception
			return Response.ok().status(500).build();
		}		
	}
	
	@GET
	@Path("/{userid}/step1")
	@Produces({ "application/json", "application/xml", "text/html" })
	public Response step1(@PathParam("userid") int userid) {
		return Response.ok(Steps.STEP1).build();
	}
	
	@GET
	@Path("/{userid}/step2")
	@Produces({ "application/json", "application/xml", "text/html" })
	public Response step2(@PathParam("userid") int userid) {
		return Response.ok(Steps.STEP2).build();
	}
	
	@POST
	@Path("/{managerid}/stepcomplete")
	@Produces({ "application/json", "application/xml", "text/html" })
	public Response stepComplete(@PathParam("managerid") int managerid, @CookieParam("data1") String data1, @CookieParam("data2") String data2, @CookieParam("data3") String data3, @CookieParam("data4") String data4) {
		DataBeanManager data = new DataBeanManager();
		data.setData1(data1);
		data.setData2(data2);
		data.setData3(data3);
		data.setData4(data4);
		try {
			dataDaomanager.save(data);
		} catch (Exception e) {
			// TODO: handle exception
			return Response.ok().status(500).build();
		}
		return Response.ok().status(200).build();
	}
}

