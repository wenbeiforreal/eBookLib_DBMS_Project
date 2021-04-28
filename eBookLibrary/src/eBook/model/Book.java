package eBook.model;


public class Book {
	protected String bookName;
	protected String isbn;
	protected Integer pages;
	protected Publisher publisher;
	protected Author author;
	protected Integer publishedYear;
	protected String writtenLanguage;
	protected Double price;
	protected Category category;

	public enum Category {
		fiction, nonFiction
	}

	public Book(String bookName, String isbn, Integer pages, Publisher publisher, Author author, Integer publishedYear,
			String writtenLanguage, Double price, Category category) {
		this.bookName = bookName;
		this.isbn = isbn;
		this.pages = pages;
		this.publisher = publisher;
		this.author = author;
		this.publishedYear = publishedYear;
		this.writtenLanguage = writtenLanguage;
		this.price = price;
		this.category = category;
	}
	
	public Book(String bookName, String isbn, Integer pages, Publisher publisher, Author author, Integer publishedYear,
			String writtenLanguage) {
		super();
		this.bookName = bookName;
		this.isbn = isbn;
		this.pages = pages;
		this.publisher = publisher;
		this.author = author;
		this.publishedYear = publishedYear;
		this.writtenLanguage = writtenLanguage;
	}

	public Book() {};

	public Book(String isbn) {
		this.isbn = isbn;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Integer getPages() {
		return pages;
	}

	public void setPages(Integer pages) {
		this.pages = pages;
	}

	public Publisher getPublisher() {
		return publisher;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public Integer getPublishedYear() {
		return publishedYear;
	}

	public void setPublishedYear(Integer publishedYear) {
		this.publishedYear = publishedYear;
	}

	public String getWrittenLanguage() {
		return writtenLanguage;
	}

	public void setWrittenLanguage(String writtenLanguage) {
		this.writtenLanguage = writtenLanguage;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

}
