package com.ssafy.ws.dto;

public class SearchConditionDto {

	// 페이지 당 10개씩 보여주는 건 상수
	public final int countPerPage = 10;

	// DB 상 PK는 isbn. isbn 검색에 대해서는 like 검색이 필요 없음
	// key = "isbn" 으로 인풋될 것
	// 만약 title 같은 값으로 검색하면 key="none"으로 입력되고
	// like 검색을 처리해야 함
	private String key = "none";

	// 검색어
	private String word;

	// orderBy를 따로 집어넣었다면 none이 아니게 됨
	// 그럼 정렬하면서 orderByDir의 순서에 따르도록 검색

	// 따로 집어넣지 않았다면 정렬하지 않는 것으로
	private String orderBy = "none";

	// 기본 정렬값 오름차순
	private String orderByDir = "asc";

	// 현재 페이지 번호. offset 계산 시 활용, 기본 페이지 1
	private int currentPage = 1;

	// limit 적용 여부
	private boolean limit = true;

	// offset에서부터(currentPage=1이면 0)
	// countPerPage(기본값 10) 개수만큼 데이터 반환하도록

	public SearchConditionDto() {
	}

	public SearchConditionDto(String key, String word) {
		this.key = key;
		this.word = word;
		this.orderBy = "none";
	}

	public SearchConditionDto(String key, String word, String orderBy) {
		this.key = key;
		this.word = word;
		this.orderBy = orderBy;
		this.orderByDir = "asc";
	}

	public SearchConditionDto(String key, String word, String orderBy, String orderByDir) {
		this.key = key;
		this.word = word;
		this.orderBy = orderBy;
		this.orderByDir = orderByDir;
		this.currentPage = 1;
	}

	public SearchConditionDto(String key, String word, String orderBy, String orderByDir, int currentPage) {
		this.key = key;
		this.word = word;
		this.orderBy = orderBy;
		this.orderByDir = orderByDir;
		this.currentPage = currentPage;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getOrderByDir() {
		return orderByDir;
	}

	public void setOrderByDir(String orderByDir) {
		this.orderByDir = orderByDir;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public boolean isLimit() {
		return limit;
	}

	public void setLimit(boolean limit) {
		this.limit = limit;
	}

	public int getOffset() {
		return (this.currentPage - 1) * countPerPage;
	}
}
