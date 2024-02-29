# API 만들기
## 문제 1
두 수를 입력하면 다음과 같은 결과가 나오는 `GET` API 를 만들어보자.
- path: `/api/v1/calc` 이다.
- 쿼리 파라이터: num1, num2

__응답 예시__
```json
{
  "add" : 덧셈결과,
  "minus" : 뺄셈결과,
  "muliply" : 곱셈결과
}
```

### 코드
__Controller__
```java
@GetMapping("/api/v1/calc")
public CalculatorResponse calculator(@RequestParam int num1, @RequestParam int num2){
	return new CalculatorResponse(num1 + num2, num1 - num2, num1 * num2);
}
```

__Response Dto__

```java
package com.group.libraryapp.dto.calculator.response;

public class CalculatorResponse {
	private int add;
	private int minus;
	private int multiply;

	public CalculatorResponse(int add, int minus, int multiply) {
		this.add = add;
		this.minus = minus;
		this.multiply = multiply;
	}

	public int getAdd() {
		return add;
	}

	public int getMinus() {
		return minus;
	}

	public int getMultiply() {
		return multiply;
	}
}

```

__input__
```
http://localhost:8080/api/v1/calc?num1=10&num2=5
```

__output__
```json
{
    "add": 15,
    "minus": 5,
    "multiply": 50
}
```

## 문제 2
날짜를 입력하면, 몇 요일인지 알려주는 `GET` API를 만들어 보자.
path와 쿼리 파라미터는 임의로 만든다.

__응답 예시__
```json
{
	"dayOfTheWeek" : "MONDAY"
}
```

### 코드
__Controller__
```java
@GetMapping("/api/v1/date")
public DateResponse getDate(@RequestParam String date) {
		LocalDate localDate = LocalDate.parse(date);
	return new DateResponse(localDate.getDayOfWeek().name());
}
```

__Response Dto__
```java
package com.group.libraryapp.dto.calculator.response;

public class DateResponse {
	private String dayOfTheWeek;

	public DateResponse(String dayOfTheWeek) {
		this.dayOfTheWeek = dayOfTheWeek;
	}

	public String getDayOfTheWeek() {
		return dayOfTheWeek;
	}
}
```

__input__
```
http://localhost:8080/api/v1/date?date=2024-02-20
```

__output__
```jason
{
    "dayOfTheWeek": "TUESDAY"
}
```


## 문제 3
여러 수를 받아 총 합을 반환하는 `POST` API를 만들어 보자.
API에서 받는 Body는 다음과 같은 형태이다.

__요청 예시__
```json
{
	"numbers" : [1,2,3,4,5]
}
```

### 코드
__Controller__
```java
@PostMapping("/api/v1/sum")
public int getSum(@RequestBody SumRequest request) {
	int sum = 0;
	for (Integer value : request.getNumbers()) {
		sum += value;
	}
	return sum;
}
```

__Request Dto__
```java
package com.group.libraryapp.dto.calculator.request;

import java.util.List;

public class SumRequest {

	private List<Integer> numbers;

	public List<Integer> getNumbers() {
		return numbers;
	}
}
```

__input__
```
http://localhost:8080/api/v1/sum
```
```json
{
    "numbers" : [1,2,3,4,5]
}
```

__output__
```
15
```