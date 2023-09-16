package com.nasquicode.vitalpets.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@AllArgsConstructor @Getter
public class PetBox {
    private String key;
    private ItemStack stack;
    private String name;
    private List<String> lore;
    private List<PetType> rewards;
}