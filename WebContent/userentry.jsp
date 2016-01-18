<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ユーザー登録</title>
<link href="css/BBS.css" rel="stylesheet" type="text/css">

</head>
<body>
<div class =main-contents>
<c:if test="${ not empty loginUser }">
<h3>ユーザー登録</h3>
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

<form action="userentry" method="post"><br />
	<label for="name">社員氏名</label>
	<font size = 2><font color =red>
	※必須（10文字以下）</font></font><br/>
	<input name="name" value="${entryUser.name}" id="name">
	<br/>
	
	<label for="login_id">ログインID</label>
	<font size = 2><font color =red>
	※必須（半角英数字6文字以上20文字以下）</font></font><br/>
	<input name="login_id" value="${entryUser.login_id}" id="login_id" />
	<br/>
	
	<label for="password">パスワード</label>
	<font size = 2><font color =red>
	※必須（半角英語・数字・記号を含む6文字以上で入力してください）</font></font><br/>
	<input name="password" type="password" id="password"/>
	<br/>
	
	<label for="passwordcheck">パスワードの確認</label>
	<font size = 2><font color =red>
	※必須（同じパスワードを入力してください）
	</font></font><br/>
	<input name="passwordcheck" type="password" 
		id="passwordcheck"/>
	<br/>
	
	<label for="branch_id">支店</label><br/>
	<select id="sel1" name="branch_id" class="selectable">
		<c:forEach items="${branchs}" var="branch" >
			<option value="${branch.id}"
			<c:if test="${entryUser.branch_id == branch.id}">
			selected="${entryUser.branch_id}"</c:if>>
			${branch.branchname}</option>
		</c:forEach>
	</select><br/>
	
	<%-- toDo連動･･･支店別にプルダウン　--%>
	<label for="department_id">部署・役職</label><br/>
	<select id="sel2" name="department_id" class="selectable">
		<c:forEach items="${departments}" var="department" >
			<option value="${department.id}"
			<c:if test="${entryUser.department_id == department.id}">
			selected="${entryUser.department_id}"</c:if>>
			${department.departmentname }</option>
		</c:forEach>
	</select><br/>

	<br/><input type="submit" value="登録" /><br/><br/>
	<a href="usermanagement">ユーザー管理画面へ戻る</a>
</form>
</c:if>
</div>
</body>
</html>