# JPA를 사용하여 API 만들기
## SQL을 직접 작성하면 아쉬운 점

1. 문자열을 작성하기 때문에 실수할 수 있고, 실수를 인지하는 시점이 느리다.

   컴파일 시점에 발견되지 않고 런타임 시점에 발견된다.

2. 특정 데이터베이스에 종속적이게 된다.
3. 반복 작업이 많아진다. 테이블을 하나 만들 때마다 CRUD 쿼리가 항상 필요하다.
4. 데이터베이스의 테이블과 객체는 패러다임이 다르다.

   객체 - 양방향, 상속 관계

   테이블 - 단방향


## JPA(Java Persistence API)

자바 진영의 ORM(Object-Relational Mapping)

- **영속성** 서버가 재시작 되어도 데이터는 영구적으로 저장되는 속성
- **API** 정해진 규칙
- **Object** 객체
- **Relational** 관계형 DB의 테이블
- **Mapping** 객체의 정보와 테이블의 정보를 짝지음

따라서, JPA란 객체와 관계형 DB의 테이블을 짝지어 데이터를 영구적으로 저장할 수 있도록 정해진 Java 진영의 **규칙(interface)이다.**

### Hibernate

JPA의 구현체, 내부적으로 JDBC를 사용한다.

### JPA 어노테이션

`@Entity`
저장되고, 관리되어야 하는 데이터
스프링이 User 객체와 user 테이블을 같은 것으로 바라본다.
> JPA를 사용하기 위해서는 **기본생성자**가 꼭 필요하다.

```java
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
@Column(nullable = false, length = 20, name = "name") // name varchar(20)
private String name;
private Integer age;
```

- `@Id` 이 필드를 primary key로 간주한다.
- `@GeneratedValue` primary key는 자동 생성되는 값이다.
    - IDENTITY: MySQL의 auto_increment
- `@Column` 객체의 필드와 Table의 필드를 매핑한다. 생략 가능
    - name = “name” 같을 경우 생략 가능

### application.yml

```java
spring:
  datasource:
    url: jdbc:mysql://localhost:3307/library
    username: root
    password: 1234
    driver-class-name: com.mysql.cj.jdbc.Driver
  jpa:
    hibernate:
      ddl-auto: none
    properties:
      hibernate:
       show_sql: true
       format_sql: true
       dialect: org.hibernate.dialect.MySQL8Dialect    
```

- `ddl-auto`

  스프링이 시작할 때 DB에 있는 테이블을 어떻게 처리할지 설정

    - `create`: 기존 테이블이 있다면 삭제 추 다시 생성
    - `create-drop`: 스프링이 종료될 때 테이블을 모두 제거
    - `update`: 객체와 테이블이 다른 부분만 변경
    - `validat`e: 객체와 테이블이 동일한지 확인
    - `none`: 별다른 조치를 하지 않는다.
- `show_sql` JPA를 사용해 DB에 SQL을 날릴 때 SQL을 보여줄 것인지 설정

- `format_sql` SQL을 보여줄 때 예쁘게 포맷팅 할 것인가

- `dialect` 이 옵션으로 DB를 특정하면 조금씩 다른 SQL을 수정해준다.
    - MySQL 8 버전에 맞춰주라고 설정해 놓음

## Spring Data JPA 사용해보기

__create__

```java
public void saveUser(UserCreateRequest request) {
		User u = userRepository.save(new User(request.getName(), request.getAge()));
	}
```

- save 하면 id가 자동으로 생성되어 응답된다.

__read__

```java
public List<UserResponse> getUsers() {
		return userRepository.findAll().stream()
			.map(UserResponse::new)
			.collect(Collectors.toList());
	}
```

__update__

```java
public void updateUser(UserUpdateRequest request) {
		User user = userRepository.findById(request.getId())
			.orElseThrow(IllegalArgumentException::new); // 유저가 없다면 예외를 던진다

		user.updateName(request.getName());
		userRepository.save(user);
	}
```

### SQL을 작성하지 않아도 동작하는 이유

Spring Data JPA가 처리해준 것

복잡한 JPA 코드를 스프링과 함께 쉽게 사용할 수 있도록 도와주는 라이브러리

