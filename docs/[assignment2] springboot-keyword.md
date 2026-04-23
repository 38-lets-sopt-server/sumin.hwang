# 2차 키워드 과제

## 1. HTTP의 멱등성(Idempotency)이란?

동일한 요청을 여러 번 보내도 결과가 한 번 보낸 것과 동일한 성질을 말한다.

> 여기서 말하는 결과는 응답 본문이 아니라 서버에서의 최종 상태를 얘기한다.

POST는 멱등성을 보장하지 않는다!!

### 멱등성은 왜 중요할까?

- 네트워크 장애로 클라이언트가 응답을 못 받았을 때 재시도 가능 여부를 결정할 수 있음
- 멱등성을 보장하지 않는 POST는 실패 후 재시도할 때 중복으로 리소스가 생성될 가능성이 있음

---

## 2. `@Controller`와 `@RestController`의 차이

둘 다 Spring MVC에서 HTTP 요청을 처리하는 컨트롤러임을 표시하는 어노테이션이지만, 응답 처리 방식이 다르다.

### `@Controller`
- 전통적인 MVC 패턴용. View 이름을 반환하면 `ViewResolver`가 템플릿을 렌더링
- 데이터를 직접 응답 본문으로 보내려면 메서드마다 `@ResponseBody`를 붙여야 함

```java
@Controller
public class PostController {

    @GetMapping("/posts/{id}")
    public String getPost(@PathVariable Long id, Model model) {
        model.addAttribute("post", postService.find(id));
        return "post-detail";
    }
}
```

### `@RestController`
- `@Controller` + `@ResponseBody`
- 메서드 반환값을 HTTP 응답 본문에 그대로 직렬화(주로 JSON)
- REST API 서버에서 표준적으로 사용

```java
@RestController
public class PostController {

    @GetMapping("/posts/{id}")
    public PostResponse getPost(@PathVariable Long id) {
        return postService.find(id); 
    }
}
```

---

## 3. Java Record란?

Java 16에서 정식 도입된 기능으로, 불변(immutable) 데이터를 담기 위한 클래스를 간결하게 선언할 수 있는 문법.

> 값 객체(Value Object, VO)로 사용할 때 자주 사용된다. Kotlin에서는 data class가 비슷한 역할을 한다.


```java
public record Point(int x, int y) {}
```

이 한 줄로 컴파일러가 자동 생성하는 것:
- `private final` 필드 (`x`, `y`)
- 모든 필드를 받는 public 생성자
- 각 필드의 getter 메서드 (`x()`, `y()` — getXxx가 아님!)
> Getter긴 하지만 get으로 시작하지 않는다.
- `equals()`, `hashCode()`, `toString()`

### 기존 클래스와 비교

```java
// 기존 클래스
public class Point {
    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() { return x; }
    public int getY() { return y; }

    @Override public boolean equals(Object o) { /* ... */ }
    @Override public int hashCode() { /* ... */ }
    @Override public String toString() { /* ... */ }
}
```

→ Record 한 줄로 대체 가능함. 보일러 플레이트를 많이 줄여준다는 점에서 과제할 때도 많이 사용하면 좋아요!!

### 주의사항

- 상속이 필요하거나, 비즈니스 로직이 많은 도메인 객체에는 부적합

## 4. Optional이란? 왜 null 대신 쓰는가?

'값이 있을 수도, 없을 수도 있음'을 명시적으로 표현하는 컨테이너 객체.

### 그래서 왜 `null` 대신 쓰는데?

```java
// 위험한 코드
User user = userRepository.findByEmail(email);
String name = user.getName(); // user가 null이면 NullPointerException 발생
```

이 문제를 타입 시스템 레벨에서 해결하자는 게 Optional의 핵심이다. (type-safe함)

```java
Optional<User> user = userRepository.findByEmail(email);
String name = user.map(User::getName).orElse("Unknown");
```

→ 컴파일러가 이 값은 null이 있을 수 있다고 강제한다.

### 기본 사용법

```java
// 생성
Optional<String> opt = Optional.of("hello");        // null이면 NPE
Optional<String> opt = Optional.ofNullable(value);  // null 허용
Optional<String> empty = Optional.empty();

// 값 꺼내기
opt.get();                          // 비어있으면 NoSuchElementException (안 하는 게 좋다)
opt.orElse("default");              // 비면 기본값
opt.orElseGet(() -> compute());     // 비면 람다로 계산
opt.orElseThrow(() -> new NotFoundException());

// 값 처리
opt.ifPresent(v -> System.out.println(v));
opt.map(String::toUpperCase);
opt.filter(s -> s.length() > 3);
```
---

## 5. Spring Bean의 생명주기(Lifecycle)

Spring Container가 Bean을 생성·관리·소멸시키는 일련의 과정

### 전체 흐름

```
1. 객체 생성 (인스턴스화)
       ↓
2. 의존성 주입 (DI)
       ↓
3. 초기화 콜백 (@PostConstruct, InitializingBean, init-method)
       ↓
4. 사용 (Bean 사용 단계)
       ↓
5. 소멸 콜백 (@PreDestroy, DisposableBean, destroy-method)
       ↓
6. 컨테이너 종료
```

