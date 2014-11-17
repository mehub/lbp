package com.asc.mds.search.area.searcher;

import org.apache.lucene.document.Document;
//import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.util.Version;

import com.asc.mds.root.isearch.helper.TextComparer;
import com.asc.mds.root.isearch.helper.TextEscaper;
import com.asc.mds.root.isearch.helper.TextExtracter;
import com.asc.mds.search.analysis.AnalyzerFactory;
import com.asc.mds.search.area.LuceneIndexSearcher;
import com.asc.mds.search.area.bean.Area;
import com.asc.mds.search.area.bean.ExtArea;

public class ExtAreaSearcher extends LuceneIndexSearcher implements IExtAreaSearcher {

	public ExtAreaSearcher(String indexFilePath){
		super(indexFilePath);
	}

	
	public Area search(String keyword) throws Exception {
		if(this.searcher == null){
			log.error("未找到搜索器");
			return null;
		}
		String text = TextEscaper.escape(TextExtracter.extract(keyword));

		log.info("从记忆库中搜索区域："+text);
		
		/*TopDocs topdocs = getSearcher().search(new QueryParser(Version.LUCENE_43, "NAME", 
			AnalyzerFactory.getQueryAnalyzer()).parse(text), 1);*/
		
//		TopDocs topdocs = getSearcher().search(new QueryParser(Version.LUCENE_36, "NAME", 
//				AnalyzerFactory.getQueryAnalyzer()).parse(text), 1);
		
		TopDocs topdocs = null;//TODO
		
		ScoreDoc[] docs = topdocs.scoreDocs;
		if(docs.length > 0){
			Document document = getSearcher().doc(docs[0].doc);
			String name = document.get("NAME");
			String province = document.get("FIRST");
			String city = document.get("SECOND");
			float similarity = TextComparer.compare(text, name);
			return new Area( new ExtArea(province, city, text, name, similarity) );
		}
		return null;
	}
	
}