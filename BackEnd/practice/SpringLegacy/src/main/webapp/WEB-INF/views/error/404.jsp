<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<c:if test="${empty msg }">
	<h3>404 Error</h3>
</c:if>
<c:if test="${!empty msg }">
	<h3>${msg }</h3>
</c:if>
<div>
	<a href="${root }/">홈 화면으로 돌아가기</a>
</div>
</body>
</html>