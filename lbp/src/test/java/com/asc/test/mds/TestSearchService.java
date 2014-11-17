/**
 * 
 */
package com.asc.test.mds;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.CacheMode;
import org.hibernate.FlushMode;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.stat.Statistics;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.asc.common.persist.IPersistDao;
import com.asc.mds.root.EntitySolrSetting;
import com.asc.mds.root.bean.CustomerAlias;
import com.asc.mds.root.bean.Org;
import com.asc.mds.root.bean.Region;
import com.asc.mds.root.isearch.SolrUpdater;
import com.asc.search.hibernate4.HibernateSearcher;
import com.asc.search.hibernate4.IndexingProgressMonitor;
import com.asc.test.TestBase;

/**
 * 
 * 类描述 .
 * @author chenzhenling
 * @version 版本信息 创建时间 2013-6-24 上午11:51:02
 */
public class TestSearchService extends TestBase {

	@Autowired
	private IPersistDao dao;
	
	@Autowired
	private HibernateSearcher hibernateSearcher;
	
	
	@Test
	@Ignore
	public void testIndex() throws InterruptedException {
		Org org = new Org();
		org.setId("333");
		org.setOrgName("solr测试333");
		org.setCode("333");
		String id = (String) dao.save(org);
		System.out.println("id:"+id);
		
		SolrUpdater.searcherUpdate(EntitySolrSetting.Org);//solr index update
		
	}
	
	@Test
	public void testIndexCom() throws InterruptedException {
		hibernateSearcher.flushToIndexes(new IndexingProgressMonitor(), CustomerAlias.class);
		hibernateSearcher.optimize(CustomerAlias.class);
	}
	
	@Test
	@Ignore
	public void testSql() throws InterruptedException {
		dao.executeSQL("insert into md_org (id,code,name) values ('123','123','insert测试')", new Object[]{});
//		dao.executeSQL("update md_org set name = 'update测试' where id = '123' ", new Object[]{});
	}
	
	@Test
	@Ignore
	public void testIndexAllMass() throws InterruptedException {
		FullTextSession fullTextSession = Search.getFullTextSession(dao.session());
		fullTextSession.createIndexer(Region.class)
				.batchSizeToLoadObjects(10)
				.threadsToLoadObjects(1)
				.idFetchSize(150)
				.threadsForSubsequentFetching(1)
				.startAndWait();
	}
	
	@Test
	@Ignore
	public void flushToIndexes(){
		Class<?> types = CustomerAlias.class;
		int BATCH_SIZE = 20;
		FullTextSession fullTextSession = Search.getFullTextSession(dao.session());
		fullTextSession.setFlushMode(FlushMode.MANUAL);
		fullTextSession.setCacheMode(CacheMode.IGNORE);
		ScrollableResults results = fullTextSession.createCriteria(types)
				.setFetchSize(BATCH_SIZE).scroll(ScrollMode.FORWARD_ONLY);
		int index = 0;
		while (results.next()) {
			index++;
			fullTextSession.index(results.get(0));
			if (index % BATCH_SIZE == 0) {
				fullTextSession.flushToIndexes();
				fullTextSession.clear(); 
			}
		}
		fullTextSession.flushToIndexes();
		fullTextSession.flush();
		fullTextSession.clear();
	}

	@Test
	@Ignore
	public void testSearch() {
		List<Org> result = hibernateSearcher.queryWithScore(Org.class
				, new String[]{"orgName"}, "丰县史"
				, Org.projections
				, 0, 10);
		List<Org> res = hibernateSearcher.queryByKeyWord(Org.class, new String[]{"orgName"}, "丰县史", 0, 10);
		for(Org org : res)
			System.out.println(org.getOrgName() + "," + org.getClassify());
		for(Org o : result)
			System.out.println(o.getId() + "," + o.getHSearch_Score() + "," + o.getRegion().getId() + "," + o.getRegion().getRegionName() + "," + o.getOrgName());
	}
	
	@Test
	@Ignore
	public void testCustomerAlias() {
		List<CustomerAlias> result = hibernateSearcher.queryWithScore(CustomerAlias.class
				, new String[]{"ctName"}, "国药天津有限公司"
				, CustomerAlias.projections
				, 0, 10);
		for(CustomerAlias o : result)
			System.out.println(o.getId() + "," + o.getHSearch_Score() + "," + o.getCtName());
	}
	
	@Test
	@Ignore
	public void testSearchStatistics() {
		Statistics stat = hibernateSearcher.getStatistics();
		System.out.println(stat.getNumberOfIndexedEntities("com.asc.mds.bean.Org"));
		Set<String> cns = stat.getIndexedClassNames();
		Iterator<String> its = cns.iterator();
		while(its.hasNext()){
			String clazz = its.next();
			System.out.println(clazz);
		}
	}

}
