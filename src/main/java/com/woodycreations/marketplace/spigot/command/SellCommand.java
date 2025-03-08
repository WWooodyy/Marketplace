package com.woodycreations.marketplace.spigot.command;

import com.woodycreations.marketplace.spigot.Marketplace;
import com.woodycreations.marketplace.spigot.database.dao.marketplace.entity.MarketEntity;
import com.woodycreations.marketplace.spigot.event.PlayerListItemEvent;
import com.woodycreations.marketplace.spigot.state.MarketState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class SellCommand implements CommandExecutor {
    private final Marketplace plugin;

    public SellCommand(Marketplace plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player player)) {
            commandSender.sendMessage("Only players can execute this command.");
            return false;
        }

        final ItemStack item = player.getInventory().getItemInMainHand();

        if (item.getType().isAir()) {
            player.sendMessage("You must be holding an item to sell it.");
            return false;
        }

        if (strings.length != 1) {
            player.sendMessage(command.getUsage());
            return false;
        }

        final double price = Double.parseDouble(strings[0]);

        final MarketEntity entity = MarketEntity.builder()
            .item(item)
            .seller(player)
            .status(MarketState.ACTIVE)
            .price(price)
            .build();

        this.plugin.getMongoDB().getDatastore().save(entity);
        player.getInventory().setItemInMainHand(null);
        player.sendMessage("Successfully listed item for sale.");

        final PlayerListItemEvent event = new PlayerListItemEvent(player, entity);
        event.callEvent();
        return true;
    }
}

