package com.asc.mds.search.area.extracter;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.asc.mds.root.isearch.helper.TextExtracter;
import com.asc.mds.search.analysis.JSecgSpliter;
import com.asc.mds.search.area.LuceneQueryInfo;
import com.asc.mds.search.area.bean.Area;
import com.asc.mds.search.area.searcher.IAreaSearcher;
import com.asc.mds.search.area.searcher.IExtAreaSearcher;
/**
 * 短文本区域智能提取实现类
 * @author WuBo
 * @CreateDate 2011-4-28
 * @version 1.0.1
 */
public class AreaExtracter implements IAreaExtracter {
	
	private Logger log = Logger.getLogger(getClass());
	
	private IAreaSearcher as;
	private IExtAreaSearcher eas;
	
	public AreaExtracter(IAreaSearcher as, IExtAreaSearcher eas){
		this.as = as;
		this.eas = eas;
	}
	
	public Area extract(String text){
		try{
			log.info("搜索："+text);
			//对短文本进行分词
			String[] ss = JSecgSpliter.split(text);
			
			//存放查询数据对象
			List<LuceneQueryInfo> queryDatas = new ArrayList<LuceneQueryInfo>();
			String[] fs = new String[]{"province", "parentName", "regionName"};
			//将分词后的每一个词均到区域索引库搜索
			for(String s : ss){
				//当词的长度小于2时，直接忽略
				if(s.length() < 2){
					continue;
				}
				for(String field : fs){
					LuceneQueryInfo qd = as.search(field, s);
					if(qd != null){
						queryDatas.add(qd);
						break;//匹配高区域策略
					}
				}
			}
			int size = queryDatas.size();
			
			if(size < 1){
				//当短文本中不包含区域关键字时，到记忆库中搜索
				return searchFromMemory(text);
			}
			//对查询结果进行“并”运算
			Area area = as.joinSearch(queryDatas);
			int loop = size - 1;
			/*
			 * 当进行并运算后无匹配结果时，表明短文本中包含多个不同的区域关键字，即区域冲突
			 * 此时，如果查询数据对象集合中查询对象大于等于2时，一种做法是去掉最后一个区域关键字再次进行并运算
			 * 此种做法并不严格，可能会导致提取出错误的区域
			 */
			while(area == null && loop >= 1){
				log.info("Loop:"+loop);
				if(loop == 1){
					LuceneQueryInfo qd = queryDatas.get(0);
					if("province".equals(qd.getField())){
						//对于最后的“省”区域关键字，由于只有省，我们无法知道“市”一级的区域，所以同样先到记忆库中搜索
						Area tempArea = searchFromMemory(text);
						if(tempArea != null){
							tempArea.setHasConflict(true);
							return tempArea;
						}
						break;
					}
				}
				/*
				 * 判断即将被去掉的区域关键字是否属于“分公司”有关的关键字
				 * 当满足以下条件时，取此区域关键字为最后匹配的区域：
				 * 1、匹配市一级或县（区）一级
				 * 2、短文本中包含“分公司”关键字
				 * 3、“分公司”关键字在短文本中索引位置大于区域关键字索引位置且距离不超过4
				 */
				LuceneQueryInfo lastQD = queryDatas.get(loop);
				if("parentName".equals(lastQD.getField()) || "regionName".equals(lastQD.getField())){
					int k_index = text.indexOf(lastQD.getKeyword());
					int s_index = TextExtracter.indexOf(text);
					if(s_index > k_index && (s_index - s_index) <= 8){
						area = as.joinSearch(queryDatas.subList(loop, loop+1));
						if(area != null){
							log.info("分公司 - 区域关键字："+lastQD.getKeyword());
							area.setHasConflict(true);
							return area;
						}
						break;
					}
				}
				
				area = as.joinSearch(queryDatas.subList(0, loop--));
				if(area != null){
					area.setHasConflict(true);
				}
			}
			return area;
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally{
			
		}
	}
	
	private Area searchFromMemory(String keyword) throws Exception {
		if(eas == null){
			return null;
		}
		return eas.search(keyword);
	}
}
