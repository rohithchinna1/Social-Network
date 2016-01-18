<%@page import="datamodels.ReportModel"%>
<%@page import="datamodels.FriendModel"%>
<%@page import="com.sun.org.apache.bcel.internal.generic.FMUL"%>
<%@page import="beans.Friend"%>
<%@page import="datamodels.LikeModel"%>
<%@page import="helpers.DateHelper"%>
<%@page import="beans.Image"%>
<%@page import="beans.Comment"%>
<%@page import="datamodels.CommentModel"%>
<%@page import="datamodels.ImageModel"%>
<%@page import="beans.User"%>
<%@page import="datamodels.UserModel"%>
<%@page import="beans.Post"%>
<%@page import="java.util.ArrayList"%>
<%@page import="datamodels.PostModel"%>
<%@page import="helpers.UserSession"%>
<jsp:include page="Template/header.jsp" />
<%
PostModel pm = new PostModel();
UserModel um = new UserModel();
ImageModel im = new ImageModel();
LikeModel lm = new LikeModel();
ReportModel rm = new ReportModel();
%>
<div style="margin-top: 70px;"></div>
<div
	class="col-md-8 col-sm-12 col-xs-12 col-md-offset-2 animated fadeIn">
	<!-- user options -->
	<div class="col-md-2 col-sm-3 col-xs-12 left-content hidden-xs">
		<div class="left-user-options">
			<div>
				<img class="img-user img-circle  img-thumbnail img-responsive"  style="background-image: url('img/Profile/<%=UserSession.getProfileImage(session)%>'),url('img/Profile/default-avatar.png'); "/>
	
			</div>
				<h4 style="text-align: center; font-weight: bolder;"><%=UserSession.getFirstname(session)%></h4>
			<br>
			<div class="list-group">
				<a href="Timeline" class="list-group-item"> <i
					class="fa fa-bars"></i> Timeline
				</a>
				<a href="About" class="list-group-item"> <i
					class="fa fa-edit"></i> Edit Profile
				</a>
				 <a href="Photos" class="list-group-item"> <i
					class="fa fa-image"></i> Photos
				</a> <a href="Friends" class="list-group-item"> <i
					class="fa fa-users"></i> Friends
				</a> 
				<!-- <a href="Messages" class="list-group-item"> <i
					class="fa fa-comment"></i> Messages
				</a> -->
			</div>
		</div>
	</div>
	<!-- en user options -->

	<!-- middle container -->
	<div class="col-md-7 col-sm-7 col-xs-12">
		<!-- update status -->
		<div class="col-md-12">
			<div class="well">
				<form class="form-horizontal" role="form" action="SubmitPost" method="post" enctype="multipart/form-data">
					<h4>What's New</h4>
					<div class="form-group" style="padding: 14px;">
						<textarea class="form-control" placeholder="Update your status" name="post" required="required"></textarea>
						<img id="wizard-post-photo" class="img-thumbnail img-responsive" style="width:100%"></img>
						<iframe  class="img-thumbnail img-responsive" style="width:100%; height:350px; margin-bottom:5px;display:none;" id="save-YouTube-iframe" frameborder="0" allowfullscreen></iframe>
					</div>
					<input type="file" id="wizard-post-photo-file" name="postimage" style="display:none;"/>
					<input type="hidden" id="save-YouTube-hidden" name="postyoutube"/>
					<input type="hidden" id="share-type" name="type" value="1"/>
					<button class="btn btn-primary pull-right" type="submit">Post</button>
					<ul class="list-inline">
						<li><a href="#" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-facetime-video"></i></a></li>
						<li><a href="#" onclick="document.getElementById('wizard-post-photo-file').click();"><i class="glyphicon glyphicon-camera"></i></a></li>
					</ul>
					<input type="hidden" name="redirect" value="Home"></input>
				</form>
			</div>			
		</div>
		<!-- end update status -->

