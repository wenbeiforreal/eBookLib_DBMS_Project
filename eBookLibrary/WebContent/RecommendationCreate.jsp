<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create a Recommendation</title>
</head>
<body>
	<h1>Create a new Recommendation</h1>
	<form action="recommendationcreate" method="post">
		<p>
			<label for="loginname">LoginName</label>
			<input id="loginname" name="loginname" value="">
		</p>
		<p>
			<label for="isbn">ISBN</label>
			<input id="isbn" name="isbn" value="">
		</p>
		<p>
			<label for="recommends">Recommends</label>
			<input id="recommends" name="recommends" type = password value="">
		</p>
		<p>
			<input type="submit">
		</p>
	</form>
	<br/><br/>
	<p>
		<span id="successMessage"><b>${messages.success}</b></span>
	</p>
</body>
</html>