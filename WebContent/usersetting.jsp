<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored ="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ユーザー編集</title>
<link href="css/BBS.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.js">
</script>
</head>
<body>
<div class="main-contents">
<h1>ユーザー編集</h1>
<c:if test="${ not empty loginUser }">
<c:if test="${not empty errorMessages}">
	<div class="errorMessages">
		<ul>
			<c:forEach items="${errorMessages}" var="message">
				<li><c:out value="${message}"/>
			</c:forEach>
		</ul>
	</div>
	<c:remove var="errorMessages" scope="session"/>
</c:if>

<form action="usersetting" method="post" ><br />
	<input name="id" type=hidden value="${editUser.id}" id="id"/>

	<label for="name">社員氏名</label>
	<font color=red size = 2>
	※（10文字以下）</font><br/>
	<input name="name" value="${editUser.name}" id="name"/><br />
	
	<label for="login_id">アカウント名</label>
	<font size = 2 color =red>
	※（半角英数字6文字以上20文字以下）</font><br/>
	<input name="login_id" value="${editUser.login_id}" id ="login_id"/><br />
	
	<label for="password">パスワード</label>
	<font size = 2 color =red>
	※（新しく更新する場合は半角英語・数字・記号を含む6文字以上で入力してください）</font><br/>
	<input name="password" type="password" id="password"/><br />
	
	<label for="passwordcheck">パスワードの確認</label>
	<font size = 2 color =red>
	※（新しいパスワードを再度入力してください）
	</font><br/>
	<input name="passwordcheck" type="password" id="passwordcheck"/><br />
	
		<label for="branch_id">支店</label><br/>
		<c:if test="${editUser.id == 1}">
		<input name="branch_id" type=hidden value="1" id="branch_id"/></c:if>
		<c:if test="${editUser.id == loginUser.id}">
		<input name="branch_id" type=hidden value="1" id="branch_id"/></c:if>
	<select id="b_sel" name="branch_id">
		<c:forEach items="${branchs}" var="branch" >
			<option value="${branch.id}"
			<c:if test="${editUser.branch_id == branch.id}">
			selected="${editUser.branch_id}"</c:if>>
			${branch.branchname}</option>
		</c:forEach>

	</select><br/>

	<label for="department_id">部署・役職</label><br/>
	<c:if test="${editUser.id == 1}">
		<input name="department_id" type=hidden value="1" id="department_id"/></c:if>
		<c:if test="${editUser.id == loginUser.id}">
		<input name="department_id" type=hidden value="1" id="department_id"/></c:if>
	<select id="d_sel" name="department_id">
		<c:forEach items="${departments}" var="department" >
			<option value="${department.id}" class="${department.branch_id}"
			<c:if test="${editUser.department_id == department.id}">
			selected="${editUser.department_id}"</c:if>>
			${department.departmentname}</option>
			
		</c:forEach>
	</select><br/>
	
	<br/><input type="submit" value="更新" /><br/><br/>
</form>

<a href=usermanagement>ユーザー管理画面へ戻る</a>
</c:if>
</div>
<script>
$(function(){
	if("${editUser.id}" == "${loginUser.id}"
			|| "${editUser.id}" == "1"){
		$("select").attr("disabled","disabled");
	}else{
		$("select").removeAttr("disabled");
	}
});
</script>

<script type="text/javascript">
$(document).ready(function(){

	// プルダウンのoption内容をコピー
	var pd2 = $("#d_sel option").clone();
	// 1→2連動
	$("#b_sel").change(function () {
		// lv1のvalue取得
		var braVal = $("#b_sel").val();
		// lv2Pulldownのdisabled解除
		$("#d_sel").removeAttr("disabled");
		// 一旦、lv2Pulldownのoptionを削除
		$("#d_sel option").remove();
		// (コピーしていた)元のlv2Pulldownを表示
		$(pd2).appendTo("#d_sel");
		// 選択値以外のクラスのoptionを削除
		
		if(braVal != "1"){
			$("#d_sel option[class != 2]").remove();
		} else {
			$("#d_sel option[class != 1]").remove();
		}
	});
});
</script>
</body>
</html>