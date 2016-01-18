package helpers;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import beans.Image;

public class SaveImage {
	public static Image Profile(HttpServletRequest request, Part part) throws IOException{
		
		String location = "Profile";
		String savePath = request.getServletContext().getRealPath("") + File.separator + "img" + File.separator + location;
		
		String ImageId = RandomID.GenerateId();
		String ext = fileExtention(part);
		String name = ImageId +"."+ ext;
		part.write(savePath + File.separator + name);
		//user.setProfileimage(ImageId);
		
		Image image = new Image();
		image.setImageid(ImageId);
		image.setLocation(location);
		image.setName(name);
		return image;
	}
	
	public static Image Cover(HttpServletRequest request, Part part) throws IOException{			
			String location = "Cover";
			String savePath = request.getServletContext().getRealPath("") + File.separator + "img" + File.separator + location;
			
			String ImageId = RandomID.GenerateId();
			String ext = fileExtention(part);
			String name = ImageId +"."+ ext;
			part.write(savePath + File.separator + name);
			//user.setProfileimage(ImageId);
			
			Image image = new Image();
			image.setImageid(ImageId);
			image.setLocation(location);
			image.setName(name);
			return image;
	}
	
	public static Image Post(HttpServletRequest request, Part part) throws IOException{			
		String location = "Post";
		String savePath = request.getServletContext().getRealPath("") + File.separator + "img" + File.separator + location;
		
		String ImageId = RandomID.GenerateId();
		String ext = fileExtention(part);
		String name = ImageId +"."+ ext;
		part.write(savePath + File.separator + name);
		//user.setProfileimage(ImageId);
		
		Image image = new Image();
		image.setImageid(ImageId);
		image.setLocation(location);
		image.setName(name);
		return image;
}
	
	public static String fileExtention(Part part) {
		//System.out.println(part);
		String contentDisp = part.getHeader("content-disposition");
		//System.out.println(contentDisp);
		String[] items = contentDisp.split(";");
		String fname=""; String ext = "";
		for (String s : items) {
			if (s.trim().startsWith("filename")){
				fname = s.substring(s.indexOf("=") + 2, s.length() - 1).toString();
				ext = fname.replaceAll(".*\\.", "");
			}
		}
		return ext;
	}
}
