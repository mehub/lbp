<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="x-ua-compatible" content="ie=7" />
	<title>明细</title>
</head>
<body>
<s:form id="detailForm">
<div style="width: 100% ; height: 100%; overflow-y:scroll; ">
	<table class="admin_table" width="100%" cellspacing="0" cellpadding="0">
		<tr class="tr1 vt">
				<td class="td1 tar">所属企业：</td>
				<td class="td2">
					<s:textfield name="ownerAscName" id="ownerAscId_ac" cssClass="input input_wa" disabled="true"/>
					<s:hidden name="ownerAscId" id="ownerAscId" disabled="true"></s:hidden>
				</td>
		</tr>
		<tr class="tr1 vt">
			<td class="td1 tar">对应企业：</td>
			<td class="td2">
				<s:textfield name="partnerAscName" id="partnerAscId_ac" cssClass="input input_wa" disabled="true"/>
				<s:hidden name="partnerAscId" id="partnerAscId" disabled="true"></s:hidden>
			</td>
		</tr>
		<tr class="tr1 vt">
			<td class="td1 tar"><span class='high_star'>*</span>名称：</td>
			<td class="td2">
				<s:hidden name="id" id="id" />
				<s:textfield name="orgName" id="orgName" cssClass="input input_wa fl" disabled="true"/><span id="commonnameTip"></span>
			</td>
		</tr>
		<tr class="tr1 vt">
			<td class="td1 tar"><span class='high_star'>*</span>经销商级别：</td>
			<td class="td2"><s:textfield name="xlevel" id="xlevel" cssClass="input input_wa fl" disabled="true"/><span id="scientificnameTip"></span></td>
		</tr>
		<tr class="tr1 vt">
			<td class="td1 tar"><span class='high_star'>*</span>终端类别：</td>
			<td class="td2"><s:textfield name="xtype" id="xtype" cssClass="input input_wa fl" disabled="true"/><span id="unitTip"></span></td>
		</tr>
		<tr class="tr1 vt">
			<td class="td1 tar">省：</td>
			<td class="td2"><s:textfield name="province" id="province" cssClass="input input_wa fl" disabled="true"/><span id="packTip"></span></td>
		</tr>
		<tr class="tr1 vt">
			<td class="td1 tar">市：</td>
			<td class="td2"><s:textfield name="city" id="city" cssClass="input input_wa fl" disabled="true"/><span id="specTip"></span></td>
		</tr>
		<tr class="tr1 vt">
			<td class="td1 tar">质检标签：</td>
			<td class="td2">
				<s:radio name="qualityCheck" list="#{0:'历史数据', 1:'新增数据',2:'免检数据' }" disabled="true"/>
			</td>
		</tr>
		<tr class="tr1 vt">
			<td class="td1 tar">是否停用：</td>
			<td class="td2">
				<s:radio name="isDisable" list="#{0:'启用', 1:'停用'  }" disabled="true"/>
			</td>
		</tr>
		<tr class="tr1 vt">
			<td class="td1 tar">创建人：</td>
			<td class="td2"><s:textfield name="creator" id="creator" cssClass="input input_wa fl" disabled="true"/><span id="typeIdTip"></span></td>
		</tr>
		<tr class="tr1 vt">
			<td class="td1 tar">创建时间：</td>
			<td class="td2"><s:date name="createTime" format="yyyy- MM-dd HH:mm:ss"/><span id="typeIdTip"></span></td>
		</tr>
		<tr class="tr1 vt">
			<td class="td1 tar">最后修改日期：</td>
			<td class="td2"><s:date name="lastmodifyTime" format="yyyy- MM-dd HH:mm:ss"/><span id="typeIdTip"></span></td>
		</tr>
		<tr class="tr1 vt">
			<td class="td1 tar">审核人：</td>
			<td class="td2"><s:textfield name="audiUser" id="audiUser" cssClass="input input_wa fl" disabled="true"/><span id="typeIdTip"></span></td>
		</tr>
		<s:if test="audiState != null">
			<tr class="tr1 vt">
				<td class="td1 tar">审核状态：</td>
				<td class="td2">
					<s:select name="audiState" list="@com.asc.mds.root.state.AudiState@values()" cssClass="input" 
							  listValue="value" listKey="code" disabled="true"/>
				</td>
			</tr>
			<tr class="tr1 vt">
				<td class="td1 tar">审核备注：</td>
				<td class="td2"><s:textfield name="audiMemo" id="audiMemo" cssClass="input input_wa fl" disabled="true"/></td>
			</tr>
		</s:if>
		<tr class="tr1 vt">
			<td class="td1 tar">备注：</td>
			<td class="td2">
				<s:textarea id="memo" name="memo" cssClass="input_wa mr10 fl" disabled="true"/>
			</td>
		</tr>
	</table>
</div>
</s:form>
</body>
</html>