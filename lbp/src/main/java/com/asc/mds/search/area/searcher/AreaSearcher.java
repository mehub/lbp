/**
 * 
 */
package com.asc.mds.search.area.searcher;

import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;

import com.asc.mds.search.area.LuceneIndexSearcher;
import com.asc.mds.search.area.LuceneQueryInfo;
import com.asc.mds.search.area.bean.Area;

/**
 * @author WuBo
 * @CreateDate 2011-4-29
 * @version 1.0.1
 */
public class AreaSearcher extends LuceneIndexSearcher implements IAreaSearcher {
	
	public AreaSearcher(String indexFilePath){
		super(indexFilePath);
	}
	
	public LuceneQueryInfo search(String field, String text) throws Exception {
		if(this.searcher == null){
			log.error("未找到搜索器");
			return null;
		}
		Query temp = new WildcardQuery(new Term(field, text+"*"));
		TopDocs topdocs = getSearcher().search(temp, 1);
		//当有结果命中时将查询数据对象添加到集合中
		if(topdocs.totalHits > 0){
			log.info("Field:"+field+" Keyword:"+text+" Hits:"+topdocs.totalHits);
			return new LuceneQueryInfo(temp, field, text, topdocs.totalHits);
		}
		return null;
	}
	
	public Area joinSearch(List<LuceneQueryInfo> queryDatas) throws Exception {
		BooleanQuery query = new BooleanQuery();
		for(LuceneQueryInfo qd : queryDatas){
			query.add(qd.getQuery(), BooleanClause.Occur.MUST);
		}
		TopDocs topdocs = getSearcher().search(query, 1);
		
		ScoreDoc[] docs = topdocs.scoreDocs;
		if(docs.length > 0){
			Document document = getSearcher().doc(docs[0].doc);
			String country = document.get("country");
			String province = document.get("province");
			String city = document.get("parentName");
			String county = document.get("regionName");
			String xlevel = document.get("xlevel");
			return new Area(country, province, city, county, xlevel);
		}
		return null;
	}

}