<%
	ArrayList<Post> posts = pm.GetFrndsPosts(UserSession.getUserid(session));
	for(Post post : posts){
		User user = um.getUser(post.getFromuserid());
%>


		<!-- first post -->
				<div class="col-md-12">
					<div class="panel panel-white post panel-shadow">
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
													out.print("posted an image.");
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

							<div class="dropdown pull-right">
								<a id="dLabel" data-target="#" href="#" data-toggle="dropdown"
									role="button" aria-haspopup="true" aria-expanded="false"><span
									class="caret"></span> </a>
								<form>								
								<input type="hidden" value="<%=post.getPostid()%>" name="postid">
								<input type="hidden" name="redirect" value="Home"></input>
								<ul class="dropdown-menu plainborder" aria-labelledby="dLabel" style="width: 80px;">
								<% if(UserSession.getUserid(session).equals(post.getFromuserid())||UserSession.getUserid(session).equals(post.getTouserid())) {%>
									<li><button type="submit" formaction="DeletePost" class="btn_delete_report" formmethod="post"><i class="fa fa-trash"></i> Delete</button></li>
								<%} 
									String disabled = "";
									if(rm.isReported(UserSession.getUserid(session), post.getPostid()))
										disabled="disabled";
								%>
									<li><button type="submit" formaction="ReportPost" class="btn_delete_report" formmethod="post" <%=disabled%>><i class="fa fa-exclamation-circle"></i> Report</button></li>								
								</ul>
								</form>
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
							<div class="stats">
								<form action="LikeDislikePost" method="post" role="form">
									<input type="hidden" value="<%=post.getPostid()%>" name="postid">
									<%
									ArrayList<User> likeusers =  lm.getLikes(post.getPostid()); 
									ArrayList<User> dislikeusers =  lm.getDisLikes(post.getPostid()); 
									String likesHtml ="",dislikesHtml="";
									for(User likeuser : likeusers){
										likesHtml += likeuser.getFirstname()+"<br>";
									}
									for(User dislikeuser : dislikeusers){
										dislikesHtml += dislikeuser.getFirstname()+"<br>";
									}
									%>
									<button type="button" class="btn btn-default stat-item tip"
										name="likedislike" value="like" data-html="true" rel="tooltip" data-placement="right" data-original-title='<%=likesHtml%>'
										onclick="dolike('<%=post.getPostid()%>','like')"
										id="like_<%=post.getPostid()%>"
										<%if(lm.isLiked(post.getPostid(), UserSession.getUserid(session))) out.print(" style='color: blue'");%>>
										<i class="fa fa-thumbs-up icon"></i>
										<%=post.getLikes()%>
									</button>
									<button type="button" class="btn btn-default stat-item tip"
										name="likedislike" value="dislike" data-html="true" rel="tooltip" data-placement="right" data-original-title='<%=dislikesHtml%>'
										onclick="dolike('<%=post.getPostid()%>','dislike')"
										id="dislike_<%=post.getPostid()%>"
										<%if(lm.isDisLiked(post.getPostid(), UserSession.getUserid(session))) out.print(" style='color: blue'");%>>
										<i class="fa fa-thumbs-down icon"></i>
										<%=post.getDislikes()%>
									</button>

									<button type="submit"
										class="btn btn-default stat-item pull-right" name="likeshare"
										value="like" formaction="SharePost" formmethod="post">
										<i class="fa fa-share icon"></i>
										<%=post.getShares()%>
									</button>
								</form>
							</div>
						</div>
						<div class="post-footer">
							<ul class="comments-list" id="comment_<%=post.getPostid()%>">
								<%
									CommentModel cm = new CommentModel();
													ArrayList<Comment> comments = cm.GetComments(post.getPostid());
													for(Comment comment : comments){
														User comment_user = um.getUser(comment.getUserid());
														Image comment_image= im.getImage(comment_user.getProfileimage());
								%>
								<li class="comment"><a class="pull-left" href="#"> <img
										class="avatar"
										style="background-image: url('img/Profile/<%=im.getImage(comment_user.getProfileimage()).getName()%>'),url('img/Profile/default-avatar.png'); background-size: cover;">
								</a>
									<div class="comment-body">
										<div class="comment-heading">
											<h4 class="comment-user-name">
												<a href="Timeline?userid=<%=comment_user.getUserid()%>"><%=comment_user.getFirstname()%>
													<%=comment_user.getLastname()%></a>
											</h4>
											<h5 class="time"><%=DateHelper.printTime(comment.getDatetime())%></h5>
										</div>
										<p style="margin-top: 3px;"><%=comment.getText()%></p>
									</div></li>
								<%
									}
								%>
							</ul>
							<form onsubmit="docomment(this); return false;">
								<div class="input-group">
									<input type="hidden" value="<%=post.getPostid()%>"
										name="postid" id="postid"> <input class="form-control"
										placeholder="Add a comment" type="text" name="comment"
										required="required"> <span class="input-group-addon"
										style="padding: 0;">
										<button type="submit"
											style="width: 100%; height: 100%; padding-left: 5px; padding-right: 10px;">
											<i class="fa fa-edit"></i> Comment
										</button>
									</span>
								</div>
							</form>
						</div>
					</div>
				</div>
				<!-- end first post -->
<% } %>
	</div>
	<!-- end middle container -->
	<div class="col-md-3 col-sm-3 col-xs-12 right-content hidden-xs">
		<%
			ArrayList<User> nonfrnds = um.getNonfriends(UserSession.getUserid(session));
			if(nonfrnds.size()!=0){
		%>
		<div class="col-md-12 col-sm-12 frind-suggest">

			<div class="row row-suggest-title text-center"
				style="margin-top: 10px;">
				<i class="fa fa-users"></i>&nbsp;People you may know
			</div>
			<%
			int i=0;
			for(User nfusr : nonfrnds) {
			if(i==5)
			break;
			i++;
			%>
			<div class="row row-suggest">
				<div class="block-update-card" style="padding: 10px">
					<div class="col-md-6 col-sm-4 col-xs-4">
						<img 
						style="background-image: url('img/Profile/<%=im.getImage(nfusr.getProfileimage()).getName()%>'),url('img/Profile/default-avatar.png'); background-size: cover;"
							class="img-responsive  img-circle  img-suggest">
					</div>
					<div class="col-md-6 col-sm-8 col-xs-8">
						<a href="#" class="suggest-name"><%=nfusr.getFirstname()%> <%=nfusr.getLastname()%></a> <a
							href="Timeline?userid=<%=nfusr.getUserid()%>" class="btn btn-default btn-responsive" style="bottom: 0;">
							<i class="fa fa-plus"></i> Add Frind
						</a>
					</div>
				</div>
			</div>
			<%} %>		
		</div>
		<%} %>
		<%
		FriendModel fm = new FriendModel();
		ArrayList<Friend> prFrnds =  fm.getPendingRequests(UserSession.getUserid(session));
		if(prFrnds.size()!=0){
		%>
		<div class="col-md-12 col-sm-12 frind-suggest">

			<div class="row row-suggest-title text-center"
				style="margin-top: 10px;">
				<i class="fa fa-users"></i>&nbsp;Pending Friend Requests
			</div>
			<%
			for(Friend prFrnd : prFrnds) {				
			User prUser = um.getUser(prFrnd.getUserid());
			%>
			<div class="row row-suggest">
				<div class="block-update-card" style="padding: 10px">
					<div class="col-md-6 col-sm-4 col-xs-4">
						<img 
						style="background-image: url('img/Profile/<%=im.getImage(prUser.getProfileimage()).getName()%>'),url('img/Profile/default-avatar.png'); background-size: cover;"
							class="img-responsive  img-circle  img-suggest">
					</div>
					<div class="col-md-6 col-sm-8 col-xs-8">
						<a href="#" class="suggest-name"><%=prUser.getFirstname()%> <%=prUser.getLastname()%></a> <a
							href="Timeline?userid=<%=prUser.getUserid()%>" class="btn btn-success btn-responsive" style="bottom: 0;">
							<i class="fa fa-tick"></i> Accept Request
						</a>
					</div>
				</div>
			</div>
			<%} %>		
		</div>
		<%} %>
	</div>
</div>
<div class="modal fade " id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog " role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel" style="font-size: 16px">Copy Paste Youtube link below</h4>
      </div>
      <div class="modal-body">
      <div class="input-group">
        <input type="text" class="form-control" id="save-YouTube-text">
        <span class="input-group-btn"><button type="button" id="save-YouTube-btn" class="btn btn-primary">Save</button>
        </span>
        </div>
      </div>
    </div>
  </div>
</div>
<jsp:include page="Template/footer.jsp" />