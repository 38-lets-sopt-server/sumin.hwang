# OAuth 2.0과 인증 흐름

## OAuth 2.0이란?

OAuth 2.0은 **제3자 애플리케이션이 사용자의 리소스에 접근할 수 있도록 권한을 위임하는 표준 프로토콜**이다.

핵심은 "인증(Authentication)"이 아닌 **"권한 부여(Authorization)"** 에 있다. 사용자가 직접 비밀번호를 넘기지 않아도, 특정 범위의 리소스 접근 권한만 제3자 앱에 위임할 수 있다.

> 예) "Google 계정으로 로그인" = Google이 우리 앱에게 "이 사용자의 이메일, 프로필 정보를 조회할 수 있는 권한"을 부여

---

## 주요 역할 (Roles)

| 역할 | 설명 | 이 프로젝트에서 |
|------|------|----------------|
| **Resource Owner** | 리소스의 실제 소유자 (사용자) | 로그인하는 사람 |
| **Client** | 리소스에 접근하려는 제3자 앱 | 이 Spring 백엔드 |
| **Authorization Server** | 권한을 인증하고 토큰을 발급하는 서버 | Google Authorization Server |
| **Resource Server** | 실제 리소스를 보유한 서버 | Google UserInfo API |

---

OAuth 2.0에는 여러 Grant Type이 있지만, 가장 안전하고 널리 쓰이는 방식은 **Authorization Code Flow**다. 이 프로젝트도 이 방식을 사용한다.

```
사용자          클라이언트(백엔드)       Authorization Server(Google)    Resource Server(Google API)
  │                    │                          │                              │
  │ ① 로그인 요청      │                          │                              │
  │──────────────────>│                          │                              │
  │                    │ ② 인가 요청              │                              │
  │                    │ (client_id, scope,       │                              │
  │                    │  redirect_uri, state)    │                              │
  │                    │─────────────────────────>│                              │
  │ ③ Google 로그인 화면                          │                              │
  │<──────────────────────────────────────────────│                              │
  │ ④ 사용자 로그인 및 동의                        │                              │
  │──────────────────────────────────────────────>│                              │
  │                    │ ⑤ Authorization Code     │                              │
  │                    │<─────────────────────────│                              │
  │                    │ ⑥ Code → Access Token 교환                             │
  │                    │─────────────────────────>│                              │
  │                    │ ⑦ Google Access Token    │                              │
  │                    │<─────────────────────────│                              │
  │                    │ ⑧ 사용자 정보 요청        │                              │
  │                    │───────────────────────────────────────────────────────>│
  │                    │ ⑨ 사용자 정보 응답        │                              │
  │                    │<───────────────────────────────────────────────────────│
```

**Authorization Code를 중간에 거치는 이유**: Access Token을 브라우저 URL에 직접 노출하지 않기 위해서다. Code는 유지 기간이 짧고, 실제 토큰 교환은 서버끼리 이루어진다.

---

## 과제에 적용한 Google OAuth2 흐름

이번 과제에서는 Spring Security의 `spring-boot-starter-oauth2-client`를 사용했는데, 이 라이브러리는 Authorization Code Flow의 상당 부분(②~⑦)을 자동으로 처리해준다. 직접 구현한 부분은 ⑧ 이후다.

### 전체 흐름

```
프론트(3000)                 백엔드(8080)                    Google
     │                           │                              │
     │ ① GET /oauth2/authorization/google                       │
     │──────────────────────────>│                              │
     │                           │ ② 302 → Google 로그인 페이지 │
     │<─────────────────────────────────────────────────────────│
     │ ③ 구글 계정 선택 및 동의  │                              │
     │──────────────────────────────────────────────────────────>
     │                           │ ④ Authorization Code         │
     │                           │  GET /login/oauth2/code/google?code=...
     │                           │<─────────────────────────────│
     │                           │ ⑤ Code → Token 교환 (자동)  │
     │                           │─────────────────────────────>│
     │                           │ ⑥ Google Access Token        │
     │                           │<─────────────────────────────│
     │                           │ ⑦ UserInfo 조회 (자동)       │
     │                           │─────────────────────────────>│
     │                           │ ⑧ {sub, email, name, ...}   │
     │                           │<─────────────────────────────│
     │                           │                              │
     │                           │ ⑨ OAuth2AuthenticationService.loadUser()
     │                           │    └─ GoogleOAuth2UserInfo 변환
     │                           │                              │
     │                           │ ⑩ OAuth2AuthenticationSuccessHandler
     │                           │    ├─ findOrCreateByOAuth2() → 신규면 회원가입
     │                           │    ├─ JWT AccessToken + RefreshToken 발급
     │                           │    ├─ Set-Cookie: refreshToken (HttpOnly)
     │                           │    └─ 302 → http://localhost:3000/auth/callback?token={accessToken}
     │                           │
     │ ⑪ /auth/callback?token=... 로 리다이렉트             │
     │<──────────────────────────│                              │
     │ ⑫ URL에서 accessToken 꺼내 저장                        │
```

### 각 구성 요소 역할

**`OAuth2AuthenticationService`** (`DefaultOAuth2UserService` 확장)
- Spring Security가 Google에서 받아온 유저 정보(`OAuth2User`)를 가공하는 진입점
- `registrationId`("google")로 `OAuth2Provider`를 결정하고, `OAuth2UserInfoFactory`를 통해 프로바이더별 `OAuth2UserInfo`로 변환
- Google의 경우 `sub`(고유 ID), `email`, `name` 필드를 사용

```java
OAuth2User oAuth2User = super.loadUser(userRequest);          // Google에서 유저 정보 로드
OAuth2Provider provider = OAuth2Provider.from(registrationId); // "google" → GOOGLE
OAuth2UserInfo userInfo = OAuth2UserInfoFactory.create(provider, attributes);
return OAuth2Authentication.create(userInfo);                  // Security Context에 저장
```

**`OAuth2AuthenticationSuccessHandler`**
- 인증 성공 후 실행되는 핸들러
- 이메일 기준으로 기존 유저를 조회하고, 없으면 `oAuth2Provider`, `oAuth2Id` 필드와 함께 자동 회원가입
- JWT 토큰 발급 후 RefreshToken은 HttpOnly 쿠키로, AccessToken은 리다이렉트 URL 쿼리 파라미터로 전달

```java
User user = userService.findOrCreateByOAuth2(userInfo);  // 신규면 회원가입
AuthTokens tokens = authService.loginWithOAuth2(user.getId(), user.getEmail());

response.addHeader(SET_COOKIE, RefreshTokenCookie.create(tokens.refreshToken(), ...));
// → Set-Cookie: refreshToken=xxx; HttpOnly; SameSite=Lax

sendRedirect(..., clientRedirectUri + "?token=" + tokens.accessToken());
// → 302: http://localhost:3000/auth/callback?token=eyJ...
```

**`OAuth2AuthenticationFailureHandler`**
- Google 인증 실패 또는 알 수 없는 프로바이더 요청 시 `401 OAUTH2_UNAUTHORIZED` 응답