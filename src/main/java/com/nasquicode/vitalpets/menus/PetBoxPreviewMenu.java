package com.nasquicode.vitalpets.menus;

import com.nasquicode.vitalcore.bukkit.utils.Color;
import com.nasquicode.vitalpets.Terminal;
import com.nasquicode.vitalpets.mappers.PetTypeMapper;
import com.nasquicode.vitalpets.objects.PetBox;
import com.nasquicode.vitalpets.objects.PetType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class PetBoxPreviewMenu {
    public static void open(Player player, PetBox petBox) {
        Inventory inv = Bukkit.createInventory(player, Terminal.menuFile.getInt("pet_box_preview.size"), Color.string(Terminal.menuFile.getString("pet_box_preview.name").replace("{box_name}",petBox.getName())));

        List<Integer> pet_slots = Terminal.menuFile.getIntegerList("pet_box_preview.pet_slots");
        int pet_slot = 0;
        for(PetType petType : petBox.getRewards()) {
            if(pet_slot == inv.getSize()) break;

            ItemStack stack = petType.getDisplay_stack();
            ItemMeta meta = stack.getItemMeta();
            meta.setDisplayName(petType.getDisplay_name());
            meta.setLore(petType.getDisplay_lore());
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

        player.openInventory(inv);
    }
}
