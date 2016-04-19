package sportclub.controller;

public class RequestSuccess {
	private String status;
	public void setStatus(String status) {
		this.status = status;
	}
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
