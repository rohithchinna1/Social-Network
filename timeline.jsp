<%@page import="beans.Friend"%>
<%@page import="datamodels.FriendModel"%>
<%@page import="datamodels.LikeModel"%>
<%@page import="datamodels.ImageModel"%>
<%@page import="beans.Post"%>
<%@page import="datamodels.PostModel"%>
<%@page import="beans.Image"%>
<%@page import="beans.Comment"%>
<%@page import="java.util.ArrayList"%>
<%@page import="datamodels.CommentModel"%>
<%@page import="helpers.DateHelper"%>
<%@page import="beans.User"%>
<%@page import="datamodels.UserModel"%>
<%@page import="helpers.UserSession"%>
<jsp:include page="Template/header.jsp" />
<%
	String UserId = UserSession.getUserid(session);
	UserModel um = new UserModel();
	User tuser = um.getUser(UserId);
	boolean visit = false;
	boolean self = true;
	if (request.getParameter("userid") != null) {
		String TempId = request.getParameter("userid");
		if (!TempId.equals(UserSession.getUserid(session))) {
	User Tempuser = um.getUser(TempId);
	if (Tempuser != null) {
		tuser = Tempuser;
		UserId = TempId;
		visit = true;
		self = false;
	}
		}
	}
	ImageModel im =new ImageModel();
	FriendModel fm = new FriendModel();
