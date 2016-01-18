package servlets;

import helpers.RandomID;
import helpers.UserSession;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Post;
import datamodels.PostModel;

@WebServlet("/SharePost")
public class SharePost extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ParentPostId = request.getParameter("postid");

		HttpSession session = request.getSession(true);
		String UserId = UserSession.getUserid(session);
		
		PostModel pm = new PostModel();
		Post post = pm.GetPost(ParentPostId);
		post.setParentpostid(post.getFromuserid());
		
		post.setFromuserid(UserId);
		post.setTouserid(UserId);
		
		post.setDatetime(new Date());
		post.setShares(0);
		post.setLikes(0);
		post.setDislikes(0);
		
		pm.Create(post);

		pm.setSharedPost(ParentPostId);
		
		response.sendRedirect("Home");
		return;
	}
}
