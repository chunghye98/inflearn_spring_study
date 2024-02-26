package com.group.example.day05;

import java.util.List;
import java.util.Scanner;

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
