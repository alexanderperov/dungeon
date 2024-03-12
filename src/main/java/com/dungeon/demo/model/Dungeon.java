package com.dungeon.demo.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Dungeon {
    private final Block[][] area;

    public Dungeon(Block[][] area) {
        this.area = area;
    }
}