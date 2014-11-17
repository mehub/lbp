<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="aa" uri="http://ajaxanywhere.sourceforge.net/"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="x-ua-compatible" content="ie=7" />
	<title>产品临时表</title>
	<%@include file="/include/header.jsp"%>
	<%@include file="/common/grid/grid.inc"%>
	<link href="<%=request.getContextPath()%>/thirdjs/jquery/ui1.9.2/css/jquery-ui-1.9.2.custom.min.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath()%>/common/css/common.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath()%>/index/css/admin_style.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath()%>/index/css/component.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath()%>/index/css/upload.css" rel="stylesheet" type="text/css" />
	
	<link href="<%=request.getContextPath()%>/thirdjs/thickbox/thickbox.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath()%>/thirdjs/autocomplete/simpleAutoComplete.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath()%>/thirdjs/validator/css/validator.css" type="text/css" rel="stylesheet" />
	
	
	<script src="<%=request.getContextPath()%>/thirdjs/jquery/jquery-1.8.3.js"></script>
	<script src="<%=request.getContextPath()%>/thirdjs/jquery/ui1.9.2/js/jquery-ui-1.9.2.custom.min.js"></script>
	<script src="<%=request.getContextPath()%>/thirdjs/jquery/jquery.form.js"></script>
	
	<script src="<%=request.getContextPath()%>/thirdjs/thickbox/thickbox.js"></script>
	<script src="<%=request.getContextPath()%>/thirdjs/autocomplete/simpleAutoComplete.js"></script>
	
	<script src="<%=request.getContextPath()%>/thirdjs/validator/formValidator.js"></script>
	<script src="<%=request.getContextPath()%>/thirdjs/validator/formValidatorRegex.js"></script>
	
	<script src="<%=request.getContextPath()%>/common/util.js"></script>
	<script src="<%=request.getContextPath()%>/common/windows.js"></script>
	<script src="<%=request.getContextPath()%>/common/function.js"></script>
	<script src="<%=request.getContextPath()%>/common/formutil.js"></script>
	
	<script src="<%=request.getContextPath()%>/thirdjs/upload/upload.js"></script>
	<script src="<%=request.getContextPath()%>/thirdjs/combox/combox.js"></script>
	
	<script type="text/javascript">
		function nextPage_productTemp(start, limit){			
			this.start=start || this.start;
			if(start == 0) this.start = 0;
			this.limit=limit || this.limit;
			var paramObj={start:this.start, limit:this.limit};
			var form = document.forms["searchForm"];
			url = path+"/mds/product/listTempProduct.htm";
			doAjaxPost(form, url, paramObj);
		}
		function doSearch(button){		
			action = path+"/mds/product/listTempProduct.htm";
			return doAjax(button, action);
		}
		function add(){
			tb_show("新增（标为<span class='high_star'>*</span>必填项）", "update.jsp?height=610&width=450");
			$( "#TB_window" ).draggable();
		}
		function upload(){
			tb_show("文件上传 &nbsp;【<a href=templateDownloadUploadFile.action?datatype=2>模板下载</a>】", 
					"#TB_inline?&inlineId=uploadContainer&height=150&width=220");
			$( "#TB_window" ).draggable();
		}
		function update(id){
			tb_show("变更（标为<span class='high_star'>*</span>必填项）", path+"/mds/product/getTempProduct.action?flag=update&table=temp&id="+id+"&height=610&width=450");
			$( "#TB_window" ).draggable();
		}
		function detail(id){
			tb_show("明细（标为<span class='high_star'>*</span>必填项）", path+"/mds/product/getTempProduct.action?flag=detail&table=temp&id="+id+"&height=600&width=450");
			$( "#TB_window" ).draggable();
		}
		function audit(id, relid, commonname){
			getById("audi").reset();
			$("#id").attr("value", id);
			$("#relid").attr("value", relid);
			$("#xcommonname").attr("value", commonname);
			tb_show("审核", "#TB_inline?height=165&width=220&inlineId=edit_container");
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
		<s:form name="searchForm" action="listTempProduct.action" method="post" id="searchForm">
			<table width="100%" cellspacing="0" cellpadding="0">
				<tbody>
					<tr class="tr1 vt">
						<td class="td2">
							所属厂商：<input name="ownerName" type="text" class="input" />
							产品通用名：<input name="commonname" type="text" class="input" />
							编码：<input name="code" type="text" class="input" />
							审核状态: 
							<s:select name="audiState" list="@com.asc.mds.root.state.AudiState@values()" cssClass="input" 
							  		listValue="value" listKey="code" headerKey="0" headerValue="--请选择--"/>
							<span class="mr5"><span class="bt2"><span><button type="submit" onclick="return doSearch(this);">查询</button></span></span></span>
							<span class="mr5"><span class="bt2"><span><button type="button" onclick="javascript:formReset('searchForm');">重置</button></span></span></span>
						</td>
					</tr>
				</tbody>
			</table>
		</s:form>
	</div>
	<h2 class="h1 cc" id="h_btns"><span class="mr10 fl">产品列表</span> 
		<span class="fr">
    		<sec:authorize ifAnyGranted="ADMIN, PRODUCT_ADD">
	    		<span class="linka fl mr5">
	    			<a class="btn_add fl" href="javascript:void(0)" onclick="add();"><i>新增</i></a>
	    		</span>
    		</sec:authorize>
    		<sec:authorize ifAnyGranted="ADMIN, PRODUCT_UPLOAD">
    		<span class="linka fl mr5">  
    			<a class="btn_add fl" href="javascript:void(0)" onclick="upload();"><i>上传</i></a>
    		</span>
    		</sec:authorize>
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
		  		<td scope="col">通用名</td>
		  		<td scope="col">编码</td>
		  		<td scope="col">学名</td>
		  		<td scope="col">单位</td>
		  		<td scope="col">包装 </td>
		  		<td scope="col">规格</td>
		  		<td scope="col">批号</td>
		  		<td scope="col">类别</td>
		  		<td scope="col">生产厂商</td>
		  		<td scope="col">所属厂商</td>		  		
		  		<td scope="col">审核状态 </td>
		  		<td scope="col">最后修改日期</td>
		  		<td scope="col">操作</td>
			</tr>
			</thead>
			<tbody>
			<s:iterator value="listTemp" status="status" var="rp" >
				<tr class="tr1 vt">		 
					<td class="td2"><input type="checkbox" name="code_cbx" value="<s:property value="id"/>" /></td>	
					<td class="td2"><s:property value="id"/></td>
					<td class="td2"><a onclick="detail('<s:property value="id"/>')" href="javascript:void(0)"><s:property value="commonname"/></a></td>
					<td class="td2"><s:property value="code"/></td>
					<td class="td2"><s:property value="scientificname"/></td>
					<td class="td2"><s:property value="unit"/></td>
					<td class="td2"><s:property value="pack"/></td>		
					<td class="td2"><s:property value="spec"/></td>
					<td class="td2"><s:property value="batchnumber"/></td>
					<td class="td2"><s:property value="typeId"/></td>
					<td class="td2"><s:property value="origin"/></td>
					<td class="td2"><s:property value="ownerName"/></td>
					<td class="td2"><s:property value="audiState.value"/></td>
					<td class="td2"><s:date format="yyyy-MM-dd HH:mm:ss" name="lastmodifyTime"/></td>
					<td class="td2">
						<s:if test="audiState.code == 1 || audiState.code == 4 || audiState.code == 5">
							<a onclick="update('<s:property value="id"/>')" href="javascript:void(0)">[变更]</a>
						</s:if>
						<s:if test="audiState.code == 1">
							<a onclick="audit('<s:property value="id"/>', '<s:property value="relid"/>', '<s:property value="commonname"/>')" href="javascript:void(0)">[审核]</a>
						</s:if><s:else><span class="c5">已审核</span></s:else>
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
				<td class="tddilag" align="right">通用名：</td>
				<td class="td2">
					<input type="hidden" id="id" name="id" value=""/>
					<input type="hidden" id="relid" name="relid" value=""/>
					<input type="text" id="xcommonname" class="input_wa mr10 fl" value="" disabled="disabled" />
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
				<td class="tddilag" align="right">审核备注：</td>
				<td class="td2"><textarea id="audiMemo" name="audiMemo" rows="2" cols="3" class="input_wa mr10 fl"></textarea></td>
			</tr>
		</table>
		<div class="tac mb10">
			<span class="btn"><span><button id="submitOperate" type="button">提 交</button></span></span>
		</div>
		</s:form>
	</div>
</div>

<!-- 批量上传 -->
<div id="uploadContainer" style="display:none" >
	<div class="box-content">
	    <div class="wrap">
	    	<div class="mb10">（EXCEL建议使用97-2003版本）</div>
			<div class="cc mb20">
				<div class="mb10">
			    	<span class="c4">直接通过审核</span>：<input id="audiPass" name="audiPass" type="checkbox" value="2"/>
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
			post:[{name:'datatype',value:'2'}],
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
				nextPage_productTemp();
			}
	});
	$('#submitOperate').bind('click',function(){
		if(confirm("准备提交数据，是否继续？")){
			Mask.show();
			jQuery.ajax({
	  			url:path+'/mds/auditProduct.action',
	  			type:'post',
	  			dataType:'json',
	  			data: {"id" : $("#id").val(), 
	  				   "relid" : $("#relid").val(), 
	  				   "audiState" : $("input[name='audiState']:checked").val(),
	  				   "audiMemo" :$("#audiMemo").val()},
	  			success:function(json){
	  				Mask.hide();
	  				if(Asc.checkCallback(json)){
						var r = Asc.getResData(json);
						if(r == "1") Ajax.success();
						else Ajax.error(r);
		  				$('#searchForm').submit();
					}else{
						Asc.windows.alert('提示',"操作失败!");
					}
	  			}
	  		});
		}
	});
</script>
</body>
</html>