package edu.manager.bean.basic;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity(name = "FileBean")
@Table(name = "file")
@XmlRootElement
public class FileBeanManager {

	public FileBeanManager() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public FileBeanManager(int id, String path, int managerid) {
		super();
		this.id = id;
		this.path = path;
		this.managerid = managerid;
	}

	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;
	
	@Column(name = "path")
	private String path;
	
	@Column(name = "managerid")
	private int managerid;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public int getManagerid() {
		return managerid;
	}
	public void setManagerid(int managerid) {
		this.managerid = managerid;
	}
	
}

