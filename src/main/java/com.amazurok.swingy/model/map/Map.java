package com.amazurok.swingy.model.map;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;

@Getter
@Setter
public class Map {

    @Min(value = 0, message = "Map size cannot be less than 0")
    private int size;

    public Map(int level) {
        size = (level - 1) * 5 + 10 - (level % 2);
    }
}
