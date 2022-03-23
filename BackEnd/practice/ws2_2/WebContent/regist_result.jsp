<%@ page language="java" contentType="text/html; charset=UTF-8" %>

	<!-- DTO를 참조하기 위해서 import 처리가 필요하다. -->
	<%@ page import="com.ssafy.backend.dto.*" %>
		<!DOCTYPE html>
		<html>

		<head>
			<meta http-equiv="Cache-Control" content="no-cache" />
			<title>영화 등록 결과</title>
		</head>

		<body>
			<h1>영화 등록 결과</h1>
			<h2>등록된 영화 정보</h2>
			<table>
				<thead>
					<tr>
						<th>항목</th>
						<th>내용</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>제목</td>
						<td>
							<%=request.getAttribute("title")%>
						</td>
					</tr>
					<tr>
						<td>감독</td>
						<td>
							<%=request.getAttribute("director")%>
						</td>
					</tr>
					<tr>
						<td>장르</td>
						<td>
							<%=request.getAttribute("genre")%>
						</td>
					</tr>
					<tr>
						<td>상영시간</td>
						<td>
							<%=request.getAttribute("runtime")%>
						</td>
					</tr>
				</tbody>
			</table>
			<!-- 다시 사용자를 등록할 수 있는 링크를 제공한다. -->
			<a href="regist.html">추가등록</a>
		</body>

		</html>