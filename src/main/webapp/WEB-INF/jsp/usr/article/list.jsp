<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="${board.name} 게시판" />
<%@ include file="../common/head.jspf"%>

<section class="mt-8">
	<div class="container mx-auto px-3">
	<div class = "flex">
		<div>총 게시글 : <span class = "badge">${TotalCount }개</span></div>
		<div class ="flex-grow"></div>
		<form class= "flex" >
			<input type="hidden" name="boardId" value="${param.boardId }" />
		
			<select data-value = "${param.TypeCode }" name="TypeCode" class = "select select=bordered">
				<option disabled="disabled">검색</option>
				<option value="title">제목</option>
				<option value="body">내용</option>
				<option value="title,body">제목+내용</option>
			</select>
			
			<input name="searchkeyword" type="text"
			class="ml-2 w-96 input input-bordered"
          	placeholder="검색어를 입력해주세요" maxlength="20"
          	value="${param.searchkeyword }" />

          <button class="ml-3 btn btn-outline btn-ghost" type="submit"> 검색</button>
          
		</form>
	</div>
	
	
	
	<div class ="table-box-type-1 mt-5">
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
	
	<div class = "page-menu mt-5 flex justify-center ">
	<div class="btn-group">
		<c:set var="pageMenuLen" value="4"/>
		<c:set var="startPage" 
		value="${page - pageMenuLen >=1 ? page - pageMenuLen : 1}"/>
		<c:set var="endPage" 
		value="${page + pageMenuLen <=PageConunt ? page + pageMenuLen : PageConunt}"/>
		
		<c:set var="pageBaseUri" value="?boarId=${boardId }" />
        <c:set var="pageBaseUri"
          value="${pageBaseUri }&TypeCode=${param.TypeCode}" />
        <c:set var="pageBaseUri"
          value="${pageBaseUri }&searchkeyword=${param.searchkeyword}" />
		
		<c:if test = "${startPage>1}">
			<a class = "btn btn-sm" href="${pageBaseUri }&page=1">1</a>
		</c:if>
		<c:if test = "${startPage>2}">
			<a class = "btn btn-sm btn-disabled">...</a>
		</c:if>
		
		<c:forEach  begin="${startPage}" end ="${endPage}" var ="i" >
			<a class="btn btn-sm ${page == i ? 'btn-active' : ''}" 
			href="${pageBaseUri }&page=${i}">${i}</a><!-- 동일한 url이니까 앞에 local/8081~생략가능, 파라미터값이랑 page값이 같을때만 하이라이팅 -->
  		</c:forEach>
  		<c:if test="${endPage < PageConunt}">
          <c:if test="${endPage < PageConunt - 1}">
            <a class="btn btn-sm btn-disabled">...</a>
          </c:if>
          <a class="btn btn-sm" href="${pageBaseUri }&page=${pagesCount}">${PageConunt }</a>
        </c:if>
  		
	</div>
	</div>


		</div>
</section>
<%@ include file="../common/foot.jspf"%>