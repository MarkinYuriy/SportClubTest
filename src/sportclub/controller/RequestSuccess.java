package sportclub.controller;

public class RequestSuccess {
	private final String status = "Success";
	private Object data;
	
	public String getStatus() {
		return status;
	}
	
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
