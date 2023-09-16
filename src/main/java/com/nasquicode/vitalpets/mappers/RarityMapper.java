package com.nasquicode.vitalpets.mappers;

import com.nasquicode.vitalcore.bukkit.utils.Color;
import com.nasquicode.vitalpets.Terminal;
import com.nasquicode.vitalpets.objects.Rarity;
import lombok.Getter;

import java.util.HashMap;

public class RarityMapper {
    @Getter
    private static HashMap<String, Rarity> mapper = new HashMap<>();
    public static void register() {
        mapper.clear();
        for(String key : Terminal.rarityFile.getConfigurationSection("rarities").getKeys(false)) {
            Rarity rarity = new Rarity(
                    key,
                    Color.string(Terminal.rarityFile.getString(String.format("rarities.%s.name", key)))
            );
            mapper.put(key, rarity);
        }
    }
}