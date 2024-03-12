package com.dungeon.demo.validator;

import com.dungeon.demo.exception.InvalidDungeonException;
import com.dungeon.demo.model.Block;
import com.dungeon.demo.model.Dungeon;

import java.util.List;
import java.util.Map;

import static com.dungeon.demo.model.Block.AIR;

public class DungeonPoolValidator {
    public static void validateAllDungeonsHaveEntryAndExit(List<Dungeon> dungeons) {
        boolean containsEntry = false;
        boolean containsExit = false;
        Block block;
        for (Dungeon dungeon : dungeons) {
            for (int i = 0; i < dungeon.getArea().length; i++) {
                block = dungeon.getArea()[i][0];
                if (AIR.equals(block)) {
                    containsEntry = true;
                }
                block = dungeon.getArea()[i][6];
                if (AIR.equals(block)) {
                    containsExit = true;
                }
            }

            if (!containsEntry) {
                throw new InvalidDungeonException("Dungeon doesn't contain entry");
            }
            if (!containsExit) {
                throw new InvalidDungeonException("Dungeon doesn't contain exit");
            }
        }
    }

    public static void validateEveryDungeonHasComparable(Map<Integer, List<Dungeon>> entriesToDungeonMap, Map<Integer, List<Dungeon>> exitsToDungeonMap) {
        if (entriesToDungeonMap.size() == 4) {
            //there are all possible entries
            return;
        }

        if (!entriesToDungeonMap.keySet().containsAll(exitsToDungeonMap.keySet())) {
            throw new InvalidDungeonException("There are not comparable dungeons");
        }
    }
}
