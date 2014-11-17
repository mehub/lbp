/**
 * 
 */
package com.asc.mds.root.isearch.helper;

/**
 * @author WuBo
 * @CreateDate 2011-4-27
 * @version 1.0.1
 */
public class TextEscaper {
	static final char[] sc = new char[]{'+','-','|','!','&','(',')','{','}',
		'[',']','^','"','~','*','?',':','/'}; 
	
	static final String[] chars = new String[] {"\\", "-", "+", "(", ")",
			"!", "{", "}", "[", "]", "^", "\"", "~", "*", "?", ":","/", " ",
			"OR", "AND", "NOT" };
	/**
	 * 对特殊字符进行转义
	 * @param text
	 * @return
	 */
	public static String escape(String text){
		if(text.matches(".*[\\/\\+\\&\\\\-\\|!\\(\\)\\{\\}\\[\\]\\^\\\"\\~\\*\\?\\:\\\\]+.*")){
			for(String c : chars){
				text = text.replace(c, "\\"+c);
			}
		}
		return text;
	}
	
	public static void main(String[] args) {
		System.out.println(escape("百多邦/10\\\\\\:"));
	}
	
}
