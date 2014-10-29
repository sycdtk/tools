package com.wolffy.utils;

import java.io.File;
import java.util.HashMap;
import java.util.logging.Logger;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import com.wolffy.manager.LoggerManager;
import com.wolffy.manager.PropsManager;

/**
 * Excel文档的读取并插入图表
 * @author wolffy
 *
 */
public class JacobUtils {

	private static Logger logger = LoggerManager.getLogger(JacobUtils.class.getName());
	
	public static final String[] KV = { "A", "B", "C", "D", "E", "F", "G", "H",
		"I", "G", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
		"V", "W", "X", "Y", "Z" };
	
	public static void drawChart() {
		
		String excelPath = PropsManager.getValue("excel.path");
		excelPath = excelPath==null?System.getProperty("user.dir"):excelPath;
		String excelName = PropsManager.getValue("excel.name");
		String excelFile = excelPath+System.getProperty("file.separator")+excelName;

		String[] reportCount = PropsManager.getValue("report.count").split(",");

		String jacobDllName = PropsManager.getValue("jacob.dll");
		String chartWidth = PropsManager.getValue("jacob.chart.width");
		String chartHeight = PropsManager.getValue("jacob.chart.height");
		String chartPrefix = PropsManager.getValue("jacob.chart.prefix");
		
		//获取配置图表编码
		String chartCategoryStr = PropsManager.getValue("jacob.chart.chartCategory");
		HashMap<String, String> chartCategorysMap = new HashMap<String,String>();
		String[] chartCategorys = chartCategoryStr.split(",");
		for(String chartCategory : chartCategorys){
			String[] chartCategory_value = chartCategory.split(":");
			if(chartCategory_value.length==2){
				chartCategorysMap.put(chartCategory_value[0].toLowerCase(), chartCategory_value[1]);
			}
		}
		
		//加载jacobdll资源
		System.setProperty("jacob.dll.path",new File(jacobDllName).getAbsolutePath());
		ComThread.InitSTA();
		ActiveXComponent excel = new ActiveXComponent("Excel.Application");
		
		// 设置是否显示打开excel
		Dispatch.put(excel, "Visible", new Variant(false));

		// 工作簿对象
		Dispatch workbooks = excel.getProperty("Workbooks").toDispatch();
			
		Dispatch workbook = Dispatch.invoke(
				// 打开具体工作簿
				workbooks, "Open", Dispatch.Method,
				// 是否显示打开
				new Object[] { excelFile, new Variant(true),
				// 是否以只读方式打开
				new Variant(false) },
				new int[1]).toDispatch();
		try {
			
			Dispatch sheets = Dispatch.get(workbook, "sheets").toDispatch();
			//遍历sheets
			for(String countStr : reportCount){
				//获取sheet配置资源
				//获取sheet名称
				int count = Integer.parseInt(countStr);
				String sheetName = PropsManager.getValue("report."+count+".name");
				String chartTypesStr = PropsManager.getValue("report."+count+".chartTypes");
				
				//sheet是否生成图表
				if(chartTypesStr!=null&&!chartTypesStr.equals("")){
					
					String[] chartTypes = chartTypesStr.split(":");
					String chartType = chartTypes[0].toLowerCase();
					Object[] chartArgs = getApplyDataLabelsArgs(chartTypes[1]);
					Integer maxRow = (chartTypes.length==3)?Integer.parseInt(chartTypes[2])+1:Integer.MAX_VALUE;
					
					logger.config("生成"+sheetName+"图表！");
					
					//获取单个sheet
					Dispatch sheet = Dispatch.invoke(sheets, "Item", Dispatch.Get,
							new Object[] { sheetName }, new int[1]).toDispatch();
					
					//计算行列宽度数量
					int rowNum = 0;
					int columnNum = 0;
					
					//获取行数
					for (int i = 1; i <= maxRow; i++){
						Dispatch cell = Dispatch.invoke(sheet, "Range", Dispatch.Get, new Object[] { "A" + i }, new int[1]).toDispatch();
				        Variant value = Dispatch.get(cell, "Value");
			    		if(!String.valueOf(value).equals("null")){
			    			rowNum++;
			    		}else{
			    			break;
			    		}
					}
					
					//获取列数
					for (char i = 'A'; i <= 'Z'; i++){
						Dispatch cell = Dispatch.invoke(sheet, "Range", Dispatch.Get, new Object[] { i +"1" }, new int[1]).toDispatch();
				        Variant value = Dispatch.get(cell, "Value");
			    		if(!String.valueOf(value).equals("null")){
			    			columnNum++;
			    		}else{
			    			break;
			    		}
					}
					
					
					// 创建chart并获取chart对象
					Dispatch chartobjects = Dispatch.call(sheet, "ChartObjects").toDispatch();
					Dispatch achart = Dispatch.invoke(
							chartobjects,
							"Add",
							Dispatch.Method,
							new Object[] { new Integer(columnNum * 15),
									new Integer(10), new Integer(chartWidth),
									new Integer(chartHeight) }, new int[1]).toDispatch();
					Dispatch chart = Dispatch.get(achart, "chart").toDispatch();
					
					//绘制图表
					// -4102 饼图
					// -4100 柱状图
					// -4101 折线图
					Dispatch.put(chart, "ChartType", chartCategorysMap.get(chartType));
					Dispatch.put(chart, "HasTitle", "true");
					Dispatch chartTitle = Dispatch.call(chart, "ChartTitle").toDispatch();
					Dispatch characters = Dispatch.call(chartTitle, "Characters").toDispatch();
					Dispatch.put(characters, "Text", chartPrefix+sheetName);
					
					Dispatch range = Dispatch.invoke(sheet, "Range", Dispatch.Get,
							new Object[] {"A1:"+KV[columnNum-1]+rowNum}, new int[1]).toDispatch();
					
					Dispatch series = Dispatch.get(chart, "SeriesCollection")
							.toDispatch();

					Dispatch.call(series, "add", range, new Variant(true));
					
					//创建图表，由于jacob的bug，增加异常继续功能
					try{
						Dispatch.invoke(
								chart,
								"ApplyDataLabels",
								Dispatch.Method,
								chartArgs,
								new int[1]).toDispatch();
					}catch(Exception e){
//						TODO jacob error，类型转换报错，原因未知，但程序结果正确，经过查询可能是jacob的bug或是对com接口调用时没有权限
						logger.config("Jacob error : " + e.getMessage());
						continue;
					}
				}
			}
			
		}catch (Exception e) {
			logger.severe(e.getMessage());
			e.printStackTrace();
		} finally {
			Dispatch.call(workbook, "Save");
	        Dispatch.call(workbook, "Close", new Variant(false));
			excel.invoke("Quit", new Variant[] {});
			ComThread.Release();
		}
		
	}
	
	private static Object[] getApplyDataLabelsArgs(String argsStr){
		String[] args = argsStr.split(",");
		return new Object[] { new Variant(Integer.parseInt(args[0])), new Variant(Boolean.parseBoolean(args[1])),
				new Variant(Boolean.parseBoolean(args[2])), new Variant(Boolean.parseBoolean(args[3])),
				new Variant(Boolean.parseBoolean(args[4])), new Variant(Boolean.parseBoolean(args[5])),
				new Variant(Boolean.parseBoolean(args[6])), new Variant(Boolean.parseBoolean(args[7])),
				new Variant(Boolean.parseBoolean(args[8])), "," };
		
	}

}
