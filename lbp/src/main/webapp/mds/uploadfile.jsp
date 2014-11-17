<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="aa" uri="http://ajaxanywhere.sourceforge.net/"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="x-ua-compatible" content="ie=7" />
	<title>上传记录</title>
	<%@include file="/include/header.jsp"%>
	<%@include file="/common/grid/grid.inc"%>
	<link href="<%=request.getContextPath()%>/index/css/admin_style.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath()%>/index/css/component.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath()%>/common/css/common.css" rel="stylesheet" type="text/css" />
	
	<link href="<%=request.getContextPath()%>/thirdjs/thickbox/thickbox.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath()%>/thirdjs/autocomplete/simpleAutoComplete.css" rel="stylesheet" type="text/css" />
	
	<script src="<%=request.getContextPath()%>/index/jquery-1.7.2.js"></script>
	<script src="<%=request.getContextPath()%>/common/util.js"></script>
	<script src="<%=request.getContextPath()%>/common/windows.js"></script>
	<script src="<%=request.getContextPath()%>/common/function.js"></script>
	
	<script src="<%=request.getContextPath()%>/thirdjs/thickbox/thickbox.js"></script>
	<script src="<%=request.getContextPath()%>/thirdjs/autocomplete/simpleAutoComplete.js"></script>
	<script src="<%=request.getContextPath()%>/thirdjs/datepicker/WdatePicker.js"></script>
	
	<script type="text/javascript">
		function nextPage_file(start, limit){			
			this.start=start || this.start;
			if(start == 0) this.start = 0;
			this.limit=limit || this.limit;			
			var paramObj={start:this.start, limit:this.limit};
			var form = document.forms["searchForm"];
			url = path+"/mds/listUploadFile.htm";
			doAjaxPost(form, url, paramObj);
		}
		
		function modifyLimit(obj){
			nextPage_file(0, obj.value);
		}
		
		function doSearch(button){		
			action = path+"/mds/listUploadFile.htm";
			return doAjax(button, action);
		}
	
		function audit(id, filename, datatype){
			getById("audi").reset();
			$("#id").attr("value", id);
			$("#filename").attr("value", filename);
			$("#datatype").attr("value", datatype);
			showBox("#TB_inline?height=170&width=230&inlineId=edit_container", "审核");
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
		<s:form name="searchForm" method="post" id="searchForm">
			<table width="100%" cellspacing="0" cellpadding="0">
				<tbody>
					<tr class="tr1 vt">
						<td class="td2">
							文件名称：<input name="filename" type="text" class="input" />
							开始时间：<input name="startTime" onfocus="WdatePicker({startTime:'%y-%M-%d %H:%m:%s', dateFmt:'yyyy-MM-dd HH:mm:ss'});" type="text" class="input" />
							结束时间：<input name="endTime" onfocus="WdatePicker({endTime:'%y-%M-%d %H:%m:%s', dateFmt:'yyyy-MM-dd HH:mm:ss'});" type="text" class="input" />
							数据类型: 
							<s:select name="datatype" list="@com.asc.mds.root.state.DataType@values()" cssClass="input" 
							  		listValue="value" listKey="code" headerKey="0" headerValue="--请选择--"/>
							处理状态: 
							<s:select name="state" list="@com.asc.mds.root.state.ProcState@values()" cssClass="input" 
							  		listValue="value" listKey="code" headerKey="0" headerValue="--请选择--"/>
							审核状态: 
							<s:select name="audiState" list="@com.asc.mds.root.state.AudiState@values()" cssClass="input" 
							  		listValue="value" listKey="code" headerKey="0" headerValue="--请选择--"/>
							<span class="mr5">
								<span class="bt2">
									<span><button type="submit" onclick="return doSearch(this);">查询</button></span>
								</span>
							</span>
						</td>
					</tr>
				</tbody>
			</table>
		</s:form>
	</div>
	<h2 class="h1 cc" id="h_btns"><span class="mr10 fl">文件列表</span> 
	 <span class="linka"><a href="javascript:void(0)" class="mr10" onclick="toggleArea(this,'show_area');">［收起］</a></span> 
	</h2>
	
	<div class="admin_table mb10" id="show_area">
		<aa:zone name="searchForm">
		<table width="100%;" border="0" cellspacing="0" cellpadding="0" id="companytable">
			<thead>
			<tr class="tr2">
				<td scope="col"><input type="checkbox" name="all_cbx" title="全选 " onclick="checkAll(this, 'code_cbx')" /></td>
		  		<td scope="col">ID</td>
		  		<td scope="col">文件名称</td>
		  		<td scope="col">文件<br />类型</td>
		  		<td scope="col">文件<br />大小</td>
		  		<td scope="col">记录<br />数目</td>
		  		<td scope="col">数据类型 </td>
		  		<td scope="col">处理状态</td>
		  		<td scope="col">重复状态</td>
		  		<td scope="col">异常信息</td>
		  		<td scope="col">创建人</td>
		  		<td scope="col">创建时间</td>
		  		<td scope="col">审核状态</td>
		  		<td scope="col">审核人 </td>
		  		<td scope="col">备注 </td>
		  		<td scope="col">操作</td>
			</tr>
			</thead>
			<tbody>
			<s:iterator value="list" status="status" var="rp" >
				<tr class="tr1 vt">		 
					<td class="td2"><input type="checkbox" name="code_cbx" value="<s:property value="id"/>" /></td>	
					<td class="td2"><s:property value="id"/></td>
					<td class="td2"><s:property value="filename"/></td>
					<td class="td2"><s:property value="filetype"/></td>
					<td class="td2"><s:property value="filesize"/></td>
					<td class="td2"><s:property value="recordNum"/></td>
					<td class="td2"><s:property value="datatype.value"/></td>		
					<td class="td2"><s:property value="state.value"/></td>
					<td class="td2"><s:property value="repeatState.value"/></td>
					<td class="td2"><s:property value="exceptmsg"/></td>		
					<td class="td2"><s:property value="creator"/></td>
					<td class="td2"><s:date format="yyyy-MM-dd HH:mm:ss" name="createTime"/></td>
					<td class="td2"><s:property value="audiState.value"/></td>		
					<td class="td2"><s:property value="audiUser"/></td>
					<td class="td2"><s:property value="memo"/></td>
					<td class="td2">
					<s:if test="state.code != 3">
						<span class="c1">等待处理</span>
					</s:if>
					<s:else>
						<s:if test="audiState.code == 1">
						<a onclick="audit('<s:property value="id"/>', '<s:property value="filename"/>', '<s:property value="datatype.code"/>')" href="javascript:void(0)">[审核]</a>
						</s:if><s:else><span class="c5">已审核</span></s:else>
					</s:else>						
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

<div id="edit_container" style="display:none;">
	 <div class="admin_table mb10" id="edit_area">
		<s:form id="audi" method="post">
		<table width="100%" cellspacing="0" cellpadding="0">
			<tr class="tr1 vt">
				<td class="tddilag" align="right">文件名：</td>
				<td class="td2">
					<input type="hidden" id="id" name="id" value=""/>
					<input type="hidden" id="datatype" name="datatype" value=""/>
					<input type="text" id="filename" class="input_wa mr10 fl" value="" disabled="disabled" />
				</td>
			</tr>
			<tr class="tr1 vt">
				<td class="tddilag" align="right">是否通过：</td>
				<td class="td2">
					<input type="radio"  name="audiState" checked="checked" value="3"/>：通过
					<input type="radio"  name="audiState" value="4"/>：不通过
				</td>
			</tr>
			<tr class="tr1 vt">
				<td class="tddilag" align="right">备注：</td>
				<td class="td2"><textarea id="memo" name="memo" rows="2" cols="3" class="input_wa mr10 fl"></textarea></td>
			</tr>
		</table>
		<div class="tac mb10">
			<span class="btn"><span><button id="submitOperate" type="button">提 交</button></span></span>
		</div>
		</s:form>
	</div>
</div>
<script type="text/javascript">
	$('#submitOperate').bind('click',function(){
		if(confirm("准备提交数据，是否继续？")){
			Mask.show();
			jQuery.ajax({
	  			url:path+'/mds/auditUploadFile.action',
	  			type:'post',
	  			dataType:'json',
	  			data: {"id" : $("#id").val(), 
	  				   "datatype" : $("#datatype").val(),
	  				   "audiState" : $("input[name='audiState']:checked").val(),
	  				   "memo" :$("#memo").val()},
	  			success:function(json){
	  				Ajax.callback(json);
	  				nextPage_file();
	  				Mask.hide();
	  			}
	  		});
		}
	})
</script>
</body>
</html>