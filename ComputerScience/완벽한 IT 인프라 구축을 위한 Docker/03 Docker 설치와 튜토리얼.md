# 제 2부 기본편

---

# Chapter 03: Docker 설치와 튜토리얼

---

## Docker 설치와 작동 확인

### Docker의 클라이언트 툴

Docker는 보통 Linux 배포판 상에서 작동하지만, 개발 환경에 이용하기 위해 클라이언트 PC용 툴을 제공한다.

#### Docker for Mac

macOS 용 네이티브 어플리케이션은, **Hypervisor 프레임워크**인 `xhyve`를 사용하고 있다.

macOS 위에 xhyve를 두고, 그 위에서 Docker for Mac이 구동된다.

#### Docker for Windows

Windows OS 용 어플리케이션은 MS가 제공한 x64용 가상화 시스템인 `Hyper-V`를 하이퍼바이저로써 사용하고 있다.

### Linux에 설치하기

APT(Advanced Packaging Tool)은, Ubuntu를 비롯한 Debian 계열 OS에서 작동하는 **패키지 관리 시스템**이다.

apt 명령어를 통해 패키지 리스트 업데이트가 가능하고, Docker CE도 이걸로 설치한다.

### Docker에서 'Hello world'

Docker 컨테이너를 작성하고 콘솔 상에 'Hello world'를 echo 표시하는 구문은 다음과 같다.

`docker container run ubuntu:latest /bin/echo 'Hello world'`

위 구문은 3가지 기능을 담고 있는데,

- 컨테이너 작성 및 실행 (`~run`)

- 바탕이 되는 Docker 이미지 지정

- 컨테이너 안에서 실행할 명령

해당 명령 실행 시,

- 먼저 해당 컨테이너의 바탕이 되는 Ubuntu의 이미지가 **로컬에 존재하는지** 확인한다.
  
  - 없다면 Docker 리포지토리에서 이미지를 다운로드한다.

- 다운로드가 완료되면 이미지를 바탕으로 컨테이너를 시작하고, 지정된 명령이 실행된다.

- 이미 해당 이미지가 존재한다면 다운로드 과정이 생략되므로, 컨테이너 시작 속도가 훨씬 빠른 것을 확인 가능하다.

위와 같이 로컬 환경에 다운로드된 Docker 이미지를 **로컬 캐시**라고 한다.

### Docker 버전 확인

`docker version`

Docker는 **클라이언트/서버 아키텍처**를 채택하고 있기 때문에, 클라이언트와 서버가 Remote API로 연결되어 있다.

그래서 명령어들도 서버로 보내져 처리되는 모습이 나타난다.

### Docker 실행 환경 확인

`docker system info`

컨테이너 수, Docker 버전, 스토리지 드라이버 종류, OS 종류, 아키텍처 등이 나타난다.

### Docker 디스크 이용 상황

`docker system df`

---

## 웹 서버를 작동시켜 보자

**Nginx**는 대량의 요청을 처리하는 대규모 사이트에서 주로 이용한다.

**리버스 프록시**나 **로드밸런서** 같은 기능도 갖고 있다.

### Docker 이미지 다운로드하기

`docker pull nginx`

- Docker에서 Nginx를 작동시키기 위해 필요한 것이 패키징되어 Docker Hub에 올라온 이미지를 다운로드 받는다.

### Nginx를 작동시켜 보자

`docker container run --name webserver -d -p 80:80 nginx`

이미지 nginx를 이용해 webserver라는 이름의 컨테이너를 기동시킨다.

브라우저 80번 포트에 대한 엑세스를 허가하기 위해 -p 옵션을 붙인다.

### Nginx 작동 확인

`docker container ps`

**webserver라는 이름**의 컨테이너에서 **Nginx 서버 프로세스**가 시작되어, **컨테이너의 80번 포트를 전송**하고 있다는 정보가 노출된다.

### Nginx의 기동 및 정지

`docker stop webserver`

ps를 완전히 remove 하지 않았다면,

`docker start webserver`로 재시작 가능하다.


