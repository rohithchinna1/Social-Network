<%@page import="datamodels.FriendModel"%>
<%@page import="beans.Friend"%>
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
								<li class="active"><a href="#"><i class="fa fa-info-circle"></i>About</a></li>
								<li><a href="Friends?userid=<%=tuser.getUserid()%>"><i class="fa fa-users"></i>Friends</a></li>
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
	
	%>
	<div class="row">
		<div class="col-md-12 user-detail">
			<!-- tabs user info -->
			<div class="col-md-12 col-sm-12 col-xs-12">
				<div class="panel panel-default panel-about">
					<div class="panel-heading">
						<h3 class="panel-title">
							<i class="fa fa-user"></i>&nbsp;About
						</h3>
					</div>
					<div class="panel-body">
						<div class="col-md-12 col-sm-12 col-xs-12 about-tab-container">
							<div class="col-lg-3 col-md-3 col-sm-3 col-xs-3 about-tab-menu">
								<div class="list-group">
									<a href="#" class="list-group-item <% if(request.getAttribute("tab").equals("1")) out.print("active");%> text-center">
										<h4 class="fa fa-child"></h4>
										<br />Overview
									</a> <a href="#" class="list-group-item <% if(request.getAttribute("tab").equals("2")) out.print("active");%> text-center">
										<h4 class="fa fa-briefcase"></h4>
										<br />Work
									</a> <a href="#" class="list-group-item <% if(request.getAttribute("tab").equals("3")) out.print("active");%> text-center">
										<h4 class="fa fa-graduation-cap"></h4>
										<br />Education
									</a> <a href="#" class="list-group-item <% if(request.getAttribute("tab").equals("4")) out.print("active");%> text-center">
										<h4 class="fa fa-newspaper-o"></h4>
										<br />Contact info
									</a>
								</div>
							</div>
							<div class="col-lg-9 col-md-9 col-sm-9 col-xs-9 about-tab">
								<!-- Overview section -->
								<div class="about-tab-content <% if(request.getAttribute("tab").equals("1")) out.print("active");%>">
									<ul class="list-group">
										<%if(works.size()!=0) {%>
										<li class="list-group-item"><i
											class="fa fa-briefcase text-primary"></i>&nbsp; Work at <%=works.get(0).getCompany()%> as <%=works.get(0).getOccupation()%></li>
										<%} if(!contact.getPhone().trim().equals("")){%>										
										<li class="list-group-item"><i
											class="fa fa-mobile text-primary"></i>&nbsp; <%=contact.getPhone() %>
										</li>
										<%} %>
										<li class="list-group-item"><i
											class="fa fa-birthday-cake text-primary"></i>&nbsp; <%=DateHelper.printDob(tuser.getDob()) %>
										</li>
										<li class="list-group-item"><i
											class="fa fa-<%=tuser.getGender().toLowerCase()%> text-primary"></i>&nbsp; <%=tuser.getGender()%>
										</li>
										<li class="list-group-item"><i
											class="fa fa-envelope text-primary"></i>&nbsp;
											<%=tuser.getEmail()%></li>
										<!-- <li class="list-group-item"><i
											class="fa fa-tags text-primary"></i>&nbsp; <label
											class="label label-info">Html 5</label> <label
											class="label label-primary">Css 3</label> <label
											class="label label-warning">Boostrap</label> <label
											class="label label-success">Jquery</label></li> -->
									</ul>
									<%
										if (self) {
									%>
									<div class="row">

										<div class="col-md-8 cols-sm-12 col-xs-12">

											<div class="media block-update-card"
												style="margin-bottom: 10px; border: 1px lightgray solid !important;">
												<div class="media-body update-card-body">
													<h4 class="media-heading" style="margin: 10px">Update
														Name</h4>
													<div class="btn-toolbar card-body-social" role="toolbar">
														<form method="post" action="About">
															<div class="input-group" style="margin: 10px">
																<input class="form-control" placeholder="first name"
																	type="text" name="fname" required="required"> <span
																	class="input-group-addon" id="basic-addon1"> : </span>
																<input class="form-control" placeholder="last name"
																	type="text" name="lname" required="required"> <span
																	class="input-group-addon" style="padding: 0;">
																	<button type="submit" class="btn-info" name="updatename"
																		style="width: 100%; height: 100%; padding-left: 5px; padding-right: 10px;">
																		<i class="fa fa-edit"></i> Update
																	</button>
																</span>
															</div>
														</form>
													</div>
												</div>
											</div>
										</div>
										<div class="col-md-4 cols-sm-12 col-xs-12">

											<div class="media block-update-card"
												style="margin-bottom: 10px; border: 1px lightgray solid !important;">
												<div class="media-body update-card-body">
													<h4 class="media-heading" style="margin: 10px">Update
														Date of birth</h4>
													<div class="btn-toolbar card-body-social" role="toolbar">
														<form method="post" action="About">
															<div class="input-group" style="margin: 10px">
																<input class="form-control" placeholder="last name"
																	type="date" name="dob" required="required"> <span
																	class="input-group-addon" style="padding: 0;">
																	<button type="submit" class="btn-info" name="updatedob"
																		style="width: 100%; height: 100%; padding-left: 5px; padding-right: 10px;">
																		<i class="fa fa-edit"></i> Update
																	</button>
																</span>
															</div>
														</form>
													</div>
												</div>
											</div>
										</div>
									</div>
									<%} %>
								</div>
								<!-- Work section -->
								<div class="about-tab-content <% if(request.getAttribute("tab").equals("2")) out.print("active");%>">
									<%if(works.size()!=0) {%>
									<ul class="list-group">
										<%for(Work work : works){ %>
										<li class="list-group-item"><i class="fa fa-briefcase"></i>&nbsp;
											<%=work.getOccupation()%> at <a href="#"><%=work.getCompany()%></a>, <%=work.getLocation()%><span class='pull-right'><%=DateHelper.printDob(work.getStartdate())%> - <%if(!work.isCurrent()) out.print(DateHelper.printDob(work.getEnddate())); else out.print("Current"); %></span> </li>
										<%} %>
									</ul>
									<%} %>
									<%
										if (self) {
									%>
									<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
										<div class="panel panel-default">
											<div class="" role="tab" id="workheading">
												
													<a  class="collapsed btn btn-default"  role="button" data-toggle="collapse"
														data-parent="#accordion" href="#workForm"
														aria-expanded="false" aria-controls="workForm" style="height: 100%;width:100%">
														Add Work </a>
												
											</div>
											<div id="workForm" class="panel-collapse collapse"
												role="tabpanel" aria-labelledby="workheading">
												<div class="panel-body">
														<form method="post" action="About">
															<div class="row"  style="margin-left: 10px;margin-right: 10px;margin-top: 20px">
																<div class="form-group col-md-6">
																	<label for="designation">Designation</label> 
																	<input class="form-control"
																		placeholder="Software developer, manager.."
																		type="text" id="designation" name="designation"
																		required="required">
																</div>
																<div class="form-group col-md-6">
																	<label for="company">Company</label> 
																	<input class="form-control" placeholder="Google, microsoft.."
																		type="text" id="company" name="company"
																		required="required">
																</div>
															</div>
															<div class="row"  style="margin-left: 10px;margin-right: 10px">
																<div class="form-group col-md-4">
																	<label for="sdate">Start Date</label> 
																	<input class="form-control"
																		type="date" id="sdate" name="sdate"
																		required="required">
																</div>
																<div class="form-group col-md-4">
																	<label for="edate">End Date</label> 
																	<input class="form-control"
																		type="date" id="edate" name="edate"
																		required="required">
																	 <label>
																      <input type="checkbox" name="current" value="true"> Current
																    </label>
																</div>
																<div class="form-group col-md-4">
																	<label for="location">Location</label> 
																	<input class="form-control"
																		type="text" id="location" name="location"
																		placeholder="Chicago, IL"
																		required="required">
																</div>
															</div>
															<button type="submit" class="btn btn-info pull-right"  name="addwork" style="margin: 10px;">
																		<i class="fa fa-plus"></i> Add Work
															</button>
														</form>
													</div>
												</div>
										</div>
											</div>
									<%} %>
								</div>

								<!-- Education section -->
								<div class="about-tab-content <% if(request.getAttribute("tab").equals("3")) out.print("active");%>">
									<%if(edus.size()!=0) {%>
									<ul class="list-group">
										<%for(Education edu : edus){ %>
										<li class="list-group-item"><i class="fa fa-briefcase"></i>&nbsp;
											<%=edu.getLevel()%> in <%=edu.getMajor()%> at <a href="#"><%=edu.getUniversity()%></a>, <%=edu.getLocation()%><span class='pull-right'><%=DateHelper.printDob(edu.getStartdate())%> - <%if(!edu.isCurrent()) out.print(DateHelper.printDob(edu.getEnddate())); else out.print("Current"); %></span> </li>
										<%} %>
									</ul>
									<%} %>
									<%
										if (self) {
									%>
									<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
										<div class="panel panel-default">
											<div class="" role="tab" id="eduheading">
												
													<a  class="collapsed btn btn-default"  role="button" data-toggle="collapse"
														data-parent="#accordion" href="#eduForm"
														aria-expanded="false" aria-controls="eduForm" style="height: 100%;width:100%">
														Add Education </a>
												
											</div>
											<div id="eduForm" class="panel-collapse collapse"
												role="tabpanel" aria-labelledby="eduheading">
												<div class="panel-body">
												<form method="post" action="About">
															<div class="row"  style="margin-left: 10px;margin-right: 10px;margin-top: 20px">
																<div class="form-group col-md-6">
																	<label>Level</label> 
																	<input class="form-control"
																		placeholder="Masters, B.Tech.."
																		type="text" id="level" name="level"
																		required="required">
																</div>
																<div class="form-group col-md-6">
																	<label>Major</label> 
																	<input class="form-control" placeholder="Computers,EEE.."
																		type="text" id="major" name="major"
																		required="required">
																</div>
															</div>
															<div class="row"  style="margin-left: 10px;margin-right: 10px">
																<div class="form-group col-md-6">
																	<label>Specialization</label> 
																	<input class="form-control"
																		placeholder="Database, Cloud.."
																		type="text" id="specialization" name="specialization"
																		required="required">
																</div>
																<div class="form-group col-md-6">
																	<label>University</label> 
																	<input class="form-control" placeholder="Illinois Institute of Technology"
																		type="text" id="university" name="university"
																		required="required">
																</div>
															</div>
															<div class="row"  style="margin-left: 10px;margin-right: 10px">
																<div class="form-group col-md-4">
																	<label for="sdate">Start Date</label> 
																	<input class="form-control"
																		type="date" id="sdate" name="sdate"
																		required="required">
																</div>
																<div class="form-group col-md-4">
																	<label for="edate">End Date</label> 
																	<input class="form-control"
																		type="date" id="edate" name="edate"
																		required="required">
																	 <label>
																      <input type="checkbox" name="current" value="true"> Current
																    </label>
																</div>
																<div class="form-group col-md-4">
																	<label for="location">Location</label> 
																	<input class="form-control"
																		type="text" id="location" name="location"
																		placeholder="Chicago, IL"
																		required="required">
																</div>
															</div>
															<button type="submit" class="btn btn-info pull-right"  name="addedu" style="margin: 10px;">
																		<i class="fa fa-plus"></i> Add Education
															</button>
														</form>
												</div>
											</div>
										</div>
										</div>
										<%} %>
								</div>
								<!-- Contact section -->
								<div class="about-tab-content <% if(request.getAttribute("tab").equals("4")) out.print("active");%>">
									<ul class="list-group">
									<%if(!contact.getPhone().trim().equals("")){ %>
										<li class="list-group-item"><i class="fa fa-mobile"></i>&nbsp;
											<%=contact.getPhone() %></li>
									<%} %>
									<li class="list-group-item"><i class="fa fa-envelope"></i>&nbsp;
											<%=tuser.getEmail()%></li>
									<%if(!contact.getTwitter().trim().equals("")){ %>
										<li class="list-group-item"><i class="fa fa-twitter text-twitter"></i>&nbsp;@<%=contact.getTwitter()%>
											</li>
									<%} %>
									<%if(!contact.getFacebook().trim().equals("")){ %>
										<li class="list-group-item"><i class="fa fa-facebook text-twitter"></i>&nbsp;/ <%=contact.getFacebook()%>
											</li>
									<%} %>
									<%if(!contact.getLinkedin().trim().equals("")){ %>
										<li class="list-group-item"><i class="fa fa-linkedin text-twitter"></i>&nbsp;/ <%=contact.getLinkedin()%>
											</li>
									<%} %>
										<li class="list-group-item"><i class="fa fa-envelope"></i>&nbsp;
											<%=contact.getAddress()%>, <%=contact.getCity()%>, <%=contact.getState()%>, <%=contact.getCountry()%> <%=contact.getZipcode()%></li>
									</ul>
									<%
										if (self) {
									%>
									<div class="panel-group" id="accordion" role="tablist"
										aria-multiselectable="true">
										<div class="panel panel-default">
											<div class="" role="tab" id="contantheading">
												<a class="collapsed btn btn-default" role="button"
													data-toggle="collapse" data-parent="#accordion"
													href="#contantForm" aria-expanded="false"
													aria-controls="contantForm"
													style="height: 100%; width: 100%"> Update Contant
													Information</a>
											</div>
											<div id="contantForm" class="panel-collapse collapse"
												role="tabpanel" aria-labelledby="contantheading">
												<div class="panel-body">
													<form method="post" action="About">
														<div class="row"
															style="margin-left: 10px; margin-right: 10px; margin-top: 20px">
															<div class="form-group col-md-6">
																<label>Phone</label> <input class="form-control"
																	placeholder="312-700-0000" type="text" id="phone"
																	name="phone" value="<%=contact.getPhone() %>">
															</div>
															<div class="form-group col-md-6">
																<label>Twitter</label>
																<div class="input-group">
																	<span class="input-group-addon"><i class="fa fa-twitter" ></i> @ </span> <input
																		class="form-control" placeholder="username"
																		type="text" id="twitter" name="twitter" value="<%=contact.getTwitter() %>">
																</div>
															</div>
														</div>
														<div class="row"
															style="margin-left: 10px; margin-right: 10px; margin-top: 20px">
															<div class="form-group col-md-6">
																<label>Facebook</label> 
																<div class="input-group">
																	<span class="input-group-addon"><i class="fa fa-facebook" ></i> / </span> <input
																		class="form-control" placeholder="facebook link"
																		type="text" id="facebook" name="facebook" value="<%=contact.getFacebook() %>">
																</div>
															</div>
															<div class="form-group col-md-6">
																<label>Linkedin</label>
																<div class="input-group">
																	<span class="input-group-addon"><i class="fa fa-linkedin" ></i> / </span> <input
																		class="form-control" placeholder="public url"
																		type="text" id="linkedin" name="linkedin" value="<%=contact.getLinkedin() %>">
																</div>
															</div>
														</div>
														<div class="row"
															style="margin-left: 10px; margin-right: 10px; margin-top: 20px">
															<div class="form-group col-md-8">
																<label>Address</label> 
																<input class="form-control" placeholder="2801 South King Drive, Apt:1202"
																		type="text" id="address" name="address" value="<%=contact.getAddress() %>">
															</div>
															<div class="form-group col-md-4">
																<label>City</label> 
																<input class="form-control" placeholder="Chicago, Newyork.."
																		type="text" id="city" name="city" value="<%=contact.getAddress() %>">
															</div>
														</div>
														<div class="row"
															style="margin-left: 10px; margin-right: 10px; margin-top: 20px">
															<div class="form-group col-md-4">
																<label>State</label> 
																<input class="form-control" placeholder="IL, MA.."
																		type="text" id="state" name="state" value="<%=contact.getState() %>">
															</div>
															<div class="form-group col-md-4">
																<label>Country</label> 
																<input class="form-control" placeholder="US, India.."
																		type="text" id="country" name="country" value="<%=contact.getCountry() %>">
															</div>
															<div class="form-group col-md-4">
																<label>Zipcode</label> 
																<input class="form-control" placeholder="60616"
																		type="text" id="zipcode" name="zipcode" value="<%=contact.getZipcode() %>">
															</div>
														</div>														
														<button type="submit" class="btn btn-info pull-right"  name="updatecontact" style="margin: 10px;">
																	update Contact
														</button>													
													</form>
												</div>
											</div>
										</div>
									</div>
									<%
										}
									%>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- end tabs user info -->
			
		</div>
	</div>
</div>
<jsp:include page="Template/footer.jsp" />