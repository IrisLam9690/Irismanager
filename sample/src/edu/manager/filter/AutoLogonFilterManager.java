package edu.manager.filter;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import edu.manager.bean.mongo.LogonSessionManager;
import edu.manager.util.Utils;

public class AutoLogonFilterManager implements Filter {
	@Resource(name = "MongoTemplate")
	private MongoTemplate mongoTemplate;
	
	
	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	public void init(FilterConfigManager filterConfigmanager) throws ServletException {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;	
		Map<String, String> cookiesMap = Utils.convertCookies(req.getCookies(), "sessionid", "userid");
		LogonSession logonSession = null;
		if (cookiesMap!=null && cookiesMap.containsKey("sessionid") && cookiesMap.containsKey("userid")) {
				logonSession = getMongoTemplate().findOne(new Query(Criteria.where("sessionid").is(cookiesMap.get("sessionid"))).addCriteria(Criteria.where("userid").is(Integer.parseInt(cookiesMap.get("userid")))), LogonSession.class, "logonsession");
		}			
		if (logonSession!=null) {
				resp.sendRedirect("http://localhost/sample/manager/"+logonSession.getManagerid());
		} else {
			chain.doFilter(request, response);
		}
	}

	public void destroy() {
		// TODO Auto-generated method stub

	}

}

