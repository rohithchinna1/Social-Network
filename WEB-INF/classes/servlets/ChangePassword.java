package servlets;

import helpers.UserSession;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.User;
import datamodels.UserModel;

@WebServlet("/ChangePassword")
public class ChangePassword extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		UserSession.isLogged(session, response);
		
		request.setAttribute("title", "Small World - Change Password");
		request.getRequestDispatcher("/changepassword.jsp").include(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String oldp= request.getParameter("old");
		String newp = request.getParameter("new");
		String renewp = request.getParameter("renew");
		
		HttpSession session = request.getSession(true);
		String UserId = UserSession.getUserid(session);
		
		UserModel um = new UserModel();
		User user = um.getUser(UserId);
		
		if (user.getPassword().equals(oldp)) {
			if (newp.equals(renewp)) {
				um.updatePassowrd(UserId, newp);
				request.setAttribute("success","Password has been successfully changed");
			} else
				request.setAttribute("error","New Password does not match Re-enter Password");
		} else
			request.setAttribute("error", "Old password does not match");

		request.setAttribute("title", "Small World - Change Password");
		request.getRequestDispatcher("/changepassword.jsp").include(request, response);
	}

}
