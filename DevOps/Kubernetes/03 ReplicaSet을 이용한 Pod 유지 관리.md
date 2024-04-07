# ReplicaSet을 이용한 Pod 유지 관리

---

## Pod를 직접 사용하지 않는 이유

지금까지는 Pod에 Service를 직접 연결하여 사용하였는데, 바람직한 방법이 아니다.

- Pod가 모종의 이유로 DOWN 됐을 때, 쿠버네티스가 아무런 조치를 해주지 않는다.
  
  - 실제로 `kubectl delete po webapp` 해보면 웹 서비스가 즉시 중단된다.

Pod는 기본적으로 **쓰고 버리는 개념**을 가지고 있기 때문에, 무중단 서비스를 Pod에 의존할 수 없다.

## ReplicaSet

ReplicaSet은 간단히 말해서 정해진 수의 Pod를 유지 관리하는 객체다.

Pod와 마찬가지로 yaml 파일에서 설정할 수 있는데,

`template` 항목에 작성하는 내용이 Pod의 내용과 **거의 완전히 동일**하다.

- ReplicaSet에서 여러 개의 동질적인 Pod를 관리하는 측면을 보여준다.

### ReplicaSet 적용

```yml
apiVersion: apps/v1
kind: ReplicaSet
metadata:
  name: webapp
spec:
  selector:
    matchLabels:
      app: webapp
  replicas: 2
  template: # template for the pods
    metadata:
      labels:
        app: webapp
    spec:
      containers:
      - name: webapp
        image: #proper_docker_image with release version
```

위와 같이 작성하면,

- webapp 이라는 이름의 ReplicaSet이 생성되고

- **label** 값으로 **app: webapp** 을 가지는 pod를 지목해서 관리하며,

- **replicas**: 2 로 지정했기 때문에, **상시 2개의 Pod**가 떠있도록 관리한다.
  
  - Pod는 webapp-XXXXX 같은 이름으로 **랜덤/자동 생성**되며
  
  - 서로 다른 Pod들이 **각각의 서비스 포트** 하에 떠있다가
  
  - 인위적으로 서비스 Pod를 delete 하면, 즉시 **대기 중이던 다른 Pod로 서비스를 이어가**는 동작이 이루어지며
  
  - 삭제된 Pod는 즉시 재생성된다.

## 효과

Pod의 **쉽게 쓰고 버리는** 목적이 달성된다.

ReplicaSet이 Pod의 개수를 유지해주기 때문에, 불시에 Pod가 DOWN되더라도 서비스 중단을 막을 수 있다.
