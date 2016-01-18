package servlets;

import helpers.UserSession;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import beans.Image;
import beans.User;
import datamodels.ImageModel;
import datamodels.UserModel;

@WebServlet("/SearchResult")
public class SearchResult extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean show = true;
		StringBuffer sb = new StringBuffer();
		if (request.getParameter("autofield") != null) {
			String field = request.getParameter("autofield");
			field = field.trim();
			if (!field.equals("")) {
				HttpSession session = request.getSession(true);
				String UserId = UserSession.getUserid(session);

				UserModel um = new UserModel();
				ImageModel im = new ImageModel();
				ArrayList<User> users = um.autoComplete(field, UserId);

				if (users.size() == 0)
					show = false;

				for (User user : users) {
					Image image = im.getImage(user.getProfileimage());
					sb.append("<user>");
					sb.append("<userid>" + user.getUserid() + "</userid>");
					sb.append("<fname>" + user.getFirstname() + "</fname>");
					sb.append("<lname>" + user.getLastname() + "</lname>");
					sb.append("<image>" + image.getName() + "</image>");
					sb.append("</user>");
				}

				//System.out.println("<users>" + sb.toString() + "</users>");
			} else
				show = false;
		} else
			show = false;
		if (show) {
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			response.getWriter().write("<users>" + sb.toString() + "</users>");
		} else {
			response.setStatus(HttpServletResponse.SC_NO_CONTENT);
		}
	}
}
