package servlets;

import helpers.UserSession;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import datamodels.FriendModel;

@WebServlet("/FollowFriend")
public class FollowFriend extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		
		
		String FriendId = request.getParameter("friendid");
		String UserId = UserSession.getUserid(session);
		String status = request.getParameter("status");
		
		FriendModel fm = new FriendModel();
		
		if(status.equals("follow")){
			fm.sendRequest(UserId, FriendId);			
		}else if(status.equals("accept")){
			fm.acceptRequest(UserId, FriendId);
		}else{
			fm.cancelRequest(UserId, FriendId);
		}
		
		response.sendRedirect("Timeline?userid="+FriendId);
		return;
	}

}
