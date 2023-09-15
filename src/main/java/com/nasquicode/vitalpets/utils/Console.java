package com.nasquicode.vitalpets.utils;

import com.nasquicode.vitalcore.bukkit.utils.Color;
import org.bukkit.Bukkit;

public class Console {
    public static String prefix = "&b[VitalPets]";
    public static void log(String message) {
        Bukkit.getConsoleSender().sendMessage(Color.string(prefix+" &f"+message));
    }
}