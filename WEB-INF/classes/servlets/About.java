package servlets;

import helpers.DateHelper;
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

import beans.Contact;
import beans.Education;
import beans.User;
import beans.Work;
import datamodels.ContactModel;
import datamodels.EducationModel;
import datamodels.UserModel;
import datamodels.WorkModel;

@WebServlet("/About")
public class About extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		if(!UserSession.isLogged(session)){
			response.sendRedirect("Login");
			return;
		}
		
		
		request.setAttribute("tab", "1");
		request.setAttribute("title", "Small World - About");
		request.getRequestDispatcher("/about.jsp").include(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		String UserId = UserSession.getUserid(session);
		UserModel um = new UserModel();
		if(request.getParameter("updatename")!=null){
			User user = um.getUser(UserId);
			user.setFirstname(request.getParameter("fname"));
			user.setLastname(request.getParameter("lname"));			
			um.updateUser(user);
			request.setAttribute("tab", "1");
		}if(request.getParameter("updatedob")!=null){
			User user = um.getUser(UserId);
			user.setDob(DateHelper.getDate(request.getParameter("dob")));			
			um.updateUser(user);
			request.setAttribute("tab", "1");
		}if(request.getParameter("addwork")!=null){
			WorkModel wm =new WorkModel();
			Work work = new Work();
			work.setWorkid(RandomID.GenerateId());
			work.setUserid(UserId);
			work.setOccupation(request.getParameter("designation"));
			work.setCompany(request.getParameter("company"));
			work.setLocation(request.getParameter("location"));
			work.setStartdate(DateHelper.getDate(request.getParameter("sdate")));
			work.setEnddate(DateHelper.getDate(request.getParameter("edate")));
			boolean crnt = false;
			if(request.getParameterValues("current")!=null) crnt = true;
			work.setCurrent(crnt);			
			wm.update(work);
			request.setAttribute("tab", "2");
		}if(request.getParameter("addedu")!=null){
			EducationModel em = new EducationModel();
			Education edu = new Education();
			edu.setEduid(RandomID.GenerateId());
			edu.setUserid(UserId);
			edu.setLevel(request.getParameter("level"));
			edu.setMajor(request.getParameter("major"));
			edu.setSpecialization(request.getParameter("specialization"));
			edu.setUniversity(request.getParameter("university"));
			edu.setLocation(request.getParameter("location"));
			edu.setStartdate(DateHelper.getDate(request.getParameter("sdate")));
			edu.setEnddate(DateHelper.getDate(request.getParameter("edate")));
			boolean crnt = false;
			if(request.getParameterValues("current")!=null) crnt = true;
			edu.setCurrent(crnt);			
			em.update(edu);
			request.setAttribute("tab", "3");
		}if(request.getParameter("updatecontact")!=null){
			ContactModel cm = new ContactModel();
			Contact contact = cm.getContact(UserId);
			contact.setUserid(UserId);
			contact.setPhone(request.getParameter("phone"));
			contact.setTwitter(request.getParameter("twitter"));
			contact.setFacebook(request.getParameter("facebook"));
			contact.setLinkedin(request.getParameter("linkedin"));
			contact.setAddress(request.getParameter("address"));
			contact.setCity(request.getParameter("city"));
			contact.setState(request.getParameter("state"));
			contact.setCountry(request.getParameter("country"));
			contact.setZipcode(request.getParameter("zipcode"));
			cm.update(contact);

			request.setAttribute("tab", "4");
		}
		request.setAttribute("title", "Small World - About");
		request.getRequestDispatcher("/about.jsp").include(request, response);		
	}
}
