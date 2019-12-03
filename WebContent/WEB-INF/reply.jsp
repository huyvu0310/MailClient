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
                            <li><a href="<%=request.getContextPath()%>/draft"> <i class="fa fa-file-text-o"></i> Drafts 
                            	<%
                            		int numDrafts = (int) session.getAttribute("numDrafts");
                            		if (numDrafts > 0) {
                            	%>
                            	<span class="label label-info pull-right inbox-notification"><%=numDrafts %></span>
                            	<%} %>
                            </a></li>
                        </ul>
                    </div>
                </section>

              
            </div>
          
           <div class="col-sm-9 mail-w3agile">
                <section class="panel">
                    <header class="panel-heading wht-bg">
                       <h4 class="gen-case"> Reply Mail
                           <form action="#" class="pull-right mail-src-position">
                           
                        </form>
                       </h4>
                    </header>
                    <div class="panel-body">
                    	<div style="color:red;">${msgErr}</div>
                     
                        <div class="compose-mail">
                            <form role="form-horizontal" method="post" action="<%=request.getContextPath()%>/reply">
                                <% if (request.getAttribute("msgSend")!=null) {
									MessageView m = (MessageView)request.getAttribute("msgSend");                                
                                %>
                                <div class="form-group">
                                    <label for="to" class="">To: </label>
                                    <input type="text" readonly="true" tabindex="1" id="to" class="form-control" name="to" <%if(!"".equals(m.getFrom())){
                                    	out.print("value='"+m.getFrom()+"'");
                                    } %>>

                                    <div class="compose-options">
                                        <a onclick="$(this).hide(); $('#cc').parent().removeClass('hidden'); $('#cc').focus();" href="javascript:;">Cc</a>
                                        <a onclick="$(this).hide(); $('#bcc').parent().removeClass('hidden'); $('#bcc').focus();" href="javascript:;">Bcc</a>
                                    </div>
                                </div>

                                <div class="form-group hidden">
                                    <label for="cc" class="">Cc:</label>
                                    <input type="text" tabindex="2" id="cc" class="form-control" name="cc">
                                </div>

                                 <div class="form-group hidden">
                                    <label for="bcc" class="">Bcc:</label>
                                    <input type="text" tabindex="2" id="bcc" class="form-control" name="bcc">
                                </div>

                                <div class="form-group">
                                    <label for="subject" class="">Subject: </label>
                                    <input type="text" readonly="true" tabindex="1" id="subject" class="form-control" name="subject"<%if(!"".equals(m.getSubject())){
                                    	out.print("value='"+m.getSubject()+"'");
                                    } %>>
                                </div>

                                 <div class="compose-editor">
                                    <textarea class="wysihtml5 form-control" rows="9" name="content"></textarea>
                                </div>
                                <%}%> 
                                <div class="compose-btn">
                                    <button class="btn btn-primary btn-sm"><i class="fa fa-check"></i> Reply</button>
                                </div>

                            </form>
                        </div>
                    </div>
                </section>
            </div>
            

        </div>

        <!-- page end-->
        </div>
</section>

<%-- <%@include file="inc/footer.jsp"%> --%>

