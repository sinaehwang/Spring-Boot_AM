<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물리스트</title>
<link rel="stylesheet" href="/resource/common.css" />
<script src="/resource/common.js" defer="defer"></script>
</head>
<body>
	<h1>게시물리스트</h1>
	<hr />
	<table border=2>
	<thead>
		<tr>
			<th>번호</th>
			<th>날짜</th>
			<th>제목</th>
			<th>작성자</th>
		</tr>
	</thead>
	
	<tbody>
		<c:forEach var="article" items="${articles }">
		<tr>
			<td>${article.id}</td>
			<td>${article.regDate.substring(2,10)}</td>
			<td>
			<a href="../aritcle/detail?id=${article.id}">${article.title}</a>
			
			</td>
			<td>${article.memberId}</td>
			
		</tr>
		</c:forEach>
	</tbody>
	
	</table>

	
</body>
</html>