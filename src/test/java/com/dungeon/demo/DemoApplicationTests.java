package com.dungeon.demo;

import com.dungeon.demo.exception.InvalidDungeonException;
import com.dungeon.demo.model.Block;
import com.dungeon.demo.model.Dungeon;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.dungeon.demo.model.Block.AIR;
import static com.dungeon.demo.model.Block.GROUND;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void twoComparableDungeons() {
		Block[][] blocksA = {
				{AIR, AIR, AIR, AIR, AIR, AIR, AIR},
				{GROUND, AIR, AIR, GROUND, AIR, GROUND, AIR},
				{AIR, AIR, GROUND, GROUND, GROUND, GROUND, GROUND},
				{AIR, AIR, GROUND, GROUND, GROUND, GROUND, GROUND}
		};
		Dungeon dungeonA = new Dungeon(blocksA);

		Block[][] blocksB = {
				{GROUND, GROUND, AIR, AIR, GROUND, GROUND, AIR},
				{AIR, AIR, AIR, AIR, AIR, AIR, AIR},
				{GROUND, GROUND, GROUND, AIR, GROUND, GROUND, GROUND},
				{GROUND, GROUND, GROUND, GROUND, GROUND, GROUND, GROUND}
		};
		Dungeon dungeonB = new Dungeon(blocksB);
		DungeonPool dungeonPool = new DungeonPool(List.of(dungeonA, dungeonB));
		List<Dungeon> xSequence = dungeonPool.createXSequence(5);
		System.out.println(xSequence);

		assertEquals(xSequence.size(), 5);
	}

	@Test
	void testZeroSequenceSize() {
		Block[][] blocksA = {
				{AIR, AIR, AIR, AIR, AIR, AIR, AIR},
				{GROUND, AIR, AIR, GROUND, AIR, GROUND, AIR},
				{AIR, AIR, GROUND, GROUND, GROUND, GROUND, GROUND},
				{AIR, AIR, GROUND, GROUND, GROUND, GROUND, GROUND}
		};
		Dungeon dungeonA = new Dungeon(blocksA);

		Block[][] blocksB = {
				{GROUND, GROUND, AIR, AIR, GROUND, GROUND, AIR},
				{AIR, AIR, AIR, AIR, AIR, AIR, AIR},
				{GROUND, GROUND, GROUND, AIR, GROUND, GROUND, GROUND},
				{GROUND, GROUND, GROUND, GROUND, GROUND, GROUND, GROUND}
		};
		Dungeon dungeonB = new Dungeon(blocksB);
		DungeonPool dungeonPool = new DungeonPool(List.of(dungeonA, dungeonB));
		List<Dungeon> xSequence = dungeonPool.createXSequence(0);
		System.out.println(xSequence);

		assertEquals(xSequence.size(), 0);
	}

	@Test
	void testNegativeSequenceSize() {
		Block[][] blocksA = {
				{AIR, AIR, AIR, AIR, AIR, AIR, AIR},
				{GROUND, AIR, AIR, GROUND, AIR, GROUND, AIR},
				{AIR, AIR, GROUND, GROUND, GROUND, GROUND, GROUND},
				{AIR, AIR, GROUND, GROUND, GROUND, GROUND, GROUND}
		};
		Dungeon dungeonA = new Dungeon(blocksA);

		Block[][] blocksB = {
				{GROUND, GROUND, AIR, AIR, GROUND, GROUND, AIR},
				{AIR, AIR, AIR, AIR, AIR, AIR, AIR},
				{GROUND, GROUND, GROUND, AIR, GROUND, GROUND, GROUND},
				{GROUND, GROUND, GROUND, GROUND, GROUND, GROUND, GROUND}
		};
		Dungeon dungeonB = new Dungeon(blocksB);
		DungeonPool dungeonPool = new DungeonPool(List.of(dungeonA, dungeonB));
		List<Dungeon> xSequence = dungeonPool.createXSequence(-1);
		System.out.println(xSequence);

		assertEquals(xSequence.size(), 0);
	}

	@Test
	void twoNotComparableDungeons() {
		Block[][] blocksA = {
				{GROUND, AIR, AIR, AIR, AIR, AIR, AIR},
				{GROUND, AIR, AIR, GROUND, AIR, GROUND, AIR},
				{AIR, AIR, GROUND, GROUND, GROUND, GROUND, GROUND},
				{AIR, AIR, GROUND, GROUND, GROUND, GROUND, GROUND}
		};
		Dungeon dungeonA = new Dungeon(blocksA);

		Block[][] blocksB = {
				{GROUND, GROUND, AIR, AIR, GROUND, GROUND, AIR},
				{AIR, AIR, AIR, AIR, AIR, AIR, AIR},
				{GROUND, GROUND, GROUND, AIR, GROUND, GROUND, GROUND},
				{GROUND, GROUND, GROUND, GROUND, GROUND, GROUND, GROUND}
		};
		Dungeon dungeonB = new Dungeon(blocksB);

		assertThrows(InvalidDungeonException.class, () -> {
			DungeonPool dungeonPool = new DungeonPool(List.of(dungeonA, dungeonB));
			dungeonPool.createXSequence(5);
		});
	}

	@Test
	void twoNeighboursNotSame() {
		Block[][] blocksA = {
				{AIR, AIR, AIR, AIR, AIR, AIR, AIR},
				{GROUND, AIR, AIR, GROUND, AIR, GROUND, AIR},
				{AIR, AIR, GROUND, GROUND, GROUND, GROUND, GROUND},
				{AIR, AIR, GROUND, GROUND, GROUND, GROUND, GROUND}
		};
		Dungeon dungeonA = new Dungeon(blocksA);

		Block[][] blocksB = {
				{GROUND, GROUND, AIR, AIR, GROUND, GROUND, AIR},
				{AIR, AIR, AIR, AIR, AIR, AIR, AIR},
				{GROUND, GROUND, GROUND, AIR, GROUND, GROUND, GROUND},
				{GROUND, GROUND, GROUND, GROUND, GROUND, GROUND, GROUND}
		};
		Dungeon dungeonB = new Dungeon(blocksB);
		DungeonPool dungeonPool = new DungeonPool(List.of(dungeonA, dungeonB));
		List<Dungeon> xSequence = dungeonPool.createXSequence(5);
		System.out.println(xSequence);

		for (int i = 0; i < xSequence.size() - 1; i++) {
			assertNotEquals(xSequence.get(i), xSequence.get(i + 1));
		}
	}

	@Test
	void twoIncorrectDungeonSize() {
		Block[][] blocksA = {
				{AIR, AIR, AIR, AIR, AIR, AIR},
				{GROUND, AIR, AIR, GROUND, AIR, GROUND, AIR},
				{AIR, AIR, GROUND, GROUND, GROUND, GROUND, GROUND},
				{AIR, AIR, GROUND, GROUND, GROUND, GROUND, GROUND}
		};
		Dungeon dungeonA = new Dungeon(blocksA);

		Block[][] blocksB = {
				{GROUND, GROUND, AIR, AIR, GROUND, GROUND, AIR},
				{AIR, AIR, AIR, AIR, AIR, AIR, AIR},
				{GROUND, GROUND, GROUND, AIR, GROUND, GROUND, GROUND},
				{GROUND, GROUND, GROUND, GROUND, GROUND, GROUND, GROUND}
		};
		Dungeon dungeonB = new Dungeon(blocksB);

		assertThrows(InvalidDungeonException.class, () -> {
			DungeonPool dungeonPool = new DungeonPool(List.of(dungeonA, dungeonB));
			dungeonPool.createXSequence(5);
		});
	}
}
