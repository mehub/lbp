<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="x-ua-compatible" content="ie=7" />

<title>区域</title>
<link href="<%=request.getContextPath()%>/index/css/admin_style.css" rel="stylesheet" type="text/css" />

<script src="<%=request.getContextPath()%>/index/jquery-1.4.2.min.js"></script>
<script src="<%=request.getContextPath()%>/thirdjs/jquery/jquery.jstree.js"></script>

<script type="text/javascript">
	$(function(){
		$("#ajaxTree").jstree({
			"ui":{
				"select_limit" : 1
			},
			"json_data":{
		     	"ajax":{
		     		"url":"<%=request.getContextPath()%>/mds/region/listJSTreeRegion.htm",
	         		"data":function(n){
		     		    return { parentId : n.attr ? n.attr("id") : -1 };
		     		 }
			 	 }
			},
			"plugins" : [ "themes", "json_data", "ui"]
		}).bind("select_node.jstree", function (e, data) {
			parentId = data.rslt.obj.attr("id");
			parentName = data.rslt.obj.attr("name");
		});
	});
	var parentId = "";
	var parentName = "";
</script>
</head>
<body>
<div id="ajaxTree" class="demo" style="background:#fff;"></div>
</body>
</html>