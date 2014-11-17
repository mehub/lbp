/**
 * 
 */
package com.asc.mds.search.analysis;

import java.io.IOException;
import java.io.Reader;
import java.util.Map;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.solr.analysis.BaseTokenizerFactory;
import org.lionsoul.jcseg.analyzer.JcsegTokenizer;
import org.lionsoul.jcseg.core.JcsegException;

/**
 * 
 * 类描述 .jcseg for lucene3.x
 * @author chenzhenling
 * @version 版本信息 创建时间 2013-6-25 下午3:38:44
 */
public class JcsegTokenizerFactory3X extends BaseTokenizerFactory {

	private int mode = 2;	//default 2 complex

	public void init(Map<String, String> args) {
		super.init(args);
	    String str = (String)args.get("mode");
	    if (str == null)
	    {
	      this.mode = 2;
	    }
	    else
	    {
	      str = str.toLowerCase();
	      if ("simple".equals(str))
	        this.mode = 1;
	      else
	        this.mode = 2;
	    }
	}

	public Tokenizer create(Reader input) {
		try {
			return new JcsegTokenizer(input, this.mode,null,null);//TODO
		} catch (JcsegException localJCSegException) {
			localJCSegException.printStackTrace();
		} catch (IOException localIOException) {
			localIOException.printStackTrace();
		}
		return null;
	}

}
