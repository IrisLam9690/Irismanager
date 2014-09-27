package edu.manager.bean.basic;

import niuzhixiang.bean.UserBean;

public  abstract class AbstractHomeBeanManager {

	private ManagerBean managerBean;
	
	public ManagerBean getMananger() {
		return managerBean;
	}
	public void setManagerBean(ManagerBean managerBean) {
		this.managerBean = managerBean;
	}
	
	public AbstractHomeBeanManager() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AbstractHomeBeanManager(ManagerBean managerBean) {
		super();
		this.managerBean = managerBean;
	}
	
}

