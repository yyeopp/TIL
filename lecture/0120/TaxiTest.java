

public class TaxiTest {

	public static void main(String[] args) {

		Taxi t1 = new Taxi();
		System.out.println("Taxi " + t1.toString()); // public�� �޼��带 �����ٰ� ���
		System.out.println("t1�ý� 20�� ����");
		for (int i = 0; i < 20; i++) {
			t1.speedUp();
		}
		System.out.println("t1�ӵ�: " + t1.getSpeed());
//		System.out.println("�̸�: " + t1.carName + "\t����: " + t1.carColor + "\t������: " + t1.maker);

		Taxi t2 = new Taxi("K5", "��ȫ��", "���");
//		t2.setCarName("�ƹݶ�"); // setter�� ���� private�� �������� �Ӽ� �ٲٱ�
		System.out.println("Taxi " + t2); // toString�� �ڵ����� ��ȯ�ȴ�

		System.out.println(t2.info());

		Taxi t3 = new Taxi("K5", "��ȫ��", "���");

		System.out.println("t2: " + t2);
		System.out.println("t3: " + t3);

		if (t2 == t3) {
			System.out.println("t2�� t3�� �ּҰ��� ���� ��");
		} else {
			System.out.println("t2�� t3�� �ּҰ��� �ٸ� ��");
		}

		Taxi t4 = t2;
		if (t2 == t4) {
			System.out.println("t2�� t4�� �ּҰ��� ���� ��");
		}

//		if (t2.carName.equals(t3.carName)) {		// ���� ���� ��Ȯ�ϰ� �ϴ� ���
//			System.out.println("t2�� t3�� �̸��� ���� ��");
//		}
//		else {
//			System.out.println("t2�� t3�� �̸��� �ٸ� ��");
//		}
		System.out.println(t2);		// �̰� �� toString() �Ȱ� ������????????????????????????????????????????????????
		if (t2.equals(t3)) { // Car Ŭ�������� equals�� �������̵��ϰ� �� ���
			System.out.println("t2�� t3�� �̸��� ���� ��");
		} else {
			System.out.println("t2�� t3�� �̸��� �ٸ� ��");
		}
		if (t2 == t3) {
			System.out.println("t2�� t3�� �̸��� ���� ��");
		} else {
			System.out.println("t2�� t3�� �̸��� �ٸ� ��");
		}
	}

}
