# 3차 키워드 과제

## 1. ERD란 무엇인가요?

**ERD(Entity-Relationship Diagram)** 는 데이터베이스의 구조를 시각적으로 표현한 다이어그램

### 구성 요소

#### 1) Entity (개체)
- 저장하고 관리할 대상. 보통 테이블 하나에 대응된다.
- 예: `User`, `Post`, `Comment`

#### 2) Attribute (속성)
- 엔티티가 가지는 데이터 항목. 테이블의 컬럼에 대응
- 예: `User`의 `id`, `email`, `nickname`
- 종류:
    - **Key Attribute**: 식별자 역할 (PK)
    - **Composite Attribute**: 여러 하위 속성으로 구성 (예: `address` → `city`, `street`)
    - **Multi-valued Attribute**: 여러 값을 가질 수 있는 속성 (예: `phoneNumbers`)

#### 3) Relationship (관계)
- 엔티티들 사이의 연관성. 외래 키로 구현된다.
- **Cardinality(관계의 차수)** 로 표현:
    - 1:1 — 한 사람당 주민등록번호 하나
    - 1:N — 한 유저가 여러 게시글 작성
    - N:M — 학생과 강의 (한 학생이 여러 강의를 듣고, 한 강의에 여러 학생이 등록)

---

## 2. 구현한 내용에 대해 ERD 구조도를 ERD Cloud에 그려서 올려주세요.

<img width="1304" height="659" alt="Image" src="https://github.com/user-attachments/assets/45315352-d1e7-474e-8901-d0fea83784b8" />

---

## 3. QueryDSL이란 무엇이고, JPQL과 어떤 차이가 있나요?

### JPQL

JPA가 제공하는 객체 지향 쿼리 언어. 테이블이 아니라 **엔티티 객체**를 대상으로 쿼리를 작성한다.

```java
@Query("SELECT p FROM Post p WHERE p.author.nickname = :name")
List<Post> findAllByAuthorNickname(String name);
```

**한계**
- 문자열 기반이라 오타·문법 오류를 런타임에야 발견
- 조건이 여러 개인 동적 쿼리는 문자열 조립이 지저분해짐

### QueryDSL

JPQL을 자바 코드로 작성하게 해주는 라이브러리. 엔티티에서 자동 생성된 **Q타입**으로 타입-세이프하게 쿼리를 짠다.

```java
queryFactory
    .selectFrom(post)
    .where(post.author.nickname.eq(name))
    .fetch();
```

**강점**
- 컴파일 타임에 오타 검출
- 동적 쿼리를 `BooleanExpression`으로 자연스럽게 조립 (`where()`에 `null`이면 자동 무시)
- IDE 자동완성 지원

### 차이 요약

| 항목 | JPQL | QueryDSL |
|---|---|---|
| 작성 방식 | 문자열 | 자바 코드 |
| 오타 검출 | 런타임 | 컴파일 타임 |
| 동적 쿼리 | 문자열 조립 | 메서드 체이닝 |
