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
	<link href="<%=request.getContextPath()%>/index/css/upload.css" rel="stylesheet" type="text/css" />
	
	<link href="<%=request.getContextPath()%>/thirdjs/thickbox/thickbox.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath()%>/thirdjs/autocomplete/simpleAutoComplete.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath()%>/thirdjs/validator/css/validator.css" type="text/css" rel="stylesheet" />
	
	<script src="<%=request.getContextPath()%>/thirdjs/jquery/jquery-1.8.3.js"></script>
	<script src="<%=request.getContextPath()%>/thirdjs/jquery/ui1.9.2/js/jquery-ui-1.9.2.custom.min.js"></script>
	
	<script src="<%=request.getContextPath()%>/thirdjs/thickbox/thickbox.js"></script>
	<script src="<%=request.getContextPath()%>/thirdjs/autocomplete/simpleAutoComplete.js"></script>
	
	<script src="<%=request.getContextPath()%>/thirdjs/validator/formValidator.js"></script>
	<script src="<%=request.getContextPath()%>/thirdjs/validator/formValidatorRegex.js"></script>
	
	<script src="<%=request.getContextPath()%>/common/util.js"></script>
	<script src="<%=request.getContextPath()%>/common/windows.js"></script>
	<script src="<%=request.getContextPath()%>/common/function.js"></script>
	<script src="<%=request.getContextPath()%>/thirdjs/upload/upload.js"></script>
	
	<script type="text/javascript">
		var pageStart=0;
		$(document).ready(function(){
			$('#orgName_ac').simpleAutoComplete(path+"/mds/org/autoCompleteTempOrg.action");
			
		});
		function nextPage_file(start, limit){
			this.start=start || this.start;
			if(start == 0) this.start = 0;
			this.limit=limit || this.limit;			
			var paramObj={start:this.start, limit:this.limit};
			var form = document.forms["orgListTable"];
			url ="listTempOrg.action";
			doAjaxPost(form, url, paramObj);
		}
		//修改
		function modifyOrg(orgId){
			tb_show("修改（标为<span class='high_star'>*</span>必填项）","inputTempOrg.action?id="+orgId+"&width=450&height=650",false); 
			$( "#TB_window" ).draggable({handle: "div#TB_title",cursor: "move"});
		}
		//新增
		function addOrg(orgId){
			tb_show("新增（标为<span class='high_star'>*</span>必填项）","inputTempOrg.action?&width=450&height=655",false); 
			$( "#TB_window" ).draggable({handle: "div#TB_title",cursor: "move"});
		}
		//查看明细
		function detail(orgId){
			tb_show("明细","inputTempOrg.action?id="+orgId+"&detail=true&width=400&height=555",false); 
			$( "#TB_window" ).draggable({handle: "div#TB_title",cursor: "move"});
		}
		//上传
		function upload(){
			tb_show("文件上传 &nbsp;【<a href=templateDownloadUploadFile.action?datatype=1>模板下载</a>】", 
					"#TB_inline?&inlineId=uploadContainer&height=150&width=220");
			$( "#TB_window" ).draggable({handle: "div#TB_title",cursor: "move"});
		}
		
		//审核
		function audit(orgId){
			$("#companytable").data("orgId",orgId);
			tb_show("审核","#TB_inline?height=100&width=270&inlineId=edit_container");
			$( "#TB_window" ).draggable({handle: "div#TB_title",cursor: "move"});
		}
		$(function(){
			$("#submitOperate").click(function(){
				var state=$("input[name='audiState']:checked").val();
				var orgId=$("#companytable").data("orgId");
				doAjaxGet("orgListTable", "auditOrg.action?id="+orgId+"&state="+state);
				tb_remove();
			});
		});
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
		        	<span class="mr20">企业名称：<input type="text" id="orgName_ac" name="orgName" class="input input_wa" /></span>
		        	<span class="mr20">编码：<input type="text" name="code" class="input input_wa" /></span>
		            <span class="mr20">企业类型：
		            	<s:select name="typeId" list="types"  listKey="id"  listValue="typeName" headerKey="" headerValue="--请选择类型--" cssClass="input input_wa"/>
		            </span>
		           	<span class="mr20">审核状态: 
						<s:select name="audiState" list="@com.asc.mds.root.state.AudiState@values()" cssClass="input" 
						  		listValue="value" listKey="code" headerKey="0" headerValue="--请选择--"/>
					</span>
		            <span class="mr5"><span class="bt2"><span><button type="button" onclick="nextPage_file(0)">查询</button></span></span></span>
		            <span class="mr5"><span class="bt2"><span><button type="button" onclick="javascript:formReset('orgListTable')">重置</button></span></span></span>
				</td>
			</tr>
			</tbody>
		</table>
		</form>
	</div>

	<h2 class="h1 cc"><span class="mr10 fl">企业列表</span>
		<span class="fr">
			<span class="linka fl mr5"><a class="btn_add fl" href="javascript:void(0);" onclick="addOrg()"><i>新增</i></a></span>
			<span class="linka fl mr5"><a class="btn_add fl" href="javascript:void(0)" onclick="upload();"><i>上传</i></a></span>
		</span>
	</h2>

    <div class="admin_table mb10">
    <aa:zone name="orgListTable">
    <table width="100%" border="0" cellspacing="0" cellpadding="0" id="companytable">
      <thead>
	      <tr class="tr2">
	        <td scope="col" width="10"><input type="checkbox" name="all_cbx" title="全选 " onclick="checkAll(this, 'code_cbx')" /></td>
	        <td scope="col">ID</td>
	        <td scope="col" width="250">名称</td>
	        <td scope="col">编码</td>
	        <td scope="col">区域</td>
	        <td scope="col" width="40">类别</td>
	        <td scope="col" width="50">性质</td>
	        <td scope="col" width="130">地址</td>
	        <td scope="col">级别</td>
	        <td scope="col">状态</td>
	        <td scope="col">最后修改时间</td>
	        <td scope="col">审核状态</td>
	        <sec:authorize ifAnyGranted="ADMIN,ORG_STATE_SETTING">
	        <td scope="col">操作</td>
	        </sec:authorize>
	      </tr>
      </thead>
      <tbody>
      	<s:iterator value="orgTempList" id="org" status="st">
      		<tr class="tr1 vt">
	        	<td class="td2"><input type="checkbox" value="<s:property value="id" />" name="code_cbx"/></td>
		        <td class="td2"><s:property value="id" /></td>
		        <td class="td2"><a href="javascript:void(0)" onclick="detail('<s:property value="id" />')"><s:property value="orgName" /></a></td>
		        <td class="td2"><s:property value="code" /></td>
		        <td class="td2"><s:property value="regionName" /></td>
		        <td class="td2"><s:property value="typeName" /></td>
		        <td class="td2"><s:property value="classify" /></td>
		        <td class="td2"><s:property value="address" /></td>
		        <td class="td2"><s:property value="xlevel" /></td>
		        <td class="td2"><s:property value="state.value" /></td>
		        <td class="td2"><s:date name="lastmodifyTime" format="yyyy-MM-dd HH:mm:ss" /></td>
		        <td class="td2"><s:property value="audiState.value" /></td>
		    	<sec:authorize ifAnyGranted="ADMIN,ORG_STATE_SETTING">
			        <td class="td2">
			        <s:if test="audiState.code==1 ||audiState.code==4 || audiState.code == 5">
						<a href="#none" onclick="modifyOrg('<s:property value="id"/>')">[变更]</a>
			       	</s:if>
			       	<s:if test="audiState.code==1">
						<a href="javascript:void(0);" onclick="audit('<s:property value="id"/>')">[审核]</a>
					</s:if><s:else><span class="c5">已审核</span></s:else>	
			        </td>
		    	</sec:authorize>
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

