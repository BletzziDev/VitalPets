package com.nasquicode.vitalpets.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter
public class Candy {
    private String key;
    private ItemStack displayStack;
    @Setter
    private ItemStack stack;
    private String name;
    private List<String> lore;
    private int levels;

    public Candy(String key, ItemStack displayStack, String name, List<String> lore, int levels) {
        this.key = key;
        this.displayStack = displayStack;
        this.name = name;
        this.lore = lore;
        this.levels = levels;
    }
}