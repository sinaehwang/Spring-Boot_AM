<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="회원리스트" />
<%@ include file="../common/head.jspf"%>

<section class="mt-8 text-xl">
	<div class="container mx-auto px-3">
		<div class="flex">
			<div>
				전체 회원수 : <span class="badge">${memberCount }개</span>
			</div>
			<div class="flex-grow"></div>
			<form class="flex">

				<select data-value="${param.searchKeywordTypeCode }" name="searchKeywordTypeCode" class="select select-bordered">
					<option disabled="disabled">검색</option>
					<option value="title">제목</option>
					<option value="body">내용</option>
					<option value="title,body">제목 + 내용</option>
				</select>


				<input name="searchKeyword" type="text" class="ml-2 w-96 input input-borderd" placeholder="검색어를 입력해주세요"
					maxlength="20" value="${param.searchKeyword }"
				/>
				<button type="submit" class="ml-2 btn btn-ghost">검색</button>
			</form>
		</div>
		<div class="table-box-type-1 mt-3">
			<table class="table table-fixed w-full">
				<colgroup>
					<col width="80" />
					<col width="150" />
					<col />
					<col width="200" />
                    <col width="80" />
                    <col width="80" />
				</colgroup>
				<thead>
					<tr>
						<th>번호</th>
						<th>회원아이디</th>
						<th>회원이름</th>
						<th>가입일</th>
					</tr>
				</thead>

				<tbody>
					<c:forEach var="member" items="${members }">
						<tr class="hover">
							<td>${member.id}</td>
							<td>${member.loginId}</td>
							<td>${member.name}</td>
							<td>${member.regDate}</td>
							


						</tr>
					</c:forEach>
				</tbody>

			</table>
		</div>

	</div>
</section>
<%@ include file="../common/foot.jspf"%>