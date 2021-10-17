<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="board">
				<form id="search_form" action="${pageContext.request.contextPath }/board" method="post">
					<input type="text" id="kwd" name="kwd" value="">
					<input type="submit" value="찾기">
				</form>
				
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th style="text-align:left">제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>
					
					<c:set var='count' value='${fn:length(list) }'/>
					<c:forEach items='${list }' var='vo' varStatus='status'>				
					<tr>
						<td>[${count-status.index }]</td>
						<td style="text-align:left; padding-left:${10*vo.depth }px">
							<a href="<c:if test="${vo.title != '삭제된 메세지 입니다.' && vo.contents != '삭제' }">
							${pageContext.request.contextPath }/board?a=view&no=${vo.no }</c:if>">${vo.title }</a>
						</td>
						<td>${vo.userName }</td>
						<td>${vo.hit }</td>
						<td>${vo.regDate }</td>
						<td>
						<a class="<c:if test="${vo.title == '삭제된 메세지 입니다.' && vo.contents == '삭제' }">del</c:if>"
						 href="<c:if test="${vo.title != '삭제된 메세지 입니다.' && vo.contents != '삭제' }">
						 ${pageContext.request.contextPath }/board?a=deleteform&no=${vo.no }</c:if>">
						 삭제</a>
						</td>
					</tr>
					</c:forEach>
				</table>
				
				
				<!-- pager 추가 -->
				<div class="pager">
					<ul>
						<li>
							<a <c:if test="${pageVo.prevPage >= 1 }">href="${pageContext.request.contextPath }/board?pState=prev"</c:if>>
								◀
							</a>
						</li>
						<c:forEach var="i" begin="${pageVo.startPage }" end="${pageVo.endPage }">
						<li class="<c:if test="${pageVo.pIndex == i }">selected</c:if>">
							<a <c:if test="${pageVo.ablepIndex >= i }">href="${pageContext.request.contextPath }/board?pState=${i }"</c:if>>
								${i }
							</a>
						</li>
						</c:forEach>
						<li>
							<a <c:if test="${pageVo.ablepIndex >= pageVo.nextPage }">href="${pageContext.request.contextPath }/board?pState=next"</c:if>>
								▶
							</a>
						</li>
					</ul>
				</div>					
				<!-- pager 추가 -->
				
				
				<div class="bottom">
					<a href="${pageContext.request.contextPath }/board?a=writeform" id="new-book">글쓰기</a>
				</div>				
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>