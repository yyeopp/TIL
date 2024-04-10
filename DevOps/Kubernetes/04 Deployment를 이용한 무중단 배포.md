# Deployment를 이용한 무중단 배포

**ReplicaSet**을 이용하여 무중단 **롤링 배포**를 수동으로 구현해볼 수 있지만, Deployment를 이용하면 보다 편리하고 안전하게 무중단 배포를 달성할 수 있다.

## Deployment

ReplicaSet이 Pod를 랩핑하고 관리하는 컨셉이라면,

Deployment는 ReplicaSet을 랩핑하고 관리하는 개념이다.

`pods.yaml`에 적혀있는 이미지의 버전 정보 등이 교체되고 `apply`되면, Deployment는 기존 ReplicaSet을 대신할 **새로운 ReplicaSet을 똑같이 생성**한 뒤, 기존 ReplicasSet의 `replicas` 수치를 **0**으로 낮춘다.

이는 **기존 ReplicaSet에 담긴 pod가 중지함**에 더불어 **기존 ReplicaSet이 아직 살아있다는 뜻**으로, 유사시 롤백이 가능한 구조를 달성한다.

## Deployment 적용

`pods.yaml` 설정을 기존 ReplicaSet에서 Deployment를 전환한다.

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: webapp
spec:
  minReadySeconds: 30
  selector:
    matchLabels:
      app: webapp
  replicas: 2
  template:
    metadata:
      labels:
        app: webapp
    spec:
      containers:
      - name: webapp
        image: webapp:release0-5
```

- kind 값이 **Deployment**로 바뀌고,

- pod에 대한 설정 정보는 **template**에서 관리하는 가운데, 거의 유사하다.
  
  - image 정보를 변경하고 적용하면 Deployment가 작동한다.

- **minReadySeconds** 값은 **이미지 변경 후 그 롤링배포를 개시할 때까지의 대기시간**이다.
  
  - 환경에 따라 적절한 값을 설정해야 하며,
  
  - 학습을 위해 30으로 정해두고 재배포해보면
  
  - `kubectl get all`에서 롤링배포가 이루어지는 동작을 관찰할 수 있다.
  
  - 이 외에도 Deployment 관련 여러 **spec** 값을 사용 가능하며, 이는 공식 문서로 확인

## Deployment 활용

`kubectl rollout status deploy webapp` 명령어를 이용하여 롤링 동작을 로그로 남길 수 있다.

`kubectl rollout history deploy webapp` 명령어를 이용여 롤링 동작의 히스토리를 확인 가능하다.

- 최근 10개까지 기록되며,

- 리비전 정보가 담겨있어서 롤백 동작에 사용할 수 있다.

### Deployment를 이용한 롤백

롤백하고자 할 시,

`kubectl rollout undo deploy webapp` 으로 롤백이 가능하다.

- `--to-revision=` 옵션으로 리비전을 지정할 수도 있다.

쿠버네티스를 롤백을 대비하여 **이전 ReplicaSet**을 삭제하지 않고 둔다. Pods 정보가 동일하다면, 이전 ReplicaSet도 그대로 재사용된다.

- 버전 정보를 0에서 0-5로 바꿨다가 0으로 다시 돌리면,

- ReplicaSet이 새로 생기는 게 아니라 예전 것을 그대로 사용하고 있다.

따라서, 롤백 또한 이전 ReplicaSet을 그대로 활용하는 방법이기에 효율적이고 안전하다.

#### 롤백 시 주의할 점

롤백을 할 경우, 필연적으로 **현재 존재하는 yaml 파일과 현재 쿠버네티스의 상태가 불일치**하게 된다.

yaml 파일을 즉시 수정하거나, 롤백 이전 상태를 회복하는 것이 필요하다.

### Deployment의 안전성

Deployment는 새로운 ReplicaSet이 **완전히 정상적으로 기동한 것을 확인할 때까지** 기존 ReplicaSet의 동작을 그대로 보존한다.

이미지 정보가 잘못 됐거나 하는 등의 이유로 새로운 ReplicaSet이 기동하지 않았다면,

**배포 자체가 이루어지지 않으며** 이에 따라 서비스 중단이 없게 된다.

- `kubectl get all` 과 `kubectl describe pod {}` 를 이용해서, Deployment 동작 상황과 상세 로그를 확인 가능하다.


