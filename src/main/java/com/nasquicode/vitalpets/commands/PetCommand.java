package com.nasquicode.vitalpets.commands;

import com.nasquicode.vitalcore.bukkit.utils.Color;
import com.nasquicode.vitalpets.Terminal;
import com.nasquicode.vitalpets.mappers.BoxMapper;
import com.nasquicode.vitalpets.mappers.CandyMapper;
import com.nasquicode.vitalpets.mappers.PetTypeMapper;
import com.nasquicode.vitalpets.mappers.PlayerDataMapper;
import com.nasquicode.vitalpets.menus.MainMenu;
import com.nasquicode.vitalpets.objects.Candy;
import com.nasquicode.vitalpets.objects.Pet;
import com.nasquicode.vitalpets.objects.PetBox;
import com.nasquicode.vitalpets.objects.PetType;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PetCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 0) {
            if(!(sender instanceof Player)) {
                // Console Commands
                for(String line : Color.list(Terminal.messageFile.getStringList("pet_admin_help")))  {
                    sender.sendMessage(line);
                }
                return false;
            }

            // Opens player pet inventory menu
            Player player = (Player) sender;
            MainMenu.open(player);
            return true;
        }

        if(args[0].equalsIgnoreCase("givepet")) {
            if(args.length != 3) {
                sender.sendMessage(Color.string(Terminal.messageFile.getString("givepet_command_usage")));
                return false;
            }

            Player player = Bukkit.getPlayer(args[1]);
            if(player == null) {
                sender.sendMessage(Color.string(Terminal.messageFile.getString("player_offline")));
                return false;
            }

            PetType petType = PetTypeMapper.getMapper().get(args[2]);
            if(petType == null) {
                sender.sendMessage(Color.string(Terminal.messageFile.getString("pet_not_exists")));
                return false;
            }

            if(PlayerDataMapper.getMapper().get(player.getName()).containsPet(args[2])) {
                sender.sendMessage(Color.string(Terminal.messageFile.getString("player_already_has_pet")));
                return false;
            }

            PlayerDataMapper.getMapper().get(player.getName()).getPets().add(new Pet(
                    petType, player, 1, 0
            ));
            sender.sendMessage(Color.string(Terminal.messageFile.getString("pet_give_success")));
            return true;
        }

        if(args[0].equalsIgnoreCase("removepet")) {
            if(args.length != 3) {
                sender.sendMessage(Color.string(Terminal.messageFile.getString("removepet_command_usage")));
                return false;
            }

            Player player = Bukkit.getPlayer(args[1]);
            if(player == null) {
                sender.sendMessage(Color.string(Terminal.messageFile.getString("player_offline")));
                return false;
            }

            if(PetTypeMapper.getMapper().get(args[2]) == null) {
                sender.sendMessage(Color.string(Terminal.messageFile.getString("pet_not_exists")));
                return false;
            }

            if(!PlayerDataMapper.getMapper().get(player.getName()).containsPet(args[2])) {
                sender.sendMessage(Color.string(Terminal.messageFile.getString("player_does_not_have_pet")));
                return false;
            }

            PlayerDataMapper.getMapper().get(player.getName()).removePet(args[2]);
            sender.sendMessage(Color.string(Terminal.messageFile.getString("pet_remove_success")));
            return true;
        }

        if(args[0].equalsIgnoreCase("givecandy")) {
            if(args.length != 4) {
                sender.sendMessage(Color.string(Terminal.messageFile.getString("givecandy_command_usage")));
                return false;
            }

            Player player = Bukkit.getPlayer(args[1]);
            if(player == null) {
                sender.sendMessage(Color.string(Terminal.messageFile.getString("player_offline")));
                return false;
            }

            Candy candy = CandyMapper.getMapper().get(args[2]);
            if(candy == null) {
                sender.sendMessage(Color.string(Terminal.messageFile.getString("candy_not_exists")));
                return false;
            }

            int amount = 0;
            try {
                amount = Integer.valueOf(args[3]);
            }catch(Exception x) {
                sender.sendMessage(Color.string(Terminal.messageFile.getString("invalid_number")));
                return false;
            }
            if(amount <= 0 || amount > 64) {
                sender.sendMessage(Color.string(Terminal.messageFile.getString("incorrect_amount")));
                return false;
            }

            ItemStack stack = candy.getStack();
            stack.setAmount(amount);
            NBTItem nbtItem = new NBTItem(stack);
            nbtItem.setString("vitalpets-candy", candy.getKey());
            player.getInventory().addItem(nbtItem.getItem());
            Bukkit.getConsoleSender().sendMessage(Color.string(Terminal.messageFile.getString("candy_give_success")));
        }

        if(args[0].equalsIgnoreCase("givebox")) {
            if(!sender.hasPermission("vitalpets.givebox")) {
                sender.sendMessage(Color.string(Terminal.messageFile.getString("no_permission")));
                return false;
            }
            // /petbox givebox <player> <type> <amount>
            if(args.length != 4) {
                sender.sendMessage(Color.string(Terminal.messageFile.getString("givebox_command_usage")));
                return false;
            }

            Player target = Bukkit.getPlayer(args[1]);
            if(target == null) {
                sender.sendMessage(Color.string(Terminal.messageFile.getString("pet_not_exists")));
                return false;
            }

            PetBox box = BoxMapper.getMapper().get(args[2]);
            if(box == null) {
                sender.sendMessage(Color.string(Terminal.messageFile.getString("box_not_exists")));
                return false;
            }

            int amount = 0;
            try {
                amount = Integer.valueOf(args[3]);
            }catch(Exception x) {
                sender.sendMessage(Color.string(Terminal.messageFile.getString("invalid_number")));
                return false;
            }

            if(amount < 1 || amount > 64) {
                sender.sendMessage(Color.string(Terminal.messageFile.getString("incorrect_amount")));
                return false;
            }

            box.give(target, amount);
        }

        return false;
    }
}