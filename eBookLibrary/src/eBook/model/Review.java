package eBook.model;

import java.sql.Timestamp;

public class Review {
	protected int reviewId;
	protected Users user;
	protected Book book;
	protected String comments;
	protected Timestamp created;
	protected Double rating;

	public Review(int reviewId, Users user, Book book, String comments, Timestamp created, Double rating) {
		this.reviewId = reviewId;
		this.user = user;
		this.book = book;
		this.comments = comments;
		this.created = created;
		this.rating = rating;
	}
	
	public Review(Users user, Book book, String comments, Timestamp created, Double rating) {
		this.user = user;
		this.book = book;
		this.comments = comments;
		this.created = created;
		this.rating = rating;
	}
	
	public Review(int reviewId) {
		super();
		this.reviewId = reviewId;
	}

	public Review(Users user, Book book) {
		super();
		this.user = user;
		this.book = book;
	}
	
	public Integer getReviewId() {
		return reviewId;
	}

	public void setReviewId(Integer reviewId) {
		this.reviewId = reviewId;
	}
	
	public String getComments() {
		return comments;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Timestamp getCreated() {
		return created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}
}
