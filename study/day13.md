## build.gradle
1. 빌드 스크립트 
2. gradle을 이용해 프로젝트를 빌드하고 의존성을 관리하기 위해 작성되었다.
3. groovy 언어를 사용해 작성되었고, kotlin을 사용할 수도 있다.
4. jvm 위에서 동작한다.

### plugins
```
plugins {
	id 'org.springframework.boot' version '3.0.1'
	id 'io.spring.dependency-management' version '1.0.12.RELEASE'
	id 'java'
}
```
`org.springframework.boot` 플러그인 역할
1. 스프링을 빌드했을 때 실행가능한 jar 파일이 나오게 도와주고
2. 스프링 애플리케이션을 실행할 수 있게 도와주고
3. 또다른 플러그인들이 잘 적용될 수 있게 해준다.

`io.spring.dependency-management`

외부라이브러리, 프레임워크의 버전관리에 도움을 주고 서로 얽혀있는 의존성을 처리하는데 도와준다.

`java`

java 프로젝트를 개발하는데 필요한 기능들을 추가해주고, 다른 JVM 언어 Gradle 플러그인을 사용할 수 있는 기반을 마련한다.

### repositories
```
repositories {
	mavenCentral()
}
```
- 외부 라이브러리/프레임워크를 가져오는 장소 결정
- mavenCentral() : maven 중앙 저장소

### dependencies
```
dependencies {
	implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
	implementation 'org.springframework.boot:spring-boot-starter-web'
	implementation 'org.projectlombok:lombok'
	annotationProcessor 'org.projectlombok:lombok'
	runtimeOnly 'com.h2database:h2'
	implementation 'mysql:mysql-connector-java'

	testImplementation 'org.springframework.boot:spring-boot-starter-test'
}
```
- 우리가 사용하는 라이브러리/프레임워크를 가져온다.
- implementation: 해당 의존성을 항시 사용한다.
- runtimeOnly : 코드를 실행할 떄에만 라이브러리를 실행한다.
- testImplementation : 테스트 코드를 사용할 때 항상 사용한다.

## Spring 과 Spring Boot

### spring boot 장점 
1. 간편한 설정

    IoC/DI, AOP, PSA와 같이 스프링은 강력한 기능을 제공한다.    
   -> 이를 사용하기 위해 xml 설정을 `많이` 해야 했다.    
   -> 스프링 부트는 어노테이션 기반의 설정 적극 사용, 기본적으로 필요한 것 모두 자동 설정

2. 간단한 의존성 관리

   스프링을 사용할 때에는 개발에 필요한 라이브러리/프레임워크를 모두 적어야 했다.    
   -> 충돌나는 경우가 잦았다.    
   -> starter로 묶어줬다.

3. 강력한 확장성

## application.yml과 application.properties, lombok
### application.yml
```yaml
spring:
  config:
    activate:
      on-profile: local
  datasource:
    url: jdbc:h2:mem:library;MODE=MYSQL;NON_KEYWORDS=USER
    username: sa
    password:
    driver-class-name: org.h2.Driver
  jpa:
    hibernate:
      ddl-auto: create
    properties:
      hibernate:
        show_sql: true
        format_sql: true
        dialect: org.hibernate.dialect.H2Dialect
  h2:
    console:
      enabled: true
      path: /h2-console
```
- key : value 형식으로 데이터 정의
- 각 계층은 들여쓰기를 통해 구분, 중복 제거 가능
- value에는 true/false, 숫자, 문자열이 들어갈 수 있다.
- value에는 배열도 들어갈 수 있다. - 을 이용한다
- #을 이용하면 주석을 사용할 수 있다.

### .properties
- .으로 계층 나눈다.
- key : value 형태
- yml로 관리하는 것이 더 직관적인 느낌이 있다.

### lombok
getter나 setter, 생성자와 같은 반복되는 보일러 플레이트 코드를 제거할 수 있다.    

__사용 방법__
1. lombok 의존성 추가
2. 인텔리제이 플러그인 추가
3. annotation processor 활성화

__대표적인 어노테이션__    
- @Getter : getter 자동 생성
- @NoArgsContructor : 기본 생성자 자동 생성
- @RequiredArgsContructor: final 필드에 대해 자동으로 생성자가 생성된다.
- @Setter
- @EqualsAndHashCode
- @ToString

> Java 14 + 을 사용한다면 record class을 사용하는 것도 좋다

## spring boot 3으로 업데이트하기
스프링부트 3 변경점
1. Java 최소 버전이 17버전으로 업그레이드 왰다.
2. Thrid-party library 버전업
3. AOP 기초 작업이 이루어졌다.
   애플리케이션 시작 시간과 메모리 사용량을 줄일 수 있게 해준다.
4. javax 대신 jakarta 패키지를 사용하게 된다.
5. 모니터링 기능들의 강화
6. 다양한 세부 변경사항이 많다!
























