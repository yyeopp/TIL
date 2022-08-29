# SpringFramework - File Upload, Download, Interceptor

---

# FileUpload

## File Upload

파일 업로드를 도와주는 library는 굉장히 많다.

Apache에서 제공해주는 lib를 사용해볼 것 (Apache commons)

### pom.xml

의존성 추가 (MVN repo)

### servlet-context.xml

file upload 관련 bean을 등록

property 설정 필요

- defaultEncoding, maxUploadSize, maxInMemorySize

### .jsp

form에 `input type="file"` 도입. 반드시 post 타입으로.

- input에 `multiple="multiple"` 설정하면 다중 업로드 가능

- name을 잘 지정해야 함: `@RequestParam`으로 key 역할을 해줄 것.

`enctype="multipart/form-data"`를 반드시 설정해줘야 함.

### Dto

기존 `Dto`에 더해, `FileInfoDto`가 추가로 필요

- saveFolder, originFile, saveFile 같은 정보가 필요하기 때문

### Controller

#### Autowired

`ServletContext` 객체를 활용해야 함

#### parameter

원래 있던 Dto에 더해 `@RequestParam("upfile") MultipartFile[] files` 가 추가되어야 함.

- 다중 업로드가 아니라면 `MultipartFile[]`이 아니여도 됨

#### body

##### saveFolder 지정

realPath 도출: 워크스페이스의 .metadata 폴더 속. 업로드된 파일이 실제로 저장될 root 경로

`File.separator` 및 날짜를 사용해서 `saveFolder` 경로 완성: 운영체제에 대응, 폴더 중복 방지

해당 folder가 이미 존재하는 지 확인해서, 없으면 `folder.mkdirs()`로 생성

#### upload 구현

다중 업로드이므로 해당 게시물에 연동되는 파일 정보를 저장할 `List<FileInfoDto>`를 ArrayList로 생성

전달된 `MultipartFile []`에 대해 for문으로 순회하면서, `fileInfoDto`를 하나씩 생성해 파라미터를 set.

완료되면 해당 mfile을 `.transferTo`를 통해 앞서 설정된 folder에 saveFileName으로 저장.

List에도 add.

#### 마무리

게시글 Dto에 파일 정보를 저장한 List를 set하고, write 메서드를 실행시킴 

### RepoImpl

*보충 필요*

----

# FileDownload

## .jsp

게시글이 attr로 가지고 있는 가지고 있는 `fileInfos` list를 `<c:forEach>`로 순회해서, 각 file에 대해 a tag를 단다.

이 때 tag의 href는 지정하지 않고 (`#`), 클릭 액션을 JS로 구현한다.

제이쿼리에서 method와 action을 지정해서 submit 하는 것도 가능하다.

submit할 form은 input type = "hidden" 으로 숨겨놓자.

## servlet-context.xml

`FileDownLoadView` 클래스를 직접 생성하고, xml에 bean으로 등록한다.

`BeanNameViewResolver`를 bean으로 등록한다.

- Controller에서 fileDownLoadView가 viewName으로 리턴되었을 때, 그걸 미리 만들었던 `ViewResolver`가 .jsp를 붙여 이동시키는 것 대신 해당 클래스로 이동할 수 있도록 하는 bean이다.

- value="0"으로 지정된 것이 우선순위가 더 높음을 보여준다.

## Controller.java

submit을 받은 후 (GET) requestmapping을 받는다.

`fileInfo`를 새로운 Map 객체로 만들고, GET으로 전달받은 `RequestParam`을 세팅한 뒤 `ModelAndView` 객체로 return해준다.

- ViewName으로 `fileDownLoadView`, modelName, model을 전송한다.

## FileDownLoadView.java

다운로드 동작을 실제로 처리하는 자바 파일로, servlet-context.xml에 의해 ViewResolver가 이 쪽으로 지정된다.

내부 코드는 DBUtil 같은 느낌이라 정확히는 몰라도 된다.

----

# Interceptor

## HandlerInterceptor를 통한 요청 가로채기

Controller가 요청을 처리하기 전/후 처리

로깅, 모니터링, 정보 수집, 접근 제어 처리 등 실제 business logic과 분리되어 처리해야 하는 기능들을 넣고 싶을 때 유용

Interceptor를 여러 개 설정할 수 있으나, **순서를 주의해야 함**

## 구현

### HandlerInterceptor 인터페이스 구현

`preHandle`, `postHandle`, `afterCompletion` 등 메서드를 제공

기능은 이름에서 확인 가능.

주로 로그인 같은 기능 구현하는 데 사용함

### servlet-context.xml

bean으로 인터셉터 객체를 등록

`<interceptors>` 에 개별 interceptor를 `<beans:ref>`으로 등록하고, `<mapping>`으로 interceptor가 개입할 path를 지정함.

여러 개의 interceptor를 등록할 수 있는데, `<interceptors>`에 등록하는 순서에 따라 실행 순서가 달라지는 점을 유의
