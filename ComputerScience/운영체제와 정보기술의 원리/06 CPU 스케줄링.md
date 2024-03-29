# Chapter 06: CPU 스케줄링

---

CPU는 프로그램의 기계어 명령을 실제로 수행하는 컴퓨터 내 중앙처리장치다.

- 프로그램이 메모리에 올라가면, **프로그램 카운터**라는 레지스터가 당장 수행해야 할 코드의 메모리 주소값을 가진다.

- CPU는 프로그램 카운터가 가리키는 주소의 기계어 명령을 하나씩 수행한다.

- 하나의 CPU를 여러 프로그램이 사용하기 위해서는 **시분할** 환경에서 효율적인 관리가 필요하다.

프로그램 실행과 관련된 **기계어 명령**은 CPU에서 수행되는 명령, 메모리 접근을 필요로 하는 명령, 입출력을 동반하는 명령으로 나눌 수 있다.

- **CPU** 내에서 수행되는 명령의 예시가 **Add 명령**이다.
  
  - 레지스터에 있는 두 값을 더해 레지스터에 저장하는 명령으로, CPU 내에서 수행되는 매우 빠른 명령이다.

- **메모리 접근**을 수행하는 명령은 **Load 명령**과 **Store 명령**이 있다.
  
  - Load는 메모리에 있는 데이터를 CPU로 읽어들이는 명령이고
  
  - Store는 CPU에서 계산된 결과값을 메모리에 저장하는 명령이다.
  
  - 비교적 짧은 시간에 수행이 가능하고, *여기까지는* 사용자 프로그램이 직접 실행 가능한 **일반명령**에 해당한다.

- **입출력 작업**을 동반하는 명령 또한 자주 발생한다.
  
  - 키보드, 마우스, 디스크 등 입출력 장치와 연관된 작업들이다.
  
  - 일반적으로 다른 둘에 비해 아주 오랜 시간이 소요되고, **모든** 입출력 명령은 **특권명령**에 해당한다.

결과적으로 사용자 프로그램이 수행되는 과정은 **CPU 작업**과 **I/O 작업**의 반복이다.

- 사용자 프로그램이 CPU를 직접 다루는 빠른 전자의 과정을 **CPU 버스트**라 하고,

- CPU 제어권이 운영체제로 넘어가고 그 자체도 매우 느린 후자의 과정을 **I/O 버스트**라고 부른다.

- CPU 버스트는 I/O 작업 사이, I/O 버스트는 CPU 작업 사이라고 보면 된다.

프로그램마다 CPU 버스트와 I/O 버스트 간의 비율은 천차만별이다.

- **I/O 바운드 프로세스**는 I/O 버스트의 비중이 상대적으로 높은 프로세스,

- **CPU 바운드 프로세스**는 CPU 버스트의 비중이 상대적으로 높은 프로세스를 말한다.

- 주로 I/O 바운드 프로세스는 사용자로부터의 **인터랙션**이 지속되는 **대화형 프로그램**들이고, CPU 바운드 프로세스는 **계산 위주의 프로그램**들이다.

**CPU 스케줄링**이란, 이와 같이 **CPU 사용 패턴**이 상이한 여러 프로그램을 시스템 내부에서 효율적으로 실행하기 위해 필요하다.

실제 프로세스들을 분석해보면, **다수의 짧은 CPU 버스트**와 **소수의 긴 CPU 버스트**로 구성됨을 확인할 수 있다.

- 즉, 대부분의 프로세스는 CPU를 잠깐만 사용하고 I/O 작업을 수행 중이라는 것.
  
  - 이러한 경우 거의 **대화형 프로그램**이기 때문에, 사용자에게 최대한 빠른 응답을 돌려줄 필요가 있다.
  
  - 어차피 CPU 작업이 길지도 않으니 최대한 빨리 할당해서 처리해줄 필요가 있다는 뜻.

- 다시 말해, CPU 스케줄링 시 I/O 바운드 프로세스는 **우선순위가 높다**.
  
  - **대화형 프로세스**에서 빠른 응답성 제공이 가능하고,
  
  - CPU는 잠깐 사용하고 곧바로 I/O 작업에 돌입할 수 있다는 점에서 **I/O 장치의 효율성을 높이는** 효과도 있다.

