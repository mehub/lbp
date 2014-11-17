<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="aa" uri="http://ajaxanywhere.sourceforge.net/"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="x-ua-compatible" content="ie=7" />
	<title>索引管理</title>
	<%@include file="/include/header.jsp"%>
	<%@include file="/common/grid/grid.inc"%>
	<link href="<%=request.getContextPath()%>/thirdjs/jquery/ui1.9.2/css/jquery-ui-1.9.2.custom.min.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath()%>/common/css/common.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath()%>/index/css/admin_style.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath()%>/index/css/component.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath()%>/thirdjs/thickbox/thickbox.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath()%>/thirdjs/validator/css/validator.css" type="text/css" rel="stylesheet" />
	
	<script src="<%=request.getContextPath()%>/thirdjs/jquery/jquery-1.8.3.js"></script>
	<script src="<%=request.getContextPath()%>/thirdjs/jquery/ui1.9.2/js/jquery-ui-1.9.2.custom.min.js"></script>
	<script src="<%=request.getContextPath()%>/thirdjs/jquery/jquery.form.js"></script>
	<script src="<%=request.getContextPath()%>/thirdjs/thickbox/thickbox.js"></script>
	<script src="<%=request.getContextPath()%>/thirdjs/validator/formValidator.js"></script>
	<script src="<%=request.getContextPath()%>/thirdjs/validator/formValidatorRegex.js"></script>
	
	<script src="<%=request.getContextPath()%>/common/util.js"></script>
	<script src="<%=request.getContextPath()%>/common/windows.js"></script>
	<script src="<%=request.getContextPath()%>/common/function.js"></script>
	<script src="<%=request.getContextPath()%>/common/formutil.js"></script>
	
	<script type="text/javascript">
		function refresh(){		
			window.location.href = path+"/mds/listIndex.htm";
		}
		function update(clazz){
			if(progressbar) {
				Asc.windows.alert('提示',"有更新正在进行，请稍后再试!");
				return;				
			}
			Asc.windows.confirm('提示','确认更新索引？',function(c){
				if(c=='yes'){
					Mask.show();
					$.ajax({
					   type:"POST",
					   url:path+"/mds/updateIndex.htm",
					   data:"clazz=" + clazz,
					   success:function(msg){
					   }
					});
					Asc.windows.alert('提示',"提交成功!");
					refresh();
				}
				this.closeWindow();
			});
		}
		function rebuild(clazz){
			if(progressbar) {
				Asc.windows.alert('提示',"有更新正在进行，请稍后再试!");
				return;				
			}
			
			Asc.windows.confirm('提示','重建结束前此索引将不可用，确认重建？',function(c){
				if(c=='yes'){
					Mask.show();
					$.ajax({
					   type:"POST",
					   url:path+"/mds/rebuildIndex.htm",
					   data:"clazz=" + clazz,
					   success:function(msg){
					   }
					});
					Asc.windows.alert('提示',"操作成功!");
					refresh();
				}
				this.closeWindow();
			});
		}
		
		function optimize(clazz) {
			Asc.windows.confirm('提示', '请在平台空闲时间操作，确认优化索引？', function(c) {
				if (c == 'yes') {
					Mask.show();
					$.ajax({
						type : "POST",
						url : path + "/mds/optimizeIndex.htm",
						data : "clazz=" + clazz,
						success : function(msg) {
							if (msg == '1') {
								Asc.windows.alert('提示', "操作成功!");
								refresh();
							} else {
								Asc.windows.alert('提示', "操作失败,请重试!");
							}
							Mask.hide();
						}
					});
				}
				this.closeWindow();
			});
		}
		
		var _si;
		$(document).ready(
			function() {
				$.formValidator.initConfig({
					formid : "deleteindexForm",
					onerror : function(msg) {
						showTip(msg);
						return false;
					},
					onsuccess : function() {
						return true;
					}
				});
				ValidUtil.init('indexIds', '请输入', '逗号分隔字符串: 1,2,3', '正确',
						false).notEmpty().length(1, 100);
				
				//refesh progressingbar
				progressing();
				_si=setInterval(progressing,5000);
		});
		var _iindex = 0;
		var progressbar = null;
		function progressing() {			
			$.ajax({
				type : "POST",
				dataType:'json',
				url : path + "/mds/progressingIndex.htm",
				success : function(json) {
					
					if(json && json.length < 1 && _si && _iindex > 0)
						refresh();
					
					else if(json && json.length < 1 && _si)
						clearInterval(_si);
					
					else if(json && json.length > 0) {
						
						for(var i=0; i<json.length; i++) {
							progressbar = $("#"+json[i].code+"bar");
							var progressLabel = $("."+json[i].code+"-label");
							
							if( _iindex == 0){
								
								progressbar.progressbar({
									value  : false,
									change : function() {
										progressLabel.text(progressbar.progressbar("value") + "%");
									},
									complete : function() {
										progressLabel.text('完成中...');
									}
								});
								progressLabel.css({ 'position': 'absolute',
									'margin-left':'20px',
									'font-weight': 'bold',
									'text-shadow': '1px 1px 0 #fff'});
								
								_iindex = 1;
							}
							var percent = json[i].percent;
							percent = parseFloat(percent);
							if ( percent <= 100 ) {
								progressbar.progressbar("value", percent);
							}
						}
					}
				},
				error:function(request, textStatus, errorThrown){
					//ignore
				}
			});
		}
		
		function deleteids(clazz) {
			if(progressbar) {
				Asc.windows.alert('提示',"有更新正在进行，请稍后再试!");
				return;				
			}
			$("#indexClazz").val(clazz);
			showBox("#TB_inline?height=120&width=300&inlineId=deleteindex", "删除索引");
		}
		
	</script>
