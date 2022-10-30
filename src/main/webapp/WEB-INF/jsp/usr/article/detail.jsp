<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="ARTICLE DETAIL" />
<%@ include file="../common/head.jspf"%>
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
						<td>${article.body }</td>
					</tr>
				</tbody>
			</table>
		</div>
        <br />
		<div class="btns">
			<button class="btn-text-link btn btn-active btn-ghost" type="button" onclick="history.back();">뒤로가기</button>
			<c:if test="${article.extra__actorCanModify }">
				<a class="btn-text-link btn btn-active btn-ghost" href="../article/modify?id=${article.id }">수정</a>
			</c:if>
			<c:if test="${article.extra__actorCanDelete }">
				<a class="btn-text-link btn btn-active btn-ghost" onclick="if(confirm('정말 삭제하시겠습니까?') == false) return false;"
					href="../article/doDelete?id=${article.id }"
				>삭제</a>
			</c:if>
		</div>
    
    <!-- 댓글구현시작 -->
    
    <div class="card w-full bg-base-100 shadow-xl">
      <div class="card-body">
        <h2 class="card-title">댓글💬</h2>
        <p><textarea class = "w-full input input-bordered" placeholder="댓글을 입력해주세요" rows="1"></textarea></p>
        <div class="card-actions justify-end">
          <button class="btn btn-primary">등록</button>
        </div>
        <br />
        <h2 class="card-title w-full">댓글리스트</h2>
          <ul id = "comment--box" class="list-group">
                <li id = "comment--1" class="list-group-item flex justify-between">
                    <div>댓글내용1</div>
                    <div class = "flex content-center">
                      <div>작성자:아무개 &nbsp; 작성일:2022</div>
                      <button class="btn badge-xs mx-3 mb-3"> 수정</button>
                      <button class="btn badge-xs mb-3"> 삭제</button>
                   </div>
                </li>
                <li class="list-group-item flex justify-between">
                    <div>댓글내용1</div>
                    <div class = "flex content-center">
                      <div>작성자:아무개 &nbsp; 작성일:2022</div>
                      <button class="btn badge-xs mx-3 mb-3"> 수정</button>
                      <button class="btn badge-xs mb-3"> 삭제</button>
                   </div>
                </li>
          </ul>
      </div>
    </div>

    

    
    
    
    
    <!-- 댓글구현끝 --> 
	</div>
</section>
<!-- <iframe src="http://localhost:8081/usr/article/doIncreaseHitCountRd?id=1" frameborder="0"></iframe> -->
<%@ include file="../common/foot.jspf"%>