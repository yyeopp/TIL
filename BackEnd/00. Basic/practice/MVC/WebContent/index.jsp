<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/include/header.jsp"%>

<div>
	<c:if test="${empty userInfo}">
		<form action="${root}/user" method="post">
			<input type="hidden" name="action" value="login"> <input
				type="text" id="id" name="id" placeholder="아이디"> <input
				type="password" id="pass" name="pass" placeholder="비밀번호"> <input
				type="submit" value="로그인">
		</form>
	</c:if>
	<c:if test="${!empty userInfo}">
		${userInfo.name}(${userInfo.id})님, 환영합니다.
		<a href="${root }/user?action=logout">로그아웃</a>
	</c:if>
</div>
<div>
	<c:if test="${!empty userInfo}">
		<ul>
			<li><a href="${root }/book?action=mvregist">도서 등록!!</a>
		</ul>
	</c:if>
</div>
</body>

</html>