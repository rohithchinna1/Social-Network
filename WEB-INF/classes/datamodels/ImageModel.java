package datamodels;
import java.util.ArrayList;

import beans.Image;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

public class ImageModel{
	DBCollection images;

	public static final String imageid="imageid";
	public static final String userid="userid";
	public static final String name="name";
	public static final String location="location";
	
	public ImageModel(){
		Model model = Model.createInstance();
		images = model.getCollection("Image");
	}
	
	public void create(Image image){		
		BasicDBObject newImage = new BasicDBObject(imageid, image.getImageid())
		.append(userid,image.getUserid())
		.append(name,image.getName())
		.append(location,image.getLocation());
		
		images.insert(newImage);
	}
	
	public Image getImage(String ImageId){
		BasicDBObject fields = new BasicDBObject();
		fields.put(imageid, ImageId);
		Image image = new Image();
		DBCursor cursor = images.find(fields);
		if(cursor.hasNext()) {
			BasicDBObject bobj = (BasicDBObject) cursor.next();
			image.setImageid(bobj.getString(imageid));
			image.setUserid(bobj.getString(userid));
			image.setName(bobj.getString(name));
			image.setLocation(bobj.getString(location));
			
			return image;
		}
		return image;
	}
	
	public ArrayList<Image> getUserImages(String UserId){
		ArrayList<Image> imgs = new ArrayList<Image>();
		BasicDBObject fields = new BasicDBObject();
		fields.put(userid, UserId);

		DBCursor cursor = images.find(fields);
		while(cursor.hasNext()) {
			Image image = new Image();
			BasicDBObject bobj = (BasicDBObject) cursor.next();
			image.setImageid(bobj.getString(imageid));
			image.setUserid(bobj.getString(userid));
			image.setName(bobj.getString(name));
			image.setLocation(bobj.getString(location));
			imgs.add(image);
		}
		return imgs;
	}
	
	public ArrayList<Image> getUserImages(String UserId, String Location){
		ArrayList<Image> imgs = new ArrayList<Image>();
		BasicDBObject fields = new BasicDBObject();
		fields.put(userid, UserId);
		fields.put(location, Location);

		DBCursor cursor = images.find(fields);
		while(cursor.hasNext()) {
			Image image = new Image();
			BasicDBObject bobj = (BasicDBObject) cursor.next();
			image.setImageid(bobj.getString(imageid));
			image.setUserid(bobj.getString(userid));
			image.setName(bobj.getString(name));
			image.setLocation(bobj.getString(location));
			imgs.add(image);
		}
		return imgs;
	}
	
	public void deleteImage(String ImageId){
		BasicDBObject findfields = new BasicDBObject();
		findfields.put(imageid, ImageId);
		
		images.remove(findfields);
	}
	
	
}
