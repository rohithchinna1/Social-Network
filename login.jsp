<%
	request.setAttribute("title", "Small World - Login");
	request.setAttribute("hidenav", "true");
%>
<jsp:include page="Template/header.jsp" />

<div class="image-container login-bg">
	<div class="container">
		<div class="row "><!-- animated fadeIn -->
			<div class="col-sm-6 col-sm-offset-3">
				<!-- Wizard container -->
				<div class="wizard-container">
					<div class="card wizard-card ct-wizard-sky" id="wizard" style="min-height: 0;">
						<form class="form-login text-center" method="post" action="Login">
							<h1 class="form-login-heading text-muted">Small World</h1>
							<%
								String email = "";
								if (request.getAttribute("email") != null)
									email = (String) request.getAttribute("email");
							
								if (request.getAttribute("error") != null)
									out.print("<h4 class='text-danger'><i class='fa fa-warning'></i> "+request.getAttribute("error")+"</h4>");
								
								if (request.getAttribute("success") != null)
									out.print("<h4 class='text-success'><i class='fa fa-thumbs-up'></i> "+request.getAttribute("success")+"</h4>");
							%>
							<input type="email" class="form-control" name="email" placeholder="Email address" value="<%=email%>" required="required" autofocus onfocus="this.value = this.value;"/> 
							<input type="password" class="form-control" name="password"  placeholder="Password"/>
							<button class="btn btn-lg btn-sky btn-fill btn-block"
								type="submit">
								<i class="fa fa-share"></i> Login
							</button>
							<style>
							. btn-forget:hover{
							color:white;
							}
							</style>
							<button class="btn btn-sky btn-xs btn-block btn-forget"
								type="submit" name="forgot">Forgot Password</button>
							<br>
							<a href="Register" class="text-center new-account">Create an account </a>
							<br>
							<br>
						</form>
					</div>
				</div>
			</div>
		</div> 
	</div>
</div>
<jsp:include page="Template/footer.jsp" />