package beans;

import java.util.Date;

public class Report {
	private String reportid;
	private String userid;
	private int type;
	private String reportpostid;
	private String reportuserid;
	private Date datetime;
		
	public Report() {
	}
	public String getReportid() {
		return reportid;
	}
	public void setReportid(String reportid) {
		this.reportid = reportid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getReportpostid() {
		return reportpostid;
	}
	public void setReportpostid(String reportpostid) {
		this.reportpostid = reportpostid;
	}
	public String getReportuserid() {
		return reportuserid;
	}
	public void setReportuserid(String reportuserid) {
		this.reportuserid = reportuserid;
	}
	public Date getDatetime() {
		return datetime;
	}
	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}
	
	
}
