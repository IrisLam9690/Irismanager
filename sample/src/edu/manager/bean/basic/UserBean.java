package edu.manager.bean.basic;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;


@Entity(name = "UserBean")
@Table(name = "user")
@XmlRootElement
public class UserBean {

	public UserBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public UserBean(int id, String password, String email, Date time) {
		super();
		this.id = id;
		this.password = password;
		this.email = email;
		this.time = time;
	}

	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "time")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date time;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.passsword = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
}

