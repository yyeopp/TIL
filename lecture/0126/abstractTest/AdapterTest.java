package com.company.abstractTest;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class AdapterTest extends Frame implements ActionListener{ // 어차피 Frame이라는 클래스에 있는 메서드들 사용할거면, 아예 상속을 받아버리자


	//	Frame f = new Frame("HasATest!!!!");
	Button ok = new Button("PUSH");
	Random random = new Random();

	public AdapterTest() { // Frame f 생성해서 f. 할 필요가 없다
		super("AdapterTest!"); // super 사용하기. 아까 Frame("str")으로 title 설정 가능하다는 API를 읽고 왔다
		setLayout(new FlowLayout());

//		ok.setLabel("PUSH");		
		add(ok);
		setBackground(Color.PINK);
		setResizable(false);
		setSize(300, 400);
		setVisible(true); // 여기까지는 창 닫는 기능이 구현되어있지 않다.

		WindowListener wl = new WindowAdapter() { // API 뒤져보자. 인터페이스.. 상속.. 을 거쳐서 어댑터는 추상클래스이지만 추상메서드가 0개인 케이스

			@Override // 그런 경우, 메서드 중 하나 이상 맘에드는것만 오버라이딩해서 쓰면 된다고 함
			public void windowClosing(WindowEvent e) {
				System.exit(0); // 닫는 기능 구현
			}

			@Override	// 입맛에 맞게 계속 갖다붙여서 다르게 만들 수 있음.
			public void windowIconified(WindowEvent e) {
				System.out.println("창이 아래로 내려감");
			}		// 이런식으로 객체를 만드는 방법이, "동적 객체 생성". 이거랑 차별되는 게 MyWindowAdapter 클래스를 하나 파서 WindowAdpater를 상속받고, 직접 구현한 다음 여기에 MWA 객체를 소환하는 방법. (그게 좀더 일반적이긴 함)
			

		}; // 뭔가 문법 자체도 좀 다름. ()하고 {};
		
		// 그럼 adaptor라는 클래스는 왜 있는건가? 인터페이스와 거의 기능이 비슷한데, 인터페이스를 implements할 시 모든 메서드를 오버라이딩해야해서 불편하기 때문.
		// 직접 WindowListener를 implements해보면 오버라이딩이 강제되면서 코드가 길어지는 걸 확인 가능하다.
		
		this.addWindowListener(wl); // 그 객체를 소환해서 기능을 연동
		
		ok.addActionListener(this);		// 이렇게 해서 ok버튼에 배경색을 바꾸는 기능을 추가했음.

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {		// 일단 코드에 써놓지는 않았는데, 지금 중요한 건 인터페이스를 "다중"으로 implements할 수 있다는 것.
		int r = random.nextInt(256);
		int g = random.nextInt(256);
		int b = random.nextInt(256);
		
		this.setBackground(new Color(r,g,b));
	}

	public static void main(String[] args) {
		new AdapterTest();
	}
}