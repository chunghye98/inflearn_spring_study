# Layered Architecture 로 API 만들기
## Layered Architecture
계층 구조는 애플리케이션의 관심사를 명확히 분리하여, 유지보수가 쉽고, 확장성 있는 코드를 작성할 수 있게 돕습니다.
각각의 계층은 특정한 역할을 담당하며, 다음과 같이 구성됩니다:

### Controller
__역할__
사용자의 요청을 받아 처리하는 계층입니다. HTTP 요청을 받아, 해당 요청을 처리하기 위해 필요한 입력을 검증하고, 적절한 서비스 계층의 메소드를 호출합니다. 그리고 서비스 계층으로부터 받은 데이터를 사용자에게 전달하는 역할을 합니다.
__특징__
Controller는 일반적으로 사용자의 입력 검증, HTTP 요청 및 응답의 처리를 담당하며, 비즈니스 로직이나 데이터 접근 로직을 직접 구현하지 않습니다. 대신, 이러한 작업을 서비스 계층에 위임합니다.
### Service
__역할__
애플리케이션의 비즈니스 로직을 구현하는 계층입니다. 사용자의 요청에 따라 데이터를 처리하거나 변환하는 작업을 담당하며, 필요에 따라 여러 리포지토리를 조합하여 복잡한 비즈니스 로직을 수행합니다.
__특징__
Service 계층은 도메인 모델의 비즈니스 규칙을 구현합니다. 이 계층은 데이터의 영속성과는 독립적으로 비즈니스 로직을 수행할 수 있도록 설계되어 있습니다. 이를 통해 비즈니스 로직의 재사용성과 테스트 용이성이 향상됩니다.
### Repository
__역할__
데이터와 관련된 작업, 특히 데이터베이스로의 CRUD(Create, Read, Update, Delete) 연산을 추상화하는 계층입니다. 이 계층은 데이터베이스에 대한 모든 접근을 캡슐화하여, 비즈니스 로직이 데이터 저장 방식의 구체적인 세부사항을 알 필요가 없게 합니다.
__특징__
Repository는 일반적으로 각 도메인 모델(예: 사용자, 주문 등)에 대해 하나씩 구현됩니다. 이 계층은 데이터 접근 로직과 비즈니스 로직을 분리하여, 데이터베이스나 데이터 저장 방식의 변경이 서비스 계층이나 컨트롤러 계층에 영향을 미치지 않도록 합니다.
### 작동 원리
1. __요청 처리__
   사용자의 요청은 먼저 Controller 계층에 도달합니다. Controller는 요청을 검증하고 처리하기 위해 적절한 Service 계층의 메소드를 호출합니다.
2. __비즈니스 로직 실행__
   Service 계층은 요청에 따른 비즈니스 로직을 실행합니다. 필요한 데이터는 Repository 계층을 통해 조회하거나 저장됩니다.
3. __데이터 접근__
   Repository 계층은 데이터베이스나 다른 저장소에 접근하여 필요한 데이터 작업을 수행합니다.
4. __응답 반환__
   처리 결과는 Controller를 통해 사용자에게 다시 전달됩니다.

이 구조를 통해, 애플리케이션의 각 부분은 명확히 정의된 역할과 책임을 가지며, 이는 코드의 가독성, 유지보수성 및 확장성을 크게 향상시킵니다.


