package eBook.model;

public class Author {
	protected String description;
	protected String authorName;

	public Author(String description, String authorName) {
		this.description = description;
		this.authorName = authorName;
	}

	public Author(String authorName) {
		this.authorName = authorName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	@Override
	public String toString() {
		return "Description: " + description + ", Name: " + authorName + "";
	}
}
