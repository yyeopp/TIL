<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<h1>도서 등록 결과</h1>
<table>
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
			<td>${book.content }</td>
		</tr>
		<c:if test="${!empty book.img}">
			<tr>
				<td>${book.img.originFile }<a href="#" class="filedown"
					sfolder="${book.img.saveFolder }" sfile="${book.img.saveFile }"
					ofile="${book.img.originFile }">[다운로드]</a>
				</td>
			</tr>
		</c:if>
	</tbody>
</table>
<!-- 다시 사용자를 등록할 수 있는 링크를 제공한다. -->
<a href="${root}/book/regist">추가등록</a>
<a href="${root}/book/list">도서목록</a>

</body>
</html>
