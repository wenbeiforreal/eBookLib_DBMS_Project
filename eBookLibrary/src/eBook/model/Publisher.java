package eBook.model;

public class Publisher {
	protected String description;
	protected String publisherName;

	public Publisher(String description, String publisherName) {
		this.description = description;
		this.publisherName = publisherName;
	}

	public Publisher(String publisherName) {
		this.publisherName = publisherName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	@Override
	public String toString() {
		return "Description: " + description + ", Name: " + publisherName + "";
	}
}
