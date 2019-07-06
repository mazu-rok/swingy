package com.amazurok.swingy.model.map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
@AllArgsConstructor
public class Coordinates {
    @Min(value = 0, message = "x coordinate cannot be less than zero")
    @Max(value = 2147483647, message = "x coordinate cannot be greater than MAX_INT value.")
    private int x;


    @Min(value = 0, message = "y coordinate cannot be less than zero")
    @Max(value = 2147483647, message = "y coordinate cannot be greater than MAX_INT value.")
    private int y;
}
