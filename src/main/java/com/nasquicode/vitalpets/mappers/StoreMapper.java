package com.nasquicode.vitalpets.mappers;

import com.nasquicode.vitalcore.bukkit.utils.Color;
import com.nasquicode.vitalcore.bukkit.utils.Console;
import com.nasquicode.vitalcore.bukkit.utils.CustomStack;
import com.nasquicode.vitalpets.Terminal;
import com.nasquicode.vitalpets.objects.Store;
import com.nasquicode.vitalpets.objects.StoreItem;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StoreMapper {
    @Getter
    private static HashMap<String, Store> mapper = new HashMap<>();
    public static void register() {
        StoreCurrencyMapper.register();
        mapper.clear();
        for(String key : Terminal.storeFile.getConfigurationSection("stores").getKeys(false)) {
            List<StoreItem> items = new ArrayList<>();
            for(String itemKey : Terminal.storeFile.getConfigurationSection(String.format("stores.%s.items", key)).getKeys(false)) {
                List<String> priceStringList = Terminal.storeFile.getStringList(String.format("stores.%s.items.%s.prices", key, itemKey));
                HashMap<String, Double> prices = new HashMap<>();
                for(String priceString : priceStringList) {
                    try {
                        String[] priceStringSplit = priceString.split(",");
                        prices.put(priceStringSplit[0], Double.parseDouble(priceStringSplit[1]));
                    }catch(Exception ignored) {}
                }

                StoreItem storeItem = new StoreItem(
                        key,
                        itemKey,
                        Terminal.storeFile.getInt(String.format("stores.%s.items.%s.display_item.slot", key, itemKey)),
                        CustomStack.get(Terminal.storeFile.getString(String.format("stores.%s.items.%s.display_item.material", key, itemKey))),
                        Color.string(Terminal.storeFile.getString(String.format("stores.%s.items.%s.display_item.name", key, itemKey))),
                        Color.list(Terminal.storeFile.getStringList(String.format("stores.%s.items.%s.display_item.lore", key, itemKey))),
                        Terminal.storeFile.getStringList(String.format("stores.%s.items.%s.commands", key, itemKey)),
                        prices
                );
                        
                items.add(storeItem);
            }

            Store store = new Store(
                    key,
                    Terminal.storeFile.getString(String.format("stores.%s.command", key)),
                    Terminal.storeFile.getInt(String.format("stores.%s.menu.size", key)),
                    Color.string(Terminal.storeFile.getString(String.format("stores.%s.menu.name", key))),
                    items
            );
            mapper.put(key, store);
        }
    }
    public static Store getStoreByCommand(String command) {
        for(Store store : mapper.values()) {
            if(store.getCommand().equalsIgnoreCase(command)) return store;
        }
        return null;
    }
}
