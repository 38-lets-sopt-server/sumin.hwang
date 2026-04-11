# 1차 키워드 과제

## 1. final / static / static final의 차이

### `final`
- **변수**: 한 번 초기화되면 **값 변경 불가** (상수)
- **메서드**: **오버라이딩 불가**
- **클래스**: **상속 불가**

```java
final int x = 10;
x = 20; // 컴파일 에러

// 인스턴스마다 다른 값을 가질 수 있지만, 한번 할당되면 변경 불가
class User {
    final String name;
    User(String name) {
        this.name = name; // 생성자에서 딱 한 번만 할당 가능
    }
}
```

### `static`
- **클래스 레벨**에 속하는 멤버. 인스턴스 생성 없이 사용 가능
- 모든 인스턴스가 **같은 값을 공유**

```java
class Counter {
    static int count = 0; // 모든 인스턴스가 공유
    Counter() { count++; }
}

Counter.count; // 인스턴스 없이 접근 가능
```

### `static final`
- **클래스 레벨 상수**. 공유되면서 + 변경 불가
- 관례적으로 **UPPER_SNAKE_CASE**로 작성

```java
class Config {
    static final int MAX_USERS = 100;     // 컴파일 타임 상수
    static final String API_URL = "https://api.example.com";
}
```

| 키워드 | 소속 | 변경 가능 | 대표 용도 |
|---|---|---|---|
| `final` | 인스턴스 | 불가 | 불변 필드, 상속/오버라이딩 방지 |
| `static` | 클래스 | **가능** | 유틸 메서드, 공유 상태 |
| `static final` | 클래스 | 불가 | 상수 정의 |

---

## 2. Java의 Generic 타입이란?

제네릭은 **클래스나 메서드에서 사용할 타입을 미리 정하지 않고, 사용 시점에 지정**하는 기능이다.

### 왜 필요한가?

```java
// 제네릭 없이 — Object로 받으면 타입 안전성이 없다
List list = new ArrayList();
list.add("hello");
String s = (String) list.get(0); // 매번 캐스팅 필요, 런타임 에러 위험

// 제네릭 사용 — 컴파일 타임에 타입 체크
List<String> list = new ArrayList<>();
list.add("hello");
String s = list.get(0); // 캐스팅 불필요, 타입 안전
```

### 기본 문법

```java
// 제네릭 클래스
public class Box<T> {
    private T value;
    public void set(T value) { this.value = value; }
    public T get() { return value; }
}

Box<String> stringBox = new Box<>();
Box<Integer> intBox = new Box<>();
```

### 주요 타입 파라미터 컨벤션

| 기호 | 의미 |
|---|---|
| `T` | Type |
| `E` | Element (컬렉션) |
| `K` | Key |
| `V` | Value |
| `N` | Number |

> 이번 과제를 통해 Generic에 대해 정리하면서 타입은 원래 그냥 T로 통일한다고만 생각했었는데
> 타입 파라미터에도 컨벤션이 있다는 것을 알게 되었다.

### 와일드카드

```java
// 상한 경계: Number 또는 그 하위 타입만 허용
public void print(List<? extends Number> list) { }

// 하한 경계: Integer 또는 그 상위 타입만 허용
public void add(List<? super Integer> list) { }

// 비경계: 모든 타입 허용 (읽기 전용에 적합)
public void log(List<?> list) { }
```

### 제네릭 메서드

```java
public <T> T firstItem(List<T> list) {
    return list.get(0);
}
```

> **핵심**: 제네릭은 **컴파일 타임 타입 안전성**을 제공하면서 **코드 재사용성**을 높여준다. 런타임에는 타입 정보가 지워지는데 이를 **타입 소거(Type Erasure)** 라고 한다.

---

## 3. Controller / Service / Repository / Domain 각각의 역할

Spring Boot의 **Layered Architecture** (계층형 아키텍처):

```
Client 요청
    ↓
┌──────────────┐
│  Controller  │  ← 요청/응답 처리 (HTTP 관심사)
└──────┬───────┘
       ↓
┌──────────────┐
│   Service    │  ← 비즈니스 로직
└──────┬───────┘
       ↓
┌──────────────┐
│  Repository  │  ← 데이터 접근 (DB 관심사)
└──────┬───────┘
       ↓
┌──────────────┐
│   Domain     │  ← 핵심 엔티티/비즈니스 규칙
└──────────────┘
```

### Controller
- HTTP 요청의 **진입점**
- 요청 파라미터 검증, DTO 변환, 응답 코드 설정
- 비즈니스 로직을 직접 갖지 않고 **Service에 위임**

### Service
- **비즈니스 로직**의 핵심, 트랜잭션 관리
- Controller와 Repository 사이에서 데이터를 가공하고 흐름을 제어
- 여러 Repository를 조합하여 복합적인 로직 수행

### Repository
- **데이터 접근 계층**. DB와의 CRUD 처리
- SQL, JPA 같은 기술적 세부사항을 캡슐화
- Service는 DB가 뭔지 알 필요 없도록 추상화

### Domain (Entity)
- 비즈니스의 **핵심 개념**을 코드로 표현한 객체
- DB 테이블과 매핑되면서, 비즈니스 규칙도 포함
- 특정 프레임워크에 의존하지 않는 순수한 객체가 이상적이긴 함
  _(그러나 간단한 프로젝트나 앱잼 등의 볼륨에서는 JPA Entity 자체를 도메인 클래스로 사용하는 경우가 많다.)_

### 왜 이렇게 나누는가?

| 이점 | 설명 |
|---|---|
| **관심사 분리** | 각 계층이 하나의 책임만 가짐 |
| **테스트 용이** | 계층별로 독립적 테스트 가능 |
| **유지보수** | DB를 바꿔도 Repository만 수정하면 됨 |
| **협업** | 계층별로 역할 분담이 명확 |