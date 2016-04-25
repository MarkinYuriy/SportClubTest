package sportclub.data;

public class ImageData {
	String imageName;
	String linkToFile;
	String descriptions;
	
	
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public String getLinkToFile() {
		return linkToFile;
	}
	public void setLinkToFile(String linkToFile) {
		this.linkToFile = linkToFile;
	}
	public String getDescriptions() {
		return descriptions;
	}
	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}
	public ImageData(String imageName, String linkToFile, String descriptions) {
		super();
		this.imageName = imageName;
		this.linkToFile = linkToFile;
		this.descriptions = descriptions;
	}
}
