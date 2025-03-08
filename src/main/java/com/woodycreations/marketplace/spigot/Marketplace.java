package com.woodycreations.marketplace.spigot;

import com.woodycreations.marketplace.spigot.command.BlackmarketCommand;
import com.woodycreations.marketplace.spigot.command.MarketplaceCommand;
import com.woodycreations.marketplace.spigot.command.SellCommand;
import com.woodycreations.marketplace.spigot.command.TransactionsCommand;
import com.woodycreations.marketplace.spigot.database.MongoCredentials;
import com.woodycreations.marketplace.spigot.database.MongoDB;
import com.woodycreations.marketplace.spigot.database.dao.marketplace.MarketplaceDAO;
import com.woodycreations.marketplace.spigot.database.dao.user.UserDAO;
import es.bukkitbettermenus.BukkitBetterMenus;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class Marketplace extends JavaPlugin {
    private MongoDB mongoDB;

    @Override
    public void onEnable() {
        this.mongoDB = new MongoDB(MongoCredentials.builder().build());

        this.setupMenuAPI();
        this.registerDAOs();
        this.registerCommands();
    }

    private void setupMenuAPI() {
        BukkitBetterMenus.setPlugin(this);
        BukkitBetterMenus.registerEventListeners(this, Bukkit.getPluginManager());
    }

    private void registerDAOs() {
        this.mongoDB.registerDAO(new MarketplaceDAO(this.mongoDB));
        this.mongoDB.registerDAO(new UserDAO(this.mongoDB));
    }

    private void registerCommands() {
        this.getCommand("transactions").setExecutor(new TransactionsCommand());
        this.getCommand("sell").setExecutor(new SellCommand(this));
        this.getCommand("marketplace").setExecutor(new MarketplaceCommand());
        this.getCommand("marketplace").setExecutor(new BlackmarketCommand());
    }
}
