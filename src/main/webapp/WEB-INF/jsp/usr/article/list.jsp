<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="${board.name} 게시판" />
<%@ include file="../common/head.jspf"%>

<section class="mt-8">
	<div class="container mx-auto px-3">
	<div class ="table-box-type-1">
	<table class ="">
			<colgroup>
				<col width="40" />
				<col width="50" />
				<col width="100" />
				<col width="40" />
			</colgroup>
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
				<td>${article.regDate.substring(2,16)}</td>
				<td><a class = "btn-text-link" href="../article/detail?id=${article.id}">${article.title}</a></td>
				<td>${article.extra_WriterName}</td>

			</tr>
		</c:forEach>
	</tbody>

</table>
	</div>
		</div>
</section>
<%@ include file="../common/foot.jspf"%>