### 단계별 상세

1. 객체 생성
- Spring이 클래스 메타데이터를 읽고 리플렉션으로 인스턴스 생성
- 이 시점엔 의존성이 아직 주입되지 않은 상태

2. 의존성 주입
- 생성자 주입은 1단계와 함께 일어남
- 세터/필드 주입은 객체 생성 후 별도로 주입

3. 초기화 콜백
- 의존성이 모두 채워진 뒤, 사용 전 준비 작업을 위한 훅
- 사용 가능한 방법:

```java
@Component
public class MyBean {

    // 방법 1: @PostConstruct (가장 권장)
    @PostConstruct
    public void init() { /* DB 연결, 캐시 워밍업 등 */ }

    // 방법 2: InitializingBean 인터페이스 구현
    @Override
    public void afterPropertiesSet() { }

    // 방법 3: @Bean(initMethod = "...")
}
```

4. 사용
- 다른 Bean에서 주입 받아 사용

5. 소멸 콜백
- 컨테이너 종료 직전, 리소스 정리 훅

```java
@PreDestroy
public void cleanup() { /* 커넥션 close, 스레드 풀 종료 등 */ }
```

### 콜백 호출 순서

초기화: `@PostConstruct` → `InitializingBean.afterPropertiesSet()` → `@Bean(initMethod)`
소멸: `@PreDestroy` → `DisposableBean.destroy()` → `@Bean(destroyMethod)`

### Bean Scope에 따른 차이

| Scope | 생성 시점 | 소멸 시점 |
|---|---|---|
| `singleton` (기본) | 컨테이너 시작 시 1회 | 컨테이너 종료 시 |
| `prototype` | 요청 시마다 새로 생성 | Spring이 소멸 관리 안 함 (사용자 책임) |
| `request` | HTTP 요청마다 | 요청 종료 시 |
| `session` | HTTP 세션마다 | 세션 종료 시 |

> 핵심: 생성자에서 무거운 작업을 하지 말고, `@PostConstruct`에서 초기화하자. 의존성이 모두 주입된 후 안전하게 실행되며, 외부 리소스(DB, 외부 API) 접근에 적합하다.

### 참고자료 

https://will-of-rough.tistory.com/59

---

## 6. Spring Boot 구동 원리 (DispatcherServlet 동작 방식)

### 6.1 Spring Boot 애플리케이션 시작 과정

```java
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

1. `SpringApplication.run()` 호출
2. `ApplicationContext` 생성 (Bean 컨테이너)
3. 자동 설정(Auto Configuration) 활성화
   - `@SpringBootApplication` 안에 `@EnableAutoConfiguration` 포함
   - `spring.factories` / `AutoConfiguration.imports`에 등록된 설정 자동 로딩
4. 컴포넌트 스캔 — `@Component`, `@Service`, `@Repository`, `@Controller` 등을 찾아 Bean으로 등록
5. 내장 Tomcat(또는 Jetty/Undertow) 시작
6. DispatcherServlet 등록 — `/`로 들어오는 모든 요청을 받음

### 6.2 DispatcherServlet이란?

모든 HTTP 요청을 가장 먼저 받아서 적절한 컨트롤러로 분배하는 단일 진입점 역할을 한다.

### 6.3 요청 처리 흐름

```
[Client]
   ↓ HTTP Request
[Filter Chain]
   ↓
[DispatcherServlet]  ← 1. 모든 요청 수신
   ↓
   2. HandlerMapping에 컨트롤러 위치 질의
   ↓
   3. HandlerAdapter로 컨트롤러 호출
   ↓
[Interceptor preHandle]
   ↓
[Controller]  ← 4. 비즈니스 로직 실행 (Service 호출)
   ↓
   5. ModelAndView 또는 객체 반환
   ↓
[Interceptor postHandle]
   ↓
[ViewResolver / HttpMessageConverter]  ← 6. 응답 변환
   ↓
[DispatcherServlet]  ← 7. 응답 전송
   ↓ HTTP Response
[Client]
```

### 6.4 단계별 상세

1. Request 수신
- 클라이언트의 모든 요청은 Servlet Container(Tomcat)를 거쳐 `DispatcherServlet`으로 전달

2. HandlerMapping
- 요청 URL에 매핑된 컨트롤러(핸들러)를 찾음
- 대표 구현: `RequestMappingHandlerMapping` — `@RequestMapping` 기반 매핑

3. HandlerAdapter
- 찾아낸 핸들러를 실행할 수 있는 형태로 호출
- 컨트롤러 메서드 시그니처가 다양해도(파라미터 종류, 반환 타입) 어댑터가 추상화해줌

4. Controller 실행
- 컨트롤러 메서드가 실행되어 비즈니스 로직 수행
- 결과로 데이터(객체) 또는 ViewName을 반환

5. 응답 처리
- `@RestController` / `@ResponseBody`: `HttpMessageConverter`가 객체를 JSON으로 직렬화 (Jackson)
- `@Controller` + ViewName: `ViewResolver`가 템플릿을 찾아 HTML 렌더링

6. Response 반환
- `DispatcherServlet`이 최종 응답을 클라이언트에게 전달

### 참고 자료

https://mangkyu.tistory.com/18
