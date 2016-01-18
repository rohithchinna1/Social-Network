<%
	request.setAttribute("title", "Small World - Registration");
	request.setAttribute("hidenav", "true");
%>
<jsp:include page="Template/header.jsp" />

<div class="image-container register-bg">
	<!--   Big container   -->
	<div class="container">
		<div class="row"> <!--  animated fadeIn -->
			<div class="col-sm-8 col-sm-offset-2">
				<!-- Wizard container -->
				<div class="wizard-container">
					<form method="post" action="Register" enctype="multipart/form-data">
						<div class="card wizard-card ct-wizard-orange" id="wizard" style="min-height: 0;">
							<div class="wizard-header text-center">
								<h1 class="app-title">Small World</h1>
							</div>
							<div class="row">
							
								<h4 class="info-text">Let's start with the basic information</h4>
								<%
									String firstname="",lastname="",email="",dob="",gender="";
									if (request.getParameter("register") != null){
										email = (String) request.getParameter("email");
										firstname = (String) request.getParameter("firstname");
										lastname = (String) request.getParameter("lastname");
										dob = (String) request.getParameter("dob");
										gender = (String) request.getParameter("gender");
									}
									if (request.getAttribute("error") != null)
										out.print("<h5 class='text-danger text-center'><b><i class='fa fa-warning'></i> "+request.getAttribute("error")+"</b></h5><br><br>");
								
								%>
								<div class="col-sm-4 col-sm-offset-1">
									<div class="picture-container">
										<div class="picture">
											<img src="img/Profile/default-avatar.png"
												class="picture-src" id="wizardPicturePreview" title="" />
											<input type="file" id="wizard-picture" name="profilepic" required="required" >
										</div>
										<h6>Choose Picture</h6>
									</div>
								</div>
								<div class="col-sm-6">
									<div class="form-group">
										<label>First Name</label> 
										<input type="text" class="form-control from-reg" name="firstname" value="<%=firstname%>" required="required" autofocus="autofocus">
									</div>
									<div class="form-group">
										<label>Last Name</label> 
										<input type="text" class="form-control from-reg" name="lastname" value="<%=lastname%>" required="required">
									</div>
								</div>
								
								<div class="col-sm-10 col-sm-offset-1">
									<div class="form-group">
										<label>Email</label> 
										<input type="email" class="form-control from-reg" name="email" value="<%=email%>" required="required">
									</div>
								</div>
								<div class="col-sm-5 col-sm-offset-1">
									<div class="form-group">
										<label>Password</label> 
										<input type="password" class="form-control from-reg" name="password" required="required">
									</div>
								</div>
								<div class="col-sm-5">
									<div class="form-group">
										<label>Re-enter Password</label> 
										<input type="password" class="form-control from-reg" name="repassword" required="required">
									</div>
								</div>
								<div class="col-sm-5 col-sm-offset-1">
									<div class="form-group">
										<label>Date of Birth</label>												
										<input type="date" class="form-control from-reg" name="dob" value="<%=dob%>" required="required">
									</div>
								</div>

								<div class="col-sm-5">
									<div class="form-group">
										<label>Gender</label><br> 
										<select name="gender" class="form-control from-reg" required="required">
											<option value="">--Select--</option>
											<option value="Male" <%if(gender.equals("Male")) out.print(" selected");%>>Male</option>
											<option value="Female" <%if(gender.equals("Female")) out.print(" selected");%>>Female</option>
											<option value="Other" <%if(gender.equals("Other")) out.print(" selected");%>>Other</option>
										</select>
									</div>
								</div>
							</div>
							<br><br>
							<div class="wizard-footer text-center">
								<input type="submit" class="btn btn-fill btn-sky btn-wd btn-sm " name="register" value="Register"/>									
								<br><br><br>
								<a href="Login" class="text-center new-account">Back to Login </a>
																				
							</div>
							<br><br>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>

<jsp:include page="Template/footer.jsp" />