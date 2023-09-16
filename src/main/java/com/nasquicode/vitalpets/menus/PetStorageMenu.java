package com.nasquicode.vitalpets.menus;

import com.nasquicode.vitalcore.bukkit.utils.Color;
import com.nasquicode.vitalpets.Terminal;
import com.nasquicode.vitalpets.mappers.PlayerDataMapper;
import com.nasquicode.vitalpets.objects.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PetStorageMenu {
    public static void open(Player player) {
        PlayerData playerData = PlayerDataMapper.getMapper().get(player.getName());
        Inventory inv = Bukkit.createInventory(player, Terminal.menuFile.getInt("pet_storage.size"), Color.string(Terminal.menuFile.getString("pet_storage.name")));

        for(int slot : Terminal.menuFile.getIntegerList("pet_storage.pet_slots")) {
            inv.setItem(slot, new ItemStack(Material.BLACK_STAINED_GLASS));
        }

        player.openInventory(inv);
    }
}