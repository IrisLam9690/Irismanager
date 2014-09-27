package edu.manager.bean.mongo;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
@XmlRootElement
public class LogonSessionManager {

	private String sessionid;
	private int managerid;

	public String getSessionid() {
		return sessionid;
	}

	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}

	public int getManagerid() {
		return managerid;
	}

	public void setManagerid(int managerid) {
		this.managerid = managerid;
	}

	public LogonSession() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LogonSession(String sessionid, int managerid) {
		super();
		this.sessionid = sessionid;
		this.managerid = managerid;
	}
	
}

