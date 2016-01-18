<%
	request.setAttribute("title", "Small World - Registration Sucess");
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
						<div class="form-login text-center">
							<h1 class="form-login-heading text-muted">Small World</h1>
							<div class="row">
							
								<h3 class="info-text">Registration successfull!</h3>
								<h4 class="info-text">An activation link is sent to <%=request.getAttribute("email")%>, please verify you email.</h4>
							</div>
							<a href="Login" class="text-center new-account">Back to Login </a>
						</div>
					</div>
				</div>
			</div>
		</div> 
	</div>
</div>
<jsp:include page="Template/footer.jsp" />