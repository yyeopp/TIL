//class Car {
//    private int serialNumber;    // 다른 클래스에서 접근 불가
//    protected String name;
//    int speed;          // class 부분에 오른쪽 클릭하고 'Generate'를 눌러서, 변수에 대해 getter와 setter 메서드를 자동으로 작성해주는 기능이 있다!!
//    static String carStatus = "active";        // static을 예약어로 사용하여, carStatus를 "클래스변수"로 지정. 이러면 Car 클래스에 속하는 인스턴스들은 자동으로 active라는 값을 carStatus로 가지게 됨. 메모리 절약 차원에서 클래스 내부 객체들의 공통변수를 밖으로 빼준 것
/////////////////////////////////////////////// 같은 방식으로 static을 붙여서 "클래스메서드"를 선언할 수 있다. 클래스메서드에서는 클래스변수만을 갖다쓸 수 있다. static이 붙어있지 않은 인스턴스변수는 인용할 수 없음.
//
//
//
//    final static double rate = 15.25;      // final 예약어는 rate라는 변수를 '상수'로 만들어준다. 처음 선언할 때를 제외하고는 이후 변경이 불가능함
//    public static void change() {
//        rate++;             // 에러 발생. rate는 final로 선언됐기 때문.
//    }               // 그와 별개로, rate와 change를 밑에서 갖다쓰려면 static을 꼭 붙여줘야 함을 확인 가능함
//}                     // 클래스변수를 static으로 선언해주면서 final을 붙이지 않을 시, 하위 객체에서 해당 변수를 다르게 설정하는 게 가능하다. 객체가 Car()라고 말하면서 객체.rate = 12 이렇게 설정해도 무방하단 뜻. 그걸 막으려면 final을 적절히 사용해줘야 한다.
//
//
//    @Override                   // Generate 메뉴에서 toString()을 누르면 변수값을 나열해주는 toString()메서드를 자동 작성할 수 있다.
//    public String toString() {
//        return "Car{" + "serialNumber=" + serialNumber +", name='" + name + '\'' +", speed=" + speed + '}';
//    }
//
//
//    public int getSerialNumber(int managerID) {  // serialNumber가 private로 선언되었지만 같은 클래스 내부에서 해당 변수에 접근 가능한 메서드를 선언
//        if (managerID == 12345) {       // 매니저아이디를 입력받아서, 그와 일치할 시 get메서드는 시리얼넘버를 리턴한다.
//            return serialNumber;          // @@@@@@ 이렇게 private 변수를 받아오는 메서드를 "getter메서드" 라고 통칭한다.
//        }
//        return 0;       // 틀리면 0을 리턴
//    }
//    public void setSerialNumber(int serialNumber){      // 마찬가지로 시리얼넘버에 접근 가능한 public 메서드를 선언해서, 시리얼넘버를 입력받을 수 있음
//        this.serialNumber = serialNumber;       // void이기 때문에 return이 필요없음. this. 는 파이썬에서의 self에 해당. this.를 통해 set메서드에 명시된 시리얼넘버 변수를 지칭함을 명시
//    }                             // @@@@@@@@마찬가지로 이렇게 private 변수를 설정하는 메서드를 "setter메서드"라고 통칭한다.
//
//}

