/**
 * 
 */
package com.asc.mds.root.isearch.helper;


/**<h3>一个简单的文本相似度算法</h3>
 * 此算法按单字符匹配，由字符匹配个数和字符出现顺序决定结果<br/>
 * @author WuBo
 * @CreateDate 2011-4-25
 * @version 1.0.1
 */
public class TextComparer {
	
	/**
	 * 返回两个文本之间的相似度浮点数，应保留四位小数
	 * @param text1
	 * @param text2
	 * @return
	 */
	public static float compare(String text1, String text2) {
		if(text1==null||text1.isEmpty() ||text2==null||text2.isEmpty()){
			return 0F;
		}
		text1 = text1.trim();
		text2 = text2.trim();
		if(text2.equals(text1)){
			return 1.0F;
		}
		char[] chars = text2.toCharArray();
		int len = chars.length;
		int[] pots = new int[len];
		int p = 0;//存放匹配词组的个数
		//计算匹配的词组个数
		for(int i=0;i<len-1;i++){
			int pot = 0;
			if( (pot = text1.indexOf(chars[i]+""+chars[i+1])) != -1 ){
				pots[p] = pot;
				p ++;
			}
		}
		//判断顺序的一致性
		int boost = 1;
		for(int i=1; i<p; i++){
			if(pots[i] > pots[i-1]){
				boost ++;
			}
		}
		int kl = text1.length();
		
		float pl = (float) (len-1);
		
		if(pl==0){
			return 0.0f;
		}
		//计算两个文本间字符匹配率，公式：匹配词组数/（字符长度-1） - （（字符长度-1）-顺序因子）/（字符长度-1）/10
		float result = (p/pl) - ((pl-boost)/pl/10F);
		if(result < 0.0){
			return 0.0f;
		}
		if(result < 0.7){
			return result;
		}
		//计算两个文本间字符长度相差因子
		float wcf = (float)Math.cbrt(Math.abs(kl - len)) / kl;
		
		if(result < wcf){
			return result;
		}
		
		return result-wcf;
	}

}
