package com.amazurok.swingy.model.artifacts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;

@Getter
@Setter
@AllArgsConstructor
public class Helm extends Artifact{

    public Helm (int power) {
        this.power = power;
        this.type = this.getClass().getSimpleName();
    }
}
