package edu.manager.util;

import static niuzhixiang.config.Configuration.ROOT_PATH;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.activation.DataHandler;

import niuzhixiang.util.Attachment;
import niuzhixiang.util.MultivaluedMap;

/**
 * �����ļ��ϴ��Ĺ�����
 * @author Niuzhixiang
 *
 */
public class Multipart {

	private static final String DATE_TIME_FORMAT = "yyyyMMddhhmmss";
	private static final String DATE_FORMAT = "yyyyMMdd";
	private static Random random = new Random();
	private static SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
	private static SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
	
	//public static final String ROOT_PATH = "D:" + File.separator + "Web Applications";
	
	/**
	 * ����ϵͳ��ǰʱ�����������Լ��������ļ���չ�����������ļ���
	 * @param extension �ļ���չ��
	 * @return �ļ������������ļ���+��չ����
	 */
	public String generateFileName(String extension){			    	
		return dateTimeFormat.format(new Date()) + Integer.toString(Math.abs(random.nextInt())) + "." + extension;		
	}
	
	/**
	 * ���ݸ������ļ���������Ŀ¼������Ŀ¼�������򴴽�
	 * @param type �ļ�����
	 * @return ���ɵ�Ŀ¼��
	 */
	public String generateDirNameAndMkdir(String type){
		String directoryName = File.separator + "Uploads" + File.separator + type + File.separator + dateFormat.format(new Date());
		File dir = new File(ROOT_PATH + directoryName);
		dir.mkdirs();
		return directoryName;
	}
	
	/**
	 * ����HTTP����ͷ�е��ļ������������յ��ļ���ʶ����Ŀ¼+�ļ�����
	 * @param header HTTP����ͷ
	 * @return ���յ��ļ���ʶ��
	 * @throws Exception �ļ����ʹ����쳣
	 */
	public String generateFinalName(MultivaluedMap<String, String> header) throws Exception {
		try {
			//Content-Disposition: form-data; name="uploadedFile"; filename="ihelper.db-wal"
			String[] contentDispositions = header.getFirst("Content-Disposition").split("; ");
			//filename="ihelper.db-wal"
			String filename = contentDispositions[contentDispositions.length-1];
			//ihelper.db-wal
			filename = filename.replaceAll("filename=\"", "").replaceAll("\"", "");
			//db-wal
			String extension;
			if (filename.split("\\.").length > 1) {
				extension = filename.split("\\.")[filename.split("\\.").length-1]; 
			} else {
				extension = "unknown-extension";
			}	
			return generateDirNameAndMkdir(extension) + File.separator + generateFileName(extension);
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception("�ļ����ʹ���");
		}	
	}
	
	/**
	 * �ϴ������ļ�
	 * @param attachment ���ϴ��ĸ���
	 * @return �ϴ����ļ��ڷ������˵�������ʶ����Ŀ¼+�ļ�����
	 * @throws Exception �ϴ��ļ�ʱ����������쳣
	 */
	public String uploadFile(Attachment attachment) throws Exception {
		String finalName = generateFinalName(attachment.getHeaders());
		try {
        	DataHandler handler = attachment.getDataHandler();
    		InputStream input = handler.getInputStream(); 		
            OutputStream output = new FileOutputStream(new File(ROOT_PATH + finalName));         
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = input.read(bytes)) != -1) {
                output.write(bytes, 0, read);
            }
            input.close();
            output.flush();
            output.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("�ϴ��ļ�ʱ��������");
		}
        return finalName;
	}
	
	/**
	 * �ϴ�����ļ������鲻ʹ�ø÷�����ʹ��uploadFile()��������
	 * @param attachments ���ϴ����ļ��б�
	 * @return �ϴ����ļ��ڷ������˵�������ʶ����Ŀ¼+�ļ��������б�
	 * @throws Exception �ϴ��ļ�ʱ����������쳣
	 */
	@Deprecated
	public List<String> uploadFiles(List<Attachment> attachments) throws Exception {
		List<String> result = new ArrayList<String>();
		for (Attachment attachment : attachments) {
			result.add(uploadFile(attachment));
		}
		return result;
	}	
}

