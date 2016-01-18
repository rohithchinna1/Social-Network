package datamodels;

import beans.Auth;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

public class AuthModel{
	DBCollection auths;
	
	public static final String authid = "authid";
	public static final String userid = "userid";
	public static final String active = "active";
	
	public AuthModel(){
		Model model = Model.createInstance();
		auths = model.getCollection("Auth");
	}
	
	public boolean create(Auth auth){
		BasicDBObject fields = new BasicDBObject();
		fields.put(userid, auth.getUserid());
		
		DBCursor cursor = auths.find(fields);
		if(cursor.hasNext()){
			BasicDBObject updatefields = new BasicDBObject();
			updatefields.put(authid, auth.getAuthid());
			updatefields.put(active, auth.isActive());
			
			BasicDBObject setfields = new BasicDBObject();
			setfields.append("$set", updatefields);
					
			auths.update(fields, setfields);
			return true;
		}
		
		BasicDBObject newAuth = new BasicDBObject(authid,auth.getAuthid())
		.append(userid, auth.getUserid())
		.append(active,auth.isActive());
		
		auths.insert(newAuth);
		
		return true;	
		
	}
	
	public String Activate(String AuthId){
		BasicDBObject fields = new BasicDBObject();
		fields.put(authid, AuthId);
		
		DBCursor cursor = auths.find(fields);
		if(cursor.hasNext()) {			
			BasicDBObject updatefields = new BasicDBObject();
			updatefields.put(active, true);
			
			BasicDBObject setfields = new BasicDBObject();
			setfields.append("$set", updatefields);
					
			auths.update(fields, setfields);
			
			BasicDBObject bobj = (BasicDBObject) cursor.next();
			UserModel um = new UserModel();
			return um.getUser(bobj.getString(userid)).getEmail();			
		}
		return null;
	}
	
	public boolean IsActivated(String UserId){
		BasicDBObject fields = new BasicDBObject();
		fields.put(userid, UserId);
		
		DBCursor cursor = auths.find(fields);
		if(cursor.hasNext()) {
			BasicDBObject bobj = (BasicDBObject) cursor.next();
			return bobj.getBoolean(active);
		}
		return false;
	}
}
