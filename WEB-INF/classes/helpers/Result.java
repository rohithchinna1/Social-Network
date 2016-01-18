package helpers;

public class Result {
	private String message;
	private boolean success;
	public Result(boolean success,String message) {
		this.message = message;
		this.success = success;
	}
	public Result() {
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}	
	
}
