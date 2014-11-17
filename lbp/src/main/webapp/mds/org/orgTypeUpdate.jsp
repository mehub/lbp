<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<%=request.getContextPath()%>/index/css/admin_style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	$(document).ready(function(){
		$.formValidator.initConfig({	
			formid:"addOrUpdateForm",
			onerror:function(msg){
				showTip(msg);;
				return false;
			},
			onsuccess:function(){
				return true;
			}
		});	
		ValidUtil.init('typeName','请输入名称','1-50位字符','正确',false).notEmpty().length(1,100);
		ValidUtil.init('code','请输入编码','1-50位字符','正确',false).notEmpty().length(1,100);
	});
	function submitForm(){
  		var form = $('#addOrUpdateForm');
		if(form){
			if(!ValidUtil.isFormValid('addOrUpdateForm')) return;
			ajaxSubmitForm(form, function(){
				nextPage_orgType();
			});
		}
  	};
</script>
</head>
<body>
	<form id="addOrUpdateForm" name="addOrUpdateForm" action="saveOrgType.action" method="post" onsubmit="return ajaxCallback(this)">
		<s:hidden name="id"/>
		<table class="admin_table" width="100%" cellspacing="0" cellpadding="0">
			<tr class="tr1 vt">
				<td class="td1 tar"><span class='high_star'>*</span>类型名称：</td>
				<td class="td2"><s:textfield name="typeName" id="typeName" cssClass="input input_wa fl"/><span id="typeNameTip"></span></td>
			</tr>
			<tr class="tr1 vt">
				<td class="td1 tar"><span class='high_star'>*</span>类型编码：</td>
				<td class="td2"><s:textfield name="code" id="code" cssClass="input input_wa fl"/><span id="codeTip"></span></td>
			</tr>
		</table>
		<div class="tac mb10" style="margin-top:10px;margin-right:20px;text-align: center;">
			<span class="btn"><span><button id="submitAddUser" type="button" onclick="javascript:submitForm()">提 交</button></span></span>
			<span class="btn"><span><button id="closeBtn" onclick="tb_remove()" type="button">关闭</button></span></span>
		</div>
	</form>
</body>
</html>