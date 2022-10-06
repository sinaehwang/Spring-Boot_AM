<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="ARTICLE DETAIL" />
<%@ include file="../common/head.jspf"%>

<section class="mt-8">
	<div class="container mx-auto px-3">
	<div class ="table-box-type-1">
	<table class ="">
	<thead>
		<tr>
			<th>번호</th>
			<td>${article.id}</td>
		</tr>
		<tr>
			<th>작성날짜</th>
			<td>${article.regDate.substring(2,10)}</td>
		</tr>
		<tr>
			<th>수정날짜</th>
			<td>${article.updateDate.substring(2,10)}</td>
		</tr>
		<tr>
			<th>제목</th>
			<td>${article.id}</td>
		</tr>
		<tr>
			<th>내용</th>
			<td>${article.body}</td>
		</tr>
		<tr>
			<th>작성자</th>
			<td>${article.memberId}</td>
		
	</thead>
</table>
	</div>
		</div>
</section>
<%@ include file="../common/foot.jspf"%>
	