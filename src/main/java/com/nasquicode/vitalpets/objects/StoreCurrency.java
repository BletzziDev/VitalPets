package com.nasquicode.vitalpets.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public class StoreCurrency {
    private String key;
    private String name;
    private String provider;
    private String setCommand;
}