<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/include/header.jsp"%>

<div>
	<form action="${root}/user" method="post">
		<input type="hidden" name="action" value="login"> <input
			type="text" id="userid" name="userid" placeholder="아이디"> <input
			type="password" id="userpwd" name="userpwd" placeholder="비밀번호">
		<input type="submit" value="로그인">
	</form>
</div>
<div>
	<ul>
		<li><a href="./regist.jsp">도서 등록!!</a>
	</ul>
</div>
</body>

</html>