%>
<div
	class="timeline-container col-md-8 col-sm-12 col-xs-12 col-md-offset-2">
	<div class="row">
		<%
			if(self){
		%>
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="cover-photo" id="wizard-cover-photo"
				style="background-image: url('img/Cover/<%=im.getImage(tuser.getCoverimage()).getName()%>'),url('img/Cover/default.jpg');">
				<form method="post" action="SaveCoverPhoto"
					enctype="multipart/form-data" class="pull-right ">
					<input type="submit" class="cover-upload-div btn btn-default" name="cover"
						id="wizard-cover-photo-btn-save" style="width: 100px" value="Save" />
					<button class="cover-upload-div btn btn-default"
						id="wizard-cover-photo-btn">
						<span id="wizard-cover-photo-text">Update Cover Photo</span> <input
							type="file" id="wizard-cover-photo-file" name="profilepic"
							required="required" class="cover-upload " />
					</button>
				</form>

				<form method="post" action="SaveCoverPhoto"
					enctype="multipart/form-data" class="pull-left">
					<button class="cover-upload-div btn btn-default" style="margin-left:10px;"
						id="wizard-profile-photo-btn">
						<span id="wizard-profile-photo-text">Update Profile Photo<input type="file"
							id="wizard-profile-photo-file" name="displaypic"
							required="required" class="cover-upload " />
					</button>
					<input type="submit" class="cover-upload-div btn btn-default" name="profile"
						id="wizard-profile-photo-btn-save" style="width: 100px" value="Save" />					
				</form>
				<div id="wizard-profile-photo"
					style="background-image: url('img/Profile/<%=im.getImage(tuser.getProfileimage()).getName()%>'),url('img/Profile/default-avatar.png'); background-size: cover;"
					class="profile-photo img-thumbnail">
				</div>
				<div class="cover-name"><%=tuser.getFirstname()%>
					<%=tuser.getLastname()%></div>
			</div>
		</div>
		<%
			} else{
		%>
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="col-md-8 col-sm-8 hidden-xs cover-photo"
				style="background-image: url('img/Cover/<%=im.getImage(tuser.getCoverimage()).getName()%>'),url('img/Cover/default.jpg'); margin-top: 0 !important;" ></div>
			<div class="col-md-4 col-sm-4 col-xs-12 text-center profile-photo-container">
				<img
					style="background-image: url('img/Profile/<%=im.getImage(tuser.getProfileimage()).getName()%>'),url('img/Profile/default-avatar.png'); background-size: cover;"
					class="profile-photo-visit img-circle show-in-modal" />
				<h4 class="text-white text-center name"><%=tuser.getFirstname()%>
					<%=tuser.getLastname()%></h4>
				<h5 class="text-white text-center ocupation"><%=tuser.getGender()%>, Age <%=DateHelper.printAge(tuser.getDob()) %></h5>
				<hr class="name-separator">
				<div class="text-center">
				<% Friend frndrel = fm.getRelation(UserSession.getUserid(session), tuser.getUserid()); %>
					<form action="FollowFriend" method="post">					
						<input type="hidden" name="friendid" value="<%=tuser.getUserid()%>"/>
						<%if(frndrel==null) {%>
						<input type="hidden" name="status" value="follow"/>
						<button role="button" class="btn btn-default" type="submit">
							<span>follow me</span>
						</button>
						<%} else if(frndrel.isAccept()){ %>
						<input type="hidden" name="status" value="unfollow"/>
						<button role="button" class="btn btn-warning" type="submit">
							<span>UnFollow</span>
						</button>
						<%}else if(frndrel.getUserid().equals(UserSession.getUserid(session))){ %>
						<input type="hidden" name="status" value="cancel"/>
						<button role="button" class="btn btn-danger" type="submit">
							<span>Cancel Request</span>
						</button>
						<%}else if(frndrel.getFriendid().equals(UserSession.getUserid(session))){ %>
						<input type="hidden" name="status" value="accept"/>
						<button role="button" class="btn btn-success" type="submit">
							<span>Accept Request</span>
						</button>
						<%} %>
					</form>
				</div>
			</div>
		</div>
		
		<%
			}
		%>
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="panel-options">
				<div class="navbar navbar-default navbar-cover">
					<div class="container-fluid">
						<div class="navbar-header">
							<button type="button" class="navbar-toggle collapsed"
								data-toggle="collapse" data-target="#profile-opts-navbar">
								<span class="sr-only">Toggle navigation</span> <span
									class="icon-bar"></span> <span class="icon-bar"></span> <span
									class="icon-bar"></span>
							</button>
						</div>
						<div class="collapse navbar-collapse" id="profile-opts-navbar">
							<ul class="nav navbar-nav navbar-right">
								<li class="active"><a href="#"><i class="fa fa-tasks"></i>Timeline</a></li>
								<li><a href="About?userid=<%=tuser.getUserid()%>"><i class="fa fa-info-circle"></i>About</a></li>
								<li><a href="Friends?userid=<%=tuser.getUserid()%>"><i class="fa fa-users"></i>Friends</a></li>
								<li><a href="Photos?userid=<%=tuser.getUserid()%>"><i class="fa fa-file-image-o"></i>Photos</a></li>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-12 user-detail">
			<div class="col-md-5 col-sm-5 col-xs-12 col-detail">
				<%ArrayList<Friend> frnds= fm.getFriends(UserId); %>
				<div class="col-md-12">
					<div class="panel panel-default panel-user-detail">
						<div class="panel-body">
							<ul class="list-unstyled">
								<li><i class="fa fa-calendar"></i>Born on <%=DateHelper.printDob(tuser.getDob())%></li>
								<li><i class="fa fa-rss"></i>Followed by <a href="#"><%=frnds.size()%>
										People</a></li>
							</ul>
						</div>
						<div class="panel-footer text-center">
							<a href="About?userid=<%=tuser.getUserid()%>" class="btn btn-success">Read more</a>
						</div>
					</div>
				</div>
				<div class="col-md-12">
					<div class="panel panel-default panel-friends">
						<div class="panel-heading">
							<a href="Friends?userid=<%=UserId%>" class="pull-right">View all&nbsp;<i
								class="fa fa-share-square-o"></i></a>
							<h3 class="panel-title">
								<i class="fa fa-users"></i>&nbsp; Friends
							</h3>
						</div>
						<div class="panel-body text-center">
							<ul class="friends">
							<%int i=0;
							for(Friend frnd : frnds) {
								if(i==3)
									break;
								i++;
								String fid = frnd.getFriendid();
								if(fid.equals(UserId))
									fid=frnd.getUserid();
								User fusr = um.getUser(fid);
							%>
								<li><a href="Timeline?userid=<%=fusr.getUserid()%>"> <img src="img/Profile/<%=im.getImage(fusr.getProfileimage()).getName()%>"
										title="<%=fusr.getFirstname()%> <%=fusr.getLastname()%>" class="img-responsive tip">
								</a></li>
								<%} %>
							</ul>
						</div>
					</div>
				</div>
				<div class="col-md-12">
					<div class="panel panel-default">
						<div class="panel-heading">
							<a href="Photos?userid=<%=UserId%>" class="pull-right">View all&nbsp;<i
								class="fa fa-share-square-o"></i></a>
							<h3 class="panel-title">
								<i class="fa fa-image"></i>&nbsp;Photos
							</h3>
						</div>
						<div class="panel-body text-center">
							<ul class="photos">
							<%
							ArrayList<Image> imgs = im.getUserImages(UserId);
							i=0;
							for(Image img :imgs){ 
								if(i==3)
									break;
								i++;
							%>
								<li><a> <img src="img/<%=img.getLocation()%>/<%=img.getName()%>"
										class="img-responsive show-in-modal">
								</a></li>
								<%} %>
							</ul>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-7 col-sm-7 col-xs-12 col-posts">
				<!-- update status -->
				<div class="col-md-12">
					<div class="well">
						<form class="form-horizontal" role="form" action="SubmitPost"  enctype="multipart/form-data"
							method="post">
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
							<%
								if(self) {
							%>
							<input type="hidden" name="redirect" value="Timeline"></input>
							<%
								} else{
							%>
							<input type="hidden" name="redirect"
								value="Timeline?userid=<%=tuser.getUserid()%>"></input> <input
								type="hidden" name="touserid" value="<%=tuser.getUserid()%>"></input>
							<%
								}
							%>
						</form>
					</div>
				</div>
				
				<!-- end update status -->

				<%
					PostModel pm = new PostModel();
					ArrayList<Post> posts = pm.GetPosts(UserId);
					for(Post post : posts){
						User user = um.getUser(post.getFromuserid());
						LikeModel lm = new LikeModel();
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
									<%
										if (self) {
									%>
									<input type="hidden" name="redirect" value="Timeline"></input>
									<%
										} else {
									%>
									<input type="hidden" name="redirect"
										value="Timeline?userid=<%=tuser.getUserid()%>"></input>
									<%
										}
									%>
									<ul class="dropdown-menu plainborder" aria-labelledby="dLabel" style="width: 80px;">
									<li><button type="submit" formaction="DeletePost" class="btn_delete_report" formmethod="post"><i class="fa fa-trash"></i> Delete</button></li>
									<li><button type="submit" formaction="ReportPost" class="btn_delete_report" formmethod="post"><i class="fa fa-exclamation-circle"></i> Report</button></li>								
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
				<%
					}
				%>
			</div>
		</div>
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
