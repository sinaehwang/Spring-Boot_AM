<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="My Page" />
<%@ include file="../common/head.jspf"%>


<section class="mt-8">
	<div class="container mx-auto px-3">
	<form class ="table-box-type-1" onsubmit ="MyPageModify__submitForm(this); return false;" >
    <input type="hidden" name="memberId" value="${member.id }" />
	<table>
			<colgroup>
				<col width="200" />
			</colgroup>
		
	<tbody>
		<tr>
			<th>아이디</th>
			<td>${member.loginId }</td>
		</tr>
        <tr>
            <th>이름</th>
            <td>${member.name }</td>
        </tr>
        <tr>
            <th>연락처</th>
            <td>${member.cellphoneNum }</td>
        </tr>
        <tr>
            <th>e-mail</th>
            <td>${member.email }</td>
        </tr>
        
        <tr>
            <th>닉네임</th>
            <td>${member.nickname }</td>
        </tr>
		
		<tr>
			<th>회원비밀번호변경</th>
			<td>${member.loginPw }</td>
		</tr>
        <tr>
            <th>회원정보 수정
                <td>
                <div class="container mx-auto btns">
                  <button class="btn btn-active btn-ghost" href = "#" >회원정보 수정</button>
                </div>
                </td>
            </th>
        </tr>  
	</tbody>
</table>
	</form>
		</div>
	
	<div class="container mx-auto btns">
		<button class="btn btn-link" type="button" onclick="history.back();">뒤로가기</button>
	</div>
	
</section>

<%@ include file="../common/foot.jspf"%>