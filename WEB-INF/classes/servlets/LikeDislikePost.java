package servlets;

import helpers.UserSession;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import datamodels.LikeModel;
import datamodels.PostModel;

@WebServlet("/LikeDislikePost")
public class LikeDislikePost extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String PostId = request.getParameter("postid");
		String likedislike = request.getParameter("likedislike");
		
		HttpSession session = request.getSession(true);
		String UserId = UserSession.getUserid(session);
		
		LikeModel lm = new LikeModel();
		
		if(likedislike.equals("like")){
			lm.setLike(PostId, UserId);
		}else if(likedislike.equals("dislike")){
			lm.setDisLike(PostId, UserId);
		}
		
		boolean liked = false;
		boolean disliked = false;
		if(lm.isLiked(PostId,UserId )){
			liked = true;
		}
		if(lm.isDisLiked(PostId, UserId)){
			disliked=true;
		}
		
		//response.sendRedirect("Home");
		PostModel pm = new PostModel();
		
        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");
        response.getWriter().write("<like>");
        response.getWriter().write("<likecount>"+pm.GetPost(PostId).getLikes()+"</likecount>");
        response.getWriter().write("<liked>"+liked+"</liked>");
        response.getWriter().write("<dislikecount>"+pm.GetPost(PostId).getDislikes()+"</dislikecount>");
        response.getWriter().write("<disliked>"+disliked+"</disliked>");        
        response.getWriter().write("</like>");
        
        
	}

}
