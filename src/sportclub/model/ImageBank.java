package sportclub.model;


import javax.persistence.*;

@Entity
public class ImageBank {
	
	public ImageBank(String imageName, String linkToFile, String descriptions) {
		super();
		this.imageName = imageName;
		this.linkToFile = linkToFile;
		this.descriptions = descriptions;
	}

	@Id
	@GeneratedValue
	int id;
	
	String imageName;
	String linkToFile;
	String descriptions;
	
	public ImageBank() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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
	
	

}
