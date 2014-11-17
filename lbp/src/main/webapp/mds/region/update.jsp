<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="x-ua-compatible" content="ie=7" />
	<title>变更</title>
	<script type="text/javascript">
		$(document).ready(function(){
			$.formValidator.initConfig({	
				formid:"updateForm",
				onerror:function(msg){
					showTip(msg);;
					return false;
				},
				onsuccess:function(){
					return true;
				}
			});
			
			ValidUtil.init('regionName','请输入名称','1-50位字符','正确',false).notEmpty().length(1,100);
			ValidUtil.init('code','请输入编码','1-50位字符','正确',false).notEmpty().length(1,100);
			ValidUtil.init('xlevel','请选择级别','区域级别','正确',false).notEmpty().plusNum();
			ValidUtil.init('xparentName','请选择父企业','1-50位字符','正确',false).notEmpty().length(1, 100);
			ValidUtil.init('province','请输入省','1-20位字符','正确',false).notEmpty().length(1,50);
			ValidUtil.init('post','请输入邮编','6位数字','正确',false).zipcode();
			
			<%  Object obj = request.getParameter("parentId");
				if(obj != null) { %>
					var parentId = '<%= request.getParameter("parentId") %>';
					if(parentId != 'null' && parentId != "source"){
						var parentName = '<%= new String(request.getParameter("parentName").getBytes("iso-8859-1"), "UTF-8") %>';
						$("#xparentId").val(parentId);
						$("#xparentName").val(parentName);
					}
			<%  } %>
		});
		
		function selectRegion(id){
			var win = new Asc.windows.base({
				title:'区域',
				iframe:'jstree.jsp',
				width:300,
				height:300,
				buttons:[{
					text:'选择',
					cls:'bt2',
					fn:function(){
						var i = this.container.find('iframe:first')[0];
						var w = i.contentWindow?i.contentWindow:i;
						if(w.parentId.length>0){
							$("#xparentId").val(w.parentId);
							$("#xparentName").val(w.parentName);
							this.closeWindow();
						}else{
							Asc.windows.alert('提示',"请选择区域!");
						}
					}
				},{
					text:'取消',
					cls:'bt2',
					fn:function(){
						this.closeWindow();
					}
				}]
			});
		}
	</script>
</head>

<body>
<s:form id="updateForm" action="saveOrUpdateRegion.action" method="post" onsubmit="return ajaxCallback(this)">
	<table class="admin_table" width="100%" cellspacing="0" cellpadding="0">
		<tr class="tr1 vt">
			<td class="td1 tar"><span class='high_star'>*</span>区域名称：</td>
			<td class="td2">
				<s:hidden name="id" id="id" />
				<s:hidden name="flag" id="flag" />
				<s:textfield name="regionName" id="regionName" cssClass="input input_wa fl"/><span id="regionNameTip"></span>
			</td>
		</tr>
		<tr class="tr1 vt">
			<td class="td1 tar"><span class='high_star'>*</span>区域代码：</td>
			<td class="td2"><s:textfield name="code" id="code" cssClass="input input_wa fl"/><span id="codeTip"></span></td>
		</tr>
		<tr class="tr1 vt">
			<td class="td1 tar"><span class='high_star'>*</span>父名称：</td>
			<td class="td2">
				<s:hidden name="parentId" id="xparentId" />
				<s:textfield name="parentName" id="xparentName" readonly="true" cssClass="input input_wa fl"/><span id="xparentNameTip"></span>
				<a href="javascript: void(0);" onclick="selectRegion('<s:property value="parentId"/>')">选择</a>
			</td>
		</tr>
		<tr class="tr1 vt">
			<td class="td1 tar"><span class='high_star'>*</span>省：</td>
			<td class="td2"><s:textfield name="province" id="province" cssClass="input input_wa fl"/><span id="provinceTip"></span></td>
		</tr>
		<tr class="tr1 vt">
			<td class="td1 tar"><span class='high_star'>*</span>级别：</td>
			<td class="td2"><s:textfield name="xlevel" id="xlevel" cssClass="input input_wa fl"/><span id="xlevelTip"></span></td>
		</tr>
		<tr class="tr1 vt">
			<td class="td1 tar">区域简称：</td>
			<td class="td2"><s:textfield name="shortName" id="shortName" cssClass="input input_wa fl"/><span id="shortNameTip"></span></td>
		</tr>
		<tr class="tr1 vt">
			<td class="td1 tar">关键词：</td>
			<td class="td2"><s:textfield name="keyWords" id="keyWords" cssClass="input input_wa fl"/><span id="keyWordsTip"></span></td>
		</tr>
		<tr class="tr1 vt">
			<td class="td1 tar">邮编：</td>
			<td class="td2"><s:textfield name="post" id="post" cssClass="input input_wa fl"/><span id="postTip"></span></td>
		</tr>
		<tr class="tr1 vt">
			<td class="td1 tar">最后修改日期：</td>
			<td class="td2"><s:date name="lastmodifyTime" format="yyyy-MM-dd HH:mm:ss" /><span id="lastmodifyTimeTip"></span></td>
		</tr>
	</table>
	<div class="tac mb5" style="margin-top:5px;margin-right:20px;text-align: align;">
		<span class="btn"><span><button type="button" onclick="javascript:submitForm()">提交</button></span></span>
		<span class="btn"><span><button type="button" onclick="javascript:clearForm()">清空</button></span></span>
		<span class="btn"><span><button id="closeBtn" onclick="tb_remove()" type="button">关闭</button></span></span>
	</div>
</s:form>
<script type="text/javascript">  	
  	function submitForm(){
  		var form = $('#updateForm');
		if(form){
			if(!ValidUtil.isFormValid('updateForm')) return;
			ajaxSubmitForm(form, function(){
				$('#searchForm').submit();
			});
		}
  	};
  	function clearForm(){
  		$('#updateForm input').each(function(i,el){
  			$(el).attr('value','');
  		});
  	}
</script>
</body>
</html>