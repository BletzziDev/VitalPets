package com.nasquicode.vitalpets.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@AllArgsConstructor @Getter
public class Candy {
    private String key;
    private ItemStack stack;
    private String name;
    private List<String> lore;
    private int levels;
}