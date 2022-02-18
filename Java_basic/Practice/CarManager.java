package com;

import java.util.List;

public interface CarManager {

	void add(Car car) throws SameNumberException; // 추가 + 이미 있는 번호판인 경우의 사용자정의예외

	List<Car> search(); // 전체목록

	Car searchByNum(int carNum) throws CarNotFoundException; // 번호판으로 검색 + 번호판이 없는 경우의 사용자정의예외

	void update(int carNum, String carName); // 이름변경

	void delete(int carNum); // 삭제 - 차번호로

	void delete(String carName); // 삭제 - 이름으로

	void fileSave(); // 목록 파일로 저장

	List<Car> fileLoad(); // 파일 상 목록 불러오기.

}
