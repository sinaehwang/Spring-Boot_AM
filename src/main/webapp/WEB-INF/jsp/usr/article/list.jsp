<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="${board.name} 게시판" />
<%@ include file="../common/head.jspf"%>

<section class="mt-8">
	<div class="container mx-auto px-3">
	<div>총 게시글 :${TotalCount }개</div>
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
	
	<div class = "page-menu mt-5 flex justify-center ">
	<div class="btn-group">
		<c:set var="pageMenuLen" value="4"/>
		<c:set var="startPage" 
		value="${page - pageMenuLen >=1 ? page - pageMenuLen : 1}"/>
		<c:set var="endPage" 
		value="${page + pageMenuLen <=PageConunt ? page + pageMenuLen : PageConunt}"/>
		<c:if test = "${startPage>1}">
			<a class = "btn btn-sm" href="?page=1&boardId=${boardId }">1</a>
		</c:if>
		<c:if test = "${startPage>2}">
			<a class = "btn btn-sm btn-disabled">...</a>
		</c:if>
		
		<c:forEach  begin="${startPage}" end ="${endPage}" var ="i" >
			<a class="btn btn-sm ${page == i ? 'btn-active' : ''}" href="?page=${i}&boardId=${boardId }">${i}</a><!-- 동일한 url이니까 앞에 local/8081~생략가능, 파라미터값이랑 page값이 같을때만 하이라이팅 -->
  		</c:forEach>
  		<c:if test="${endPage < PageConunt}">
          <c:if test="${endPage < PageConunt - 1}">
            <a class="btn btn-sm btn-disabled">...</a>
          </c:if>
          <a class="btn btn-sm" href="?page=${PageConunt }&boardId=${boardId }">${PageConunt }</a>
        </c:if>
  		
	</div>
	</div>


		</div>
</section>
<%@ include file="../common/foot.jspf"%>