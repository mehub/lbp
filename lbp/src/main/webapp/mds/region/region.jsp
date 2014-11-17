<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="aa" uri="http://ajaxanywhere.sourceforge.net/"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="x-ua-compatible" content="ie=7" />
	<title>区域</title>
	<%@include file="/include/header.jsp"%>
	<%@include file="/common/grid/grid.inc"%>
	<link href="<%=request.getContextPath()%>/thirdjs/jquery/ui1.9.2/css/jquery-ui-1.9.2.custom.min.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath()%>/common/css/common.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath()%>/index/css/admin_style.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath()%>/index/css/component.css" rel="stylesheet" type="text/css" />
	
	<link href="<%=request.getContextPath()%>/thirdjs/thickbox/thickbox.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath()%>/thirdjs/autocomplete/simpleAutoComplete.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath()%>/thirdjs/validator/css/validator.css" type="text/css" rel="stylesheet" />
	
	
	<script src="<%=request.getContextPath()%>/thirdjs/jquery/jquery-1.8.3.js"></script>
	<script src="<%=request.getContextPath()%>/thirdjs/jquery/ui1.9.2/js/jquery-ui-1.9.2.custom.min.js"></script>
	<script src="<%=request.getContextPath()%>/thirdjs/jquery/jquery.form.js"></script>
	
	<script src="<%=request.getContextPath()%>/thirdjs/thickbox/thickbox.js"></script>
	<script src="<%=request.getContextPath()%>/thirdjs/autocomplete/simpleAutoComplete.js"></script>
	
	<script src="<%=request.getContextPath()%>/thirdjs/jquery/jquery.jstree.js"></script>
	
	<script src="<%=request.getContextPath()%>/thirdjs/validator/formValidator.js"></script>
	<script src="<%=request.getContextPath()%>/thirdjs/validator/formValidatorRegex.js"></script>
	
	<script src="<%=request.getContextPath()%>/thirdjs/combox/combox.js"></script>
	
	<script src="<%=request.getContextPath()%>/common/util.js"></script>
	<script src="<%=request.getContextPath()%>/common/windows.js"></script>
	<script src="<%=request.getContextPath()%>/common/function.js"></script>
	<script src="<%=request.getContextPath()%>/common/formutil.js"></script>
	
	<style>
		html,body {
			height:100%;
			height:100%;
			overflow:hidden;
		}
	</style>
	<script type="text/javascript">
		//区域树
		$(function(){
			$("#jsTree").jstree({
				"ui":{
					"select_limit" : 1
				},
				"json_data":{
			     	"ajax":{
			     		"url":path+"/mds/region/listJSTreeRegion.htm",
		         		"data":function(n){
			     		    return { parentId : n.attr ? n.attr("id") : -1 };
			     		 }
				 	 }
				},
				"plugins" : [ "themes", "json_data", "ui"]
			}).bind("select_node.jstree", function (e, data) {
				$('#parentId').val(data.rslt.obj.attr("id"));
				$('#parentName').val(data.rslt.obj.attr("name"));
				nextPage_region();
			});
			
			if(Asc.isIE6){
				$('#rightcontent').width($(window).width()-240);
			}else{
				$('#rightcontent').width($(window).width()-192);
			}
		});
		function nextPage_region(start, limit){			
			this.start=start || this.start;
			if(start == 0) this.start = 0;
			this.limit=limit || this.limit;			
			var paramObj={start:this.start, limit:this.limit};
			var form = document.forms["searchForm"];
			url = path+"/mds/region/listRegion.htm";
			doAjaxPost(form, url, paramObj);
		}
		function doSearch(button){	
			document.getElementById("root").value="";
			action = path + "/mds/region/listRegion.htm";
			return doAjax(button, action);
		}
		function add(){
			var parentId = document.getElementById("parentId").value;
			var parentName = document.getElementById("parentName").value;
			tb_show("新增（标为<span class='high_star'>*</span>必填项）", "update.jsp?parentId=" + parentId + encodeURI("&parentName=" + parentName) + "&height=390&width=450");
			$( "#TB_window" ).draggable();
		}
		function update(id){
			tb_show("变更（标为<span class='high_star'>*</span>必填项）", path+"/mds/region/getRegion.action?id="+id+"&height=390&width=450");
			$( "#TB_window" ).draggable();
		}
		function remove(id){
			Asc.windows.confirm('提示','确认删该除区域及其子区域？',function(c){
				if(c=='yes'){
					$.ajax({
					   type:"POST",
					   url:path+"/mds/region/removeRegion.htm",
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

<div style="width:190px; height:97%;position:absolute; left:0; top:0;overflow:auto;">
	<ul id="jsTree" class="filetree" style="margin-top:5px;margin-left:5px;"></ul>
</div>
<div style="position:absolute;height:97%;left:190px; top:5px;right:5px;overflow-y:auto;" id="rightcontent">
	<div style="width:100%">
	<h2 class="h1">搜索 
		<span class="linka"><a href="javascript:void(0)" class="mr10" onclick="toggleArea(this,'query_area');">［收起］</a></span>
	</h2>
	<div class="admin_table mb10" id="query_area" style="display: block;">
		<s:form name="searchForm" action="listRegion.action" method="post" id="searchForm">
			<table width="100%" cellspacing="0" cellpadding="0">
				<tbody>
					<tr class="tr1 vt">
						<td class="td2">
							<s:hidden name="parentId" id="parentId" value="source" />
							<s:hidden name="parentName" id="parentName" value="source" />
							名称：<s:textfield name="regionName" cssClass="input input_wa" />
							编码：<s:textfield name="code" cssClass="input input_wa" />
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
	<h2 class="h1 cc" id="h_btns"><span class="mr10 fl">区域列表</span> 
		<span class="fr">
    		<sec:authorize ifAnyGranted="ADMIN, REGION_ADD">
	    		<span class="linka fl mr5">
	    			<a class="btn_add fl" href="javascript:void(0)" onclick="add();"><i>新增</i></a>
	    		</span>
    		</sec:authorize>
    	</span>
	 <span class="linka"><a href="javascript:void(0)" class="mr10" onclick="toggleArea(this,'show_area');">［收起］</a></span> 
	</h2>

    <div class="admin_table mb10" id="show_area">
		<aa:zone name="searchForm">
		<table width="100%;" border="0" cellspacing="0" cellpadding="0" id="companytable">
	      <thead>	
		      <tr class="tr2 td_bgB">
		        <td scope="col"><input type="checkbox" name="all_cbx" title="全选 " onclick="checkAll(this, 'code_cbx')" /></td>
		        <td scope="col" width="5">序号</td>
		        <td scope="col">区域名称</td>
		        <td scope="col">区域代码</td>
		        <td scope="col">区域简称</td>
		        <td scope="col">父区域</td>
		        <td scope="col">省</td>	  
		        <td scope="col">级别</td>
		        <td scope="col">邮编</td>      
		        <td scope="col">关键词</td>
		        <td scope="col">修改人</td>
		        <td scope="col">最后修改日期</td>
		        <td scope="col">操作</td>
		      </tr>
	      </thead>
	      <tbody>
	      	<s:iterator value="list" status="st" var="rp" >
	      		<tr class="tr1 vt">
		        	<td class="td2"><input type="checkbox" name="code_cbx" value="<s:property value="id"/>" /></td>
			        <td class="td2"><s:property value="#st.count" /></td>
			        <td class="td2"><s:property value="regionName" /></td>
			        <td class="td2"><s:property value="code" /></td>
			        <td class="td2"><s:property value="shortName" /></td>
			        <td class="td2"><s:property value="parentName" /></td>
			        <td class="td2"><s:property value="province" /></td>
			        <td class="td2"><s:property value="xlevel" /></td>
			        <td class="td2"><s:property value="post" /></td>
			        <td class="td2"><s:property value="keyWords" /></td>	
			        <td class="td2"><s:property value="modifyer" /></td>
			        <td class="td2"><s:date name="lastmodifyTime" format="yyyy-MM-dd HH:mm:ss" /></td>		    
			        <td class="td2">
			       		 <a onclick="update('<s:property value="id"/>')" href="javascript:void(0)">[变更]</a>
			       		 <a onclick="remove('<s:property value="id"/>')" href="javascript:void(0)">[删除]</a>
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
</div>
</body>
</html>