<%@page import="java.text.SimpleDateFormat"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="bean.MessageView"%>
<%@page import="java.util.ArrayList"%>
<%@page import="javax.mail.Address"%>
<%@page import="javax.mail.Message"%>
<%@page import="bean.MailService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@include file="inc/header.jsp"%>
<section id="main-content" style="margin-left: 0px !important;">
	<section class="wrapper">
		<div class="mail-w3agile">
        <!-- page start-->
        <div class="row">
            <div class="col-sm-3 com-w3ls">
                <section class="panel">
                    <div class="panel-body">
                        <a href="<%=request.getContextPath()%>/compose"  class="btn btn-compose">
                            Compose Mail
                        </a>
                        <ul class="nav nav-pills nav-stacked mail-nav">
                            <li><a href="<%=request.getContextPath()%>/inbox"> <i class="fa fa-inbox"></i> Inbox  
                            	<%
                            		int numUnreadMsg = (int) session.getAttribute("numberUnreadMsg");
                            		if (numUnreadMsg > 0) {
                            	%>
                            	<span class="label label-danger pull-right inbox-notification"><%=numUnreadMsg %></span>
                            	<%} %>
                            </a></li>
                            <li><a href="<%=request.getContextPath()%>/sent"> <i class="fa fa-envelope-o"></i> Sent Mail</a></li>
                            <li class="active"><a href="<%=request.getContextPath()%>/draft"> <i class="fa fa-file-text-o"></i> Drafts 
                            <%
                            		int numDraft = (int) session.getAttribute("numberDraft");
                            		if (numDraft > 0) {
                            	%>
                            	<span class="label label-info pull-right inbox-notification"><%=numDraft %></span>
                            	<%} %>
                            </a></li>
                        <li><a href="<%=request.getContextPath()%>/spam"><i class="fa fa-ban"></i> Spam Mail</a></li>
                            
                        </ul>
                    </div>
                </section>

              
            </div>
            <div class="col-sm-9 mail-w3agile">
                <section class="panel">
                    <header class="panel-heading wht-bg">
                       <h4 class="gen-case">Drafts (${numDraft})
                        <form action="#" class="pull-right mail-src-position">
                            <div class="input-append">
                                <input type="text" class="form-control " placeholder="Search Mail">
                            </div>
                        </form>
                       </h4>
                    </header>
                    <div style="color:blue;">${msg}</div>
                    <div class="panel-body minimal">
                        <div class="mail-option">
                            <div class="chk-all">
                                <div class="pull-left mail-checkbox ">
                                    <input type="checkbox" class="">
                                </div>

                                <div class="btn-group">
                                    <a data-toggle="dropdown" href="#" class="btn mini all">
                                        All
                                        <i class="fa fa-angle-down "></i>
                                    </a>
                                    <ul class="dropdown-menu">
                                        <li><a href="#"> None</a></li>
                                        <li><a href="#"> Read</a></li>
                                        <li><a href="#"> Unread</a></li>
                                    </ul>
                                </div>
                            </div>

                            <div class="btn-group">
                                <a data-original-title="Refresh" data-placement="top" data-toggle="dropdown" href="#" class="btn mini tooltips">
                                    <i class=" fa fa-refresh"></i>
                                </a>
                            </div>
                            <div class="btn-group hidden-phone">
                                <a data-toggle="dropdown" href="#" class="btn mini blue">
                                    More
                                    <i class="fa fa-angle-down "></i>
                                </a>
                                <ul class="dropdown-menu">
                                    <li><a href="#"><i class="fa fa-pencil"></i> Mark as Read</a></li>
                                    <li><a href="#"><i class="fa fa-ban"></i> Spam</a></li>
                                    <li class="divider"></li>
                                    <li><a href="#"><i class="fa fa-trash-o"></i> Delete</a></li>
                                </ul>
                            </div>
                            <div class="btn-group">
                                <a data-toggle="dropdown" href="#" class="btn mini blue">
                                    Move to
                                    <i class="fa fa-angle-down "></i>
                                </a>
                                <ul class="dropdown-menu">
                                    <li><a href="#"><i class="fa fa-pencil"></i> Mark as Read</a></li>
                                    <li><a href="#"><i class="fa fa-ban"></i> Spam</a></li>
                                    <li class="divider"></li>
                                    <li><a href="#"><i class="fa fa-trash-o"></i> Delete</a></li>
                                </ul>
                            </div>

                            <ul class="unstyled inbox-pagination">
                                <li><span>${start }-${end} of ${numberMsg}</span></li>
                                <%
                                	int currentPage = (int) request.getAttribute("currentPage");
                                	int sumPage =(int) request.getAttribute("sumPage");
                                	if(currentPage-1>0){
                                %>
                                
                                <li>
                                    <a class="np-btn" href="<%=request.getContextPath()%>/inbox?page=<%=currentPage-1%>"><i class="fa fa-angle-left  pagination-left"></i></a>
                                </li>
                                <%}else{ %>
                                <li>
                                    <a class="np-btn" href="#"><i class="fa fa-angle-left  pagination-left"></i></a>
                                </li>
                                <%} 
                                	if(sumPage>=currentPage){
                                
                                %>
                                
                                
                                <li>
                                    <a class="np-btn" href="<%=request.getContextPath()%>/inbox?page=<%=currentPage+1%>"><i class="fa fa-angle-right pagination-right"></i></a>
                                </li>
                                
                                <%}else{ %>
                                <li>
                                    <a class="np-btn" href="#"><i class="fa fa-angle-right pagination-right"></i></a>
                                </li>
                                <%} %>
                            </ul>
                        </div>
                        <div class="table-inbox-wrap ">
                            <table class="table table-inbox table-hover">
                        <tbody>
                        <%  
                        	SimpleDateFormat df= new SimpleDateFormat("HH:mm dd-MM-yyyy");
                        	ArrayList<MessageView> alMsg = (ArrayList<MessageView>)request.getAttribute("alMsg");
                        	for(MessageView msg: alMsg){
						%>
			          					<tr>
											<a href="<%=request.getContextPath()%>/compose?id=<%=msg.getId()%>">			                            
											<td class="view-message  dont-show"  style="color:red; width:20%;"><a href="<%=request.getContextPath()%>/compose?id=<%=msg.getId()%>">Draft</a></td>
				                            <td class="view-message " style="width:40%;"><a href="<%=request.getContextPath()%>/compose?id=<%=msg.getId()%>"><%=msg.getSubject()%></a></td>
				                            <td class="view-message  text-right"><%=df.format(msg.getDate())%></td>
				                            </a>	
				                        </tr>
	                    <%
							}										
						%>
                       
                        </tbody>
                        </table>

                        </div>
                    </div>
                </section>
            </div>
        </div>

        <!-- page end-->
        </div>
</section>
<%-- <%@include file="inc/footer.jsp"%>
 --%>
