package com.nasquicode.vitalpets.commands;

import com.nasquicode.vitalcore.bukkit.utils.Color;
import com.nasquicode.vitalpets.Terminal;
import com.nasquicode.vitalpets.mappers.BoxMapper;
import com.nasquicode.vitalpets.objects.PetBox;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BoxCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        /*
        if(!(sender instanceof Player)) {
            return false;
        }
         */

        if(args.length == 0) {
            return false;
        }

        if(args[0].equalsIgnoreCase("give")) {
            if(!sender.hasPermission("vitalpets.givebox")) {
                sender.sendMessage(Color.string(Terminal.messageFile.getString("no_permission")));
                return false;
            }
            // /boxes give <player> <type> <amount>
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