<!-- 审核 -->
<div id="edit_container" style="display:none;">
	<div class="admin_table mb10">
		<table width="100%" cellspacing="0" cellpadding="0">
			<tr class="tr1 vt">
				<td class="td1 tar">是否通过：</td>
				<td class="td2">
					<input type="radio"  name="audiState" checked="checked" value="3"/>：通过
					<input type="radio"  name="audiState" value="4"/>：不通过
				</td>
			</tr>
		</table>
		<div class="tac mb10" style="margin-top:10px;margin-right:20px;">
			<span class="btn"><span><button id="submitOperate" type="button">提 交</button></span></span>
		</div>
	</div>
</div>

<!-- 批量上传企业 -->
<div id="uploadContainer" style="display:none" >
	<div class="box-content">
	    <div class="wrap">
	    	<div class="mb10">（EXCEL建议使用97-2003版本）</div>
			<div class="cc mb20">
				<div class="mb10">
			    	<span class="c4">直接通过审核</span>：<input id="audiPass" name="audiPass" type="checkbox" value="2" />
			    </div>
			    <div class="mb10" id="fileUpload">
			    
			    </div>	
			</div>
		</div>		
	</div>
</div>
<script type="text/javascript">
	var fileUpload = new Asc.upload.uploadSingle({
			el:'fileUpload',
			action:path+'/mds/uploadUploadFile.action',
			name:'uploadFile',
			post:[{name:'datatype',value:'1'}],
			type:'xls|xlsx',
			num:1,
			dynamicPost:function(obj){
				var ap = $('#audiPass').attr('checked');
				if(ap=='checked'){
					obj.option.post.splice(1,1,{name:'audiPass',value:$('#audiPass').val()});					
				}else{
					obj.option.post.splice(1,1,{name:'audiPass',value:'1'});
				}
			},
			callback:function(){
				var limit=$("#limit").val();
				nextPage_file(0, limit);
			}
	});
</script>
</body>
</html>