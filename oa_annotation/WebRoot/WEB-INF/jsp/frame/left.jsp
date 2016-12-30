<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/jsp/comm/common.jsp"%>
<script language="javascript" src="${pageContext.request.contextPath}/js/jquery-ztree-2.5.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/js/menu.js"></script>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>导航菜单</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="css/blue/menu.css" />
<link rel="stylesheet" type="text/css" href="zTreeStyle/zTreeStyle.css">
</head>
<body style="margin: 0">
<TABLE border=0 width="700">
	<TR>
		<TD width=340px align=center valign=top>
		<div class="zTreeDemoBackground">
			<ul id="menuTree" class="tree"></ul>
		</div>		
		</TD>
	</TR>
</TABLE>
</body>
</html>

