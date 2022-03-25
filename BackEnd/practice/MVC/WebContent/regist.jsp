<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<%@ include file="/include/header.jsp"%>
	<form method="post" action="{root}/book">
		<fieldset>
			<legend>도서 등록</legend>
			<input type="hidden" name="action" value="regist"> <label
				for="isbn">도서번호</label> <input type="text" id="isbn" name="isbn"><br>
			<label for="title">도서명</label> <input type="text" id="title"
				name="title"><br> <label for="author">저자</label> <input
				type="text" id="author" name="author"><br> <label
				for="price">가격</label> <input type="number" id="price" name="price"><br>
			<label for="desc">설명</label> <input type="text" id="desc" name="desc"><br>
			<input type="submit" value="등록"> <input type="reset"
				value="초기화">

		</fieldset>

	</form>
</body>

</html>

