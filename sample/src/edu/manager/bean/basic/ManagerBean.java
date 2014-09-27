package edu.manager.bean.basic;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity(name = "ManagerBean")
@Table(name = "manager")
@XmlRootElement
public class ManagerBean {

	public Mananger(){
		super();
		
	}
	
	public ManagerBean(Integer id, String managername, String password,Enterprise enterprise ) {
		super();
		this.id = id;
		this.managername = managername;
		this.password = password;
		this.enterpriseBean = enterpriseBean;
	}

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "managername")
	private String managername;
	
	@Column(name = "password")
	private String password;
	
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "Enterpriseid")
	private EnterBean EnterpriseBean;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getaManagername() {
		return Managername;
	}
	public void setManagername(String Managername) {
		this.Managername = Managername;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
public EnterpriseBean getEnterpriseBean() {
	return enterpriseBean;
}

public void setEnterpriseBean(EnterpriseBean enterpriseBean) {
	this.enterpriseBean = enterpriseBean;
}




