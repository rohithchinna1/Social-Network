package servlets;

import helpers.RandomID;
import helpers.Result;
import helpers.SendEMail;
import helpers.UserSession;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import datamodels.AuthModel;
import datamodels.UserModel;
import beans.User;

@WebServlet("/Login")
public class Login extends HttpServlet {
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		UserSession.isNotLogged(session, response);
				
		if (request.getParameter("authid") != null) {
			String AuthId = request.getParameter("authid");
			AuthModel am = new AuthModel();
			String Email = am.Activate(AuthId);
			if (Email != null) {
				request.setAttribute("success", Email	+ " is successfully activated. Please login");
				request.setAttribute("email", Email);
			}
		}
		request.getRequestDispatcher("/login.jsp").include(request, response);		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		UserSession.isNotLogged(session, response);

		String email = request.getParameter("email");
		String password = request.getParameter("password");

		if(email.equals("admin@smallworld.com")&&password.equals("password")){
			UserSession.setAdminLogged(session);
			response.sendRedirect("Admin");
			return;
		}	
		
		request.setAttribute("email", email);
		
		UserModel um = new UserModel();
		if (request.getParameter("forgot") != null) {			
			User user = um.getUserByEmail(email);
			if(user!=null){
				String newPassword = RandomID.GeneratePassword();
				um.updatePassowrd(user.getUserid(), newPassword);
				user.setPassword(newPassword);
				SendEMail sm = new SendEMail();
				sm.ChangePassword(user);
				request.setAttribute("success", "Your new password is sent to "+email);
			}else{
				request.setAttribute("error", email +" not found. Please enter registered email id.");
			}
			
		}else{

			User user = new User();
			user.setEmail(email);
			user.setPassword(password);
			
			Result result = um.login(user, session);
			if (result.isSuccess()) {
				response.sendRedirect("Home");
				return;
			}
			request.setAttribute("error", result.getMessage());
		}
		request.getRequestDispatcher("/login.jsp").include(request, response);
		return;		
	}

}
