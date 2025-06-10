# 스프링 데이터 JPA 분석

---

## 구현체 분석

`JpaRepository`의 기본 구현체인 `SimpleJpaRepository`에 대한 분석이다.

- `@Repository`
  
  - 스프링 빈을 선언하여, JPA의 예외를 **스프링의 추상화된 예외**로 변환한다.
  
  - DB접근 구현기술이 바뀌어도 **스프링의 맥락에서 해결되게끔**한 의도

- `@Transactional(readOnly=true)`
  
  - JPA의 모든 변경은 트랜잭션 안에서 처리되어야 한다. 순수 JPA 사용할 시 트랜잭션을 직접 선언하지 않으면 오류가 발생했다.
  
  - 스프링 데이터 JPA의 기본 구현체는 리포지토리 계층에서 직접 트랜잭션을 선언해둬서, 개발자가 굳이 선언하지 않아도 되게 해뒀다.
  
  - 만약 개발자가 서비스 계층 혹은 커스텀 리포지토리에서 트랜잭션을 시작했다면 그게 **전파**될 것.
  
  - 내부 메서드에는 단순 조회성 트랜잭션도 존재하기 때문에, `readOnly` 옵션으로 **플러시를 생략**시킴으로써 성능을 조금이나마 개선시킨다.
  
  - 대신 `save()` 같은 변경 메서드에서는 별도의 `@Transactional`을 선언함으로써 `readOnly` 옵션을 제거해주고 있다.

- `save()` 의 상세 동작
  
  - **새로운 엔티티인지 확인해서** 저장(persist) 혹은 병합(merge)
  
  - 이 때의 병합은 **단순 update**가 아니다. **영속성 컨텍스트**에 대한 병합을 의미
    
    - 개발자가 `merge` 를 호출하는 게 가능은 하지만, 웬만하면 `save` 호출하는 게 맞다.

### 새로운 엔티티를 구별하는 방법

#### 기본 전략

- **식별자**를 보고 **객체**인데 null이면 새로운 것

- 식별자를 보고 primitive type인데 0이면 새로운 것

#### 문제점

식별자(`@Id`)의 생성전략이 `@GeneratedValue` 가 아닌 경우, 새로운 엔티티임에도 `merge` 쪽으로 빠지게 된다.

- 개발자가 직접 UUID 같은 식별자를 생성하고 `save()` 를 호출하는 경우도 생길 수 밖에 없는데, `isNew` 를 판단하는 기준은 `== null` 이기 때문

- `merge` 가 호출될 시, 단순 `insert` 가 아니라 `select + insert` 로 전개되기 때문에 **상당한 비효율**이 발생한다.

#### Persistable 구현

```java
@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Item implements Persistable<String> {
    @Id
    private String id;

    @CreatedDate
    private LocalDateTime createdDate;

    public Item(String id) {
        this.id = id;
    }
    @Override
    public boolean isNew() {
        return createdDate == null;
    }
}
```

- `Persistable` 인터페이스를 엔티티가 구현하도록 만들면, `isNew` 메서드를 직접 오버라이드할 수 있다.

- 일반적인 방식은 `@CreatedDate` 와 조합하는 것. 실제 save가 이루어지기 전에는 해당 필드가 반드시 null이기 때문에 안전하게 동작한다.


