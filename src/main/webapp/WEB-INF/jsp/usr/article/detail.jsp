<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="ARTICLE DETAIL" />
<%@ include file="../common/head.jspf"%>
<%@ include file="../common/toastUIEditorLib.jspf"%>
<script>
	const params = {};
	params.id = parseInt('${param.id}');
</script>

<script>
	function ArticleDetail__increaseHitCount() {
		const localStorageKey = 'article__' + params.id + '__alreadyView';
		if (localStorage.getItem(localStorageKey)) {
			return;
		}
		localStorage.setItem(localStorageKey, true);
		$.get('../article/doIncreaseHitCountRd', {
			id : params.id,
			ajaxMode : 'Y'
		}, function(data) {
			$('.article-detail__hit-count').empty().html(data.data1);
		}, 'json');
	}
	$(function() {
		// 실전코드
		//ArticleDetail__increaseHitCount();
		// 연습코드
		setTimeout(ArticleDetail__increaseHitCount, 2000);
	})
</script>


<section class="mt-8 text-xl">
	<div class="container mx-auto px-3">
		<div class="table-box-type-1">
			<table>
				<colgroup>
					<col width="200" />
				</colgroup>
				<tbody>
					<tr>
						<th>번호</th>
						<td>
							<div class="badge">${article.id }</div>
						</td>
					</tr>
					<tr>
						<th>작성날짜</th>
						<td>${article.regDate }</td>
					</tr>
					<tr>
						<th>수정날짜</th>
						<td>${article.updateDate }</td>
					</tr>
					<tr>
						<th>조회수</th>
						<td>
							<span class="badge article-detail__hit-count">${article.hitCount }</span>
						</td>
					</tr>
					<tr>
						<th>작성자</th>
						<td>${article.extra__writerName }</td>
					</tr>
					<tr>
						<th>추천</th>
						<td>
								<span class="badge">${article.goodReactionPoint }</span>
                                <c:if test="${actorCanMakeReaction}">
                                      <span>&nbsp;</span>
                                      <a href="/usr/reactionPoint/doGoodReaction?relTypeCode=article&relId=${param.id}&replaceUri=${rq.encodedCurrentUri}" class = "btn btn-xs " > 좋아요💖${article.goodReactionPoint }</a>
                                      <span>&nbsp;</span>
                                      <a href="/usr/reactionPoint/doBadReaction?relTypeCode=article&relId=${param.id}&replaceUri=${rq.encodedCurrentUri}" class = "btn btn-xs ">싫어요🤍${article.badReactionPoint }</a>
                                </c:if>
                                
                                <c:if test="${actorCanCancelGoodReaction}">
                                      <span>&nbsp;</span>
                                      <a href="/usr/reactionPoint/CancleGoodReaction?relTypeCode=article&relId=${param.id}&replaceUri=${rq.encodedCurrentUri}" class = "btn btn-accent btn-xs"> 좋아요💖${article.goodReactionPoint }</a>
                                      <span>&nbsp;</span>
                                      <a onclick = "alert(this.title); return false;" title = "좋아요를 먼저 취소해주세요" href="#" class = "btn btn-xs">싫어요🤍${article.badReactionPoint }</a>
                                </c:if>
                                
                               <c:if test="${actorCanCancelBadReaction}">
                                      <span>&nbsp;</span>
                                      <a onclick = "alert(this.title); return false;" title = "싫어요를 먼저 취소해주세요" href="#" class = "btn btn-xs"> 좋아요💖${article.goodReactionPoint }</a>
                                      <span>&nbsp;</span>
                                      <a href="/usr/reactionPoint/CanCancelBadReaction?relTypeCode=article&relId=${param.id}&replaceUri=${rq.encodedCurrentUri}" class = "btn btn-accent btn-xs ">싫어요🤍${article.badReactionPoint }</a>
                                </c:if>
                                
						</td>
					</tr>
					<tr>
						<th>제목</th>
						<td>${article.title }</td>
					</tr>
					<tr>
            <th>내용</th>
            <td>
              <div class="toast-ui-viewer">
                <script type="text/x-template">
