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
			$('#orgName_ac').simpleAutoComplete(path+"/mds/org/autoCompleteOrg.action");
		});
		function nextPage_file(start, limit){
			this.start=start || this.start;
			if(start == 0) this.start = 0;
			this.limit=limit || this.limit;			
			var paramObj={start:this.start, limit:this.limit};
			var form = document.forms["orgListTable"];
			url ="listOrg.action";
			doAjaxPost(form, url, paramObj);
		}
		
		//修改
		function modify(orgId){
			tb_show("修改（标为<span class='high_star'>*</span>必填项）","inputOrg.action?id="+orgId+"&width=450&height=660",false); 
			$( "#TB_window" ).draggable({handle: "div#TB_title",cursor: "move"});
		}
		//查看明细
		function detail(orgId){
			tb_show("明细","inputOrg.action?id="+orgId+"&detail=true&width=400&height=555",false); 
			$( "#TB_window" ).draggable({handle: "div#TB_title",cursor: "move"});
		}
		//删除
		function deletes(param){
			var ids;
			if(param){
				ids=param;
			}else{
				var flag=false;
				var idArr=[];
				$("input[name='code_cbx']:checked").each(function(i,n){
					idArr.push($(n).val())
				});
				if(idArr.length==0){
					Asc.windows.alert('提示',"请选择要删除的记录！");
					return;
				}
				$.each(idArr,function(i,n){
					if(n.split("-")[1]==2){
						flag=true;
						return false;
					}
				});
				if(flag){
					Asc.windows.alert('提示',"变更中的记录不能删除！");
					return;
				}
				var idsArr=[];
				$.each(idArr,function(i,n){
					idsArr.push(n.split("-")[0]);
				});
				ids=idsArr.join(",");
			}
			if(!confirm("确认删除该企业？")){
				return;
			}
			doAjaxGet("orgListTable","deleteOrg.action?ids="+ids);
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
		        	<span class="mr20">企业名称：
		        		<input type="text" id="orgName_ac" name="orgName" class="input input_wa" />
		        		<input title="模糊检索" id="isLike" type="checkbox" name="useLike" value="true"/>
		        	</span>
					
		        	<span class="mr20">编码：<input type="text" name="code" class="input input_wa" /></span>
		            <span class="mr20">企业类型：
		            	<s:select name="typeId" list="types"  listKey="id"  listValue="typeName" headerKey="" headerValue="--请选择类型--" cssClass="input input_wa"/>
		            </span>
		            <span class="mr5"><span class="bt2"><span><button type="button" onclick="nextPage_file(0,10)">查询</button></span></span></span>
		            <span class="mr5"><span class="bt2"><span><button type="button" onclick="javascript:formReset('orgListTable');">重置</button></span></span></span>
				</td>
			</tr>
			</tbody>
		</table>
		</form>
	</div>
	<h2 class="h1 cc"><span class="mr10 fl">企业列表</span>
		<span class="fr">
			<span class="linka fl mr5"><a class="btn_add fl" href="javascript:void(0);" onclick="deletes()"><i>删除</i></a></span>
		</span>
	</h2>
    <div class="admin_table mb10">
    <aa:zone name="orgListTable">
    <table width="100%" border="0" cellspacing="0" cellpadding="0" id="companytable">
      <thead>
	      <tr class="tr2">
	        <td scope="col" width="10">
	        	<input type="checkbox" name="all_cbx" title="全选 " onclick="checkAll(this, 'code_cbx')" />
	        </td>
	        <td scope="col">ID</td>
	        <td scope="col" width="240">名称</td>
	        <td scope="col" width="110">编码</td>
	        <td scope="col">区域</td>
	        <td scope="col" width="40">类别</td>
	        <td scope="col" width="50">性质</td>
	        <td scope="col" width="130">地址</td>
	        <td scope="col">级别</td>
	        <sec:authorize ifAnyGranted="ADMIN,ORG_STATE_SETTING">	        
	        	<td scope="col">操作</td>
	        </sec:authorize>
	      </tr>
      </thead>
      <tbody>
      	<s:iterator value="orgList" id="org" status="st">
      		<tr class="tr1 vt">
	        	<td class="td2"><input type="checkbox" value='<s:property value="id" />-<s:property value="state.code" />' name="code_cbx"/></td>
		        <td class="td2"><s:property value="id" /></td>
		        <td class="td2">
		        	<a href="javascript:void(0)" onclick="detail('<s:property value="id" />')"><s:property value="orgName" /></a>
		        </td>
		        <td class="td2"><s:property value="code" /></td>
		        <td class="td2"><s:property value="regionName" /></td>
		        <td class="td2"><s:property value="typeName" /></td>
		        <td class="td2"><s:property value="classify" /></td>
		        <td class="td2"><s:property value="address" /></td>
		        <td class="td2"><s:property value="xlevel" /></td>
		    	<sec:authorize ifAnyGranted="ADMIN,ORG_STATE_SETTING">
			        <td class="td2">
			        	<s:if test="state.code==1">
				        	<a href="#none" onclick="modify('<s:property value="id"/>')">[变更]</a>
				        	<a href="#none" onclick="deletes('<s:property value="id"/>')">[删除]</a>
			        	</s:if><s:else><span class="c5">变更中</span></s:else>
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
</body>
</html>