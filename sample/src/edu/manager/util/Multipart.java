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
 * 处理文件上传的工具类
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
	 * 根据系统当前时间和随机数，以及给定的文件扩展名生成完整文件名
	 * @param extension 文件扩展名
	 * @return 文件完整名（主文件名+扩展名）
	 */
	public String generateFileName(String extension){			    	
		return dateTimeFormat.format(new Date()) + Integer.toString(Math.abs(random.nextInt())) + "." + extension;		
	}
	
	/**
	 * 根据给定的文件类型生成目录名，若目录不存在则创建
	 * @param type 文件类型
	 * @return 生成的目录名
	 */
	public String generateDirNameAndMkdir(String type){
		String directoryName = File.separator + "Uploads" + File.separator + type + File.separator + dateFormat.format(new Date());
		File dir = new File(ROOT_PATH + directoryName);
		dir.mkdirs();
		return directoryName;
	}
	
	/**
	 * 根据HTTP请求头中的文件类型生成最终的文件标识符（目录+文件名）
	 * @param header HTTP请求头
	 * @return 最终的文件标识符
	 * @throws Exception 文件类型错误异常
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
			throw new Exception("文件类型错误");
		}	
	}
	
	/**
	 * 上传单个文件
	 * @param attachment 待上传的附件
	 * @return 上传的文件在服务器端的完整标识符（目录+文件名）
	 * @throws Exception 上传文件时发生错误的异常
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
			throw new Exception("上传文件时发生错误");
		}
        return finalName;
	}
	
	/**
	 * 上传多个文件，建议不使用该方法，使用uploadFile()方法代替
	 * @param attachments 待上传的文件列表
	 * @return 上传的文件在服务器端的完整标识符（目录+文件名）的列表
	 * @throws Exception 上传文件时发生错误的异常
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

