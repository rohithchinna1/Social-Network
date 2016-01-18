package datamodels;
import java.util.ArrayList;

import beans.Education;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

public class EducationModel{
	public static final String userid = "userid";
	public static final String eduid = "eduid";
	public static final String level = "level";
	public static final String major = "major";
	public static final String specialization = "specialization";
	public static final String university = "university";
	public static final String startdate = "startdate";
	public static final String enddate = "enddate";
	public static final String location = "location";
	public static final String current = "current";

	DBCollection educations;
	
	public EducationModel(){
		Model model = Model.createInstance();
		educations = model.getCollection("Education");
	}
	
	public void update(Education education) {
		BasicDBObject fields = new BasicDBObject();
		fields.put(userid, education.getUserid());
		fields.put(eduid, education.getEduid());

		DBCursor cursor = educations.find(fields);

		BasicDBObject EduObj = new BasicDBObject()
				.append(level, education.getLevel())
				.append(major, education.getMajor())
				.append(specialization, education.getSpecialization())
				.append(university, education.getUniversity())
				.append(startdate, education.getStartdate())
				.append(enddate, education.getEnddate())
				.append(location, education.getLocation())
				.append(current, education.isCurrent());
		
		if (cursor.hasNext()) {
			BasicDBObject setfields = new BasicDBObject();
			setfields.append("$set", EduObj);					
			educations.update(fields, setfields);
		} else {
			EduObj.put(userid, education.getUserid());
			EduObj.put(eduid, education.getEduid());
			educations.insert(EduObj);
		}
	}
	
	public ArrayList<Education> getEdus(String UserID){
		ArrayList<Education> edus = new ArrayList<Education>();
		BasicDBObject fields = new BasicDBObject();
		fields.put(userid, UserID);

		DBCursor cursor = educations.find(fields);
		while(cursor.hasNext()) {
			Education education = new Education();
			BasicDBObject bobj = (BasicDBObject) cursor.next();
			education.setUserid(bobj.getString(userid));
			education.setEduid(bobj.getString(eduid));
			education.setLevel(bobj.getString(level));
			education.setMajor(bobj.getString(major));
			education.setSpecialization(bobj.getString(specialization));
			education.setUniversity(bobj.getString(university));
			education.setStartdate(bobj.getDate(startdate));
			education.setEnddate(bobj.getDate(enddate));
			education.setLocation(bobj.getString(location));
			education.setCurrent(bobj.getBoolean(current));
			edus.add(education);
		}
		return edus;
	}
	
	public void deleteEdu(String EduID){
		BasicDBObject fields = new BasicDBObject();
		fields.put(eduid, EduID);
		educations.remove(fields);
	}
}
