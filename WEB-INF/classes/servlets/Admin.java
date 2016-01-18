package servlets;

import helpers.UserSession;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Post;
import datamodels.AuthModel;
import datamodels.CommentModel;
import datamodels.LikeModel;
import datamodels.PostModel;
import datamodels.ReportModel;

@WebServlet("/Admin")
public class Admin extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		if(!UserSession.isAdminLogged(session)){
			response.sendRedirect("Home");
			return;
		}
		request.getRequestDispatcher("/admin.jsp").include(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("logout")!=null){
			HttpSession session = request.getSession(true);
			UserSession.AdminLogout(session);
			response.sendRedirect("Login");
			return;
		}
		
		
		String PostId = request.getParameter("postid");
		
		PostModel pm = new PostModel();					
		pm.deletePost(PostId);
		
		LikeModel lm = new LikeModel();
		lm.deleteLikeDislikes(PostId);
		
		CommentModel cm = new CommentModel();
		cm.deleteComments(PostId);
		
		ReportModel rm =new ReportModel();
		rm.deleteReports(PostId);
		
		request.getRequestDispatcher("/admin.jsp").include(request, response);
	}

}
