/**
 * 
 */
package com.asc.mds.search.area;

import com.asc.common.util.InitConfUtils;
import com.asc.mds.search.area.bean.Area;
import com.asc.mds.search.area.extracter.AreaExtracter;
import com.asc.mds.search.area.extracter.IAreaExtracter;
import com.asc.mds.search.area.searcher.AreaSearcher;
import com.asc.mds.search.area.searcher.ExtAreaSearcher;
import com.asc.mds.search.area.searcher.IAreaSearcher;
import com.asc.mds.search.area.searcher.IExtAreaSearcher;

/**
 * 
 * @author WuBo
 * @CreateDate 2011-4-29
 * @version 1.0.1
 *
 */
public class AreaExtracterUtil {
	
	private static IAreaExtracter ae;
	private static IAreaSearcher as;
	private static IExtAreaSearcher mas;
	
	static{
		as = new AreaSearcher(InitConfUtils.getParamValue("search.index.path.area"));
		mas = new ExtAreaSearcher(InitConfUtils.getParamValue("search.index.path.area.ext"));
		ae = new AreaExtracter(as, mas);
	}
	
	public static Area extrater(String text){
		return ae.extract(text);
	}
	
	public static String extraterstr(String text){
		return parseToXML(ae.extract(text));
	}
	
	private static String parseToXML(Area area) {
		if(area == null) return null;
		StringBuffer res = new StringBuffer();
		res.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		res.append("<area>\n");
		res.append(" <country>"+getValue(area.getCountry())+"</country>\n");
		res.append(" <province>"+getValue(area.getProvince())+"</province>\n");
		res.append(" <city>"+getValue(area.getCity())+"</city>\n");
		res.append(" <county>"+getValue(area.getCounty())+"</county>\n");
		res.append(" <hasconflict>"+area.isHasConflict()+"</hasconflict>\n");
		if(area.getMar() != null){
			res.append(" <memoryarea>\n");
			res.append("  <keyword>"+getValue(area.getMar().getKeyword())+"</keyword>\n");
			res.append("  <name>"+getValue(area.getMar().getName())+"</name>\n");
			res.append("  <similarity>"+floatToPercent( area.getMar().getSimilarity() )+"</similarity>\n");
			res.append(" </memoryarea>\n");
		}else{
			res.append(" <memoryarea/>\n");
		}
		res.append("</area>");
		return res.toString();
	}
	
	public String parseToCSV(Area area){
		StringBuffer res = new StringBuffer();
		res.append(getValue(area.getCountry()));
		res.append(","+getValue(area.getProvince()));
		res.append(","+getValue(area.getCity()));
		res.append(","+getValue(area.getCounty()));
		res.append(","+area.isHasConflict());
		if(area.getMar() != null){
			res.append(","+getValue(area.getMar().getKeyword()));
			res.append(","+getValue(area.getMar().getName()));
			res.append(","+floatToPercent( area.getMar().getSimilarity() ));
		}
		return res.toString();
	}
	
	private static String getValue(String text){
		if(text != null){
			return text;
		}
		return "";
	}
	private static String floatToPercent(float f){
		return Math.round(f * 10000) / 100F + "%";
	}
	
}
