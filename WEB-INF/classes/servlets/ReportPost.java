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
import beans.Report;
import datamodels.CommentModel;
import datamodels.LikeModel;
import datamodels.PostModel;
import datamodels.ReportModel;

@WebServlet("/ReportPost")
public class ReportPost extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String PostId = request.getParameter("postid");
		String redirect = request.getParameter("redirect");

		HttpSession session = request.getSession(true);
		String UserId = UserSession.getUserid(session);
		
		ReportModel rm = new ReportModel();
		Report report = new Report();
		report.setUserid(UserId);
		report.setReportpostid(PostId);
		rm.reportPost(report);
		
		response.sendRedirect(redirect);
		return;
	}

}
