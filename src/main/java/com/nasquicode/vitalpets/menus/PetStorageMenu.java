package com.nasquicode.vitalpets.menus;

import com.nasquicode.vitalcore.bukkit.utils.Color;
import com.nasquicode.vitalcore.bukkit.utils.CustomStack;
import com.nasquicode.vitalpets.Terminal;
import com.nasquicode.vitalpets.mappers.PlayerDataMapper;
import com.nasquicode.vitalpets.objects.Pet;
import com.nasquicode.vitalpets.objects.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class PetStorageMenu {
    public static void open(Player player) {
        PlayerData playerData = PlayerDataMapper.getMapper().get(player.getName());
        Inventory inv = Bukkit.createInventory(player, Terminal.menuFile.getInt("pet_storage.size"), Color.string(Terminal.menuFile.getString("pet_storage.name")));

        List<Integer> pet_slots = Terminal.menuFile.getIntegerList("pet_storage.pet_slots");
        int pet_slot = 0;
        if(PlayerDataMapper.getMapper().get(player.getName()).getPets().size() > 0) {
            for(Pet pet : PlayerDataMapper.getMapper().get(player.getName()).getPets()) {
                if(pet_slot == inv.getSize()) break;
                ItemStack stack = pet.getPetType().getDisplay_stack();
                ItemMeta meta = stack.getItemMeta();
                meta.setDisplayName(pet.getPetType().getName());

                if(playerData.getEquippedPetsStringList().contains(pet.getPetType().getKey())) {
                    List<String> lore = Color.list(Terminal.menuFile.getStringList("pet_storage.items.equipped_pet_lore"));
                    for(int i=0;i<lore.size();i++) {
                        lore.set(i,lore.get(i).replace("{level}",String.valueOf(pet.getLevel())).replace("{block_progress}",String.valueOf(pet.getBlocks_progress())).replace("{block_goal}",String.valueOf(pet.getPetType().getLevels().get(pet.getLevel()).getBlocks())));
                    }
                    meta.setLore(lore);
                }else {
                    List<String> lore = Color.list(Terminal.menuFile.getStringList("pet_storage.items.equip_pet_lore"));
                    for(int i=0;i<lore.size();i++) {
                        lore.set(i,lore.get(i).replace("{level}",String.valueOf(pet.getLevel())).replace("{block_progress}",String.valueOf(pet.getBlocks_progress())).replace("{block_goal}",String.valueOf(pet.getBlocks_progress())));
                    }
                    meta.setLore(lore);
                }

                stack.setItemMeta(meta);
                for(int i=0;i<(inv.getSize()-1);i++) {
                    if(pet_slots.contains(pet_slot)) {
                        inv.setItem(pet_slot, stack);
                        pet_slot++;
                        break;
                    }
                    pet_slot++;
                }
            }
        }else {
            ItemStack stack = CustomStack.get(Terminal.menuFile.getString("pet_storage.items.no_pet_available.material"));
            ItemMeta meta = stack.getItemMeta();
            meta.setDisplayName(Color.string(Terminal.menuFile.getString("pet_storage.items.no_pet_available.name")));
            meta.setLore(Color.list(Terminal.menuFile.getStringList("pet_storage.items.no_pet_available.lore")));
            stack.setItemMeta(meta);
            inv.setItem(Terminal.menuFile.getInt("pet_storage.items.no_pet_available.slot"), stack);
        }

        player.openInventory(inv);
    }
}