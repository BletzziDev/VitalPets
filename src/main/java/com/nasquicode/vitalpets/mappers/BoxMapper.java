package com.nasquicode.vitalpets.mappers;

import com.nasquicode.vitalcore.bukkit.utils.Color;
import com.nasquicode.vitalcore.bukkit.utils.CustomStack;
import com.nasquicode.vitalpets.Terminal;
import com.nasquicode.vitalpets.objects.PetBox;
import com.nasquicode.vitalpets.objects.PetType;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BoxMapper {
    @Getter
    private static HashMap<String, PetBox> mapper = new HashMap<>();
    public static void register() {
        mapper.clear();
        for(String key : Terminal.boxFile.getConfigurationSection("boxes").getKeys(false)) {
            List<PetType> rewards = new ArrayList<>();
            for(String petName : Terminal.boxFile.getStringList(String.format("boxes.%s.pets", key))) {
                if(PetTypeMapper.getMapper().containsKey(petName)) rewards.add(PetTypeMapper.getMapper().get(petName));
            }
            PetBox box = new PetBox(
                    key,
                    CustomStack.get(Terminal.boxFile.getString(String.format("boxes.%s.material", key))),
                    Color.string(Terminal.boxFile.getString(String.format("boxes.%s.name", key))),
                    Color.list(Terminal.boxFile.getStringList(String.format("boxes.%s.lore", key))),
                    rewards
            );
            mapper.put(key, box);
        }
    }
}
