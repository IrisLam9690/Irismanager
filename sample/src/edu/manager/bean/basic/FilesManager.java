package edu.manager.bean.basic;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement
@XmlSeeAlso(value = {FileBeanManager.class})
public class FilesManager {

	public FilesManager() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FilesManager(List<FileBeanManager> filesmanager) {
		super();
		this.filesmanager = filesmanager;
	}

	private List<FileBeanManager> filesmanager;

	public List<FileBean> getFilesManager() {
		return filesmanager;
	}

	public void setFilesManager(List<FileBeanManager> filesmanager) {
		this.filesmanager = filesmanager;
	}
}
