package com.nasquicode.vitalpets.menus;

import com.nasquicode.vitalcore.bukkit.utils.Color;
import com.nasquicode.vitalcore.bukkit.utils.CustomFileConfiguration;
import com.nasquicode.vitalcore.bukkit.utils.CustomStack;
import com.nasquicode.vitalpets.Terminal;
import com.nasquicode.vitalpets.mappers.PlayerDataMapper;
import com.nasquicode.vitalpets.objects.Candy;
import com.nasquicode.vitalpets.objects.Pet;
import com.nasquicode.vitalpets.objects.PlayerData;
import com.nasquicode.vitalpets.utils.Console;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class CandyUseMenu {
    public static void open(Player player) {
        CustomFileConfiguration menuFile = Terminal.menuFile;
        PlayerData playerData = PlayerDataMapper.getMapper().get(player.getName());
        Inventory inv = Bukkit.createInventory(player,  menuFile.getInt("candy_use_menu.size"), menuFile.getString("candy_use_menu.name"));

        List<Integer> pet_slots = menuFile.getIntegerList("candy_use_menu.pet_slots");
        int pet_slot = 0;
        if(PlayerDataMapper.getMapper().get(player.getName()).getPets().size() > 0) {
            for(Pet pet : PlayerDataMapper.getMapper().get(player.getName()).getPets()) {
                if(pet_slot == inv.getSize()) break;
                ItemStack stack = pet.getPetType().getDisplay_stack();
                ItemMeta meta = stack.getItemMeta();
                meta.setDisplayName(pet.getPetType().getName());
                meta.setLore(Color.list(menuFile.getStringList("candy_use_menu.upgrade_lore")));

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
        }

        player.openInventory(inv);
    }
}