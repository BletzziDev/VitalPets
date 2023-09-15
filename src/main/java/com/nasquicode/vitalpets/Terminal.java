package com.nasquicode.vitalpets;

import com.nasquicode.vitalcore.bukkit.api.VitalCoreAPI;
import com.nasquicode.vitalcore.bukkit.objects.Database;
import com.nasquicode.vitalcore.bukkit.utils.CustomFileConfiguration;
import com.nasquicode.vitalpets.commands.PetCommand;
import com.nasquicode.vitalpets.listeners.PlayerConnectionListener;
import com.nasquicode.vitalpets.misc.Constants;
import com.nasquicode.vitalpets.utils.Console;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class Terminal extends JavaPlugin {

    public static Terminal instance;
    public static VitalCoreAPI coreApi;
    public static CustomFileConfiguration config;
    public static CustomFileConfiguration messageFile;
    public static CustomFileConfiguration petFile;
    public static CustomFileConfiguration rarityFile;
    public static CustomFileConfiguration candyFile;
    public static CustomFileConfiguration boxeFile;
    public static CustomFileConfiguration menuFile;
    public static Database database;

    @Override
    public void onEnable() {
        Console.log("&eInitializing plugin...");
        Terminal.instance = this;
        coreApi = com.nasquicode.vitalcore.bukkit.Terminal.api;

        Console.log("&eLoading files...");
        try {
            config = new CustomFileConfiguration(Constants.configFile,this);
            Console.log(String.format("&eThe file &b%s &ehas been loaded.", Constants.configFile));
            messageFile = new CustomFileConfiguration(Constants.messageFile,this);
            Console.log(String.format("&eThe file &b%s &ehas been loaded.", Constants.messageFile));
            petFile = new CustomFileConfiguration(Constants.petFile,this);
            Console.log(String.format("&eThe file &b%s &ehas been loaded.", Constants.petFile));
            rarityFile = new CustomFileConfiguration(Constants.rarityFile,this);
            Console.log(String.format("&eThe file &b%s &ehas been loaded.", Constants.rarityFile));
            candyFile = new CustomFileConfiguration(Constants.candyFile,this);
            Console.log(String.format("&eThe file &b%s &ehas been loaded.", Constants.candyFile));
            boxeFile = new CustomFileConfiguration(Constants.boxeFile,this);
            Console.log(String.format("&eThe file &b%s &ehas been loaded.", Constants.boxeFile));
            menuFile = new CustomFileConfiguration(Constants.menuFile,this);
            Console.log(String.format("&eThe file &b%s &ehas been loaded.", Constants.menuFile));
            Console.log("&aAll files has been loaded.");
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }

        if(coreApi.getDatabase(config.getString("storage.database-identifier")) == null) {
            Console.log(String.format("&cThe database %s not exists! Disabling plugin.", config.getString("storage.database-identifier")));
            getPluginLoader().disablePlugin(this);
            return;
        }
        database = coreApi.getDatabase(config.getString("storage.database-identifier"));
        Console.log(String.format("&eDatabase &b%s &ehas been hooked.", database.getInfo().getKEY()));

        Console.log("&eRegistering commands and listeners...");
        getCommand("pet").setExecutor(new PetCommand());
        Bukkit.getPluginManager().registerEvents(new PlayerConnectionListener(), this);
        Console.log("&aAll commands and listeners has been registered.");

        Console.log("&aThe plugin started successfully!");
    }

    @Override
    public void onDisable() {
        Console.log("&cPlugin has been disabled.");
    }
}