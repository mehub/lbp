<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="aa" uri="http://ajaxanywhere.sourceforge.net/"%>
<%@taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="x-ua-compatible" content="ie=7" />
	<title>经销商企业关系</title>
	<%@include file="/include/header.jsp"%>
	<%@include file="/common/grid/grid.inc"%>
	<link href="<%=request.getContextPath()%>/thirdjs/jquery/ui1.9.2/css/jquery-ui-1.9.2.custom.min.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath()%>/index/css/admin_style.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath()%>/common/css/common.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath()%>/index/css/component.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath()%>/index/css/upload.css" rel="stylesheet" type="text/css" />
	
	<link href="<%=request.getContextPath()%>/thirdjs/thickbox/thickbox.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath()%>/thirdjs/autocomplete/simpleAutoComplete.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath()%>/thirdjs/validator/css/validator.css" type="text/css" rel="stylesheet" />
	
	<script src="<%=request.getContextPath()%>/thirdjs/jquery/jquery-1.8.3.js"></script>
	<script src="<%=request.getContextPath()%>/thirdjs/jquery/ui1.9.2/js/jquery-ui-1.9.2.custom.min.js"></script>
	<script src="<%=request.getContextPath()%>/thirdjs/jquery/jquery.form.js"></script>
	<script src="<%=request.getContextPath()%>/common/util.js"></script>
	<script src="<%=request.getContextPath()%>/common/windows.js"></script>
	<script src="<%=request.getContextPath()%>/thirdjs/combox/combox.js"></script>
	<script src="<%=request.getContextPath()%>/common/function.js"></script>
	<script src="<%=request.getContextPath()%>/common/formutil.js"></script>
	<script src="<%=request.getContextPath()%>/thirdjs/upload/upload.js"></script>
	
	<script src="<%=request.getContextPath()%>/thirdjs/thickbox/thickbox.js"></script>
	<script src="<%=request.getContextPath()%>/thirdjs/autocomplete/simpleAutoComplete.js"></script>
	
	<script src="<%=request.getContextPath()%>/thirdjs/validator/formValidator.js"></script>
	<script src="<%=request.getContextPath()%>/thirdjs/validator/formValidatorRegex.js"></script>

	<script src="<%=request.getContextPath()%>/thirdjs/datepicker/WdatePicker.js"></script>
	
	<script type="text/javascript">
		$(document).ready(function(){
			$('#orgName_ac').simpleAutoComplete(path+"/mds/franchiseorg/autoCompleteFranchiseOrg.action");
			$('#ownerAscName_ac').simpleAutoComplete(path+"/mds/franchiseorg/autoCompleteOrg.action");
		});
		
		function nextPage_franchiseOrg(start, limit){			
			this.start=start || this.start;
			if(start == 0) this.start = 0;
			this.limit=limit || this.limit;			
			var paramObj={start:this.start, limit:this.limit};
			var form = document.forms["searchForm"];
			url = path+"/mds/franchiseorg/listFranchiseOrg.htm";
			doAjaxPost(form, url, paramObj);
		}
		function doSearch(button){		
			action = path+"/mds/franchiseorg/listFranchiseOrg.htm";
			return doAjax(button, action);
		}
		function update(id){
			tb_show("变更（标为<span class='high_star'>*</span>必填项）", path+"/mds/franchiseorg/getFranchiseOrg.action?flag=update&id="+id+"&height=460&width=450");
			$( "#TB_window" ).draggable();
		}
		function detail(id,state){
			tb_show("明细（标为<span class='high_star'>*</span>必填项）", path+"/mds/franchiseorg/getFranchiseOrg.action?flag=detail&id="+id+"&height=540&width=400");
			$( "#TB_window" ).draggable();
		}
		function qualityCheck(id,quality){
			tb_show("质检标签", path+"/mds/franchiseorg/getFranchiseOrg.action?flag=qualityCheck&id="+id+"&qualityCheck="+quality+"&height=100&width=400");
			$( "#TB_window" ).draggable();
		}
		
		function remove(id){
			Asc.windows.confirm('提示','确认删该企业及其临时表？',function(c){
				if(c=='yes'){
					$.ajax({
					   type:"POST",
					   url:path+"/mds/franchiseorg/removeFranchiseOrg.htm",
					   data:"id=" + id,
					   success:function(msg){
					   		if(msg ==  '1'){
					   			Asc.windows.alert('提示',"操作成功!");
					   			$('#searchForm').submit();
					   		}else{
					   			Asc.windows.alert('提示',"操作失败,请重试!");
					   		}
					   }
					});
				}
				this.closeWindow();
			});
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
		<s:form name="searchForm" action="listFranchiseOrg.action" method="post" id="searchForm">
			<table width="100%" cellspacing="0" cellpadding="0">
				<tbody>
					<tr class="tr1 vt">
						<td class="td2">
							所属企业：<s:textfield id="ownerAscName_ac" name="ownerAscName" cssClass="input"></s:textfield>
							名称：<s:textfield id="orgName_ac" name="orgName" cssClass="input"></s:textfield>
							<input title="模糊检索" id="isLike" type="checkbox" name="useLike" value="true"/>
							编码：<s:textfield name="code" cssClass="input"></s:textfield>
							数据状态: 
							<s:select name="state" list="@com.asc.mds.root.state.OperateState@values()" cssClass="input" 
							  		listValue="value" listKey="code" headerKey="0" headerValue="--请选择--"/><p/>
							开始时间：<input name="startTime" onfocus="WdatePicker({startTime:'%y-%M-%d %H:%m:%s', dateFmt:'yyyy-MM-dd HH:mm:ss'});" type="text" class="input" />
							结束时间：<input name="endTime" onfocus="WdatePicker({endTime:'%y-%M-%d %H:%m:%s', dateFmt:'yyyy-MM-dd HH:mm:ss'});" type="text" class="input" />
							<span class="mr5"><span class="bt2"><span><button type="submit" onclick="return doSearch(this);">查询</button></span></span></span>
							<span class="mr5"><span class="bt2"><span><button type="button" onclick="javascript:formReset('searchForm');">重置</button></span></span></span>
						</td>
					</tr>
				</tbody>
			</table>
		</s:form>
	</div>
	<h2 class="h1 cc" id="h_btns"><span class="mr10 fl">企业列表</span> 
		<span class="fr">
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
		  		<td scope="col">所属企业</td>
		  		<td scope="col">对应企业</td>
		  		<td scope="col">编码</td>
		  		<td scope="col">名称</td>
		  		<td scope="col">经销商级别</td>
		  		<td scope="col">终端类别 </td>
		  		<td scope="col">省</td>
		  		<td scope="col">市</td>
		  		<td scope="col">质检标签</td>
		  		<td scope="col">是否停用 </td>
		  		<td scope="col">最后修改日期</td>
		  		<td scope="col">状态</td>
		  		<td scope="col">操作</td>
			</tr>
			</thead>
			<tbody>
			<s:iterator value="list" status="status" var="rp" >
				<tr class="tr1 vt">		 
					<td class="td2"><input type="checkbox" name="code_cbx" value="<s:property value="id"/>" /></td>	
					<td class="td2"><s:property value="id"/></td>
					<td class="td2"><s:property value="ownerAscName"/></td>
					<td class="td2"><s:property value="partnerAscName"/></td>
					<td class="td2"><s:property value="code"/></td>
					<td class="td2"><a onclick="detail('<s:property value="id"/>')" href="javascript:void(0)"><s:property value="orgName"/></a></td>
					<td class="td2"><s:property value="xlevel"/></td>
					<td class="td2"><s:property value="xtype"/></td>		
					<td class="td2"><s:property value="province"/></td>
					<td class="td2"><s:property value="city"/></td>
					<td class="td2">
						<s:if test="qualityCheck==0">历史数据</s:if>
						<s:if test="qualityCheck==1">新增数据</s:if>
						<s:if test="qualityCheck==2">免检数据</s:if>
					</td>
					<td class="td2"><s:property value="isDisable.value"/></td>
					<td class="td2"><s:date format="yyyy-MM-dd HH:mm:ss" name="lastmodifyTime"/></td>
					<td class="td2">
						<s:if test="state.code == 1">
							<span class="c5">正常</span>
						</s:if>
						<s:else>
							<span class="c5">变更中</span>
						</s:else>
					</td>
					<td class="td2">
						<s:if test="state.code == 1">
						<a onclick="update('<s:property value="id"/>')" href="javascript:void(0)">[变更]</a>
						<a onclick="remove('<s:property value="id"/>')" href="javascript:void(0)">[删除]</a>
						<a onclick="qualityCheck('<s:property value="id"/>','<s:property value="qualityCheck"/>')" href="javascript:void(0)">[质检]</a>
						</s:if><s:else><span class="c5">变更中</span></s:else>
					</td>
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