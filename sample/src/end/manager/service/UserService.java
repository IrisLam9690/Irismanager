package end.manager.service;



import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import edu.manager.bean.basic.UserBean;
import edu.manager.bean.basic.ListContainerManager;
import end.manager.dao.UserDao;

public class UserService {

	@Resource(name = "UserDao")
	private UserDao userDao;
	
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllUsers() {
		return Response.ok(new ListContainerManager<UserBean>(userDao.readAll())).build();
	}
}

