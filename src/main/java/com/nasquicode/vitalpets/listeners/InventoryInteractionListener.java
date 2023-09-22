package com.nasquicode.vitalpets.listeners;

import com.nasquicode.vitalcore.bukkit.utils.Color;
import com.nasquicode.vitalcore.bukkit.utils.Console;
import com.nasquicode.vitalpets.Terminal;
import com.nasquicode.vitalpets.mappers.PlayerDataMapper;
import com.nasquicode.vitalpets.menus.PetStorageMenu;
import com.nasquicode.vitalpets.misc.Constants;
import com.nasquicode.vitalpets.objects.Pet;
import com.nasquicode.vitalpets.objects.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
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
            assert !stack.getItemMeta().getDisplayName().equalsIgnoreCase(Color.string(Terminal.menuFile.getString("pet_storage.items.no_pet_available.name")));
            PlayerData playerData = PlayerDataMapper.getMapper().get(player.getName());
            Pet pet = playerData.matchPetDisplayName(stack.getItemMeta().getDisplayName());

            assert pet != null;
            // USING PET
            if(playerData.getActive_pets().containsValue(pet.getPetType().getKey())) {
                Console.log("InventoryInteractionListener.java Debug 1");
                if(!e.isLeftClick()) return;
                Console.log("InventoryInteractionListener.java Debug 2");
                playerData.unequipePet(pet);
                player.sendMessage(Color.string(Terminal.messageFile.getString("pet_unequipped")));
                PetStorageMenu.open(player);
                return;
            }

            // NOT USING PET
            if(e.isLeftClick()) {
                if(playerData.equipPet(1, pet)) {
                    player.sendMessage(Color.string(Terminal.messageFile.getString("pet_equipped")));
                    return;
                }
                player.sendMessage(Color.string(Terminal.messageFile.getString("insufficient_slots")));
                PetStorageMenu.open(player);
                return;
            }
            if(e.isRightClick()) {
                if(playerData.equipPet(2, pet)) {
                    player.sendMessage(Color.string(Terminal.messageFile.getString("pet_equipped")));
                    return;
                }
                player.sendMessage(Color.string(Terminal.messageFile.getString("insufficient_slots")));
                PetStorageMenu.open(player);
                return;
            }
            if(e.getClick().equals(ClickType.DROP)) {
                if(playerData.equipPet(3, pet)) {
                    player.sendMessage(Color.string(Terminal.messageFile.getString("pet_equipped")));
                    PetStorageMenu.open(player);
                    return;
                }
                player.sendMessage(Color.string(Terminal.messageFile.getString("insufficient_slots")));
                player.closeInventory();
                return;
            }
            return;
        }
    }
    @EventHandler
    public void onInventoryDrag(InventoryDragEvent e) {
        String inv_name = e.getView().getTitle();
        if(Constants.inventoryNames.contains(inv_name)) e.setCancelled(true);
    }
}