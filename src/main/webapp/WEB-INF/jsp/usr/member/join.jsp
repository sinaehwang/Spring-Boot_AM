<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="회원가입" />
<%@ include file="../common/head.jspf"%>
<%@ page import="com.hsn.exam.demo.util.Ut"%>


<!-- 아이디 중복체크 ajax로직 -->
<script>

const MemberdoJoin__submitFormDone = false;

let JoinForm__validLoginId = "";//중복체크 여부 판단하기 위해서

function JoinForm__checkLoginIdDup(obj) {
	const form = $(obj).closest('form').get(0); //가장가까운 form을 찾아간다
	
	form.loginId.value = form.loginId.value.trim();
	
	if(form.loginId.value.length ==0 ){
	  alert('아이디를 입력해주세요');
	  form.loginId.focus();
	  return;
	}
	
	var url = 'getLoginIdDup?loginId=' + form.loginId.value;
	
	$.get(
		'getLoginIdDup',
		{
		  loginId:form.loginId.value
		},
		function(data){
		  
		  let colorClass = 'text-blue-500';
			if ( data.fail ) {
				colorClass = 'text-red-500';
			}
			
			$('.loginIdInputMsg').html("<span class='" + colorClass + "'>" + data.msg + "</span>");
		
			//alert(data.msg);
		
		  if(data.fail) {
		    form.loginId.focus();
		    JoinForm__validLoginId = '';
		  }
			else {
				JoinForm__validLoginId = data.data1Name;
				form.loginPw.focus();
		  }
		},
		'json'
	);
	
}




  function MemberdoJoin__submitForm(form) {

    if (MemberdoJoin__submitFormDone) {
      return;
    }

    form.loginId.value = form.loginId.value.trim();

    if (form.loginId.value.length == 0) {
      alert('아이디를 입력해주세요.')
      return;
    }
    
	if ( form.loginId.value != JoinForm__validLoginId ) { //실제입력한 id와 중복체크로 전송된 id가 다르다면 다시 중복체크 과정을 거치도록
		alert('로그인아이디 중복체크를해주세요.');
		return;
	}

    form.loginPw.value = form.loginPw.value.trim();

    if (form.loginPw.value.length == 0) {
      alert('비밀번호을 입력해주세요.')
      return;
    }

    if (form.loginPw.value.length > 0) {
      form.loginPwConfirm.value = form.loginPwConfirm.value.trim();

      if (form.loginPwConfirm.value.length == 0) {
        alert('비밀번호확인을 입력해주세요.');
        return;
      }

      if (form.loginPw.value != form.loginPwConfirm.value) {
        alert('비밀번호확인이 일치하지 않습니다.');
        return;
      }
    }

    form.name.value = form.name.value.trim();
    if (form.name.value.length == 0) {
      alert('이름을 입력해주세요');
      return;
    }

    form.nickname.value = form.nickname.value.trim();
    if (form.nickname.value.length == 0) {
      alert('닉네임을 입력해주세요');
      return;
    }

    form.cellphoneNum.value = form.cellphoneNum.value.trim();
    if (form.cellphoneNum.value.length == 0) {
      alert('연락처를 입력해주세요');
      return;
    }

    form.email.value = form.email.value.trim();
    if (form.email.value.length == 0) {
      alert('이메일을 입력해주세요');
      return;
    }

    MemberdoJoin__submitFormDone = true;

    form.submit();
  }
</script>



<section class="mt-8">
  <div class="container mx-auto px-3 table-box-type-1">
    <form class="table-box-type-1"
      onsubmit="MemberdoJoin__submitForm(this); return false;"
      method="POST" action="../member/doJoin">
      <input type="hidden" name = "afterLoginUri"  value = ${param.afterLoginUri }/>
      <table>
        <colgroup>
          <col width="200" />
          
        </colgroup>

        <tbody>

          <tr>
            <th>아이디</th>
            <td>
            <input required="required" class="w-full input input-bordered  max-w-xs" type="text" name=loginId
				placeholder="사용하실 아이디를 입력해주세요"/>
            <input type="button"  onclick="JoinForm__checkLoginIdDup(this);" class="btn btn-outline btn-primary"  value="중복체크"/>
            <div class = "loginIdInputMsg mt-2"></div>
            </td>
          </tr>
          <tr>
            <th>비밀번호</th>
            <td>
            <input required="required" class="w-full input input-bordered  max-w-xs" type="text" name=loginPw
                placeholder="사용하실 비밀번호를 입력해주세요"
              /></td>
          </tr>
          <tr>
            <th>이름</th>
            <td>            
            <input required="required" class="w-full input input-bordered  max-w-xs" type="text" name=name
                placeholder="사용하실 이름을 입력해주세요"
              /></td>
          </tr>
          <tr>
          <tr>
            <th>닉네임</th>
            <td>
            <input required="required" class="w-full input input-bordered  max-w-xs" type="text" name=nickname
                placeholder="사용하실 닉네임을 입력해주세요"
              /></td>
          </tr>
          <tr>
            <th>연락처</th>
            <td>            
            <input required="required" class="w-full input input-bordered  max-w-xs" type="text" name=cellphoneNum
                placeholder="사용하실 연락처를 입력해주세요"
              /></td>
          </tr>
          <tr>
            <th>e-mail</th>
            <td>
            <input required="required" class="w-full input input-bordered  max-w-xs" type="text" name=email
                placeholder="사용하실 이메일을 입력해주세요"
              /></td>
          </tr>

          <tr>
            <th>회원가입</th>
            <td>
              <div class="container mx-auto btns">
                <button class="btn btn-active btn-ghost" type="submit"
                  value="가입하기"
                >가입하기</button>
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