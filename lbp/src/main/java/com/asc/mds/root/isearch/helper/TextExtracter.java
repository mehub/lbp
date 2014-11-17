/**
 * 
 */
package com.asc.mds.root.isearch.helper;

/**
 * @author WuBo
 * @CreateDate 2011-4-26
 * @version 1.0.1
 */
public class TextExtracter {
	
	public static String extract(String text) {
		String result = text;
		int index = 0;
		if((index = text.indexOf("股份有限公司")) != -1){
			result = text.substring(0, index);
		}else if((index = text.indexOf("有限公司")) != -1){
			result = text.substring(0, index);
		}else if((index = text.indexOf("有限责任公司")) != -1){
			result = text.substring(0, index);
		}else if((index = text.indexOf("股份公司")) != -1){
			result = text.substring(0, index);
		}else if((index = text.indexOf("制药厂")) != -1){
			result = text.substring(0, index);
		}else if((index = text.indexOf("部队")) != -1){
			result = text.substring(0, index+2);
		}else if((index = text.indexOf("(集团)")) != -1){
			result = text.substring(0, index+4);
		}else if((index = text.indexOf("集团")) != -1){
			result = text.substring(0, index+2);
		}
		return result;
	}
	
	private static String[] parts = new String[]{"分公司","办事处"};
	public static int indexOf(String text){
		int i = -1;
		for(String s : parts){
			if( (i = text.indexOf(s)) != 1)
				return i;
		}
		return i;
	}
	
}
