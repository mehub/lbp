<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="aa" uri="http://ajaxanywhere.sourceforge.net/"%>
<%@taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="x-ua-compatible" content="ie=7" />
	<title>客户企业别称</title>
	<%@include file="/include/header.jsp"%>
	<%@include file="/common/grid/grid.inc"%>
	<link href="<%=request.getContextPath()%>/index/css/admin_style.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath()%>/common/css/common.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath()%>/index/css/component.css" rel="stylesheet" type="text/css" />
	
	<link href="<%=request.getContextPath()%>/thirdjs/thickbox/thickbox.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath()%>/thirdjs/autocomplete/simpleAutoComplete.css" rel="stylesheet" type="text/css" />
	
	<script src="<%=request.getContextPath()%>/thirdjs/jquery/jquery-1.8.3.js"></script>
	<script src="<%=request.getContextPath()%>/thirdjs/jquery/jquery.form.js"></script>
	<script src="<%=request.getContextPath()%>/common/util.js"></script>
	<script src="<%=request.getContextPath()%>/common/windows.js"></script>
	<script src="<%=request.getContextPath()%>/thirdjs/combox/combox.js"></script>
	<script src="<%=request.getContextPath()%>/common/function.js"></script>
	<script src="<%=request.getContextPath()%>/common/formutil.js"></script>
	
	<script src="<%=request.getContextPath()%>/thirdjs/thickbox/thickbox.js"></script>
	<script src="<%=request.getContextPath()%>/thirdjs/autocomplete/simpleAutoComplete.js"></script>
	
	<script type="text/javascript">
		$(document).ready(function(){
			$('#ctName_ac').simpleAutoComplete(path+"/mds/factoryorg/autoCompleteCustomerAlias.action");
		});
		function nextPage_customerAlias(start, limit){			
			this.start=start || this.start;
			if(start == 0) this.start = 0;
			this.limit=limit || this.limit;			
			var paramObj={start:this.start, limit:this.limit};
			var form = document.forms["searchForm"];
			url = path+"/mds/factoryorg/listCustomerAlias.htm";
			doAjaxPost(form, url, paramObj);
		}
		function doSearch(button){		
			action = path+"/mds/factoryorg/listCustomerAlias.htm";
			return doAjax(button, action);
		}
	</script>
</head>

<body>
<%@include file="/include/body.jsp"%>
<s:if test="flag">
	<script type="text/javascript">showTip("操作成功");</script>
</s:if>

<div class="wrap">
	<h2 class="h1">搜索 
		<span class="linka"><a href="javascript:void(0)" class="mr10" onclick="toggleArea(this,'query_area');">［收起］</a></span>
	</h2>
	<div class="admin_table mb10" id="query_area" style="display: block;">
		<s:form name="searchForm" action="listFactoryOrg.action" method="post" id="searchForm">
			<table width="100%" cellspacing="0" cellpadding="0">
				<tbody>
					<tr class="tr1 vt">
						<td class="td2">
							CT_NAME：<s:textfield id="ctName_ac" name="ctName" cssClass="input"></s:textfield>
							<input title="模糊检索" id="isLike" type="checkbox" name="useLike" value="true"/>
							编码：<s:textfield name="code" cssClass="input"></s:textfield>
							所属企业ID：<s:textfield name="ownerAscId" cssClass="input"></s:textfield>
							<span class="mr5"><span class="bt2"><span><button type="submit" onclick="return doSearch(this);">查询</button></span></span>	</span>
							<span class="mr5"><span class="bt2"><span><button type="button" onclick="javascript:formReset('searchForm');">重置</button></span></span></span>
						</td>
					</tr>
				</tbody>
			</table>
		</s:form>
	</div>
	<h2 class="h1 cc" id="h_btns"><span class="mr10 fl">企业列表</span> 
		<span class="fr">
			<span class="linka fl mr5">  
    			<a class="btn_add fl" href="templateDownloadCustomerAlias.action"><i>模板下载</i></a>
    		</span>
    	</span>
	 <span class="linka"><a href="javascript:void(0)" class="mr10" onclick="toggleArea(this,'show_area');">［收起］</a></span> 
	</h2>
	
	<div class="admin_table mb10" id="show_area">
		<aa:zone name="searchForm">
		<table width="100%;" border="0" cellspacing="0" cellpadding="0" id="companytable">
			<thead>
			<tr class="tr2">
				<td scope="col"><input type="checkbox" name="all_cbx" title="全选 " onclick="checkAll(this, 'code_cbx')" /></td>
		  		<td scope="col">ID</td>
		  		<td scope="col">所属企业ID</td>
		  		<td scope="col">所属企业CODE</td>
		  		<td scope="col">CT_NAME</td>
		  		<td scope="col">名称</td>
		  		<td scope="col">编码</td>
		  		<td scope="col">大区 </td>
		  		<td scope="col">省</td>
		  		<td scope="col">市</td>
		  		<td scope="col">县</td>
		  		<td scope="col">日期版本</td>
		  		<td scope="col">创建人</td>
		  		<td scope="col">创建时间</td>
			</tr>
			</thead>
			<tbody>
			<s:iterator value="list" status="status" var="rp" >
				<tr class="tr1 vt">		 
					<td class="td2"><input type="checkbox" name="code_cbx" value="<s:property value="id"/>" /></td>	
					<td class="td2"><s:property value="id"/></td>
					<td class="td2"><s:property value="ownerAscId"/></td>
					<td class="td2"><s:property value="ownerAscCode"/></td>
					<td class="td2"><s:property value="ctName"/></td>
					<td class="td2"><s:property value="orgName"/></td>
					<td class="td2"><s:property value="code"/></td>
					<td class="td2"><s:property value="region"/></td>
					<td class="td2"><s:property value="province"/></td>
					<td class="td2"><s:property value="city"/></td>
					<td class="td2"><s:property value="county"/></td>		
					<td class="td2"><s:property value="dateVersion"/></td>
					<td class="td2"><s:property value="creator"/></td>
					<td class="td2"><s:date format="yyyy-MM-dd HH:mm:ss" name="createTime"/></td>
				</tr>
			</s:iterator>
			</tbody>
		</table>
		<div class="mb10 cc">
			<div class="pages" id="page_bar"><s:property value="pageBar" escape="0" escapeJavaScript="0"/></div>
		</div>
		</aa:zone>
	</div>
	<s:if test="msg!=null">
		<script type="text/javascript">
			$(document).ready(function(){
				var msg='<s:property value="msg" escape="false"/>';
				Asc.windows.alert('提示',msg);
			})
		</script>
		
	</s:if>
</div>

</body>
</html>