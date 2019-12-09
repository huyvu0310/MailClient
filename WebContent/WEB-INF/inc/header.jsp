<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
	<title>My Mail Service</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	
	<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
	<!-- bootstrap-css -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/templates/assets/css/bootstrap.min.css" >
	<!-- //bootstrap-css -->
	<!-- Custom CSS -->
	<link href="${pageContext.request.contextPath}/templates/assets/css/style.css" rel='stylesheet' type='text/css' />
	<link href="${pageContext.request.contextPath}/templates/assets/css/style-responsive.css" rel="stylesheet"/>
	<!-- font CSS -->
	<!-- <link href='//fonts.googleapis.com/css?family=Roboto:400,100,100italic,300,300italic,400italic,500,500italic,700,700italic,900,900italic' rel='stylesheet' type='text/css'>-->
	<!-- font-awesome icons -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/templates/assets/css/font.css" type="text/css"/>
	<link href="${pageContext.request.contextPath}/templates/assets/css/font-awesome.css" rel="stylesheet"> 
	<!-- //font-awesome icons -->
	<script src="${pageContext.request.contextPath}/templates/assets/js/jquery2.0.3.min.js" type="text/javascript"></script>
	
	<!-- Date -->
	<link href="${pageContext.request.contextPath}/templates/assets/css/jquery.datepick.css" rel="stylesheet">
	<script src="${pageContext.request.contextPath}/templates/assets/js/jquery.plugin.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/templates/assets/js/jquery.datepick.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/templates/assets/js/bootstrap.js"></script>
<script src="<%=request.getContextPath()%>/templates/assets/js/jquery.dcjqaccordion.2.7.js"></script>
<script src="<%=request.getContextPath()%>/templates/assets/js/scripts.js"></script>
<script src="<%=request.getContextPath()%>/templates/assets/js/jquery.slimscroll.js"></script>
<script src="<%=request.getContextPath()%>/templates/assets/js/jquery.nicescroll.js"></script>
<!--[if lte IE 8]><script language="javascript" type="text/javascript" src="js/flot-chart/excanvas.min.js"></script><![endif]-->
<script src="<%=request.getContextPath()%>/templates/assets/js/jquery.scrollTo.js"></script>
</head>
<body>
	<section id="container">
	<!--header start-->
		<header class="header fixed-top clearfix">
			<!--logo start-->
			<div class="brand">
				<a href="${pageContext.request.contextPath}/inbox" class="logo">GMAIL</a>
    			<div class="sidebar-toggle-box">
        			<div class="fa fa-bars"></div>
    			</div>
			</div>
			<!--logo end-->
			<div class="nav notify-row" id="top_menu">
    			<!--  notification start -->
    			<ul class="nav top-menu">
        			<!-- settings start -->
        			<li class="dropdown">
            			<a data-toggle="dropdown" class="dropdown-toggle" href="#">
                			<i class="fa fa-tasks"></i>
                			<span class="badge bg-success">0</span>
            			</a>
            			<ul class="dropdown-menu extended tasks-bar">
                			<li>
                    			<p class="">Tính năng đang cập nhật!!</p>
                			</li>
            			</ul>
        			</li>
        			<!-- settings end -->
       				<!-- inbox dropdown start-->
        			<li id="header_inbox_bar" class="dropdown">
            			<a data-toggle="dropdown" class="dropdown-toggle" href="#">
                			<i class="fa fa-envelope-o"></i>
                			<span class="badge bg-important">0</span>
            			</a>
            			<ul class="dropdown-menu extended inbox">
                			<li>
                    			<p class="red">Tính năng đang cập nhật</p>
                			</li>
            			</ul>
        			</li>
        			<!-- inbox dropdown end -->
    			</ul>
   				<!--  notification end -->
			</div>
			<div class="top-nav clearfix">
    			<!--search & user info start-->
    			<ul class="nav pull-right top-menu">
        			<!-- user login dropdown start-->
        			<li class="dropdown">
            			<a data-toggle="dropdown" class="dropdown-toggle" href="#">
                			<img alt="" src="${pageContext.request.contextPath}/templates/assets/images/2.png"/>
                			<span class="username">${userlogin.username}</span>
                			<b class="caret"></b>
            			</a>
            			<ul class="dropdown-menu extended logout">
                			
                			<li><a href="${pageContext.request.contextPath}/logout"><i class="fa fa-key"></i> Đăng xuất</a></li>
            			</ul>
        			</li>
        			<!-- user login dropdown end -->
    			</ul>
    			<!--search & user info end-->
			</div>
		</header>
		<!--header end-->