${article.body}
				</script>
              </div>
            </td>
          </tr>
				</tbody>
			</table>
		</div>
        <br />
		<div class="btns">
        
        <c:if test="${empty param.listUri }"><!-- listUri파라미터가 없다면 기존과동일한 뒤로가기형태 -->
			<button class="btn-text-link btn btn-active btn-ghost" type="button" onclick="history.back();">뒤로가기</button>
        </c:if>
        
        <c:if test="${not empty param.listUri }"><!-- listUri파라미터가 있다면 listUri를 가지고가도록만듬 -->
             <a class="btn-text-link btn btn-active btn-ghost" href="${param.listUri}">뒤로가기</a>
        </c:if>
			<c:if test="${article.extra__actorCanModify }">
				<a class="btn-text-link btn btn-active btn-ghost" href="../article/modify?id=${article.id }">수정</a>
			</c:if>
			<c:if test="${article.extra__actorCanDelete }">
				<a class="btn-text-link btn btn-active btn-ghost" onclick="if(confirm('정말 삭제하시겠습니까?') == false) return false;"
					href="../article/doDelete?id=${article.id }"
				>삭제</a>
			</c:if>
		</div>
<!-- 댓글작성시작 -->    
        <div class="card w-full bg-base-100 shadow-xl mt-8 px-3 ">
        <div>
            <div>댓글 작성</div>
            <c:if test="${rq.logined }">
                <div class="card-body">
                <form class="table-box-type-1" method="POST" action="../reply/doWriteReply" onsubmit="ReplyWrite__submitForm(this); return false;"> <!-- 중복전송,내용미입력체크스크립트 -->
                <input type="hidden" name="replaceUri" value="${rq.currentUri }" /> <!-- 해당detailUri값에 usr/detail/id 값이 남아지게됨 -->
                <input type="hidden" name="relTypeCode" value="article" />
                <input type="hidden" name="relId" value="${article.id }" />
                  <h2 class="card-title">댓글💬</h2>
                  <p><textarea class = "w-full input input-bordered" name = "body" placeholder="댓글을 입력해주세요" rows="1" ></textarea></p>
                  <div class="card-actions justify-end">
                    <button class="btn btn-primary" type="submit" value="등록" >등록</button>
                  </div>
               </form>
               </div>
          </c:if>
          <c:if test="${rq.notLogined }">
              <a class="btn-text-link btn  btn-ghost" href="${rq.loginUri }">로그인</a> 후 이용해주세요
           </c:if>
           </div>
<!-- 댓글리스트시작 -->
              <div class="container mx-auto">
                <h2>댓글 리스트(${repliesCount })</h2>
                <table class="table table-fixed w-full">
                  <colgroup>
                    <col width="30" />
                    <col width="100" />
                    <col width="100" />
                    <col width="30" />
                    <col width="140" />
                    <col width="30" />
                    <col width="30" />
                  </colgroup>
                  <thead>
                    <tr>
                      <th>번호</th>
                      <th>날짜</th>
                      <th>작성자</th>
                      <th>추천</th>
                      <th>내용</th>
                      <th>수정</th>
                      <th>삭제</th>
                    </tr>
                  </thead>
            
                  <tbody>
                    <c:forEach var="reply" items="${replies }" varStatus="status"> <!--게시판별로 댓글번호를 1부터 정의해서 사용하기  -->
                      <tr class="hover">
                        <td>${status.count}</td>
                        <td>${reply.regDate}</td>
                        <td>${reply.extra__writerName}</td>
                        <td>${reply.goodReactionPoint}</td>
                        <td class="text-left">${reply.getForPrintBody()}</td>
                        <td><a href="../reply/modify?id=${reply.id }&replaceUri=${rq.encodedCurrentUri}" class="btn btn-xs">수정</a></td>
                        <td><a href="../reply/doDelete?id=${reply.id }&replaceUri=${rq.encodedCurrentUri}" onclick="if(confirm('정말 삭제하시겠습니까?') == false) return false;" class="btn btn-xs">삭제</a></td>
                      </tr>
                    </c:forEach>
                  </tbody>
            
                </table>
            
              </div>
<!-- 댓글리스트끝 -->               
        </div>
<!-- 댓글작성끝 -->     
    </div>
</section>


<%@ include file="../common/foot.jspf"%>