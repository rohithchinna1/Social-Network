package datamodels;

import helpers.RandomID;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import beans.Post;
import beans.Report;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class ReportModel {	
	public static final String reportid = "reportid";
	public static final String userid = "userid";
	public static final String type = "type";
	public static final String reportpostid = "reportpostid";
	public static final String reportuserid = "reportuserid";
	public static final String datetime = "datetime";
	
	DBCollection reports;
	public ReportModel(){
		Model model = Model.createInstance();
		reports = model.getCollection("Report");
	}
	
	public String reportPost(Report report){
		String ReportId = RandomID.GenerateId();
		BasicDBObject fields = new BasicDBObject();
		fields.put(userid, report.getUserid());
		fields.put(reportpostid, report.getReportpostid());

		DBCursor cursor = reports.find(fields);
		if (!cursor.hasNext()) {
			report.setReportid(ReportId);
			report.setType(1);
			report.setDatetime(new Date());
			BasicDBObject ReportObj = new BasicDBObject()
					.append(reportid, report.getReportid())
					.append(userid, report.getUserid())
					.append(type, report.getType())
					.append(reportpostid, report.getReportpostid())
					.append(reportuserid, report.getReportuserid())
					.append(datetime, report.getDatetime());

			reports.insert(ReportObj);
			return ReportId;
		}
		return null;
	}
	
	public Report getReport(String ReportId){
		BasicDBObject fields = new BasicDBObject();
		fields.put(reportid, ReportId);
		DBCursor cursor = reports.find(fields);
		if (cursor.hasNext()) {
			BasicDBObject bobj = (BasicDBObject) cursor.next();
			Report report = new Report();
			report.setReportid(bobj.getString(reportid));
			report.setUserid(bobj.getString(userid));
			report.setType(bobj.getInt(type));
			report.setReportpostid(bobj.getString(reportpostid));
			report.setReportuserid(bobj.getString(reportuserid));
			report.setDatetime(bobj.getDate(datetime));
			
			return report;
		}
		return null;
	}
	
	public HashMap<Post,String> getReport(){
		HashMap<Post,String> reprts = new HashMap<Post,String>();
		PostModel pm =new PostModel();
		AggregationOutput aggregateData = reports.aggregate(new BasicDBObject("$group" , new BasicDBObject("_id" , "$reportpostid").append("count",new BasicDBObject("$sum",1))));

		for (DBObject result : aggregateData.results()) {
			BasicDBObject bobj = (BasicDBObject) result;
			//System.out.println(bobj.getString("_id") +":"+bobj.getString("count"));
			Post post = pm.GetPost(bobj.getString("_id"));
			if(post!=null){
			reprts.put(post,bobj.getString("count"));
			}
		}
		return reprts;
	}
	
	public void deleteReports(String ReportpostId){
		BasicDBObject fields = new BasicDBObject();
		fields.put(reportpostid, ReportpostId);
		reports.remove(fields);		
	}
	
	public boolean isReported(String UserId, String PostId){
		BasicDBObject fields = new BasicDBObject();
		fields.put(userid, UserId);
		fields.put(reportpostid, PostId);

		DBCursor cursor = reports.find(fields);
		if (cursor.hasNext())
			return true;
		return false;
	}
	
	
}
