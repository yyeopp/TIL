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
