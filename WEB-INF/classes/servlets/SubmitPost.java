package servlets;

import helpers.SaveImage;
import helpers.UserSession;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import datamodels.ImageModel;
import datamodels.PostModel;
import beans.Image;
import beans.Post;

@WebServlet("/SubmitPost")
@MultipartConfig(fileSizeThreshold=1024*1024*2, 
maxFileSize=1024*1024*10,
maxRequestSize=1024*1024*50) 
public class SubmitPost extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String text = request.getParameter("post");
		String touserid = request.getParameter("touserid");
		String redirect = request.getParameter("redirect");
		String type = request.getParameter("type");
		HttpSession session = request.getSession(true);
		String UserId = UserSession.getUserid(session);
		
		Post post = new Post();
		post.setFromuserid(UserId);
		post.setParentpostid(null);
		if (touserid == null)
			post.setTouserid(UserId);
		else
			post.setTouserid(touserid);
		
		if(type.equals("3")){
			post.setType(3);
			post.setImage(null);
			post.setVideo(request.getParameter("postyoutube"));
		}else if(type.equals("2")){				
				Part part = request.getPart("postimage");
				Image image = SaveImage.Post(request, part);
				image.setUserid(UserId);
				
				ImageModel im = new ImageModel();
				im.create(image);

				post.setType(2);
				post.setImage(image.getImageid());
				post.setVideo(null);
		}else{
			post.setType(1);
			post.setImage(null);
			post.setVideo(null);
		}
		System.out.println(text);
		post.setText(text);
		post.setLocation(null);
		post.setDatetime(new Date());
		post.setShares(0);
		post.setLikes(0);
		post.setDislikes(0);
		
		PostModel pm = new PostModel();
		pm.Create(post);
		
		response.sendRedirect(redirect);
		return;
	}

}
