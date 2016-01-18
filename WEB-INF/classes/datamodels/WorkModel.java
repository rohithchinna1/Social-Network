package datamodels;

import java.util.ArrayList;

import beans.Work;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

public class WorkModel{
	public static final String userid = "userid";
	public static final String workid = "workid";
	public static final String occupation = "occupation";
	public static final String company = "company";
	public static final String startdate = "startdate";
	public static final String enddate = "enddate";
	public static final String location = "location";
	public static final String current = "current";

	DBCollection works;
	
	public WorkModel(){
		Model model = Model.createInstance();
		works = model.getCollection("Work");
	}
	
	public void update(Work work) {
		BasicDBObject fields = new BasicDBObject();
		fields.put(userid, work.getUserid());
		fields.put(workid, work.getWorkid());

		DBCursor cursor = works.find(fields);

		BasicDBObject WorkObj = new BasicDBObject()
				.append(occupation, work.getOccupation())
				.append(company, work.getCompany())
				.append(startdate, work.getStartdate())
				.append(enddate, work.getEnddate())
				.append(location, work.getLocation())
				.append(current, work.isCurrent());
		
		if (cursor.hasNext()) {
			BasicDBObject setfields = new BasicDBObject();
			setfields.append("$set", WorkObj);					
			works.update(fields, setfields);
		} else {
			WorkObj.put(userid, work.getUserid());
			WorkObj.put(workid, work.getWorkid());
			works.insert(WorkObj);
		}
	}
	
	public ArrayList<Work> getWorks(String UserID){
		ArrayList<Work> wrks = new ArrayList<Work>();
		BasicDBObject fields = new BasicDBObject();
		fields.put(userid, UserID);

		DBCursor cursor = works.find(fields);
		while(cursor.hasNext()) {
			Work work = new Work();
			BasicDBObject bobj = (BasicDBObject) cursor.next();
			work.setUserid(bobj.getString(userid));
			work.setWorkid(bobj.getString(workid));
			work.setOccupation(bobj.getString(occupation));
			work.setCompany(bobj.getString(company));
			work.setStartdate(bobj.getDate(startdate));
			work.setEnddate(bobj.getDate(enddate));
			work.setLocation(bobj.getString(location));
			work.setCurrent(bobj.getBoolean(current));
			wrks.add(work);
		}
		return wrks;
	}
	
	public void deleteWork(String WorkId){
		BasicDBObject fields = new BasicDBObject();
		fields.put(workid, WorkId);
		works.remove(fields);
	}

}
