package com.group.example.day05;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
