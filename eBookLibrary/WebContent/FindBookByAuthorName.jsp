<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Find Book By Author Name</title>
</head>
<body>
	<form action="findbookbyauthorname" method="post">
		<h1>Search for a Book by Author Name</h1>
		<p>
			<label for="authorName">AuthorName</label>
			<input id="authorName" name="authorName" value="${fn:escapeXml(param.authorName)}">
		</p>
		<p>
			<input type="submit">
			<br/><br/><br/>
			<span id="successMessage"><b>${messages.success}</b></span>
		</p>
	</form>
	<br/>
	<div id="bookCreate"><a href="bookcreate">Create Book</a></div>
	<br/>
	<h1>Matching Books</h1>
		<table border="1">
			<tr>
				<th>BookName</th>
				<th>ISBN</th>
				<th>Pages</th>
				<th>PublisherName</th>
				<th>AuthorName</th>
				<th>PublishedYear</th>
				<th>WrittenLanguage</th>
				<th>Price</th>
				<th>CheckOutBook</th>
				<th>Recommendation</th>
				<th>Review</th>
				<th>Delete Book</th>
			</tr>
			<c:forEach items="${books}" var="book" >
				<tr>
					<td><c:out value="${book.getBookName()}" /></td>
					<td><c:out value="${book.getIsbn()}" /></td>
					<td><c:out value="${book.getPages()}" /></td>
					<td><c:out value="${book.getPublisher()}" /></td>
					<td><c:out value="${book.getAuthor()}" /></td>					
					<td><c:out value="${book.getPublishedYear()}" /></td>
					<td><c:out value="${book.getWrittenLanguage()}" /></td>
					<td><c:out value="${book.getPrice()}" /></td>
					<td><a href="checkoutbook?isbn=<c:out value="${book.getIsbn()}"/>">CheckOutBook</a></td>
					<td><a href="recommendation?isbn=<c:out value="${book.getIsbn()}"/>">Recommendation</a></td>
					<td><a href="review?isbn=<c:out value="${book.getIsbn()}"/>">Review</a></td>
					<td><a href="bookdelete?isbn=<c:out value="${book.getIsbn()}"/>">Delete</a></td>
				</tr>
			</c:forEach>
		</table>
</body>
</html>