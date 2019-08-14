package com.amazurok.swingy.model.artifacts;

import lombok.Getter;

import javax.validation.constraints.Min;

@Getter
public class Artifact {
    protected String type;

    @Min(value = 1)
    protected int power;
}
