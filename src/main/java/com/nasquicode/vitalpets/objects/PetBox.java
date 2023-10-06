package com.nasquicode.vitalpets.objects;

import de.tr7zw.nbtapi.NBTItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

@AllArgsConstructor @Getter
public class PetBox {
    private String key;
    private ItemStack stack;
    private String name;
    private List<String> lore;
    private List<PetType> rewards;

    public void give(Player player, int amount) {
        ItemStack stack = this.stack;
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(this.name);
        meta.setLore(this.lore);
        stack.setItemMeta(meta);
        stack.setAmount(amount);

        NBTItem nbtItem = new NBTItem(stack);
        nbtItem.setString("vitalpets-box", this.key);

        player.getInventory().addItem(nbtItem.getItem());
    }
}