# Vuejs04 - CLI

---

# Node.js 설치 및 NPM

## NPM

**Node Package Manager**

Command에서 써드파티 모듈을 설치하고 관리하는 툴.

## NPM 명령어

`npm init`: 새로운 프로젝트나 패키지를 만듦

- package.json 생성

`npm install package`: 생성되는 위치에서만 사용 가능한 패키지로 설치

`npm install -g package`: 글로벌 패키지에 추가하여 모든 프로젝트에서 사용 가능한 패키지로 설치

---

# @vue/cli

## 개요

CLI는, **Command Line Interface**

Vue.js 개발을 위한 시스템으로, Vue.js에서 공식으로 제공하는 CLI다.

개발의 편리성을 위해 필수처럼 사용 중임

## 생성

`vue create <project-name>`으로 생성.

manual로 이런저런 설정을 정하게 됨

`vue add <plugin-name>`으로 생성 이후 별도 플러그인을 추가 가능

---

# SFC

## SFC (Single File Component)

확장자가 `.vue`인 파일로, template, script, style이 합쳐져 있다.

CSS의 범위를 컴포넌트 단위로 제한하는 것이 가능

### template

기본 언어는 html.

각 vue 파일은 최대 하나의 template 블록을 포함할 수 있다.

### script

기본 언어는 js.

vue 파일에 최대 하나의 블록으로 포함.

ES6를 지원하여, import와 export를 사용 가능하다.

### style

기본 언어는 css

scoped 속성을 이용해 css의 적용 범위 지정 가능
