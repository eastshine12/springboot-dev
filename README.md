# springboot-dev
Spring boot 3 블로그 만들기 (Study)
<br/><br/><br/>

# 프로젝트 개요
로그인을 통해 유저가 간단한 게시글을 작성할 수 있는 블로그 구현

<br/><br/><br/>

# 개발 환경

Spring boot 3, Java 17, Gradle, Github Action, AWS

<br/><br/><br/>

# 학습 목표
1. IntelliJ와 Spring Boot 3를 사용하여 백엔드 개발
2. Spring Data JPA로 데이터베이스와의 상호작용 이해
3. MockMvc 및 JUnit을 활용한 효율적인 테스트 코드 작성
4. 타임리프로 웹 뷰 작성
5. 스프링 시큐리티로 안전하게 로그인/로그아웃 구현
6. SecurityFilterChain을 통한 인증과 인가를 설정
7. BCryptPasswordEncoder를 활용하여 비밀번호 암호화 구현
8. JWT Access Token과 Refresh Token 발급 및 관리
9. GitHub Actions를 통한 지속적 통합 및 배포 자동화
10. AWS Elastic Beanstalk을 이용한 서버 구축과 배포 프로세스
<br/><br/><br/>



# 주요 내용

### Spring Data JPA와 데이터베이스 상호작용
Spring Data JPA를 사용하여 데이터베이스와 상호작용하는 방법을 배웠습니다. 이를 통해 데이터의 CRUD 작업을 효율적으로 수행할 수 있게 되었습니다.  
```
package com.eastshine.springbootdev.repository;

import com.eastshine.springbootdev.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
```


<br/><br/>
### 효율적인 테스트 코드 작성
MockMvc와 JUnit을 이용하여 테스트 코드를 작성하는 방법을 학습하였습니다. 이를 통해 애플리케이션의 품질을 향상시키고 버그를 미리 발견할 수 있게 되었습니다.  
```
    @DisplayName("addArticle : 블로그 글 추가에 성공한다")
    @Test
    public void addArticle() throws Exception {
        //given
        final String url = "/api/articles";
        final String title = "title";
        final String content = "content";
        final AddArticleRequest userRequest = new AddArticleRequest(title, content);

        /* 객체 JSON으로 직렬화 */
        final String requestBody = objectMapper.writeValueAsString(userRequest);

        //when
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        //then
        result.andExpect(status().isCreated());

        List<Article> articles = blogRepository.findAll();

        assertThat(articles.size()).isEqualTo(1);
        assertThat(articles.get(0).getTitle()).isEqualTo(title);
        assertThat(articles.get(0).getContent()).isEqualTo(content);

    }
```



<br/><br/>
### 스프링 시큐리티를 활용한 로그인/로그아웃
스프링 시큐리티를 사용하기 위해 SecurityFilterChain을 Bean으로 등록하여 사용자 인증 및 인가를 구현하였습니다. 로그인 및 로그아웃 프로세스를 안전하게 다룰 수 있게 되었습니다.  
```
// 특정 HTTP 요청에 대한 웹 기반 보안 구성
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeRequests() // 인증, 인가 설정
                .requestMatchers("/login", "/signup", "/user").permitAll() // 해당 요청이 오면 인증/인가 없이도 접근 가능하게 설정
                .anyRequest().authenticated()// 위에서 설정한 url 이외의 요청은 인가는 필요하지 않지만 인증은 성공된 상태여야 접근 가능
                .and()
                .formLogin() // 폼 기반 로그인 설정
                .loginPage("/login") // 로그인 페이지 경로 설정
                .defaultSuccessUrl("/articles") // 로그인이 완료되었을 때 이동할 경로
                .and()
                .logout() // 로그아웃 설정
                .logoutSuccessUrl("/login") // 로그아웃이 완료되었을 때 이동할 경로
                .invalidateHttpSession(true) // 로그아웃 이후에 세션을 전체 삭제할지 여부 설정
                .and()
                .csrf().disable() // csrf 설정 비활성화 (실습을 위해)
                .build();
    }
```


<br/><br/>
### 지속적 통합 및 배포 자동화
GitHub Actions를 이용하여 코드 변경 사항을 자동으로 빌드하고 배포하는 프로세스를 구현하였습니다. 이를 통해 개발 프로세스를 더 효율적으로 관리할 수 있게 되었습니다.  
```
name: CI

on:
  push:
    branches: [ main ]

jobs:
  build:
    runs-on: ubuntu-latest

    steps:
      - uses: actions/checkout@v3

      - uses: actions/setup-java@v3
        with:
          distribution: 'zulu'
          java-version: '17'

      - name: Grant execute permission for gradlew
        run: chmod +x gradlew

      - name: Build with Gradle
        run: ./gradlew clean build
```



<br/><br/>
### AWS Elastic Beanstalk을 활용한 배포
AWS Elastic Beanstalk을 사용하여 서버를 구축하고 애플리케이션을 배포하였습니다. 클라우드 환경에서의 배포 경험을 얻을 수 있었습니다.  


<br/><br/>
