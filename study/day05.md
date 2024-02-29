# Clean Code
## Clean Code
"클린 코드(Clean Code)"는 로버트 C. 마틴(Robert C. Martin)이 저술한 동명의 책에서 유래한 용어로, 소프트웨어 개발 분야에서 널리 인정받는 개념입니다. 클린 코드는 단순히 동작하는 코드를 넘어서, 다른 개발자가 읽고 이해하기 쉬우며 유지보수가 용이한 코드를 의미합니다. 여기에는 여러 가지 원칙과 실천 방법이 포함되며, 주요 내용은 다음과 같습니다.

1. __가독성__:  코드는 명확하고 이해하기 쉬워야 하며, 의도가 분명해야 합니다. 변수, 함수, 클래스 이름은 그 목적이나 역할을 명확히 반영해야 합니다.

2. __간결성__: 불필요한 코드나 복잡성을 피하며, 가능한 한 간결하게 표현해야 합니다. 이는 유지보수를 용이하게 하고, 코드의 이해를 돕습니다.

3. __재사용 가능성__: 코드는 재사용이 가능하도록 일반적인 경우를 고려하여 설계되어야 합니다. 이를 통해 코드 중복을 줄이고, 효율성을 높일 수 있습니다.

4. __테스트 용이성__: 클린 코드는 테스트가 용이해야 합니다. 즉, 자동화된 단위 테스트를 작성하기 쉬워야 하며, 이를 통해 코드의 안정성을 보장할 수 있습니다.

5. __유지보수성__: 시간이 지나도 쉽게 수정, 확장 또는 개선할 수 있어야 합니다. 이는 코드의 구조와 설계가 잘 되어 있음을 의미합니다.

로버트 마틴은 이러한 원칙들을 통해 개발자들이 더 효율적으로, 더 높은 품질의 소프트웨어를 개발할 수 있도록 돕고자 합니다. 클린 코드를 작성하는 것은 개발 초기에 더 많은 시간과 노력을 요구할 수 있지만, 장기적으로는 프로젝트의 유지보수 비용을 줄이고, 개발 팀의 생산성을 향상시키는 데 큰 도움이 됩니다.

### 예시
먼저, 아래와 같이 모든 기능이 `Main` 클래스 하나에만 모여있는 코드가 있다고 가정합니다.
```java
public class Main {

    public static void main(String[] args) throws Exception {
        System.out.print("주사위를 던질 횟수를 입력하세요 : ");
        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        int r1 = 0, r2 = 0, r3 = 0, r4 = 0, r5 = 0, r6 = 0;

        for (int i = 0; i < a; i++) {
            double b = Math.random() * 6;
            if (b >= 0 && b < 1) {
                r1++;
            } else if (b >= 1 && b < 2) {
                r2++;
            } else if (b >= 2 && b < 3) {
                r3++;
            } else if (b >= 3 && b < 4) {
                r4++;
            } else if (b >= 4 && b < 5) {
                r5++;
            } else if (b >= 5 && b < 6) {
                r6++;
            }
        }

        System.out.printf("1이 %d번 나왔습니다.\n", r1);
        System.out.printf("2이 %d번 나왔습니다.\n", r2);
        System.out.printf("3이 %d번 나왔습니다.\n", r3);
        System.out.printf("4이 %d번 나왔습니다.\n", r4);
        System.out.printf("5이 %d번 나왔습니다.\n", r5);
        System.out.printf("6이 %d번 나왔습니다.\n", r6);
	}
}
```

이 코드는 `Game`이라는 클래스에 주사위 게임에 대한 책임을 나눠 아래와 같이 분리할 수 있습니다. 주사위의 범위가 달라지더라도(ex. 12..) 코드를 적게 수정할 수 있도록 작성하였습니다.


__Main__
```java
public class Main {
	public static void main(String[] args) throws Exception {
		int playNum = getPlayNum();

		int diceNum = 6;
		Game game = new Game(diceNum);
		List<GameResult> gameResults = game.play(playNum);

		printResults(gameResults);
	}

	private static int getPlayNum() {
		System.out.print("숫자를 입력하세요 : ");
		Scanner scanner = new Scanner(System.in);
		int playNum = scanner.nextInt();
		return playNum;
	}

	private static void printResults(List<GameResult> gameResults) {
		for (GameResult result : gameResults) {
			System.out.printf("%d은 %d번 나왔습니다.\n", result.getIndex(), result.getCount());
		}
	}
}
```

__GameResult__

```java
public class GameResult {
	private int index;
	private int count;

	public GameResult(int index, int count) {
		this.index = index;
		this.count = count;
	}

	public int getIndex() {
		return index;
	}

	public int getCount() {
		return count;
	}
}

```

__Game__

```java
public class Game {

	private int diceNum;
	private List<Integer> counts;

	public Game(int diceNum) {
		this.diceNum = diceNum;
		counts = new ArrayList<>(diceNum);
		for (int i = 0; i < diceNum; i++) {
			counts.add(0);
		}
	}

	public List<GameResult> play(int num) {
		for (int i = 0; i < num; i++) {
			int b = (int) (Math.random() * 6);
			counts.set(b, counts.get(b) + 1);
		}

		return IntStream.range(0, diceNum)
			.mapToObj(i -> new GameResult(i + 1, counts.get(i)))
			.collect(Collectors.toList());
	}
}

```
