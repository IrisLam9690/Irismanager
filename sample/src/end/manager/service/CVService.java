package end.manager.service;



@Path("/cv")
public class CVService {
	@Resource(name = "CVDao")
	private ItemDao CVDao;
	
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllCV() {
		return Response.ok(new ListContainerManager<CVBean>(cvDao.readAll())).build();
	}
}
