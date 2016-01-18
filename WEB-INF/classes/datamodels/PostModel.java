package datamodels;

import java.util.ArrayList;
import java.util.List;

import helpers.RandomID;
import beans.Post;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

public class PostModel{
	public static final String postid = "postid";
	public static final String parentpostid = "parentpostid";
	public static final String fromuserid = "fromuserid";
	public static final String touserid = "touserid";
	public static final String type = "type";
	public static final String text = "text";
	public static final String image = "image";
	public static final String video = "video";
	public static final String location = "location";
	public static final String datetime = "datetime";
	public static final String shares = "shares";
	public static final String likes = "likes";
	public static final String dislikes = "dislikes";
	
	DBCollection posts;
	public PostModel(){
		Model model = Model.createInstance();
		posts = model.getCollection("Post");
	}
	
	public String Create(Post post){
		String PostId = RandomID.GenerateId();
		BasicDBObject newPost = new BasicDBObject(postid, PostId)
		.append(parentpostid,post.getParentpostid())
		.append(fromuserid,post.getFromuserid())
		.append(touserid,post.getTouserid())
		.append(type,post.getType())
		.append(text,post.getText())
		.append(image,post.getImage())
		.append(video,post.getVideo())
		.append(location,post.getLocation())
		.append(datetime,post.getDatetime())
		.append(shares,post.getShares())
		.append(likes,post.getLikes())
		.append(dislikes,post.getDislikes());
		
		posts.insert(newPost);		
		return PostId;
	}
	
	public Post GetPost(String PostId){
		BasicDBObject fields = new BasicDBObject();
		fields.put(postid, PostId);
		return GetPost(fields);		
	}
	
	public ArrayList<Post> GetPosts(String UserId){
		ArrayList<Post> psts = new ArrayList<Post>();
		
		List<BasicDBObject> fields = new ArrayList<BasicDBObject>();
		fields.add(new BasicDBObject(touserid, UserId));
		
		BasicDBObject orfields = new BasicDBObject();
		orfields.put("$or", fields);
		
		BasicDBObject sortfields = new BasicDBObject();
		sortfields.put(datetime, -1);
		
		DBCursor cursor = posts.find(orfields).sort(sortfields);
		while(cursor.hasNext()){
			Post post = new Post();
			BasicDBObject bobj = (BasicDBObject) cursor.next();
			post.setPostid(bobj.getString(postid));
			post.setParentpostid(bobj.getString(parentpostid));
			post.setFromuserid(bobj.getString(fromuserid));
			post.setTouserid(bobj.getString(touserid));
			post.setType(bobj.getInt(type));
			post.setText(bobj.getString(text));
			post.setImage(bobj.getString(image));
			post.setVideo(bobj.getString(video));
			post.setLocation(bobj.getString(location));
			post.setDatetime(bobj.getDate(datetime));
			post.setShares(bobj.getInt(shares));
			post.setLikes(bobj.getInt(likes));
			post.setDislikes(bobj.getInt(dislikes));
			
			psts.add(post);
		}		
		return psts;				
	}
	
	public ArrayList<Post> GetFrndsPosts(String UserId){
		FriendModel fm = new FriendModel();
 		
		List<String> frndids = new ArrayList<String>();
		for(String frndid : fm.getFriendsIds(UserId)){
			frndids.add(frndid);
		}
		frndids.add(UserId);
		
		
		ArrayList<Post> psts = new ArrayList<Post>();
		
		List<BasicDBObject> fields = new ArrayList<BasicDBObject>();
		fields.add(new BasicDBObject(fromuserid, new BasicDBObject("$in",frndids)));
		fields.add(new BasicDBObject(touserid, new BasicDBObject("$in",frndids)));
		
		BasicDBObject orfields = new BasicDBObject();
		orfields.put("$or", fields);
		
		BasicDBObject sortfields = new BasicDBObject();
		sortfields.put(datetime, -1);
		
		DBCursor cursor = posts.find(orfields).sort(sortfields);;
		while(cursor.hasNext()){
			Post post = new Post();
			BasicDBObject bobj = (BasicDBObject) cursor.next();
			post.setPostid(bobj.getString(postid));
			post.setParentpostid(bobj.getString(parentpostid));
			post.setFromuserid(bobj.getString(fromuserid));
			post.setTouserid(bobj.getString(touserid));
			post.setType(bobj.getInt(type));
			post.setText(bobj.getString(text));
			post.setImage(bobj.getString(image));
			post.setVideo(bobj.getString(video));
			post.setLocation(bobj.getString(location));
			post.setDatetime(bobj.getDate(datetime));
			post.setShares(bobj.getInt(shares));
			post.setLikes(bobj.getInt(likes));
			post.setDislikes(bobj.getInt(dislikes));
			
			psts.add(post);
		}		
		return psts;				
	}
	
	
	public ArrayList<Post> GetPosts(String FromUserid,String ToUserid){
		ArrayList<Post> psts = new ArrayList<Post>();
		
		List<BasicDBObject> fields = new ArrayList<BasicDBObject>();
		fields.add(new BasicDBObject(fromuserid, ToUserid));
		fields.add(new BasicDBObject(touserid, ToUserid));
		
		BasicDBObject orfields = new BasicDBObject();
		orfields.put("$or", fields);
		
		BasicDBObject sortfields = new BasicDBObject();
		sortfields.put(datetime, -1);
		
		DBCursor cursor = posts.find(orfields).sort(sortfields);;
		while(cursor.hasNext()){
			Post post = new Post();
			BasicDBObject bobj = (BasicDBObject) cursor.next();
			post.setPostid(bobj.getString(postid));
			post.setParentpostid(bobj.getString(parentpostid));
			post.setFromuserid(bobj.getString(fromuserid));
			post.setTouserid(bobj.getString(touserid));
			post.setType(bobj.getInt(type));
			post.setText(bobj.getString(text));
			post.setImage(bobj.getString(image));
			post.setVideo(bobj.getString(video));
			post.setLocation(bobj.getString(location));
			post.setDatetime(bobj.getDate(datetime));
			post.setShares(bobj.getInt(shares));
			post.setLikes(bobj.getInt(likes));
			post.setDislikes(bobj.getInt(dislikes));
			
			psts.add(post);
		}		
		return psts;				
	}
	
