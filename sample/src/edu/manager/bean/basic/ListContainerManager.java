package edu.manager.bean.basic;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement
@XmlSeeAlso(value = {UserBean.class, EnterpriseBean.class, FileBeanManager.class })
public class ListContainerManager {

	private List<T> list;

	public ListContainerManager() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ListContainerManager(List<T> list) {
		super();
		this.list = list;
	}

	public List<T> getListManager() {
		return list;
	}

	public void setListManager(List<T> list) {
		this.list = list;
	}
	
}

