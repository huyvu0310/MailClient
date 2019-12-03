<%@page import="java.io.Console"%>
<%@page import="javax.mail.BodyPart"%>
<%@page import="javax.mail.Multipart"%>
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
							<a href="<%=request.getContextPath()%>/compose"
								class="btn btn-compose"> Compose Mail </a>
							<ul class="nav nav-pills nav-stacked mail-nav">
								<li><a
									href="<%=request.getContextPath()%>/inbox"> <i
										class="fa fa-inbox"></i> Inbox 
										<%
										 	int numUnreadMsg = (int) session.getAttribute("numberUnreadMsg");
										 	if (numUnreadMsg > 0) {
										 %> <span
										class="label label-danger pull-right inbox-notification"><%=numUnreadMsg%></span>
										<%
											}
										%>
								</a></li>
								<li><a href="<%=request.getContextPath()%>/sent"> <i
										class="fa fa-envelope-o"></i> Send Mail
								</a></li>
								<li><a href="<%=request.getContextPath()%>/draft"> <i
										class="fa fa-file-text-o"></i> Drafts 
								<%
 									int numDrafts = (int) session.getAttribute("numDrafts");
 									if (numDrafts > 0) {
 								%>
 									<span
										class="label label-info pull-right inbox-notification"><%=numDrafts%>
										</span>
								<%
									}
								%>
								</a></li>
							</ul>
						</div>
					</section>


				</div>
				<%
					MessageView msg = (MessageView) request.getAttribute("msgRead");
					if (msg != null) {
				%>
				<div class="col-sm-9 mail-w3agile">
					<section class="panel">
						<div class="panel-body">
							<div style="color: red;">${msgErr}</div>
							<div class="compose-mail">
									<div class="form-group">
										<label style="width: 100%" for="to" class="">From: <%=msg.getFrom()%></label>
									</div>

									<div class="form-group">
										<label style="width: 100%" for="to" class="">To: <%=msg.getTo()%><%=msg.getCc()%>
											<%
												if (!"".equals(msg.getBcc()))
														out.print("bcc: " + msg.getBcc());
											%></label>
									</div>

									<div class="form-group">
										<label style="width: 100%" for="subject" class="">Subject:
											<%=msg.getSubject()%></label>
									</div>

									<div class="compose-editor">
										<%=msg.getContent()%>
									</div>
									
									<div>
										<% ArrayList<String> listFile = (ArrayList<String>) request.getAttribute("attachFile");
										for(String file:listFile){
											%> 
											<div class="attach-file">
											<div class="type"></div>
												<div class = "filename"><%=file%></div>
											</div>
										<%} 
										%>
									</div>
									<%
										boolean sentMail = (boolean) request.getAttribute("isSentMail");
										if(!sentMail){
											%>
											<div class="compose-btn">
                                    			<a href="<%=request.getContextPath() %>/reply?id=<%=msg.getId()%>" class="btn btn-primary btn-sm"><i class="fa fa-check"></i> Reply</a>
                                			</div>
										<%
										}
										
									%>
									
							</div>
						</div>
					</section>
				</div>
				<%
					}
				%>
			</div>

			<!-- page end-->
		</div>
	</section>
	<%-- <%@include file="inc/footer.jsp"%> --%>
	
	
	