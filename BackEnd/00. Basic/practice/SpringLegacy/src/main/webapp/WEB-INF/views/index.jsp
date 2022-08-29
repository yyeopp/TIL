<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>


<div>

	<ul>
		<c:if test="${!empty userInfo}">
			<li><a href="${root }/book/regist">도서 등록!!</a>
		</c:if>
		<li><a href="${root }/book/list">도서 목록</a>
	</ul>


</div>
</body>

</html>