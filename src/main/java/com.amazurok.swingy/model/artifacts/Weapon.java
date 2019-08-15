package com.amazurok.swingy.model.artifacts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;

@Getter
@Setter
@NoArgsConstructor
public class Weapon extends Artifact {

    public Weapon (int power) {
        this.power = power;
        this.type = this.getClass().getSimpleName();
    }
}
