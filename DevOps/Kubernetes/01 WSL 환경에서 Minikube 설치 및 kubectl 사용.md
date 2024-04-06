# WSL 환경에서 kubectl 사용하기

---

## 로컬 환경

- Windows 11 Home

- WSL 설치 후 Devian OS로 기동

- root 계정 사용

## Minikube 설치

```bash
curl -LO https://storage.googleapis.com/minikube/releases/latest/minikube-linux-amd64
install minikube-linux-amd64 /usr/local/bin/minikube && rm minikube-linux-amd64
```

`minikube start` 시도 시 실패함

- Driver로써 사용할 Docker가 설치되어있지 않기 때문

## Docker 설치

```bash
apt-get update
apt-get install ca-certificates curl
install -m 0755 -d /etc/apt/keyrings
curl -fsSL https://download.docker.com/linux/debian/gpg -o /etc/apt/keyrings/docker.asc
chmod a+r /etc/apt/keyrings/docker.asc
echo \
  "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.asc] https://download.docker.com/linux/debian \
  $(. /etc/os-release && echo "$VERSION_CODENAME") stable" | \
  sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
apt-get update
apt-get install docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin
```

`service docker start` 로 docker 기동 시도 후

`service docker status` 로 상태 확인 시, 기동 실패함

상세 사유는 `vi /var/log/docker.log`

### Docker Daemon 실행 실패 해결

```bash
update-alternatives --set iptables /usr/sbin/iptables-legacy
update-alternatives --set ip6tables /usr/sbin/ip6tables-legacy
```

`service docker start` 할 시 기동 성공

## Minikube 기동

```bash
usermod -aG docker $USER && newgrp docker
minikube start
```

## kubectl 사용

```bash
vi /etc/profile

# 마지막 라인에 추가 후 저장
alias kubectl="minikube kubectl --"

source /etc/profile
# 재기동
```

`kubectl version` 시 정상 작동 확인

- 사실 `minikube kubectl -- version` 을 축약한 효과임

## Minikube와 Docker 연동

```bash
eval $(minikube -p minikube docker-env)
```

`docker image ls` 를 했을 때, 원래는 아무것도 없어야 정상

위와 같이 연동할 시, Minikube가 자체적으로 기동시킨 docker image들을 확인 가능함
