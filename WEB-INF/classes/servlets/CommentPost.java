package servlets;

import helpers.DateHelper;
import helpers.UserSession;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Comment;
import datamodels.CommentModel;

@WebServlet("/CommentPost")
public class CommentPost extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String text = request.getParameter("comment");
		String PostId = request.getParameter("postid");
		
		HttpSession session = request.getSession(true);
		String UserId = UserSession.getUserid(session);
		
		Comment comment = new Comment();
		comment.setPostid(PostId);
		comment.setUserid(UserId);
		comment.setType(1);
		comment.setText(text);
		comment.setDatetime(new Date());
		comment.setLikes(0);
		comment.setDislikes(0);
		
		CommentModel cm = new CommentModel();
		cm.AddComment(comment);
		response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");
        response.getWriter().write("<comment>");
        response.getWriter().write("<userid>"+UserSession.getUserid(session)+"</userid>");        
        response.getWriter().write("<image>"+UserSession.getProfileImage(session)+"</image>");
        response.getWriter().write("<name>"+UserSession.getFirstname(session)+" "+UserSession.getLastname(session)+"</name>");
        response.getWriter().write("<time>"+DateHelper.printTime(comment.getDatetime())+"</time>");
        response.getWriter().write("<text>"+text+"</text>");        
        response.getWriter().write("</comment>");
	}

}
