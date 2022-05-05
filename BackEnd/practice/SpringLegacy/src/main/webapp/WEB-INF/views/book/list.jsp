<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<%@ include file="/WEB-INF/views/include/header.jsp"%>
<script type="text/javascript">
	$(document).ready(
			function() {
				$('.filedown').click(
						function() {
							alert("원본 :  " + $(this).attr('ofile')
									+ "      실제 :  " + $(this).attr('sfile'));
							$(document).find('[name="sfolder"]').val(
									$(this).attr('sfolder'));
							$(document).find('[name="ofile"]').val(
									$(this).attr('ofile'));
							$(document).find('[name="sfile"]').val(
									$(this).attr('sfile'));
							$('#downform').attr('action',
									'${root}/book/download').attr('method',
									'get').submit();
						});

			
	
	if("${key}")
		$("#skey").val("${key}").prop("selected", true);
	$("#searchBtn").click(function () {
        var word = $("#sword").val();
        if(word == "") {
        	alert("모든 목록 조회");
        }
        $("#searchform").attr("action", "${root}/book/list").submit();
    });
	
	$(".page-item").click(function() {
		$("#pg").val(($(this).attr("data-pg")));
		console.log($(this).attr("data-pg"));
		$("#pageform").attr("action", "${root}/book/list").submit();
	});
	
});
</script>

<h2>등록 도서 정보</h2>
<form id="downform">
	<input type="hidden" name="sfolder"> <input type="hidden"
		name="ofile"> <input type="hidden" name="sfile">
</form>
<form name="pageform" id="pageform" method="GET" action="">
		<input type="hidden" name="pg" id="pg" value="">
		<input type="hidden" name="key" id="key" value="${key}">
		<input type="hidden" name="word" id="word" value="${word}">
</form>
			<div >
        		 <form id="searchform" method="get">
            		<input type="hidden" name="pg" value="1">
	            	<select id="skey" name="key">
	            		<option value="author"> 작가
	            		<option value="isbn"> 등록번호
	            		<option value="title"> 제목
	            	</select>
	            	<input type="text" id="sword" name="word" value="${word}">
	            	<button type="button" id="searchBtn" >검색</button>
            	</form>
            </div>

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
					<td>${book.content }</td>
				</tr>
				<c:if test="${!empty book.img}">
					<tr>
						<td>${book.img.originFile }<a href="#" class="filedown"
							sfolder="${book.img.saveFolder }"
							sfile="${book.img.saveFile }"
							ofile="${book.img.originFile }">[다운로드]</a>
						</td>
					</tr>
				</c:if>
				<tr>
					<td colspan="2" class="text-right">
						<a href="${root}/book/update?isbn=${book.isbn}">수정</a>
						<a href="${root}/book/delete?isbn=${book.isbn}">삭제</a>
					</td>
				</tr>
			</tbody>
		</c:forEach>
		<div class="m-3 row justify-content-center">${navigation.navigator}</div>
	</c:if>
	<c:if test="${empty books}">
		<h3>등록된 정보가 없습니다.</h3>
	</c:if>
</table>
<!-- 다시 사용자를 등록할 수 있는 링크를 제공한다. -->
<a href="${root}/book/regist">추가등록</a>
</body>
</html>
