package com.wolffy.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wolffy.model.Table;

public class EscapeUtils {

	/**
	 * 数据转义
	 * @param table
	 * @param expression
	 */
	public static Table escape(Table table, String expression){
		List<HashMap<String, String>> mapList = analysis(expression);
		for(Map<String, String> map : mapList){
			int column = Integer.parseInt(map.get("c"));
			int rowSize = table.getRowSize();
			for(int row=0; row<rowSize; row ++){
				String value = table.get(row, column);
				table.put(row, column, map.get(value));
			}
		}
		return table;
	}
	
	/**
	 * 表达式解析
	 * @param expression
	 */
	public static List<HashMap<String, String>> analysis(String expression){
		List<HashMap<String, String>> mapList = new ArrayList<HashMap<String,String>>();
		
		String[] strs = expression.split(";");
		
		for(String str : strs){
			String[] column_value = str.split("\"");
			if(column_value.length==2){
				Map<String,String> map = new HashMap<String,String>();
				map.put("c", column_value[0]);
				String[] values = column_value[1].split(",");
				for(String value : values){
					String[] keys = value.split(":");
					if(keys.length==2){
						map.put(keys[0], keys[1]);
					}
				}
				mapList.add((HashMap<String, String>) map);
			}
		}
		
		return mapList;
	}

}
