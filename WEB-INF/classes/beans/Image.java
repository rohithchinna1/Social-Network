package beans;

public class Image {
	private String imageid;
	private String userid;
	private String name;
	private String location;
	public Image(){
		
	}
	public Image(String imageid, String userid, String name, String location) {
		super();
		this.imageid = imageid;
		this.userid = userid;
		this.name = name;
		this.location = location;
	}
	public String getImageid() {
		return imageid;
	}
	public void setImageid(String imageid) {
		this.imageid = imageid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
}
