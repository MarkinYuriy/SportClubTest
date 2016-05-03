package sportclub.data;

public abstract class MessageData {
	private int id;
	private String errorMessage;
	
	
	
	public MessageData(int id, String errorMessage) {
		super();
		this.id = id;
		this.errorMessage = errorMessage;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	
	
}
