package com.nasquicode.vitalpets.listeners;

import com.nasquicode.vitalcore.bukkit.utils.Color;
import com.nasquicode.vitalpets.Terminal;
import com.nasquicode.vitalpets.api.EdPrisonAPI;
import com.nasquicode.vitalpets.mappers.CandyMapper;
import com.nasquicode.vitalpets.mappers.PlayerDataMapper;
import com.nasquicode.vitalpets.mappers.StoreCurrencyMapper;
import com.nasquicode.vitalpets.mappers.StoreMapper;
import com.nasquicode.vitalpets.menus.PetStorageMenu;
import com.nasquicode.vitalpets.menus.PetUpgradeMenu;
import com.nasquicode.vitalpets.misc.Constants;
import com.nasquicode.vitalpets.objects.*;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
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

            if(pet == null) return;

            // UPGRADE
            if(e.isShiftClick()) {
                if(pet.getPetType().getLevels().containsKey(pet.getLevel()+1)) {
                    PetUpgradeMenu.open(player, pet);
                    return;
                }
                player.closeInventory();
                player.sendMessage(Color.string(Terminal.messageFile.getString("pet_max_level")));
                return;
            }

            // USING PET
            if(playerData.getActive_pets().containsValue(pet.getPetType().getKey())) {
                if(!e.isLeftClick()) return;
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
                player.closeInventory();
                PetStorageMenu.open(player);
                return;
            }
            if(e.isRightClick()) {
                if(playerData.equipPet(2, pet)) {
                    player.sendMessage(Color.string(Terminal.messageFile.getString("pet_equipped")));
                    return;
                }
                player.sendMessage(Color.string(Terminal.messageFile.getString("insufficient_slots")));
                player.closeInventory();
                PetStorageMenu.open(player);
                return;
            }
            if(e.getClick().equals(ClickType.DROP)) {
                if(playerData.equipPet(3, pet)) {
                    player.sendMessage(Color.string(Terminal.messageFile.getString("pet_equipped")));
                    player.closeInventory();
                    PetStorageMenu.open(player);
                    return;
                }
                player.sendMessage(Color.string(Terminal.messageFile.getString("insufficient_slots")));
                player.closeInventory();
                return;
            }
            return;
        }

        if(inv_name.equalsIgnoreCase(Color.string(Terminal.menuFile.getString("pet_upgrade_menu.name")))) {
            if(stack.getItemMeta().getDisplayName().equalsIgnoreCase(Color.string(Terminal.menuFile.getString("pet_upgrade_menu.items.evolute.name")))) {
                try {
                    double playerBalance = EdPrisonAPI.getApi().getApi().getEconomyApi().getEco(player.getUniqueId(), Terminal.config.getString("pet_upgrade_edp_currency"));
                    Pet pet = PlayerDataMapper.getMapper().get(player.getName()).matchPetDisplayName(Color.string(player.getOpenInventory().getItem(Terminal.menuFile.getInt("pet_upgrade_menu.items.pet.slot")).getItemMeta().getDisplayName()));
                    if(pet == null) return;
                    double price = pet.getPetType().getLevels().get(pet.getLevel()+1).getPrice();
                    if(playerBalance >= price) {
                        EdPrisonAPI.getApi().getApi().getEconomyApi().setEco(player.getUniqueId(), Terminal.config.getString("pet_upgrade_edp_currency"), playerBalance-price);
                        pet.setLevel(pet.getLevel()+1);
                        player.closeInventory();
                        player.sendMessage(Color.string(Terminal.messageFile.getString("successfully_upgrade")));
                        return;
                    }
                    player.closeInventory();
                    player.sendMessage(Color.string(Terminal.messageFile.getString("insufficient_funds")));
                    return;
                }catch(Exception x) {
                    player.closeInventory();
                    throw new RuntimeException(x);
                    //return;
                }
            }
            return;
        }

        if(inv_name.equalsIgnoreCase(Color.string(Terminal.menuFile.getString("candy_use_menu.name")))) {
            ItemStack playerHand = player.getInventory().getItemInMainHand();
            NBTItem nbtItem = new NBTItem(playerHand);
            if(!nbtItem.hasCustomNbtData() || !CandyMapper.getMapper().containsKey(nbtItem.getString("vitalpets-candy"))) {
                player.closeInventory();
                return;
            }

            PlayerData playerData = PlayerDataMapper.getMapper().get(player.getName());
            Candy candy = CandyMapper.getMapper().get(nbtItem.getString("vitalpets-candy"));
            Pet pet = playerData.matchPetDisplayName(stack.getItemMeta().getDisplayName());
            if(pet == null) {
                player.closeInventory();
                return;
            }

            int level = pet.getLevel()+candy.getLevels();
            int up_level = 0;
            for(int i=0;i<level;i++) {
                if(pet.getPetType().getLevels().containsKey(level)) up_level++;
            }
            if(up_level < 1) {
                // MAX LEVEL
                player.closeInventory();
                player.sendMessage(Color.string(Terminal.messageFile.getString("pet_max_level")));
                return;
            }
            pet.setBlocks_progress(0);
            pet.setLevel(up_level);
            player.closeInventory();
            player.sendMessage(Color.string(Terminal.messageFile.getString("successfully_upgrade")));

            if(playerHand.getAmount() > 1) {
                player.getInventory().getItemInMainHand().setAmount(playerHand.getAmount()-1);
            }else {
                player.getInventory().remove(playerHand);
            }

            return;
        }

        for(Store store : StoreMapper.getMapper().values()) {
            if(inv_name.equalsIgnoreCase(store.getMenuTitle())) {
                for(StoreItem item : store.getItems()) {
                    if(stack.getItemMeta().getDisplayName().equalsIgnoreCase(item.getName())) {
                        for(String currency : item.getPrices().keySet()) {
                            double balance = StoreCurrencyMapper.getPlayerBalance(player, currency);
                            if(balance >= item.getPrices().get(currency)) {
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), StoreCurrencyMapper.getMapper().get(currency).getSetCommand().replace("{player}",player.getName()).replace("{amount}",String.valueOf(balance-item.getPrices().get(currency))));
                                for(String command : item.getCommands()) {
                                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("{player}",player.getName()).replace("{amount}",String.valueOf(balance)));
                                }
                                player.closeInventory();
                                return;
                            }
                            player.closeInventory();
                            player.sendMessage(Color.string(Terminal.messageFile.getString("insufficient_funds")));
                            return;
                        }
                    }
                }
                return;
            }
        }
    }
    @EventHandler
    public void onInventoryDrag(InventoryDragEvent e) {
        String inv_name = e.getView().getTitle();
        if(Constants.inventoryNames.contains(inv_name)) e.setCancelled(true);
    }
}