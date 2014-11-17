<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://ajaxanywhere.sourceforge.net/" prefix="aa"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>企业类型</title>
	<%@include file="/include/header.jsp"%>
	<%@include file="/common/grid/grid.inc"%>
	<link href="<%=request.getContextPath()%>/thirdjs/jquery/ui1.9.2/css/jquery-ui-1.9.2.custom.min.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath()%>/index/css/admin_style.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath()%>/common/css/common.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath()%>/index/css/component.css" rel="stylesheet" type="text/css" />
	
	<link href="<%=request.getContextPath()%>/thirdjs/thickbox/thickbox.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath()%>/thirdjs/validator/css/validator.css" type="text/css" rel="stylesheet" />
	
	<script src="<%=request.getContextPath()%>/thirdjs/jquery/jquery-1.8.3.js"></script>
	<script src="<%=request.getContextPath()%>/thirdjs/jquery/ui1.9.2/js/jquery-ui-1.9.2.custom.min.js"></script>
	<script src="<%=request.getContextPath()%>/thirdjs/jquery/jquery.form.js"></script>
	<script src="<%=request.getContextPath()%>/common/util.js"></script>
	<script src="<%=request.getContextPath()%>/common/windows.js"></script>
	<script src="<%=request.getContextPath()%>/common/function.js"></script>
	<script src="<%=request.getContextPath()%>/common/formutil.js"></script>
	
	<script src="<%=request.getContextPath()%>/thirdjs/thickbox/thickbox.js"></script>
	<script src="<%=request.getContextPath()%>/thirdjs/validator/formValidator.js"></script>
	<script src="<%=request.getContextPath()%>/thirdjs/validator/formValidatorRegex.js"></script>
	
	
	<script type="text/javascript">
		function nextPage_orgType(start, limit){
			this.start=start || this.start;
			if(start == 0) this.start = 0;
			this.limit=limit || this.limit;			
			var paramObj={start:this.start, limit:this.limit};
			var form = document.forms["searchForm"];
			url ="listOrgType.action";
			doAjaxPost(form, url, paramObj);
		}
		function add(id){
			tb_show("新增（标为<span class='high_star'>*</span>必填项）", "orgTypeUpdate.jsp?width=400&height=130");
			$( "#TB_window" ).draggable();
		}
		function update(id){
			tb_show("变更（标为<span class='high_star'>*</span>必填项）","inputOrgType.action?id=" + id + "&width=400&height=130", false); 
			$( "#TB_window" ).draggable();
		}
	</script>
</head>
<body>
<div class="wrap">
	<h2 class="h1 cc"><span class="mr10 fl">企业类型</span>
		<span class="fr">
			<span class="linka fl mr5">
    			<a class="btn_add fl" href="javascript:void(0)" onclick="nextPage_orgType();"><i>刷新</i></a>
    		</span>
			<sec:authorize ifAnyGranted="ADMIN, PRODUCT_ADD">
	    		<span class="linka fl mr5">
	    			<a class="btn_add fl" href="javascript:void(0)" onclick="add();"><i>新增</i></a>
	    		</span>
    		</sec:authorize>
		</span>
	</h2>

    <div class="admin_table mb10">
    <form name="searchForm" method="post" id="searchForm"></form>
    <aa:zone name="searchForm">
    <table width="100%" border="0" cellspacing="0" cellpadding="0" id="typetable">
      <thead>
	      <tr class="tr2 td_bgB">
	        <td>
	        	<input type="checkbox" name="all_cbx" title="全选 " onclick="checkAll(this, 'code_cbx')" />
	        </td>
	        <td scope="col">ID</td>
	        <td scope="col">类型名称</td>
	        <td scope="col">类型编码</td>
	        <td scope="col">操作</td>
	      </tr>
      </thead>
      <tbody>
      	<s:iterator value="orgTypeList" id="orgType" status="st">
      		<tr class="tr1 vt">
	        	<td class="td2"><input type="checkbox" name="code_cbx" value="<s:property value="id" />"/></td>
		        <td class="td2"><s:property value="id" /></td>
		        <td class="td2"><s:property value="typeName" /></td>
		        <td class="td2"><s:property value="code"/></td>
		        <td class="td2">
		        	<a href="#none" onclick="update('<s:property value="id"/>')">[变更]</a>
		        </td>
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
<s:if test="msg">
	<!-- 显示操作成功 -->
	<script type="text/javascript">
		showTip("操作成功");
	</script>
</s:if>
</body>
</html>