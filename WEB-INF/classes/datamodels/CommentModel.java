package datamodels;

import java.util.ArrayList;

import helpers.RandomID;
import beans.Comment;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

public class CommentModel{
	DBCollection comments;

	public static final String commentid = "commentid";
	public static final String postid = "postid";
	public static final String userid = "userid";
	public static final String type = "type";
	public static final String text = "text";
	public static final String datetime = "datetime";
	public static final String likes = "likes";
	public static final String dislikes = "dislikes";
	
	public CommentModel(){
		Model model = Model.createInstance();
		comments = model.getCollection("Comment");
	}
	
	public String AddComment(Comment comment){
		String CommentId = RandomID.GenerateId();
		BasicDBObject newComment = new BasicDBObject(commentid, CommentId)
		.append(postid,comment.getPostid())
		.append(userid,comment.getUserid())
		.append(type,comment.getType())
		.append(text,comment.getText())
		.append(datetime,comment.getDatetime())
		.append(likes,comment.getLikes())
		.append(dislikes,comment.getDislikes());
		
		comments.insert(newComment);		
		return CommentId;
	}
	
	public ArrayList<Comment> GetComments(String PostId){
		ArrayList<Comment> cmnts = new ArrayList<Comment>();
		
		BasicDBObject fields = new BasicDBObject();
		fields.put(postid, PostId);
		
		DBCursor cursor = comments.find(fields);
		while(cursor.hasNext()){
			BasicDBObject bobj = (BasicDBObject) cursor.next();
			Comment comment = new Comment();
			comment.setCommentid(bobj.getString(commentid));
			comment.setPostid(bobj.getString(postid));
			comment.setUserid(bobj.getString(userid));
			comment.setType(bobj.getInt(type));
			comment.setText(bobj.getString(text));
			comment.setDatetime(bobj.getDate(datetime));
			comment.setLikes(bobj.getInt(likes));
			comment.setDislikes(bobj.getInt(dislikes));
			
			cmnts.add(comment);
		}
		return cmnts;
	}
	
	public void deleteComments(String PostId){
		BasicDBObject fields = new BasicDBObject();
		fields.put(postid, PostId);
		
		comments.remove(fields);
	}
}
