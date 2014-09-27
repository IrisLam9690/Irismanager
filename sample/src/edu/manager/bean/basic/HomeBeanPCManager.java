package edu.manager.bean.basic;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement

public class HomeBeanPCManager extends AbstractHomeBeanManager {

	private ListContainerManager<UserBean> users;

	public ListContainerManager<UserBean> getUsers() {
		return users;
	}

	public void setUsers(ListContainerManager<UserBean> users) {
		this.users = users;
	}
	
	public HomeBeanPCManager() {
		super();
		// TODO Auto-generated constructor stub
	}

	public HomeBeanPCManager(ManagerBean managerBean, ListContainerManager<UserBean> users){
		super(managerBean);
		this.users = users;
	}

}

