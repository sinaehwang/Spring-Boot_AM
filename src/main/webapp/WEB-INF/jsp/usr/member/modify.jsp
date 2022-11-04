<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="회원정보 수정" />
<%@ include file="../common/head.jspf"%>
<%@ page import="com.hsn.exam.demo.util.Ut"%>

<script>
	let MemberdoModify__submitFormDone = false;

	function MemberdoModify__submitForm(form) {
	  
	  if(MemberdoModify__submitFormDone) {
	    return ;
	  }
	  
  	  form.loginId.value = form.loginId.value.trim();

  	  if(form.loginId.value.length == 0) {
  	    alert('아이디를 입력해주세요');
  	    return ;
  	  }

  	  form.loginPw.value = form.loginPw.value.trim();
  	  
  	  if(form.loginPw.value.length==0) {
  	    alert('비밀번호을 입력해주세요.')
        return;
  	  }
      
      if (form.loginPw.value.length > 0) {
        form.loginPwConfirm.value = form.loginPwConfirm.value.trim();
        
        if (form.loginPwConfirm.value.length == 0) {
          alert('비밀번호확인을 입력해주세요.')
          return;
        }
        
        if (form.loginPw.value != form.loginPwConfirm.value) {
          alert('비밀번호확인이 일치하지 않습니다.');
          return;
        }
      }
  	  
	  form.name.value = form.name.value.trim();
	  if(form.name.value.length == 0) {
	    alert('이름을 입력해주세요');
	    return ;
	  }
	  
	  form.nickname.value = form.nickname.value.trim();
	  if(form.nickname.value.length == 0) {
	    alert('닉네임을 입력해주세요');
	    return ;
	  }
	  
	  form.cellphoneNum.value = form.cellphoneNum.value.trim();
	  if(form.cellphoneNum.value.length == 0) {
	    alert('연락처를 입력해주세요');
	    return ;
	  }
	  
	  form.email.value = form.email.value.trim();
	  if(form.email.value.length == 0) {
	    alert('이메일을 입력해주세요');
	    return ;
	  }

	  MemberdoModify__submitFormDone = true;

	  form.submit();
  }	


</script>



<section class="mt-8">
	<div class="container mx-auto px-3 ">
    <form class ="table-box-type-1"  onsubmit="MemberdoModify__submitForm(this); return false;" method="POST" action="../member/doModify">
  	<table>
  			<colgroup>
  				<col width="200" />
  			</colgroup>
  		
  	<tbody>
  
  		<tr>
  			<th>아이디</th>
  			<td>
                <input class = "w-96 input input-bordered" name = "loginId" type = "text" placeholder="변경할 아이디를 입력해주세요">
                <a href="" class = "btn btn-xs">중복체크</a>
            </td>
  		</tr>
        <tr>
        <th>비밀번호</th>
        <td>
                <input class = "w-96 input input-bordered" name = "loginPw" type = "password" placeholder="변경할 비밀번호를 입력해주세요">
            </td>
      </tr>
      <tr>
        <th>비밀번호재입력</th>
        <td>
                <input class = "w-96 input input-bordered" name = "loginPwConfirm" type = "password" placeholder="비밀번호를 재입력해주세요">
            </td>
      </tr>
          <tr>
              <th>이름</th>
              <td>
                  <input class = "w-96 input input-bordered" name = "name" type = "text" placeholder="변경할 이름을 입력해주세요">
              </td>
          </tr>
          
          <tr>
              <th>닉네임</th>
              <td>
                <input class = "w-96 input input-bordered" name = "nickname" type = "text" placeholder="변경할 닉네임을 입력해주세요">
              </td>
          </tr>
          <tr>
              <th>연락처</th>
              <td>
                <input class = "w-96 input input-bordered" name = "cellphoneNum" type = "text" placeholder="변경할 연락처를 입력해주세요">
              </td>
          </tr>
          <tr>
              <th>e-mail</th>
              <td>
                <input class = "w-96 input input-bordered" name = "email" type = "text" placeholder="변경할 이메일을 입력해주세요">
              </td>
          </tr>

          <tr>
              <th>회원정보 수정</th>
                  <td>
                  <div class="container mx-auto btns">
                      <button class="btn btn-active btn-ghost" type="submit" value="수정완료" >수정완료</button>
                  </div>
                  </td>
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