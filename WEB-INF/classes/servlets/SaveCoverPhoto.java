package servlets;

import helpers.SaveImage;
import helpers.UserSession;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import beans.Image;
import beans.User;
import datamodels.UserModel;

@WebServlet("/SaveCoverPhoto")
@MultipartConfig(fileSizeThreshold=1024*1024*2, 
maxFileSize=1024*1024*10,
maxRequestSize=1024*1024*50) 
public class SaveCoverPhoto extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserModel um = new UserModel();
		HttpSession session = request.getSession(true);
		String UserId = UserSession.getUserid(session);
		System.out.println("profile---");
		if(request.getParameter("cover")!=null){		
			Part part = request.getPart("profilepic");
			 System.out.println("cover---"+ part);
			Image image = SaveImage.Cover(request, part);
			image.setUserid(UserId);

			um.updateCoverImage(image);
		}
		else if (request.getParameter("profile") != null) {

			Part part = request.getPart("displaypic");
			 System.out.println("profile---"+ part);
			Image image = SaveImage.Profile(request, part);
			image.setUserid(UserId);

			um.updateProfileImage(image);
		}
		response.sendRedirect("Timeline");
		return;
	}

}
