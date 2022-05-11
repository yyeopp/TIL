package com.company.ws01.step5;

public class RectArea {

	public static void main(String[] args) {
		Rect r = new Rect(5.0, 4.0);
		
		System.out.printf("가로길이 %.1f, 세로길이 %.1f인 사각형\n넓이 : %.1f\n둘레 길이 : %.1f", r.width, r.length, r.getSpace(), r.getArea());
		
	}

}