![](https://velog.velcdn.com/images/chunghye98/post/3ad15509-1870-49cd-a5c2-9e99949d67cf/image.png)


### 다양한 쿼리 작성하기

__delete__

```java
public void deleteUser(String name) {
		User user = userRepository.findByName(name)
			.orElseThrow(IllegalArgumentException::new);
		userRepository.delete(user);
	}
```

```java
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByName(String name);
}
```

- findByName : 함수 이름을 제대로 작성해줘야 sql문이 자동으로 만들어진다.

### By 앞에 들어갈 수 있는 기능

`find`

1건을 가져온다. 반환 타입은 객체가 될 수도 있고 Optional<타입>일 수도 있다.

`findAll`

쿼리의 결과물이 N개인 경우 사용. List<타입> 반환

`exists`

쿼리 결과가 존재하는 지 확인. boolean 반환

`count`

SQL의 결과 개수를 센다. long 반환

### By 뒤에 들어갈 수 있는 기능

각 구절은 `And`나 `Or`로 조합할 수도 있다.

`GreaterThan` 초과

`GreaterThanEqual`이상

`LessThan` 미만

`LessThanEqual`이하

`Between` 사이에

`StartsWith` ~ 시작하는

`EndsWith` ~ 끝나는

## JPA 사용 예시
### 문제 1
[과제 6](https://velog.io/@chunghye98/JavaSpring-Layered-Architecture%EB%A1%9C-API-%EB%A7%8C%EB%93%A4%EA%B8%B0)에서 만들었던 Fruit 기능들을 JPA를 이용해서 변경해봅시다.

__FruitController__
```java
@RestController
public class FruitController {

	private final FruitServiceV2 fruitService; // V2로 변경


	public FruitController(FruitServiceV2 fruitService) {
		this.fruitService = fruitService;
	}
```
__FruitServiceV2__
```java

@Service
public class FruitServiceV2 {

	private final FruitRepository fruitRepository;

	public FruitServiceV2(FruitRepository fruitRepository) {
		this.fruitRepository = fruitRepository;
	}

	public void createFruit(FruitCreateRequest request) {
		fruitRepository.save(new Fruit(request.getName(), request.getPrice(), request.getWarehousingDate()));
	}

	public void updateFruit(FruitUpdateRequest request) {
		Fruit fruit = fruitRepository.findById(request.getId())
			.orElseThrow(IllegalArgumentException::new);

		fruit.updateStatus(true);
		fruitRepository.save(fruit);
	}

	public FruitAmountReadResponse getFruitByStatus(String name) {
		List<Fruit> salesFruits = fruitRepository.findAllByNameAndStatus(name, true);
		List<Fruit> unSalesFruits = fruitRepository.findAllByNameAndStatus(name, false);

		long salesAmount = salesFruits.stream()
			.mapToLong(Fruit::getPrice)
			.sum();
		long unSalesAmount = unSalesFruits.stream()
			.mapToLong(Fruit::getPrice)
			.sum();

		return new FruitAmountReadResponse(salesAmount, unSalesAmount);
	}
}
```

__Fruit__
```java
@Entity
public class Fruit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	private long price;
	@Column(name = "stocked_date")
	private LocalDate warehousingDate;
	@Column(columnDefinition = "default false")
	private boolean status;

	public Fruit() {
	}
    
    public void updateStatus(boolean status) {
		this.status = status;
	}
    ...
}
```
__FruitRepository__
```java
public interface FruitRepository extends JpaRepository<Fruit, Long> {
	List<Fruit> findAllByNameAndStatus(String name, boolean status);
}
```

### 문제 2
우리는 특정 과일을 기준으로 지금까지 우리 가게를 거쳐갔던 과일 개수를 세고 싶습니다.
<문제 1>에서 만들었던 과일 Entity Class를 이용해 기능을 만들어봅시다.
예를 들어,
1. (1, 사과, 3000원, 판매 O)
2. (2, 바나나, 4000원, 판매 X)
3. (3, 사과, 3000원, 판매 O)
   와 같은 세 데이터가 있고, 사과를 기준으로 과일 개수를 센다면, 우리의 API는 2를 반환할 것입니다.

구체적인 스펙은 다음과 같습니다.
- HTTP method : `GET`
- HTTP path : `/api/v1/fruit/count`
- HTTP query
    - name : 과일 이름
- 예시 `GET /api/v1/fruit/count?name=사과`
- HTTP 응답 Body
  ```json
  {
      "count":long
  }
  ```
- HTTP 응답 Body 예시
  ```json
  {
      "count":2
  }
  ```

__FruitController__
```java
@GetMapping("/api/v1/fruit/count")
public FruitCountResponse getFruitCount(@RequestParam String name) {
	return fruitService.getFruitCount(name);
}
```
__FruitCountResponse__
```java
public class FruitCountResponse {
	private long count;

	public FruitCountResponse(long count) {
		this.count = count;
	}

	public long getCount() {
		return count;
	}
}
```


__FruitServiceV2__
```java
public FruitCountResponse getFruitCount(String name) {
	long count = fruitRepository.countByName(name);
	return new FruitCountResponse(count);
}
```

__FruitRepository__
```java
public interface FruitRepository extends JpaRepository<Fruit, Long> {
	List<Fruit> findAllByNameAndStatus(String name, boolean status);

	long countByName(String name);
}
```




### 문제 3
우리는 아직 판매되지 않은 특정 금액 이상 혹은 특정 금액 이하의 과일 목록을 받아보고 싶습니다.
구체적인 스펙은 다음과 같습니다.
- HTTP method : `GET`
- HTTP path : `/api/v1/fruit/list`
- HTTP query
    - option : 'GTE' 혹은 'LTE'라는 문자열이 들어온다.
        - GTE : greater than equal
        - LTE : less than equal
    - price : 기준이 되는 금액이 들어온다.
- 예시 1 - `GET /api/v1/fruit/list?option=GTE&price=3000`
    - 판매되지 않은 3000원 이상의 과일 목록을 반환해야 한다.
- 예시 2 - `GET /api/v1/fruit/list?option=LTE&price=5000`
    - 판매되지 않은 5000원 이하의 과일 목록을 반환해야 한다.
- HTTP 응답 Body
  ```json
  [{
      "name":String,
      "price":long,
      "warehousingDate":LocalDate
  }, ...]
  ```
- HTTP 응답 Body 예시
  ```json
  [
    {
      "name":"사과",
      "price":4000,
      "warehousingDate":"2024-01-05"
    },
    {
      "name":"바나나",
      "price":6000,
      "warehousingDate":"2024-01-08"
    },...
  ]
  ```

__FruitController__
```java
@GetMapping("/api/v1/fruit/list")
public List<FruitResponse> getFruitsInRange(@RequestParam String option, @RequestParam long price) {
	return fruitService.getFruitsInRange(option, price);
}
```

__FruitResponse__
```java
public class FruitResponse {
  	private String name;
  	private long price;
  	private LocalDate warehousingDate;

  	public FruitResponse(Fruit fruit) {
  		this.name = fruit.getName();
  		this.price = fruit.getPrice();
  		this.warehousingDate = fruit.getWarehousingDate();
  	}

  	public String getName() {
  		return name;
  	}

  	public long getPrice() {
  		return price;
  	}

  	public LocalDate getWarehousingDate() {
  		return warehousingDate;
  	}
}
```

__FruitServiceV2__
```java
public List<FruitResponse> getFruitsInRange(String option, long price) {
  	List<Fruit> fruits;
  	if (option.equals("GTE")) {
  		fruits = fruitRepository.findAllByPriceGreaterThanEqualAndStatus(price, false);
  	} else if (option.equals("LTE")) {
  		fruits = fruitRepository.findAllByPriceLessThanEqualAndStatus(price, false);
  	} else {
  		throw new IllegalArgumentException();
  	}

  	return fruits.stream()
  		.map(FruitResponse::new)
  		.collect(Collectors.toList());
}
```

__FruitRepository__
```java
public interface FruitRepository extends JpaRepository<Fruit, Long> {
  	List<Fruit> findAllByNameAndStatus(String name, boolean status);

  	long countByName(String name);

  	List<Fruit> findAllByPriceGreaterThanEqualAndStatus(long price, boolean status);
  	List<Fruit> findAllByPriceLessThanEqualAndStatus(long price, boolean status);
}
```


## 참고
[자바와 스프링 부트로 생애 최초 서버 만들기, 누구나 쉽게 개발부터 배포까지! [서버 개발 올인원 패키지]](https://www.inflearn.com/course/%EC%9E%90%EB%B0%94-%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8-%EC%84%9C%EB%B2%84%EA%B0%9C%EB%B0%9C-%EC%98%AC%EC%9D%B8%EC%9B%90/dashboard)