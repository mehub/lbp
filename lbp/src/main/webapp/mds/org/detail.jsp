<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<%=request.getContextPath()%>/index/css/admin_style.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<form id="orgForm">
	<div style="width: 100% ; height: 100%; overflow-y:scroll; ">
		<table class="admin_table" width="100%" cellspacing="0" cellpadding="0">
			<tr class="tr1 vt">
				<td class="td1 tar">企业名称：</td>
				<td class="td2"><s:textfield id="orgName" name="orgName" cssClass="input input_wa fl" disabled="true"/></td>
			</tr>
			<tr class="tr1 vt">
				<td class="td1 tar">企业编码：</td>
				<td class="td2"><s:textfield id="code" name="code" cssClass="input input_wa fl" disabled="true"/></td>
			</tr>
			<tr class="tr1 vt">
				<td class="td1 tar">企业类型：</td>
				<td class="td2">
					<s:select name="typeId" list="types" listKey="id"  listValue="typeName" headerKey="" headerValue="--请选择类型--" cssClass="input input_wa fl" disabled="true"/>
				</td>
			</tr>
			<tr class="tr1 vt">
				<td class="td1 tar">区域：</td>
				<td class="td2">
					<s:textfield name="regionName" id="regionName" cssClass="input input_wa fl"/>
				</td>
			</tr>
			<tr class="tr1 vt">
				<td class="td1 tar">简称：</td>
				<td class="td2"><s:textfield id="shortName" name="shortName" cssClass="input input_wa fl" disabled="true"/></td>
			</tr>
			<tr class="tr1 vt">
				<td class="td1 tar">电话：</td>
				<td class="td2"><s:textfield id="telephone" name="telephone" cssClass="input input_wa fl" disabled="true"/></td>
			</tr>
			<tr class="tr1 vt">
				<td class="td1 tar">传真：</td>
				<td class="td2"><s:textfield id="fax" name="fax" cssClass="input input_wa fl" disabled="true"/></td>
			</tr>
			<tr class="tr1 vt">
				<td class="td1 tar">email：</td>
				<td class="td2"><s:textfield id="email" name="email" cssClass="input input_wa fl" disabled="true"/></td>
			</tr>
			<tr class="tr1 vt">
				<td class="td1 tar">地址：</td>
				<td class="td2"><s:textfield id="address" name="address" cssClass="input input_wa fl" disabled="true"/></td>
			</tr>
			<tr class="tr1 vt">
				<td class="td1 tar">网址：</td>
				<td class="td2"><s:textfield id="url" name="url" cssClass="input input_wa fl" disabled="true"/></td>
			</tr>
			<tr class="tr1 vt">
				<td class="td1 tar">经销商级别：</td>
				<td class="td2"><s:textfield id="xlevel" name="xlevel" cssClass="input input_wa fl" disabled="true"/></td>
			</tr>
			<tr class="tr1 vt">
				<td class="td1 tar">分类性质：</td>
				<td class="td2"><s:textfield id="classify" name="classify" cssClass="input input_wa fl"/></td>
			</tr>
			<tr class="tr1 vt">
				<td class="td1 tar">质检标签：</td>
				<td class="td2">
					<s:radio name="qualityCheck" list="@com.asc.mds.root.state.CheckState@values()" listKey="code" listValue="value" theme="simple"/>					
				</td>
			</tr>
			<tr class="tr1 vt">
				<td class="td1 tar">创建人：</td>
				<td class="td2"><s:textfield name="creator" cssClass="input input_wa fl" disabled="true"/></td>
			</tr>
			<tr class="tr1 vt">
				<td class="td1 tar">创建时间：</td>
				<td class="td2"><s:date name="createTime" format="yyyy-MM-dd HH:mm:ss" /></td>
			</tr>
			<tr class="tr1 vt">
				<td class="td1 tar">最后修改时间：</td>
				<td class="td2"><s:date name="lastmodifyTime" format="yyyy-MM-dd HH:mm:ss"/></td>
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
				<td class="td2"><s:textarea id="memo" name="memo"  cssClass="input input_wa fl" disabled="true"/></td>
			</tr>
		</table>
		</div>
	</form>
</body>
</html>