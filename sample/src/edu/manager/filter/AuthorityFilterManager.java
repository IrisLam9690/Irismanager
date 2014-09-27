package edu.manager.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.manager.bean.mongo.LogonSessionManager;
import edu.manager.util.Utils;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.sun.istack.logging.Logger;


public class AuthorityFilterManager implements Filter {

	private Logger logger = Logger.getLogger(AuthorityFilterManager.class);
	
	@Resource(name = "MongoTemplate")
	private MongoTemplate mongoTemplate;
	
	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	public void destroy() {
		// TODO Auto-generated method stub

	}
	
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;
		String uri = req.getRequestURI();
		
		//�����ע��͵�¼ҳ�棬����֤�Ƿ��¼
		if (uri.equals("")||uri.equals("/sample")||uri.equals("/sample/")
				||uri.equals("/sample/register")||uri.equals("/sample/register/")
				||uri.equals("/sample/logon")||uri.equals("/sample/logon/")
				//������
				||uri.equals("/sample/test")||uri.equals("/sample/test/")) {
			logger.info("��¼ע��ҳ�棬�������");
			chain.doFilter(request, response);
		} 
		//����Ҫ��֤�Ƿ��¼
		else {	
			//�������cookieת���Map��ʽ�����ں�������
			Map<String, String> cookiesMap = Utils.convertCookies(req.getCookies(), "sessionid", "managerid");
			logger.info("cookies : " + cookiesMap.toString());		
			//����а���sessionid��userid��cookie������ȥmongodb�ж�ȡLogonSession		
			if (cookiesMap!=null && cookiesMap.containsKey("sessionid") && cookiesMap.containsKey("userid")) {
				LogonSession logonSession = getMongoTemplate().findOne(new Query(Criteria.where("sessionid").is(cookiesMap.get("sessionid"))).addCriteria(Criteria.where("userid").is(Integer.parseInt(cookiesMap.get("userid")))), LogonSession.class, "logonsession");
				//LogonSession��ȫƥ�䣬˵���Ѿ���¼
				if (logonSession!=null) {
					logger.info("�Ѿ���¼");
					chain.doFilter(request, response);
				}
				else {
					logger.info("û�ҵ�logonsession");
					resp.sendRedirect("http://localhost/MyREST_Maven");
				}
			} else {
				logger.info("cookie�в�����sessionid��userid");
				resp.sendRedirect("http://localhost/MyREST_Maven");
			}
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}

