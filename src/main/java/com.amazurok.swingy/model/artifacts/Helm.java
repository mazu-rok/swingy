package com.amazurok.swingy.model.artifacts;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class Helm extends Artifact{

    public Helm (int power) {
        this.power = power;
        this.type = this.getClass().getSimpleName();
    }
}