## 예시
### Layered Architecture을 적용해보자
[이전에 만들었던 API](https://velog.io/@chunghye98/JavaSpring-%EB%8D%B0%EC%9D%B4%ED%84%B0%EB%B2%A0%EC%9D%B4%EC%8A%A4%EB%A5%BC-%EC%97%B0%EA%B2%B0%ED%95%98%EC%97%AC-API-%EB%A7%8C%EB%93%A4%EA%B8%B0)를 분리해보자.


__FruitController__
```java
@RestController
public class FruitController {

	private final FruitService fruitService;


	public FruitController(FruitService fruitService) {
		this.fruitService = fruitService;
	}

	@PostMapping("/api/v1/fruit")
	public void createFruit(@RequestBody FruitCreateRequest request) {
		fruitService.createFruit(request);
	}

	@PutMapping("/api/v1/fruit")
	public void updateFruit(@RequestBody FruitUpdateRequest request) {
		fruitService.updateFruit(request);
	}

	@GetMapping("/api/v1/fruit/stat")
	public FruitAmountReadResponse getFruitByStatus(@RequestParam String name) {
		return fruitService.getFruitByStatus(name);
	}
}
```

__FruitService__
```java
@Service
public class FruitService {

	private final FruitRepository fruitRepository;

	public FruitService(FruitRepository fruitRepository) {
		this.fruitRepository = fruitRepository;
	}

	public void createFruit(FruitCreateRequest request) {
		fruitRepository.createFruit(request.getName(), request.getPrice(), request.getWarehousingDate());
	}

	public void updateFruit(FruitUpdateRequest request) {
		boolean isExistFruit = fruitRepository.isExistFruit(request.getId());
		if (isExistFruit) {
			throw new IllegalArgumentException("존재하지 않는 과일입니다.");
		}

		fruitRepository.updateFruit(request.getId());
	}

	public FruitAmountReadResponse getFruitByStatus(String name) {
		return fruitRepository.getFruitByStatus(name);
	}
}
```

__FruitRepository__
```java
@Repository
public class FruitRepository {

	private final JdbcTemplate jdbcTemplate;

	public FruitRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void createFruit(String name, long price, LocalDate warehousingDate) {
		String sql = "INSERT INTO fruit (name, price, stocked_date) VALUES (?, ?,?)";

		jdbcTemplate.update(sql, name, price, warehousingDate);
	}

	public void updateFruit(long id) {
		String sql = "UPDATE fruit SET status = ? WHERE id = ?";
		jdbcTemplate.update(sql, true, id);
	}

	public boolean isExistFruit(long id) {
		String readSql = "SELECT * FROM fruit WHERE id = ?";
		return jdbcTemplate.query(readSql, (rs, rowNum) -> 0, id).isEmpty();
	}

	public FruitAmountReadResponse getFruitByStatus(String name) {
		String sql = "SELECT "
			+ "	 SUM(CASE WHEN status = true THEN price ELSE 0 END) AS salesAmount,\n"
			+ "  SUM(CASE WHEN status = false THEN price ELSE 0 END) AS notSalesAmount\n"
			+ "FROM fruit\n"
			+ "WHERE name = ?";

		return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
			long salesAmount = rs.getLong("salesAmount");
			long notSalesAmount = rs.getLong("notSalesAmount");
			return new FruitAmountReadResponse(salesAmount, notSalesAmount);
		}, name);
	}
}
```

### @Primary 어노테이션 적용
__FruitRepository__
```java
public interface FruitRepository {
	void createFruit(String name, long price, LocalDate warehousingDate);
	void updateFruit(long id);
	boolean isExistFruit(long id);
	FruitAmountReadResponse getFruitByStatus(String name);
}
```

__FruitMemoryRepository__
```java

@Repository
public class FruitMemoryRepository implements FruitRepository{
	private final List<Fruit> fruits = new ArrayList<>();
	private final AtomicLong counter = new AtomicLong();

	@Override
	public void createFruit(String name, long price, LocalDate warehousingDate) {
		fruits.add(new Fruit(counter.incrementAndGet(), name, price, warehousingDate, false));
	}

	@Override
	public void updateFruit(long id) {
		fruits.stream()
			.filter(fruit -> fruit.getId() == id)
			.findFirst()
			.ifPresent(fruit -> fruit.setStatus(true));
	}

	@Override
	public boolean isExistFruit(long id) {
		return fruits.stream().anyMatch(fruit -> fruit.getId() == id);
	}

	@Override
	public FruitAmountReadResponse getFruitByStatus(String name) {
		long salesAmount = fruits.stream()
			.filter(fruit -> fruit.getName().equals(name) && fruit.isStatus())
			.mapToLong(Fruit::getPrice)
			.sum();

		long notSalesAmount = fruits.stream()
			.filter(fruit -> fruit.getName().equals(name) && !fruit.isStatus())
			.mapToLong(Fruit::getPrice)
			.sum();

		return new FruitAmountReadResponse(salesAmount, notSalesAmount);
	}
}
```

__Fruit__
```java
public class Fruit {
	private final long id;
	private final String name;
	private final long price;
	private final LocalDate warehousingDate;
	private boolean status;

	public Fruit(long id, String name, long price, LocalDate warehousingDate, boolean status) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.warehousingDate = warehousingDate;
		this.status = status;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public long getPrice() {
		return price;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
}

```

__FruitMySQLRepository__
```java
@Primary
@Repository
public class FruitMySqlRepository implements FruitRepository {

	private final JdbcTemplate jdbcTemplate;

	public FruitMySqlRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void createFruit(String name, long price, LocalDate warehousingDate) {
		String sql = "INSERT INTO fruit (name, price, stocked_date) VALUES (?, ?,?)";

		jdbcTemplate.update(sql, name, price, warehousingDate);
	}

	@Override
	public void updateFruit(long id) {
		String sql = "UPDATE fruit SET status = ? WHERE id = ?";
		jdbcTemplate.update(sql, true, id);
	}

	@Override
	public boolean isExistFruit(long id) {
		String readSql = "SELECT * FROM fruit WHERE id = ?";
		return jdbcTemplate.query(readSql, (rs, rowNum) -> 0, id).isEmpty();
	}

	@Override
	public FruitAmountReadResponse getFruitByStatus(String name) {
		String sql = "SELECT "
			+ "	 SUM(CASE WHEN status = true THEN price ELSE 0 END) AS salesAmount,\n"
			+ "  SUM(CASE WHEN status = false THEN price ELSE 0 END) AS notSalesAmount\n"
			+ "FROM fruit\n"
			+ "WHERE name = ?";

		return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
			long salesAmount = rs.getLong("salesAmount");
			long notSalesAmount = rs.getLong("notSalesAmount");
			return new FruitAmountReadResponse(salesAmount, notSalesAmount);
		}, name);
	}
}
```