---

## CPU 스케줄러

CPU 스케줄러는 **준비** 상태의 프로세스 중 어디에 CPU를 할당할지 결정하는 운영체제의 코드다.

**타이머 인터럽트** 발생 시 CPU 스케줄러가 호출되어, **준비 큐**에서 기다리고 있는 프로세스 중 하나에게 CPU를 할당하게 된다.

그 외에도 CPU 스케줄링이 필요한 경우는

- 실행 상태의 프로세스가 I/O 요청 등으로 인해 **봉쇄** 상태로 바뀌거나

- 실행 상태의 프로세스가 **타이머 인터럽트** 발생으로 인해 준비 상태로 바뀌거나

- I/O 요청이 완료되어 봉쇄 상태의 프로세스가 준비 상태로 바뀌거나
  
  - (I/O 요청이 완료된 프로세스는 인터럽트 당한 프로세스보다 우선순위가 높고, **문맥교환**을 통해 CPU를 할당받게 된다)

- 실행 상태의 프로세스가 스스로 종료되는 경우가 있다.

구체적인 방식에는 **비선점형** 방식과 **선점형** 방식이 있다.

- 비선점형 방식은, CPU를 획득한 프로세스가 **스스로 반납하기 전까지는** CPU를 빼앗기지 않는다.

- 선점형 방식은, 프로세스로부터 CPU를 **강제로** 빼앗을 수 있다.
  
  - 빼앗는 방식으로 대표적인 것이 **타이머 인터럽트**.
  
  - 위의 4가지도 1,4는 비선점형, 2,3은 선점형으로 구분할 수 있다.

---

## 디스패처

선택된 프로세스에게 **실제로 CPU를 이양하는 작업**이 필요한데, 이를 수행하는 운영체제의 코드를 **디스패처**라고 부른다.

디스패처는 수행 중이던 프로세스의 문맥을 **해당 프로세스의 PCB에 저장**하고, 선택된 프로세스의 **문맥을 PCB로터 복원**한 뒤 시스템 상태를 **사용자모드로 전환**하여 **CPU를 넘기**는 과정을 수행한다.

사용자 프로그램은 복원된 문맥 중 **프로그램 카운터**로부터 당장 수행해야 할 코드의 주소를 찾을 수 있다.

디스패처가 *하나의 프로세스를 정지시키고 다른 프로세스에게 CPU를 전달하기까지 걸리는 시간*을 **디스패치 지연시간**이라고 하는데, 그 대부분은 **문맥교환**에 따른 오버헤드다.

---

## 스케줄링의 성능 평가

스케줄링 기법의 성능 평가를 위한 여러 지표들은, **시스템 관점**의 지표와 **사용자 관점**의 지표로 나눌 수 있다.

- 시스템 관점 지표는 CPU 이용률과 처리량이 있고

- 사용자 관점 지표는 소요시간, 대기시간, 응답시간 등 시간 관련 지표가 있다.

**CPU 이용률**은, 전체 시간 중 **CPU가 일을 한 시간의 비율**을 뜻한다.

- CPU는 고비용 자원이므로, 이용률이 높을수록 시스템 전체의 성능이 높다.

- CPU의 휴면 시간을 최대한으로 단축하는 것은 스케줄링의 중요한 목표다.

**처리량**은 주어진 시간 동안 **준비 큐**에서 기다리고 있는 프로세스 중 몇 개를 끝마쳤는지를 나타낸다. **CPU 버스트를 완료한 프로세스의 개수**라고 볼 수 있다.

- CPU 버스트가 짧은 프로세스에게 우선적으로 CPU를 할당함으로써 달성 가능하다.

**소요시간**은 프로세스가 CPU를 요청한 시점부터 **CPU 버스트가 끝날 때까지** 걸린 시간이다.

- 준비 큐에서 기다린 시간과 CPU를 실제로 사용한 시간의 합이다.

**대기시간**은 CPU 버스트 **기간 중** 프로세스가 준비 큐에서 기다린 시간의 합이다.

- **시분할** 시스템에서는 한 번의 CPU 버스트 중에도, **타이머 인터럽트**로 인해 준비 큐로 돌아가 기다리는 상황이 여러 번 발생할 수 있다.

