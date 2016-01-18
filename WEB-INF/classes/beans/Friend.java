package beans;

public class Friend {
	private String userid;
	private String friendid;
	private String relation;
	private boolean accept;
	public Friend(String userid, String friendid, String relation,
			boolean accept) {
		this.userid = userid;
		this.friendid = friendid;
		this.relation = relation;
		this.accept = accept;
	}
	public Friend(){
		
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getFriendid() {
		return friendid;
	}
	public void setFriendid(String friendid) {
		this.friendid = friendid;
	}
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	public boolean isAccept() {
		return accept;
	}
	public void setAccept(boolean accept) {
		this.accept = accept;
	}
	
}
