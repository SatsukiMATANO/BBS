<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored = "false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ユーザー管理</title>

<script type="text/javascript">
<!--

function check(){
	if(window.confirm('アカウントの状態を更新しますか？')){
		
		window.alert('更新されました');
		return true;
		location.href = "./usermanagement";
	}else{
		
		window.alert('キャンセルされました');
		return false;
	}
}

function deleteCheck(){
	if(window.confirm('このアカウントを削除しますか？\n※削除したユーザーは元にもどせません')){
		
		window.alert('削除されました');
		return true;
	}else{
		
		window.alert('キャンセルされました');
		return false;
	}
}
// -->
</script>


</head>
<body>
<div class="main-contents">
<c:if test="${ not empty loginUser }">
<a href="userentry">ユーザー新規登録</a><br/><br/>

<font color = grey><font size = 2>
<br/>社員氏名を選択すると該当ユーザーの情報編集画面へ遷移します。<br/>
</font></font>

	<table border = "1">
		<tr>
			<th>ログインID</th>
			<th>社員氏名</th>
			<th>支店</th>
			<th>部署・役職</th>
			<th>ステータスの更新</th>
			<th>削除</th>
		</tr>
		<c:forEach items="${users}" var="user">
		
		<tr>
		<div class="user">
			<td>
				<span class="login_id"><c:out value="${user.login_id}" />
				</span>
			</td>
			<td>
					<span class="name">
					<a href = "usersetting?user_id=${user.id}"><c:out value="${user.name}" />
					</a>
					</span>
			</td>
			<td>
				<span class="branchname">
				<c:forEach items="${branchs}" var="branch">
				<c:if test="${user.branch_id == branch.id}">
				<c:out value="${branch.branchname}"/></c:if></c:forEach></span>
			</td>
			<td>
				<span class="departmentname">
				<c:forEach items="${departments}" var="department">
				<c:if test="${user.department_id == department.id}">
				<c:out value="${department.departmentname}"/></c:if></c:forEach></span>
			</td>
			<th>
				<!-- loginUserと管理ユーザーは停止・復活ボタンを非表示にする -->
				<form action="usermanagement" method ="post" onSubmit="return check()">
					<input name="id" type=hidden value="${user.id}" id="id"/>
					<span class="stoped">
						<c:if test="${user.stoped == 0}">
							<c:if test="${loginUser.id != user.id}">
							<c:if test="${user.id != 1}">
							<input name="stoped" type="hidden" value=1 id="stoped">
							<input type="submit" value="停止する">
							</c:if></c:if></c:if>
						<c:if test="${user.stoped == 1}">
						<c:if test="${loginUser.id != user.id}">
						<c:if test="${user.id != 1}">
							<input name="stoped" type="hidden" value=0 id="stoped">
							<input type="submit" value="復活する">
							</c:if></c:if></c:if>
							
					</span>
				</form>
			</th>
			<th>
			<!-- loginUserと管理ユーザーは削除ボタンを非表示にする -->
			<c:if test="${loginUser.id != user.id}">
			<c:if test="${user.id != 1}">
				<form action="usermanagement" method="post" onSubmit="return deleteCheck()">
					<input name="id" type=hidden value="${user.id}" id="id"/>
					<span class="delete">
					<input name="delete" type="hidden" value="delete" id="delete">
					<input type="submit" value="削除する">
					</span>
				</form>
			</c:if></c:if>
			</th>
		</div>
		</tr>
		</c:forEach>
		
	</table>



<br/><a href="./">ホームへ戻る</a>
</c:if>
</div>
</body>
</html>