- 그러한 대기시간의 총합이다.

**응답시간**은 프로세스가 준비 큐에 들어온 후, 처음 CPU를 획득하기까지 기다린 시간이다.

- **타이머 인터럽트**가 빈번할 수록 *처음* CPU를 획득하기는 쉽다.

- 즉, **대화형 시스템**에 적합한 성능 척도고, 사용자 입장에서 중요한 척도다.

---

## 스케줄링 알고리즘

### 선입선출 스케줄링

FCFS 스케줄링. 준비 큐에 **도착한 순서대로** CPU를 할당하는 방식이다.

CPU가 자발적으로 CPU를 반납할 때까지 빼앗지 않는다.

- 얼핏 합리적인 방식이지만, 경우에 따라 비효율이 매우 커질 수 있다. CPU 버스트가 긴 프로세스 하나가, CPU 버스트가 짧은 프로세스 다수의 작업을 막아버릴 수 있기 때문.
  
  - 이러한 현상을 **콘보이 현상**(Convoy effect)이라고 한다.

- CPU를 **잠깐만** 사용하면 I/O 작업을 수행하러 가는 다수의 I/O 바운드 프로세스가 준비 큐에 체류하면서 **평균 대기시간**이 길어지고, **I/O 장치 이용률**까지 낮아지는 결과가 나타날 수 있다.

즉, FCFS에서는 먼저 도착한 프로세스의 성격에 따라 평균 대기시간이 크게 달라진다.

### 최단작업 우선 스케줄링

SJF 스케줄링 알고리즘은 CPU 버스트가 **가장 짧은** 프로세스에게 제일 먼저 CPU를 할당하는 방식이다.

프로세스들이 준비 큐에서 기다리는 전체적인 시간이 줄어들기 때문에, **평균 대기시간**을 짧게 하는 데에는 최적의 알고리즘이다.

**비선점형** 방식과 **선점형** 방식으로 구현될 수 있다.

- 비선점형 방식은, CPU를 **자진 반납하기 전까지** CPU를 빼앗지 않는 방식이다.

- 선점형 방식은, 현재 CPU를 할당받은 프로세스보다 더 짧은 CPU 버스트를 가진 프로세스가 준비 큐에 들어오면 **CPU를 빼앗아** 더 짧은 쪽에 부여하는 방식이다.
  
  - 이러한 방식은 **SRTF**이라고 한다.

프로세스들이 준비 큐에 도착하는 시간이 **불규칙한 환경**에서는, 선점형 방식이 **평균 대기시간**을 최소화하는 최적 알고리즘이다.

- **시분할** 환경에서는 사실상 이게 일반적이다.

반면 프로세스들이 준비 큐에 **한꺼번에 도착**하는 환경에서는, 비선점형 방식과 선점형 방식이 동등한 결과를 나타낸다.

SJF 스케줄링을 구현하면서 발생하는 어려움은, **프로세스의 CPU 버스트 시간을 미리 알 수가 없다**는 점이다. 그래서 **예측**을 통해 CPU 버스트 시간을 구한다.

CPU 버스트 예측 시간 $T_(n+1)$은 **과거의 CPU 버스트 시간**을 통해 예측이 이루어진다.

$$
T_(n+1) = at_n + (1-a)T_n
$$

- $t_n$은 n번째 실제 CPU 버스트 시간이고,

- $T_n$은 n번째 CPU 버스트 예측시간이며,

- $a$는 0과 1 사이 상수값이다. 위 두 변수 간의 비중을 조절하는 매개변수에 해당한다.
  
  - a가 0이면 계속 고정된 값을 예측값으로 사용하고,
  
  - a가 1이면 실제 CPU 버스트 값을 그대로 예측값으로 사용하는 식이다.
  
  - 보통은 0과 1 사이로 두어, 가중평균으로 구현한다.

- 연속적인 상황을 생각해보면, 과거의 CPU 버스트 시간들은 미래의 CPU 버스트 시간을 예측하는 데 사용되지만, **최근의 CPU 버스트 시간일수록** 가중치가 더 높게 된다.

SJF 알고리즘이 **평균 대기시간**을 최소화하는 것은 사실이지만, 그것이 무조건 좋은 방식이라고는 할 수 없다. CPU 바운드 프로세스의 대기시간이 무한정 길어질 우려가 있기 때문.

