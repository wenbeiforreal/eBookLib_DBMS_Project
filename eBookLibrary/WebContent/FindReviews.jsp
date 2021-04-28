<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet">
<title>Find a Review</title>
</head>
<body>
    <div class="container theme-showcase" role="main">
    
	<form action="findreviews" method="post">
	    <div class="jumbotron">
		<h1>Search for a Review</h1>
		</div>
		<p>
			<h2><label for="loginname">LoginName</label></h2>
			<input id="loginname" name="loginname" value="${fn:escapeXml(param.loginname)}">
		</p>
		<p>
			<input type="submit" class="btn btn-lg btn-primary">
			<br/><br/>
		</p>
	</form>
	<div id="reviewCreate"><h3><a href="reviewcreate">Create Review</a></h3></div>
	<br/>
	<div class="alert alert-info" role="alert">
	<h2><span id="successMessage"><b>${messages.success}</b></span></h2>
	</div>
	<br/>
	<h1>Matching Reviews</h1>
        <table class="table table-striped">
            <thead><tr>
                <th>LoginName</th>
                <th>ISBN</th>
                <th>Comments</th>
                <th>Created</th>
                <th>Rating</th>
<!--                 <th>Review</th>
                <th>Recommendation</th>
                <th>CheckOutBook</th> -->
                <th>Update Review</th>
				<th>Delete Review</th>
            </tr></thead>
            <c:forEach items="${reviews}" var="review" >
                <tbody><tr>
                    <td><c:out value="${review.getUser().getLoginName()}" /></td>
                    <td><c:out value="${review.getBook().getIsbn()}" /></td>
                    <td><c:out value="${review.getComments()}" /></td>
                    <td><c:out value="${review.getCreated()}" /></td>
                    <td><c:out value="${review.getRating()}" /></td>
					<td><a href="reviewupdate?reviewid=<c:out value="${review.getReviewId()}"/>">Update</a></td>
			 		<td><a href="reviewdelete?loginname=<c:out value="${review.getUser().getLoginName()}"/>">Delete</a></td>
                </tr></tbody>
            </c:forEach>
       </table>
       
    </div>
     
    <!-- Bootstrap -->
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="js/bootstrap.min.js"></script>
       
</body>
</html>
