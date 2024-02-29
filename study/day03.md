# 익명 클래스 Anonymous Class
이름 없이 선언과 동시에 객체를 생성하는 데 사용됩니다. 익명 클래스는 인터페이스나 추상 클래스를 구현하거나, 기존 클래스를 확장할 때 주로 사용됩니다. 익명 클래스의 주요 목적은 코드의 간결성을 높이고, 단 한번만 사용되는 클래스를 정의하는 데 있습니다. 이러한 클래스는 일반적으로 이벤트 리스너나 작은 콜백 객체 등을 구현하는 데 유용합니다.

익명 클래스는 간결하고 편리하지만, 몇 가지 제한 사항이 있습니다. 예를 들어, 다른 외부 객체에서 익명 클래스의 객체를 직접 참조할 수 없고, 익명 객체 내에서만 사용되는 변수는 `final`이어야 합니다. 따라서 익명 클래스의 사용은 그 범위와 용도를 고려하여 결정해야 합니다.

### 익명 클래스의 예제
다음은 `Runnable` 인터페이스의 익명 구현체를 생성합니다.
```java
Runnable task = new Runnable() {
    @Override
    public void run() {
        System.out.println("익명 클래스를 사용한 Runnable의 구현");
    }
};

new Thread(task).start();
```
이 예제에서 `Runnable` 인터페이스는 이름 없이 구현되며, 구현된 객체는 `task` 변수에 할당됩니다. 이 객체는 `Thread` 생성자에 전달되어 새 스레드에서 실행됩니다.

# 람다식 Lambda Expression
자바의 람다식은 자바 8부터 도입된 기능으로, 간결한 방식으로 익명 함수를 표현할 수 있게 해줍니다. 람다식은 주로 함수형 인터페이스의 인스턴스를 생성할 때 사용됩니다. __람다식은 자바에서 함수형 프로그래밍 패러다임을 지원해줍니다.__ 함수를 일급 객체로 다룰 수 있게 해주며, 코드의 추상화 수준을 높여줍니다. 또한, 람다식은 스트림 API와 함께 사용될 때 병렬 처리를 쉽게 구현할 수 있게 해주어 데이터 처리 성능을 향상시킬 수 있습니다.

### 람다식의 구문
람다식은 화살표(`->`) 연산자를 사용하여 표현됩니다. 왼쪽에는 매개변수(parameter)가 오고, 오른쪽에는 실행될 코드 또는 표현식(expression)이 옵니다. 매개변수가 하나뿐이라면 괄호를 생략할 수 있고, 실행 코드가 한 줄이라면 중괄호도 생략할 수 있습니다.
```java
(매개변수) -> { 실행 코드 }
```

### 람다식의 예제
```java
// 매개 변수 없는 람다식
() -> System.out.println("Hello, Lambda!");

// 매개 변수를 받고 값을 반환하는 람다식
(int a, int b) -> { return a + b; }
```

## @FunctionalInterface
`@FunctionalInterface`는 자바 8에서 도입된 annotation으로, 해당 어노테이션이 붙은 인터페이스가 함수형 인터페이스임을 의미합니다. 함수형 인터페이스는 추상 메소드를 딱 하나만 가지고 있어야 하며, 이는 람다 표현식을 사용하여 해당 인터페이스의 구현체를 제공할 수 있음을 의미합니다. 함수형 인터페이스는 자바에서 람다 표현식과 함께 사용될 때 강력한 표현력을 발휘하며, 코드의 간결성과 가독성을 크게 향상시킵니다.

