package com.wolffy.utils;

import com.wolffy.model.Table;

/**
 * 补全缺失数据
 * @author wolffy
 *
 */
public class CompletionUtils {

	public static Table completion(Table table, String expression){
		//需要补充数据的列数
		int columnNum = Integer.parseInt(expression.split("\"")[0]);
		//需要补充的其他列默认值
		String defaultValue = expression.split(":")[1];
		//列数据全集
		String[] values = expression.split("\"")[1].split(":")[0].split(",");
		
		for(String value : values){
			if(null==table.columnContains(columnNum, value)){
				table.put(table.getRowSize(), columnNum, value);
				int columnSize = table.getColumnSize();
				for(int column=0; column<columnSize;column++){
					if(column!=columnNum){
						table.put(table.getRowSize()-1, column, defaultValue);
					}
				}
			}
		}
		
		table.sort(columnNum, values);
		return table;
	}
}
