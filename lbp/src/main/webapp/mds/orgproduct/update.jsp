<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="x-ua-compatible" content="ie=7" />
	<link href="<%=request.getContextPath()%>/index/css/admin_style.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath()%>/thirdjs/thickbox/thickbox.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath()%>/thirdjs/validator/css/validator.css" type="text/css" rel="stylesheet"/>
	<link href="<%=request.getContextPath()%>/thirdjs/jquery/jquery.autocomplete.css" type="text/css" rel="stylesheet"/>
	<link href="<%=request.getContextPath()%>/thirdjs/autocomplete/simpleAutoComplete.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath()%>/index/css/component.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath()%>/common/css/common.css" rel="stylesheet" type="text/css" />
	
	<script src="<%=request.getContextPath()%>/index/jquery-1.4.2.min.js"></script>
	<script src="<%=request.getContextPath()%>/thirdjs/jquery/jquery.form.js"></script>
	<script src="<%=request.getContextPath()%>/common/util.js"></script>
	<script src="<%=request.getContextPath()%>/common/windows.js"></script>
	<script src="<%=request.getContextPath()%>/thirdjs/combox/combox.js"></script>
	<script src="<%=request.getContextPath()%>/common/function.js"></script>
	<script src="<%=request.getContextPath()%>/common/formutil.js"></script>
	
	<script src="<%=request.getContextPath()%>/thirdjs/thickbox/thickbox.js"></script>
	<script src="<%=request.getContextPath()%>/thirdjs/autocomplete/simpleAutoComplete.js"></script>
	
	<script src="<%=request.getContextPath()%>/thirdjs/validator/formValidator.js"></script>
	<script src="<%=request.getContextPath()%>/thirdjs/validator/formValidatorRegex.js"></script>
	<script src="<%=request.getContextPath()%>/thirdjs/datepicker/WdatePicker.js"></script>
	
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
			ValidUtil.init('commonname','请输入通用名','1-50位字符','正确',false).notEmpty().length(1,100);
			ValidUtil.init('code','请输入编码','1-50位字符','正确',false).notEmpty().length(1,100);
			ValidUtil.init('unit','请输入单位','1-5位字符','正确',false).notEmpty().length(1, 5);
			ValidUtil.init('origin','请输入生产厂商','1-50位字符','正确',false).notEmpty().length(1,100);
			ValidUtil.init('ownerAscId','请选择所属企业','选择所属企业','正确',false).notEmpty().length(1,100);
			ValidUtil.init('productAscId','请选择对应产品','选择对应产品','正确',false).notEmpty().length(1,100);
			$('#ownerAscId_ac').simpleAutoComplete(path+"/mds/org/autoCompleteMoreOrg.action",
				{
					hiddenElement : {
						ownerAscId : 'id'
					}
			 	},function(obj){}
			);
			$('#productAscId_ac').simpleAutoComplete(path+"/mds/product/autoCompleteProduct.action",
				{
					hiddenElement : {
						productAscId : 'id'
					}
			 	},function(obj){}
			);
		});
	</script>
</head>

