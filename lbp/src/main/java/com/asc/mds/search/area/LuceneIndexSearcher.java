package com.asc.mds.search.area;

import java.io.File;

import org.apache.log4j.Logger;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 * @author WuBo
 * @CreateDate 2011-4-29
 * @version 1.0.1
 */
public abstract class LuceneIndexSearcher {
	
	protected Directory directory;
	protected String indexPath;
	protected IndexSearcher searcher;
	
	protected Logger log = Logger.getLogger(getClass());
	
	public LuceneIndexSearcher(String indexPath){
		this.indexPath = indexPath;
		try {
			this.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void init() throws Exception {
		directory = FSDirectory.open(new File(indexPath));
//		IndexReader reader = DirectoryReader.open(directory);
		IndexReader reader = IndexReader.open(directory);
		searcher = new IndexSearcher(reader);
	}

	public void close(){
		if(directory != null){
			try{directory.close();}catch(Exception e){}
		}
	}

	protected Directory getDirectory() {
		return directory;
	}

	protected String getIndexPath() {
		return indexPath;
	}

	protected IndexSearcher getSearcher() {
		return searcher;
	}
	
}
