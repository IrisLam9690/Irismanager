package edu.manager.bean.basic;

import niuzhixiang.bean.ListContainer;
import niuzhixiang.bean.SchoolBean;
import niuzhixiang.bean.XmlRootElement;

@XmlRootElement
public class RegisterBeanManager {
	public RegisterBeanManager() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RegisterBeanManager(ListContainerManager<EnterpriseBean> enterprises) {
		super();
		this.enterprises= enterprises;
	}
	
	private ListContainerManager<EnterpriseBean> enterprises;

	public ListContainerManager<EnterpriselBean> getEnterprises() {
		return enterprises;
	}

	public void setEnterprises(ListContainerManager<EnterpriseBean> enterprises) {
		this.enterprises = enterprises;
	}

}
