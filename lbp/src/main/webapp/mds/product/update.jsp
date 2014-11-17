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
				formid:"addOrUpdateForm",
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
			
			ValidUtil.init('scientificname','请输入学名','1-50位字符','正确',false).notEmpty().length(1,100);
			ValidUtil.init('unit','请输入单位','1-5位字符','正确',false).notEmpty().length(1, 5);
			ValidUtil.init('origin','请输入生产厂商','1-50位字符','正确',false).notEmpty().length(1,100);
			ValidUtil.init('ownerId_ac','请输入所属厂商','选择所属厂商','正确',false).notEmpty().length(1,100);
			$('#ownerName_ac').simpleAutoComplete(path+"/mds/org/autoCompleteMoreOrg.action?typeId=000001395017",
				{
					hiddenElement : {
						ownerId_ac : 'id'
					}
			 	},function(obj){}
			 );
		});

	</script>
</head>

<body>
<s:form id="addOrUpdateForm" action="addOrUpdateProduct.action" method="post" onsubmit="return ajaxCallback(this)">
	<table class="admin_table" width="100%" cellspacing="0" cellpadding="0">
		<tr class="tr1 vt">
			<td class="td1 tar"><span class='high_star'>*</span>产品通用名：</td>
			<td class="td2">
				<s:hidden name="id" id="xid" />
				<s:hidden name="relid" id="xrelid" />
				<s:hidden name="flag" id="xflag" />
				<s:hidden name="table" id="xtable" />
				<s:textfield name="commonname" id="commonname" valid="commonname" cssClass="input input_wa fl"/><span id="commonnameTip"></span>
				<input type="hidden" value="<s:property value='commonname'/>" id="commonname_pre"/>
			</td>
		</tr>
		<tr class="tr1 vt">
			<td class="td1 tar"><span class='high_star'>*</span>编码：</td>
			<td class="td2">
				<s:textfield name="code" id="code" valid="code" cssClass="input input_wa fl"/><span id="codeTip"></span>
				<input type="hidden" value="<s:property value='code'/>" id="code_pre"/>
			</td>
		</tr>
		<tr class="tr1 vt">
			<td class="td1 tar"><span class='high_star'>*</span>学名：</td>
			<td class="td2">
				<s:textfield name="scientificname" id="scientificname" valid="scientificname" cssClass="input input_wa fl"/><span id="scientificnameTip"></span>
				<input type="hidden" value="<s:property value='scientificname'/>" id="scientificname_pre"/>
			</td>
		</tr>
		<tr class="tr1 vt">
			<td class="td1 tar"><span class='high_star'>*</span>单位：</td>
			<td class="td2">
				<s:textfield name="unit" id="unit" valid="unit" cssClass="input input_wa fl"/><span id="unitTip"></span>
				<input type="hidden" value="<s:property value='unit'/>" id="unit_pre"/>
			</td>
		</tr>
		<tr class="tr1 vt">
			<td class="td1 tar">包装：</td>
			<td class="td2">
				<s:textfield name="pack" id="pack" valid="pack" cssClass="input input_wa fl"/><span id="packTip"></span>
				<input type="hidden" value="<s:property value='pack'/>" id="pack_pre"/>
			</td>
		</tr>
		<tr class="tr1 vt">
			<td class="td1 tar">规格：</td>
			<td class="td2">
				<s:textfield name="spec" id="spec" valid="spec" cssClass="input input_wa fl"/><span id="specTip"></span>
				<input type="hidden" value="<s:property value='spec'/>" id="spec_pre"/>
			</td>
		</tr>
		<tr class="tr1 vt">
			<td class="td1 tar">批号：</td>
			<td class="td2">
				<s:textfield name="batchnumber" id="batchnumber" valid="batchnumber" cssClass="input input_wa fl"/><span id="batchnumberTip"></span>
				<input type="hidden" value="<s:property value='batchnumber'/>" id="batchnumber_pre"/>
			</td>
		</tr>
		<tr class="tr1 vt">
			<td class="td1 tar"><span class='high_star'>*</span>生产厂商：</td>
			<td class="td2">
				<s:textfield name="origin" id="origin" valid="origin" cssClass="input input_wa fl"/><span id="originTip"></span>
				<input type="hidden" value="<s:property value='origin'/>" id="origin_pre"/>
			</td>
		</tr>
		<tr class="tr1 vt">
			<td class="td1 tar"><span class='high_star'>*</span>所属厂商：</td>
			<td class="td2">
				<s:textfield name="ownerName" id="ownerName_ac" valid="ownerName" cssClass="input input_wa fl"/><span id="ownerId_acTip"></span>
				<s:hidden name="ownerId" value="<s:property value='ownerId'/>" id="ownerId_ac"></s:hidden>
			</td>
		</tr>
		<tr class="tr1 vt">
			<td class="td1 tar">类别：</td>
			<td class="td2">
				<s:textfield name="typeId" id="typeId" disabled="true" valid="typeId" cssClass="input input_wa fl"/><span id="typeIdTip"></span>
				<input type="hidden" value="<s:property value='typeId'/>" id="typeId_pre"/>
			</td>
		</tr>
		<tr class="tr1 vt">
			<td class="td1 tar">是否进口：</td>
			<td class="td2">
				<s:if test="isImport == 1">
					<s:radio name="isImport" list="#{0:'否', 1:'是' }" value="1" cssClass=""/>
				</s:if><s:else>
					<s:radio name="isImport" list="#{0:'否', 1:'是' }" value="0" cssClass=""/>
				</s:else>
				<input type="hidden" value="<s:property value='isImport'/>" id="isImport_pre"/>
			</td>
		</tr>
		<tr class="tr1 vt">
			<td class="td1 tar">是否停用：</td>
			<td class="td2">
				<s:if test="isDisable == 1">
					<s:radio name="isDisable" list="#{0:'启用', 1:'停用' }" value="1"/>
				</s:if><s:else>
					<s:radio name="isDisable" list="#{0:'启用', 1:'停用' }" value="0"/>
				</s:else>
				<input type="hidden" value="<s:property value='isDisable'/>" id="isDisable_pre"/>
			</td>
		</tr>
		<tr class="tr1 vt">
			<td class="td1 tar">备注：</td>
			<td class="td2">
				<s:textarea id="memo" name="memo" valid="memo" cssClass="input input_wa fl" />
				<input type="hidden" value="<s:property value='memo'/>" id="memo_pre"/>
				<s:hidden id="audiPass" name="creator"/>
				<s:hidden id="createTime" name="createTime"/>
				<s:hidden id="fingerMark" name="fingerMark"/>
			</td>
		</tr>
		<tr class="tr1 vt">
			<td class="td1 tar"><span class="c4">直接通过审核</span>：</td>
			<td class="td2">
				<input type="checkbox" id="audiPass" name="audiPass" value="2"/>
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
	function isChange(){
		var els = $("input[valid],textarea[valid]");
		for(var i=0; i < els.size(); i++){
			var eid = $(els[i]).attr("valid");
			var evalue = $(els[i]).attr("value");
			var prevalue = $("#"+eid+"_pre").attr("value");
			if(evalue != prevalue){
				return true;
			}
		}
		var eradios = $("input[type='radio'][class]");
		for(var i=0; i < eradios.size(); i++){
			var ck = $(eradios[i]).attr("checked");
			if(ck == 'checked'){
				var eid = $(eradios[i]).attr("name");
				var evalue = $(eradios[i]).attr("value");
				var prevalue = $("#"+eid+"_pre").attr("value");
				if(evalue != prevalue){
					return true;
				}
			}
		}
		return false;
	}
	
  	function submitForm(){
		if(!isChange()){
			showTip("请修改后再提交!");
			return;
		}
  		var form = $('#addOrUpdateForm');
		if(form){
			$("#commonname").blur();
			$("#code").blur();
			if(!ValidUtil.isFormValid('addOrUpdateForm')) return;
			ajaxSubmitForm(form, function(){
				$('#searchForm').submit();
			});
		}
  	};
  	
  	function clearForm(){
  		$('#addOrUpdateForm input').each(function(i,el){
  			$(el).attr('value','');
  		});
  	}
</script>
</body>
</html>