<%@page import="beans.Friend"%>
<%@page import="datamodels.FriendModel"%>
<%@page import="beans.Education"%>
<%@page import="datamodels.EducationModel"%>
<%@page import="beans.Work"%>
<%@page import="datamodels.WorkModel"%>
<%@page import="beans.Contact"%>
<%@page import="datamodels.ContactModel"%>
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
	//String UserId = UserSession.getUserid(session); 
//UserModel um = new UserModel();
//User loginuser = um.getUser(UserId);

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
	ImageModel im = new ImageModel();

	ContactModel cm = new ContactModel();
	Contact contact = cm.getContact(UserId);

	WorkModel wm = new WorkModel();
	ArrayList<Work> works = wm.getWorks(UserId);

	EducationModel em = new EducationModel();
	ArrayList<Education> edus = em.getEdus(UserId);
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
								<li><a href="Timeline?userid=<%=tuser.getUserid()%>"><i class="fa fa-tasks"></i>Timeline</a></li>
								<li><a href="About?userid=<%=tuser.getUserid()%>"><i class="fa fa-info-circle"></i>About</a></li>
								<li class="active"><a href="#"><i class="fa fa-users"></i>Friends</a></li>
								<li><a href="Photos?userid=<%=tuser.getUserid()%>"><i class="fa fa-file-image-o"></i>Photos</a></li>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- user detail -->
	<%
	ArrayList<Friend> friends = fm.getFriends(UserId);
	%>
      <div class="row"> 
        <div class="col-md-12 user-detail">
          <!-- content -->
            <div class="col-md-12 col-sm-12 col-xs-12">
              <div class="panel panel-default">
                <div class="panel-heading">
                  <h3 class="panel-title">
                    <i class="fa fa-users"></i>&nbsp; Friends
                  </h3>
                </div>
                <div class="panel-body">
                <% for(Friend friend : friends){ 
                	String fuserid = friend.getFriendid();
                	if(fuserid.equals(UserId))
                		fuserid = friend.getUserid();
                	User fuser = um.getUser(fuserid);
                %>
                  <div class="col-md-4 cols-sm-6 col-xs-6">
                    <div class="media block-update-card">
                      <a class="pull-left" href="#">
                        <img class="media-object update-card-MDimentions"
                        	style="background-image: url('img/Profile/<%=im.getImage(fuser.getProfileimage()).getName()%>'),url('img/Profile/default-avatar.png'); background-size: cover;">
                      </a>
                      <div class="media-body update-card-body">
                        <h4 class="media-heading"><%=fuser.getFirstname()%> <%=fuser.getLastname()%></h4>
                        <div class="btn-toolbar card-body-social" role="toolbar">
                          <a class="btn btn-default" href="Timeline?userid=<%=fuserid%>"><i class="fa fa-user"></i> Visit Profile</a>
                        </div>
                      </div>
                    </div> 
                  </div>
                  <%} %>                  
                </div>
              </div>
            </div>
        </div>
      </div><!-- end user details -->
</div>
<jsp:include page="Template/footer.jsp" />