### 함수형 인터페이스의 특징
- __정확히 하나의 추상 메소드__를 가지고 있어야 합니다. 이 추상 메소드는 함수형 인터페이스의 대상 메소드(target method)입니다.
- `@FunctionalInterface` 애너테이션은 선택적입니다. 즉, 추상 메소드가 하나뿐인 인터페이스는 애너테이션이 없어도 함수형 인터페이스로 간주됩니다. 하지만, 이 애너테이션을 명시적으로 사용함으로써 컴파일러에게 해당 인터페이스가 함수형 인터페이스임을 명확히 알립니다. 이는 실수로 메소드를 추가하는 것을 방지하여 인터페이스가 계속해서 함수형 인터페이스의 조건을 만족하도록 합니다.
- **정적 메소드(static methods)**와 **디폴트 메소드(default methods)**를 포함할 수 있습니다. 이들 메소드는 함수형 인터페이스의 추상 메소드 개수에 포함되지 않습니다.
- `java.lang.Object` 클래스의 `public` 메소드를 추상 메소드로 오버라이딩하는 것은 추상 메소드의 개수에 포함되지 않습니다.

### @FuntionalInterface 사용 예제
```java
@FunctionalInterface
public interface SimpleFunctionalInterface {
    void doWork();
}
```
```java
SimpleFunctionalInterface sfi = () -> System.out.println("Doing work");
sfi.doWork(); // 출력: Doing work

```

# 함수형 프로그래밍 Functional Programming
함수형 프로그래밍은 프로그래밍 패러다임 중 하나로, 계산을 수학적 함수의 평가ㅗㄹ 처리하고 상태 변경이나 가변 데이터를 피하는 것을 중심으로 합니다. 이 접근 방식은 사이드 이펙트를 최소화하고, 프로그램의 예측 가능성과 유지 보수성을 높이는 데 도움이 됩니다.

### 함수형 프로그래밍의 특징
1. 불변성(Immutability)
   데이터는 변하지 않습니다. 데이터를 변경해야 할 경우, 원본 데이터는 그대로 유지하고 변경된 새 데이터를 생성합니다.
2. 함수의 일급 객체(First-class functions)
   함수를 일급 객체로 취급합니다. 이는 함수를 변수에 할당하거나, 다른 함수의 인자로 전달하거나, 함수에서 함수를 반환할 수 있음을 의미합니다.
3. 고차 합수(Higher-order functions)
   함수를 인자로 받거나 함수를 결과로 반환하는 함수입니다. 이를 통해 함수의 조합과 추상화를 쉽게 할 수 있습니다.
4. 순수 함수(Pure functions)
   동일한 입력에 대해 항상 동일한 출력을 반환하는 함수입니다. 순수 함수는 외부 상태를 변경하지 않으며 외부 상태에 의존하지 않습니다.

### 함수형 프로그래밍의 장점
- __모듈성과 재사용성__
  함수의 독립성과 일급 객체로서의 특성 덕분에 코드의 모듈성이 향상되고, 함수 재사용성이 높아집니다.
- __버그 감소와 유지 보수성 향상__
  불변성과 순수 함수의 사용으로 인해 발생할 수 있는 버그가 줄어들고, 프로그램의 동작을 예측하기 쉬워집니다.
- __병렬 처리 용이__
  데이터의 불변성으로 인해 여러 스레드에서 동시에 데이터를 안전하게 읽을 수 있어 병렬 처리가 용이합니다.

### 함수형 프로그래밍의 단점
- __학습 곡선__
  전통적인 명령형 프로그래밍에 익숙한 개발자에게 함수형 프로그래밍 개념은 처음에 이해하기 어려울 수 있습니다.
- __성능 이슈__ 불변 데이터 구조와 레이지 평가는 때때로 성능 저하를 초래할 수 있습니다. 하지만 적절한 최적화로 대부분의 문제를 해결할 수 있습니다.




## 스트림 API Stream API
자바 8에서 도입된 스트림 API는 컬렉션, 배열 또는 I/O 자원 등의 데이터 원본을 함수형 접근 방식으로 처리할 수 있는 기능을 제공합니다. 이 API는 데이터를 추상화된 연속 스트림으로 변환하고, 다양한 중간 연산과 최종 연산을 통해 데이터를 처리하고 결과를 생성합니다.

