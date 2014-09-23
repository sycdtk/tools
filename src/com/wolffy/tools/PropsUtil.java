package com.wolffy.tools;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * 资源文件加载类
 * @author wolffy
 *
 */
public class PropsUtil {
	
	private static Properties P = null;
	private static String CONFIG_FILE_NAME = "config.properties";
	
	
	public static void load(String path){
		P = new Properties();
		String classPath = PropsUtil.class.getClassLoader().getResource("").getPath();
		try {
			FileInputStream fis = new FileInputStream(classPath+System.getProperty("file.separator") + CONFIG_FILE_NAME);
			P.load(fis);
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getValue(String key){
		return P.getProperty(key);
	}
	
}
