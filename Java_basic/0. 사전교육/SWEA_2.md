//class Car {
//    private int serialNumber;    // �ٸ� Ŭ�������� ���� �Ұ�
//    protected String name;
//    int speed;          // class �κп� ������ Ŭ���ϰ� 'Generate'�� ������, ������ ���� getter�� setter �޼��带 �ڵ����� �ۼ����ִ� ����� �ִ�!!
//    static String carStatus = "active";        // static�� ������ ����Ͽ�, carStatus�� "Ŭ��������"�� ����. �̷��� Car Ŭ������ ���ϴ� �ν��Ͻ����� �ڵ����� active��� ���� carStatus�� ������ ��. �޸� ���� �������� Ŭ���� ���� ��ü���� ���뺯���� ������ ���� ��
/////////////////////////////////////////////// ���� ������� static�� �ٿ��� "Ŭ�����޼���"�� ������ �� �ִ�. Ŭ�����޼��忡���� Ŭ������������ ���پ� �� �ִ�. static�� �پ����� ���� �ν��Ͻ������� �ο��� �� ����.
//
//
//
//    final static double rate = 15.25;      // final ������ rate��� ������ '���'�� ������ش�. ó�� ������ ���� �����ϰ�� ���� ������ �Ұ�����
//    public static void change() {
//        rate++;             // ���� �߻�. rate�� final�� ����Ʊ� ����.
//    }               // �׿� ������, rate�� change�� �ؿ��� ���پ����� static�� �� �ٿ���� ���� Ȯ�� ������
//}                     // Ŭ���������� static���� �������ָ鼭 final�� ������ ���� ��, ���� ��ü���� �ش� ������ �ٸ��� �����ϴ� �� �����ϴ�. ��ü�� Car()��� ���ϸ鼭 ��ü.rate = 12 �̷��� �����ص� �����ϴ� ��. �װ� �������� final�� ������ �������� �Ѵ�.
//
//
//    @Override                   // Generate �޴����� toString()�� ������ �������� �������ִ� toString()�޼��带 �ڵ� �ۼ��� �� �ִ�.
//    public String toString() {
//        return "Car{" + "serialNumber=" + serialNumber +", name='" + name + '\'' +", speed=" + speed + '}';
//    }
//
//
//    public int getSerialNumber(int managerID) {  // serialNumber�� private�� ����Ǿ����� ���� Ŭ���� ���ο��� �ش� ������ ���� ������ �޼��带 ����
//        if (managerID == 12345) {       // �Ŵ������̵� �Է¹޾Ƽ�, �׿� ��ġ�� �� get�޼���� �ø���ѹ��� �����Ѵ�.
//            return serialNumber;          // @@@@@@ �̷��� private ������ �޾ƿ��� �޼��带 "getter�޼���" ��� ��Ī�Ѵ�.
//        }
//        return 0;       // Ʋ���� 0�� ����
//    }
//    public void setSerialNumber(int serialNumber){      // ���������� �ø���ѹ��� ���� ������ public �޼��带 �����ؼ�, �ø���ѹ��� �Է¹��� �� ����
//        this.serialNumber = serialNumber;       // void�̱� ������ return�� �ʿ����. this. �� ���̽㿡���� self�� �ش�. this.�� ���� set�޼��忡 ��õ� �ø���ѹ� ������ ��Ī���� ���
//    }                             // @@@@@@@@���������� �̷��� private ������ �����ϴ� �޼��带 "setter�޼���"��� ��Ī�Ѵ�.
//
//}

