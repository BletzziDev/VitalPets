package com.nasquicode.vitalpets.objects;

import com.nasquicode.vitalpets.enums.PowerType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public class PetPower {
    private String key;
    private PowerType type;
    private Double percentage;
}