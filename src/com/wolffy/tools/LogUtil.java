package com.wolffy.tools;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * 日志输出管理
 * @author wolffy
 *
 */
public class LogUtil {
	
	public static String level = null;
	public static String file = null;
	public static ConsoleHandler consoleHandler = null;
	public static FileHandler fileHandler = null;
	
	static{
		level = PropsUtil.getValue("log.level").toLowerCase();
		file = PropsUtil.getValue("log.file");
		
		consoleHandler = new ConsoleHandler();
		//默认值为INFO，仅判断CONFIG
		if(level.equals("config")){
			consoleHandler.setLevel(Level.CONFIG);
		}else{
			consoleHandler.setLevel(Level.INFO);
		}
		
		if(file!=null){
			try {
				//FileHandler第二个参数true追加模式，若文件存在则追加
				fileHandler = new FileHandler(System.getProperty("user.dir")+System.getProperty("file.separator")+file,true);
				if(level.equals("config")){
					fileHandler.setLevel(Level.CONFIG);
				}else{
					fileHandler.setLevel(Level.INFO);
				}
				fileHandler.setFormatter(new SimpleFormatter());
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取Logger对象
	 * @param className
	 * @return
	 */
	public static Logger getLogger(String className){
		
		Logger logger = Logger.getLogger(className);
		
		//禁用默认Handler
		logger.setUseParentHandlers(false);
		
		//默认值为INFO，仅判断CONFIG
		if(level.equals("config")){
			logger.setLevel(Level.CONFIG);
		}else{
			logger.setLevel(Level.INFO);
		}
		logger.addHandler(consoleHandler);
		
		if(file!=null){
			logger.addHandler(fileHandler);
		}
		
		return logger;
	}
}