### 스트림 API의 주요 특징
- 선언적 처리: 데이터를 처리하는 과정을 간결하고 명시적으로 표현할 수 있어 코드의 가독성이 향상됩니다.
- 불변성: 스트림 연산은 원본 데이터를 변경하지 않으며, 필요한 경우 새로운 스트림을 반환합니다.
- 내부 반복: 스트림 API는 내부적으로 반복을 처리하여 사용자가 직접 반복 로직을 작성할 필요가 없습니다.
- 병렬 처리: parallelStream()을 사용하여 손쉽게 병렬 처리를 할 수 있어 데이터 처리 성능을 향상시킬 수 있습니다.

### 스트림 연산의 종류
- 중간 연산: 스트림을 변환하는 연산으로, 필터링(filtering), 매핑(mapping), 정렬(sorting) 등이 있습니다. 중간 연산은 또 다른 스트림을 반환하며, 연속적으로 연결할 수 있습니다. 중간 연산은 최종 연산이 호출될 때까지 지연(lazy) 실행됩니다.

- 최종 연산: 스트림 처리를 종료하고 결과를 도출하는 연산으로, 반복(iteration), 집계(aggregation), 검사(checking) 등이 있습니다. 최종 연산이 호출되면, 그때서야 중간 연산이 실행되고 스트림의 데이터가 처리됩니다.

### 스트림 API 사용 예제
```java
import java.util.Arrays;
import java.util.List;

public class StreamExample {
    public static void main(String[] args) {
        List<String> items = Arrays.asList("apple", "banana", "cherry", "apple", "date");

        // 중복 제거 후 대문자로 변환하여 출력
        items.stream()                   // 스트림 생성
             .distinct()                // 중복 제거 (중간 연산)
             .map(String::toUpperCase)  // 각 요소를 대문자로 변환 (중간 연산)
             .forEach(System.out::println); // 결과 출력 (최종 연산)
    }
}

```

## 메소드 레퍼런스
메소드 레퍼런스(Method Reference)는 자바 8에서 소개된 기능으로, 메소드를 직접 호출하는 대신 메소드의 참조를 전달하는 간결한 방식입니다. 람다 표현식(lambda expression)을 더욱 단순화할 수 있어 코드의 가독성을 높이는 데 유용합니다. 메소드 레퍼런스는 특정 메소드만을 호출하는 람다 표현식을 대체하는 방법으로 사용됩니다. 즉, 람다 표현식을 사용할 수 있는 모든 곳에서 메소드 레퍼런스를 사용할 수 있습니다. 메소드 레퍼런스는 코드를 더 간결하고 명확하게 만들어 주므로, 코드의 가독성과 유지보수성을 향상시키는 데 도움이 됩니다.

### 메소드 레퍼런스의 종류
1. 정적 메소드 레퍼런스
   클래스의 정적 메소드 참조에 사용됩니다. `클래스명::정적메소드명` 형식으로 사용합니다.
```java
// 람다 표현식
Consumer<String> lambda = s -> System.out.println(s);
// 메소드 레퍼런스
Consumer<String> methodRef = System.out::println;
```

2. 인스턴스 메소드 레퍼런스
   특정 객체의 인스턴스 메소드 참조에 사용됩니다. `인스턴스명::인스턴스메소드명` 형식으로 사용합니다.
```java
// 람다 표현식
Consumer<String> lambda = s -> instance.method(s);
// 메소드 레퍼런스
Consumer<String> methodRef = instance::method;
```

3. 임의 객체의 인스턴스 메소드 레퍼런스
   특정 타입의 임의 객체에 대한 인스턴스 메소드 참조에 사용됩니다. `클래스명::인스턴스메소드명` 형식으로 사용합니다.
```java
// 람다 표현식
Function<String, Integer> lambda = s -> s.length();
// 메소드 레퍼런스
Function<String, Integer> methodRef = String::length;
```

4. 생성자 레퍼런스
   클래스의 생성자 참조에 사용됩니다. `클래스명::new` 형식으로 사용합니다.
```java
// 람다 표현식
Supplier<List<String>> lambda = () -> new ArrayList<>();
// 메소드 레퍼런스
Supplier<List<String>> methodRef = ArrayList::new;
```
