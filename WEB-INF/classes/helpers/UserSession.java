package helpers;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import datamodels.UserModel;

public class UserSession {
	private HttpSession session;
	
	public UserSession(HttpSession session){
		this.session = session;
	}
	
	public String getUserid(){
		return (String) session.getAttribute(UserModel.userid);		
	}
	
	public String getFirstname(){
		return (String) session.getAttribute(UserModel.firstname);		
	}
	
	public String getLastname(){
		return (String) session.getAttribute(UserModel.lastname);		
	}
	
	public String getEmail(){
		return (String) session.getAttribute(UserModel.email);		
	}
	
	public String getProfileImage(){
		return (String) session.getAttribute(UserModel.profileimage);		
	}
	
	public void setUserid(String userid){
		session.setAttribute(UserModel.userid,userid);		
	}
	
	public void setFirstname(String firstname){
		session.setAttribute(UserModel.firstname,firstname);		
	}
	
	public void setLastname(String lastname){
		session.setAttribute(UserModel.lastname,lastname);		
	}
	
	public void setEmail(String email){
		session.setAttribute(UserModel.email,email);	
	}
	
	public void setProfileImage(String profileimage){
		session.setAttribute(UserModel.profileimage,profileimage);	
	}
	
	public static void setAdminLogged(HttpSession session){
		session.setAttribute("admin",true);		
	}
	
	public static boolean isAdminLogged(HttpSession session){
		if(session.getAttribute("admin")!=null)
			return true;
		return false;
	}
	public static void AdminLogout(HttpSession session){
		session.removeAttribute("admin");
	}
	
	
	public static String getUserid(HttpSession session){
		return (String) session.getAttribute(UserModel.userid);		
	}
	
	public static String getFirstname(HttpSession session){
		return (String) session.getAttribute(UserModel.firstname);		
	}
	
	public static String getLastname(HttpSession session){
		return (String) session.getAttribute(UserModel.lastname);		
	}
	
	public static String getEmail(HttpSession session){
		return (String) session.getAttribute(UserModel.email);		
	}	

	public static String getProfileImage(HttpSession session){
		return (String) session.getAttribute(UserModel.profileimage);		
	}
	
	public static boolean isLogged(HttpSession session){
		if(getUserid(session)!=null)
			return true;
		return false;
	}
	
	public static void isLogged(HttpSession session,HttpServletResponse response){
		try {
			if(!isLogged(session)){
				response.sendRedirect("Login");
				return;
			}
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void isNotLogged(HttpSession session,HttpServletResponse response){
		try {
			if(isLogged(session)){
				response.sendRedirect("Home");
				return;
			}
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void logout(HttpSession session){
		session.removeAttribute(UserModel.userid);
		session.removeAttribute(UserModel.firstname);
		session.removeAttribute(UserModel.lastname);
		session.removeAttribute(UserModel.email);
	}
	
	
	
}
