package edu.manager.bean.basic;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity(name = "EnterpriseBean")
@Table(name = "enterprise")
@XmlRootElement
public class EnterpriseBean {


	public EnterpriseBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EnterpriseBean(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;
	
	@Column(name = "name")
	private String name;
	
	/*@OneToMany(mappedBy = "schoolBean", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<UserBean> userBeans;*/
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}

