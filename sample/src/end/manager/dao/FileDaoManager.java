package end.manager.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import edu.manager.bean.basic.FileBeanManager;
import edu.manager.bean.basic.FilesManager;
import edu.manager.util.Multipart;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

public class FileDaoManager {

	@Resource(name = "HibernateSessionFactory")
	private SessionFactory sessionFactory;	
	
	@Resource(name = "Multipart")
	private Multipart multipart;
	
	public boolean uploadFile(Attachment attachment, int managerid) {
		try {
			String path = multipart.uploadFile(attachment);
			FileBeanManager fileBeanmanager = new FileBeanManager();
			fileBeanmanager.setPath(path);
			fileBeanmanager.setManagerid(managerid);
			sessionFactory.getCurrentSession().save(fileBeanmanager);	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean uploadFiles(List<Attachment> attachments, int managerid) {
		boolean result = true;
		for (Attachment attachment : attachments) {
			if (!uploadFile(attachment, managerid)) {
				result = false;
			}		
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public FilesManager getFilesByManager(int managerid) {
		Files filesmanager = new FilesManager();
		Query query = sessionFactory.getCurrentSession().createQuery("from FileBean as fileBean where fileBean.userid = :userid");
		query.setParameter("managerid", managerid);
		files.setFilesManager(query.list());
		return filesmanager;
	}
	
	public FileBeanManager getFileById(int fileid) {
		Query query = sessionFactory.getCurrentSession().createQuery("from FileBean as fileBean where fileBean.id = :id");
		query.setParameter("id", fileid);
		List<FileBeanManager> resultList = query.list();
		if (resultList.size() > 0) {
			return resultList.get(0);
		} else {
			return null;
		}
	}
}
	