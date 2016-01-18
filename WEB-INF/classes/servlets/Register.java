package servlets;

import helpers.DateHelper;
import helpers.Helper;
import helpers.RandomID;
import helpers.SaveImage;
import helpers.SendEMail;
import helpers.UserSession;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import datamodels.UserModel;
import beans.Auth;
import beans.Image;
import beans.User;

@WebServlet("/Register")
@MultipartConfig(fileSizeThreshold=1024*1024*2, 
maxFileSize=1024*1024*10,
maxRequestSize=1024*1024*50) 
public class Register extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		UserSession.isNotLogged(session, response);
		
		request.getRequestDispatcher("/register.jsp").include(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		UserSession.isNotLogged(session, response);
		
		String password = request.getParameter("password");
		String repassword = request.getParameter("repassword");
		
		if(!password.equals(repassword)){
			request.setAttribute("error", "Password does not match!");
			request.getRequestDispatcher("/register.jsp").include(request, response);
			return;
		}
		
		User user = new User();
		user.setUserid(RandomID.GenerateId());
		user.setFirstname(request.getParameter("firstname"));
		user.setLastname(request.getParameter("lastname"));
		user.setEmail(request.getParameter("email"));
		user.setPassword(password);
		user.setDob(DateHelper.getDate(request.getParameter("dob")));
		user.setGender(request.getParameter("gender"));
		
		Part part = request.getPart("profilepic");		
		Image image = SaveImage.Profile(request, part);		
		image.setUserid(user.getUserid());
		
		String ImageId = image.getImageid();		
		user.setProfileimage(ImageId);
		
		String authid = RandomID.GenerateId();
		Auth auth = new Auth();
		auth.setUserid(user.getUserid());
		auth.setAuthid(authid);
		auth.setActive(false);
		
		UserModel um = new UserModel();
		if(um.create(user, image,auth)){
			String Link = Helper.getURL(request);
			Link+= "Login?authid="+authid;
			SendEMail sm = new SendEMail();
			sm.NewUser(user, Link);
			request.setAttribute("email", request.getParameter("email"));
			request.getRequestDispatcher("/success.jsp").include(request, response);
			return;
		}
		else{
			request.setAttribute("error", request.getParameter("email")+" is already been registered");
			request.getRequestDispatcher("/register.jsp").include(request, response);
			return;
		}		
	}
	

}