- CPU 버스트가 긴 프로세스가 영원히 CPU를 할당받지 못하고 준비 큐에 대기만 하는 현상을 **기아 현상**(starvation)이라고 한다.

### 우선순위 스케줄링

준비 큐에서 기다리는 프로세스들 중, **우선순위**가 가장 높은 프로세스에게 CPU를 할당한다.

우선순위는 **우선순위값**을 통해 표시하고, 그 값이 작을수록 우선순위는 높다.

결국에는 우선순위값 결정 방식이 중요하다.

- CPU 버스트 시간을 기준으로 할 수도 있고, 시스템 관련성을 기준으로 할 수도 있다.

우선순위 스케줄링 또한 비선점형과 선점형 방식으로 각각 구현될 수 있다.

- 현재 실행 중인 프로세스보다 우선순위가 높은 프로세스가 준비 큐에 들어왔을 때 CPU를 빼앗기는 지 여부에 따라 구분될 것.

SJF와 마찬가지로 **기아 현상**이 발생할 수 있다.

- 하지만 우선순위 스케줄링에서는 **노화** 기법을 사용해 이 문제를 해결할 수 있는데,

- 기다리는 시간이 길어질수록 우선순위를 조금씩 높이는 방식이다.

### 라운드 로빈 스케줄링

**시분할** 시스템의 성질을 잘 활용하는 스케줄링 방식으로, 각 프로세스가 CPU를 연속적으로 사용할 수 있는 시간을 **특정 시간으로 제한**하여, 그 시간이 경과하면 **CPU를 회수**해 준비 큐의 다른 프로세스에게 할당하는 방식이다. CPU를 빼앗긴 프로세스는 준비 큐의 **제일 뒤**에 줄을 서게 된다.

각 프로세스마다 한 번에 CPU를 연속적으로 사용할 수 있는 최대 시간을 **할당시간**(**time quantum**)이라 한다.

- 할당시간이 너무 길면, FCFS와 별 차이가 없게 된다.

- 반대로 너무 짧으면, **문맥교환**의 오버헤드가 너무 커진다.
  
  - 문맥교환이 너무 잦으면, 실질적인 **CPU 이용률**이 낮아져 비효율적이다.

- 일반적으로는 수십 밀리초 정도로 설정되어, **대화형 프로세스**에 CPU가 다시 할당되기까지 오랜 시간이 걸리지 않도록 조절한다.

라운드 로빈 스케줄링은 여러 종류의 **이질적인 프로세스가 같이 실행**되는 환경에 효과적이다. 

- n개의 프로세스가 있고 할당시간이 q면, **모든** 프로세스가 (n-1)q 시간 이내에 **적어도 한 번** CPU를 할당받는다고 **확정**할 수 있기 때문.

- **대화형 프로세스**에서 1초 이내 정도의 응답시간을 보인다면, 인간은 충분히 빠르다고 느낄 수 있다.

- CPU 버스트가 길수록, 프로세스 **대기시간**이 비례하여 길어지기 때문에 **형평성**에도 부합한다.
  
  - SJF가 전체 성능 향상을 위해 CPU 버스트 시간이 긴 프로세스를 일방적으로 희생시켰던 것과 대비된다.

- SJF와 비교할 때 **평균 대기시간**은 더 길어지지만, **응답시간**은 훨씬 짧을 수 있다.

할당시간이 만료되면 **타이머 인터럽트**를 사용하여 CPU를 회수한다. 자진 반납할 수도 있다.

FCFS와 비교할 때,

- **평균 대기시간**과 **평균 소요시간**은 라운드 로빈 스케줄링에서 더 길다.

- **평균 응답시간**은 라운드 로빈 스케줄링에서 더 짧다.

- 단, **프로세스 간** 대기시간과 소요시간의 **편차**는 크게 줄어든다.

- 일반적인 시스템에서 프로세스들의 CPU 버스트 기간은 불균일한데, CPU 버스트 시간에 비례하여 대기시간과 소요시간을 증가시킨다는 점은 매우 합리적이고, 그 편차도 최소화된다는 점에서 안정적이다.

### 멀티레벨 큐

