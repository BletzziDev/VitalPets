package com.nasquicode.vitalpets.menus;

import com.nasquicode.vitalpets.objects.Store;
import com.nasquicode.vitalpets.objects.StoreItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class StoreMenu {
    public static void open(Player player, Store store) {
        Inventory inv = Bukkit.createInventory(player, store.getMenuSize(), store.getMenuTitle());

        for(StoreItem storeItem : store.getItems()) {
            ItemStack stack = storeItem.getStack();
            ItemMeta meta = stack.getItemMeta();
            meta.setDisplayName(storeItem.getName());
            meta.setLore(storeItem.getLore());
            stack.setItemMeta(meta);
            inv.setItem(storeItem.getSlot(), stack);
        }

        player.openInventory(inv);
    }
}
