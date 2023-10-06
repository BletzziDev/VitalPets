package com.nasquicode.vitalpets.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

@AllArgsConstructor @Getter
public class StoreItem {
    private String store;
    private String key;
    private int slot;
    private ItemStack stack;
    private String name;
    private List<String> lore;
    private List<String> commands;
    private HashMap<String, Double> prices;
}