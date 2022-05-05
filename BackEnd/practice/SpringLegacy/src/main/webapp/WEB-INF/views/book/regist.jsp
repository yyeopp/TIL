<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<%@ include file="/WEB-INF/views/include/header.jsp"%>
<c:if test="${empty userInfo }">
	<script>
		alert("로그인한 사용자만 볼 수 있는 페이지입니다.");
		location.href = "${root}/book/list";
	</script>
</c:if>

<c:if test="${!empty userInfo }">
	<form method="post" action="${root}/book/regist" enctype="multipart/form-data">
		<fieldset>
			<legend>도서 등록</legend>
			<input type="hidden" name="action" value="regist"> <label
				for="isbn">도서번호</label> <input type="text" id="isbn" name="isbn"><br>
			<label for="title">도서명</label> <input type="text" id="title"
				name="title"><br> <label for="author">저자</label> <input
				type="text" id="author" name="author"><br> <label
				for="price">가격</label> <input type="number" id="price" name="price"><br>
			<label for="content">설명</label> <input type="text" id="content" name="content"><br>
			<label for="upimg">이미지</label> <input type="file" id="img" name="upimg"><br>
			<input type="submit" id="register" value="등록"> <input
				type="reset" value="초기화">

		</fieldset>

	</form>
</c:if>
</body>

</html>

