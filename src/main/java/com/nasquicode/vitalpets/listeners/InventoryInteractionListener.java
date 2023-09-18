package com.nasquicode.vitalpets.listeners;

import com.nasquicode.vitalcore.bukkit.utils.Color;
import com.nasquicode.vitalpets.Terminal;
import com.nasquicode.vitalpets.menus.PetStorageMenu;
import com.nasquicode.vitalpets.misc.Constants;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryInteractionListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        String inv_name = e.getView().getTitle();
        ItemStack stack = e.getCurrentItem();
        Player player = (Player) e.getWhoClicked();

        if(!Constants.inventoryNames.contains(inv_name)) return;
        e.setCancelled(true);

        if(stack == null || stack.getAmount() < 0 || !stack.hasItemMeta()) return;

        if(inv_name.equalsIgnoreCase(Color.string(Terminal.menuFile.getString("main.name")))) {
            if(stack.getItemMeta().getDisplayName().equalsIgnoreCase(Color.string(Terminal.menuFile.getString("main.items.pet_storage.name")))) {
                PetStorageMenu.open(player);
            }
            return;
        }

        if(inv_name.equalsIgnoreCase(Color.string(Terminal.menuFile.getString("pet_storage.name")))) {
            return;
        }
    }
    @EventHandler
    public void onInventoryDrag(InventoryDragEvent e) {
        String inv_name = e.getView().getTitle();
        if(Constants.inventoryNames.contains(inv_name)) e.setCancelled(true);
    }
}