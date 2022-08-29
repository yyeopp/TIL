<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<%@ include file="/WEB-INF/views/include/header.jsp"%>
<c:if test="${empty userInfo }">
	<script>
		alert("로그인한 사용자만 볼 수 있는 페이지입니다.");
		location.href = "${root}/book/list";
	</script>
</c:if>

<c:if test="${!empty userInfo }">
	<p>도서번호</p>
	<p>${book.isbn }</p>
	<form method="post" action="${root}/book/update" enctype="multipart/form-data">
		<fieldset>
			<legend>도서정보 수정</legend>
			<input type="hidden" name="isbn" value="${book.isbn }"> 
			<label for="title">제목</label> <input type="text" id="title"	name="title" value="${book.title }"><br> 
			<label for="author">저자</label> <input type="text" id="author" name="author" value="${book.author}"><br> 
			<label for="price">가격</label> <input type="number" id="price" name="price" value="${book.price }"><br>
			<label for="content">설명</label> <input type="text" id="content" name="content" value="${book.content }"><br>
			<input type="submit" id="update" value="수정"> <input type="reset" value="초기화">

		</fieldset>

	</form>
</c:if>
</body>

</html>

