<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="x-ua-compatible" content="ie=7" />
	<title>质检标签</title>
	
</head>
<body>
<div style="width: 100% ; height: 100%; overflow-y:scroll; ">
<s:form id="qualityCheckForm" action="qualityCheckFranchiseOrg.action" method="post" onsubmit="return ajaxCallback(this)">
	<table class="admin_table" width="100%" cellspacing="0" cellpadding="0">
		<tr class="tr1 vt">
				<td class="td1 tar">质检标签：</td>
				<td class="td2">
					<s:hidden name="id"></s:hidden>
					<s:radio name="qualityCheck" list="#{ 0:'历史数据',1:'新增数据',2:'免检数据' }" />
				</td>
		</tr>
	</table>
	<div class="tac mb5" style="margin-top:5px;margin-right:20px;text-align: align;">
		<span class="btn"><span><button type="button" onclick="javascript:submitForm()">提交</button></span></span>
		<span class="btn"><span><button id="closeBtn" onclick="tb_remove()" type="button">关闭</button></span></span>
	</div>
</s:form>
</div>
<script type="text/javascript">  	
  	function submitForm(){
  		var form = $('#qualityCheckForm');
		if(form){
			if(!ValidUtil.isFormValid('qualityCheckForm'))
				return;
			ajaxSubmitForm(form, function(){
				$('#searchForm').submit();
			});
		}
  	};
</script>
</body>
</html>