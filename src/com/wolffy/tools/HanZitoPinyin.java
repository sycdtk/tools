package com.wolffy.tools;

import net.sourceforge.pinyin4j.PinyinHelper;  
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;  
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;  
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;  
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;  
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;  

public class HanZitoPinyin {
	/** 
     * 将汉字转换为全拼 
     *  
     * @param src 
     * @return String 
     */  
    public static String getPinYin(String src) {  
        char[] t1 = null;  
        t1 = src.toCharArray();  
        // System.out.println(t1.length);  
        String[] t2 = new String[t1.length];  
        // System.out.println(t2.length);  
        // 设置汉字拼音输出的格式  
        HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();  
        t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);  
        t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);  
        t3.setVCharType(HanyuPinyinVCharType.WITH_V);  
        String t4 = "";  
        int t0 = t1.length;  
        try {  
            for (int i = 0; i < t0; i++) {  
                // 判断是否为汉字字符  
                // System.out.println(t1[i]);  
                if (Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) {  
                    t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);// 将汉字的几种全拼都存到t2数组中  
                    t4 += t2[0];// 取出该汉字全拼的第一种读音并连接到字符串t4后  
                } else {  
                    // 如果不是汉字字符，直接取出字符并连接到字符串t4后  
                    t4 += Character.toString(t1[i]);  
                }  
            }  
        } catch (BadHanyuPinyinOutputFormatCombination e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        return t4;  
    }  
  
    /** 
     * 提取每个汉字的首字母 
     *  
     * @param str 
     * @return String 
     */  
    public static String getPinYinHeadChar(String str) {  
        String convert = "";  
        for (int j = 0; j < str.length(); j++) {  
            char word = str.charAt(j);  
            // 提取汉字的首字母  
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);  
            if (pinyinArray != null) {  
                convert += pinyinArray[0].charAt(0);  
            } else {  
                convert += word;  
            }  
        }  
        return convert;  
    }  
  
    /** 
     * 将字符串转换成ASCII码 
     *  
     * @param cnStr 
     * @return String 
     */  
    public static String getCnASCII(String cnStr) {  
        StringBuffer strBuf = new StringBuffer();  
        // 将字符串转换成字节序列  
        byte[] bGBK = cnStr.getBytes();  
        for (int i = 0; i < bGBK.length; i++) {  
            // System.out.println(Integer.toHexString(bGBK[i] & 0xff));  
            // 将每个字符转换成ASCII码  
            strBuf.append(Integer.toHexString(bGBK[i] & 0xff)+" ");  
        }  
        return strBuf.toString();  
    }  
  
    public static void main(String[] args) {  
        String cnStr = "龚伟华"
        		+",魏武中"
        		+",杨春光"
        		+",王刚"
        		+",李立"
        		+",武威"
        		+",高昕"
        		+",张楠"
        		+",包跃基"
        		+",徐恺"
        		+",武烨"
        		+",姜大宇"
        		+",刘洋"
        		+",刘柳"
        		+",邓维科"
        		+",付宇"
        		+",刘宇"
        		+",王超"
        		+",赵波"
        		+",杨子良"
        		+",王萌"
        		+",程磊"
        		+",朱学良"
        		+",董健"
        		+",吴争"
        		+",程岩"
        		+",党育红"
        		+",韩笑千"
        		+",马超群"
        		+",李晓薇"
        		+",马元龙"
        		+",关军"
        		+",代由锐"
        		+",杨冬玲"
        		+",于跃"
        		+",郭琮"
        		+",张訔怡"
        		+",石玉刚"
        		+",王曌"
        		+",王天颖"
        		+",郝陈超"
        		+",仲夏"
        		+",冯超跃"
        		+",甘宇"
        		+",贺怡龙"
        		+",曹洪伟"
        		+",杨鑫龙"
        		+",张敬"
        		+",郭俊刚"
        		+",石磊"
        		+",戴鹏飞"
        		+",冯博亮"
        		+",王庆"
        		+",郭志利"
        		+",刘罡"
        		+",秦宏"
        		+",李军"
        		+",何成全"
        		+",宫朝勇"
        		+",王东兵"
        		+",李晋"
        		+",万颖"
        		+",李增新"
        		+",肖明"
        		+",张玮"
        		+",刘攀"
        		+",徐磊"
        		+",马志涛"
        		+",毛嘉亮"
        		+",杨晓东"
        		+",尚笑飞"
        		+",吕延"
        		+",袁莺"
        		+",张彪"
        		+",高琪"
        		+",梁石磊"
        		+",郑月刚"
        		+",赵志环"
        		+",徐斌"
        		+",周明磊"
        		+",陈阳"
        		+",于海龙"
        		+",王江"
        		+",胡先铭"
        		+",李明"
        		+",童昌"
        		+",尤晓青"
        		+",裴昂昂"
        		+",李鑫"
        		+",李蕾"
        		+",荆晶"
        		+",白靖"
        		+",郭祯"
        		+",汪铎"
        		+",周江磊";

        System.out.println(getPinYin(cnStr));  
//        System.out.println(getPinYinHeadChar(cnStr));  
//        System.out.println(getCnASCII(cnStr));  
    } 
}
