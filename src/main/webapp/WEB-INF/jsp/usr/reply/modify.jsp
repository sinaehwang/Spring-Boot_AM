<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="REPLY MODIFY" />
<%@ include file="../common/head.jspf"%>

<script>
  
  let ReplyModify__submitFormDone = false; //중복전송방지위해 false로 변수 선언
  function ReplyModify__submitForm(form) {
    
    if(ReplyModify__submitFormDone){//중복전송이 true로 바껴있다면 아무것도 리턴하지않아 중복발송을 막아줌
      return;
    }
    
    form.body.value = form.body.value.trim();
    
    if(form.body.value.length==0) {
      alert('댓글내용을 입력해주세요');
      form.body.focus();//하이라이팅효과
      return;
    }
    
    ReplyModify__submitFormDone =true;
    form.submit(); //폼전송실행
  }

</script>

<section class="mt-8 text-xl">
	<div class="container mx-auto px-3">
		<form class="table-box-type-1" method="POST" action="../reply/doModify" onsubmit ="ReplyModify__submitForm(this); return false;">
            <input type="hidden" name="replaceUri" value="${param.replaceUri }" />
            <input type="hidden" name="id" value="${reply.id }" />
            <input type="hidden" name="relTypeCode" value="${reply.relTypeCode }" />
            <input type="hidden" name=relId value="${reply.relId }" />
			<table class="table table-zebra w-full">
				<colgroup>
					<col width="200" />
				</colgroup>

				<tbody>
                    <tr>
                        <th>게시판번호</th>
                        <td><div class="badge">${reply.relId }</div></td>
                    </tr>
        
					<tr>
						<th>댓글번호</th>
						<td><div class="badge">${reply.id }</div></td>
					</tr>
					<tr>
						<th>작성날짜</th>
						<td>${reply.regDate }</td>
					</tr>
					<tr>
						<th>수정날짜</th>
						<td>${reply.updateDate }</td>
					</tr>
					<tr>
						<th>작성자</th>
						<td>${reply.extra__writerName }</td>
					</tr>
                    <tr>
                          <th>추천수</th>
                          <td>${reply.goodReactionPoint }</td> 
                    </tr>
					<tr>
						<th>내용</th>
						<td><textarea class="textarea textarea-bordered w-full" type="text" name="body" placeholder="내용을 입력해주세요" >${reply.body }</textarea></td>
					</tr>
					<tr>
						<th>댓글수정</th>
						<td><button class="btn btn-active btn-ghost" type="submit" value="수정" >수정
							</button></td>
					</tr>
				</tbody>

			</table>
		</form>

		<div class="btns">
			<a class="btn-text-link btn btn-active btn-ghost" href = "${param.replaceUri}">뒤로가기</a>

		</div>
	</div>
</section>
<%@ include file="../common/foot.jspf"%>