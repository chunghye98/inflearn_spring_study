# 데이터베이스를 연결하여 API 만들기
## 문제 1
우리는 작은 과일 가게를 운영하고 있습니다. 과일 가게에 입고된 '과일 정보'를 저장하는 API를 만들어 봅시다. 스펙은 다음과 같습니다.
- HTTP method: `POST`
- HTTP path : `/api/v1/fruit`
- HTTP 요청 Body
```json
{
	"name" : String,
  	"warehousingDate" : LocalDate,
  	"price" : long
}
```
- 응답: 성공 시 200

> __API에서 long을 사용하는 이유__
> 1. 데이터의 범위(int : 32비트, long : 64비트)가 long형이 더 크기 때문에 큰 수를 다뤄야 하는 경우에 int형보다 적합하다.
> 2. 미래에 발생할 수 있는 범위 초과 문제를 방지할 수 있다.
> 3. 특정 플랫폼이나 데이터베이스는 기본적으로 64비트 정수를 사용하여 데이터를 저장할 수 있으므로 시스템 호환성을 보장할 수 있다. (ex. GO, RUST, etc)
> 4. 더 큰 정수 타입을 사용하면 더 정밀한 계산이 가능하다.

### 자바 코드
__FruitController__
```java
@RestController
public class FruitController {

	private final JdbcTemplate jdbcTemplate;

	public FruitController(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@PostMapping("/api/v1/fruit")
	public void createFruit(@RequestBody FruitCreateRequest request) {
		String sql = "INSERT INTO fruit (name, price, stocked_date) VALUES (?, ?,?)";

		jdbcTemplate.update(sql, request.getName(), request.getPrice(), request.getWarehousingDate());
	}
}
```

__FruitCreateRequest__
```java
public class FruitCreateRequest {
	private String name;
	private LocalDate warehousingDate;
	private long price;

	public String getName() {
		return name;
	}

	public LocalDate getWarehousingDate() {
		return warehousingDate;
	}

	public long getPrice() {
		return price;
	}
}
```

## 문제 2
과일이 팔리게 되면, 우리 시스템에 팔린 과일 정보를 기록해야 합니다. 스펙은 다음과 같습니다.
- HTTP method: `PUT`
- HTTP path : `/api/v1/fruit`
- HTTP 요청 Body
```json 
{
	"id" : long
}
```
- 응답: 성공 시 200

### 자바 코드
__FruitController__
```java
@PutMapping("/api/v1/fruit")
public void updateFruit(@RequestBody FruitUpdateRequest request) {
	String readSql = "SELECT * FROM fruit WHERE id = ?";
	boolean isExistFruit = jdbcTemplate.query(readSql, (rs, rowNum) -> 0, request.getId()).isEmpty();
	if(isExistFruit){
		throw new IllegalArgumentException("존재하지 않는 과일입니다.");
	}

	String sql = "UPDATE fruit SET status = ?";
	jdbcTemplate.update(sql, true);
}
```

__FruitUpdateRequest__
```java
public class FruitUpdateRequest {
	private long id;

	public long getId() {
		return id;
	}
}
```

__성공 응답__
```
200 OK
```

__예외 발생__
```
java.lang.IllegalArgumentException: 존재하지 않는 과일입니다.
```

## 문제 3
우리는 특정 과일을 기준으로 팔린 금액, 팔리지 않은 금액을 조회하고 싶습니다.
예를 들어
1. (1, 사과, 3000원, 판매 O)
2. (2, 사과, 4000원, 판매 X)
3. (3, 사과, 3000원, 판매 O)
   와 같은 세 데이터가 있다면 우리의 API는 판매된 금액 : 6000원, 판매되지 않은 금액 : 4000원이라고 응답해야 합니다.
   구체적인 스펙은 다음과 같습니다.
- HTTP method : `GET`
- HTTP path : `/api/v1/fruit/stat
- HTTP query
    - name : 과일 이름
- 예시 `GET /api/v1/fruit/stat?name=사과`
- HTTP 응답 Body
```json
{
	"salesAmount" : long,
  	"notSalesAmount" : long
}
```

### 자바 코드
__FruitController__
```java
@GetMapping("/api/v1/fruit/stat")
public FruitAmountReadResponse getFruitByStatus(@RequestParam String name) {
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
```

__FruitAmountReadResponse__
```java
public class FruitAmountReadResponse {
	private long salesAmount;
	private long notSalesAmount;

	public FruitAmountReadResponse(long salesAmount, long notSalesAmount) {
		this.salesAmount = salesAmount;
		this.notSalesAmount = notSalesAmount;
	}

	public long getSalesAmount() {
		return salesAmount;
	}

	public long getNotSalesAmount() {
		return notSalesAmount;
	}
}
```
