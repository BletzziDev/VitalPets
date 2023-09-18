package com.nasquicode.vitalpets.mappers;

import com.nasquicode.vitalcore.bukkit.utils.Color;
import com.nasquicode.vitalcore.bukkit.utils.CustomStack;
import com.nasquicode.vitalpets.Terminal;
import com.nasquicode.vitalpets.objects.Candy;
import de.tr7zw.nbtapi.NBTItem;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;

public class CandyMapper {
    @Getter
    private static HashMap<String, Candy> mapper = new HashMap<>();
    public static void register() {
        mapper.clear();
        for(String key : Terminal.candyFile.getConfigurationSection("candies").getKeys(false)) {
            Candy candy = new Candy(
                    key,
                    CustomStack.get(Terminal.candyFile.getString(String.format("candies.%s.material", key))),
                    Color.string(Terminal.candyFile.getString(String.format("candies.%s.name", key))),
                    Color.list(Terminal.candyFile.getStringList(String.format("candies.%s.lore", key))),
                    Terminal.candyFile.getInt(String.format("candies.%s.levels", key))
            );
            ItemStack stack = candy.getStack();
            ItemMeta meta = stack.getItemMeta();
            meta.setDisplayName(candy.getName());
            meta.setLore(candy.getLore());
            stack.setItemMeta(meta);
            NBTItem nbtItem = new NBTItem(stack);
            nbtItem.setString("vitalpets-candy",candy.getKey());
            candy.setStack(nbtItem.getItem());
            mapper.put(key, candy);
        }
    }
}