package com.nasquicode.vitalpets.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

@AllArgsConstructor @Getter
public class PetType {
    private String key;
    private String custom_model_name;
    private String name;
    private ItemStack display_stack;
    private String display_name;
    private List<String> display_lore;
    private Rarity rarity;
    private List<PetPower> powers;
    private HashMap<Integer, PetLevel> levels;
}