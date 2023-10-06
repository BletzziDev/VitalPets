package com.nasquicode.vitalpets.mappers;

import com.nasquicode.vitalcore.bukkit.utils.Color;
import com.nasquicode.vitalpets.Terminal;
import com.nasquicode.vitalpets.objects.StoreCurrency;
import lombok.Getter;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class StoreCurrencyMapper {
    @Getter
    private static HashMap<String, StoreCurrency> mapper = new HashMap<>();
    public static void register() {
        mapper.clear();
        for(String key : Terminal.storeFile.getConfigurationSection("currencies").getKeys(false)) {
            StoreCurrency currency = new StoreCurrency(
                key,
                    Color.string(Terminal.storeFile.getString(String.format("currencies.%s.name", key))),
                Terminal.storeFile.getString(String.format("currencies.%s.provider", key)),
                Terminal.storeFile.getString(String.format("currencies.%s.set_command", key))

            );
            mapper.put(key, currency);
        }
    }
    public static double getPlayerBalance(Player player, String currency) {
        try {
            return Double.parseDouble(PlaceholderAPI.setPlaceholders(player, mapper.get(currency).getProvider()));
        }catch(Exception x) {
            return 0;
        }
    }
}