**준비 큐를 여러 개로 분할해 관리**하는 스케줄링 기법으로, 프로세스들이 CPU를 기다리기 위해 **여러 줄로** 서서 기다리게 된다.

- 어떤 줄에 CPU를 우선적으로 스케줄링할 것인지, 프로세스가 도착했을 때 어느 줄에 세워야할 것인지 등의 문제를 함께 해결해야 한다.

일반적으로 **성격이 다른** 프로세스들을 별도로 관리하여 스케줄링을 적용하기 위해 사용된다.

- 보통 **대화형 작업**을 위한 **전위 큐**와 **계산 위주 작업**을 위한 **후위 큐**로 분할된다.

- 전위 큐는 빠른 응답시간을 위해 **라운드 로빈**을,

- 후위 큐는 문맥교환 오버헤드를 최소화하기 위해 **FCFS** 기법을 적용하는 편이다.

어느 큐에 먼저 CPU를 할당할 지에 대해서도 결정되어야 한다.

- **고정 우선순위 방식**은, 큐에 고정적인 우선순위를 부여하여 서비스한다.
  
  - 위 예시에서는 전위 큐에 무조건적으로 높은 우선순위를 매길 수 있다.

- **타임 슬라이스 방식**은, 특정 큐의 기아 현상을 해소하기 위해 CPU 시간을 비율 단위로 할당하는 방식이다.

### 멀티레벨 피드백 큐

여러 개의 큐를 사용하면서도, 그 안의 프로세스가 **다른 큐로 이동 가능**하다는 차이점이 있다.

- 우선순위 스케줄링에서 기아 현상을 해결하기 위해 **노화 기법**을 적용했었는데,

- 멀티레벨 피드백 큐에서도 같은 원리를 적용할 수 있다.
  
  - 오래 기다린 프로세스를 우선순위가 보다 높은 큐로 이동시키는 식.

즉, 멀티레벨 피드백 큐를 정의하는 요소에는 **큐의 수**, **각 큐의 스케줄링 알고리즘**, **프로세스를 상위 큐로 승격시키는 기준**, **프로세스를 하위 큐로 강등시키는 기준**, **프로세스가 도착했을 때 들어갈 큐를 결정하는 기준** 정도를 꼽을 수 있다.

### 다중처리기 스케줄링

**CPU가 여러 개**인 시스템을 **다중처리기 시스템**이라고 부른다.

이 떄 CPU 스케줄링은 보다 복잡한 문제가 되는데, 일부 CPU에 작업이 편중되는 등의 현상을 막기 위해 적절한 **다중처리기 스케줄링**이 필요하다.

- **부하균형** 메커니즘을 필요로 한다.

- **대칭형** 다중처리는 각 CPU가 각자 알아서 스케줄링을 결정하는 방식이고

- **비대칭형** 다중처리는 하나의 CPU가 다른 모든 CPU의 스케줄링과 데이터 접근을 책임지는 방식이다.

### 실시간 스케줄링

**실시간 시스템**은 각 작업마다 주어진 **데드라인**이 있어서, 그 안에 *반드시* 작업을 처리해야 한다.

- **경성** 실시간 시스템은 원자로 제어 같이 *위험하기 때문에* 정말 반드시 시간을 지켜야 하는 시스템이고,

- **연성** 실시간 시스템은 동영상 스트리밍 같이 데드라인은 있지만 못 지켰다고 해서 위험하지는 않은 시스템이다.

앞서 다룬 스케줄링 기법은 모두 시분할 환경에서의 방식으로, 데드라인 개념을 별도로 두지 않았다.

실시간 환경의 경우, *먼저 온* 요청보다도 **데드라인이 얼마 남지 않은** 요청을 먼저 처리하는 게 중요한 **EDF** 스케줄링을 널리 사용한다.

---

## 스케줄링 알고리즘의 평가

큐잉모델은, 확률분포를 통해 이론적으로 각종 성능지표를 계산한다.

구현 및 실측은 실제 커널의 소스 코드를 수정해서 직접 프로그램의 실행시간을 측정한다.

시뮬레이션은 가상의 CPU 스케줄링 프로그램을 작성해 CPU 요청을 입력값으로 넣고 확인하는 방법이다.
