package com.amazurok.swingy.model.artifacts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;

@Getter
@Setter
public class Armor extends Artifact{
    public Armor (int power) {
        this.power = power;
        this.type = this.getClass().getSimpleName();
    }
}
