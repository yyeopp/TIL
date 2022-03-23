<%@ page language="java" contentType="text/html; charset=UTF-8" %>

	<!-- DTO를 참조하기 위해서 import 처리가 필요하다. -->
	<%@ page import="com.ssafy.backend.dto.*" %>
		<!DOCTYPE html>
		<html>

		<head>
			<meta http-equiv="Cache-Control" content="no-cache" />
			<title>Hello! SSAFY</title>
		</head>

		<body>
			<h1>도서 등록 결과</h1>
			<h2>등록 도서 정보</h2>
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
						<td>
							<%=request.getAttribute("isbn")%>
						</td>
					</tr>
					<tr>
						<td>도서명</td>
						<td>
							<%=request.getAttribute("title")%>
						</td>
					</tr>
					<tr>
						<td>저자</td>
						<td>
							<%=request.getAttribute("author")%>
						</td>
					</tr>
					<tr>
						<td>가격</td>
						<td>
							<%=request.getAttribute("price")%>
						</td>
					</tr>
					<tr>
						<td>설명</td>
						<td>
							<%=request.getAttribute("desc")%>
						</td>
					</tr>
				</tbody>
			</table>
			<!-- 다시 사용자를 등록할 수 있는 링크를 제공한다. -->
			<a href="regist.html">추가등록</a>
		</body>

		</html>