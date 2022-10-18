<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="ARTICLE WRITE" />
<%@ include file="../common/head.jspf"%>

<section class="mt-8">
	<div class="container mx-auto px-3">
	<form class ="table-box-type-1" method="POST" action="../article/doAdd">
	<input type="hidden" name = "id" value="${article.id }" />
	<table>
			<colgroup>
				<col width="200" />
			</colgroup>
		
	<tbody>
		<tr>
			<th>작성자</th>
			<td>${rq.loginedMember.nickname}</td>
		</tr>
		
		<tr> 
			<th>게시판</th>
			<td>
			<label>
				공지사항
				<input type="radio" name = "boardId" value ="1" />
			</label>
			<label>
				자유
				<input type="radio" name = "boardId" value ="2" />
			</label>
			</td>
		</tr>
		
		<tr>
			<th>제목</th>
			<td><input class="w-full input input-bordered" type="text" name="title" placeholder="제목을 입력해주세요"" /></td>
		</tr>
		
		<tr> 
			<th>내용</th>
			<td><textarea class="w-full input input-bordered" rows="10" name="body" placeholder="내용을 입력해주세요" /></textarea></td>
		</tr>
		


		<tr>
			<th></th>
			<td>
			<button type="submit" class="btn btn-outline btn-primary">저장</button>
			</td>
			</tr>	
		
	</tbody>
</table>
	</form>
		<div class="btns mt-5">
			 <button class="btn btn-link" type="button" onclick="history.back();">뒤로가기</button>
      		<button class="btn btn-link" type="button" onclick="location.href='../home/main' ">HOME</button>
		</div>
		</div>
</section>
<%@ include file="../common/foot.jspf"%>
	