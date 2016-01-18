package beans;

public class Auth {
	private String authid;
	private String userid;
	private boolean active;
	
	public Auth(){
		
	}
	
	public Auth(String authid, String userid, boolean active) {
		this.authid = authid;
		this.userid = userid;
		this.active = active;
	}

	public String getAuthid() {
		return authid;
	}

	public void setAuthid(String authid) {
		this.authid = authid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	
}


