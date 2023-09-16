package com.nasquicode.vitalpets.mappers;

import com.nasquicode.vitalcore.bukkit.utils.Color;
import com.nasquicode.vitalcore.bukkit.utils.CustomStack;
import com.nasquicode.vitalpets.Terminal;
import com.nasquicode.vitalpets.enums.PowerType;
import com.nasquicode.vitalpets.objects.PetLevel;
import com.nasquicode.vitalpets.objects.PetPower;
import com.nasquicode.vitalpets.objects.PetType;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PetTypeMapper {
    @Getter
    private static HashMap<String, PetType> mapper = new HashMap<>();
    public static void register() {
        mapper.clear();
        for(String key : Terminal.petFile.getConfigurationSection("pets").getKeys(false)) {
            List<PetPower> powers = new ArrayList<>();
            for(String power_name : Terminal.petFile.getStringList(String.format("pets.%s.powers", key))) {
                if(matchPower(power_name) != null) powers.add(
                        new PetPower(power_name, matchPower(power_name), Terminal.petFile.getDouble(String.format("pets.%s.powers.%s.percentage", key, power_name)))
                );
            }
            HashMap<Integer, PetLevel> levels = new HashMap<>();
            for(String level : Terminal.petFile.getConfigurationSection(String.format("pets.%s.levels", key)).getKeys(false)) {
                PetLevel petLevel = new PetLevel(
                        Integer.parseInt(level),
                        Terminal.petFile.getDouble(String.format("pets.%s.levels.%s.blocks", key, level)),
                        Terminal.petFile.getDouble(String.format("pets.%s.levels.%s.bonus", key, level))
                );
                levels.put(petLevel.getLevel(), petLevel);
            }
            PetType petType = new PetType(
                key,
                    Terminal.petFile.getString(String.format("pets.%s.custom_model", key)),
                    Color.string(Terminal.petFile.getString(String.format("pets.%s.name", key))),
                    CustomStack.get(Terminal.petFile.getString(String.format("pets.%s.display.material", key))),
                    Color.string(Terminal.petFile.getString(String.format("pets.%s.display.name", key))),
                    Color.list(Terminal.petFile.getStringList(String.format("pets.%s.display.lore", key))),
                    RarityMapper.getMapper().get(Terminal.petFile.getString(String.format("pets.%s.rarity", key))),
                    powers,
                    levels
            );
            mapper.put(key, petType);
        }
    }
    public static PowerType matchPower(String name) {
        switch (name) {
            case "VAULT":
                return PowerType.VAULT;
            case "EDP_CURRENCIES_TOKENS":
                return PowerType.EDP_CURRENCIES_TOKENS;
            case "EDP_CURRENCIES_CRYSTAL":
                return PowerType.EDP_CURRENCIES_CRYSTAL;
            case "EDP_CURRENCIES_GEMS":
                return PowerType.EDP_CURRENCIES_GEMS;
        }
        return null;
    }
}