<body>
<s:form id="updateForm" action="updateOrgProduct.action" method="post" onsubmit="return ajaxCallback(this)">
	<s:hidden name="id"/>
	<table class="admin_table" width="100%" cellspacing="0" cellpadding="0">
		<tr class="tr1 vt">
			<td class="td1 tar"><span class='high_star'>*</span>所属企业：</td>
			<td class="td2">
				<s:textfield name="ownerAscName"  id="ownerAscId_ac" cssClass="input input_wa fl"/><span id="ownerAscIdTip"></span>
				<s:hidden name="ownerAscId" id="ownerAscId"></s:hidden>
				<span id="ownerAscIdTip"></span>
			</td>
		</tr>
		<tr class="tr1 vt">
			<td class="td1 tar"><span class='high_star'>*</span>对应产品：</td>
			<td class="td2">
				<s:textfield name="productAscName"  id="productAscId_ac" cssClass="input input_wa fl"/><span id="productAscIdTip"></span>
				<s:hidden name="productAscId" id="productAscId"></s:hidden>
				<span id="productAscIdTip"></span>
			</td>
		</tr>
		<tr class="tr1 vt">
			<td class="td1 tar"><span class='high_star'>*</span>编码：</td>
			<td class="td2"><s:textfield name="code" id="code" cssClass="input input_wa fl"/><span id="codeTip"></span></td>
		</tr>
		<tr class="tr1 vt">
			<td class="td1 tar"><span class='high_star'>*</span>产品通用名：</td>
			<td class="td2">
				<s:textfield name="commonname" id="commonname" cssClass="input input_wa fl"/><span id="commonnameTip"></span>
			</td>
		</tr>
				<tr class="tr1 vt">
			<td class="td1 tar">学名：</td>
			<td class="td2"><s:textfield name="scientificname" id="scientificname" cssClass="input input_wa fl"/><span id="scientificnameTip"></span></td>
		</tr>
		<tr class="tr1 vt">
			<td class="td1 tar"><span class='high_star'>*</span>单位：</td>
			<td class="td2"><s:textfield name="unit" id="unit" cssClass="input input_wa fl"/><span id="unitTip"></span></td>
		</tr>
		<tr class="tr1 vt">
			<td class="td1 tar">包装：</td>
			<td class="td2"><s:textfield name="pack" id="pack" cssClass="input input_wa fl"/><span id="packTip"></span></td>
		</tr>
		<tr class="tr1 vt">
			<td class="td1 tar">规格：</td>
			<td class="td2"><s:textfield name="spec" id="spec" cssClass="input input_wa fl"/><span id="specTip"></span></td>
		</tr>
		<tr class="tr1 vt">
			<td class="td1 tar">批号：</td>
			<td class="td2"><s:textfield name="batchnumber" id="batchnumber" cssClass="input input_wa fl"/><span id="batchnumberTip"></span></td>
		</tr>
		<tr class="tr1 vt">
			<td class="td1 tar"><span class='high_star'>*</span>生产厂商：</td>
			<td class="td2"><s:textfield name="origin" id="origin" cssClass="input input_wa fl"/><span id="originTip"></span></td>
		</tr>
		<tr class="tr1 vt">
			<td class="td1 tar">换算比：</td>
			<td class="td2">
				<s:if test="flag!='update'">
					<s:textfield name="discount" id="discount" cssClass="input input_wa fl"  	value='1'/><span id="discountTip"></span>
				</s:if>
				<s:else>
					<s:textfield name="discount" id="discount" cssClass="input input_wa fl"  	/><span id="discountTip"></span>
				</s:else>
						
			</td>
		</tr>
		<tr class="tr1 vt">
			<td class="td1 tar">产品类别：</td>
			<td class="td2"><s:textfield name="productType" id="productType" cssClass="input input_wa fl"/><span id="productTypeTip"></span></td>
		</tr>
		<s:if test="flag!='update'">
		<tr class="tr1 vt">
			<td class="td1 tar">质检标签：</td>
			<td class="td2">
				<s:if test="isDisable == 2">
					<s:radio name="qualityCheck" list="#{ 1:'新增数据',2:'免检数据' }" value="2"/>
				</s:if>
				<s:else>
					<s:radio name="qualityCheck" list="#{ 1:'新增数据',2:'免检数据' }" value="1"/>
				</s:else>
			</td>
		</tr>
		</s:if>
		<tr class="tr1 vt">
			<td class="td1 tar">是否停用：</td>
			<td class="td2">
				<s:if test="isDisable == 1">
					<s:radio name="isDisable" list="#{0:'启用', 1:'停用' }" value="1"/>
				</s:if><s:else>
					<s:radio name="isDisable" list="#{0:'启用', 1:'停用' }" value="0"/>
				</s:else>
			</td>
		</tr>
		<tr class="tr1 vt">
			<td class="td1 tar">备注：</td>
			<td class="td2">
				<s:textarea id="memo" name="memo" cssClass="input_wa mr10 fl" />
				<s:hidden id="createTime" name="createTime"/>
				<s:hidden id="fingerMark" name="fingerMark"/>
				<s:hidden name="relid" />
				<s:hidden name="fileId" id="fileId" />
				<s:hidden name="flag" id="flag" />
				<s:hidden name="table" id="xtable" />
			</td>
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
			if(!ValidUtil.isFormValid('updateForm'))
				return;
			if($('#ownerAscId').val()==""){
				$.formValidator.setFailState('ownerAscIdTip','所属企业不存在');
				return;
			} else {
				$.formValidator.setFailState('ownerAscIdTip','');
			}
			if($('#productAscId').val()==""){
				$.formValidator.setFailState('productAscIdTip','对应产品不存在');
				return;
			} else {
				$.formValidator.setFailState('productAscIdTip','');
			}
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