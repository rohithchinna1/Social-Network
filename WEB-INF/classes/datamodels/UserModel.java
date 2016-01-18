package datamodels;

import java.util.ArrayList;
import java.util.List;

import helpers.Result;
import helpers.UserSession;

import javax.servlet.http.HttpSession;

import beans.Auth;
import beans.Image;
import beans.User;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

public class UserModel{
	DBCollection users;
	
	public static final String userid="userid";
	public static final String firstname="firstname";
	public static final String lastname="lastname";
	public static final String email="email";
	public static final String password="password";
	public static final String dob="dob";
	public static final String gender="gender";
	public static final String profileimage="profileimage";
	public static final String coverimage="coverimage";
	
	public UserModel(){
		Model model = Model.createInstance();
		users = model.getCollection("User");
	}	
	
	public boolean create(User user,Image image, Auth auth){
		BasicDBObject fields = new BasicDBObject();
		fields.put(email, user.getEmail());
		
		DBCursor cursor = users.find(fields);
		if(cursor.hasNext())
			return false;		
		
		ImageModel im = new ImageModel();
		im.create(image);		
		
		BasicDBObject newUser = new BasicDBObject(userid, user.getUserid())
		.append(firstname,user.getFirstname())
		.append(lastname,user.getLastname())
		.append(email,user.getEmail())
		.append(password,user.getPassword())
		.append(dob,user.getDob())
		.append(gender,user.getGender())
		.append(profileimage,user.getProfileimage())
		.append(coverimage,user.getCoverimage());
		
		users.insert(newUser);
		
		AuthModel am = new AuthModel();
		am.create(auth);
		
		return true;
	}
	
	public Result login(User user, HttpSession session){
		
		BasicDBObject fields = new BasicDBObject();
		fields.put(email, user.getEmail());
		
		DBCursor cursor = users.find(fields);
		if(cursor.hasNext()) {
			BasicDBObject bobj = (BasicDBObject) cursor.next();
			
			AuthModel am = new AuthModel();
			if(!am.IsActivated(bobj.getString(userid)))
				return new Result(false,"You have to activate you account");
						
			if(!user.getPassword().equals(bobj.getString(password)))
				return new Result(false,"You have entered wrong password");
						
			UserSession usersession = new UserSession(session);
			usersession.setUserid(bobj.getString(userid));
			usersession.setFirstname(bobj.getString(firstname));
			usersession.setLastname(bobj.getString(lastname));
			usersession.setEmail(bobj.getString(email));
			
			ImageModel im = new ImageModel();
			Image image = im.getImage(bobj.getString(profileimage));
			
			usersession.setProfileImage(image.getName());
			return new Result(true,"Login Successful");
		}
		return new Result(false,"There is no account with this email");		
	}
		
	public User getUser(String UserId){
		BasicDBObject fields = new BasicDBObject();
		fields.put(userid, UserId);

		DBCursor cursor = users.find(fields);
		if(cursor.hasNext()) {
			User user = new User();
			BasicDBObject bobj = (BasicDBObject) cursor.next();
			user.setUserid(bobj.getString(userid));
			user.setFirstname(bobj.getString(firstname));
			user.setLastname(bobj.getString(lastname));
			user.setEmail(bobj.getString(email));
			user.setPassword(bobj.getString(password));
			user.setDob(bobj.getDate(dob));
			user.setGender(bobj.getString(gender));
			user.setProfileimage(bobj.getString(profileimage));
			user.setCoverimage(bobj.getString(coverimage));
			return user;
		}
		return null;		
	}
	
	public User getUserByEmail(String Email){
		BasicDBObject fields = new BasicDBObject();
		fields.put(email, Email);

		DBCursor cursor = users.find(fields);
		if(cursor.hasNext()) {
			User user = new User();
			BasicDBObject bobj = (BasicDBObject) cursor.next();
			user.setUserid(bobj.getString(userid));
			user.setFirstname(bobj.getString(firstname));
			user.setLastname(bobj.getString(lastname));
			user.setEmail(bobj.getString(email));
			user.setPassword(bobj.getString(password));
			user.setDob(bobj.getDate(dob));
			user.setGender(bobj.getString(gender));
			user.setProfileimage(bobj.getString(profileimage));
			user.setCoverimage(bobj.getString(coverimage));
			return user;
		}
		return null;		
	}
	
