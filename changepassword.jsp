<%@page import="helpers.UserSession"%>
<jsp:include page="Template/header.jsp" />
<div style="margin-top: 70px;"></div>
<div
	class="col-md-8 col-sm-12 col-xs-12 col-md-offset-2 animated fadeIn">
	<!-- user options -->
	<div class="col-md-2 col-sm-3 col-xs-12 left-content hidden-xs">
		<div class="left-user-options">
			<div class="img-thumbnail img-circle">
				<div class="img-user img-circle"
					style="background-image: url('img/Profile/<%=UserSession.getProfileImage(session)%>'),url('img/Profile/default-avatar.png'); ">

				</div>
			</div>
			<h4 style="text-align: center; font-weight: bolder;"><%=UserSession.getFirstname(session)%></h4>
			<br>
			<div class="list-group">
				<a href="Timeline" class="list-group-item"> <i
					class="fa fa-bars"></i> Timeline
				</a> <a href="About" class="list-group-item"> <i class="fa fa-edit"></i>
					Edit Profile
				</a> <a href="Photos" class="list-group-item"> <i
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
		<div class="col-md-12">
			<div class="panel panel-default">
				<div class="panel-heading" style="text-align: center;">
					<h4 style="font-size: 16px; font-weight: bold; ">Change
						Password</h4>
						<%
							if (request.getAttribute("error") != null)
								out.print("<br><h4 class='text-danger'><i class='fa fa-warning'></i> "+request.getAttribute("error")+"</h4>");
							
							if (request.getAttribute("success") != null)
								out.print("<br><h4 class='text-success'><i class='fa fa-thumbs-up'></i> "+request.getAttribute("success")+"</h4>");
						%>
				</div>
				<div class="panel-body">
					<div class="col-md-8 col-md-offset-2">
						<form class="form-horizontal" role="form" action="ChangePassword"
							method="post">

							<div class="form-group">
								<label>Old Password</label> <input type="password"
									class="form-control" name="old" required="required">
							</div>
							<div class="form-group">
								<label>New Password</label> <input type="password"
									class="form-control" name="new" required="required">
							</div>
							<div class="form-group">
								<label>Re-enter New Password</label> <input type="password"
									class="form-control" name="renew" required="required">
							</div>
							<button type="submit" class="btn btn-primary pull-right">Change Password</button>

						</form>
					</div>
				</div>
			</div>
		</div>

	</div>
</div>
<jsp:include page="Template/footer.jsp" />