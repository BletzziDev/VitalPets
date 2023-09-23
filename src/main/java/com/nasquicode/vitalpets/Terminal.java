package com.nasquicode.vitalpets;

import com.nasquicode.vitalcore.bukkit.api.VitalCoreAPI;
import com.nasquicode.vitalcore.bukkit.objects.Database;
import com.nasquicode.vitalcore.bukkit.utils.CustomFileConfiguration;
import com.nasquicode.vitalpets.commands.PetCommand;
import com.nasquicode.vitalpets.database.PetDAO;
import com.nasquicode.vitalpets.listeners.CurrencyIncomeListener;
import com.nasquicode.vitalpets.listeners.InventoryInteractionListener;
import com.nasquicode.vitalpets.listeners.PlayerConnectionListener;
import com.nasquicode.vitalpets.mappers.*;
import com.nasquicode.vitalpets.misc.Constants;
import com.nasquicode.vitalpets.objects.Pet;
import com.nasquicode.vitalpets.objects.PlayerData;
import com.nasquicode.vitalpets.tasks.PetTeleportTask;
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
    public static CustomFileConfiguration boxFile;
    public static CustomFileConfiguration menuFile;
    public static Database database;
    public static PetDAO dao;

    @Override
    public void onEnable() {
        Console.log("&eInitializing plugin...");
        Terminal.instance = this;
        coreApi = com.nasquicode.vitalcore.bukkit.Terminal.api;

        Console.log("&eLoading files...");
        try {
            config = new CustomFileConfiguration(Constants.configFile, this);
            Console.log(String.format("&eThe file &b%s &ehas been loaded.", Constants.configFile));
            messageFile = new CustomFileConfiguration(Constants.messageFile, this);
            Console.log(String.format("&eThe file &b%s &ehas been loaded.", Constants.messageFile));
            petFile = new CustomFileConfiguration(Constants.petFile, this);
            Console.log(String.format("&eThe file &b%s &ehas been loaded.", Constants.petFile));
            rarityFile = new CustomFileConfiguration(Constants.rarityFile, this);
            Console.log(String.format("&eThe file &b%s &ehas been loaded.", Constants.rarityFile));
            candyFile = new CustomFileConfiguration(Constants.candyFile, this);
            Console.log(String.format("&eThe file &b%s &ehas been loaded.", Constants.candyFile));
            boxFile = new CustomFileConfiguration(Constants.boxFile, this);
            Console.log(String.format("&eThe file &b%s &ehas been loaded.", Constants.boxFile));
            menuFile = new CustomFileConfiguration(Constants.menuFile, this);
            Console.log(String.format("&eThe file &b%s &ehas been loaded.", Constants.menuFile));
            Console.log("&aAll files has been loaded.");
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }

        if (coreApi.getDatabase(config.getString("storage.database-identifier")) == null) {
            Console.log(String.format("&cThe database %s not exists! Disabling plugin.", config.getString("storage.database-identifier")));
            getPluginLoader().disablePlugin(this);
            return;
        }
        database = coreApi.getDatabase(config.getString("storage.database-identifier"));
        dao = new PetDAO(database.getConnection());
        dao.setup();
        Console.log(String.format("&eDatabase &b%s &ehas been hooked.", database.getInfo().getKEY()));

        Console.log("&eLoading plugin objects...");
        RarityMapper.register();
        Console.log(String.format("&b%s &erarities has been loaded.", String.valueOf(RarityMapper.getMapper().size())));
        PetTypeMapper.register();
        Console.log(String.format("&b%s &epets has been loaded.", String.valueOf(PetTypeMapper.getMapper().size())));
        CandyMapper.register();
        Console.log(String.format("&b%s &ecandies has been loaded.", String.valueOf(CandyMapper.getMapper().size())));
        BoxMapper.register();
        Console.log(String.format("&b%s &eboxes has been loaded.", String.valueOf(BoxMapper.getMapper().size())));
        Console.log("&aAll plugin objects has been loaded.");

        Constants.inventoryNames.clear();
        Constants.inventoryNames.add(menuFile.getString("main.name"));
        Constants.inventoryNames.add(menuFile.getString("pet_storage.name"));

        Console.log("&eRegistering commands and listeners...");
        getCommand("pet").setExecutor(new PetCommand());
        Bukkit.getPluginManager().registerEvents(new PlayerConnectionListener(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryInteractionListener(), this);
        Bukkit.getPluginManager().registerEvents(new CurrencyIncomeListener(), this);
        Console.log("&aAll commands and listeners has been registered.");

        new PetTeleportTask().runTaskTimer(this, 100L, 100L);
        Console.log("&aThe plugin started successfully!");
    }

    @Override
    public void onDisable() {
        Console.log("&cPlugin has been disabled.");
    }
}