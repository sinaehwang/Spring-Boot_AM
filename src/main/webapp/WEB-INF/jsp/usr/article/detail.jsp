<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물상세페이지</title>
</head>
<body>
	<div>게시물상세페이지</div>
	<hr />
	<table border=2>
	<thead>
		<tr>
			<th>번호</th>
			<th>작성날짜</th>
			<th>수정날짜</th>
			<th>제목</th>
			<th>내용</th>
			<th>작성자</th>
		</tr>
		
	</thead>
	
	<tbody>
		<c:forEach var="article" items="${article }">
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