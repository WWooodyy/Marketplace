package com.woodycreations.marketplace.spigot.command;

import com.woodycreations.marketplace.spigot.menu.TransactionsMenu;
import es.bukkitbettermenus.BukkitBetterMenus;
import es.bukkitbettermenus.MenuService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TransactionsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player player)) {
            commandSender.sendMessage("Only players can execute this command.");
            return false;
        }

        final MenuService service = BukkitBetterMenus.MENU_SERVICE;
        service.open(player, new TransactionsMenu());

        return false;
    }
}
