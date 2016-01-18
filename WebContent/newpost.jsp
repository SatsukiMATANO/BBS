<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新規投稿</title>
<link href="css/BBS.css" rel="stylesheet" type="text/css">

</head>
<body>
<div class="main-contents">
<h3>新規投稿</h3>
<c:if test="${ not empty loginUser }">
<c:if test="${not empty errorMessages}">
	<div class="errorMessages">
		<ul>
			<c:forEach items="${errorMessages}" var = "message">
				<li><font color=red>
				<c:out value="${message}" /></font>
			</c:forEach>
		</ul>
	</div>
	<c:remove var = "errorMessages" scope="session"/>
</c:if>

<form action="newpost" method ="post">
		<label for="title">件名</label><font size=2>(50文字以下)</font><br/>
		<input name="title" value="${entryPost.title}" id="title"/><br/>
		
		<label for="category">カテゴリー</label><font size=2>(10文字以下)</font>
		<br/>
		<input name="category" value="${entryPost.category}" id="category"/><br/>
		
		<label for="text">本文</label><font size=2>(1000文字以下)</font><br/>
		<textarea name="text" cols="50" rows="10" class="text-box"><c:out value="${entryPost.text}"/></textarea><br/>
		
		<br/><input type="submit" value="投稿する">
	</form>
<br/><a href="./">ホームへ戻る</a>
</c:if>
</div>
</body>
</html>