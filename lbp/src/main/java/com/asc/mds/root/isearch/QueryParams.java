package com.asc.mds.root.isearch;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * 检索中使用的各种属性参数
 * @author wangshaohu
 * @date 2011-4-20 下午02:18:59
 * @version 1.0.0
 */
public class QueryParams {

	public final static String SORT_ASC = "asc";
	public final static String SORT_DESC = "desc";

	private final static String BLANK = " ";

	private int start; 										// start 起始记录数
	private int rows; 										// rows 最大记录数
	private String[] fieldList; 							// fl 返回结果集包含的字段列表
	private String[] hlFieldList; 							// hl.fl 高亮显示的字段列表
	private List<Sort> sorts = new ArrayList<Sort>();

	public QueryParams() throws IllegalArgumentException {
		this(0, 5);
	}

	public QueryParams(int start, int rows) throws IllegalArgumentException {
		this.start = start;
		this.rows = rows;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String[] getFieldList() {
		return fieldList;
	}

	public void setFieldList(String[] fieldList) {
		this.fieldList = fieldList;
	}

	public String[] getHlFieldList() {
		return hlFieldList;
	}

	public void setHlFieldList(String[] hlFieldList) {
		this.hlFieldList = hlFieldList;
	}

	public void addSort(Sort sort) {
		if (sort == null) {
			return;
		}
		if (sort.name.isEmpty()) {
			return;
		}
		if (!sort.dirc.equalsIgnoreCase(SORT_ASC)
				&& !sort.dirc.equalsIgnoreCase(SORT_DESC)) {
			return;
		}
		sorts.add(sort);
	}

	/**
	 * 返回搜索接口要求的排序字符串，排序字段和方式间用空格分隔，多个排序字段用”;“间隔。
	 * 
	 * @return
	 */
	private String getSorts() {
		StringBuilder sb = new StringBuilder();
		for (Sort sort : sorts) {
			sb.append(sort.name).append(BLANK).append(sort.dirc.toLowerCase())
					.append(";");
		}
		return sb.toString();
	}

	/**
	 * 根据QueryInfo中的相关信息组成搜索接口要求的搜索格式，用于后续的查询操作
	 * 
	 * @return 用于搜索的字符串，相关信息用java.net.URLEncoder的encode方法加密
	 * @throws UnsupportedEncodingException
	 */
	public String getQueryStr() throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder("&");
		sb.append("start=").append(start).append("&").append("rows=").append(rows).append("&");

		String fieldStr = null;
		if (fieldList == null || fieldList.length == 0) {
			fieldStr = "*,score";
		} else {
			fieldStr = arrayToString(fieldList);
		}
		sb.append("fl=").append(URLEncoder.encode(fieldStr, "UTF-8")).append("&");

		if (hlFieldList != null && hlFieldList.length != 0) {
			String hlFieldStr = arrayToString(hlFieldList);
			sb.append("hl.fl=").append(URLEncoder.encode(hlFieldStr, "UTF-8")).append("&");
		}

		if (!getSorts().isEmpty()) {
			sb.append("sort=").append(URLEncoder.encode(getSorts(), "UTF-8")).append("&");
		}

		sb.append("wt=xml&indent=true");
		return sb.toString();
	}

	private String arrayToString(String[] strs) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < strs.length; i++) {
			if (i > 0) {
				sb.append(",");
			}
			sb.append(strs[i]);
		}
		return sb.toString();
	}

	class Sort {

		private String name; // 排序字段名
		private String dirc; // 排序方式

		public Sort(String name, String dirc) {
			this.name = name;
			this.dirc = dirc;
		}

	}

}
