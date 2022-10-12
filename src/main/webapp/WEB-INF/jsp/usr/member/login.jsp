<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="로그인" />
<%@ include file="../common/head.jspf"%>

<section class="mt-8">
	<div class="container mx-auto px-3">
	<form class ="table-box-type-1" action="../usr/member/doLogin" method="POST">
	<table>
			<colgroup>
				<col width="200" />
			</colgroup>
		
	<tbody>
		<tr>
			<th>아이디</th>
			<td><input class = "w-96" name = "loginId" type = "text" placeholder ="아이디를 입력해주세요"></input></td>
		</tr>
		
		<tr>
			<th>비밀번호</th>
			<td><input class = "w-96" name = "loginPw" type = "text" placeholder ="비밀번호를 입력해주세요"></input></td>
		</tr>
		
		<tr>
        <th>로그인</th>
        <td>
          <input type ="submit" value ="로그인" />
          <button type = "button" onclick="history.back();"></button> //여기부터 다시 체크하기
        </td>
      </tr>
	</tbody>
</table>
	</form>
		<div class="btns mt-5">
			<button type="button" onclick="history.back();">뒤로가기</button>
			<c:if test="${article.extra__actorCanDelete }">
				<a class="btn-text-link ml-5" onclick="if(confirm('정말 삭제하시겠습니까?') == false) return false;"
					href="../article/doDelete?id=${article.id }"
				>삭제</a>
			</c:if>
		</div>
		</div>
</section>




<%@ include file="../common/foot.jspf"%>