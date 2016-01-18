package datamodels;
import java.util.ArrayList;
import java.util.List;

import beans.Friend;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

public class FriendModel{
	public static final String userid = "userid";
	public static final String friendid = "friendid";
	public static final String relation = "relation";
	public static final String accept = "accept";

	DBCollection friends;
	
	public FriendModel(){
		Model model = Model.createInstance();
		friends = model.getCollection("Friend");
	}
	
	public void sendRequest(String UserId, String FriendId){
		BasicDBObject fields = findFeild(UserId, FriendId);
		
		DBCursor cursor = friends.find(fields);
		if(!cursor.hasNext()){
			BasicDBObject newFrnd = new BasicDBObject(userid, UserId)
			.append(friendid,FriendId)
			.append(relation,null)
			.append(accept,false);
			
			friends.insert(newFrnd);
		}		
	}
	
	public void acceptRequest(String UserId, String FriendId){		
		BasicDBObject findfields = findFeild(UserId, FriendId);
		
		BasicDBObject updatefields = new BasicDBObject();
		updatefields.put(accept, true);
		
		BasicDBObject setfields = new BasicDBObject();
		setfields.put("$set", updatefields);
		
		friends.update(findfields, setfields);
	}
	
	public void cancelRequest(String UserId, String FriendId){	
		friends.remove(findFeild(UserId,FriendId));
	}
	
	public BasicDBObject findFeild(String UserId, String FriendId){		
		BasicDBObject fields1 = new BasicDBObject();
		fields1.put(userid, UserId);
		fields1.put(friendid, FriendId);
		
		BasicDBObject fields2 = new BasicDBObject();
		fields2.put(userid, FriendId);
		fields2.put(friendid, UserId);
		
		List<BasicDBObject> orfields = new ArrayList<BasicDBObject>();
		orfields.add(fields1);
		orfields.add(fields2);
		
		BasicDBObject fields = new BasicDBObject();
		fields.put("$or", orfields);
		return fields;
	}
	
	public boolean isFriend(String UserId, String FriendId){
		BasicDBObject fields1 = new BasicDBObject();
		fields1.put(userid, UserId);
		fields1.put(friendid, FriendId);
		
		BasicDBObject fields2 = new BasicDBObject();
		fields2.put(userid, FriendId);
		fields2.put(friendid, UserId);
		
		List<BasicDBObject> orfields = new ArrayList<BasicDBObject>();
		orfields.add(fields1);
		orfields.add(fields2);
		
		BasicDBObject fields = new BasicDBObject();
		fields.put("$or", orfields);
		
		DBCursor cursor = friends.find(fields);
		if(cursor.hasNext())
			return true;
		return false;
	}
	
	public Friend getRelation(String UserId, String FriendId){
		BasicDBObject fields1 = new BasicDBObject();
		fields1.put(userid, UserId);
		fields1.put(friendid, FriendId);
		
		BasicDBObject fields2 = new BasicDBObject();
		fields2.put(userid, FriendId);
		fields2.put(friendid, UserId);
		
		List<BasicDBObject> orfields = new ArrayList<BasicDBObject>();
		orfields.add(fields1);
		orfields.add(fields2);
		
		BasicDBObject fields = new BasicDBObject();
		fields.put("$or", orfields);		
		
		
		DBCursor cursor = friends.find(fields);
		if(cursor.hasNext()){
			BasicDBObject bobj = (BasicDBObject) cursor.next();
			Friend friend = new Friend();
			friend.setUserid(bobj.getString(userid));
			friend.setFriendid(bobj.getString(friendid));
			friend.setRelation(bobj.getString(relation));
			friend.setAccept(bobj.getBoolean(accept));
			
			return friend;
		}
		return null;
	}
	
	
	
	public ArrayList<Friend> getFriends(String UserId){
		ArrayList<Friend> frnds = new ArrayList<Friend>();
		List<BasicDBObject> orfields = new ArrayList<BasicDBObject>();
		orfields.add(new BasicDBObject(userid, UserId));
		orfields.add(new BasicDBObject(friendid, UserId));
		
		BasicDBObject fields = new BasicDBObject();
		fields.put(accept, true);
		fields.put("$or", orfields);		
		
		DBCursor cursor = friends.find(fields);
		while(cursor.hasNext()){
			BasicDBObject bobj = (BasicDBObject) cursor.next();
			Friend friend = new Friend();
			friend.setUserid(bobj.getString(userid));
			friend.setFriendid(bobj.getString(friendid));
			friend.setRelation(bobj.getString(relation));
			friend.setAccept(bobj.getBoolean(accept));
			
			frnds.add(friend);
		}
		return frnds;
	}
	
	public ArrayList<Friend> getPendingRequests(String FriendId){
		ArrayList<Friend> frnds = new ArrayList<Friend>();
		
		BasicDBObject fields = new BasicDBObject();
		fields.put(accept, false);
		fields.put(friendid, FriendId);		
		
		DBCursor cursor = friends.find(fields);
		while(cursor.hasNext()){
			BasicDBObject bobj = (BasicDBObject) cursor.next();
			Friend friend = new Friend();
			friend.setUserid(bobj.getString(userid));
			friend.setFriendid(bobj.getString(friendid));
			friend.setRelation(bobj.getString(relation));
			friend.setAccept(bobj.getBoolean(accept));
			
			frnds.add(friend);
		}
		return frnds;
	}
	
	public ArrayList<String> getFriendsIds(String UserId){
		ArrayList<String> frnds = new ArrayList<String>();
		List<BasicDBObject> orfields = new ArrayList<BasicDBObject>();
		orfields.add(new BasicDBObject(userid, UserId));
		orfields.add(new BasicDBObject(friendid, UserId));
		
		BasicDBObject fields = new BasicDBObject();
		fields.put("$or", orfields);
		fields.put(accept, true);
		
		DBCursor cursor = friends.find(fields);
		while(cursor.hasNext()){
			BasicDBObject bobj = (BasicDBObject) cursor.next();	
			if(bobj.getString(userid).equals(UserId))
				frnds.add(bobj.getString(friendid));
			frnds.add(bobj.getString(userid));
		}
		return frnds;
	}
	
	public ArrayList<String> getAllFriendsIds(String UserId){
		ArrayList<String> frnds = new ArrayList<String>();
		List<BasicDBObject> orfields = new ArrayList<BasicDBObject>();
		orfields.add(new BasicDBObject(userid, UserId));
		orfields.add(new BasicDBObject(friendid, UserId));
		
		BasicDBObject fields = new BasicDBObject();
		fields.put("$or", orfields);
		
		DBCursor cursor = friends.find(fields);
		while(cursor.hasNext()){
			BasicDBObject bobj = (BasicDBObject) cursor.next();	
			if(bobj.getString(userid).equals(UserId))
				frnds.add(bobj.getString(friendid));
			frnds.add(bobj.getString(userid));
		}
		return frnds;
	}
}