	public ArrayList<Post> GetPosts(){
		ArrayList<Post> psts = new ArrayList<Post>();
	
		BasicDBObject sortfields = new BasicDBObject();
		sortfields.put(datetime, -1);
		
		DBCursor cursor = posts.find().sort(sortfields);
		while(cursor.hasNext()){
			Post post = new Post();
			BasicDBObject bobj = (BasicDBObject) cursor.next();
			post.setPostid(bobj.getString(postid));
			post.setParentpostid(bobj.getString(parentpostid));
			post.setFromuserid(bobj.getString(fromuserid));
			post.setTouserid(bobj.getString(touserid));
			post.setType(bobj.getInt(type));
			post.setText(bobj.getString(text));
			post.setImage(bobj.getString(image));
			post.setVideo(bobj.getString(video));
			post.setLocation(bobj.getString(location));
			post.setDatetime(bobj.getDate(datetime));
			post.setShares(bobj.getInt(shares));
			post.setLikes(bobj.getInt(likes));
			post.setDislikes(bobj.getInt(dislikes));
			
			psts.add(post);
		}		
		return psts;				
	}
	
	
	public Post GetPost(BasicDBObject fields){
		DBCursor cursor = posts.find(fields);
		if(cursor.hasNext()){
			Post post = new Post();
			BasicDBObject bobj = (BasicDBObject) cursor.next();
			post.setPostid(bobj.getString(postid));
			post.setParentpostid(bobj.getString(parentpostid));
			post.setFromuserid(bobj.getString(fromuserid));
			post.setTouserid(bobj.getString(touserid));
			post.setType(bobj.getInt(type));
			post.setText(bobj.getString(text));
			post.setImage(bobj.getString(image));
			post.setVideo(bobj.getString(video));
			post.setLocation(bobj.getString(location));
			post.setDatetime(bobj.getDate(datetime));
			post.setShares(bobj.getInt(shares));
			post.setLikes(bobj.getInt(likes));
			post.setDislikes(bobj.getInt(dislikes));
			return post;
		}
		return null;
	}
	
	public void setLikePost(String PostId, boolean Liked){
		BasicDBObject findfields = new BasicDBObject();
		findfields.put(postid, PostId);
		int inc = -1;
		if(Liked) inc=1;
		
		BasicDBObject updatefields = new BasicDBObject();
		updatefields.put(likes, inc);
		
		BasicDBObject incfields = new BasicDBObject();
		incfields.append("$inc", updatefields);
				
		posts.update(findfields, incfields);		
	}
	
	public void setDisLikePost(String PostId, boolean Liked){
		BasicDBObject findfields = new BasicDBObject();
		findfields.put(postid, PostId);
		
		int inc = -1;
		if(Liked) inc=1;
		
		BasicDBObject updatefields = new BasicDBObject();
		updatefields.put(dislikes, inc);
		
		BasicDBObject incfields = new BasicDBObject();
		incfields.append("$inc", updatefields);
				
		posts.update(findfields, incfields);		
	}
	
	
	public void setSharedPost(String PostId){
		BasicDBObject findfields = new BasicDBObject();
		findfields.put(postid, PostId);
		
		BasicDBObject updatefields = new BasicDBObject();
		updatefields.put(shares, 1);
		
		BasicDBObject incfields = new BasicDBObject();
		incfields.append("$inc", updatefields);
				
		posts.update(findfields, incfields);		
	}
	
	public void deletePost(String PostId){
		BasicDBObject findfields = new BasicDBObject();
		findfields.put(postid, PostId);
		
		posts.remove(findfields);
	}
}