</head>

<body>
<%@include file="/include/body.jsp"%>
<s:if test="flag">
	<script type="text/javascript">showTip("操作成功");</script>
</s:if>

<div class="wrap">
	<h2 class="h1 cc" id="h_btns"><span class="mr10 fl">索引列表</span> 
		<span class="fr">
			<span class="linka fl mr5">
	    			<a class="btn_add fl" href="javascript:void(0)" onclick="refresh();"><i>刷新</i></a>
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
		  		<td scope="col">索引名称</td>
		  		<td scope="col">索引文档数量</td>
		  		<td scope="col">总检索数量</td>
		  		<td scope="col">最大检索时间</td>
		  		<td scope="col">总检索时间</td>
		  		<td scope="col">solr地址</td>
		  		<td scope="col" align="center">操作</td>
			</tr>
			</thead>
			<tbody>
			<s:iterator value="list" status="status" var="rp" >
				<tr class="tr1 vt">		 
					<td class="td2"><input type="checkbox" name="code_cbx" value="<s:property value="id"/>" /></td>	
					<td class="td2"><s:property value="name"/></td>
					<td class="td2"><s:property value="indexedCount"/></td>
					<td class="td2"><s:property value="searchQueryCount"/></td>
					<td class="td2"><s:property value="searchExecutionMaxTime"/></td>
					<td class="td2"><s:property value="searchExecutionTotalTime"/></td>
					<td class="td2"><a href="<s:property value="solrUrl"/>"><s:property value="solrUrl"/></a></td>
					<td class="td2" align="center">
						<s:if test="isRebuilding == 0">
							<s:if test="name=='客户别称'">
								<a style="color:darkcyan" title="索引更新，增加新索引（id一致的将更新）" onclick="update('<s:property value="clazz"/>')" href="javascript:void(0)">[更新]</a>
							</s:if><s:else>
								<a style="color:grey" title="索引重建，索引库将重建" onclick="rebuild('<s:property value="clazz"/>')" href="javascript:void(0)">[重建]</a>
							</s:else>
							<%-- <a style="color:blue" title="索引优化，请在平台空闲时间操作" onclick="optimize('<s:property value="clazz"/>')" href="javascript:void(0)">[优化]</a> --%>
							<a style="color:blank" title="索引删除，需提供逗号分隔的Id" onclick="deleteids('<s:property value="clazz"/>')" href="javascript:void(0)">[删除]</a>
						</s:if>
						<s:else>
							<div id="<s:property value="code"/>bar"><div class="<s:property value="code"/>-label">更新中...</div></div>
						</s:else>
					</td>
				</tr>
			</s:iterator>
			</tbody>
		</table>
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
<div id="deleteindex" style="display:none;">
	 <div class="admin_table mb10" id="deleteindex_wrap">
		<form id="deleteindexForm" action="<%=request.getContextPath()%>/mds/purgesIndex.action" method="post">
		<input id="indexClazz" type="hidden" name="clazz" value=""/>
		<table width="100%" cellspacing="0" cellpadding="0">
			<tr class="tr1 vt">
				<td class="td1">索引主键</td>
				<td class="td2"><textarea id="indexIds" class="input_wa mr10 fl" name="ids" value="" rows="2" cols="1"></textarea><span id="indexIdsTip"></span></td>
			</tr>
		</table>
		<div class="tac mb10">
			<span class="btn"><span><button type="submit">提 交</button></span></span>
		</div>
		</form>
	</div>
</div>
</body>
</html>