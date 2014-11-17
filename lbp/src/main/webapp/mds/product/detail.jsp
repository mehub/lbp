<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
			<td class="td1 tar"><span class='high_star'>*</span>产品通用名：</td>
			<td class="td2">
				<s:hidden name="id" id="id" />
				<s:textfield name="commonname" id="commonname" cssClass="input input_wa fl" disabled="true"/>
			</td>
		</tr>
		<tr class="tr1 vt">
			<td class="td1 tar"><span class='high_star'>*</span>编码：</td>
			<td class="td2"><s:textfield name="code" id="code" cssClass="input input_wa fl" disabled="true"/></td>
		</tr>
		<tr class="tr1 vt">
			<td class="td1 tar"><span class='high_star'>*</span>学名：</td>
			<td class="td2"><s:textfield name="scientificname" id="scientificname" cssClass="input input_wa fl" disabled="true"/></td>
		</tr>
		<tr class="tr1 vt">
			<td class="td1 tar"><span class='high_star'>*</span>单位：</td>
			<td class="td2"><s:textfield name="unit" id="unit" cssClass="input input_wa fl" disabled="true"/></td>
		</tr>
		<tr class="tr1 vt">
			<td class="td1 tar">包装：</td>
			<td class="td2"><s:textfield name="pack" id="pack" cssClass="input input_wa fl" disabled="true"/></td>
		</tr>
		<tr class="tr1 vt">
			<td class="td1 tar">规格：</td>
			<td class="td2"><s:textfield name="spec" id="spec" cssClass="input input_wa fl" disabled="true"/></td>
		</tr>
		<tr class="tr1 vt">
			<td class="td1 tar">批号：</td>
			<td class="td2"><s:textfield name="batchnumber" id="batchnumber" cssClass="input input_wa fl" disabled="true"/></td>
		</tr>
		<tr class="tr1 vt">
			<td class="td1 tar"><span class='high_star'>*</span>生产厂商：</td>
			<td class="td2"><s:textfield name="origin" id="origin" cssClass="input input_wa fl" disabled="true"/></td>
		</tr>
		<tr class="tr1 vt">
			<td class="td1 tar"><span class='high_star'>*</span>所属厂商：</td>
			<td class="td2"><s:textfield name="ownerName" id="ownerName" cssClass="input input_wa fl" disabled="true"/></td>
		</tr>
		<tr class="tr1 vt">
			<td class="td1 tar">类别：</td>
			<td class="td2"><s:textfield name="typeId" id="typeId" cssClass="input input_wa fl" disabled="true"/></td>
		</tr>
		<tr class="tr1 vt">
			<td class="td1 tar">是否进口：</td>
			<td class="td2">
				<s:radio name="isImport" list="#{0:'否', 1:'是' }" disabled="true"/>
			</td>
		</tr>
		<tr class="tr1 vt">
			<td class="td1 tar">是否停用：</td>
			<td class="td2">
				<s:radio name="isDisable" list="#{0:'否', 1:'是' }" disabled="true"/>
			</td>
		</tr>
		<tr class="tr1 vt">
			<td class="td1 tar">创建人：</td>
			<td class="td2"><s:textfield name="creator" id="creator" cssClass="input input_wa fl" disabled="true"/></td>
		</tr>
		<tr class="tr1 vt">
			<td class="td1 tar">创建时间：</td>
			<td class="td2"><s:date name="createTime" format="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
		<tr class="tr1 vt">
			<td class="td1 tar">最后修改日期：</td>
			<td class="td2"><s:date name="lastmodifyTime" format="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
		<tr class="tr1 vt">
			<td class="td1 tar">审核人：</td>
			<td class="td2"><s:textfield name="audiUser" id="audiUser" cssClass="input input_wa fl" disabled="true"/></td>
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