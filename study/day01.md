# 어노테이션 Annotation
## 개념
코드에 대한 메타데이터를 제공하는 방법입니다. 즉, 소스 코드에 추가적인 정보를 제공하거나, 컴파일 타임 및 런타임에 특정 동작을 하도록 지시할 수 있는 마크업 요소입니다. 리플렉션을 통해 런타임에 조회할 수 있으며, 코드의 가독성, 유지보수성을 높이고 버그의 가능성을 줄이는 데 도움을 줍니다.

### 예시
```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RequestMapping(method = RequestMethod.GET)
public @interface GetMapping {

	/**
	 * Alias for {@link RequestMapping#name}.
	 */
	@AliasFor(annotation = RequestMapping.class)
	String name() default "";

	/**
	 * Alias for {@link RequestMapping#value}.
	 */
	@AliasFor(annotation = RequestMapping.class)
	String[] value() default {};

	/**
	 * Alias for {@link RequestMapping#path}.
	 */
	@AliasFor(annotation = RequestMapping.class)
	String[] path() default {};

	/**
	 * Alias for {@link RequestMapping#params}.
	 */
	@AliasFor(annotation = RequestMapping.class)
	String[] params() default {};

	/**
	 * Alias for {@link RequestMapping#headers}.
	 */
	@AliasFor(annotation = RequestMapping.class)
	String[] headers() default {};

	/**
	 * Alias for {@link RequestMapping#consumes}.
	 * @since 4.3.5
	 */
	@AliasFor(annotation = RequestMapping.class)
	String[] consumes() default {};

	/**
	 * Alias for {@link RequestMapping#produces}.
	 */
	@AliasFor(annotation = RequestMapping.class)
	String[] produces() default {};

}
```
- `@Target(ElementType.METHOD)` : 이 어노테이션이 메서드에만 적용될 수 있음을 나타냅니다.
- `@Retention(RetentionPolicy.RUNTIME)` : 이 어노테이션의 정보가 런타임에도 유지되어야 함을 나타냅니다. 즉, 런타임에 리플렉션을 통해 이 어노테이션 정보에 접근할 수 있습니다.
- `@Documented` : 이 어노테이션이 Javadoc과 같은 문서에 포함될 수 있음을 나타냅니다.
- `@RequestMapping(method = RequestMethod.GET)` : 이것은 `@GetMapping`이 기본적으로 `@RequestMapping`을 사용하며, HTTP 메서드로 GET을 사용함을 지정합니다.

---

## 어노테이션의 용도
1. 컴파일러에게 코드 문법 에러를 체크하도록 정보를 제공합니다.
   예를 들어, `@Override` 어노테이션은 메서드가 오버라이드 되었음을 컴파일러에게 알립니다. 만약 부모 클래스 또는 인터페이스에서 해당 메서드를 찾을 수 없다면, 컴파일러는 에러를 발생시킵니다.

2. 소프트웨어 개발 툴이 코드를 생성하거나 설정 파일을 생성할 때 참조 정보를 제공합니다.
   예를 들어, `@Entity`, `@Service`, `@Repository` 등이 있습니다.

3. 실행 시 특정 기능을 실행하도록 정보를 제공합니다.
   리플렉션을 사용하여 어노테이션 정보를 런타임에 읽어서 특정 로직을 실행할 수 있습니다. 예를 들어, `@Autowired`는 스프링 프레임워크에서 의존성 주입에 사용됩니다.

> 리플렉션(Reflection): 자바에서 런타임에 클래스, 인터페이스, 필드, 메서드 등과 같은 객체의 정보를 조회하거나 수정할 수 있는 API를 제공하는 기능

---

## 어노테이션의 종류
1. 표준 어노테이션
   자바에서 기본적으로 제공하는 어노테이션입니다.
   `@Override`, `@Deprecated` 등이 여기에 해당합니다.

2. 메타 어노테이션
   다른 어노테이션을 정의할 때 사용하는 어노테이션입니다.
   `@Target`, `@Retention`, `@Documented` 등이 있습니다.

3. 확장 어노테이션
   `@Entity`와 같이 JPA, Spring Framework, Hibernate 등의 특정 프레임워크나 라이브럴, 또는 Java EE 사양에 정의되어 있는 어노테이션입니다. 이러한 어노테이션들은 해당 기술 스택을 사용할 때만 의미를 갖습니다.

4. 커스텀 어노테이션
   개발자가 직접 정의하여 사용하는 어노테이션입니다.
   `@interface` 키워드를 사용하여 정의합니다.

---


## 어노테이션 정의 방법
커스텀 어노테이션을 만들 때는 `@interface` 키워드를 사용합니다. 어노테이션 내에서 메서드 선언을 통해 어노테이션의 요소를 정의할 수 있으며, 각 요소는 기본값을 가질 수 있습니다.

__정의 예시__
```java
public @interface MyAnnotation {
    String value() default "Hello";
}
```
__사용 예시__
```java
@MyAnnotation(value = "Example")
public class MyClass {
}
```