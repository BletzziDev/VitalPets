package com.nasquicode.vitalpets.commands;

import com.nasquicode.vitalcore.bukkit.utils.Color;
import com.nasquicode.vitalpets.Terminal;
import com.nasquicode.vitalpets.menus.MainMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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

        return false;
    }
}