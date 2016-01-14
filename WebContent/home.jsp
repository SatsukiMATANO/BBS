<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Home</title>

<script type="text/javascript">
<!--
function deleteCheck(){
	if(window.confirm('この投稿を削除しますか？\n※削除した投稿は元にもどせません')){
		
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
<div class="mail-contents">
<c:if test="${ not empty errorMessages }">
	<div class="errorMessages">
		<ul>
			<c:forEach items="${errorMessages}" var="message">
				<li><c:out value="${message}" />
			</c:forEach>
		</ul>
	</div>
	<c:remove var="errorMessages" scope="session"/>
</c:if>
<div class="header">
	<c:if test="${ not empty loginUser }">
		<div Align=right>
		<c:out value="${loginUser.name}"/>/
		<a href ="logout">ログアウト</a><br/><br/>
		<a href ="usermanagement">ユーザー管理</a><br/>
		(管理メニューは本社総務部のみが操作可能)<br/></div>
		<a href ="newpost">新規投稿</a><br/>
		<form method="get">
			<label>日付検索
			<input type="date" value="${start_date}" name="start_date">
			～
			<input type="date" value="${end_date}" name="end_date">
			</label>
			<label for="s_category">カテゴリー検索</label>
			<select name="s_category">				
				<option value="">=カテゴリーを選択=</option>
				<c:forEach items="${categorys}" var="cate">
					<option value="${cate.category}"
					<c:if test="${select == cate.category}">
					selected="${cate.category}"</c:if>>
					<c:out value="${cate.category}"/></option>
				</c:forEach>
			</select>
			<input type="submit" value="検索"><br/>
		</form>
			<a href="./">全件表示</a>
	</c:if>
</div>
<c:if test="${ not empty loginUser }">


<div class="post">
	<c:forEach items="${posts}" var="post">
	<hr size=2 color=#999999>
		 <div class="post">
			<div class="title">件名：<c:out value="${post.title}"/>
			</div>
			<div class="name">投稿者：<c:out value="${post.name}"/>
			</div>
			<div class="text">本文：<pre><font size = 3><c:out value="${post.text}"/></font>
			</pre></div>
			<div class="category">カテゴリー：<c:out value="${post.category}"/>
			</div>
			<div class="insert_date">投稿日時：
			<fmt:formatDate value="${post.insert_date}"
			pattern="yyyy/MM/dd HH:mm:ss" /></div>
				<c:if test="${loginUser.department_id == 2}"><!-- 2=情報管理担当 -->
				<form method="post" onSubmit="return deleteCheck()">
					<input name="id" type=hidden value="${post.message_id}" id="id"/>
					<span class="delete">
					<input name="delete" type="hidden" value="delete" id="delete">
					<input type="submit" value="削除する">
					</span>
				</form>
				</c:if>
				<c:if test="${loginUser.department_id == 3}"><!-- 3=店長 -->
				<c:if test="${loginUser.branch_id == post.branch_id}">
				<form method="post" onSubmit="return deleteCheck()">
					<input name="id" type=hidden value="${post.message_id}" id="id"/>
					<span class="delete">
					<input name="delete" type="hidden" value="delete" id="delete">
					<input type="submit" value="削除する">
					</span>
				</form>
				</c:if></c:if>
		</div>
	<hr size=1 color=#999999 width="25%" align=left>
		<div class="comment">
			<font size="3">◆コメント◆
			<c:forEach items="${comments}" var="comment">
			<c:if test="${post.message_id == comment.message_id }">
			<div class="name">投稿者：<c:out value="${comment.name}"/></div>
			<div class="text">コメント本文：<br/>
			<pre><c:out value="${comment.text}"/></pre></div>
			<div class="insert_date">投稿日時：
			<fmt:formatDate value="${comment.insert_date}"
			pattern="yyyy/MM/dd HH:mm:ss" />
			</div>
			<hr size=1 color=#999999 width="25%" align=left>
			</c:if></c:forEach></font>
		</div>
			
		<form method ="post">
			<input type="hidden" name="message_id" value="${post.message_id}"/>
			<label for="text">コメント</label><font size=2>(500文字以下)</font><br/>
			<textarea name="text" cols="50" rows="5" class="text-box"><c:out value="${entryComment.text}"/></textarea><br/>
			<input type="submit" value="コメントする">
		</form>
			
	</c:forEach>
</div>
</c:if>
</div>

</body>
</html>