package end.manager.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import edu.manager.bean.basic.HtmlDispatcherBean;


@Path("/htmldispatcher")
public class HtmlDispatcherServiceManager {
	@GET
	@Path("/")
	@Produces({"application/json", "application/xml", "text/html"})
	public Response foo() {
		return Response.ok(new HtmlDispatcherBeanManager()).build();
	}
}
