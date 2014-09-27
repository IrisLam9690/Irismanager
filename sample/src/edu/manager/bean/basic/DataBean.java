package edu.manager.bean.basic;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity(name = "DataBean")
@Table(name = "data")
@XmlRootElement

public class DataBean {
	public DataBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public DataBean(int id, String data1, String data2, String data3,
			String data4) {
		super();
		this.id = id;
		this.data1 = data1;
		this.data2 = data2;
		this.data3 = data3;
		this.data4 = data4;
	}
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;
	
	@Column(name = "data1")
	private String data1;
	
	@Column(name = "data2")
	private String data2;
	
	@Column(name = "data3")
	private String data3;
	
	@Column(name = "data4")
	private String data4;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getData1() {
		return data1;
	}
	public void setData1(String data1) {
		this.data1 = data1;
	}
	public String getData2() {
		return data2;
	}
	public void setData2(String data2) {
		this.data2 = data2;
	}
	public String getData3() {
		return data3;
	}
	public void setData3(String data3) {
		this.data3 = data3;
	}
	public String getData4() {
		return data4;
	}
	public void setData4(String data4) {
		this.data4 = data4;
	}
	
}


