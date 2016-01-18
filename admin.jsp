<%@page import="helpers.DateHelper"%>
<%@page import="datamodels.LikeModel"%>
<%@page import="beans.User"%>
<%@page import="datamodels.ImageModel"%>
<%@page import="datamodels.UserModel"%>
<%@page import="java.util.Map"%>
<%@page import="beans.Post"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="datamodels.ReportModel"%>
<%@page import="com.mongodb.BasicDBObject"%>
<%@page import="com.mongodb.DBCursor"%>
<%@page import="datamodels.Model"%>
<%
	request.setAttribute("title", "Small World - Admin");
	request.setAttribute("hidenav", "true");
%>
<jsp:include page="Template/header.jsp" />

<div class="image-container">
	<div class="container">
		<div class="row "><!-- animated fadeIn -->
			<div class="col-md-4">
				<!-- Wizard container -->
				<div class="wizard-container">
					<div class="card wizard-card ct-wizard-orange" id="wizard"
						style="min-height: 0;">
						<!-- <div class="wizard-header text-center">
							<h1 class="app-title">Small World</h1>
						</div> -->
						<h3 class="info-text">User Activity</h3>
						<br>
						<div class="row">
							<div class="col-sm-10 col-sm-offset-1">
								<div class="form-group">
									<%
										Model m = Model.createInstance();
										DBCursor statuser = m.getCollection("User").find();
										
										BasicDBObject bdpost = new BasicDBObject();
										bdpost.put("parentpostid", null);
										DBCursor statposts = m.getCollection("Post").find(bdpost);
										
										DBCursor statphotos = m.getCollection("Image").find();
										BasicDBObject bd = new BasicDBObject();
										bd.put("like", true);
										DBCursor statlikes = m.getCollection("Like").find(bd);
										BasicDBObject bd1 = new BasicDBObject();
										bd1.put("like", false);
										DBCursor statdislikes = m.getCollection("Like").find(bd1);
										DBCursor statcomment = m.getCollection("Comment").find();
										
										BasicDBObject bdshares = new BasicDBObject();
										bdshares.put("parentpostid", new BasicDBObject("$ne", null));
										DBCursor statshare = m.getCollection("Post").find(bdshares);
									%>
									<table
										class="table table-striped  table-hover table-condensed">
										<tr>
											<td><h4 text-align="left">Total Users:</h4></td>
											<td><h4><%=statuser.count()%></h4></td>
										</tr>
										<tr>
											<td><h4 text-align="left">Total Photos:</h4></td>
											<td><h4><%=statphotos.count()%></h4></td>
										</tr>
										<tr>
											<td><h4 text-align="left">Total Posts:</h4></td>
											<td><h4><%=statposts.count()%></h4></td>
										</tr>
										<tr>
											<td><h4 text-align="left">Total Likes:</h4></td>
											<td><h4><%=statlikes.count()%></h4></td>
										</tr>
										<tr>
											<td><h4 text-align="left">Total Dislikes:</h4></td>
											<td><h4><%=statdislikes.count()%></h4></td>
										</tr>
										<tr>
											<td><h4 text-align="left">Total Comments:</h4></td>
											<td><h4><%=statcomment.count()%></h4></td>
										</tr>
										<tr>
											<td><h4 text-align="left">Total Shares:</h4></td>
											<td><h4><%=statshare.count()%></h4></td>
										</tr>
									</table>
								</div>
								<br> <br>
							</div>
							
						</div>
					</div>
				</div>
			</div>
			
			<div class="col-md-8">
				<!-- Wizard container -->
				<div class="wizard-container">
					<div class="card wizard-card ct-wizard-orange" id="wizard"
						style="min-height: 0;">
						<div class="col-sm-10 col-sm-offset-9">
								<form action="Admin" method="post">
								<h4>
									<button type="submit" class="nav-controller" name="logout"><i
										class="fa fa-sign-out"></i> Logout</button>
								</h4>
								</form>
							</div>
							
						<h3 class="info-text">Reported Posts</h3>
						<%
							ReportModel rm = new ReportModel();
							UserModel um = new UserModel();
							ImageModel im =new ImageModel();
							//LikeModel lm =new LikeModel();
							Map<Post,String> map = rm.getReport();
							if(map.size()==0){
								out.print("<h4 style='color:red'>No reports to delete</h4>");
							}else{
							
							for(Map.Entry<Post,String> entry : map.entrySet()){
								Post post = entry.getKey();
								String count = entry.getValue();
								User user = um.getUser(post.getFromuserid());
							
						%>
						<div class="col-md-12">
						
					<div class="panel panel-white post panel-shadow">
					<div class="post-description">
							<b style="">No of Reports :  <%=count%></b>
							<div class="pull-right">
								<form action="Admin" method="post" role="form">
									<input type="hidden" value="<%=post.getPostid()%>" name="postid">
									<button type="submit" class="btn btn-danger stat-item"
										name="delete"><i class="fa fa-trash"></i> Delete
									</button>
								</form>
							</div>
							</div>
						<div class="post-heading">
							<div class="pull-left image">
								<img
									style="background-image: url('img/Profile/<%=im.getImage(user.getProfileimage()).getName()%>'),url('img/Profile/default-avatar.png'); background-size: cover;"
									class="img-rounded avatar">
							</div>
							<div class="pull-left meta">
								<div class="title h5">
									<a href="Timeline?userid=<%=user.getUserid()%>"
										class="post-user-name"><%=user.getFirstname()%> <%=user.getLastname()%></a>
									<%
										if(post.getParentpostid()!=null){
											out.print(" shared ");
											User ppostUser = um.getUser(post.getParentpostid());%>
										<a href="Timeline?userid=<%=ppostUser.getUserid()%>"
										class="post-user-name"><%=ppostUser.getFirstname()%> <%=ppostUser.getLastname()%>'s</a>
										<%
										if(post.getType()==1)
										out.print("post.");
										if(post.getType()==2)
											out.print("image.");
										if(post.getType()==3)
											out.print("video.");
										
										}else{
										if (post.getFromuserid().equals(post.getTouserid())) {
												if(post.getType()==1)
												out.print("made a post.");
												if(post.getType()==2)
													out.print("posted a image.");
												if(post.getType()==3)
													out.print("posted a video.");
											} else {
												User touser = um.getUser(post.getTouserid());
									%>
									<i class="fa fa-play" style="color: gray"></i> <a
										href="Timeline?userid=<%=touser.getUserid()%>"
										class="post-user-name"><%=touser.getFirstname()%> <%=touser.getLastname()%></a>
									<%
										}
										}
									%>
								</div>
								<h6 class="text-muted time"><%=DateHelper.printTime(post.getDatetime())%></h6>
							</div>
						</div>
						<div class="post-description">
							<p><%=post.getText()%></p>
							<%if(post.getType()==3) {%>
							<div class="post-image" style="margin-top:5px">
								<iframe class="img-thumbnail img-responsive" style="width:100%; height:350px;" src="https://www.youtube.com/embed/<%=post.getVideo()%>" frameborder="0" allowfullscreen></iframe>
							</div>
							<%} else if(post.getType()==2) {%>
							<div class="post-image" style="margin-top:5px">
								<img class="img-thumbnail img-responsive" style="width:100%" src="img/Post/<%=im.getImage(post.getImage()).getName()%>"></img>
							</div>
							<%} %>
							
						</div>						
					</div>
				</div>
				<%} }%>
					</div>
				</div>
			</div>
			
		</div> 
	</div>
</div>
<jsp:include page="Template/footer.jsp" />