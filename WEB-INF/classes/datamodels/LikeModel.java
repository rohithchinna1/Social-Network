package datamodels;
import java.util.ArrayList;
import java.util.Date;

import beans.User;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

public class LikeModel{
	public static final String postid = "postid";
	public static final String userid = "userid";
	public static final String like = "like";
	public static final String datetime = "datetime";
	
	DBCollection likes;
	public LikeModel(){
		Model model = Model.createInstance();
		likes = model.getCollection("Like");
	}
	
	public boolean setLike(String PostId, String UserId){
		PostModel pm = new PostModel();
		
		BasicDBObject findfields = new BasicDBObject();
		findfields.put(postid, PostId);
		findfields.put(userid, UserId);
		DBCursor cursor = likes.find(findfields);
		if(cursor.hasNext()){
			BasicDBObject bobj = (BasicDBObject) cursor.next();
			if(!bobj.getBoolean(like)){
				BasicDBObject updatefields = new BasicDBObject();
				updatefields.put(like, true);
				updatefields.put(datetime, new Date());
				
				BasicDBObject setfields = new BasicDBObject();
				setfields.append("$set", updatefields);
						
				likes.update(findfields, setfields);
				pm.setLikePost(PostId, true);
				pm.setDisLikePost(PostId, false);
				return true;
			}else{
				likes.remove(findfields);
				pm.setLikePost(PostId, false);
				return false;
			}			
		}
		
		BasicDBObject newLike = new BasicDBObject(postid, PostId)
		.append(postid,PostId)
		.append(userid,UserId)
		.append(like,true)
		.append(datetime,new Date());
		
		likes.insert(newLike);
		pm.setLikePost(PostId, true);
		return true;
	}
	
	public void setDisLike(String PostId, String UserId){
		PostModel pm = new PostModel();
		
		BasicDBObject findfields = new BasicDBObject();
		findfields.put(postid, PostId);
		findfields.put(userid, UserId);
		DBCursor cursor = likes.find(findfields);
		if(cursor.hasNext()){
			BasicDBObject bobj = (BasicDBObject) cursor.next();
			if(bobj.getBoolean(like)){
				BasicDBObject updatefields = new BasicDBObject();
				updatefields.put(like, false);
				updatefields.put(datetime, new Date());
				
				BasicDBObject setfields = new BasicDBObject();
				setfields.append("$set", updatefields);
						
				likes.update(findfields, setfields);
				pm.setLikePost(PostId, false);
				pm.setDisLikePost(PostId, true);
				return;
			}else{
				likes.remove(findfields);
				pm.setDisLikePost(PostId, false);
				return;
			}			
		}
		
		BasicDBObject newLike = new BasicDBObject(postid, PostId)
		.append(postid,PostId)
		.append(userid,UserId)
		.append(like,false)
		.append(datetime,new Date());
		
		likes.insert(newLike);
		pm.setDisLikePost(PostId, true);
		return;
	}
	
	public boolean isLiked(String PostId, String UserId){
		BasicDBObject findfields = new BasicDBObject();
		findfields.put(postid, PostId);
		findfields.put(userid, UserId);
		DBCursor cursor = likes.find(findfields);
		if(cursor.hasNext()){
			BasicDBObject bobj = (BasicDBObject) cursor.next();
			if(bobj.getBoolean(like))
			return true;
		}
		return false;		
	}
	
	public boolean isDisLiked(String PostId, String UserId){
		BasicDBObject findfields = new BasicDBObject();
		findfields.put(postid, PostId);
		findfields.put(userid, UserId);
		DBCursor cursor = likes.find(findfields);
		if(cursor.hasNext()){
			BasicDBObject bobj = (BasicDBObject) cursor.next();
			if(!bobj.getBoolean(like))
			return true;
		}
		return false;		
	}
	
	public void deleteLikeDislikes(String PostId){
		BasicDBObject findfields = new BasicDBObject();
		findfields.put(postid, PostId);
		
		likes.remove(findfields);
	}
	
	public ArrayList<User> getLikes(String PostId){
		BasicDBObject findfields = new BasicDBObject();
		findfields.put(postid, PostId);
		findfields.put(like, true);
		DBCursor cursor = likes.find(findfields);
		UserModel um = new UserModel();
		ArrayList<User> users = new ArrayList<User>();
		while(cursor.hasNext()){
			BasicDBObject bobj = (BasicDBObject) cursor.next();
			users.add(um.getUser(bobj.getString(userid)));
		}
		return users;		
	}
	
	public ArrayList<User> getDisLikes(String PostId){
		BasicDBObject findfields = new BasicDBObject();
		findfields.put(postid, PostId);
		findfields.put(like, false);
		DBCursor cursor = likes.find(findfields);
		UserModel um = new UserModel();
		ArrayList<User> users = new ArrayList<User>();
		while(cursor.hasNext()){
			BasicDBObject bobj = (BasicDBObject) cursor.next();
			users.add(um.getUser(bobj.getString(userid)));
		}
		return users;		
	}
}
