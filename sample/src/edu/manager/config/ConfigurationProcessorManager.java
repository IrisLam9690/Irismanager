package edu.manager.config;

import java.io.File;
import java.util.Iterator;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.runners.JUnit4;

import com.sun.istack.logging.Logger;

public class ConfigurationProcessorManager implements ServletContextListener{

private Logger logger = Logger.getLogger(ConfigurationProcessorManager.class);
	
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		doConfiguration();
	}
	
	/**
	 * 进行配置工作
	 */
	public void doConfiguration() {		
		try {
			logger.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			logger.info("classpath:" + ConfigurationProcessorManager.class.getClassLoader().getResource("config.xml").toString());
			String configPath = ConfigurationProcessorManager.class.getClassLoader().getResource("config.xml").toString();
			configPath = pathFormat(configPath);
			File xmlFile = new File(configPath);
			SAXReader saxReader = new SAXReader();
			Document doc = saxReader.read(xmlFile);
			Element root = doc.getRootElement();
			Iterator iterator = root.elementIterator();
			//实际上只循环一次
			while (iterator.hasNext()) {
				Element data = (Element) iterator.next();
				ConfigurationManager.ROOT_PATH = data.elementText("rootpath");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}		
	}
	
	/**
	 * 格式化路径
	 * @param path 原路径
	 * @return 格式化后的路径
	 */
	public String pathFormat(String path) {
		return path.replaceAll("%20", " ").replaceAll("file:/", "");
	}
	
	public static void main(String[] args) {
		System.out.println(String.class.getClassLoader());
		System.out.println(ConfigurationProcessorManager.class.getClassLoader());
	}
}

