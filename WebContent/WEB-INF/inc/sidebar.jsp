<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
		<!--sidebar start-->
		<aside>
    		<div id="sidebar" class="nav-collapse">
        	<!-- sidebar menu start-->
        		<div class="leftside-navigation">
            		<ul class="sidebar-menu" id="nav-accordion">
                		
                		<li class="sub-menu">
                    		<a href="javascript:;">
                        		<i class="fa fa-th-list"></i>
                        		<span>Môn Học</span>
                    		</a>
                    		<ul class="sub">
								<li><a href="${pageContext.request.contextPath}/admin/monhoc">Danh môn học</a></li>
								<li><a href="${pageContext.request.contextPath}/admin/monhoc/them">Thêm môn học</a></li>
                    		</ul>
                		</li>
                		<li class="sub-menu">
                    		<a href="javascript:;">
                        		<i class="fa fa-th-list"></i>
                        		<span>Giáo viên</span>
                    		</a>
                    		<ul class="sub">
								<li><a href="${pageContext.request.contextPath}/admin/giaovien">Danh giáo viên</a></li>
								<li><a href="${pageContext.request.contextPath}/admin/giaovien/them">Thêm giáo viên</a></li>
                    		</ul>
                		</li>
                		<li class="sub-menu">
                    		<a href="javascript:;">
                        		<i class="fa fa-th-list"></i>
                        		<span>Học sinh</span>
                    		</a>
                    		<ul class="sub">
								<li><a href="${pageContext.request.contextPath}/admin/hocsinh">Danh học sinh</a></li>
								<li><a href="${pageContext.request.contextPath}/admin/hocsinh/them">Thêm học sinh</a></li>
                    		</ul>
                		</li>
                		
                		
           			 </ul>            
           		</div>
        		<!-- sidebar menu end-->
    		</div>
		</aside>
		<!--sidebar end-->