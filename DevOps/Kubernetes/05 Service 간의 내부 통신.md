# Service 간의 통신

웹 애플리케이션은 DB를 포함하여 다른 컨테이너와 **통신**하면서 서비스를 제공할 수 있다.

1개 Pod에 1개의 컨테이너만 사용하라는 제약은 없기 때문에, 웹 애플리케이션 컨테이너와 DB 컨테이너가 **하나의 파드에 올라가 있다면** 상호 간에는 **로컬호스트** 호출이 가능하다.

- 일반적인 단일 호스트 서버 환경이나 로컬 개발환경이 흔히 이렇다.

하지만 파드와 컨테이너는 1:1로 매칭하여 제공하는 것이 바람직하고, 결과적으로 **파드** 내지는 **서비스** 간의 통신 연결이 반드시 필요하다.

## Service 간 통신을 위한 DNS

엄밀히 말해서 Pod끼리 직접 통신하는 것은 바람직하지 못하고, 불가능에 가깝다.

앞서 다룬 바와 같이 **Service**로 랩핑하여 **고정된 IP**를 부여받은 상태에서 상호 간 통신이 이루어져야 한다.

하지만, Service 조차도 쿠버네티스 자체가 재시작되거나 한다면 **IP가 변경**되기 때문에,

Pod에서 **고정된 IP를 지목한다면** 서비스가 안정적으로 운영될 수 없다.

그래서, **쿠버네티스만을 위한 내부 DNS** 처리가 필요하다.

### kube-DNS

쿠버네티스는 내부적으로 DNS 처리를 제공한다.

정확히는, DNS 처리를 위한 `kube-dns` 라는 **Service**가 상시 동작하고 있다.

하지만 `kubectl get all` 에서 kube-dns는 보이지 않는데,

이는 해당 Service가 **default**가 아닌 **kube-system** **네임스페이스**에서 동작하기 때문이다.

## Namespace

네임스페이스는 쿠버네티스가 제공하는 여러 레이어들 중에서도 최상위에 해당한다.

**시스템 혹은 애플리케이션** 간의 **경계**를 구분짓는 단위로 볼 수 있고,

별달리 지정하지 않았다면 사용자가 생성 및 관리하는 모든 객체는 **default** 네임스페이스에 할당된다.

### 쿠버네티스 시스템 Namespace

쿠버네티스는 자체적으로

- **kube-system**

- **kube-public**

두 가지의 Namespace를 생성하여 관리하고 있다.

해당 네임스페이스에서 관리하는 개체들은

`kubectl get all -n kube-system` 같이 **명령어 상 지정하지 않는다면** 굳이 조회되지 않는다.

**kube-dns**가 바로 **kube-system**에서 동작하기 때문에, 일견 눈에 보이지 않았던 것이다.

그 외에도

- `kubectl describe svc kube-dns -n kube-system` 같은 명령어로 좀 더 탐구해볼 수 있다.

## DB 설치 후 연동하기

웹 애플리케이션과 별도로 DB 파드를 띄우고 연결해본다.

- mysql 을 사용하고, ClusterIP로 3306번 포트를 Service 오픈하며, name은 database로 지정한다.

웹 애플리케이션 파드로 sh 접속한 뒤, `/etc/resolv.conf` 를 확인해보면 아래와 같이 확인 가능하다.

```vim
nameserver 10.96.0.10
search default.svc.cluster.local svc.cluster.local cluster.local
options ndots:5
```

`kubectl get all -n kube-system` 에서 확인 가능한 `kube-dns`의 내부 IP 주소는,

위에서 nameserver 값에 해당하는 10.96.0.10 과 정확히 일치한다.

- 이는 쿠버네티스가 내부적으로 리눅스 컨테이너를 새로 생성할 때, 알아서 `nameserver` 값을 kube-dns로 처리해준다는 의미.

이 상태에서 `nslookup database` 하면, `kubectl get all` 에서 조회되는 mysql 서비스의 IP가 정확히 조회된다.

- 아예 mysql 클라이언트까지 설치한 뒤 

- `mysql -h database -uroot -ppassword fleetman` 같은 방식으로 접속해보면, CLI 로 DB 접속까지 가능하다.

- `-h` 옵션으로 통신 엔드포인트를 지정해야 하는데, 거기에 **database**를 적은 것만으로도 DNS 처리가 완성된 것