public class SWEA_2 {
    public static void main(String args[]) {


//        Car.change();         // static�� �پ����� ��
//        System.out.println(Car.rate);     // �⺻������ ������ ��������

//        System.out.println(Car.crStatus);      // ������ static String carStatus�� Ȱ��ȭ�ϸ�, Car Ŭ������ ���ϴ� ������ ��ü�� �������� �ʾƵ� "Car"��� Ŭ���� �̸����κ��� ���� �ش� Ŭ���������� ����ؿ� �� �ִ�.
//        System.out.println("�ƹ��ų�");   // ��� ���� �� �ڵ� ����, �ڹٰ� �⺻������ ����ִ� "System"�̶�� Ŭ������ "out"�̶�� �ý��ۺ����� ����ϴ� �ڵ��.
//
//
//        Car Yellow = new Car();
//        Yellow.name = "Light";      // name�� ���� ��Ű���� ���� ����
//        Yellow.speed = 300;     // default�ϱ� ���� ����
        /////////////////////////Yellow.serialNumber = 3040;  // �̰� ���� Ǯ�� ������
//        Yellow.setSerialNumber(4929);   // !!!!!!!!!! ���, Car Ŭ�������� public���� ������ set�޼��带 �̿��Ѵٸ� �ø���ѹ��� ������� ������ �� �ְ� ��
//        System.out.println(Yellow.name + " : " + Yellow.speed); // ���⼭ serialNumber�� �����õ� ����. private�� ��ȣ�Ǿ��ֱ� ����
//        System.out.println("Car No: " + Yellow.getSerialNumber(12345));     // !!!!!!!!! ���, get �޼��带 �̿��Ѵٸ� �ø���ѹ� �������� ������.
//        System.out.println(Yellow.toString());      // toString���� ������ Ȯ���ϴ� ���.
//


        // Ŭ������ ���α׷��� ���� �⺻���� ���. �޸� �� ��ü�� �ν��Ͻ��� �����ϱ� ���� ���ø�.
        // �ν��Ͻ��� ��ü�� �޸𸮿� �ö� ��. �޸� ���� ��ü. ��ǻ� ��ü�� ���Ǿ�
        // ��ü�� ���� ���迡 �����ϴ� �������� ��� ��. ������� �ǹ̸� �ο��ϰ� �з��ϴ� ������ ����
        // �߻�ȭ�� ������ ��ü���� ������ Ư���� ��� �Ϲ�ȭ�ϴ� ��. Ŭ���� ���� �� �ٽ�
        // �������� ���� �������̽��� �̿��� ���� �ٸ� ���� ��ü�� �ϰ��� ������� ����� �� �ְ� ��. ���α׷� ���������� ���. ���߿� �ڼ��� ����
        // ����� Ŭ�������� ���� ����ȭ�Ͽ� ������ Ŭ���� ������ �޼��带 �����ϴ� ���.
        // ����������, Ŭ���� �� ������ �޼��带 ���������� �����ϰų� ����� ���. private�� public���� ���� ����
        // ĸ��ȭ��, �ϳ��� Ŭ���� �ȿ� ������ �޼��带 �ϳ��� ���� ���� ���������� �������� �ʵ��� �ϴ� �� ��ü�� ���ϴ� ��


//        class Car {
//            String name;         // Car Ŭ������ name, currentSpeed, currenGear�� �Ӽ� �ο�
//            int currentSpeed;
//            int currentGear;
//            void start(){       // �޼��� start ����
//                System.out.println(name + "�� �õ��Ҵ�");
//                currentSpeed = 1;
//            }
//            void changeGear(int gear){   // �޼��� changeGear ����. void�� �����ϸ� return�� �ʿ���ٰ� �ϴµ� ���� ����� ���ظ���
//                System.out.println("��"+gear+"�����κ���");
//                currentGear = gear;
//            }
//            int getCurrentSpeed() {     // �޼��� ����. int�� ���������ϱ�
//                currentSpeed += currentGear * 10;
//                return currentSpeed;           // return�� ���� int���� currentSpeed�� ���س�����. �̰Ÿ� getCurrentState�� ���� ��Ʈ�����·� ����Ʈ�ϴ°� �ʿ�
//            }
//            void stop() {
//                System.out.println(name+"�� �õ�����");
//                currentSpeed = 0;
//                currentGear = 0;
//            }
//            String getCurrentState(){       // ���������� String�� ���س��� ��
//                return name + "�� ���� �ӵ�: " + getCurrentSpeed();      // return�� ���� getCurrentSpeed() �޼���� ���ư�
//            }
//        }
//        Car myCar = new Car();      // Car Ŭ������ �ν��Ͻ��� myCar ����
//        myCar.name = "Red";         // �Ӽ� ����
//        myCar.currentGear = 0;
//        myCar.currentSpeed = 0;
//
//        myCar.start();
//        System.out.println(myCar.getCurrentState());
//        myCar.changeGear(2);
//        System.out.println(myCar.getCurrentState());
//        myCar.changeGear(3);
//        System.out.println(myCar.getCurrentState());
//        myCar.stop();
//        System.out.println(myCar.getCurrentState());


//        class Taxi extends Car {        // �����ִ� Car Ŭ���� Ȱ��ȭ ��, ���ο� Taxi Ŭ������ Car�� ��� ������ �޼��带 ��ӹ޴´�. extends�� ���
//            int fare;       // ���ο� ������ ���� ����
//            boolean passengerYn;
//        }     // �ڹٿ����� ���߻���� ���� �ȵ�. �ϳ��� Ŭ������ �ϳ��� ���� Ŭ�������� ���� �� ����
//        Taxi myTaxi = new Taxi();
//        myTaxi.name = "308";
//        myTaxi.currentGear = 2;
//        myTaxi.fare = 3400;
//        myTaxi.passengerYn = true;
//        System.out.println(myTaxi.getCurrentState());       // �Ʊ�� ���� �������� ��� ����


// Modifier�� ���ؼ��� ���� �����ؼ� Ȱ���ϴ� �� �ʿ��ϴ�.
        // ���ٱ��Ѱ� Ȱ������ ���� ǥ���� �� �ִµ�, class, ����, �޼��忡 ���� �� �ִ� modifier�� ���� �������ְ� ȿ���� �������̴�. ���� ������ ������ �����ؾ� �� ��


//        int enScore[] = {55,70,45,90,100,90,95};
//        System.out.println("���� ���: " + getAvgScore(enScore));       // ���θ޼��忡�� get�޼��带 ȣ��.
//        int koScore[] = {93,55,87,80,100,95,65};
//        System.out.println("���� ���: " + getAvgScore(koScore));
//    }
//    private static double getAvgScore(int scoreList[]){     // ���θ޼���� ������ get �޼��带 ����. int ������ scoreList[]�� �Ű������� �޴� �޼���
//        int sum = 0;
//        double avgScore = 0.0;
//        for (int i=0; i<scoreList.length; i++){
//            sum += scoreList[i];
//        }
//        avgScore = (double)sum/scoreList.length;        // double���� ��ȯ�� ���Խ�Ű�� �� ����
//        return avgScore;        // double �޼����̹Ƿ� return���� �ʼ�. ���� avg ���� ���θ޼��忡�� ������


//        class Car {
//            String name;
//            int speed;
//            void printInfo(){        // �̰� final void�� ������ ��, �ڽ�Ŭ������ Taxi���� printInfo �޼��带 "�������̵�"�� �� ���� �ȴ�. final�� ���� ���� ���� �߻�
//                System.out.println(name + "�� ����ӵ�: " + speed);
//            }
//        }
//        class Taxi extends Car {            // ���� �������� Ŭ���� �̸� �տ� final�� ���� ��, ����� �����Ǵ� Ŭ������ ��. Car���� final �Է��ϸ� �ٷ� ���� �߻�
//            int currentoil;
//            void printInfo(){
//                System.out.println(name + "�� ���� ����: " + currentoil); // �޼��� �������̵�. �ڽ� Ŭ�������� �θ� Ŭ������ �޼��� ����� ������
//            }
//        }
//        Taxi a = new Taxi();
//        a.printInfo();


//        abstract class SuperClass {     // ���ο� ��ü�� ������ �� ���� '�߻�'Ŭ������ �ش�. ��ӿ� Ȱ��ȴٴ� ������ �ǹ̸� ����.
//            void methodA() {
//                System.out.println("A����");
//            }
//            abstract void methodB();            // "�߻�޼���"�� �ش�. �Ű������� �̸� ������ ���������� ��ü���� ����� ���� �������� ����
//        }
//        class SubClass extends SuperClass {
//            void methodB(){             // �߻�޼��带 �ڽ�Ŭ�������� "�������̵�"�����ν� ��μ� �ǹ̸� ������ ��.
//                System.out.println("B����");        // �ݴ�� ���ϸ�, �߻�Ŭ������ ��ӹ޴� �ڽ�Ŭ������ ����� ����ϱ� ���ؼ��� �θ�Ŭ������ "��� �߻�޼���"�� "�������̵�"�ؾ��Ѵٴ� ��. �ڽ�Ŭ������ �߻��̸� �� �𸦱�.
//            }
//        }
//////////////////        SuperClass Y = new SuperClass();        // �̰� Ǯ�� ������. SuperClass�� �߻�Ŭ������ ��ü ������ �Ұ���
//        SubClass X = new SubClass();            // ��� �װ� ��ӹ��� SubClass���� ��ü ������ ����.
//        X.methodA();
//        X.methodB();            // �̰͵� ������ ���� ����.


//        class Employee {
//            String name;
//            int number;
//            int age;
//            String title;
//            String dept;
//            String grade;
//            public Employee(String name, int number, int age, String title, String dept, String grade) {
//                this.name = name;       // �ؿ����� n1���� ������ ���� �����ߴµ�, ��� �װ� ������ ���ŷο� ���̱� ������ �״�� name�̶�� �̸��� �����
//                this.number = number;   // �ٵ� �׷����� �� Ŭ������ ��������� name�� �������� �Ű����� name �� ȥ���� �߻��� �� ����
//                this.age = age;         // �׷���, this��� ���� ���� Ŭ������ ����������� Ȯ���ϰ� ������                this.title = title;
//                this.dept = dept;       // ���̽㿡�� self�� ���� ���
//                this.grade = grade;      // �Ʊ��ߴ���ó�� generate �޴����� �����ڸ޼��带 �ڵ��ϼ����� �� ����!!!!
//            }
//            Employee(String n1, int n2, int a, String t, String d, String g) {
//                name = n1;      // ���⼭ Employee�� Ŭ������ ���� �̸��� ���� Ư���� '�޼���'
//                number = n2;    // ���� �ش� Ŭ������ ���ϴ� ��ü�� ������ ��, ��� �������� �ʱ�ȭ�ϴ� �޼����̴�.
//                age = a;        // void�� ������� �ʰ�, �ٵ� Return�� ��� ��. ���� Ư���� ��
//                title = t;      // ���� ���� �Ű������� �ߺ������� �� �ִ� Ư¡
//                dept = d;       // ��, �����ڴ� "�޼����� ����". �׷��ٺ��� ���ٱ��ѿ� ���� modifier�� �տ� �� �� ����
//                grade = g;
//            }
//        }
//        Employee kim = new Employee("�赿��", 1543,30,"�븮","������","���");  // �̷������� ��������� �Ѳ����� �ʱ�ȭ
//        Employee lee = new Employee();          // Ŭ���� ���� �� �����ڸ޼��带 ���� �� ���, ���� ���ο� ��ü�� �ƹ����� ���� Ŭ���� �ν��Ͻ��� ������ ��� "�⺻��"�� ������ �����ȴ�.
//        System.out.println(kim.name);       // ����� new ���� ������(?)
//        System.out.println(lee.name);       // name�� ��� Str�̹Ƿ� null�� ��. int��� 0�� �⺻��. ����..


//        class Employee {
//            String name;
//            int age;
//            Employee(String name, int age){
//                this.name = name;
//                this.age = age;
//            }
//            Employee(String name){
//                this.name = name;
//            }
//            Employee(){                 // �����ڸ޼��� ���� �����ε��� �����ϴ�. ��ü���� �׶��׶� �ʿ��� �����͸��� �ʱ�ȭ�� �� �־ ���ϴ�.
//                name = "No name";
//            }
//            void set(String name, int age){
//                this.name = name;
//                this.age = age;
//            }
//            void set(String name){     // set�̶�� �̸��� �޼��尡 Ŭ���� ���ο� 3���� ���������, ����ִ� �Ű������� ������ Ÿ���� �������̶� �浹�� �������� ����.
//                this.name = name;       // �̷� ����� "�޼��� �����ε�" �̶�� �Ѵ�. "�ߺ�����"
//            }
//            void set() {                // �޼��� �����ε��� �����.. ���� �����Ǿ� ����.
//                name = "No name";
//                age = 0;
//            }               // �����ε��� ����� �Ǳ� ���ؼ��� �Ű������� "����"�� "Ÿ��"�� �޶���Ѵ�. String name + int age�� �ִ� ���¿��� String name + int salary�� ������ �� �����ε� ���� �Ұ�. ��ü�� �Է��� �� � �޼���� ������ �� �Һи��ϱ� ����
//        }                       // ���, int salary + String name���� �Ű����� "����"�� �޸��ϸ� �����ε��� �� �ִ�. �Է��� �� �򰥸� ���� ���� ����
//        Employee park = new Employee();
//        park.set("���缺",28);         // �̷��� ù��° set��,
//        System.out.println(park.name);
//        park.set();                             // �̷��� ����° set�� �˾Ƽ� ����Ǵ� ����.
//        System.out.println(park.name);          // println�޼��� ����, �Ű������� ���ԵǴ� �پ��� �Ű������� ���� '�����ε�'�� �Ǿ��ִ�. �� ���п� �ȿ� � �ڷ����� �ִ��� ���ٸ� ���� ���� ���ϴ� ��¹��� ���� �� �ִ� ��!!!


//        int score = 10;
//        System.out.println("���� ��: " + score);
//        changeScore(score);
//        System.out.println("���� ��: " + score);          // score���� ���� �޼��忡 �����ϴ� ���¿��� change�޼��忡 "�� ���� 10��" �����ؼ� ���� �� "�� �ּ��� score"�� �ٲ��� �ʾҴ�. �׷��� change �޼��� ���� ���Ŀ��� �� ���� 10�� ��.
//    }
//    private static int changeScore(int score) {         // main �޼���� ������ �޼��带 �ϳ� �� ����, score��� ������ default�� ��������Ƿ� ���� ��Ű�� ���� �ִ� change �޼��尡 ������ �� ����
//        score = 100;
//        System.out.println("�����: " + score);
//        return score;           // return �ʿ�


//        int[] scorelist = {40, 78, 98};
//        System.out.println("������: " + scorelist[0]);
//        changeScore(scorelist);
//        System.out.println("������: " + scorelist[0]);     // �� ������ list[0]���� �������� ������?
//    }
//    private static int[] changeScore(int[] scorelist) {         // �̰� �Ʊ� int score �Ű������� ������� �Ŷ� ���� �ٸ���..?
//        scorelist[0] = 100;
//        System.out.println("�����: "+scorelist[0]);
//        return scorelist;


//        class Test {
//            int intSum(int... num){     // test Ŭ������ intSum �޼���� �Ű������� Ÿ���� int��, �̸��� num���� ����������, �� ������ '������'��.
//                int sum = 0;
//                for (int i=0; i<num.length; i++){
//                    sum += num[i];      // �̰� �� ������� �ʳ�? �޼��� �Ű��������� int[]��� ������ ���� ������?
//                }
//                return sum;
//            }
//        }
//        Test x = new Test();
//        Test y = new Test();
//        System.out.println(x.intSum(1,2,3));
//        System.out.println(y.intSum(5,7,8,4,4,3,2));        // �̷��� �Ű����� ������ ����� ���� �� �ִٴ� ��.



//        class SuperClass {
//            int num1;
//            SuperClass() {      // SuperClass ���� ������ �ʱ�ȭ��Ű�� �����ڸ޼���. ()�� �Ű����� �ڸ��� ��������Ƿ� '�⺻������' ��� ��
//                num1=100;       // ���࿡ �̰� SuperClass(int num) �̷��� �Է��� ��, �ڽ� Ŭ�������� �ҷ��� �� �ִ� '�⺻������'�� ���� ������ ������ �߻��Ѵ�.
//            }
//        }
//        class SubClass extends SuperClass {     // ��ӹ���
//            int num2;
//            SubClass() {        // ���������� �����ڸ޼����ε�, Ư���� SuperClass(); �� ȣ���ϴ� �κ��� ����.
//                num2=10000;
//            }
//        }
//        SubClass sub = new SubClass();
//        System.out.println(sub.num1);      // 100�� ���� ���
//        System.out.println(sub.num2);      // ��, ������� �ʾƵ� �ڽ�Ŭ������ �θ�Ŭ������ '�⺻'�����ڸ޼��带 ȣ���ؿ�.



//        class Shape {
//            int x = 0;
//            int y = 0;
//            Shape() {           // �⺻������.
//                this(0,0);      // �� ���� �̷��� �ᵵ ������°ǰ�
//            }
//            Shape(int x, int y) {
//                this.x = x;
//                this.y = y;
//            }
//        }
//        class Circle extends Shape {                // ��� ��, �θ�Ŭ������ private ������ ��ӵ��� �ʴ´�. �ڽ�Ŭ�������� �θ�Ŭ������ private������ ����(�ʱ�ȭ)�Ϸ��� �ϸ� ���� �߻�
//            int radius;                              // �� �� �������� ��ӵ�����, �ڽ� Ŭ�������� �θ�Ŭ������ ������ ���� �̸��� ������ �ٸ��� �ʱ�ȭ�� ��� (�ڷ����� �޶����� ��� ����) �װ� ���󰡰Եȴ�. ����..�������̵�?
//            Circle(int x, int y, int radius) {      // �굵 �ڽ�Ŭ������ ��ü���� �����ڸ޼���
//                super(x, y);         // super�� ���̽��� self, �ڹ��� this�� �ߴ� ��ó�� Ư�� �޼��带 ��Ī�ϴ� �ܾ�. �ڽ�Ŭ�������� �θ�Ŭ������ �����ڸ� ȣ���� �� ���. Circle Ŭ������ �Է¹��� x,y���� �θ�Ŭ���� �� Shape �޼��忡 ����ְڴٴ� �ǹ�
//                this.radius = radius;       // super(int x, int y)��� �ߴ��� x�� y�� local �Ű������� �ν��Ѵ�. �̹� Circle���� �����س����� �򰥸��� ���
//            }                           // ���� super�κ��� �ƿ� ������ ��, "�⺻������"�� ȣ��ǹǷ� x�� y�� 0���� �ʱ�ȭ�� ���ۿ� ����
//            void draw() {               // *** super()�� �θ�Ŭ������ �����ڸ� ���������� �ڽ�Ŭ���� ������ ���� ���� �� ���ٿ� �־�� �Ѵ�. this.radius�� ���������� ����
//                System.out.println(x+","+y+","+radius);
//            }
//        }
//        Circle c = new Circle(200,500,100);
//        c.draw();




//        class Employee {
//            String name;
//            int deptNo;
//            String grade;
//        }
//        class Manager extends Employee {
//            String boss;
//            char grade;
//            void printGrade() {
//                this.grade = 'A';           // ���� �̸��� ������ ���� this�� super�� ��ġ�� �����ϴ� ���.
//                super.grade = "A���";
//                System.out.println(this.grade + "," + super.grade);
//            }
//        }
//        Manager kim = new Manager();
//        kim.printGrade();



//        class SuperClass {
//            void print(String str) {
//                System.out.println("ȣ��");
//            }
//        }
//        class SubClass extends SuperClass {
//            void print() {          // �θ� Ŭ������ �޼���� ���� �̸��̶�� �ؼ� "�������̵�"�� �ƴ�. �Ű����� ������ ������ �ٸ��� ������, �̰� �θ�Ŭ�����κ��� ��� �޼��带 ��ӹ��� �ڽ�Ŭ������ print �޼��尡 "�����ε�"�� ��.
//                System.out.println("�� ȣ��");
//            }
//        }
//        SubClass sub = new SubClass();
//        sub.print("�׽�Ʈ");           // �׷��� �Ѵ� ���� �����.
//        sub.print();



//        class Camera {
//            String name;
//            int sheets;
//            void take() {         // Ȥ�� �޼��忡 �������̵��� ������� ��������, final void�� �����ϸ� �ȴ�.
//                System.out.println(name);     // ī�޶� ���÷� ���ڸ� PCamera�� �ݵ�� ����־���ϴ� ��ɿ� ���ؼ��� final�� �ɾ���� ��
//            }
//        }
//        class PCamera extends Camera {
//            int battery;
//            void take() {           // �̷��� �ؾ� ����� �޼��� �������̵��� ����
//                super.take();           // �θ�Ŭ���� �޼����� ����� ����ְ� �ʹٸ�, �̷��� ������ָ� �Ǵ� ��. �ڵ� �����ϱ�
//                System.out.println(sheets);
//                System.out.println(battery);
//            }
//        }
//        PCamera c = new PCamera();
//        c.name = "dd";
//        c.sheets = 3;
//        c.battery = 23;
//        c.take();




//        abstract class TV {             // �߻�Ŭ������ �޼��带 �� ����ϴ���.
//            abstract void powerOn();        // �ڽ�Ŭ������ ������ �ҹ��ϰ� ������ ���� ����� �߻�޼���� �ھƳ���
//            abstract void volumeUp();
//
//        }
//        class S_TV extends TV {
//            void powerOn() {            // �׷� ���� ��ǰ���� �װ� ��ӹ��� ���Ŀ� �������̵������ν� ��üȭ�ϸ� �Ǵ� ��
//                System.out.println("S_TV on");
//            }
//            void volumeUp() {
//                System.out.println("S_TV up");
//            }
//        }
//        class L_TV extends TV {
//            void powerOn() {
//                System.out.println("L_TV on");
//            }
//            void volumeUp() {
//                System.out.println("L_TV up");
//            }
//        }
//        S_TV tv = new S_TV();
//        tv.powerOn();
//        tv.volumeUp();
//        L_TV tv2 = new L_TV();          // ���� tv�� tv2 ������ ��ü�� �ƴ�, tv��� ��ü�� S_Tv���� L_TV�� ����Ÿ���ϴ� ��Ȳ�� ���� ������ �����ϰ� ������ �� ����.
//        tv2.powerOn();             // �ּ����� ��������, ���ϴ� ��ü�� ����� �� �����Ƿ� ���������� ����.
//        tv2.volumeUp();



//        int age = 25;       // int���� double�� �� ū ������ �ڷ���.
//        double avgAge = age;  // ������ ����ȯ�� ������. (double)age �̷��� �Ƚᵵ ���� �Ȼ���

//        double avgAge = 24.56;
//        int age = (int)avgAge;      // �̷� ��� ��ȯ�ڸ� Ȱ���� ����� ����ȯ�� �ʼ�
//        System.out.println(age);        //24�� ���



//        class Employee{
//            String name;
//            int No;
//            void getInfo(){
//            }
//        }
//        class Manager extends Employee {
//            Employee[] employeeList;        // �ű� ����
//            void getInfo(){     // �޼��� �������̵�.       �̷ν� �θ��ڽ�Ŭ���� �� ������ �߻�
//                System.out.println("dd");
//            }
//        }
//        Manager m = new Manager();
//        Employee e = new Employee();
//        System.out.println(m instanceof Manager);     // Ư�� ��ü�� � Ŭ������ �ν��Ͻ��� �ش��ϴ��� ���θ� Ȯ���ϴ� �Լ�. T/F�� ��ȯ
//        System.out.println(m instanceof Employee);     // m�� Employee Ŭ������ ����� ���� Manager Ŭ������ ��ü�̹Ƿ� �̰͵� True
//        System.out.println(e instanceof Manager);   // �̰� False
//        Employee e2 = new Employee();
//        Manager m2 = new Manager();
//        Manager m3 = (Manager)e2;       //      �̰� �� ������ �ȶߴ���, ���� �ǹ����� �ٽ� ������ �ʿ�
//        System.out.println(e2 instanceof Manager);      //  �̰� ������. m3�� ����



    }
}

