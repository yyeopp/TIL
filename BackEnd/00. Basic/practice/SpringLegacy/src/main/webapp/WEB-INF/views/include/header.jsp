<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set value="${pageContext.request.contextPath }" var="root"></c:set>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>SSAFY 도서 관리</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
</head>

<body>
	<h1 id="title">SSAFY 도서 관리</h1>
	<div>
		<c:if test="${empty userInfo}">
			<form action="${root}/user/login" method="post">
				<input type="hidden" name="action" value="login"> <input
					type="text" id="id" name="id" placeholder="아이디"> <input
					type="password" id="pass" name="pass" placeholder="비밀번호"> <input
					type="submit" value="로그인">
			</form>
		</c:if>
		<c:if test="${!empty userInfo}">
		${userInfo.name}(${userInfo.id})님, 환영합니다.
		<a href="${root }/user/logout">로그아웃</a>
		</c:if>
	</div>