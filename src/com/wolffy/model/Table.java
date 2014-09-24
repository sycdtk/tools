package com.wolffy.model;

import java.util.HashMap;
import java.util.Map;

/**
 * 表格模型
 * @author wolffy
 *
 */
public class Table {
	
	private Map<Integer, Map<Integer, String>> tableMap = new HashMap<Integer, Map<Integer, String>>();
	
	public void put(int rowNum, int columnNum, String content){
		if(tableMap.containsKey(rowNum)){
			tableMap.get(rowNum).put(columnNum, content);
		}else{
			Map<Integer, String> recordMap = new HashMap<Integer, String>();
			recordMap.put(columnNum, content);
			tableMap.put(rowNum, recordMap);
		}
	}
	
	public String get(int rowNum, int columnNum){
		String result = null;
		if(tableMap.containsKey(rowNum)){
			if(tableMap.get(rowNum).containsKey(columnNum)){
				result = tableMap.get(rowNum).get(columnNum);
			}
		}
		return result;
	}
	
	public int size(){
		return tableMap.size();
	}

}
