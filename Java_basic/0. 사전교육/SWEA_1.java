public class SWEA_1 {
    public static void main(String[] args) {    // main() �޼���� �ڹٿ� �־���ϴ� Ư���� �޼���. main �޼��� ������ �ٸ� Ŭ������ ��ü, �޼��带 ȣ���ϰ� ������ �� ����. ������ �����Ѵٴ� ��
        // main()�� �Ű������� String Ÿ���� �迭. ���� �� �ڿ� �ۼ��ϴ� ��� �ڵ���� ��·�� '����'�̱� ������ String Ÿ���̶�� �����ϴ� ��.
        // arg[]�� ����� �Ű�����. ������ �����ڷ� �Ͽ� ���� ���� ���� �����ϰ� ��. �ڵ忡�� ;�� �ٴ� ���� �׷� ��� �ƴұ�?
        // "Run configuration" â���� Program arguments �κп� ���ڿ��̳� int �迭�� �������, �װ� �ش� class�� �⺻���� �Ǿ� ȣ���ؿ� ���� �ִ�.
        // �̷� ���ŷο� ���� �ϴ� ������, �ڹٰ� �⺻������ ���̽㿡�� �ִ� input�Լ��� �������� �ʰ� import�ؿ;��ϱ� ����. input�� argument���ٰ� ����Ѵٰ� �����ϸ� �ȴ�.
//        int [] javalist = new int [5];
//        javalist [0] = Integer.parseInt(args[0]);   // �̷��� �س��� argument �� �ټ��� ������ �������� �����ؼ� �������, �� �� ù��°�� "string" Ÿ������ ������ �� parseInt �Լ��� ���� int�� ��ȯ, javalist 0�� ������ ����Ǵ� ��.
//        System.out.println(javalist[0]);

        // ���������, main �޼��尡 ���ڿ��� �迭�� �Ű������μ� �޾� ���α׷� ���� �� �ʿ��� ������ ���α׷��� �����ϴ� ����


//        int num1 = 3;
//        double calc;
//        calc = (double)num1 / 4;    // ���� int�� num1�� double�� ��ȯ���ִ� (double) ��ȯ�ڸ� �ٿ��� ����� �� ����� ������
//        System.out.println(calc);

        //        String a = new String("hello");
//        System.out.println(a);
//
//        int scorelist[] = new int[5];   // scorelist�� �迭�� ����, int���� �迭�μ� ���̴� 5��� ������
//        scorelist = new int[] {1,2,3,4,5};  // �̷��� ���� �ٷ� ���������ϴٴ� ��.
//        System.out.println(scorelist);      // �ٵ� �� ����� �̻��ϰԳ�����
//        int scorelist[] = new int[100];    // scorelist�� ���� 100�� int �迭��
//        scorelist[0] = 1;       // �迭�� ù��°�� 1��
//        scorelist[99] = 100;        // �迭�� �������� 100����
//        System.out.println(scorelist[0]);   // ���.
//        System.out.println(scorelist.length);   // �迭�� ���̸� �ҷ����� �Լ�.
//
//
//        String namelist[] = new String[5];  // string�� ���ؼ��� �����ϰ� ��� ����
//        namelist[0] = "A";
//        namelist[4] = "E";
//        System.out.println(namelist[0]);


//        int scorelist [][] = new int[3][3];     // ������ �迭�� ����. 2���� �迭�ν� 3*3=9 ���� ���Ҹ� ����. ��İ� ���
//
//        int list2 [][] = new int[3][];
//        list2 [0] = new int[2];
//        list2 [1] = new int[3];     // �̷������� �� �������� �ƴϿ��� ��. ù���� 2��, ��°���� 3�� �̷������� ����
//        list2 [2] = new int[4];     // ��� �ϳ��ϳ� �� �����ؾ� �� ��.
//        System.out.println(list2[1].length);    // 3

// ===================================================

//        int num = 9;
//        if(num%2 ==0){      // if�� ���� ���.
//            System.out.println(num+"�� ¦��");
//        }   // else ���� else if(){} �� (������)����� ���� �ִ�.
//        else {      // else�� ���̽�� ����� �������� ��� ����
//            System.out.println(num + "�� Ȧ��");
//        }


//        int a = 11;    // switch�ȿ��� chr, int, byte, short�� �� �� ����.
//        switch (a) {    // switch�� ���� ���� �бⰡ ����. a�� �Է��ϴ� ���� ���� �� case�� �ش��ϴ��� �˻��ؼ� ������ �Լ��� ���.
//            case 9:    // ���⿡ �� >5 ���� boolean�� �����ϴ� ������ ��������� �ȵ�. �׳� expression�� ����� �ڷ����� ���� ���� ���� �Է�
//                System.out.println("9");
//                break;  // �ݵ�� �� case�� ���� break�� ����� ��. �ȱ׷��� ���� case�� ���ڴ�� �Ѿ��
//            case 10:
//                System.out.println("10");
//                break;
//            case 11: // break ������ ���� ���̽��� �Ѿ�ٴ� ���� �̿��ؼ�, �̷��� ������ ���� ����.
//            case 12:
//                System.out.println("11, 12");   // 11�� ������ case 12 ���� �ͼ� 11,12 ��·�                break;
//            default:   // ��� case�� �ش����� ���� �� default�� �����. ���⼭�� break�� ���� ������ "���� case"�� ��� ū ������ �� ����
//                System.out.println("�ƹ��ų�");


//for(�ʱ��; ���ǽ�; ������) {
//Statement1;
//]
//statement2;
//
//�ʱ�Ŀ� ���� ���ǽ��� true�̸�cc statement1�� ����, �������� ����,
//�ٽ� ���ǽ��� true���� Ȯ��.
//�������� ������ ���ǽİ� statement1 ������ ���ѷ����� �߻�.
//���ǽ��� false�� �� �� ���� statement2 ����.

        // for ��� ���� ������ �ܺο��� ȣ���� �Ұ���. for�� �Լ������� �ָ������� �� ���� ����������.. ��� ����.
        // for�������� �ܺο� �ִ� ������ ����پ��°� �����ϴ�.


//        int sum = 0;
//        for (int i = 1; i <= 100; i ++){
//            if (i%2 == 0) {
//                sum += i;   // for �ۿ� �ִ� sum�� ���پ�.
//            }
//        }
//        System.out.println("¦�� ���� " + sum);  // ���⼭ i�� ����� �� ���ٴ� ��


//        for (int i = 1; i <= 10; i ++){     // ���߹ݺ����� ����.
//            for (int j = 1; j <= i; j ++) {
//                System.out.printf("*");   // �̰� j���� *�� ���� ���̱� ����
//            }
//            System.out.println("");   // �̰� j�� ���� for �ݺ��� �ѹ� ���ư��� i�� �ѹ� �þ�� ���� ������ �ֱ� ����.
//        }


//        int i = 1;
//        int sum = 0;
//        while (i <= 100) {      // ��ȣ �ȿ� ���ǽ�. �̰� true�̸� ��� ���ΰ� ����, false�̸� ����� skip
//            if(i%2 == 0) {      // �ݺ��ؼ� ����Ǵ� �κ�
//                sum += i;
//            }
//            ++i;        // ���ѹݺ��� �����ϱ� ���� ��ġ.   # ++ �����ڰ� �̷��� �ܵ����� ���Ǵ� �����, ++i�� i++�� ��������� �����ϴ�. x = ++i(i�� 1 ���� �� x�� ����)�� x = i++(���� i���� x�� ���Ե� �� i�� 1 ����)�� �ٸ�.
//        }
//        System.out.println("¦�� ���� " + sum);


//        int i = 102;    // do-while�� ��� ��������� ��¦ �ٸ�
//        int sum = 0;
//        do {        // do ��Ͽ� �ִ� ���� ���ǽ�(while����)�� TF�� ������� �ݵ�� �ѹ� ����ȴٴ� ��.
//            if(i%2==0) {
//                sum += i;
//            }
//            ++i;
//        }
//        while (i <= 100);   // �̰� True�� �ٽ� do�� ���ư�����, false�� �����
//        System.out.println("¦�� ��: " + sum);


//        int sum = 0;
//        for (int i=0; i<10; i++) {      // for ��
//            if(i==5) {      // �� if���� ���� ����.
//                break;      // i==5�� ������ ��, for �ݺ����� ��������ǰ� ������ ���������� ��
//            }
//            sum += i;       // sum�� ����, i����� �� �����Ŀ� ���� i�� 1�� ����
//            System.out.println("i value: " + i);
//        }
//        System.out.println("sum: "+sum);


//        int scoreList [] = {98,57,49,100,99,85,77};
//        int maxScore = 300;
//        int scoreSum = 0;
//        for (int i=0; i<scoreList.length; i++){ //.lengthȰ��
//            scoreSum += scoreList[i];   // list �׸��� sum�� �ϳ��� ����
//            if(scoreSum>maxScore) {     //���� max�� ���� ��
//                scoreSum -= scoreList[i];   // sum�� max�� ������ �ȵǴϱ� ������ ���� ���ְ�
//                break;      // break�� �ش� for���� ����.
//            }       // for �ȿ� for�� ����ִ� ���� ���߹ݺ��������� break�� ���� for�� �ִٸ� ������ for�� ����ǰ� �ٱ� for�� ������ �����.
//        }
//        System.out.println("scoreSum: " + scoreSum);


//        int scoreList[] = {98,57,49,100,99,85,77};
//        int scoreSum = 0;
//        for (int i =0; i < scoreList.length; i++) {
//            if(scoreList[i] %2 != 0) {  // list�� score���� ¦���� �ƴ�, Ȧ���� ��
//                continue;       // continue�� ���� for �ݺ��� ���� ������ ������� �ʰ� �Ѿ. i++�� ������ �� �ٽ� Ȧ¦���� �Ǵ�.
//            }
//            scoreSum += scoreList[i];
//        }
//        System.out.println("scoreSum: "+scoreSum);
//


//        System.out.println("main�޼��� ����");
//        methodA();  // main�޼��忡�� A�޼��带 ȣ���ϴ� ����
//        System.out.println("main�޼��� ��");
//    }       // �̷��� main �޼��带 �ݾƳ���
//    private static void methodA() {     // ���Ӱ� A�޼��带 ������ �� ����
//        System.out.println("A�޼��� ����");
//        for (int i = 0; i < 5; i++) {      // ���� 4���� ����ϴ� �ݺ���������
//            if (i == 3) {
//                return;     // 3�� �Ǵ� ���� ���̻� ������� ���ϰ� return�� ���� A�޼��带 ȣ���ߴ� �������� ���ư�.
//            }
//            System.out.println(i);      // 0,1,2�� ����ϰ� ��
//        }
//        System.out.println("A�޼��� ��");
//    }       // break, continue, return�� �ᱹ �ڵ� ���� ��ġ�� �Դٰ����ϰ� ����� '�̵� ���'�� �ش�. ���� ���߰��� �����ϴٰ� �Ⱦ��Ͱ���





    }
}