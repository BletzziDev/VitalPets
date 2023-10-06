package com.nasquicode.vitalpets.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor @Getter
public class Store {
    private String key;
    private String command;
    private int menuSize;
    private String menuTitle;
    private List<StoreItem> items;
}