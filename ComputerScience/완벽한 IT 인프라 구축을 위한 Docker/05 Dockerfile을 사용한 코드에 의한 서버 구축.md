# Chapter 05: Dockerfile을 사용한 코드에 의한 서버 구축

---

Docker에서는 **인프라 구성 관리**를 **Dockerfile**로 기술한다.

Dockerfile은 베이스 이미지에 각종 **미들웨어**를 설치 및 설정하고, 개발한 어플리케이션의 실행 모듈을 전개하기 위한 모든 **구성 정보**를 기술한다.

---

## Dockerfile을 사용한 구성 관리

### Dockerfile이란?

Dockerfile은, Docker 상에서 작동시킬 **컨테이너의 구성 정보**를 기술하기 위한 파일이다.

- 베이스가 될 Docker 이미지

- Docker 컨테이너 안에서 수행할 조작

- 환경변수 등 설정

- Docker 컨테이너 안에서 작동시켜 둘 데몬 실행

`docker build` 명령을 내리면, Dockerfile에 기술된 구성 정보를 바탕으로 Docker 이미지를 작성하게 된다.

### Dockerfile의 기본 구문

확장자가 필요 없는 텍스트 형식의 파일이다.

기본 서식은, `명령 인수` 의 형태이다.

- 명령은 관례적으로 대문자로 통일한다.

- FROM, RUN, CMD, LABEL, EXPOSE, ENV, ADD, COPY, ENTRYPOINT, VOLUME, USER, WORKDIR, ARG, ONBUILD, STOPSIGNAL, HEALTHCHECK, SHELL

주석은 `#`으로 달 수 있다.

### Dockerfile 작성

해당 컨테이너를 어떤 Docker 이미지로부터 생성할지 정보를 반드시 기술해야 하는데, 이를 **베이스 이미지**라고 한다.

`FROM [이미지명]`이 기본이고, 뒤에 `:[태그명]` 또는 `@[다이제스트]`를 붙일 수 있다.
