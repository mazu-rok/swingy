package com.amazurok.swingy.model.artifacts;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;

@Getter
@Setter
public class Helm {

    @Min(value = 1)
    private int power;
}