	public ArrayList<User> getNonfriends(String UserId){
		FriendModel fm = new FriendModel();
		ArrayList<User> usrs = new ArrayList<User>();
 		
		List<String> frndids = new ArrayList<String>();
		for(String frndid : fm.getAllFriendsIds(UserId)){
			frndids.add(frndid);
		}
		frndids.add(UserId);
		
		BasicDBObject fields = new BasicDBObject();
		fields.put(userid, new BasicDBObject("$nin",frndids));
		
		DBCursor cursor = users.find(fields);
		while(cursor.hasNext()) {
			User user = new User();
			BasicDBObject bobj = (BasicDBObject) cursor.next();
			user.setUserid(bobj.getString(userid));
			user.setFirstname(bobj.getString(firstname));
			user.setLastname(bobj.getString(lastname));
			user.setEmail(bobj.getString(email));
			user.setPassword(bobj.getString(password));
			user.setDob(bobj.getDate(dob));
			user.setGender(bobj.getString(gender));
			user.setProfileimage(bobj.getString(profileimage));
			user.setCoverimage(bobj.getString(coverimage));
			usrs.add(user);
		}
		return usrs;		
	}
	
	public Image getProfileImage(String UserId){
		BasicDBObject fields = new BasicDBObject();
		fields.put(userid, UserId);
		DBCursor cursor = users.find(fields);
		if(cursor.hasNext()) {
			BasicDBObject bobj = (BasicDBObject) cursor.next();
			ImageModel im = new ImageModel();
			Image image = im.getImage(bobj.getString(profileimage));
			return image;
		}
		return null;
	}
	
	public Image getCoverImage(String UserId){
		BasicDBObject fields = new BasicDBObject();
		fields.put(userid, UserId);
		DBCursor cursor = users.find(fields);
		if(cursor.hasNext()) {
			BasicDBObject bobj = (BasicDBObject) cursor.next();
			ImageModel im = new ImageModel();
			Image image = im.getImage(bobj.getString(coverimage));
			return image;
		}
		return null;
	}
	
	public void updateUser(User user){
		BasicDBObject findfields = new BasicDBObject();
		findfields.put(userid, user.getUserid());

		BasicDBObject updatefields = new BasicDBObject();
		updatefields.put(firstname, user.getFirstname());
		updatefields.put(lastname, user.getLastname());
		updatefields.put(gender, user.getGender());
		updatefields.put(dob, user.getDob());
		
		BasicDBObject setfields = new BasicDBObject();
		setfields.append("$set", updatefields);
				
		users.update(findfields, setfields);
	}
	
	public void updatePassowrd(String UserId,String NewPassword){
		BasicDBObject findfields = new BasicDBObject();
		findfields.put(userid, UserId);

		BasicDBObject updatefields = new BasicDBObject();
		updatefields.put(password, NewPassword);
		
		BasicDBObject setfields = new BasicDBObject();
		setfields.append("$set", updatefields);
				
		users.update(findfields, setfields);
	}
	
	public void updateProfileImage(Image image){
		ImageModel im = new ImageModel();
		im.create(image);
		
		BasicDBObject findfields = new BasicDBObject();
		findfields.put(userid, image.getUserid());

		BasicDBObject updatefields = new BasicDBObject();
		updatefields.put(profileimage, image.getImageid());
		
		BasicDBObject setfields = new BasicDBObject();
		setfields.append("$set", updatefields);
				
		users.update(findfields, setfields);
	}
	
	public void updateCoverImage(Image image){
		ImageModel im = new ImageModel();
		im.create(image);
		
		BasicDBObject findfields = new BasicDBObject();
		findfields.put(userid, image.getUserid());

		BasicDBObject updatefields = new BasicDBObject();
		updatefields.put(coverimage, image.getImageid());
		
		BasicDBObject setfields = new BasicDBObject();
		setfields.append("$set", updatefields);
				
		users.update(findfields, setfields);
	}
	
	public void deleteUser(String UserId){
		BasicDBObject findfields = new BasicDBObject();
		findfields.put(userid, UserId);
		
		users.remove(findfields);		
	}
	
	public ArrayList<User> autoComplete(String field, String UserId){
		ArrayList<User> usrs = new ArrayList<User>();
		
		BasicDBObject pattern = new BasicDBObject();
		pattern.append("$regex", field);
		pattern.append("$options", "$i");
		
		
		List<BasicDBObject> orfields = new ArrayList<BasicDBObject>();		
		orfields.add(new BasicDBObject(firstname, pattern));
		orfields.add(new BasicDBObject(lastname, pattern));
		
		BasicDBObject findfield = new BasicDBObject();
		findfield.append("$or", orfields);
		findfield.append(userid, new BasicDBObject("$ne",UserId));
		
		DBCursor cursor = users.find(findfield);
		while(cursor.hasNext()) {
			User user = new User();
			BasicDBObject bobj = (BasicDBObject) cursor.next();
			user.setUserid(bobj.getString(userid));
			user.setFirstname(bobj.getString(firstname));
			user.setLastname(bobj.getString(lastname));
			user.setEmail(bobj.getString(email));
			user.setPassword(bobj.getString(password));
			user.setDob(bobj.getDate(dob));
			user.setGender(bobj.getString(gender));
			user.setProfileimage(bobj.getString(profileimage));
			user.setCoverimage(bobj.getString(coverimage));
			usrs.add(user);
		}
		return usrs;
	}
	
}
