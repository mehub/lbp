<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://ajaxanywhere.sourceforge.net/" prefix="aa"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>企业管理</title>
	<%@include file="/include/header.jsp"%>
	<%@include file="/common/grid/grid.inc"%>
	<link href="<%=request.getContextPath()%>/thirdjs/jquery/ui1.9.2/css/jquery-ui-1.9.2.custom.min.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath()%>/index/css/admin_style.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath()%>/index/css/component.css" rel="stylesheet" type="text/css" />
	
	<link href="<%=request.getContextPath()%>/thirdjs/thickbox/thickbox.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath()%>/thirdjs/autocomplete/simpleAutoComplete.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath()%>/thirdjs/validator/css/validator.css" type="text/css" rel="stylesheet" />
	
	<script src="<%=request.getContextPath()%>/index/jquery-1.7.2.js"></script>
	<script src="<%=request.getContextPath()%>/thirdjs/jquery/ui1.9.2/js/jquery-ui-1.9.2.custom.min.js"></script>
	<script src="<%=request.getContextPath()%>/common/util.js"></script>
	<script src="<%=request.getContextPath()%>/common/windows.js"></script>
	<script src="<%=request.getContextPath()%>/common/function.js"></script>
	
	<script src="<%=request.getContextPath()%>/thirdjs/thickbox/thickbox.js"></script>
	<script src="<%=request.getContextPath()%>/thirdjs/autocomplete/simpleAutoComplete.js"></script>
	
	<script src="<%=request.getContextPath()%>/thirdjs/validator/formValidator.js"></script>
	<script src="<%=request.getContextPath()%>/thirdjs/validator/formValidatorRegex.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			$('#orgName_ac').simpleAutoComplete(path+"/mds/org/autoCompleteFranOrg.action");
		});
		function nextPage_file(start, limit){
			this.start=start || this.start;
			if(start == 0) this.start = 0;
			this.limit=limit || this.limit;			
			var paramObj={start:this.start, limit:this.limit};
			var form = document.forms["orgListTable"];
			url ="listFranOrg.action";
			doAjaxPost(form, url, paramObj);
		}

		//查看明细
		function detail(orgId){
			tb_show("明细","inputOrg.action?id="+orgId+"&detail=true&width=400&height=555",false); 
			$( "#TB_window" ).draggable({handle: "div#TB_title",cursor: "move"});
		}
	</script>
</head>
<body>
<div class="wrap">
	<h2 class="h1">搜索</h2>
    <div class="admin_table mb10">   
    	<form name="orgListTable" method="post" id="orgListTable"> 	
		<table width="100%" cellspacing="0" cellpadding="0">
			<tbody>
			<tr class="tr1 vt">
				<td class="td2">
		        	<span class="mr20">经销商名称：<input type="text" id="orgName_ac" name="orgName" class="input input_wa" /></span>
		           	<span class="mr20">编码：<input type="text" name="code" class="input input_wa" /></span>
		            <span class="mr5"><span class="bt2"><span><button type="button" onclick="nextPage_file(0)">查询</button></span></span></span>
		            <span class="mr5"><span class="bt2"><span><button type="button" onclick="javascript:formReset('orgListTable');">重置</button></span></span></span>
				</td>
			</tr>
			</tbody>
		</table>
		</form>
	</div>
	<h2 class="h1 cc"><span class="mr10 fl">经销商列表</span></h2>
    <div class="admin_table mb10">
    <aa:zone name="orgListTable">
    <table width="100%" border="0" cellspacing="0" cellpadding="0" id="companytable">
      <thead>
	      <tr class="tr2">
	        <td scope="col" width="10">
	        	<input type="checkbox" name="all_cbx" title="全选 " onclick="checkAll(this, 'code_cbx')" />
	        </td>
	        <td scope="col">ID</td>
	        <td scope="col" width="250">名称</td>
	        <td scope="col">编码</td>
	        <td scope="col">区域</td>
	        <td scope="col">级别</td>
	        <td scope="col">电话</td>
	        <td scope="col">传真</td>
	        <td scope="col">邮箱</td>
	        <td scope="col">网址</td>
	        <td scope="col">最后修改时间</td>
	        <td scope="col">状态</td>
	      </tr>
      </thead>
      <tbody>
      	<s:iterator value="franchiseList" id="org" status="st">
      		<tr class="tr1 vt">
	        	<td class="td2"><input type="checkbox" value="<s:property value="id" />" name="code_cbx"/></td>
		        <td class="td2"><s:property value="id" /></td>
		        <td class="td2"><a href="javascript:void(0)" onclick="detail('<s:property value="id" />')"><s:property value="orgName" /></a></td>
		        <td class="td2"><s:property value="code" /></td>
		        <td class="td2"><s:property value="regionName" /></td>
		        <td class="td2"><s:property value="xlevel" /></td>
		        <td class="td2"><s:property value="telephone" /></td>
		        <td class="td2"><s:property value="fax" /></td>
		        <td class="td2"><s:property value="email"/></td>
		        <td class="td2"><s:property value="url" /></td>
		        <td class="td2"><s:date name="lastmodifyTime" format="yyyy-MM-dd HH:mm:ss" /></td>	        
		        <td class="td2"><s:property value="state.value" /></td>
	        </tr>
      	</s:iterator>
      </tbody>	      
	</table>
	<div class="mb10 cc">
		<div class="pages" id="page_bar"><s:property value="pageBar" escape="0" escapeJavaScript="0" /></div>
	</div>
	</aa:zone>
	</div>
</div>
</body>
</html>