public class SWEA_2 {
    public static void main(String args[]) {


//        Car.change();         // static이 붙어있을 때
//        System.out.println(Car.rate);     // 기본적으로 실행이 가능함함

//        System.out.println(Car.crStatus);      // 위에서 static String carStatus를 활성화하면, Car 클래스에 속하는 별도의 객체를 생성하지 않아도 "Car"라는 클래스 이름으로부터 직접 해당 클래스변수를 출력해올 수 있다.
//        System.out.println("아무거나");   // 계속 쓰던 이 코드 역시, 자바가 기본적으로 깔고있는 "System"이라는 클래스의 "out"이라는 시스템변수를 사용하는 코드다.
//
//
//        Car Yellow = new Car();
//        Yellow.name = "Light";      // name은 같은 패키지라서 접근 가능
//        Yellow.speed = 300;     // default니까 접근 가능
        /////////////////////////Yellow.serialNumber = 3040;  // 이거 각주 풀면 에러뜸
//        Yellow.setSerialNumber(4929);   // !!!!!!!!!! 대신, Car 클래스에서 public으로 선언한 set메서드를 이용한다면 시리얼넘버를 내맘대로 지정할 수 있게 됨
//        System.out.println(Yellow.name + " : " + Yellow.speed); // 여기서 serialNumber는 프린팅도 못함. private로 보호되어있기 때문
//        System.out.println("Car No: " + Yellow.getSerialNumber(12345));     // !!!!!!!!! 대신, get 메서드를 이용한다면 시리얼넘버 프린팅이 가능함.
//        System.out.println(Yellow.toString());      // toString으로 정보를 확인하는 방법.
//


        // 클래스는 프로그램의 가장 기본적인 요소. 메모리 상 객체인 인스턴스를 생성하기 위한 템플릿.
        // 인스턴스는 객체가 메모리에 올라간 것. 메모리 상의 객체. 사실상 객체와 동의어
        // 객체는 현실 세계에 존재하는 유무형의 모든 것. 사람들이 의미를 부여하고 분류하는 논리적인 단위
        // 추상화는 현실의 객체들의 공통적 특성을 모아 일반화하는 것. 클래스 정의 시 핵심
        // 다형성은 동일 인터페이스를 이용해 서로 다른 구현 객체를 일관된 방법으로 사용할 수 있게 함. 프로그램 유지보수성 향상. 나중에 자세히 이해
        // 상속은 클래스들을 계층 구조화하여 기존의 클래스 변수와 메서드를 재사용하는 방식.
        // 정보은닉은, 클래스 속 변수와 메서드를 선택적으로 공개하거나 숨기는 기법. private와 public으로 선언 가능
        // 캡슐화는, 하나의 클래스 안에 변수와 메서드를 하나로 묶어 서로 독립적으로 동작하지 않도록 하는 그 자체를 뜻하는 말


//        class Car {
//            String name;         // Car 클래스에 name, currentSpeed, currenGear의 속성 부여
//            int currentSpeed;
//            int currentGear;
//            void start(){       // 메서드 start 선언
//                System.out.println(name + "의 시동켠다");
//                currentSpeed = 1;
//            }
//            void changeGear(int gear){   // 메서드 changeGear 선언. void로 선언하면 return이 필요없다고 하는데 아직 제대로 이해못함
//                System.out.println("기어를"+gear+"단으로변경");
//                currentGear = gear;
//            }
//            int getCurrentSpeed() {     // 메서드 선언. int로 선언했으니까
//                currentSpeed += currentGear * 10;
//                return currentSpeed;           // return을 통해 int값인 currentSpeed를 토해내야함. 이거를 getCurrentState를 통해 스트링형태로 프린트하는게 필요
//            }
//            void stop() {
//                System.out.println(name+"의 시동끈다");
//                currentSpeed = 0;
//                currentGear = 0;
//            }
//            String getCurrentState(){       // 마찬가지로 String을 토해내야 함
//                return name + "의 현재 속도: " + getCurrentSpeed();      // return을 통해 getCurrentSpeed() 메서드로 돌아감
//            }
//        }
//        Car myCar = new Car();      // Car 클래스의 인스턴스로 myCar 선언
//        myCar.name = "Red";         // 속성 설정
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


//        class Taxi extends Car {        // 위에있는 Car 클래스 활성화 시, 새로운 Taxi 클래스는 Car의 모든 변수와 메서드를 상속받는다. extends를 사용
//            int fare;       // 새로운 변수도 선언 가능
//            boolean passengerYn;
//        }     // 자바에서는 다중상속이 지원 안됨. 하나의 클래스는 하나의 상위 클래스만을 가질 수 있음
//        Taxi myTaxi = new Taxi();
//        myTaxi.name = "308";
//        myTaxi.currentGear = 2;
//        myTaxi.fare = 3400;
//        myTaxi.passengerYn = true;
//        System.out.println(myTaxi.getCurrentState());       // 아까와 같은 느낌으로 사용 가능


// Modifier에 대해서는 따로 정리해서 활용하는 게 필요하다.
        // 접근권한과 활용방법을 각각 표시할 수 있는데, class, 변수, 메서드에 붙일 수 있는 modifier가 각각 정해져있고 효과도 제각각이다. 따로 정리된 문서를 참고해야 할 것


//        int enScore[] = {55,70,45,90,100,90,95};
//        System.out.println("영어 평균: " + getAvgScore(enScore));       // 메인메서드에서 get메서드를 호출.
//        int koScore[] = {93,55,87,80,100,95,65};
//        System.out.println("국어 평균: " + getAvgScore(koScore));
//    }
//    private static double getAvgScore(int scoreList[]){     // 메인메서드와 별개로 get 메서드를 정의. int 형식의 scoreList[]를 매개변수로 받는 메서드
//        int sum = 0;
//        double avgScore = 0.0;
//        for (int i=0; i<scoreList.length; i++){
//            sum += scoreList[i];
//        }
//        avgScore = (double)sum/scoreList.length;        // double로의 변환자 포함시키는 거 주의
//        return avgScore;        // double 메서드이므로 return값이 필수. 계산된 avg 값이 메인메서드에서 구현됨


//        class Car {
//            String name;
//            int speed;
//            void printInfo(){        // 이걸 final void로 설정할 시, 자식클래스인 Taxi에서 printInfo 메서드를 "오버라이딩"할 수 없게 된다. final을 쓰는 순간 에러 발생
//                System.out.println(name + "의 현재속도: " + speed);
//            }
//        }
//        class Taxi extends Car {            // 같은 원리에서 클래스 이름 앞에 final을 넣을 시, 상속이 금지되는 클래스가 됨. Car에서 final 입력하면 바로 에러 발생
//            int currentoil;
//            void printInfo(){
//                System.out.println(name + "의 현재 연료: " + currentoil); // 메서드 오버라이딩. 자식 클래스에서 부모 클래스의 메서드 기능을 재정의
//            }
//        }
//        Taxi a = new Taxi();
//        a.printInfo();


//        abstract class SuperClass {     // 내부에 객체를 형성할 수 없는 '추상'클래스에 해당. 상속에 활용된다는 점에서 의미를 가짐.
//            void methodA() {
//                System.out.println("A실행");
//            }
//            abstract void methodB();            // "추상메서드"에 해당. 매개변수나 이름 정도는 선언했으나 구체적인 기능은 아직 정해지지 않음
//        }
//        class SubClass extends SuperClass {
//            void methodB(){             // 추상메서드를 자식클래스에서 "오버라이딩"함으로써 비로소 의미를 가지게 됨.
//                System.out.println("B실행");        // 반대로 말하면, 추상클래스를 상속받는 자식클래스가 제대로 기능하기 위해서는 부모클래스의 "모든 추상메서드"를 "오버라이딩"해야한다는 뜻. 자식클래스도 추상이면 또 모를까.
//            }
//        }
//////////////////        SuperClass Y = new SuperClass();        // 이거 풀면 오류뜸. SuperClass가 추상클래스라 객체 대입이 불가능
//        SubClass X = new SubClass();            // 대신 그걸 상속받은 SubClass에는 객체 대입이 가능.
//        X.methodA();
//        X.methodB();            // 이것도 멀쩡히 실행 가능.


//        class Employee {
//            String name;
//            int number;
//            int age;
//            String title;
//            String dept;
//            String grade;
//            public Employee(String name, int number, int age, String title, String dept, String grade) {
//                this.name = name;       // 밑에서는 n1같은 참조를 새로 도입했는데, 사실 그게 굉장히 번거로운 짓이기 때문에 그대로 name이라는 이름을 사용함
//                this.number = number;   // 근데 그렇게할 시 클래스의 멤버변수인 name과 생성자의 매개변수 name 간 혼동이 발생할 수 있음
//                this.age = age;         // 그래서, this라는 예약어를 통해 클래스의 멤버변수임을 확실하게 해줌줌                this.title = title;
//                this.dept = dept;       // 파이썬에서 self와 같은 기능
//                this.grade = grade;      // 아까했던것처럼 generate 메뉴에서 생성자메서드를 자동완성해줄 수 있음!!!!
//            }
//            Employee(String n1, int n2, int a, String t, String d, String g) {
//                name = n1;      // 여기서 Employee는 클래스와 같은 이름을 갖는 특별한 '메서드'
//                number = n2;    // 이후 해당 클래스에 속하는 객체를 생성할 시, 멤버 변수값을 초기화하는 메서드이다.
//                age = a;        // void를 허용하지 않고, 근데 Return도 없어도 됨. 완전 특별한 놈
//                title = t;      // 여러 개의 매개변수를 중복정의할 수 있는 특징
//                dept = d;       // 즉, 생성자는 "메서드의 일종". 그러다보니 접근권한에 관한 modifier가 앞에 올 수 있음
//                grade = g;
//            }
//        }
//        Employee kim = new Employee("김동완", 1543,30,"대리","안전부","우수");  // 이런식으로 멤버변수를 한꺼번에 초기화
//        Employee lee = new Employee();          // 클래스 정의 시 생성자메서드를 빼고 올 경우, 이후 새로운 객체를 아무내용 없이 클래스 인스턴스로 선언할 경우 "기본값"이 변수로 지정된다.
//        System.out.println(kim.name);       // 참고로 new 또한 생성자(?)
//        System.out.println(lee.name);       // name의 경우 Str이므로 null이 뜸. int라면 0이 기본값. 등등등..


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
//            Employee(){                 // 생성자메서드 역시 오버로딩이 가능하다. 객체마다 그때그때 필요한 데이터만을 초기화할 수 있어서 편리하다.
//                name = "No name";
//            }
//            void set(String name, int age){
//                this.name = name;
//                this.age = age;
//            }
//            void set(String name){     // set이라는 이름의 메서드가 클래스 내부에 3개나 선언됐지만, 담고있는 매개변수의 개수와 타입이 제각각이라서 충돌을 유발하지 않음.
//                this.name = name;       // 이런 기법을 "메서드 오버로딩" 이라고 한다. "중복정의"
//            }
//            void set() {                // 메서드 오버로딩의 기능은.. 따로 정리되어 있음.
//                name = "No name";
//                age = 0;
//            }               // 오버로딩이 제대로 되기 위해서는 매개변수의 "개수"와 "타입"이 달라야한다. String name + int age가 있는 상태에서 String name + int salary를 선언할 시 오버로딩 성립 불가. 객체를 입력할 시 어떤 메서드로 가야할 지 불분명하기 때문
//        }                       // 대신, int salary + String name으로 매개변수 "순서"를 달리하면 오버로딩할 수 있다. 입력할 때 헷갈릴 일이 없기 때문
//        Employee park = new Employee();
//        park.set("박재성",28);         // 이러면 첫번째 set이,
//        System.out.println(park.name);
//        park.set();                             // 이러면 세번째 set이 알아서 실행되는 구조.
//        System.out.println(park.name);          // println메서드 역시, 매개변수로 대입되는 다양한 매개변수에 대해 '오버로딩'이 되어있다. 그 덕분에 안에 어떤 자료형을 넣더라도 별다른 오류 없이 원하는 출력물을 얻을 수 있는 것!!!


//        int score = 10;
//        System.out.println("변경 전: " + score);
//        changeScore(score);
//        System.out.println("변경 후: " + score);          // score값은 메인 메서드에 존재하는 상태에서 change메서드에 "그 값인 10만" 복사해서 들어갔을 뿐 "그 주소인 score"는 바뀌지 않았다. 그래서 change 메서드 실행 이후에도 이 값은 10이 됨.
//    }
//    private static int changeScore(int score) {         // main 메서드와 별도로 메서드를 하나 더 선언, score라는 변수는 default로 선언됐으므로 같은 패키지 내에 있는 change 메서드가 가져올 수 있음
//        score = 100;
//        System.out.println("변경된: " + score);
//        return score;           // return 필요


//        int[] scorelist = {40, 78, 98};
//        System.out.println("변경전: " + scorelist[0]);
//        changeScore(scorelist);
//        System.out.println("변경후: " + scorelist[0]);     // 왜 기존의 list[0]값이 유지되지 못하지?
//    }
//    private static int[] changeScore(int[] scorelist) {         // 이게 아까 int score 매개변수로 집어넣은 거랑 뭐가 다르지..?
//        scorelist[0] = 100;
//        System.out.println("변경된: "+scorelist[0]);
//        return scorelist;


//        class Test {
//            int intSum(int... num){     // test 클래스의 intSum 메서드는 매개변수의 타입이 int로, 이름이 num으로 지정됐지만, 그 개수가 '가변적'임.
//                int sum = 0;
//                for (int i=0; i<num.length; i++){
//                    sum += num[i];      // 이거 좀 어색하지 않나? 메서드 매개변수에서 int[]라고 해줬어야 하지 않을까?
//                }
//                return sum;
//            }
//        }
//        Test x = new Test();
//        Test y = new Test();
//        System.out.println(x.intSum(1,2,3));
//        System.out.println(y.intSum(5,7,8,4,4,3,2));        // 이렇게 매개변수 개수를 맘대로 넣을 수 있다는 것.



//        class SuperClass {
//            int num1;
//            SuperClass() {      // SuperClass 내부 변수를 초기화시키는 생성자메서드. ()로 매개변수 자리가 비어있으므로 '기본생성자' 라고 함
//                num1=100;       // 만약에 이걸 SuperClass(int num) 이렇게 입력할 시, 자식 클래스에서 불러올 수 있는 '기본생성자'가 없기 때문에 오류가 발생한다.
//            }
//        }
//        class SubClass extends SuperClass {     // 상속받음
//            int num2;
//            SubClass() {        // 마찬가지로 생성자메서드인데, 특별히 SuperClass(); 를 호출하는 부분은 없다.
//                num2=10000;
//            }
//        }
//        SubClass sub = new SubClass();
//        System.out.println(sub.num1);      // 100이 정상 출력
//        System.out.println(sub.num2);      // 즉, 명시하지 않아도 자식클래스는 부모클래스의 '기본'생성자메서드를 호출해옴.



//        class Shape {
//            int x = 0;
//            int y = 0;
//            Shape() {           // 기본생성자.
//                this(0,0);      // 얜 뭐지 이렇게 써도 상관없는건가
//            }
//            Shape(int x, int y) {
//                this.x = x;
//                this.y = y;
//            }
//        }
//        class Circle extends Shape {                // 상속 시, 부모클래스의 private 변수는 상속되지 않는다. 자식클래스에서 부모클래스의 private변수에 접근(초기화)하려고 하면 오류 발생
//            int radius;                              // 그 외 변수들은 상속되지만, 자식 클래스에서 부모클래스의 변수와 같은 이름의 변수를 다르게 초기화할 경우 (자료형이 달라지는 경우 포함) 그걸 따라가게된다. 변수..오버라이딩?
//            Circle(int x, int y, int radius) {      // 얘도 자식클래스의 자체적인 생성자메서드
//                super(x, y);         // super는 파이썬의 self, 자바의 this가 했던 것처럼 특정 메서드를 지칭하는 단어. 자식클래스에서 부모클래스의 생성자를 호출할 때 사용. Circle 클래스로 입력받은 x,y값을 부모클래스 속 Shape 메서드에 집어넣겠다는 의미
//                this.radius = radius;       // super(int x, int y)라고 했더니 x랑 y를 local 매개변수로 인식한다. 이미 Circle에서 선언해놨으니 헷갈리는 모양
//            }                           // 만약 super부분을 아예 빼버릴 시, "기본생성자"가 호출되므로 x랑 y는 0으로 초기화될 수밖에 없다
//            void draw() {               // *** super()로 부모클래스의 생성자를 가져오려면 자식클래스 생성자 선언 직후 맨 윗줄에 있어야 한다. this.radius가 먼저나오면 오류
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
//                this.grade = 'A';           // 같은 이름의 변수에 대해 this와 super로 위치를 구분하는 모습.
//                super.grade = "A등급";
//                System.out.println(this.grade + "," + super.grade);
//            }
//        }
//        Manager kim = new Manager();
//        kim.printGrade();



//        class SuperClass {
//            void print(String str) {
//                System.out.println("호출");
//            }
//        }
//        class SubClass extends SuperClass {
//            void print() {          // 부모 클래스의 메서드와 같은 이름이라고 해서 "오버라이딩"이 아님. 매개변수 유형과 개수가 다르기 때문에, 이건 부모클래스로부터 모든 메서드를 상속받은 자식클래스에 print 메서드가 "오버로딩"된 것.
//                System.out.println("또 호출");
//            }
//        }
//        SubClass sub = new SubClass();
//        sub.print("테스트");           // 그래서 둘다 정상 실행됨.
//        sub.print();



//        class Camera {
//            String name;
//            int sheets;
//            void take() {         // 혹시 메서드에 오버라이딩을 허락하지 않으려면, final void로 선언하면 된다.
//                System.out.println(name);     // 카메라를 예시로 들자면 PCamera가 반드시 들고있어야하는 기능에 대해서는 final을 걸어놓는 식
//            }
//        }
//        class PCamera extends Camera {
//            int battery;
//            void take() {           // 이렇게 해야 제대로 메서드 오버라이딩이 가능
//                super.take();           // 부모클래스 메서드의 기능을 살려주고 싶다면, 이렇게 명시해주면 되는 것. 코드 절약하기
//                System.out.println(sheets);
//                System.out.println(battery);
//            }
//        }
//        PCamera c = new PCamera();
//        c.name = "dd";
//        c.sheets = 3;
//        c.battery = 23;
//        c.take();




//        abstract class TV {             // 추상클래스와 메서드를 왜 사용하는지.
//            abstract void powerOn();        // 자식클래스의 종류를 불문하고 무조건 들어가는 기능을 추상메서드로 박아놓음
//            abstract void volumeUp();
//
//        }
//        class S_TV extends TV {
//            void powerOn() {            // 그럼 실제 제품에서 그걸 상속받은 이후에 오버라이딩함으로써 구체화하면 되는 것
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
//        L_TV tv2 = new L_TV();          // 만약 tv와 tv2 별개의 객체가 아닌, tv라는 객체가 S_Tv에서 L_TV로 갈아타야하는 상황에 대해 굉장히 유연하게 대응할 수 있음.
//        tv2.powerOn();             // 최소한의 수정으로, 원하는 객체를 사용할 수 있으므로 유지보수가 편리함.
//        tv2.volumeUp();



//        int age = 25;       // int보다 double이 더 큰 범위의 자료형.
//        double avgAge = age;  // 묵시적 형변환이 가능함. (double)age 이렇게 안써도 오류 안생김

//        double avgAge = 24.56;
//        int age = (int)avgAge;      // 이런 경우 변환자를 활용한 명시적 형변환이 필수
//        System.out.println(age);        //24로 출력



//        class Employee{
//            String name;
//            int No;
//            void getInfo(){
//            }
//        }
//        class Manager extends Employee {
//            Employee[] employeeList;        // 신규 변수
//            void getInfo(){     // 메서드 오버라이딩.       이로써 부모자식클래스 간 차별점 발생
//                System.out.println("dd");
//            }
//        }
//        Manager m = new Manager();
//        Employee e = new Employee();
//        System.out.println(m instanceof Manager);     // 특정 객체가 어떤 클래스의 인스턴스에 해당하는지 여부를 확인하는 함수. T/F값 반환
//        System.out.println(m instanceof Employee);     // m은 Employee 클래스의 상속을 받은 Manager 클래스의 객체이므로 이것도 True
//        System.out.println(e instanceof Manager);   // 이건 False
//        Employee e2 = new Employee();
//        Manager m2 = new Manager();
//        Manager m3 = (Manager)e2;       //      이게 왜 오류가 안뜨는지, 무슨 의미인지 다시 이해할 필요
//        System.out.println(e2 instanceof Manager);      //  이거 오류남. m3도 오류



    }
}

