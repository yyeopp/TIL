<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<%@ include file="/include/header.jsp"%>
<c:if test="${empty userInfo }">
	<script>
		alert("로그인한 사용자만 볼 수 있는 페이지입니다.");
		location.href = "${root}/user?act=mvlogin";
	</script>
</c:if>
<c:if test="${!empty userInfo }">
	<h1>도서 등록 결과</h1>
	<h2>등록 도서 정보</h2>
	<table>
		<c:if test="${!empty books}">
			<c:forEach var="book" items="${books}">
				<thead>
					<tr>
						<th>항목</th>
						<th>내용</th>
					</tr>
				</thead>

				<tbody>
					<tr>
						<td>도서번호</td>
						<td>${book.isbn}</td>
					</tr>
					<tr>
						<td>도서명</td>
						<td>${book.title }</td>
					</tr>
					<tr>
						<td>저자</td>
						<td>${book.author }</td>
					</tr>
					<tr>
						<td>가격</td>
						<td>${book.price }</td>
					</tr>
					<tr>
						<td>설명</td>
						<td>${book.desc }</td>
					</tr>
				</tbody>
			</c:forEach>
		</c:if>
		<c:if test="${empty books}">
			<h3>등록된 정보가 없습니다.</h3>
		</c:if>
	</table>
	<!-- 다시 사용자를 등록할 수 있는 링크를 제공한다. -->
	<a href="${root}/book?action=mvregist">추가등록</a>
</c:if>
</body>
</html>
