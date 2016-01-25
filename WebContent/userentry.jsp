<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ユーザー新規登録</title>
<link href="css/BBS.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="//ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/
	themes/smoothness/jquery-ui.css">
<script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js">
</script>
<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>

</head>
<body>
<div class =main-contents>
<c:if test="${ not empty loginUser }">
<h1>ユーザー新規登録</h1>
<c:if test="${not empty errorMessages}">
	<div class="errorMessages">
		<ul>
			<c:forEach items="${errorMessages}" var = "message">
				<li><c:out value="${message}" />
			</c:forEach>
		</ul>
	</div>
	<c:remove var = "errorMessages" scope="session"/>
</c:if>

<form action="userentry" method="post"><br />
	<label for="name">社員氏名</label>
	<font size = 2 color =red>
	※（10文字以下）</font><br/>
	<input name="name" value="${entryUser.name}" id="name">
	<br/>
	
	<label for="login_id">ログインID</label>
	<font size = 2 color =red>
	※（半角英数字6文字以上20文字以下）</font><br/>
	<input name="login_id" value="${entryUser.login_id}" id="login_id" />
	<br/>
	
	<label for="password">パスワード</label>
	<font size = 2 color =red>
	※（半角英語・数字・記号を含む6文字以上で入力してください）</font><br/>
	<input name="password" type="password" id="password"/>
	<br/>
	
	<label for="passwordcheck">パスワードの確認</label>
	<font size = 2 color =red>
	※（同じパスワードを入力してください）
	</font><br/>
	<input name="passwordcheck" type="password" 
		id="passwordcheck"/>
	<br/>
	
	<label for="branch_id">支店</label><br/>
	<select id="b_sel" name="branch_id" class="selectable">
	<option value="99">=選択してください=</option>
		<c:forEach items="${branchs}" var="branch" >
			<option value="${branch.id}"
			<c:if test="${entryUser.branch_id == branch.id}">
			selected="${entryUser.branch_id}"</c:if>>
			${branch.branchname}</option>
		</c:forEach>
	</select><br/>

	<%-- toDo連動･･･支店別にプルダウン　--%>
	<label for="department_id">部署・役職</label><br/>
	<select id="d_sel" name="department_id" class="selectable">
	<option value="99" class=99>=選択してください=</option>
		<c:forEach items="${departments}" var="department" >
			<option value="${department.id}" class="${department.branch_id}"
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
<script type="text/javascript">
$(document).ready(function(){

	if($("#b_sel").val() == "99"){
	$("#d_sel").attr("disabled","disabled");
	}
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
		if(braVal != "99"){
		if(braVal != "1"){
			$("#d_sel option[class = 1]").remove();
		} else {
			$("#d_sel option[class = 2]").remove();
		}
		} else {
			$("#d_sel option[class != 99]").remove();
			$("#d_sel").attr("disabled","disabled");
		}
	});
});
</script>
</body>
</html>