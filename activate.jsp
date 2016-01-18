<%@page import="datamodels.AuthModel"%>
<%@page import="helpers.UserSession"%>
<%
	request.setAttribute("title", "Small World - User Activation");
	request.setAttribute("hidenav", "true");
	
	UserSession.isNotLogged(session, response);
	
	String AuthId = request.getParameter("authid");
	
	AuthModel am = new AuthModel();
	String email = am.Activate(AuthId);
	
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
								<% if(email!=null){%>							
								<h3 class="info-text">Account Activation successfull!</h3>
								<h4 class="info-text">User with email <%=email%>  is now activated. Please <a href="Login" class="new-account">login now</a></h4>
								<%} else { %>
								<h4 class="info-text">Something wrong with you activation link. Please try again!</h4>
								<%} %>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div> 
	</div>
</div>
<jsp:include page="Template/footer.jsp" />