package com.nasquicode.vitalpets.menus;

import com.nasquicode.vitalcore.bukkit.utils.Color;
import com.nasquicode.vitalcore.bukkit.utils.CustomFileConfiguration;
import com.nasquicode.vitalcore.bukkit.utils.CustomStack;
import com.nasquicode.vitalcore.bukkit.utils.NumberFormat;
import com.nasquicode.vitalpets.Terminal;
import com.nasquicode.vitalpets.objects.Pet;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class PetUpgradeMenu {
    public static void open(Player player, Pet pet) {
        CustomFileConfiguration menuFile = Terminal.menuFile;
        Inventory inv = Bukkit.createInventory(player, menuFile.getInt("pet_upgrade_menu.size"), Color.string(menuFile.getString("pet_upgrade_menu.name")));

        List<String> menuItems = Arrays.asList("pet","evolute");
        for(String key : menuItems) {
            ItemStack stack = CustomStack.get(menuFile.getString(String.format("pet_upgrade_menu.items.%s.material", key)).replace("{pet_skull}",Terminal.petFile.getString(String.format("pets.%s.display.material", pet.getPetType().getKey()))));
            ItemMeta meta = stack.getItemMeta();
            meta.setDisplayName(Color.string(menuFile.getString(String.format("pet_upgrade_menu.items.%s.name", key)).replace("{pet_name}",pet.getPetType().getDisplay_name())));
            List<String> lore = Color.list(menuFile.getStringList(String.format("pet_upgrade_menu.items.%s.lore", key)));
            for(int i=0;i<lore.size();i++) {
                lore.set(i, lore.get(i).replace("{level}",String.valueOf(pet.getLevel())).replace("{price}", NumberFormat.format(pet.getPetType().getLevels().get(pet.getLevel()+1).getPrice())));
            }
            meta.setLore(lore);
            stack.setItemMeta(meta);
            inv.setItem(menuFile.getInt(String.format("pet_upgrade_menu.items.%s.slot", key)) ,stack);
        }

        player.openInventory(inv);
    }
}
