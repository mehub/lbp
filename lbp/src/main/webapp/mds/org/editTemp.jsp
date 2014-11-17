<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="<%=request.getContextPath()%>/mds/org/js/edit.js"></script>
</head>
<body>
	<form id="orgForm" name="orgForm" action="saveTempOrg.action" method="post">
		<s:hidden name="id"/>
		<s:hidden name="isDisable" value="1"/>
		<s:hidden name="fileId"/>
		<s:hidden name="fingerMark"/>
		<s:hidden name="createTime"/>
		<s:hidden name="creator"/>
		<s:hidden name="relid"/>
		
		<table class="admin_table" width="100%" cellspacing="0" cellpadding="0">
			<tr class="tr1 vt">
				<td class="td1 tar"><span class='high_star'>*</span>企业名称：</td>
				<td class="td2"><s:textfield id="orgName" name="orgName" cssClass="input input_wa fl"/><span id="orgNameTip"></span></td>
			</tr>
			<tr class="tr1 vt">
				<td class="td1 tar"><span class='high_star'>*</span>企业编码：</td>
				<td class="td2"><s:textfield id="code" name="code" cssClass="input input_wa fl"/><span id="codeTip"></span></td>
			</tr>
			<tr class="tr1 vt">
				<td class="td1 tar"><span class='high_star'>*</span>企业类型：</td>
				<td class="td2">
					<s:select name="typeId" list="types"  listKey="id"  listValue="typeName" headerKey="" headerValue="--请选择类型--" cssClass="input input_wa fl"/>
					<span id="typeIdTip"></span>
				</td>
			</tr>
			<tr class="tr1 vt">
				<td class="td1 tar"><span class='high_star'>*</span>区域：</td>
				<td class="td2">
					<s:hidden name="regionId" id="regionId" />
					<s:textfield name="regionName" id="regionName" disabled="true" cssClass="input input_wa fl"/>
					<span class="fl mr5" style="line-height: 22px;"><a href="javascript: void(0);" onclick="selectRegion('<s:property value="parentId"/>')">选择</a></span>
					<span id="regionNameTip"></span>
				</td>
			</tr>
			<tr class="tr1 vt">
				<td class="td1 tar">简称：</td>
				<td class="td2"><s:textfield id="shortName" name="shortName" cssClass="input input_wa fl"/><span id="shortNameTip"></span></td>
			</tr>
			<tr class="tr1 vt">
				<td class="td1 tar">电话：</td>
				<td class="td2"><s:textfield id="telephone" name="telephone" cssClass="input input_wa fl"/><span id="telephoneTip"></span></td>
			</tr>
			<tr class="tr1 vt">
				<td class="td1 tar">传真：</td>
				<td class="td2"><s:textfield id="fax" name="fax" cssClass="input input_wa fl"/><span id="faxTip"></span></td>
			</tr>
			<tr class="tr1 vt">
				<td class="td1 tar">email：</td>
				<td class="td2"><s:textfield id="email" name="email" cssClass="input input_wa fl"/><span id="emailTip"></span></td>
			</tr>
			<tr class="tr1 vt">
				<td class="td1 tar">地址：</td>
				<td class="td2"><s:textfield id="address" name="address" cssClass="input input_wa fl"/><span id="addressTip"></span></td>
			</tr>
			<tr class="tr1 vt">
				<td class="td1 tar">网址：</td>
				<td class="td2"><s:textfield id="url" name="url" cssClass="input input_wa fl"/><span id="urlTip"></span></td>
			</tr>
			<tr class="tr1 vt">
				<td class="td1 tar">经销商级别：</td>
				<td class="td2"><s:textfield id="xlevel" name="xlevel" cssClass="input input_wa fl"/><span id="xlevelTip"></span></td>
			</tr>
			<tr class="tr1 vt">
				<td class="td1 tar">分类性质：</td>
				<td class="td2"><s:textfield id="classify" name="classify" cssClass="input input_wa fl"/><span id="classifyTip"></span></td>
			</tr>
			<tr class="tr1 vt">
				<td class="td1 tar">质检标签：</td>
				<td class="td2">
					<s:radio name="qualityCheck" list="@com.asc.mds.root.state.CheckState@values()" listKey="code" listValue="value" theme="simple" />
				</td>
			</tr>
			<tr class="tr1 vt">
				<td class="td1 tar">备注：</td>
				<td class="td2"><s:textarea id="memo" name="memo"  cssClass="input input_wa fl"/></td>
			</tr>
			<tr class="tr1 vt">
				<td class="td1 tar"></td>
				<td class="td2" style="color:red;">
					<s:checkboxlist list="#{'2':'直接通过审核'}" name="audiPass" />
				</td>
			</tr>
		</table>
		<div class="tac mb10" style="margin-top:10px;margin-right:20px;text-align: center;">
			<span class="btn"><span><button type="button" onclick="saveOrUpdate()">提 交</button></span></span>
			<span class="btn"><span><button onclick="tb_remove()" type="button">关闭</button></span></span>
		</div>
	</form>
</body>
</html>