package com.dungeon.demo;

import com.dungeon.demo.exception.InvalidDungeonException;
import com.dungeon.demo.model.Block;
import com.dungeon.demo.model.Dungeon;

import java.util.*;

import static com.dungeon.demo.validator.DungeonPoolValidator.validateAllDungeonsHaveEntryAndExit;
import static com.dungeon.demo.validator.DungeonPoolValidator.validateEveryDungeonHasComparable;
import static com.dungeon.demo.model.Block.AIR;

public class DungeonPool {
    private final Map<Integer, List<Dungeon>> entriesToDungeonMap = new HashMap<>();
    private final Map<Integer, List<Dungeon>> exitsToDungeonMap = new HashMap<>();

    public DungeonPool(List<Dungeon> dungeons) {
        createDungeonsWithEntriesMap(dungeons);
        validateAllDungeonsHaveEntryAndExit(dungeons);
        validateEveryDungeonHasComparable(entriesToDungeonMap, exitsToDungeonMap);
    }

    public List<Dungeon> createXSequence(int length) {
        List<Dungeon> result = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            Dungeon current = result.isEmpty() ? null : result.get(i - 1);
            result.add(findNext(current));
        }
        return result;
    }

    private void createDungeonsWithEntriesMap(List<Dungeon> dungeons) {
        for (Dungeon dungeon : dungeons) {
            if (dungeon.getArea() == null || dungeon.getArea().length != 4) {
                throw new InvalidDungeonException("Row count not equals 4");
            }
            if (Arrays.stream(dungeon.getArea()).anyMatch(r -> r.length != 7)) {
                throw new InvalidDungeonException("Row length not equals 7");
            }
            populateComparableDungeons(entriesToDungeonMap, dungeon, 0);
            populateComparableDungeons(exitsToDungeonMap, dungeon, 6);
        }
    }

    private void populateComparableDungeons(Map<Integer, List<Dungeon>> dungeonMap, Dungeon dungeon, int columnIndex) {
        for (int i = 0; i < dungeon.getArea().length; i++) {
            Block block = dungeon.getArea()[i][columnIndex];
            if (AIR.equals(block)) {
                if (!dungeonMap.containsKey(i)) {
                    dungeonMap.put(i, new ArrayList<>());
                }
                dungeonMap.get(i).add(dungeon);
            }
        }
    }

    private Dungeon findNext(Dungeon current) {
        if (current == null) {
            return entriesToDungeonMap.values().stream().findFirst().map(list -> list.get(0)).orElse(null);
        }

        List<Integer> exits = new ArrayList<>();
        for (int i = 0; i < current.getArea().length; i++) {
            Block block = current.getArea()[i][6];
            if (AIR.equals(block)) {
                exits.add(i);
            }
        }
        return entriesToDungeonMap.entrySet().stream()
                .filter(e -> exits.contains(e.getKey()) && !e.getValue().contains(current))
                .map(Map.Entry::getValue).flatMap(Collection::stream).findAny().orElse(null);
    }
}
